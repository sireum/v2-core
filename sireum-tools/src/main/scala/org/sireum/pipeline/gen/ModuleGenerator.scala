/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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
import org.stringtemplate.v4.STGroupFile

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
object ModuleGenerator {
  def main(args : Array[String]) {
    val opt = PipelineMode()

    if (args.isEmpty) {
      opt.classNames = Array("org.sireum.pipeline.examples.C2")
      opt.dir = "./src/main/scala/org/sireum/pipeline/examples"
      opt.genClassName = "testpipeline"
    } else {
      opt.classNames = Array(args(0))
    }
    run(opt)
  }

  def run(poptions : PipelineMode) {
    new ModuleGenerator().execute(poptions)
  }
}

/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 */
class ModuleGenerator {
  type Method = java.lang.reflect.Method
  val stg : STGroupFile = new STGroupFile(getClass.getResource("module.stg"), "UTF-8", '$', '$')
  val topLevel = stg.getInstanceOf("topLevel")
  val skeletons = marrayEmpty[String]
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

  def getReflectName(c : Class[_]) = (c.getName + "Def", c.getSimpleName + "Def")

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
          case _ =>
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
      ModuleGenerator.this.skeletons += usertemp.render
    }

    val modLevel = stg.getInstanceOf("modLevel")
    val modName = getModName(c)
    modLevel.add("className", ci.modName)
    modLevel.add("title", ci.methodDefValues("title")._2)
    modLevel.add("reflectClass", getReflectName(c)._1)
    modLevel.add("origin", c.getSimpleName)

    for (m <- filter(c.getDeclaredMethods())) yield {
      val (loc_className, loc_keyName, loc_keyVal) = getKeyName(c, m, true)
      val (glob_className, glob_keyName, glob_keyVal) = getKeyName(c, m, false)

      val hasInput = collect(m, classOf[Input]).isDefined
      val consumes = collect(m, classOf[Consume])
      val hasOutputs = collect(m, classOf[Output]).isDefined
      val hasProduces = collect(m, classOf[Produce]).isDefined

      if (hasInput || consumes.isDefined) {
        inputConsume(hasInput, consumes)
      }

      if (hasOutputs || hasProduces) {
        outputProduce(hasOutputs, hasProduces)
      }

        def outputProduce(hasOutput : Boolean, hasProduce : Boolean) {
          val modsetname = "modSet" + cap(m.getName)
          val extGetName = "get" + cap(m.getName)
          var rt = ModuleGenerator.this.getActualRetTypeName(m)

          val extGetst = stg.getInstanceOf("get_mod_method")
          extGetst.add("mname", extGetName)
          extGetst.add("rtype", rt)

          var smmst = stg.getInstanceOf("set_mod_method")
          smmst.add("mname", modsetname)
          smmst.add("pname", m.getName)
          smmst.add("rtype", rt)
          if (hasOutput) {
            val keyname = glob_className + "." + glob_keyName

            val akvst = stg.getInstanceOf("assign_key_value")
            akvst.add("key", keyname)
            akvst.add("pname", m.getName)
            smmst.add("assign_key_value", akvst)

            val icst = stg.getInstanceOf("inputchk")
            icst.add("key", keyname)
            icst.add("rtype", rt)
            extGetst.add("inputchk", icst)
          }
          if (hasProduce) {
            val keyname = loc_className + "." + loc_keyName
            val akvst = stg.getInstanceOf("assign_key_value")
            akvst.add("key", keyname)
            akvst.add("pname", m.getName)
            smmst.add("assign_key_value", akvst)

            val icst = stg.getInstanceOf("inputchk")
            icst.add("key", keyname)
            icst.add("rtype", rt)
            extGetst.add("inputchk", icst)
          }

          modLevel.add("mod_meth", extGetst)
          modLevel.add("mod_meth", smmst)

          val stm = stg.getInstanceOf("set_trait_method")

          stm.add("mname", m.getName)
          stm.add("modname", modName)
          stm.add("modset", modsetname)
          stm.add("rtype", rt)
          modLevel.add("trait_meth", stm);

          val oknfst = stg.getInstanceOf("outdef_key_not_found")
          oknfst.add("mname", m.getName);
          modLevel.add("outdef", oknfst)

          if (hasProduce) doit(false, loc_className, loc_keyName, loc_keyVal)
          if (hasOutput) doit(true, glob_className, glob_keyName, glob_keyVal)

            def doit(isProduce : Boolean, cn : String, kn : String, kv : String) {
              val keyst = stg.getInstanceOf("key")
              keyst.add("value", kv)
              keyst.add("keyname", kn)
              keys += keyst.render
              oknfst.add("key", cn + "." + kn);

              val outdefhst = stg.getInstanceOf("outdef_is_valid_entry")
              outdefhst.add("rtype", rt)
              outdefhst.add("key", cn + "." + kn)
              modLevel.add("outdef", outdefhst);
            }
        }

        def inputConsume(hasInput : Boolean, hasConsume : scala.Option[Consume]) {
          val modGetName = "modGet" + cap(m.getName)
          val extSetName = "set" + cap(m.getName)

          val extSetst = stg.getInstanceOf("set_mod_method")
          val gmmst = stg.getInstanceOf("get_mod_method")
          val gtmst = stg.getInstanceOf("get_trait_method")
          val idst = stg.getInstanceOf("indef")

          var rt = getActualRetTypeName(m)

          extSetst.add("mname", extSetName)
          extSetst.add("rtype", rt)
          extSetst.add("pname", m.getName)

          val init = stg.getInstanceOf("init")

          if (ci.methodDefValues.contains(m.getName)) {
            val (index, value) = ci.methodDefValues(m.getName)
            init.add("set_mod_name", extSetName)
            init.add("spec_name", c.getName)
            init.add("mname", m.getName)
            init.add("static_mname", "$lessinit$greater$default$" + index)
            init.add("rtype", rt)
            modLevel.add("init", init)
          }

          gmmst.add("mname", modGetName)
          gmmst.add("rtype", rt)
          idst.add("mname", m.getName);
          idst.add("rtype", rt);

          gtmst.add("modname", modName)
          gtmst.add("modget", modGetName)
          gtmst.add("mname", m.getName)
          gtmst.add("rtype", rt)

          if (hasInput) {
            val gkeyname = glob_className + "." + glob_keyName

            init.add("key", gkeyname)

            val consumchkst = stg.getInstanceOf("inputchk")
            consumchkst.add("key", gkeyname)
            consumchkst.add("rtype", rt)
            gmmst.add("inputchk", consumchkst)
            idst.add("key", gkeyname)

            val akvst = stg.getInstanceOf("assign_key_value")
            akvst.add("key", gkeyname).add("pname", m.getName)
            extSetst.add("assign_key_value", akvst)

            val keyst = stg.getInstanceOf("key")
            keyst.add("keyname", glob_keyName)
            keyst.add("value", glob_keyVal)
            keys += keyst.render
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
                gmmst.add("inputchk", consumchkst)
                idst.add("key", dkeyname)

                val akvst = stg.getInstanceOf("assign_key_value")
                akvst.add("key", dkeyname).add("pname", m.getName)
                extSetst.add("assign_key_value", akvst)

                mineClass(d)
              }
            case None =>
          }

          modLevel.add("indef", idst)
          modLevel.add("mod_meth", gmmst)
          modLevel.add("mod_meth", extSetst)
          modLevel.add("trait_meth", gtmst)
        }
    } // end for loop

    // now add the generated key defs to the top level
    keys.foreach(k => modLevel.add("key", k))
    topLevel.add("module", modLevel)
  }

  def execute(poptions : PipelineMode) {

    if (!poptions.typeSubstitutions.isEmpty) {
      poptions.typeSubstitutions.foreach { t =>
        val q = t.split("/")
        assert(q.size == 2)
        ModuleGenerator.this.typeSubMap(q(0)) = q(1)
      }
    }

    val packs = msetEmpty[String]
    poptions.classNames.foreach(cn => {
      val clazz = Class.forName(cn)
      mineClass(clazz)
      packs += clazz.getPackage.getName
    })
    assert(packs.size == 1)
    ModuleGenerator.this.topLevel.add("_package", packs.head)

    ModuleGenerator.this.skeletons.foreach(s => println(s))

    (isortedSetEmpty[String] ++ imports).foreach(i => {
      if (i != "boolean") ModuleGenerator.this.topLevel.add("imports", cleanup(i))
    })

    if (!poptions.dir.isEmpty && !poptions.genClassName.isEmpty) {
      val dir = poptions.dir
      val genName = poptions.genClassName

      try {
        val fname = dir + "/" + genName + ".scala"
        val out = new PrintWriter(new FileWriter(fname))
        out.write(topLevel.render())
        out.close()

        println("Succesfully wrote: " + fname)
      } catch {
        case s => println(s)
      }
    } else {
      println(topLevel.render())
    }
  }
}