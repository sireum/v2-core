/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pipeline.gen

import java.io.{ PrintWriter, FileWriter }
import java.lang.reflect.ParameterizedType
import scala.Array.fallbackCanBuildFrom
import org.sireum.option.PipelineMode
import org.sireum.pipeline.{ Produce, Output, Input, Consume }
import org.sireum.util._
import org.stringtemplate.v4.ST
import org.stringtemplate.v4.STGroupFile

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
object ModuleGenerator {
  def run(p : PipelineMode) = new ModuleGenerator(p).execute
}

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
class ModuleGenerator(p : PipelineMode) {
  type Method = java.lang.reflect.Method
  val stg : STGroupFile = new STGroupFile(getClass.getResource("module.stg"), "UTF-8", '$', '$')
  val moduleDefs = marrayEmpty[String]
  val imports = msetEmpty[String]
  val seenSet = msetEmpty[Class[_]]
  val rtMap = mmapEmpty[Method, String]

  var typeSubMap = mmapEmpty[String, String]
  typeSubMap("java.lang.Boolean") = "scala.Boolean"

  def collect[T](m : Method, c : Class[T]) : scala.Option[T] = {
    for (a <- m.getDeclaredAnnotations) {
      if (a.annotationType.getName == c.getName) {
        return Some(a.asInstanceOf[T])
      }
    }
    None
  }

  def methodExists(c : Class[_], m : java.lang.reflect.Method) : scala.Option[String] = {
    val mProds = c.getDeclaredMethods.filter(name => name.getName == m.getName)
    if (mProds.isEmpty) {
      return Some(c.getSimpleName + " does not contain " + m.getName)
    }
    if (mProds.size > 1) {
      return Some("")
    }

    val m1 = mProds.head
    val mname = c.getSimpleName + "." + m1.getName

    val prods = m1.getAnnotations.collect({ case a : Produce => a })
    if (prods.isEmpty) {
      return Some(mname + " does not have Produce annotation")
    }

    if (ModuleGenerator.this.getActualRetTypeName(m1) != ModuleGenerator.this.getActualRetTypeName(m)) {
      return Some("expecting " + ModuleGenerator.this.getActualRetTypeName(m) + " found" + ModuleGenerator.this.getActualRetTypeName(m1))
    }

    return None
  }

  def cleanup(s : String) = s.replaceAll("\\$", ".")

  def getActualRetTypeName(m : java.lang.reflect.Method) : String = {
    if (ModuleGenerator.this.rtMap.contains(m)) {
      return rtMap(m)
    }

    var ret = m.getReturnType.getName
    ModuleGenerator.this.imports += m.getReturnType.getName

    if (m.getGenericReturnType.isInstanceOf[java.lang.reflect.ParameterizedType]) {
      val pt = m.getGenericReturnType.asInstanceOf[java.lang.reflect.ParameterizedType]
      ret = pt.toString.replaceAll("<", "[").replaceAll(">", "]")

      {
        pt.getActualTypeArguments.foreach(ta =>
          ta match {
            case q : ParameterizedType =>
              // TODO:
              val a = q.getActualTypeArguments
              val b = q.getOwnerType
              val c = q.getRawType
              val d = q.getClass
              println(q)
              println(q)
            case q : Class[_] =>
              ModuleGenerator.this.imports += q.getName
          })
      }
    }

    if (ret == "boolean")
      ret = "scala.Boolean"
    else
      ret = cleanup(ret)

    ModuleGenerator.this.typeSubMap.foreach(e => ret = ret.replaceAll(e._1, e._2))

    ModuleGenerator.this.rtMap(m) = ret

    return ret
  }

  def filter(meths : Array[Method]) : Array[Method] =
    meths.filter(f => !f.getName.startsWith("copy") && !f.getName.contains("$"))

  def getReflectName(c : Class[_]) = (c.getName + "ModuleDef", c.getSimpleName + "ModuleDef")

  def getModName(c : Class[_]) = c.getSimpleName + "Module"

  def getKeyName(c : Class[_], m : Method, isLocal : Boolean) : (String, String, String) = {
    if (isLocal) {
      (getModName(c), m.getName + "Key", c.getSimpleName + "." + m.getName)
    } else {
      (getModName(c),
        "global" + m.getName.head.toUpper + m.getName.tail + "Key",
        "Global." + m.getName)
    }
  }

  class ClassInfo(c : Class[_]) {
    val modName = getModName(c)
    val methodDefValues = mmapEmpty[String, (Integer, Any)]
    val inst = build()

