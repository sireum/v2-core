package org.sireum.js.casegen

import org.sireum.option._
import java.lang.ClassLoader
import java.lang.Class
import java.lang.NoSuchMethodException
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.lang.IllegalArgumentException
import java.net.URL
import java.net.URLClassLoader
import org.sireum.util.Reflection._
import org.sireum.util.Reflection
import org.stringtemplate.v4.ST
import org.stringtemplate.v4.STGroupFile
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map
import scala.collection.mutable.Set


object JsConvBuilder {
  val jsConvAnnoName = "org.sireum.option.annotation.JsConv"
  val seqClassName = "scala.collection.Seq"
  
  type ListTest = List[Int]
  
  def run(cmode : JsGenMode) {
    new JsConvBuilder().execute(cmode)
  }
  def main(args : Array[String]) {
    val classpath = System.getProperty("java.class.path")
    val classpathEntries = classpath.split(File.pathSeparator)
    val cp = "/Users/jakeehrlich/Documents/workspace/sireum-bakar-private/sireum-bakar-kiasan-server-plugin/bin"//  +: classpathEntries.toList
    //cp foreach println
    val example = JsGenMode(
        classNames = Vector[String](
            "org.sireum.bakar.kiasan.message.UnitInfoStoreMessage",
            "org.sireum.bakar.kiasan.message.AnalysisProcessRequestMessage"
            ,"org.sireum.bakar.kiasan.message.StatObject"
        ),
        classpath = List(cp).toVector,
        dir = "/Users/jakeehrlich/Documents/workspace/sireum-bakar-private/sireum-bakar-kiasan.js/src/main/scala/org/sireum/js/bakar/kiasan",
        packageName = "org.sireum.js.bakar.kiasan"
    )
    run(example)
  }
}

class JsConvBuilder {
  import scala.reflect.runtime.universe._
  
  private val jsMapName = "jsMap"
  
  private val stg : STGroupFile = new STGroupFile(getClass.getResource("jsconv.stg"), "UTF-8", '$', '$')
  private val topLevel = stg.getInstanceOf("topLevel")
 
  private var names : Map[String, Int] = Map()
  private val implsInitTypes = Set[Type](
    typeOf[Int], typeOf[Double], typeOf[Float], typeOf[Long],
    typeOf[Short], typeOf[Byte], typeOf[Char], typeOf[Boolean],
    typeOf[Unit], typeOf[String]
  )
  private val implsInit = implsInitTypes map (_.toString())
  private val implsInitSym = implsInitTypes map (_.typeSymbol)
  private var impls = implsInit.clone()
  
  private val seqType = typeOf[Seq[_]]
  private val mapType = typeOf[scala.collection.immutable.Map[_, _]]
  private val arrType = typeOf[Array[_]]
  private val vecType = typeOf[Vector[_]]
  
  private var classLoader : URLClassLoader = null
  
  private def isLeavesType(tipe : Type): Boolean = {
    //taken from https://gist.github.com/xeno-by/4985929 and http://stackoverflow.com/questions/27821841/working-with-opaque-types-char-and-long
    val classObject = Class.forName(tipe.typeSymbol.asClass.fullName, true, classLoader)
    try {
      val method = classObject.getMethod(jsMapName)
      return tipe.typeSymbol.isAbstract
    } catch {
      case e : NoSuchMethodException => return false
    }
    false
  }
  
  private def isTupleType(tipe : Type): Boolean = {
    val tupleTypes = List(typeOf[Tuple1[_]], typeOf[Tuple2[_, _]], typeOf[Tuple3[_, _, _]], typeOf[Tuple4[_, _, _, _]])
    //lists don't have forsome method? I used demorgan's law to make one
    !tupleTypes.forall { x => !(tipe.erasure <:< x.erasure) }
  }
  
  private def hasBeenImplemented(tipe : Type): Boolean = {
    implsInitSym(tipe.typeSymbol) || impls(tipe.toString())
  }
  
  private def markAsImplemented(tipe : Type) {
    impls += tipe.toString()
  }
  
  //def getValueName(tipe : Type, name : String) = tipe.member(newTermName(name)).asMethod.returnType
  
  private def getConversionCount(tipe : Type): String = {
    val name = tipe.typeSymbol.name.toString()
    if(names.contains(name)) names(name).toString()
    else ""
  }
  
  private def addConversion(tipe : Type, to : ST, from : ST) {
    val name = tipe.typeSymbol.name.toString()
    names += name -> (names.withDefaultValue(0)(name) + 1)
    topLevel.add("entry", to)
    topLevel.add("entry", from)
  }
  
