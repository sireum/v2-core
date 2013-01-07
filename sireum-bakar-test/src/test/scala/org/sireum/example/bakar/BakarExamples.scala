package org.sireum.example.bakar

import java.io.File
import java.io.FilenameFilter
import java.net.URI

import org.sireum.util._

case class Project(testName : String, files : ISeq[FileResourceUri])

trait ProjectProvider {
  def getProjects(dir : File) : ISeq[Project]
}

object BakarExamples {
  val BAKAR_FILE_EXTS = ilist(".ada", ".ads", ".adb", ".cfg")
  val SEQ_PATTERN = ".*(__x.*)$".r
  val IGNORE_SUFFIX = "_IGNORE"

  def sourceDirUri(claz : Class[_], path : String) = {
    FileUtil.fileUri(claz, path).replaceFirst("/bin/", "/src/test/resources/")
  }

  def genExampleName(files : ISeq[FileResourceUri], prefix : String = "") : String = {
    val name = files.head
    val subs = sourceDirUri(BakarExamplesAnchor.getClass, "")
    (if (!prefix.isEmpty) prefix + "_" else "") + name.replace(subs, "").replaceAll("/", "_")
  }

  def getProjects(pp : ProjectProvider, dirUri : FileResourceUri,
                  recursive : Boolean = false) : ISeq[Project] = {
    val ret = mlistEmpty[Project]
    val dir = new File(new URI(dirUri))
    if (dir.exists && dir.isDirectory()) {
      ret ++= pp.getProjects(dir)

      if (recursive) {
        dir.listFiles.filter(f => f.isDirectory()).foreach(d =>
          ret ++= getProjects(pp, FileUtil.toUri(d), recursive)
        )
      }
    }
    ret.toList
  }

  def exampleMap(dirUri : FileResourceUri,
                 prefix : String = "",
                 recursive : Boolean = false,
                 exts : ISeq[String] = BAKAR_FILE_EXTS) : MMap[String, ISeq[FileResourceUri]] = {
    val result = exampleFiles(dirUri, exts, recursive)
    val ret = mmapEmpty[String, ISeq[FileResourceUri]]
    result.foreach(s => {
      ret(genExampleName(s, prefix)) = s
    })
    return ret
  }

  def exampleFiles(dirUri : FileResourceUri,
                   exts : ISeq[String] = BAKAR_FILE_EXTS,
                   recursive : Boolean = false,
                   result : MArray[ISeq[FileResourceUri]] = marrayEmpty[ISeq[FileResourceUri]]) : ISeq[ISeq[FileResourceUri]] = {
    if (dirUri.endsWith(IGNORE_SUFFIX + "/")) {
      return result.toList
    }

    val dir = new File(new URI(dirUri))

    if (dir.exists) {
      val files = dir.listFiles(new FilenameFilter {
        override def accept(dir : File, name : String) = exts.exists(ext => name.endsWith(ext))
      })

      val seen = msetEmpty[File]
      files.foreach(f =>
        if (!seen.contains(f)) {
          val locseen = mlinkedSetEmpty[File]
          val name = f.getName.substring(0, f.getName.lastIndexOf('.'))
          val fs = files.filter(p => p.getName.contains(name)).sortWith((f1, f2) => f1.getName < f2.getName)
          locseen ++= fs

          name match {
            case SEQ_PATTERN(m) =>
              locseen ++= files.filter(p => p.getName.contains(m + ".")).sortWith((f1, f2) => f1.getName < f2.getName)
            case _ =>
          }
          result += locseen.map(a => FileUtil.toUri(a)).toList
          seen ++= locseen
        }
      )
    }

    if (recursive) {
      dir.listFiles.foreach { f =>
        if (f.isDirectory) exampleFiles(FileUtil.toUri(f), exts, recursive, result)
      }
    }
    result.toList
  }

  def UriToPath(sources : ISeq[FileResourceUri]) : Array[String] = {
    val l = marrayEmpty[String]
    sources.foreach(s => l += new File(new URI(s)).getAbsolutePath)
    l.toArray
  }
}