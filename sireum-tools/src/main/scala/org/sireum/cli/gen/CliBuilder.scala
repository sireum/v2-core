/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.cli.gen

import org.sireum.option._
import org.sireum.cli._
import org.sireum.util._
import com.thoughtworks.xstream.XStream
import java.io.FileWriter
import java.io.PrintWriter
import org.stringtemplate.v4.ST
import org.stringtemplate.v4.STGroupFile
import org.sireum.pipeline._
import java.io.File
import org.apache.commons.lang3.StringUtils

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object CliBuilder {

  def run(cmode : CliGenMode) {
    new CliBuilder().execute(cmode)
  }

  def main(args : Array[String]) {
    import com.thoughtworks.xstream._

    var cgm : CliGenMode = null

    try {
      // TODO: need to escape spaces in xml (or something like that)
      val arg = args.foldLeft("")((b, a) => b + " " + a)

      cgm = new XStream().fromXML(arg).asInstanceOf[CliGenMode]
    } catch {
      case e : Throwable =>
        //e.printStackTrace() 

        cgm = CliGenMode()
        cgm.dir = "../sireum-cli/src/main/scala/"
        cgm.genClassName = "org.sireum.cli.SireumCli"
        cgm.packages = List("org.sireum")
        cgm.className = SireumMode.getClass.getName.replace("$", "")
    }
    CliBuilder.run(cgm)
  }
}

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
object tenum extends Enumeration {
  type tenum = Value
  val ARG, OPTARG, VARARG, DEFAULT = Value
}

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class CliBuilder {
  import org.sireum.cli.gen.tenum._

  type Method = java.lang.reflect.Method

  val SHORT_KEY_PREFIX = "-"
  val LONG_KEY_PREFIX = "--"
  val EMPTY_STRING = "\"\""

  val stg : STGroupFile = new STGroupFile(getClass.getResource("cli.stg"), "UTF-8", '$', '$')
  val topLevel = stg.getInstanceOf("topLevel")
  var stMain : ST = null

  var imports = Set[String]()

  var cgm : CliGenMode = null

  val tags : MBuffer[Tag] = marrayEmpty[Tag]

  def mineClass(c : Class[_], o : AnyRef, path : List[String], groupName : String = null) {
    for (ca <- c.getDeclaredAnnotations) {
      ca match {
        case s : Mode => {
          imports += c.getPackage.getName
          val stMode = stg.getInstanceOf("parseMode")
          stMode.add("className", c.getSimpleName)
          stMode.add("header", s.header.trim)
          stMode.add("name", s.command)

          val q1 = getMainModeInfo(filter(c.getDeclaredMethods))
          val modeDesc = marrayEmpty[(String, String)]
          var longestName = 0
          for ((m, modeName, desc) <- q1) {
            val stcase = stg.getInstanceOf("caseMode")
            stcase.add("modeName", modeName)
            stcase.add("className", m.getReturnType.getSimpleName)
            stMode.add("modeName", modeName)
            stMode.add("caseMode", stcase)

            modeDesc += ((modeName, desc))
            if (modeName.length > longestName) longestName = modeName.length + 1
            mineClass(m.getReturnType, m.invoke(o), path :+ s.command)
          }
          for ((name, desc) <- modeDesc.sortWith({ (md1, md2) => md1._1 <= md2._1 })) {
            val stAvailMode = stg.getInstanceOf("mode")
            stAvailMode.add("name", name)
            if (desc.length > 0) stAvailMode.add("desc",
              format(desc, longestName, name.length))
            stMode.add("mode", stAvailMode)
          }
          topLevel.add("entry", stMode)
        }
        case s : Main => {
          imports += c.getPackage.getName
          assert(stMain == null)

          try {
            val cclass = Class.forName(s.className)
            val oclass = Class.forName(s.className + "$")
            if (!classOf[PipelineRunner].isAssignableFrom(cclass) && !classOf[PipelineRunner].isAssignableFrom(oclass)) {
              tags += OptionUtil.genTag(OptionUtil.ErrorMarker, (s.className + " is not an instance of PipelineRunner"))
            }
          } catch {
            case e => tags += OptionUtil.genTag(OptionUtil.ErrorMarker, (e.getClass.getSimpleName + " " + e.getMessage))
          }

          stMain = stg.getInstanceOf("parseMain")
          stMain.add("className", c.getSimpleName)
          stMain.add("targetName", s.className)
          stMain.add("featureName", s.featureName)
          topLevel.add("entry", stMain)

          val chks = this.collect(c.getDeclaredAnnotations, classOf[Check])
          chks match {
            case Some(chk) =>
              val meths = chk.value.getDeclaredMethods.filter(name => name.getName == "check")
              if (meths.length != 1)
                tags += OptionUtil.genTag(OptionUtil.ErrorMarker, "Expecting a single check method for %s but found %d".format(c.getName, meths.length))
              else
                stMain.add("optCheck", this.genCheckMethodCall(meths.head))
            case None =>
          }

          val stuse = stg.getInstanceOf("usage")
          path.foreach { s => stuse.add("name", s) }
          stuse.add("name", s.value)
          stMain.add("header", stuse)

          val md = filter(c.getDeclaredMethods, true)

          val args = collect(md, classOf[Arg]).sortBy(f => f._2.index)
          val optArgs = collect(md, classOf[OptionalArg]).sortBy(f => f._2.index)
          val varArgs = collect(md, classOf[Args])

          if (!(args.isEmpty && varArgs.isEmpty && optArgs.isEmpty)) {
            stMain.add("nonempty", true)
            validate(c, args, optArgs, varArgs)
          }

          stMain.add("nummeth", args.length)
          var i = 0
          for ((meth, arg, optCheck) <- args) {
            stuse.add("arg", arg.value)
            stMain.add("caseArg",
              handleCase(i.toString, "", "args(j)", (meth, o), optCheck, null, meth.getName, tenum.ARG))
            i += 1
          }

          for ((meth, optArg, optCheck) <- optArgs) {
            stuse.add("optArg", optArg.value)
            stMain.add("caseArg",
              handleCase(i.toString(), "", "args(j)", (meth, o), optCheck, null, meth.getName, tenum.OPTARG))
            i += 1
          }

          if (!varArgs.isEmpty) {
            stMain.add("hasVarg", true)
            val (meth, args, optCheck) = varArgs.head
            stuse.add("arg", args.value)
            stMain.add("caseArg",
              handleCase(i.toString, "", "args(j)", (meth, o), optCheck, null, meth.getName, tenum.VARARG))
            i += 1
          }

          val options = collect(md, classOf[Option])
          val (l1, l2) = computeMaxLen(options)
          val optblock = marrayEmpty[String]
          for ((mo, opt, optCheck) <- options) {
            val (st1, st2) = handleOption(o, mo, opt, l1, l2, optCheck)
            optblock += st1.render
            stMain.add("caseOpt", st2)
          }
          val sortedopts = optblock.sortWith({ (a, b) =>
              def isLongKey(s : String) = s.startsWith(LONG_KEY_PREFIX)
              def isShortKey(s : String) = s.startsWith(SHORT_KEY_PREFIX) && !s.startsWith(LONG_KEY_PREFIX)

            if (isLongKey(a) && isShortKey(b))
              false
            else if (isShortKey(a) && isLongKey(b))
              true
            else a.compareTo(b) <= 0
          })
          val optblockst = stg.getInstanceOf("optblock")
          optblockst.add("opt", "-h | --help")
          sortedopts.foreach(so => optblockst.add("opt", so))
          stMain.add("header", optblockst)

          for (m <- filter(c.getDeclaredMethods)) {
            mineClass(m.getReturnType, m.invoke(o), path :+ s.value, m.getName)
          }
          stMain = null
        }
        case s : Group => {
          imports += c.getPackage.getName
          val stgroup = stg.getInstanceOf("groupy")
          stgroup.add("groupName", s.value)
          stMain.add("header", stgroup)

          val opts = collect(filter(c.getDeclaredMethods, true), classOf[Option])
          val (l1, l2) = computeMaxLen(opts)
          val optblock = marrayEmpty[String]
          for ((mo, opt, optCheck) <- opts) {
            val (st1, st2) = handleOption(o, mo, opt, l1, l2, optCheck, groupName)
            optblock += st1.render
            stMain.add("caseOpt", st2)
          }
          val sortedopts = optblock.sortWith((a, b) => a.compareTo(b) <= 0)
          sortedopts.foreach(so => stgroup.add("option", so))
        }
        case _ => {

        }
      }
    }
  }

  /**
   * Performs sanity checks for program arguments
   * assumes args and optArgs are sorted based on index value
   */
  def validate(c : Class[_], args : List[(Method, Arg, _)], optArgs : List[(Method, OptionalArg, _)],
               varArg : List[(Method, Args, _)]) {

    if (args.isEmpty && varArg.isEmpty) {
      tags += OptionUtil.genTag(OptionUtil.ErrorMarker, ("No arguments provided for %s".format(c.getName)))
    } else if (!optArgs.isEmpty) {
      if (!varArg.isEmpty) {
        tags += OptionUtil.genTag(OptionUtil.ErrorMarker,
          ("Annotations @OptionalArg and @Args cannot appear in the same class: %s".format(c.getName)))
      }

      if (args(args.length - 1)._2.index >= optArgs.head._2.index) {
        tags += OptionUtil.genTag(OptionUtil.ErrorMarker,
          ("Optional arguments must occur after required arguments: %s".format(c.getName)))
      }
    }

    if (varArg.length > 1) {
      tags += OptionUtil.genTag(OptionUtil.ErrorMarker,
        ("There can be at most one method annotated with @Args: %s".format(c.getName)))
    }

    for (i <- 1 until args.length) {
      if (args(i - 1)._2.index == args(i)._2.index)
        tags += OptionUtil.genTag(OptionUtil.ErrorMarker, ("Same index value used for arguments: %s, %s".format(args(i - 1)._1.getName, args(i)._1.getName)))
    }

    for (i <- 1 until optArgs.length) {
      if (optArgs(i - 1)._2.index == optArgs(i)._2.index)
        tags += OptionUtil.genTag(OptionUtil.ErrorMarker, ("Same index value used for optional arguments: %s, %s".format(optArgs(i - 1)._1.getName, optArgs(i)._1.getName)))
    }
  }

  def computeMaxLen(l : List[(_, Option, _)]) : (Int, Int) = {
    var slen, llen = 0
    for ((_, o, _) <- l) {
      if (o.shortKey.length > slen) slen = o.shortKey.length
      if (o.longKey.length > llen) llen = o.longKey.length
    }
    (slen, llen)
  }

  def handleOption(o : AnyRef, mo : Method,
                   ot : Option, maxShort : Int, maxLong : Int,
                   check : scala.Option[Check], groupName : String = null) : (ST, ST) = {

    val max = maxShort + (if (maxShort > 0) SHORT_KEY_PREFIX.length else 0) +
      (if (maxLong > 0 && maxShort > 0) " | ".length else 0) +
      maxLong + (if (maxLong > 0) LONG_KEY_PREFIX.length else 0)

    var keys = ""

    val shortKey = if (!ot.shortKey.isEmpty) Some(SHORT_KEY_PREFIX + ot.shortKey) else None
    val longKey = if (!ot.longKey.isEmpty) Some(LONG_KEY_PREFIX + ot.longKey) else None

    val matchings = stg.getInstanceOf("matchings")
    val optioncheckst = stg.getInstanceOf("optionsetCheck")

    var colsadded = 0
    if (shortKey.isDefined) {
      keys += shortKey.get + " " * (maxShort - ot.shortKey.length)
      stMain.add("key", shortKey.get)
      matchings.add("g", shortKey.get)
      optioncheckst.add("shortkey", shortKey.get)
    }

    if (longKey.isDefined) {
      keys += (if (!keys.isEmpty) " | " else "") + longKey.get
      stMain.add("key", longKey.get)
      matchings.add("g", longKey.get)
      optioncheckst.add("longkey", longKey.get)
    }

    val stoption_desc = stg.getInstanceOf("option_desc")
    stoption_desc.add("desc", ot.desc)

    if (!ot.isRaw) {
      val defval = mo.invoke(o)
      val (_, _, simpleName, ochoices) = prettify(mo, defval)

      if (defval.isInstanceOf[Seq[_]])
        stoption_desc.add("opt", stg.getInstanceOf("separator").add("sep", ot.separator))

      if (!defval.isInstanceOf[Boolean])
        stoption_desc.add("opt", stg.getInstanceOf("defaultval").add("val", simpleName))

      if (ochoices.isDefined) {
        val stchoices = stg.getInstanceOf("choices")
        ochoices.get.foreach(c => stchoices.add("c", c))
        stoption_desc.add("opt", stchoices)
      }
    } else {
      stoption_desc.add("desc", " (accepts all following string arguments)")
    }

    val stoption = stg.getInstanceOf("combine")
    stoption.add("f", keys).add("s", format(stoption_desc.render, max, keys.length))

    val value = if (mo.getReturnType() == classOf[scala.Boolean]) "\"true\""
    else "args(j + 1)"

    val stcase =
      if (ot.isRaw)
        handleRawCase(matchings.render, optioncheckst.render, groupName, mo.getName)
      else
        handleCase(matchings.render, optioncheckst.render, value,
          (mo, o), check, groupName, mo.getName, tenum.DEFAULT)

    (stoption, stcase)
  }

  def handleRawCase(matching : String, optcheck : String,
                    groupName : String, fieldName : String) : ST = {
    val result = stg.getInstanceOf("caseRawProcess")
    result.add("matching", matching)
    result.add("optioncheck", optcheck)
    if (groupName != null)
      result.add("gname", groupName)
    result.add("fname", fieldName)
  }

  def format(d : String, max : Int, curCol : Int) : String = {

      def sub(e : String, len : Int) : (String, String) = {
        if (len < e.length) {
          val pos = e.lastIndexOf(" ", len)
          (e.substring(0, pos), e.substring(pos, e.length))
        } else {
          (e, "")
        }
      }

    val minToUse = Math.min(max, this.cgm.minCol)

    val sb = new StringBuilder
    if (curCol > minToUse)
      sb.append("\n" + (" " * (minToUse + 1)))
    else
      sb.append(" " * (minToUse - curCol))

    var rem = d
    while (!rem.isEmpty) {
      var (s, r) = sub(rem, this.cgm.maxCol - minToUse)
      sb.append(s)
      rem = r
      if (!r.isEmpty)
        sb.append("\n" + (" " * (minToUse)))
    }

    sb.toString.replaceAll("~", " ")
  }

  def handleCase(matching : String, optcheck : String, value : String,
                 m : (Method, AnyRef), checkMethod : scala.Option[Check],
                 groupName : String, fieldName : String,
                 t : tenum) : ST = {
    val stCase = stg.getInstanceOf("caseProcess")
    stCase.add("matching", if (t == tenum.VARARG) "_" else matching)
    stCase.add("value", value)
    if (groupName != null) stCase.add("gname", groupName)
    stCase.add("fname", fieldName)
    if (t != tenum.DEFAULT)
      stCase.add("isArg", true)

    val (typen, pvalue, _, _) = prettify(m._1, m._1.invoke(m._2))
    stCase.add("optioncheck", optcheck)

    if (t == tenum.VARARG) {
      assert(typen.startsWith("Array"))

      stCase.add("isVarg", true)
      val baset = typen.stripPrefix("Array[").stripSuffix("]")
      var pval = ""
      baset match {
        case "Double"                 => "0D"
        case "Byte" | "Int" | "Short" => "0"
        case "Char"                   => "'c'"
        case "Float"                  => "0F"
        case "Long"                   => "0L"
        case "Boolean"                => "false"
        case "java.lang.String"       => ""
      }
      stCase.add("instance", "\"" + pval + "\"")
      stCase.add("type", baset)
    } else {
      stCase.add("instance", pvalue)
      stCase.add("type", typen)
    }

    checkMethod match {
      case Some(c) =>
        val meths = c.value.getDeclaredMethods.filter { s => s.getName == m._1.getName + "Check" }

        if (meths.size != 1) {
          tags += OptionUtil.genTag(OptionUtil.ErrorMarker, "Expecting a single check method for %s but found %d".format(m._1.getName, meths.length))
          return stCase
        } else {
          val params = meths.head.getParameterTypes
          if (params.size != 2 || params(0) != m._1.getDeclaringClass ||
            classOf[MBuffer[Tag]] != params(1)) {
            tags += OptionUtil.genTag(OptionUtil.ErrorMarker, "Invalid check method found for %s".format(m._1.getName))
            return stCase
          }
          return stCase.add("optCheck", genCheckMethodCall(meths.head))
        }
      case _ => stCase
    }
  }

  /**
   * (castTypeName, fullyQualifiedName, simpleName, choices)
   */
  def prettify(m : Method, v : AnyRef) : (String, AnyRef, AnyRef, scala.Option[Seq[String]]) = {
    v match {
      case s : String =>
        val r = "\"" + s + "\""
        (v.getClass().getName(), r, r, None)
      case s : org.sireum.util.Enum#EnumElem =>
        val t = s.elements.head
        val fqName = s.enum.getClass.getName.dropRight(1) + "." + t.toString.replace("$", "")
        val castType = t.getClass().getSuperclass().getName().replace("$", ".")
        val choices = s.elements.foldLeft(Seq[String]())((b, a) => b.+:(a.toString().replace("$", "")))
        (castType, fqName, s.toString.replace("$", ""), Some(choices))
      case s : Array[_] =>
        val tt = m.getReturnType.toString.stripPrefix("class ")
        var t = "Array["
        tt match {
          case "[B"                    => t += "Byte"
          case "[C"                    => t += "Char"
          case "[D"                    => t += "Double"
          case "[F"                    => t += "Float"
          case "[I"                    => t += "Int"
          case "[J"                    => t += "Long"
          case "[S"                    => t += "Short"
          case "[Z"                    => t += "Boolean"
          case s if s.startsWith("[L") => t += s.stripPrefix("[L").stripSuffix(";")
          case _                       =>
        }
        val array = stg.getInstanceOf("array")
        val z = v.asInstanceOf[Array[_]]
        z.foreach(elem => array.add("elem", elem))
        (t + "]", array.render, array.render, None)
      case s : Seq[_] =>
        val pt = m.getGenericReturnType.asInstanceOf[java.lang.reflect.ParameterizedType]
        val castType = pt.toString.replaceAll("<", "[").replaceAll(">", "]")
        val genericType = pt.getActualTypeArguments.head.asInstanceOf[Class[_]]
        val fqn = v.getClass.getName.replace("Nil$", "List[" + genericType.getName + "]") + "()"
        val simpleName = "\"" + v.asInstanceOf[Seq[_]].mkString(",") + "\""

        /*
        println("castType: " + castType)
        println("fqn: " + fqn)
        println("simpleName: " + simpleName)
        println("genericType: " + genericType)
        
        (castType, castType + "()", simpleName, None)
        */

        // TODO: due to type erasure issues we'll only allow lists of strings.
        // Should update this one the new version of scala w/ scala reflection
        // is released
        if (genericType != classOf[java.lang.String]) {
          throw new RuntimeException("Only supporting lists of strings at this time: " + genericType.getName)
        }
        ("ISeq[String]", "ivectorEmpty[String]", simpleName, None)
      case s : scala.Option[_] =>
        throw new RuntimeException("Not handling Option type yet")
      case _ =>
        (v.getClass().getName(), v, v, None)
    }
  }

  def getMainModeInfo(meths : Array[Method]) : List[(Method, String, String)] = {
    var l = List[(Method, String, String)]()
    for (m <- meths) {
      val c = m.getReturnType
      for (a <- c.getDeclaredAnnotations) {
        a match {
          case s : Mode =>
            l :+= (m, s.command, s.desc)
          case s : Main =>
            l :+= (m, s.value, s.desc)
          case s : Check                        => // ignore
          case s : scala.reflect.ScalaSignature => // ignore  
          case s =>
            throw new RuntimeException("Unexpected: " + s.annotationType)
        }
      }
    }
    l
  }

  def genCheckMethodCall(m : Method) : ST = {
    val methCall = stg.getInstanceOf("methCall")
    methCall.add("exp", "new " + m.getDeclaringClass.getName + "()." + m.getName)
    methCall.add("arg", "opt")
    methCall.add("arg", "result.tags")
  }

  def filter(meths : Array[Method], includePrim : Boolean = false) : Array[Method] = {
    val result = meths.filter(f => !f.getName.startsWith("copy") && !f.getName.startsWith("_") &&
      !f.getName.contains("$") &&
      (includePrim ||
        this.cgm.packages.exists(p => f.getReturnType.getName.startsWith(p))))

    result
  }

  def collect[T](ants : Array[java.lang.annotation.Annotation], c : Class[T]) : scala.Option[T] = {
    for (a <- ants) {
      if (a.annotationType.getName == c.getName) {
        return Some(a.asInstanceOf[T])
      }
    }
    None
  }

  def collect[T](m : Method, c : Class[T]) : scala.Option[T] = {
    collect(m.getDeclaredAnnotations, c)
  }

  def collect[T](ma : Array[Method], c : Class[T]) : List[(Method, T, scala.Option[Check])] = {
    var l = List[(Method, T, scala.Option[Check])]()
    for (m <- ma) {
      collect(m, c) match {
        case Some(annot) =>
          collect(m, classOf[Check]) match {
            case Some(checkAnnot) =>
              l :+= (m, annot, Some(checkAnnot))
            case None =>
              l :+= (m, annot, None)
          }
        case None =>
      }
    }
    l
  }

  def execute(cmode : CliGenMode) {
    this.cgm = cmode

    val c = Class.forName(cgm.className)
    val const = c.getConstructors().head
    val params = new Array[AnyRef](const.getParameterTypes().length)

    for (i <- 0 until const.getParameterTypes().length) {
      // static methods created by scala to access the default values
      var m = c.getMethod("$lessinit$greater$default$" + (i + 1))
      params(i) = m.invoke(c)
    }
    val inst = const.newInstance(params : _*).asInstanceOf[AnyRef]
    mineClass(c, inst, List[String]())

    for (c <- imports) {
      topLevel.add("imports", stg.getInstanceOf("imports").add("p", c))
    }

    // get annotation attached to c
    val top = c.getDeclaredAnnotations().collect { case c : Mode => c }
    assert(top.length == 1)

    var rootDir = cgm.dir
    var objectName = cgm.genClassName
    if (objectName.isEmpty) {
      objectName = top.head.command.head.toUpper + top.head.command.tail + "Cli"
    }

    val fq = objectName.split("\\.")
    if (fq.length > 1) {
      objectName = fq(fq.length - 1)
      val fqo = fq.dropRight(1).asInstanceOf[Array[Object]]
      topLevel.add("packageName", StringUtils.join(fqo, '.'))
      rootDir += StringUtils.join(fqo, '/')
    }

    topLevel.add("className", objectName)
    topLevel.add("topMethod", c.getSimpleName())

    val root = new File(rootDir)
    if (!root.exists && !root.mkdirs) {
      tags += OptionUtil.genTag(OptionUtil.ErrorMarker, "Invalid directory %s".format(root.getAbsolutePath))
      return
    }

    val fname = new File(root, objectName + ".scala")

    try {
      val out = new PrintWriter(new FileWriter(fname))
      out.write(topLevel.render())
      out.close()
      println("Succesfully wrote: " + fname.getAbsolutePath)
    } catch {
      case s => println(s)
    }

    for (t @ InfoTag(mt, Some(desc)) <- tags) {
      mt match {
        case OptionUtil.ErrorMarker =>
          Console.err.println(desc)
          Console.err.flush
        case _ =>
          Console.out.println(desc)
          Console.out.flush
      }
    }
  }
}
