/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.tools.sapp

import org.sireum.option._
import java.util.zip._
import java.io._
import scala.collection.mutable._
import java.nio.file.Files
import java.util.Properties

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Sapper {
  def run(option : SapperMode) {
    new Sapper().execute(option)
  }

  def main(args : Array[String]) {
    Sapper.run(SapperMode("workspace.sapp", Array("Workspaces/SireumV2")))
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class Sapper {
  val buffer = new Array[Byte](1000)

  def execute(option : SapperMode) {
    val fos = new FileOutputStream(option.sappFile)
    val zos = new ZipOutputStream(fos)
    val filePermProp = new Properties
    try {
      for (f <- option.files)
        sapp(zos, new File(f), filePermProp)

      val ze = new ZipEntry(".sappperm")
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
}