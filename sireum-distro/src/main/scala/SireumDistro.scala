/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

import java.io._
import java.net._
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
  val GUESS_DISTANCE_MAX = 3

  val BUFFER_SIZE = 1024
  val GLOBAL_OPTION_KEY = "Global.ProgramOptions"
  val CLI_FEATURE = "Sireum CLI"
  val CLI_CLASS = "org.sireum.cli.SireumCli"

  lazy val out = scala.Console.out
  lazy val err = scala.Console.err

  val allowableCopyDiffFiles = Set[String]()

  val scriptName =
    getOsString match {
      case "win64" | "win32" => "sireum.bat"
      case _                 => "sireum"
    }

  val updateUrl = {
    var url = System.getProperty("sireum.update.url")
    if (url == null) url = System.getenv("SIREUM_UPDATE")
    if (url == null) url = "http://update.sireum.org/"
    if (url != null && !url.startsWith("http://") && !url.startsWith("file://"))
      url = new File(url).toURI.toURL.toExternalForm
    if (url.endsWith("/")) url else url + "/"
  }

  val isDevelopment = {
    val d = System.getenv("SIREUM_DIST")
    if (d == null) true else false
  }

  object StatusPrinter {
    val statusSyms = Seq("\b/", "\b-", "\b\\", "\b|")
    var i = 0
    var lastTime = System.currentTimeMillis
    def first {
      i = 0
      lastTime = System.currentTimeMillis
      out.print("  ")
      out.flush
      next
    }
    def next {
      val t = System.currentTimeMillis
      if (t - lastTime > 35) {
        out.print(statusSyms(i))
        i = (i + 1) % statusSyms.length
        lastTime = t
      }
      out.flush
    }
    def last {
      out.print("\b\b")
      out.flush
    }
  }

  var _features = Map[String, List[String]]()
  var _checksums = Map[String, String]()
  var _addedClasspathURLs = Set[String]()

  def shouldUpdate(f : String) =
    if (f == scriptName) !isDevelopment else true

  object Mode extends Enumeration {
    type Mode = Value
    val NoUpdate, Replaced, Deleted, Error = Value
  }

  import Mode._

  val sireumDir = new File(args(0))
  var unmanagedDir = new File(System.getProperty("user.home"))

  def deleteJar {
    new File(sireumDir, scriptName + ".jar").deleteOnExit
  }

  try {
    if ((util.Properties.javaVersion.charAt(2) - '0') < 7) {
      out.println("This version of Sireum requires at least Java 7")
      out.flush
      deleteJar
      sys.exit(-1)
    }
    if (util.Properties.versionString.substring(8) != "2.9.2") {
      out.println("This version of Sireum requires Scala 2.9.2")
      out.flush
      deleteJar
      sys.exit(-1)
    }
    if (getOsString == "unsupported") {
      out.println("Running on an unsupported platform: some features maybe unavailable")
      out.println
      out.flush
    }
    args match {
      case Array(_, "distro") =>
        distroMode
      case Array(_, "clean") =>
        cleanApps
      case Array(_, "list") =>
        for (f <- getFeatures.keys.toArray.sortWith { (f1, f2) => f1 <= f2 })
          out.println(removeSappExt(f))
        out.flush
      case Array(_, "list", m) =>
        if (m == "installed")
          for (f <- loadInstalledFeatures.sortWith { (f1, f2) => f1 <= f2 })
            out.println(removeSappExt(f))
        else {
          err.println(m + " is not a mode of list")
          err.flush
        }
        out.flush
      case Array(_, "update") =>
        update(new ArrayBuffer[String](), true)
      case Array(_, "update", m) =>
        if (m == "script")
          updateScript
        else {
          err.println(m + " is not a mode of update")
          err.flush
        }
      case Array(_, "version") =>
        install(CLI_FEATURE)
        out.println("Sireum v2 (Build " + readBuild + ")")
        out.flush
      case Array(_, "install") =>
        out.println("Please specify features to install")
        out.flush
      case _ if args.length >= 3 && args(1) == "install" =>
        args(2) match {
          case "-d" =>
            if (args.length >= 5) {
              unmanagedDir = new File(args(3))
              install(args.slice(4, args.length) : _*)
            } else {
              err.println("Missing install argument")
              err.flush()
            }
          case arg if arg.startsWith("-") =>
            err.println(arg + " is not an option of install")
            err.flush()
          case _ =>
            install(args.slice(2, args.length) : _*)
        }
      case _ if args.length >= 3 && args(1) == "uninstall" =>
        uninstall(args.slice(2, args.length).deep.mkString(" "))
      case _ if args.length > 0 =>
        parseArgs(args)
      case _ =>
        parseArgs(Seq("."))
    }
  } catch {
    case e => logError("Error: ", e)
  }

  def logError(text : String, e : Throwable) {
    out.println
    out.flush
    err.println(text + e.getMessage)
    err.flush
    val f = new File(sireumDir, ".errorlog")
    f.getParentFile.mkdirs
    val fw = new FileWriter(f)
    try {
      val pw = new PrintWriter(fw)
      pw.println("An error occured on " + timeStamp)
      e.printStackTrace(pw)
      fw.close
      out.println("Written: " + f.getAbsolutePath)
      out.flush
    } catch {
      case e =>
        err.println("Error: " + e.getMessage)
        err.flush
    }
  }

  def distroMode {
    out.println("Sireum Distro")
    out.println
    out.println("""Available Top Level Modes:
  install <feature>+           Install features
    Option: -d <dir>             Installation directory for unmanaged apps
                                 [ Default: user's home dir]
  clean                        Remove stale or backed-up managed apps
  list                         List available features
  list installed               List installed features
  update                       Update features
  update script                Update Sireum script
  uninstall <feature>          Uninstall a feature and all features 
                               depending on it
  uninstall all                Uninstall all features and scrub Sireum directory
  version                      Display version
""".trim)
    out.flush
  }

  def parseArgs(args : Seq[String]) {
    install(CLI_FEATURE)
    val cli = getCli
    val cliArgs = args.slice(1, args.length)
    val cr = cli.parse(cliArgs)

    cr.printTags(new PrintWriter(new OutputStreamWriter(out)),
      new PrintWriter(new OutputStreamWriter(err)))

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
    val job = Class.forName("org.sireum.pipeline.PipelineJob").
      getMethod("create").invoke(null).asInstanceOf[PJob]
    job.setProperty(GLOBAL_OPTION_KEY, option)
    job
  }

  def computePipeline(className : String, job : PJob) = {
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
      out.print("Scrubbing " + sireumDir.getAbsolutePath)
      var status = true
      StatusPrinter.first
      for (f <- sireumDir.listFiles) {
        val fName = f.getName
        if (!keepFiles.contains(fName)) {
          if (fName == "sireum.jar" || fName == "sireum.bat.jar")
            f.deleteOnExit
          else
            status = delete(f, false) && status
        }
      }
      StatusPrinter.last
      if (status) {
        out.println("... done!")
        out.flush
      } else {
        err.println("... failed!")
        err.flush
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
            out.println("Uninstalling feature : " + feature)
            out.flush
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
              val mFeatureDir = new File(sireumDir, METADATA_DIR + featureRelPath)
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

    val features = getFeatures

    val newFeatures = new ArrayBuffer[String]()

    for (featureName <- guessFeatureNames(featureNames, features.keys.toSeq)) {

      val installedFeatures = loadInstalledFeatures
      if (!installedFeatures.contains(featureName))
        if (features.contains(featureName)) {
          out.println("Installing " + featureName + " feature in " +
            sireumDir.getAbsolutePath)
          out.flush

          val installedFiles = downloadNewFiles(features, installedFeatures.toSet,
            featureName, newFeatures)
          update(newFeatures, featureName.endsWith(SAPP_EXT), installedFiles)
          saveInstalledFeatures(newFeatures.toList ++ installedFeatures)
        } else {
          err.println("Invalid feature: " + featureName + "!")
          err.println
          err.flush
          sys.exit
        }
    }
  }

  def downloadNewFiles(features : Map[String, List[String]],
                       installedFeatures : Set[String],
                       featureName : String,
                       newFeatures : ArrayBuffer[String]) = {
    var installedFiles = HashSet[String]()
    for (fPath <- getAllFilenames(features, installedFeatures.toSet, featureName, newFeatures)) {
      val file = new File(sireumDir, fPath)
      if (!isDownloaded(file))
        if (downloadFile(false, fPath, file))
          installedFiles = installedFiles + fPath
    }
    installedFiles
  }

  def abnormalExit {
    out.println("Warning: Sireum maybe in an inconsistent state; " +
      "run clean and update to try to fix the issue.")
    out.flush
    sys.exit
  }

  def isDownloaded(f : File) =
    if (f.getName.endsWith(SAPP_EXT))
      new File(sireumDir, METADATA_DIR + relativize(sireumDir, f) + ".checksum").exists
    else
      f.exists

  def updateScript {
    out.println("Updating Sireum script(s) in " + sireumDir.getAbsolutePath)
    out.flush

    var replacedCount = 0
    var deleteCount = 0
    var errorCount = 0

    val checksums = getChecksums

      def update(script : String) {
        val file = new File(sireumDir, script)
        if (file.exists)
          updateFile(checksums(script), script, file, None) match {
            case Replaced =>
              replacedCount += 1
            case Deleted =>
              deleteCount += 1
            case Error =>
              errorCount += 1
            case NoUpdate =>
          }
      }

    update("sireum")
    update("sireum.bat")

    printStatus(false, replacedCount, deleteCount, errorCount, 0, Seq())
  }

  def update(newFeatures : ArrayBuffer[String], isApp : Boolean, installedFiles : Set[String] = Set()) {
    val checksums = getChecksums
    val features = getFeatures

    if (installedFiles.isEmpty) {
      out.println("Updating Sireum in " + sireumDir.getAbsolutePath)
      out.flush
    }

    val installedFileCount = installedFiles.size
    var downloadCount = installedFileCount
    var replacedFiles = installedFiles
    var errorCount = 0
    var deleteCount = 0

      def update(filePath : String, f : File, currChecksum : Option[String] = None) {
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
                else if (isApp && !new File(sireumDir, METADATA_DIR + fPath + ".checksum").exists)
                  if (downloadFile(false, fPath, file))
                    downloadCount += 1
              } else if (features.contains(fPath) && !installedFeatures.contains(fPath) &&
                (isApp || !isAppFile(file))) {
                val newFeatures2 = ArrayBuffer[String]()
                val installedFiles =
                  downloadNewFiles(features, installedFeatures.toSet, fPath, newFeatures2)
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
              if (f.getName.endsWith(".checksum")) {
                val currentChecksum = readLine(f)
                var filePath = relativize(metaDir, f)
                filePath = filePath.substring(0, filePath.length - ".checksum".length)
                update(filePath, new File(sireumDir, filePath), Some(currentChecksum))
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
    if (replacedCount == 0 && deleteCount == 0 && errorCount == 0 && downloadCount == 0)
      out.println("There was no update.")
    else {
      if (buildDownload)
        downloadBuild
      out.println
      out.println("Finished updating Sireum.")
      if (newFeatures.size > 0) {
        out.println
        out.println("Newly installed feature(s): " + newFeatures.size)
        for (f <- newFeatures)
          out.println("* " + f)
        out.println
      }
      out.println("File download(s): " + downloadCount)
      out.println("File update(s): " + replacedCount)
      out.println("File deletion(s): " + deleteCount)
      out.println("Error(s): " + errorCount)
      out.println
      out.println
    }
    out.flush
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
      out.println("File " + file.getAbsolutePath +
        " will be deleted upon exit.")
      out.flush
      file.deleteOnExit()
      Deleted
    }
  }

  def updateFile(checksum : String, filename : String, file : File,
                 currChecksum : Option[String]) : Mode = {
    try
      checksum match {
        case "0" => deleteFile(file, filename)
        case checksum =>
          val download = if (!file.exists && !isAppFile(file)) true else {
            val currentChecksum =
              if (currChecksum.isDefined) currChecksum.get else getChecksum(file)
            checksum != currentChecksum
          }
          if (download) {
            downloadFile(file.exists, filename, file)
            Replaced
          } else
            NoUpdate
      }
    catch {
      case e =>
        err.println("Failed to update " + file.getAbsolutePath)
        err.println("Reason: " + e.getMessage)
        err.println
        err.flush
        Error
    }
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

  def downloadFile(isUpdate : Boolean, filename : String, file : File) : Boolean = {
    if (!isUpdate && isPlatformSpecific(filename) && isNotForThisPlatform(filename))
      return false
    out.print((if (isUpdate) "Updating" else "Downloading") + " file " + filename)
    out.flush

    val is = new URL(updateUrl + filename).openStream()
    try {
      file.getParentFile.mkdirs
      val os = new BufferedOutputStream(new FileOutputStream(file))
      try {
        val buffer = new Array[Byte](BUFFER_SIZE)
        var n = is.read(buffer)
        StatusPrinter.first
        while (n != -1) {
          os.write(buffer, 0, n)
          n = is.read(buffer)
          StatusPrinter.next
        }
      } finally os.close
    } finally is.close

    StatusPrinter.last
    out.println("... done!")
    out.flush

    if (isAppFile(file))
      installApp(file)

    true
  }

  def isAppFile(file : File) = file.getName.endsWith(SAPP_EXT)

  def relativize(baseFile : File, file : File) =
    file.getAbsolutePath.substring(baseFile.getAbsolutePath.length + 1).replace("\\", "/")

  def isManaged(file : File) =
    file.getParentFile.getAbsolutePath != new File(sireumDir, "apps").getAbsolutePath

  def installApp(file : File) {
    val relPath = relativize(sireumDir, file)
    val managed = isManaged(file)
    val installDir = if (managed) file.getParentFile else unmanagedDir
    val dirs = movePrevApp(file, installDir)
    deleteRemainingAppFiles(relPath)
    if (managed)
      out.print("Installing managed app file: " + file.getName)
    else
      out.print("Installing unmanaged app file: " + file.getName + " to " + installDir.getAbsolutePath)
    out.flush
    try {
      val fileList =
        if (managed) {
          val checksums = getChecksums
          write(new File(sireumDir, METADATA_DIR + relPath + ".checksum"), checksums(relPath))
          Some(new PrintWriter(new FileWriter(new File(sireumDir, METADATA_DIR + relPath + ".filelist"))))
        } else
          None
      try {
        unzip(file, installDir, fileList)
        file.delete
        dirs match {
          case Some((appDir, appBackupDir)) => copyBackupDiff(appDir, appBackupDir)
          case _                            =>
        }
      } finally
        if (fileList.isDefined)
          fileList.get.close
      out.println("... done!")
      out.flush
    } catch {
      case e =>
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
    val fos = new BufferedOutputStream(new FileOutputStream(dest))
    try {
      val fis = new BufferedInputStream(new FileInputStream(src))
      try {
        val buffer = new Array[Byte](BUFFER_SIZE)
        var n = fis.read(buffer)
        while (n != -1) {
          fos.write(buffer, 0, n)
          n = fis.read(buffer)
        }
      } finally fis.close
    } finally fos.close
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

  def movePrevApp(file : File, installDir : File) : Option[(File, File)] = {
    val appName = getAppPath(file.getName)
    val appDir = new File(installDir, appName)
    if (appDir.exists) {
      val appDirBackup = new File(appDir.getParentFile, appDir.getName + "-backup-" + timeStamp)
      if (appDir.renameTo(appDirBackup)) {
        out.println("Moved " + appDir.getName + " to " + appDirBackup.getName)
        out.flush
        Some((appDir, appDirBackup))
      } else {
        out.flush
        err.println("Unable to move " + appDir.getName + " to " + appDirBackup.getName)
        err.flush
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
          else f.delete
        }
        StatusPrinter.next
      }
    if (onExit) {
      file.deleteOnExit
      true
    } else file.delete
  }

  def deleteRec(dir : File, msg : String, onExit : Boolean) : Boolean = {
    out.print(msg)
    out.flush
    StatusPrinter.first
    val status = delete(dir, onExit)
    StatusPrinter.last
    if (status) {
      out.println("... done!")
      out.flush
      true
    } else {
      err.println("... failed!")
      err.flush
      false
    }
  }

  def deleteAppsBackups(dir : File) {
    for (f <- dir.listFiles)
      if ((f.isDirectory && f.getName.indexOf("-backup-") >= 0) || isAppFile(f))
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
      case e =>
        err.println("Could not connect to update site.")
        err.println
        err.flush
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
      case e =>
        err.println("Could not connect to Sireum update site.")
        err.println
        err.flush
        sys.exit
    }
  }

  def loadInstalledFeatures = {
    val installedFeaturesFile = new File(sireumDir, METADATA_DIR + "installed-features.txt")
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
    val installedFeaturesFile = new File(sireumDir, METADATA_DIR + "installed-features.txt")
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

    StatusPrinter.first
    val zipFile = new ZipFile(file)
    val dirLastModMap = scala.collection.mutable.Map[String, Long]()
    try {
      for (e <- zipFile.entries) {
        if (pw.isDefined)
          pw.get.println(e.getName)
        unzipEntry(zipFile, e, outputDir, dirLastModMap)
      }
    } finally zipFile.close

    var i = 0
    val nextCount = 1000
      def next = {
        if (i == nextCount - 1)
          StatusPrinter.next
        i = (i + 1) % nextCount
      }
    for (
      path <- dirLastModMap.keys.toSeq.sortWith({
        (s1, s2) =>
          next
          s1.compareTo(s2) > 0
      })
    ) {
      new File(path).setLastModified(dirLastModMap(path))
      next
    }
    StatusPrinter.last
  }

  def unzipEntry(zipFile : ZipFile, entry : ZipEntry, outputDir : File,
                 dirLastModMap : scala.collection.mutable.Map[String, Long]) {
    if ({
      val n = entry.getName
      n.indexOf("__MACOSX") < 0 && n.indexOf(".DS_Store") < 0
    })
      if (entry.isDirectory) {
        val dir = new File(outputDir, entry.getName)
        dir.mkdirs
        val time = entry.getTime
        if (time != 0) {
          dirLastModMap(dir.getAbsolutePath) = time
        }
      } else {

        val outputFile = new File(outputDir, entry.getName)
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
      StatusPrinter.next
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

    def run(waitTime : Long, args : Seq[String], input : Option[String], dir : Option[File] = None) : Result = {
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

    private def singleReader(caller : Actor, waitTime : Long, dir : Option[File]) = actor {
      (if (waitTime < 0)
        react _
      else
        reactWithin(waitTime) _) {
        case TIMEOUT =>
          caller ! Some(TIMEOUT)
        case (args : Seq[_], in : Option[_]) =>
          import java.io._
          val processBuilder = new ProcessBuilder(args.asInstanceOf[Seq[String]] : _*)
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
            val br = new BufferedReader(new InputStreamReader(proc.getInputStream))
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

  def guessFeatureNames(featureNames : Seq[String], features : Seq[String]) : Seq[String] = {
      def simpl(s : String) =
        removeSappExt(s).toLowerCase

    var sFeatures = Map[String, String]()
    for (f <- features)
      sFeatures += simpl(f) -> f

    val result = new Array[String](featureNames.length)

    var featureNotFound = false
    for (i <- 0 until featureNames.length) {
      val fName = featureNames(i)
      val sfName = simpl(fName)
      if (sFeatures.contains(sfName)) {
        result(i) = sFeatures(sfName)
      } else {
        var minSfs = List((GUESS_DISTANCE_MAX + 1, ""))
        for ((sf, f) <- sFeatures) {
          val min = minSfs.head._1
          val d = Levenshtein.distance(sfName, sf, min)
          if (d == min && d < sf.length) {
            minSfs = (d, f) :: minSfs
          } else if (d < min && d < sf.length) {
            minSfs = List((d, f))
          }
        }
        if (minSfs.length == 1 && minSfs.head._2 != "") {
          result(i) = minSfs.head._2
        } else {
          featureNotFound = true
          err.println("Invalid feature: " + fName + "!")
          err.flush
          if (minSfs.length > 1 && minSfs.forall(_._1 <= GUESS_DISTANCE_MAX)) {
            out.println("Did you mean one of the following features?")
            for (minSf <- minSfs) {
              out.println("  " + removeSappExt(minSf._2))
            }
            out.flush
          }
          sys.exit(-1)
        }
      }
    }

    result
  }

  /*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
  object Levenshtein {
    /* This is based on code from the Apache Commons lang project. */
    def distance(s : CharSequence, t : CharSequence, max : Int = Int.MaxValue) = {
      import scala.annotation.tailrec
        def impl(s : CharSequence, t : CharSequence, n : Int, m : Int) = {
          val p = new Array[Int](n + 1)
          val d = new Array[Int](n + 1)

            @tailrec def fillP(i : Int) {
              p(i) = i
              if (i < n) fillP(i + 1)
            }
          fillP(0)

            @tailrec def eachJ(j : Int, t_j : Char, d : Array[Int], p : Array[Int]) : Int = {
              d(0) = j
                @tailrec def eachI(i : Int) {
                  val a = d(i - 1) + 1
                  val b = p(i) + 1
                  d(i) = if (a < b) a else {
                    val c = if (s.charAt(i - 1) == t_j) p(i - 1) else p(i - 1) + 1
                    if (b < c) b else c
                  }
                  if (i < n)
                    eachI(i + 1)
                }
              eachI(1)

              if (j < m)
                eachJ(j + 1, t.charAt(j), p, d)
              else
                d(n)
            }
          eachJ(1, t.charAt(0), d, p)
        }

      val n = s.length
      val m = t.length
      if (n == 0) m else if (m == 0) n else {
        if (n > m) impl(t, s, m, n) else impl(s, t, n, m)
      }
    }
  }
}
