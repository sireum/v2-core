/*
Copyright (c) 2011-2013 Robby, Kansas State University.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
*/

package org.sireum.tools.sapp

import java.io._
import java.nio.file._
import java.nio.file.attribute._
import java.util._
import java.util.zip._
import scala.collection.mutable._
import org.sireum.option._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Sapper {
  def run(option : SapperMode) {
    new Sapper().execute(option)
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class Sapper {
  final val SAPP_EXT = ".sapp"
  final val SAPP_LINK_EXT = ".sapp_link"
  final val SAPP_INFO = ".sapp_info"
  val buffer = new Array[Byte](1024)
  val OS_STRING = {
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

  def errPrintln(s : String) {
    scala.Console.err.println(s)
    scala.Console.err.flush
  }

  def execute(option : SapperMode) {
    if (option.isExtract) extract(option)
    else sapp(option)
  }

  def sapp(option : SapperMode) {
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
      val bytes = Files.readSymbolicLink(fPath).toFile.getPath.toString.getBytes
      val ze = new ZipEntry(new File(f.getParentFile, f.getName + ".sapp_link").getPath)
      ze.setTime(f.lastModified)
      ze.setSize(bytes.length)
      zos.putNextEntry(ze)
      zos.write(bytes)
      zos.closeEntry
    } else {
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
          val buffer = this.buffer
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

  def extract(option : SapperMode) {
    unzip(new File(option.sappFile), new File("."))
  }

  def unzip(file : File, outputDir : File) {
    import scala.collection.JavaConversions._
    import scala.collection.mutable._

    val zipFile = new ZipFile(file)
    val dirLastModMap = Map[String, Long]()
    try {
      for (e <- zipFile.entries) {
        unzipEntry(zipFile, e, outputDir, dirLastModMap)
      }

      if (OS_STRING.indexOf("win") < 0) {
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
          }
        }
      }
    } finally zipFile.close

    for (
      path <- dirLastModMap.keys.toSeq.sortWith({
        (s1, s2) =>
          s1.compareTo(s2) > 0
      })
    ) {
      new File(path).setLastModified(dirLastModMap(path))
    }
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
      val outputFile =
        new File(outputDir,
          entryName.substring(0, entryName.length - SAPP_LINK_EXT.length))
      if (outputFile.exists)
        delete(outputFile, false)
      val outputParentFile = outputFile.getParentFile
      outputParentFile.mkdirs
      val bytes = new Array[Byte](entry.getSize.toInt)
      val is = zipFile.getInputStream(entry)
      try {
        val n = is.read(bytes)
        assert(n == bytes.length)
      } finally is.close
      val linkPath = new String(bytes)
      val path = new File(linkPath).toPath
      val outputPath = outputFile.toPath

      try Files.createSymbolicLink(outputPath, path)
      catch { case e : Exception => }
      if (!Files.isSymbolicLink(outputPath))
        OS_STRING match {
          case "linux32" | "linux64" | "mac64" | "mac32" =>
            new Exec().
              run(-1, Seq("ln", "-s", linkPath, outputFile.getName),
                None, Some(outputParentFile)) match {
                  case Exec.StringResult(_, exitCode) if exitCode == 0 =>
                  case _ =>
                    errPrintln("Could not create symbolic link: " +
                      outputFile.getAbsolutePath + " to " + path)
                }
          case _ =>
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
    val buf = buffer
    var len = is.read(buf)
    while (len > 0) {
      os.write(buf, 0, len)
      len = is.read(buf)
    }
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
}