  private def make(inst: String, args: (String, Any)*): ST = {
     val instance = stg.getInstanceOf(inst)
     args.foreach[ST](x => instance.add(x._1, x._2))
     return instance
  }
 
  private def makeScalaName(tipe : Type): String = {
    if(implsInitSym(tipe.typeSymbol)) {
      if(tipe.typeSymbol == typeOf[Long].typeSymbol) {
        "toScalaLong" //we need this special case to properly handle conversion from javascript numbers to scala longs 
      } else {
        "toScala"
      }
    } else {
      make("toScalaName", "type" -> tipe, "id" -> "").render()
    }
  }
  
  private def makeJsName(tipe : Type): String = {
    if(implsInitSym(tipe.typeSymbol)) {
      "toJs"
    } else {
      make("toJsName", "type" -> tipe, "id" -> "").render()
    }
  }
  
  private def addConvertVec(tipe : Type) {
    tipe.dealias.typeArgs foreach { tipeArg =>
      addConv(tipeArg)
    }
    val typeArg = tipe.dealias.typeArgs(0)
    val toScalaVec = make("toScalaVec", "className" -> typeArg, "name" -> makeScalaName(tipe), "toFunc" -> makeScalaName(typeArg))
    val fromVecToJs = make("fromVecToJs", 
        "className" -> typeArg, 
        "name" -> makeJsName(tipe), 
        "toFunc" -> makeJsName(typeArg))
    addConversion(tipe, fromVecToJs, toScalaVec)
  }
  
  private def addConvertMap(tipe : Type) {
    tipe.typeArgs foreach { tipeArg =>
      addConv(tipeArg)
    }
    val keyArg = tipe.typeArgs(0)
    val valArg = tipe.typeArgs(1)
    val toScalaMap = make("toScalaMap", 
        "keyClassName" -> keyArg, 
        "valueClassName" -> valArg, 
        "name" -> makeScalaName(tipe), 
        "toFunc1" -> makeScalaName(keyArg),
        "toFunc2" -> makeScalaName(valArg))
    val fromMapToJs = make("fromMapToJs", 
        "keyClassName" -> keyArg, 
        "valueClassName" -> valArg, 
        "name" -> makeJsName(tipe),
        "toFunc1" -> makeJsName(keyArg),
        "toFunc2" -> makeJsName(valArg))
    addConversion(tipe, fromMapToJs, toScalaMap)
  }
  
  private def addConvertCaseClass(tipe : Type) {
    var caseClass : CaseClass = null
    try {
      caseClass = CaseClass.caseClassType(tipe, true)
    } catch {
      case ex: IllegalArgumentException => {
        println("Failed to convert:" + tipe)
        ex.printStackTrace()
        throw new IllegalArgumentException("the type '" + tipe + "' was not a case class, vector, or map")
      }
    }
    val toJs = make("toJs", "className" -> tipe, "name" -> makeJsName(tipe))
    val toScala = make("toScala", "className" -> tipe, "name" -> makeScalaName(tipe))
    caseClass.params foreach { param =>
      //System.out.println(param)
      addConv(param.tipe)
      toJs.add("params", make("assignToDict", "paramName" -> param.name, "toFunc" -> makeJsName(param.tipe)))
      toScala.add("args", make("assignFromDict", "paramName" -> param.name, "type" -> param.tipe, "toFunc" -> makeScalaName(param.tipe)))
    }
    addConversion(tipe, toJs, toScala)
  }
  
  private def getNumParamsTuple(tipe : Type): Int = {
    tipe match {
      case t if t <:< typeOf[Tuple1[_]] => 1
      case t if t <:< typeOf[Tuple2[_, _]] => 2
      case t if t <:< typeOf[Tuple3[_, _, _]] => 3
      case t if t <:< typeOf[Tuple4[_, _, _, _]] => 4
      case t if t <:< typeOf[Tuple5[_, _, _, _, _]] => 5
      case t if t <:< typeOf[Tuple6[_, _, _, _, _, _]] => 6
      case t if t <:< typeOf[Tuple7[_, _, _, _, _, _, _]] => 7
      case t if t <:< typeOf[Tuple8[_, _, _, _, _, _, _, _]] => 8
      case t if t <:< typeOf[Tuple9[_, _, _, _, _, _, _, _, _]] => 9
      case t if t <:< typeOf[Tuple10[_, _, _, _, _, _, _, _, _, _]] => 10
      case otherwise => throw new IllegalArgumentException("You are a terrible person. Refactor your code")
    }
  }
  
