package org.sireum.jvm.translator

import org.sireum.option.JVMMode
import java.io.File
import com.google.common.reflect.ClassPath
import scala.collection.JavaConversions._

object Translator {
  def run(option: JVMMode) {
    new Translator().execute(option)
  }
}

class Translator {
  def execute(option: JVMMode) {
    val jvmMode = new JVMMode()
    val classes = option.classes

    val dir = if (option.dir == jvmMode.dir) ""
    else if (option.dir.endsWith(File.separator)) option.dir
    else option.dir + File.separator

    val dirFile = new java.io.File(dir)
    if (!dirFile.exists) dirFile.mkdirs()

    classes.foreach(clazz => {
      val (output, fileName) =
        if (clazz.endsWith(".class"))
          (ClassTranslator.translate(Right(clazz)), clazz.substring(clazz.lastIndexOf(File.separatorChar) + 1, clazz.lastIndexOf(".")))
        else if (isClass(clazz)) {
          (ClassTranslator.translate(Left(clazz)), clazz)
        } else {
          val pckgOutput = new StringBuilder()
          getClassesFromPackage(clazz).foreach(x => {
            pckgOutput ++= ClassTranslator.translate(Left(x.getName()))
          })
          (pckgOutput, clazz)
        }
      val pw = new java.io.PrintWriter(new java.io.File(dir + fileName + ".plr"))
      pw.println(output)
      pw.close
    })

  }

  def isClass(name: String) = {
    try {
      Class.forName(name, false, getClass.getClassLoader())
      true
    } catch {
      case ce: ClassNotFoundException => false
    }
  }

  def getClassesFromPackage(packageName: String) = {
    val cl = getClass.getClassLoader
    val cp = ClassPath.from(cl)
    cp.getTopLevelClassesRecursive(packageName)
  }
}