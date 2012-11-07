package org.sireum.tools.sapp

import org.sireum.option._
import java.util.zip._
import java.io._
import scala.collection.mutable._
import java.nio.file.Files
import java.util.Properties

object Sapper {
  def run(option : SapperMode) {
    new Sapper().execute(option)
  }
}

class Sapper {
  val buffer = new Array[Byte](1000)

  def execute(option : SapperMode) {
    val fos = new FileOutputStream(option.sappFile)
    val zos = new ZipOutputStream(fos)
    val filePermProp = new Properties
    try {
      for (f <- option.files)
        sapp(zos, new File(f), filePermProp)

      val ze = new ZipEntry(".sapp_info")
      zos.putNextEntry(ze)
      filePermProp.store(zos, null)
      zos.closeEntry
    } finally zos.close
  }

  def sapp(zos : ZipOutputStream, f : File, filePermProp : Properties) {
    import scala.collection.JavaConversions._
    val fPath = f.toPath
    if (Files.isSymbolicLink(fPath)) {
      System.err.println("Sapper does not support symbolic link: " + fPath)
      System.err.flush
      return
    }

    var mask = 0
    for (p <- Files.getPosixFilePermissions(fPath)) {
      mask = mask | (1 << p.ordinal)
    }
    filePermProp.put(fPath.toString, mask.toString)

    if (f.isDirectory) {
      val p = f.getPath
      val path = if (p.endsWith("/")) p else p + "/"
      val ze = new ZipEntry(path)
      ze.setTime(f.lastModified)
      zos.putNextEntry(ze)
      zos.closeEntry
      for (f2 <- f.listFiles) {
        sapp(zos, f2, filePermProp)
      }
    } else {
      val fis = new FileInputStream(f)
      try {
        val path = f.getPath
        val ze = new ZipEntry(path)
        ze.setTime(f.lastModified)
        zos.putNextEntry(ze)
        try {
          var n = fis.read(buffer)
          while (n >= 0) {
            zos.write(buffer, 0, n)
            n = fis.read(buffer)
          }
        } finally zos.closeEntry
      } finally fis.close
    }
  }
}