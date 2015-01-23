package org.sireum.js.casegen

import org.sireum.option._
import java.lang.ClassLoader
import java.lang.Class
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.lang.IllegalArgumentException
import java.net.URL
import java.net.URLClassLoader
import org.sireum.util.Reflection._
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
    val cp = "/Users/jakeehrlich/Documents/workspace/sireum-bakar-private/sireum-bakar-kiasan-server-plugin/bin" +: classpathEntries.toList
    //cp foreach println
    val example = JsGenMode(
        className = "org.sireum.bakar.kiasan.message.UnitInfoStoreMessage",
        classpath = cp.toVector,
        dir = "/Users/jakeehrlich/Documents/workspace/sireum-bakar-private/sireum-bakar-kiasan.js/src/main/scala/org/sireum/js/bakar/kiasan",
        packageName = "org.sireum.js.bakar.kiasan"
    )
    run(example)
  }
}

class JsConvBuilder {
  import scala.reflect.runtime.universe._
  
  private val stg : STGroupFile = new STGroupFile(getClass.getResource("jsconv.stg"), "UTF-8", '$', '$')
  private val topLevel = stg.getInstanceOf("topLevel")
 
  private var names : Map[String, Int] = Map()
  private var impls = Set[Type](
    typeOf[Int], typeOf[Double], typeOf[Float], typeOf[Long],
    typeOf[Short], typeOf[Byte], typeOf[Char], typeOf[Boolean],
    typeOf[Unit], typeOf[String]
  ) map (_.typeSymbol)
  
  private val seqType = typeOf[Seq[_]]
  private val mapType = typeOf[scala.collection.immutable.Map[_, _]]
  private val arrType = typeOf[Array[_]]
  private val vecType = typeOf[Vector[_]]
  
  private def isTupleType(tipe : Type): Boolean = {
    val tupleTypes = List(typeOf[Tuple1[_]], typeOf[Tuple2[_, _]], typeOf[Tuple3[_, _, _]], typeOf[Tuple4[_, _, _, _]])
    //lists don't have forsome method? I used demorgan's law to make one
    !tupleTypes.forall { x => !(tipe.erasure <:< x.erasure) }
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
  
  private def makeScalaName(tipe : Type): ST = {
    make("toScalaName", "type" -> tipe, "id" -> getConversionCount(tipe))
  }
  
  private def makeJsName(tipe : Type): ST = {
    make("toJsName", "type" -> tipe, "id" -> getConversionCount(tipe))
  }
  
  private def addConvertVec(tipe : Type) {
    tipe.typeArgs foreach { tipeArg =>
      addConv(tipeArg)
    }
    val toScalaVec = make("toScalaVec", "className" -> tipe.typeArgs(0), "name" -> makeScalaName(tipe))
    val fromVecToJs = make("fromVecToJs", "className" -> tipe.typeArgs(0), "name" -> makeJsName(tipe))
    addConversion(tipe, fromVecToJs, toScalaVec)
  }
  
  private def addConvertMap(tipe : Type) {
    tipe.typeArgs foreach { tipeArg =>
      addConv(tipeArg)
    }
    val toScalaMap = make("toScalaMap", "keyClassName" -> tipe.typeArgs(0), "valueClassName" -> tipe.typeArgs(1), "name" -> makeScalaName(tipe))
    val fromMapToJs = make("fromMapToJs", "keyClassName" -> tipe.typeArgs(0), "valueClassName" -> tipe.typeArgs(1), "name" -> makeJsName(tipe))
    addConversion(tipe, fromMapToJs, toScalaMap)
  }
  
  private def addConvertCaseClass(tipe : Type) {
    var caseClass : CaseClass = null
    try {
      caseClass = CaseClass.caseClassType(tipe, true)
    } catch {
      case ex: IllegalArgumentException => {
        ex.printStackTrace()
        throw new IllegalArgumentException("the type '" + tipe + "' was not a case class, vector, or map")
      }
    }
    val toJs = make("toJs", "className" -> tipe, "name" -> makeJsName(tipe))
    val toScala = make("toScala", "className" -> tipe, "name" -> makeScalaName(tipe))
    caseClass.params foreach { param =>
      addConv(param.tipe)
      toJs.add("params", make("assignToDict", "paramName" -> param.name))
      toScala.add("args", make("assignFromDict", "paramName" -> param.name, "type" -> param.tipe))
    }
    addConversion(tipe, toJs, toScala)
  }
  
  private def getNumParamsTuple(tipe : Type): Int = {
    tipe match {
      case t if t <:< typeOf[Tuple1[_]] => 1
      case t if t <:< typeOf[Tuple2[_, _]] => 2
      case t if t <:< typeOf[Tuple3[_, _, _]] => 2
      case t if t <:< typeOf[Tuple4[_, _, _, _]] => 3
      case t if t <:< typeOf[Tuple5[_, _, _, _, _]] => 4
      case t if t <:< typeOf[Tuple6[_, _, _, _, _, _]] => 5
      case t if t <:< typeOf[Tuple7[_, _, _, _, _, _, _]] => 6
      case t if t <:< typeOf[Tuple8[_, _, _, _, _, _, _, _]] => 7
      case t if t <:< typeOf[Tuple9[_, _, _, _, _, _, _, _, _]] => 8
      case t if t <:< typeOf[Tuple10[_, _, _, _, _, _, _, _, _, _]] => 9
      case otherwise => throw new IllegalArgumentException("You gave me a really strange type. Don't do that")
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
      toJs.add("params", make("assignToDict", "paramName" -> param._2))
      toScala.add("args", make("assignFromDict", "paramName" -> param._2, "type" -> param._1))
    }
  }
  
  private def addConv(tipe : Type) {
    //this serves to dispatch to diffrent functions based on what kind of type
    //we are looking at
    if(impls(tipe.typeSymbol)) return
    if(tipe.erasure <:< vecType.erasure) {
      addConvertVec(tipe)
    } else if(tipe.erasure <:< mapType.erasure) {
      addConvertMap(tipe)
    } else if(isTupleType(tipe)) {
      addConvertTuple(tipe)
    } else {
      addConvertCaseClass(tipe)
    }
    impls += tipe.typeSymbol
    println("added conversion for: " + tipe)
  }

  def execute(cmode : JsGenMode) {
    topLevel.add("objectName", cmode.genObjectName)
    if(cmode.packageName.length() > 0) topLevel.add("packageName", cmode.packageName)
    //get the class to make the converter for
    val loader = new URLClassLoader(cmode.classpath.map(x => new File(x).toURI().toURL()).toArray)
    val clazz = Class.forName(cmode.className, true, loader)
    val caseClass = CaseClass.caseClassType(clazz, true)
    //walk the type and build the code
    caseClass.annotations foreach { anno =>
      if(anno.clazz.getCanonicalName() == JsConvBuilder.jsConvAnnoName) {
        addConv(caseClass.tipe)
      }
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