  private def addConvertTuple(tipe : Type) {
    //convert each stored type
    tipe.typeArgs foreach { tipeArg =>
      addConv(tipeArg)
    }
    //now add the conversion for the actual tuple type
    val toJs = make("toJs", "className" -> tipe, "name" -> makeJsName(tipe))
    val toScala = make("toScala", "className" -> tipe, "name" -> makeScalaName(tipe))
    tipe.typeArgs zip (1 to getNumParamsTuple(tipe)) foreach { param =>
      toJs.add("params", make("assignToDict", 
          "paramName" -> ("_" + param._2.toString()),
          "toFunc" -> makeJsName(param._1)))
      toScala.add("args", make("assignFromDict", 
          "paramName" -> ("_" + param._2.toString()), 
          "type" -> param._1,
          "toFunc" -> makeScalaName(param._1)))
    }
    addConversion(tipe, toJs, toScala)
  }
  
  private def addLeavesConversion(tipe : Type) {
    val clazz = Class.forName(tipe.typeSymbol.asClass.fullName, true, classLoader)
    val seq = clazz.getMethod(jsMapName).invoke(null).asInstanceOf[Seq[Class[_]]]
    val toJs = make("leavesToJs",
          "name" -> makeJsName(tipe), 
          "type" -> tipe)
      val toScala = make("leavesToScala", 
          "name" -> makeScalaName(tipe), 
          "type" -> tipe)
    seq foreach {implType =>
      val caseClass = CaseClass.caseClassType(implType, true)
      addConv(caseClass.tipe) //now we can be sure that this subtype has been implemented
      val leafToJs = make("leafToJs", "subType" -> caseClass.tipe, "toFunc" -> makeJsName(caseClass.tipe))
      val leafToScala = make("leafToScala", "subType" -> caseClass.tipe, "toFunc" -> makeScalaName(caseClass.tipe))
      toJs.add("cases", leafToJs)
      toScala.add("cases", leafToScala)
    }
    addConversion(tipe, toJs, toScala)
  }
  
  private def addConv(tipe : Type) {
    //this serves to dispatch to diffrent functions based on what kind of type
    //we are looking at
    if(hasBeenImplemented(tipe)) return
    markAsImplemented(tipe) //mark this as implemented now so that another call to addConv dosn't try and convert it
    //println(typeOf[Int].dealias, tipe.dealias, impls(tipe.toString()))
    if(tipe.erasure <:< seqType.erasure) {
      addConvertVec(tipe)
    } else if(tipe.erasure <:< mapType.erasure) {
      addConvertMap(tipe)
    } else if(isTupleType(tipe)) {
      addConvertTuple(tipe)
    } else if(isLeavesType(tipe)) {
      addLeavesConversion(tipe)
      println(tipe)
    } else {
      addConvertCaseClass(tipe)
    }
    
    println("conversion function pair created: " + tipe)
  }
  
  def converterObject(tipe : Type): ST = {
    make("converterObj", "type" -> tipe, "toJs" -> makeJsName(tipe), "toScala" -> makeScalaName(tipe))
  }

  def execute(cmode : JsGenMode) {
    topLevel.add("objectName", cmode.genObjectName)
    if(cmode.packageName.length() > 0) topLevel.add("packageName", cmode.packageName)
    //get the class to make the converter for
    classLoader = new URLClassLoader(cmode.classpath.map(x => new File(x).toURI().toURL()).toArray)
    cmode.classNames foreach { className =>
      val clazz = Class.forName(className, true, classLoader)
      val caseClass = CaseClass.caseClassType(clazz, true)
      //now convert the type all all parameters and such, and all parameters parameters, etc...
      addConv(caseClass.tipe)
      //now that we have created the conversions for all the types, add the outward interface
      topLevel.add("converters", converterObject(caseClass.tipe))
      //inform the user of the new converter
      println("Converter for " + caseClass.tipe + " has been created")
    }
    
    //finally we need to output to a file
    val root = new File(cmode.dir)
    if (!root.exists && !root.mkdirs) {
      //tags += OptionUtil.genTag(OptionUtil.ErrorMarker, "Invalid directory %s".format(root.getAbsolutePath))
      return
    }
    val fname = new File(root, cmode.genObjectName + ".scala")
    try {
      val out = new PrintWriter(new FileWriter(fname))
      out.write(topLevel.render())
      out.close()
      println("Succesfully wrote: " + fname.getAbsolutePath)
    } catch {
      case s : Throwable => Console.err.println(s)
    }
  }
}