    def build() : AnyRef = {
      val const = c.getConstructors.head
      val params = new Array[AnyRef](const.getParameterTypes.length)
      for (i <- 0 until const.getParameterTypes.length) {
        try {
          val mname = "$lessinit$greater$default$" + (i + 1)
          params(i) = c.getMethod(mname).invoke(null)

          // get the method corresponding to 'i' (the field name and method name
          // are the same in scala 2.9)
          val field = c.getDeclaredFields()(i)
          var actualMethod = c.getDeclaredMethod(field.getName)

          methodDefValues(actualMethod.getName) = (i + 1, params(i))
        } catch {
          case _ : Throwable =>
            // assign a default value for the param so that we can create an
            // instance of c
            val t = const.getParameterTypes()(i)
            if (t.isPrimitive) {
              if (t.getName == "boolean")
                params(i) = false.asInstanceOf[AnyRef]
              else
                params(i) = -1.asInstanceOf[AnyRef]
            } else {
              params(i) = null
            }
        }
      }
      return const.newInstance(params : _*).asInstanceOf[AnyRef]
    }
  }

  def cap(s : String) = s.head.toUpper + s.tail

  def mineClass(c : Class[_]) {
    if (ModuleGenerator.this.seenSet.contains(c)) {
      return
    }
    ModuleGenerator.this.seenSet += c

    val ci = new ClassInfo(c)

    val keys = msetEmpty[String]

    {
      val usertemp = stg.getInstanceOf("user_class")
      usertemp.add("package", c.getPackage.getName)
      usertemp.add("cname", getReflectName(c)._2)
      usertemp.add("mname", getModName(c))
      moduleDefs += usertemp.render
    }

    val modLevel = stg.getInstanceOf("modLevel")
    val modName = getModName(c)
    modLevel.add("className", ci.modName)
    modLevel.add("title", ci.methodDefValues("title")._2)
    modLevel.add("reflectClass", getReflectName(c)._1)
    modLevel.add("origin", c.getSimpleName)

    var consumptions = ilistEmpty[ST]
    var productions = ilistEmpty[ST]

    for (m <- filter(c.getDeclaredMethods())) yield {

      val hasInput = collect(m, classOf[Input]).isDefined
      val consumes = collect(m, classOf[Consume])
      val hasOutputs = collect(m, classOf[Output]).isDefined
      val hasProduces = collect(m, classOf[Produce]).isDefined

      if (hasInput || consumes.isDefined || hasOutputs || hasProduces) {
        val isGlobalField = hasInput || hasOutputs
        val isLocalField = consumes.isDefined || hasProduces

        val (glob_className, glob_keyName, glob_keyVal) = getKeyName(c, m, false)
        val globalKeyName = glob_className + "." + glob_keyName

        val (loc_className, loc_keyName, loc_keyVal) = getKeyName(c, m, true)
        val localKeyName = loc_className + "." + loc_keyName

        val rt = ModuleGenerator.this.getActualRetTypeName(m)

        // each annotated field yields a getter and setter in the companion
        val companionGetter = stg.getInstanceOf("get_mod_method")
        val companionGetName = "get" + cap(m.getName)
        companionGetter.add("mname", companionGetName)
        companionGetter.add("rtype", rt)
        modLevel.add("mod_meth", companionGetter)

        val companionSetter = stg.getInstanceOf("set_mod_method")
        val companionSetName = "set" + cap(m.getName)
        companionSetter.add("mname", companionSetName)
        companionSetter.add("pname", m.getName)
        companionSetter.add("rtype", rt)
        modLevel.add("mod_meth", companionSetter)

        {
          // each annotated field yields a consumer and producer view
          val consumerGet = stg.getInstanceOf("get_trait_method")
          consumerGet.add("modname", modName)
          consumerGet.add("modget", companionGetName)
          consumerGet.add("mname", m.getName)
          consumerGet.add("rtype", rt)
          consumptions :+= consumerGet

          val producerSet = stg.getInstanceOf("set_trait_method")
          producerSet.add("mname", m.getName)
          producerSet.add("modname", modName)
          producerSet.add("setter_name", companionSetName)
          producerSet.add("getter_name", companionGetName)
          producerSet.add("rtype", rt)
          productions :+= producerSet
        }

        if (isGlobalField) {
          // add a set entry in companion
          val akvst = stg.getInstanceOf("assign_key_value")
          akvst.add("key", globalKeyName)
          akvst.add("pname", m.getName)
          companionSetter.add("assign_key_value", akvst)

          // add a get entry in companion
          val icst = stg.getInstanceOf("inputchk")
          icst.add("key", globalKeyName)
          icst.add("rtype", rt)
          companionGetter.add("inputchk", icst)

          val keyst = stg.getInstanceOf("key")
          keyst.add("value", glob_keyVal)
          keyst.add("keyname", glob_keyName)
          keys += keyst.render
        }

        if (isLocalField) {
          val akvst = stg.getInstanceOf("assign_key_value")
          akvst.add("key", loc_keyName)
          akvst.add("pname", m.getName)
          companionSetter.add("assign_key_value", akvst)

          val icst = stg.getInstanceOf("inputchk")
          icst.add("key", localKeyName)
          icst.add("rtype", rt)
          companionGetter.add("inputchk", icst)

          val keyst = stg.getInstanceOf("key")
          keyst.add("value", loc_keyVal)
          keyst.add("keyname", loc_keyName)
          keys += keyst.render
        }

        if (hasInput || consumes.isDefined) {
          inputConsume(hasInput, consumes)
        }

        if (hasOutputs || hasProduces) {
          outputProduce(hasOutputs, hasProduces)
        }

          def outputProduce(hasOutput : Boolean, hasProduce : Boolean) {
            val traitSet = stg.getInstanceOf("set_trait_method")
            traitSet.add("mname", m.getName)
            traitSet.add("modname", modName)
            traitSet.add("setter_name", companionSetName)
            traitSet.add("getter_name", companionGetName)
            traitSet.add("rtype", rt)
            modLevel.add("trait_meth", traitSet);

            val oknfst = stg.getInstanceOf("outdef_key_not_found")
            oknfst.add("mname", m.getName);
            modLevel.add("outdef", oknfst)

            if (hasProduce) doit(false, localKeyName, loc_keyVal)
            if (hasOutput) doit(true, globalKeyName, glob_keyVal)

              def doit(isProduce : Boolean, key : String, kv : String) {

                oknfst.add("key", key);

                val outdefhst = stg.getInstanceOf("outdef_is_valid_entry")
                outdefhst.add("rtype", rt)
                outdefhst.add("key", key)
                modLevel.add("outdef", outdefhst);
              }
          }

          def inputConsume(hasInput : Boolean, hasConsume : scala.Option[Consume]) {

            if (!hasOutputs && !hasProduces) {
              val traitGet = stg.getInstanceOf("get_trait_method")
              traitGet.add("modname", modName)
              traitGet.add("modget", companionGetName)
              traitGet.add("mname", m.getName)
              traitGet.add("rtype", rt)
              modLevel.add("trait_meth", traitGet)
            }

            val init = stg.getInstanceOf("init")

            if (ci.methodDefValues.contains(m.getName)) {
              val (index, value) = ci.methodDefValues(m.getName)
              init.add("set_mod_name", companionSetName)
              init.add("spec_name", c.getName)
              init.add("mname", m.getName)
              init.add("static_mname", "$lessinit$greater$default$" + index)
              init.add("rtype", rt)
              modLevel.add("init", init)
            }

            val idst = stg.getInstanceOf("indef")
            idst.add("mname", m.getName);
            idst.add("rtype", rt);

            if (hasInput) {
              init.add("key", globalKeyName)
              idst.add("key", globalKeyName)
            }

            hasConsume match {
              case Some(s) =>
                for (d <- s.value) {
                  methodExists(d, m) match {
                    case Some(message) =>
                      println("Error: " + message)
                      System.exit(0)
                    case None =>
                  }
                  val (d_className, d_keyName, d_keyValue) = getKeyName(d, m, true)
                  val dkeyname = d_className + "." + d_keyName

                  init.add("key", d_keyName)
                  modLevel.add("dep", d_className)

                  val consumchkst = stg.getInstanceOf("inputchk")
                  consumchkst.add("rtype", rt)
                  consumchkst.add("key", dkeyname)
                  companionGetter.add("inputchk", consumchkst)

                  idst.add("key", dkeyname)
                  mineClass(d)
                }
              case None =>
            }

            modLevel.add("indef", idst)
          }
      }
    } // end for loop

    if (!consumptions.isEmpty) {
      // add a consumer view
      val consumerst = stg.getInstanceOf("consumerProducer")
      consumerst.add("type", "ConsumerView")
      consumerst.add("cname", ci.modName)

      for (c <- consumptions)
        consumerst.add("entry", c)

      modLevel.add("mod_meth", consumerst)
    }

    if (!productions.isEmpty) {
      // add a production view
      val producerst = stg.getInstanceOf("consumerProducer")
      producerst.add("type", "ProducerView")
      producerst.add("cname", ci.modName)

      for (p <- productions)
        producerst.add("entry", p)

      modLevel.add("mod_meth", producerst)
    }

    // now add the generated key defs to the top level
    keys.foreach(k => modLevel.add("key", k))

    val topLevel = stg.getInstanceOf("topLevel")
    topLevel.add("module", modLevel)
    topLevel.add("_package", c.getPackage().getName)
    topLevel.add("moduleName", c.getName)
    topLevel.add("generatorName", this.getClass().getName)

    (isortedSetEmpty[String] ++ imports).foreach(i => {
      if (i != "boolean") topLevel.add("imports", cleanup(i))
    })

    if (!p.dir.isEmpty) {
      val genName = c.getSimpleName + "Module.scala"

      try {
        val fname = p.dir + "/" + genName
        val out = new PrintWriter(new FileWriter(fname))
        out.write(topLevel.render())
        out.close()

        println("Succesfully wrote: " + fname)
      } catch {
        case s : Throwable => println(s)
      }
    } else {
      println(topLevel.render())
    }
  }

  def execute {

    if (!p.typeSubstitutions.isEmpty) {
      p.typeSubstitutions.foreach { t =>
        val q = t.split("/")
        assert(q.size == 2)
        ModuleGenerator.this.typeSubMap(q(0)) = q(1)
      }
    }

    for (cn <- p.classNames)
      mineClass(Class.forName(cn))

    moduleDefs.foreach(s => println(s))
  }
}