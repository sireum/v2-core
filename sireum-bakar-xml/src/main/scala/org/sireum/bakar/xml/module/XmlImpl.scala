package org.sireum.bakar.xml.module

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URI
import scala.Some.apply
import org.sireum.bakar.xml.CompilationUnit
import org.sireum.pipeline.PipelineJob
import org.sireum.pipeline.PipelineJobModuleInfo
import org.sireum.util.Exec
import org.sireum.util.FileUtil
import org.sireum.util.ilist
import org.sireum.util.mlistEmpty
import org.sireum.util.mmapEmpty
import org.sireum.util.msetEmpty
import com.thoughtworks.xstream.XStream
import javax.xml.bind.JAXBContext
import org.sireum.util.FileResourceUri

object Util {
  val gnat2xml_key = "gnat2xml"
  def base = {
    val loc = scala.util.Properties.envOrElse(gnat2xml_key, "")
    val f = new File(loc)
    if (!f.exists() || !f.isDirectory() || !(new File(f, "gnat2xml").exists())) {
      println("""val of environmental variable '%s' does not appear to be valid. 
It should point to the location of the gnat2xml bin, instead it's 
value is: %s""".format(gnat2xml_key, loc))
    }
    f
  }
  val gnatmake = new File(base, "gnatmake")
  val gnat2xml = new File(base, "gnat2xml")

  def copy(srcUri : FileResourceUri, destUri : FileResourceUri) {
      def copyFile(f : File) {
        try {
          val fin = new FileInputStream(f)
          val dest = new File(new File(new URI(destUri)), f.getName())
          val fout = new FileOutputStream(dest)
          val buffer = new Array[Byte](1024)
          var bytesRead = fin.read(buffer)
          while (bytesRead > 0) {
            fout.write(buffer, 0, bytesRead)
            bytesRead = fin.read(buffer)
          }
          fin.close
          fout.close
        } catch {
          case e : Exception =>
            e.printStackTrace()
        }
      }

    val src = new File(new URI(srcUri))
    val dest = new File(new URI(destUri))

    if (src.exists()) {
      if (src.isDirectory()) {
        src.listFiles().foreach { f =>
          if (f.isFile()) {
            copyFile(f)
          }
        }
      } else {
        copyFile(src)
      }
    }
  }

  def cleanDir(dirUri : FileResourceUri) {
    val dir = new File(new URI(dirUri))

    if (dir.exists)
      dir.listFiles.foreach { f =>
        if (f.isDirectory()) {
          cleanDir(f.getAbsoluteFile.toURI.toASCIIString)
        }
        f.delete()
      }
  }
}

class Gnat2XMLWrapperDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends Gnat2XMLWrapperModule {
  val waittime = 200000
  val results = mlistEmpty[FileResourceUri]

  val baseDestDir = if (this.destDir.isDefined) new File(new URI(this.destDir.get)) else new File(".")
  baseDestDir.mkdir()

  val tempDir = new File(baseDestDir, "0/temp")
  tempDir.mkdirs();
  val tempUri = FileUtil.toUri(tempDir)

  val seen = msetEmpty[String]

  this.srcFiles.foreach(s => {
    if (s.endsWith("ads") || s.endsWith("adb")) {
      val adaFile = new File(new URI(s));

      val parentUri = FileUtil.toUri(adaFile.getParentFile())
      if (!seen.contains(parentUri)) {
        seen += parentUri

        Util.cleanDir(tempUri)
        this.srcFiles.foreach(f => Util.copy(f, tempUri))

        val args = ilist("/bin/bash", "-c",
          Util.gnatmake.getAbsolutePath() + " -gnat2012 -gnatct *.ads *.adb")
        val result = new Exec().run(waittime, args, None, Some(tempDir))
        println(result)

        val cmd = String.format("%s -v -t -m%s *.ads *.adb",
          Util.gnat2xml.getAbsolutePath(),
          baseDestDir.getAbsolutePath())
        val gnat2xmlResult = new Exec().run(waittime, ilist("/bin/bash", "-c", cmd),
          None, Some(tempDir))

        gnat2xmlResult match {
          case Exec.StringResult(str, exitval) =>
            str.split("\n").foreach { l =>
              if (l.startsWith("Creating")) {
                results += FileUtil.toUri(new File(l.substring("Creating ".length())))
              }
            }
          case _ =>
            println(gnat2xmlResult)
        }
      }
    }
  })

  this.gnat2xmlResults_=(results)
}

class ParseGnat2XMLDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends ParseGnat2XMLModule {
  val results = mmapEmpty[FileResourceUri, String]

  import javax.xml.bind._
  import org.sireum.bakar.xml._;

  val jc = JAXBContext.newInstance("org.sireum.bakar.xml");
  val u = jc.createUnmarshaller();
  val xs = new XStream();

  this.gnat2xmlResults.foreach { uri =>
    val f = new File(new URI(uri))
    val o = u.unmarshal(new FileInputStream(f)).asInstanceOf[JAXBElement[CompilationUnit]]
    results(uri) = xs.toXML(o.getValue())
  }

  this.parseGnat2XMLresults_=(results)
}
