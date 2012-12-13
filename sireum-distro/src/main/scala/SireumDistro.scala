/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

import java.net._
import java.nio.file._
import java.nio.file.attribute._
import java.io._
import java.security._
import java.text._
import java.util.Date
import java.util.Properties
import java.util.StringTokenizer
import java.util.zip._
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.HashMap
import scala.collection.immutable.HashSet
import java.util.regex.Pattern

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object SireumDistro extends App {
  type Cli = {
    def parse(args : Seq[String]) : CliResult
  }

  type CliResult = {
    def status : Boolean
    def className : String
    def featureName : String
    def options : scala.Option[AnyRef]
    def printTags(out : PrintWriter, err : PrintWriter)
  }

  type PRunner = {
    def pipeline : PConfig
  }

  type PConfig = {
    def compute(j : PJob) : PJob
  }

  type PJob = {
    def setProperty(k : Object, v : Object) : Object
  }

  val METADATA_DIR = ".metadata/"
  val BUILD_FILENAME = "BUILD"
  val INSTALLED_FEATURES_FILENAME = "installed-features.txt"
  val SAPP_EXT = ".sapp"
  val SAPP_LINK_EXT = ".sapp_link"
  val SAPP_INFO = ".sapp_info"
  val CHECKSUM_SUFFIX = ".checksum"

  val BUFFER_SIZE = 1024
  val GLOBAL_OPTION_KEY = "Global.ProgramOptions"
  val CLI_FEATURE = "Sireum CLI"
  val CLI_CLASS = "org.sireum.cli.SireumCli"

  def outPrint(s : String) {
    scala.Console.out.print(s)
    scala.Console.out.flush
  }

  def outPrintln(s : String) {
    scala.Console.out.println(s)
    scala.Console.out.flush
  }

  def outPrintln {
    scala.Console.out.println
    scala.Console.out.flush
  }

  def errPrintln(s : String) {
    scala.Console.err.println(s)
    scala.Console.err.flush
  }

  def errPrintln {
    scala.Console.err.println
    scala.Console.err.flush
  }

  val allowableCopyDiffFiles = Set[String]()

  val scriptName =
    getOsString match {
      case "win64" | "win32" => "sireum.bat"
      case _                 => "sireum"
    }

  val updateUrl = {
    var url = System.getProperty("sireum.update.url")
    if (url == null) url = System.getenv("SIREUM_UPDATE")
    if (url == null) url = "http://update.sireum.org/dev/latest/"
    if (url != null && !url.startsWith("http://") && !url.startsWith("file://"))
      url = new File(url).toURI.toURL.toExternalForm
    if (url.endsWith("/")) url else url + "/"
  }

  val isDevelopment = {
    val d = System.getenv("SIREUM_DIST")
    if (d == null) true else false
  }

  val sireumDir = new File(args(0))
  var unmanagedDir = new File(System.getProperty("user.home"))

  var _features = Map[String, List[String]]()
  var _checksums = Map[String, String]()
  var _addedClasspathURLs = Set[String]()

  object ProgressPrinter {
    val statusSyms = Seq("\b/", "\b-", "\b\\", "\b|")
    var i = 0
    var lastTime = System.currentTimeMillis
    def first {
      i = 0
      lastTime = System.currentTimeMillis
      outPrint("  ")
      next
    }
    def next {
      val t = System.currentTimeMillis
      if (t - lastTime > 35) {
        outPrint(statusSyms(i))
        i = (i + 1) % statusSyms.length
        lastTime = t
      }
    }
    def last {
      outPrint("\b\b")
    }
  }

  def shouldUpdate(f : String) =
    if (f == scriptName) !isDevelopment else true

  object Mode extends Enumeration {
    type Mode = Value
    val NoUpdate, Replaced, Deleted, Error = Value
  }

  import Mode._

  def deleteJar {
    new File(sireumDir, scriptName + ".jar").deleteOnExit
  }

  try {
    if ((util.Properties.javaVersion.charAt(2) - '0') < 7) {
      outPrintln("This version of Sireum requires at least Java 7")
      deleteJar
      sys.exit(-1)
    }
    if (util.Properties.versionString.substring(8, 12) != "2.10") {
      outPrintln("This version of Sireum requires Scala 2.10")
      deleteJar
      sys.exit(-1)
    }
    if (getOsString == "unsupported") {
      outPrintln(
        "Running on an unsupported platform: some features maybe unavailable")
      outPrintln
    }
    if (args.length < 2) parseCliArgs(Seq("."))
    else parseDistroArgs(1)
  } catch {
    case e : Throwable => logError("Error: ", e)
  }

  def parseDistroArgs(i : Int) {
      def notMode(mode : String, parentMode : String) {
        errPrintln(mode + " is not a mode of " + parentMode)
      }
    val last = i + 1 == args.length
    val strLe = { (s1 : String, s2 : String) => s1 <= s2 }
    val appModes = Seq("distro", "launch", "tools")
    val distroModes = Seq("clean", "install", "list", "uninstall", "update",
      "version")
    val mode = args(i)
    val (parentMode, modes) =
      if (i == 1) ("sireum", (appModes ++ distroModes).sortWith(strLe))
      else ("distro", distroModes)

    val modeMatches = modes.filter(_.startsWith(mode))
    if (modeMatches.size == 1) {

      modeMatches(0) match {
        case "distro" =>
          if (last) distroMode
          else parseDistroArgs(i + 1)
        case "clean" =>
          if (last) cleanApps
          else notMode(args(i + 1), "clean")
        case "install" =>
          if (last) {
            errPrintln("Please specify features to install")
          } else {
            args(i + 1) match {
              case "-d" =>
                if (args.length > i + 3) {
                  unmanagedDir = new File(args(i + 2))
                  if (!unmanagedDir.exists) {
                    errPrintln(unmanagedDir.getAbsolutePath + " does not exist")
                  } else if (!unmanagedDir.isDirectory) {
                    errPrintln(unmanagedDir.getAbsolutePath +
                      " is not a directory")
                  } else
                    updateScriptAndPlatform
                  install(args.slice(i + 3, args.length) : _*)
                } else if (args.length == i + 2) {
                  errPrintln("Missing install option -d argument")
                } else if (args.length == i + 3) {
                  errPrintln("Please specify features to install")
                }
              case arg if arg.startsWith("-") =>
                errPrintln(arg + " is not an option of install")
              case _ =>
                updateScriptAndPlatform
                install(args.slice(i + 1, args.length) : _*)
            }
          }
        case "list" =>
          if (last) {
            updateScriptAndPlatform
            for (f <- getFeatures.keys.toArray.sortWith(strLe))
              outPrintln(removeSappExt(f))
          } else {
            if ("installed".startsWith(args(i + 1))) {
              for (f <- loadInstalledFeatures.sortWith(strLe))
                outPrintln(removeSappExt(f))
            } else notMode(args(i + 1), "list")
          }
        case "uninstall" =>
          if (last) {
            errPrintln("Please specify features to uninstall")
          } else {
            updateScriptAndPlatform
            var allFound = false
            for (j <- i + 1 until args.length if !allFound) {
              if (args(j) == "all")
                allFound = true
            }
            if (allFound) uninstall("all")
            else
              for (j <- i + 1 until args.length)
                uninstall(args(j))
          }
        case "update" =>
          if (last) {
            updateScriptAndPlatform
            update(new ArrayBuffer[String](), true)
          } else notMode(args(i + 1), "update")
        case "version" =>
          if (last) {
            install(CLI_FEATURE)
            outPrintln("Sireum v2 (Build " + readBuild + ")")
          } else notMode(args(i + 1), "version")
        case _ =>
          parseCliArgs(args)
      }
    } else {
      notMode(mode, parentMode)
      outPrintln("Did you mean one of the following modes?")
      for (mm <- modeMatches) {
        outPrintln("  " + mm)
      }
    }
  }

  def logError(text : String, e : Throwable) {
    outPrintln
    errPrintln(text + e.getMessage)
    val f = new File(sireumDir, ".errorlog")
    f.getParentFile.mkdirs
    val fw = new FileWriter(f)
    try {
      val pw = new PrintWriter(fw)
      pw.println("An error occured on " + timeStamp)
      e.printStackTrace(pw)
      fw.close
      outPrintln("Written: " + f.getAbsolutePath)
    } catch {
      case e : Throwable =>
        errPrintln("Error: " + e.getMessage)
    }
  }

  def distroMode {
    outPrintln("Sireum Distro")
    outPrintln
    outPrintln("""Available Top Level Modes:
  install <feature>+           Install features
    Option: -d <dir>             Installation directory for unmanaged apps
                                 [ Default: user's home dir]
  install all                  Install all features
  clean                        Remove stale or backed-up managed apps
  list                         List available features
  list installed               List installed features
  update                       Update features
  uninstall <feature>+         Uninstall features and all features 
                               depending on them
  uninstall all                Uninstall all features and scrub Sireum directory
  version                      Display version
""".trim)
  }

  def parseCliArgs(args : Seq[String]) {
    import language.reflectiveCalls
    install(CLI_FEATURE)
    val cli = getCli
    val cliArgs = args.slice(1, args.length)
    val cr = cli.parse(cliArgs)

    cr.printTags(new PrintWriter(new OutputStreamWriter(scala.Console.out)),
      new PrintWriter(new OutputStreamWriter(scala.Console.err)))

    if (cr.status && cr.className != "") {
      install(cr.featureName)
      execute(cr.className, cr.options.get)
    }
  }

  def execute(className : String, options : AnyRef) {
    val c = Class.forName(className)
    val prc = Class.forName("org.sireum.cli.PipelineRunner")
    if (c.isAssignableFrom(prc)) {
      val job = createPipelineJob(options)
      computePipeline(className, job)
    } else {
      val run = c.getMethod("run", options.getClass)
      run.invoke(null, options)
    }
  }

  def createPipelineJob(option : AnyRef) = {
    import language.reflectiveCalls
    val job = Class.forName("org.sireum.pipeline.PipelineJob").
      getMethod("create").invoke(null).asInstanceOf[PJob]
    job.setProperty(GLOBAL_OPTION_KEY, option)
    job
  }

  def computePipeline(className : String, job : PJob) = {
    import language.reflectiveCalls
    val pc = Class.forName(className).newInstance.asInstanceOf[PRunner].pipeline
    pc.compute(job)
  }

  def getCli = {
    val cliClassName = CLI_CLASS
    Class.forName(cliClassName).newInstance.asInstanceOf[Cli]
  }

  def readBuild = readLine(new File(sireumDir, METADATA_DIR + BUILD_FILENAME))

  def readLine(file : File) = {
    val r = new BufferedReader(new FileReader(file))
    val result = r.readLine.trim
    r.close
    result
  }

  def write(file : File, text : String) {
    file.getParentFile.mkdirs
    val w = new PrintWriter(new FileWriter(file))
    w.println(text)
    w.close
  }

  def updateClasspath(baseDir : File) {
    if (isDevelopment)
      return

    val libDir = new File(baseDir, "lib")
    if (libDir.exists)
      for (f <- libDir.listFiles) {
        if (f.getName.endsWith(".jar"))
          addClasspathURL(f.toURI.toURL)
      }
  }

  def uninstall(featureName : String) {
    var installedFeatures = loadInstalledFeatures

    val all = featureName == "all"

    val keepFiles = Set("sireum", "sireum.bat", "README.TXT")

    if (all) {
      outPrint("Scrubbing " + sireumDir.getAbsolutePath)
      var status = true
      ProgressPrinter.first
      for (f <- sireumDir.listFiles) {
        val fName = f.getName
        if (!keepFiles.contains(fName)) {
          if (fName == "sireum.jar" || fName == "sireum.bat.jar")
            f.deleteOnExit
          else
            status = delete(f, false) && status
        }
      }
      ProgressPrinter.last
      if (status) {
        outPrintln("... done!")
      } else {
        errPrintln("... failed!")
      }
      return
    }

    val features = getFeatures

    val fName = guessFeatureNames(Seq(featureName), features.keys.toSeq)(0)

    if (!installedFeatures.contains(fName))
      return

    var seenFeatures = Set[String]()
    val featuresToDelete = ArrayBuffer[String](fName)
    for ((feature, fPaths) <- features) {
      if (!seenFeatures.contains(feature)) {
        seenFeatures = seenFeatures + feature
        var deleteFiles = false
        val files = ArrayBuffer[String]()
        for (fPath <- fPaths) {
          if (feature == fName || all) {
            deleteFiles = true
          } else if (featuresToDelete.contains(fPath)) {
            deleteFiles = true
            featuresToDelete += feature
          }
          if (!features.contains(fPath)) {
            files += fPath
          }
        }
        if (deleteFiles) {
          if (installedFeatures.contains(feature)) {
            outPrintln("Uninstalling feature : " + feature)
            installedFeatures = installedFeatures.filterNot(_ == feature)
          }
          for (f <- files)
            deleteFile(new File(sireumDir, f), f)
          for (featureToDelete <- featuresToDelete)
            if (featureToDelete.endsWith(SAPP_EXT)) {
              val featureRelPath = "apps/" +
                removeSappExt(featureToDelete).toLowerCase
              val featureDir = new File(sireumDir, featureRelPath)
              if (featureDir.exists)
                featureDir.delete
              val mFeatureDir = new File(sireumDir, METADATA_DIR +
                featureRelPath)
              if (mFeatureDir.exists)
                delete(mFeatureDir, false)
            }
        }
      }
    }
    saveInstalledFeatures(installedFeatures)
  }

  def install(featureNames : String*) {
    updateClasspath(sireumDir)

    if (isDevelopment)
      return

    {
      val installedFeatures = loadInstalledFeatures
      if (featureNames.forall(installedFeatures.contains(_)))
        return
    }

    val features = getFeatures

    for (featureName <- guessFeatureNames(featureNames, features.keys.toSeq)) {

      val newFeatures = new ArrayBuffer[String]()

      val installedFeatures = loadInstalledFeatures
      if (!installedFeatures.contains(featureName))
        if (features.contains(featureName)) {
          outPrintln("Installing " + featureName + " feature in " +
            sireumDir.getAbsolutePath)

          val installedFiles = downloadNewFiles(features,
            installedFeatures.toSet, featureName, newFeatures)
          update(newFeatures, featureName.endsWith(SAPP_EXT), installedFiles)
          saveInstalledFeatures(newFeatures.toList ++ installedFeatures)
        } else {
          errPrintln("Invalid feature: " + featureName + "!")
          errPrintln
          sys.exit
        }
    }
  }

  def downloadNewFiles(features : Map[String, List[String]],
                       installedFeatures : Set[String],
                       featureName : String,
                       newFeatures : ArrayBuffer[String]) = {
    var installedFiles = HashSet[String]()
    for (
      fPath <- getAllFilenames(features, installedFeatures.toSet,
        featureName, newFeatures)
    ) {
      val file = new File(sireumDir, fPath)
      if (!isDownloaded(file))
        if (downloadFile(false, fPath, file))
          installedFiles = installedFiles + fPath
    }
    installedFiles
  }

  def abnormalExit {
    outPrintln
    outPrintln("Warning: Sireum maybe in an inconsistent state; " +
      "run clean and update to try to fix the issue.")
    outPrintln
    sys.exit
  }

  def isDownloaded(f : File) =
    if (f.getName.endsWith(SAPP_EXT))
      new File(sireumDir, METADATA_DIR + relativize(sireumDir, f) +
        CHECKSUM_SUFFIX).exists
    else
      f.exists

  def updateScriptAndPlatform {
    val checksums = getChecksums

    val file = new File(sireumDir, scriptName)
    val checksum = checksums(scriptName)

      def getPlatformFileUpdates : List[(String, String)] = {
        val platformDir = new File(sireumDir, METADATA_DIR + "apps/platform")
        var result = List[(String, String)]()
        if (platformDir.isDirectory) {
          for (f <- platformDir.listFiles) {
            if (f.getName.endsWith(CHECKSUM_SUFFIX)) {
              val fName = f.getName
              val filePath = "apps/platform/" +
                f.getName.substring(0, fName.length - CHECKSUM_SUFFIX.length)
              val checksum = readLine(f).trim
              if (checksum != checksums(filePath))
                result = (filePath, checksum) :: result
            }
          }
        }
        result
      }

    val pfiles = getPlatformFileUpdates

    if (checksum == getChecksum(file) && pfiles.isEmpty)
      return

    outPrintln("Updating Sireum in " + sireumDir.getAbsolutePath)

    var replacedCount = 0
    var deleteCount = 0
    var errorCount = 0

      def status(x : Mode) =
        x match {
          case Replaced =>
            replacedCount += 1
          case Deleted =>
            deleteCount += 1
          case Error =>
            errorCount += 1
          case NoUpdate =>
        }

    status(updateFile(checksum, scriptName, file, None))
    pfiles.foreach { pfile =>
      val (filePath, checksum) = pfile
      status(updateFile(checksums(filePath), filePath,
        new File(sireumDir, filePath), Some(checksum)))
    }

    printStatus(false, replacedCount, deleteCount, errorCount, 0, Seq())

    if (errorCount != 0)
      new File(sireumDir, scriptName + ".new").delete

    sys.exit
  }

  def update(newFeatures : ArrayBuffer[String], isApp : Boolean,
             installedFiles : Set[String] = Set()) {
    val checksums = getChecksums
    val features = getFeatures

    if (installedFiles.isEmpty) {
      outPrintln("Updating Sireum in " + sireumDir.getAbsolutePath)
    }

    val installedFileCount = installedFiles.size
    var downloadCount = installedFileCount
    var replacedFiles = installedFiles
    var errorCount = 0
    var deleteCount = 0

      def update(filePath : String, f : File,
                 currChecksum : Option[String] = None) {
        if (!replacedFiles.contains(filePath) && shouldUpdate(filePath) &&
          checksums.contains(filePath)) {
          updateFile(checksums(filePath), filePath, f, currChecksum) match {
            case Replaced =>
              replacedFiles = replacedFiles + filePath
            case Deleted =>
              deleteCount += 1
            case Error =>
              errorCount += 1
            case NoUpdate =>
          }
        }
      }

      def updateInstalledFeatures {
        val features = getFeatures
        var installedFeatures = loadInstalledFeatures
        for (f <- installedFeatures)
          if (features.contains(f))
            for (fPath <- features(f)) {
              val file = new File(sireumDir, fPath)
              if (!features.contains(fPath)) {
                if (!isAppFile(file))
                  update(fPath, file)
                else if (isApp && !new File(sireumDir, METADATA_DIR + fPath +
                  CHECKSUM_SUFFIX).exists &&
                  (file.getParentFile != new File(sireumDir, "apps")))
                  if (downloadFile(false, fPath, file))
                    downloadCount += 1
              } else if (features.contains(fPath) &&
                !installedFeatures.contains(fPath) &&
                (isApp || !isAppFile(file))) {
                val newFeatures2 = ArrayBuffer[String]()
                val installedFiles =
                  downloadNewFiles(features, installedFeatures.toSet, fPath,
                    newFeatures2)
                downloadCount += installedFiles.size
                replacedFiles ++= installedFiles
                installedFeatures ++= newFeatures2
                newFeatures ++= newFeatures2
              }
            }
          else {
            installedFeatures = installedFeatures.filterNot(_ == f)
            if (f.endsWith(SAPP_EXT)) {
              val file = new File(sireumDir, "apps/" + removeSappExt(f))
              deleteRec(file, "Deleting obsoelete feature: " + f, true)
            }
          }
        saveInstalledFeatures(installedFeatures)
      }

      def updateExisting(dirFile : File, relDirPath : String) {
        val metaPath = new File(sireumDir, METADATA_DIR).getAbsolutePath
        for (f <- dirFile.listFiles)
          if (f.isDirectory) {
            if (dirFile.getAbsolutePath != metaPath)
              updateExisting(f, relDirPath + f.getName + "/")
          } else if (!isAppFile(f)) {
            val filePath = relDirPath + f.getName
            update(filePath, f)
          }
      }

    if (isApp) {
      val metaDir = new File(sireumDir, METADATA_DIR)
      val metaAppDir = new File(metaDir, "apps")

      if (metaAppDir.exists)
        for (d <- metaAppDir.listFiles) {
          if (d.isDirectory)
            for (f <- d.listFiles) {
              if (f.getName.endsWith(CHECKSUM_SUFFIX)) {
                val currentChecksum = readLine(f)
                var filePath = relativize(metaDir, f)
                filePath = filePath.substring(0, filePath.length -
                  CHECKSUM_SUFFIX.length)
                update(filePath, new File(sireumDir, filePath),
                  Some(currentChecksum))
              }
            }
        }
    }

    updateInstalledFeatures
    updateExisting(sireumDir, "")

    printStatus(true, replacedFiles.size - installedFileCount, deleteCount,
      errorCount, downloadCount, newFeatures)

    updateClasspath(sireumDir)
  }

  def printStatus(buildDownload : Boolean,
                  replacedCount : Int, deleteCount : Int, errorCount : Int,
                  downloadCount : Int, newFeatures : Seq[String]) {
    if (replacedCount == 0 && deleteCount == 0 && errorCount == 0 &&
      downloadCount == 0)
      outPrintln("There was no update.")
    else {
      if (buildDownload)
        downloadBuild
      outPrintln
      outPrintln("Finished updating Sireum.")
      if (newFeatures.size > 0) {
        outPrintln
        outPrintln("Newly installed feature(s): " + newFeatures.size)
        for (f <- newFeatures)
          outPrintln("* " + f)
        outPrintln
      }
      outPrintln("File download(s): " + downloadCount)
      outPrintln("File update(s): " + replacedCount)
      outPrintln("File deletion(s): " + deleteCount)
      outPrintln("Error(s): " + errorCount)
      outPrintln
    }
  }

  def downloadBuild {
    val buildFile = new File(sireumDir, METADATA_DIR + BUILD_FILENAME)
    downloadFile(buildFile.exists, BUILD_FILENAME, buildFile)
  }

  def deleteRemainingAppFiles(filePath : String) {
    val appPath = getAppPath(filePath)
    val dir = new File(sireumDir, appPath).getParentFile
    val f = new File(sireumDir, METADATA_DIR + filePath + ".filelist")
    if (!f.exists)
      return
    val r = new LineNumberReader(new FileReader(f))
    try {
      var line = r.readLine
      while (line != null) {
        val rf = new File(dir, line.trim)
        if (rf.exists)
          delete(rf, false)
        line = r.readLine
      }
    } finally
      r.close
    f.delete
  }

  def deleteFile(file : File, filePath : String) = {
    if (isAppFile(file)) {
      val appPath = getAppPath(filePath)
      val dir = new File(sireumDir, appPath)
      if (dir.exists) {
        if (deleteRec(dir, "Deleting " + appPath, false)) {
          deleteRemainingAppFiles(filePath)
          Deleted
        } else Error
      } else {
        NoUpdate
      }
    } else {
      outPrintln("File " + file.getAbsolutePath +
        " will be deleted upon exit.")
      file.deleteOnExit()
      Deleted
    }
  }

  def updateFile(checksum : String, filename : String, file : File,
                 currChecksum : Option[String]) : Mode =
    try
      checksum match {
        case "0" => deleteFile(file, filename)
        case checksum =>
          if (isAppFile(file) && !isManaged(file))
            NoUpdate
          else {
            val download = if (!file.exists && !isAppFile(file)) true else {
              val currentChecksum =
                if (currChecksum.isDefined) currChecksum.get
                else getChecksum(file)
              checksum != currentChecksum
            }
            if (download) {
              if (file.getName == scriptName && file.getParentFile == sireumDir)
                downloadFile(file.exists, filename,
                  new File(sireumDir, scriptName + ".new"))
              else
                downloadFile(file.exists, filename, file)
              Replaced
            } else
              NoUpdate
          }
      }
    catch {
      case e : Throwable =>
        errPrintln("Failed to update " + file.getAbsolutePath)
        errPrintln("Reason: " + e.getMessage)
        errPrintln
        Error
    }

  def getMacOsString(filename : String) = {
    val osString = getOsString
    val osStringEx = osString + "-10."
    val i = filename.indexOf(osStringEx)
    if (i < 0)
      osString
    else
      filename.substring(i, i + osStringEx.length + 1)
  }

  def getMacOsString = {
    val osString = getOsString
    val e = new Exec
    e.run(-1, Seq("sw_vers", "-productVersion"), None) match {
      case Exec.StringResult(s, _) =>
        val i = s.lastIndexOf(".")
        osString + "-" + s.substring(0, i)
      case _ => "?"
    }
  }

  def isNotForThisPlatform(filename : String) = {
    val osString = getOsString
    osString match {
      case "mac32" | "mac64" =>
        if (filename.indexOf(osString) < 0) true
        else {
          val fOsString = getMacOsString(filename)
          if (fOsString == osString) false
          else getMacOsString != fOsString
        }
      case _ =>
        filename.indexOf(osString) < 0
    }
  }

  def toOsPath(path : String) =
    getOsString match {
      case "win64" | "win32" => path.replace('/', '\\')
      case _                 => path
    }

  def downloadFile(isUpdate : Boolean, filename : String,
                   file : File) : Boolean = {
    if (!isUpdate && isPlatformSpecific(filename)
      && isNotForThisPlatform(filename))
      return false
    outPrint((if (isUpdate) "Updating" else "Downloading") + " file " +
      toOsPath(filename))

    val is = new URL(updateUrl + filename).openStream
    try {
      file.getParentFile.mkdirs
      val os = new BufferedOutputStream(new FileOutputStream(file))
      try {
        val buffer = new Array[Byte](BUFFER_SIZE)
        var n = is.read(buffer)
        ProgressPrinter.first
        while (n != -1) {
          os.write(buffer, 0, n)
          n = is.read(buffer)
          ProgressPrinter.next
        }
      } finally os.close
    } finally is.close

    ProgressPrinter.last
    outPrintln("... done!")

    if (isAppFile(file))
      installApp(file)

    true
  }

  def isAppFile(file : File) = file.getName.endsWith(SAPP_EXT)

  def relativize(baseFile : File, file : File) =
    file.getAbsolutePath.substring(baseFile.getAbsolutePath.length + 1).
      replace("\\", "/")

  def isManaged(file : File) =
    file.getParentFile.getAbsolutePath != new File(sireumDir, "apps").
      getAbsolutePath

  def installApp(file : File) {
    val relPath = relativize(sireumDir, file)
    val managed = isManaged(file)
    val installDir = if (managed) file.getParentFile else unmanagedDir
    val dirs = movePrevApp(file, installDir)
    deleteRemainingAppFiles(relPath)
    if (managed)
      outPrint("Installing managed app file: " + file.getName)
    else
      outPrint("Installing unmanaged app file: " + file.getName + " to " +
        installDir.getAbsolutePath)
    try {
      val fileList =
        if (managed) {
          val checksums = getChecksums
          write(new File(sireumDir, METADATA_DIR + relPath + CHECKSUM_SUFFIX),
            checksums(relPath))
          Some(new PrintWriter(new FileWriter(new File(sireumDir, METADATA_DIR +
            relPath + ".filelist"))))
        } else
          None
      try {
        unzip(file, installDir, fileList)
        file.delete
        dirs match {
          case Some((appDir, appBackupDir)) =>
            copyBackupDiff(appDir, appBackupDir)
          case _ =>
        }
      } finally
        if (fileList.isDefined)
          fileList.get.close
      outPrintln("... done!")
    } catch {
      case e : Throwable =>
        logError("Error installing app: ", e)
        abnormalExit
    }
  }

  def allowableCopyDiff(f : File) =
    f.getName.endsWith(".link") || allowableCopyDiffFiles.contains(f.getName)

  def copyBackupDiff(appDir : File, appBackupDir : File) {
    for (fBackup <- appBackupDir.listFiles) {
      val f = new File(appDir, fBackup.getName)
      if (!f.exists) {
        if (fBackup.isDirectory) {
          copyBackupDiff(f, fBackup)
        } else if (allowableCopyDiff(fBackup)) {
          f.getParentFile.mkdirs
          copyFile(fBackup, f)
        }
      } else if (fBackup.isDirectory && f.isDirectory)
        copyBackupDiff(f, fBackup)
    }
  }

  def copyFile(src : File, dest : File) {
    Files.copy(src.toPath, dest.toPath, StandardCopyOption.REPLACE_EXISTING,
      StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS)
  }

  def getAppPath(filePath : String) = {
    var appPath = filePath
    appPath = removeSappExt(appPath)
    if ((isPlatformSpecific(appPath))) {
      val osString = getOsString
      osString match {
        case "mac32" | "mac64" =>
          appPath = appPath.replace("-" + getMacOsString(appPath), "")
        case _ =>
          appPath = appPath.replace("-" + osString, "")
      }
    }
    appPath
  }

  def movePrevApp(file : File, installDir : File) : Option[(File, File)] =
    if (relativize(sireumDir, installDir) == "apps/platform")
      None
    else {
      val appName = getAppPath(file.getName)
      val appDir = new File(installDir, appName)
      if (appDir.exists) {
        val appDirBackup = new File(appDir.getParentFile, appDir.getName +
          "-backup-" + timeStamp)
        if (appDir.renameTo(appDirBackup)) {
          outPrintln("Moved " + appDir.getName + " to " + appDirBackup.getName)
          Some((appDir, appDirBackup))
        } else {
          errPrintln("Unable to move " + appDir.getName + " to " +
            appDirBackup.getName)
          outPrintln("""This might happen due to file permission issues or it is because some 
Sireum Distro managed apps are currently running.""")
          abnormalExit
          None
        }
      } else
        None
    }

  def delete(file : File, onExit : Boolean) : Boolean = {
    if (file.isDirectory)
      for (f <- file.listFiles) {
        if (f.isDirectory) {
          delete(f, onExit)
        } else {
          if (onExit) f.deleteOnExit
          else Files.delete(f.toPath)
        }
        ProgressPrinter.next
      }
    if (onExit) {
      file.deleteOnExit
      true
    } else
      try {
        Files.delete(file.toPath)
        true
      } catch {
        case e : IOException => false
      }
  }

  def deleteRec(dir : File, msg : String, onExit : Boolean) : Boolean = {
    outPrint(msg)
    ProgressPrinter.first
    val status = delete(dir, onExit)
    ProgressPrinter.last
    if (status) {
      outPrintln("... done!")
      true
    } else {
      errPrintln("... failed!")
      false
    }
  }

  def deleteAppsBackups(dir : File) {
    for (f <- dir.listFiles)
      if ((f.isDirectory && f.getName.indexOf("-backup-") >= 0) || isAppFile(f)
        || f.getName.endsWith(".new"))
        deleteRec(f, "Deleting " + relativize(sireumDir, f), false)
      else if (f.isDirectory)
        deleteAppsBackups(f)
  }

  def cleanApps {
    val appsDir = new File(sireumDir, "apps")
    if (appsDir.exists) {
      deleteAppsBackups(appsDir)
    }
  }

  def timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date)

  def getChecksum(file : File) = {
    val md = MessageDigest.getInstance("MD5")

    val is = new BufferedInputStream(new FileInputStream(file))
    try {
      val dis = new DigestInputStream(is, md)
      while (dis.read != -1) {}
    } finally is.close

    val digest = md.digest

    val result = new StringBuilder
    for (i <- 0 until digest.length) {
      val s = Integer.toString((digest(i) & 0xff), 16)
      if (s.length == 1) result.append('0')
      result.append(s)
    }

    result.toString
  }

  def getChecksums = {
    try {
      if (_checksums.size == 0) {
        val properties = loadProperties("checksums.properties")
        var result = Map[String, String]()
        val i = properties.entrySet.iterator
        while (i.hasNext) {
          val e = i.next
          val key = e.getKey.toString
          val value = e.getValue.toString.toLowerCase
          result = result + (key -> value)
        }
        _checksums = result
      }

      _checksums
    } catch {
      case e : Throwable =>
        errPrintln("Could not connect to update site.")
        errPrintln
        sys.exit
    }
  }

  def getAllFilenames(features : Map[String, List[String]],
                      installedFeatures : Set[String],
                      featureName : String,
                      newFeatures : ArrayBuffer[String]) : List[String] = {
    var l = List(featureName)
    var result = List[String]()
    var seenFeatures = Set[String]()
    while (!l.isEmpty) {
      val fName = l.head
      l = l.tail
      if (!seenFeatures.contains(fName) && !installedFeatures.contains(fName)) {
        seenFeatures = seenFeatures + fName
        for (name <- features(fName)) {
          if (features.contains(name)) {
            l = name :: l
          } else
            result = name :: result
        }
        newFeatures += fName
      }
    }
    result
  }

  def getFeatures = {
    try {
      if (_features.size == 0) {
        val properties = loadProperties("features.properties")
        var result = Map[String, List[String]]()
        val i = properties.entrySet.iterator
        while (i.hasNext) {
          val e = i.next
          val feature = e.getKey.toString.trim
          var filenames = List[String]()
          val st = new StringTokenizer(e.getValue.toString.trim, ",")
          while (st.hasMoreTokens) {
            val filename = st.nextToken.trim
            filenames = filename :: filenames
          }
          result = result + (feature -> filenames.reverse)
        }
        _features = result
      }
      _features
    } catch {
      case e : Throwable =>
        errPrintln("Could not connect to Sireum update site.")
        errPrintln
        sys.exit
    }
  }

  def loadInstalledFeatures = {
    val installedFeaturesFile = new File(sireumDir, METADATA_DIR +
      "installed-features.txt")
    if (!installedFeaturesFile.exists)
      List[String]()
    else {
      val result = new ListBuffer[String]
      val r = new BufferedReader(new FileReader(installedFeaturesFile))
      try {
        var line : String = r.readLine
        while (line != null) {
          result += line.trim
          line = r.readLine
        }
      } finally r.close
      result.toList
    }
  }

  def saveInstalledFeatures(installedFeatures : List[String]) {
    val installedFeaturesFile = new File(sireumDir, METADATA_DIR +
      "installed-features.txt")
    installedFeaturesFile.getParentFile.mkdirs
    val pw = new PrintWriter(new FileWriter(installedFeaturesFile))
    for (f <- installedFeatures)
      pw.println(f)
    pw.close
  }

  def loadProperties(filename : String) = {
    val p = new Properties()

    val is = new URL(updateUrl + filename).openStream()
    try p.load(is)
    finally is.close
    p
  }

  def addClasspathURL(url : URL) {
    val urlText = url.toString
    if (!_addedClasspathURLs.contains(urlText)) {
      _addedClasspathURLs = _addedClasspathURLs + urlText
      val sysLoader = getClass.getClassLoader.asInstanceOf[URLClassLoader]
      val sysClass = classOf[URLClassLoader]

      val m = sysClass.getDeclaredMethod("addURL", classOf[URL])
      m.setAccessible(true)
      m.invoke(sysLoader, url)
    }
  }

  def getOsString = {
    val osArch = System.getProperty("os.arch")
    val is64bit = osArch.contains("64")

    val osName = System.getProperty("os.name").toLowerCase()
    if (osName.indexOf("mac") >= 0)
      (if (is64bit) "mac64" else "mac32")
    else if (osName.indexOf("nux") >= 0)
      (if (is64bit) "linux64" else "linux32")
    else if (osName.indexOf("win") >= 0)
      (if (is64bit) "win64" else "win32")
    else
      "unsupported"
  }

  def isPlatformSpecific(filename : String) =
    (filename.indexOf("mac64") >= 0) || (filename.indexOf("mac32") >= 0) ||
      (filename.indexOf("linux64") >= 0) || (filename.indexOf("linux32") >= 0) ||
      (filename.indexOf("win64") >= 0) || (filename.indexOf("win32") >= 0)

  def unzip(file : File, outputDir : File, pw : Option[PrintWriter]) {
    import scala.collection.JavaConversions._
    import scala.collection.mutable._

    ProgressPrinter.first
    val zipFile = new ZipFile(file)
    val dirLastModMap = Map[String, Long]()
    try {
      for (e <- zipFile.entries) {
        if (pw.isDefined)
          pw.get.println(e.getName)
        unzipEntry(zipFile, e, outputDir, dirLastModMap)
      }

      if (getOsString.indexOf("win") < 0) {
        val ze = zipFile.getEntry(".sapp_info")
        if (ze != null) {
          val is = zipFile.getInputStream(ze)
          val p = new Properties
          p.load(is)
          for (e <- p) {
            try {
              Files.setPosixFilePermissions(new File(outputDir, e._1).toPath,
                maskToPermSet(e._2.toInt))
            } catch {
              case e : IOException =>
            }
            ProgressPrinter.next
          }
        }
      }
    } finally zipFile.close

    for (
      path <- dirLastModMap.keys.toSeq.sortWith({
        (s1, s2) =>
          ProgressPrinter.next
          s1.compareTo(s2) > 0
      })
    ) {
      new File(path).setLastModified(dirLastModMap(path))
      ProgressPrinter.next
    }
    ProgressPrinter.last
  }

  def maskToPermSet(mask : Int) = {
    import scala.collection.mutable._
    val result = Set[PosixFilePermission]()
    val values = PosixFilePermission.values
    for (i <- 0 until values.length) {
      if ((mask & (1 << i)) > 0) {
        result += values(i)
      }
    }
    result
  }

  def unzipEntry(zipFile : ZipFile, entry : ZipEntry, outputDir : File,
                 dirLastModMap : scala.collection.mutable.Map[String, Long]) {
    val entryName = entry.getName
    if (!(entryName.indexOf("__MACOSX") < 0 &&
      entryName.indexOf(".DS_Store") < 0 &&
      entryName.indexOf(SAPP_INFO) < 0)) return

    if (entryName.endsWith(SAPP_LINK_EXT)) {
      val bytes = new Array[Byte](entry.getSize.toInt)
      val is = zipFile.getInputStream(entry)
      try {
        val n = is.read(bytes)
        assert(n == bytes.length)
      } finally is.close
      val outputFile = new File(outputDir,
        entryName.substring(0, entryName.length - SAPP_LINK_EXT.length))
      if (outputFile.exists)
        delete(outputFile, false)
      val path = new File(new String(bytes)).toPath
      try {
        Files.createSymbolicLink(outputFile.toPath, path)
      } catch {
        case e : IOException =>
          errPrintln("Could not create symbolic link: " +
            outputFile.getAbsolutePath + " to " + path)
      }
      val time = entry.getTime
      if (time != 0)
        outputFile.setLastModified(time)
      return
    }

    if (entry.isDirectory) {
      val dir = new File(outputDir, entryName)
      dir.mkdirs
      val time = entry.getTime
      if (time != 0) {
        dirLastModMap(dir.getAbsolutePath) = time
      }
    } else {
      val outputFile = new File(outputDir, entryName)
      if (!outputFile.getParentFile.exists)
        outputFile.getParentFile.mkdirs

      val is = new BufferedInputStream(zipFile.getInputStream(entry))
      val os = new BufferedOutputStream(new FileOutputStream(outputFile))

      try transfer(is, os)
      finally {
        os.close
        is.close
      }

      outputFile.setReadable(true)
      outputFile.setWritable(true)
      outputFile.setExecutable(true)

      val time = entry.getTime
      if (time != 0)
        outputFile.setLastModified(time)
    }
  }

  def transfer(is : InputStream, os : OutputStream) {
    val buf = new Array[Byte](BUFFER_SIZE)
    var len = is.read(buf)
    while (len > 0) {
      ProgressPrinter.next
      os.write(buf, 0, len)
      len = is.read(buf)
    }
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  object Exec {
    sealed abstract class Result
    object Timeout extends Result
    case class ExceptionRaised(e : Exception) extends Result
    case class StringResult(s : String, exitValue : Int) extends Result
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  class Exec {
    import Exec._
    import scala.actors._
    import scala.actors.Actor._

    def run(waitTime : Long, args : Seq[String], input : Option[String],
            dir : Option[File] = None) : Result = {
      singleReader(self, waitTime, dir) ! (args, input)

      (if (waitTime < 0)
        receive[Result] _
      else receiveWithin[Result](waitTime) _) {
        case TIMEOUT | Some(TIMEOUT) =>
          Timeout
        case e : Exception =>
          ExceptionRaised(e)
        case (result : String, exitValue : Int) =>
          StringResult(result, exitValue)
      }
    }

    private def singleReader(caller : Actor, waitTime : Long,
                             dir : Option[File]) = actor {
      (if (waitTime < 0)
        react _
      else
        reactWithin(waitTime) _) {
        case TIMEOUT =>
          caller ! Some(TIMEOUT)
        case (args : Seq[_], in : Option[_]) =>
          import java.io._
          val processBuilder = new ProcessBuilder(
            args.asInstanceOf[Seq[String]] : _*)
          if (dir.isDefined)
            processBuilder.directory(dir.get)
          processBuilder.redirectErrorStream(true)
          try {
            val proc = processBuilder.start()
            if (in.isDefined) {
              val osr = new OutputStreamWriter(proc.getOutputStream)
              osr.write(in.get.asInstanceOf[String])
              osr.flush
            }
            val br = new BufferedReader(
              new InputStreamReader(proc.getInputStream))
            val sb = new StringBuilder()
            var line : String = null
            while ({ line = br.readLine; line != null }) {
              sb.append(line)
              sb.append('\n')
            }
            proc.waitFor
            caller ! (sb.toString, proc.exitValue)
          } catch {
            case e : Exception =>
              caller ! e
          }
      }
    }
  }

  def removeSappExt(s : String) =
    if (s.endsWith(SAPP_EXT))
      s.substring(0, s.length - SAPP_EXT.length)
    else s

  def guessFeatureNames(featureNames : Seq[String],
                        features : Seq[String]) : Seq[String] = {
    if (featureNames.exists(_ == "all"))
      return features

    var sFeatures = Map[String, String]()
    for (f <- features)
      sFeatures += removeSappExt(f) -> f

    val result = new Array[String](featureNames.length)

    var featureNotFound = false
    for (i <- 0 until featureNames.length) {
      val fName = featureNames(i)
      val sfName = removeSappExt(fName)
      if (sFeatures.contains(sfName)) {
        result(i) = sFeatures(sfName)
      } else {
        val camel =
          if (sfName.forall(_.isLower)) sfName.toUpperCase else sfName
        val matches = camelMatches(camel, sFeatures)
        if (matches.size == 1) {
          result(i) = matches.head
        } else {
          featureNotFound = true
          errPrintln("Invalid feature: " + fName + "!")
          if (matches.size > 1) {
            outPrintln("Did you mean one of the following features?")
            for (fm <- matches.toSeq.sortWith((s1, s2) => s1.compareTo(s2) < 0))
              outPrintln("  " + removeSappExt(fm))
          }
        }
      }
    }
    if (featureNotFound)
      sys.exit(-1)

    result
  }

  def camelMatches(camel : String, m : Map[String, String]) = {
    var result = List[String]()
    val query = camel.replaceAll("\\*", ".*?")
    val re = "\\b(" + query.replaceAll("([A-Z][^A-Z]*)", "$1[^A-Z]*") + ".*?)\\b"
    val regex = Pattern.compile(re)

    m.filter(e => regex.matcher(e._1).find).map(_._2)
  }
}
