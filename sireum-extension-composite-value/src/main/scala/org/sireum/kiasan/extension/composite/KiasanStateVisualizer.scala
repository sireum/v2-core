/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan.extension.composite

import java.io._
import org.sireum.kiasan.state._
import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.util._
import KiasanStateVisualizer._
import org.sireum.kiasan.extension.composite.value.KiasanValCompositeExtension
import org.sireum.konkrit.extension.composite.value.KonkritValCompositeExtension

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanStateVisualizer {
  type SHK[S <: SHK[S]] = State[S] with KiasanStatePart[S] with Heap[S]

  def apply[S <: SHK[S]](
    s : S, name : String, w : Writer,
    vviz : Value => String = { _.toString },
    eviz : Exp => String = { _.toString },
    pc : Boolean = true,
    template : String = "rst.stg",
    filter : String => Boolean = { _ => true }) =
    new KiasanStateVisualizer(s, name, w, vviz, eviz, pc, template, filter)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class KiasanStateVisualizer[S <: SHK[S]](
    s : S,
    name : String,
    w : Writer,
    vviz : Value => String,
    eviz : Exp => String,
    pc : Boolean,
    template : String,
    filter : String => Boolean) {

  import org.stringtemplate.v4._

  val stg = new STGroupFile(getClass.getResource(template), "UTF-8", '$', '$')
  val state = stg.getInstanceOf("state")
  state.add("name", name)

  if (!s.globalStore.isEmpty) { // globals
    val globals = stg.getInstanceOf("globalVars")
    vars(s.globalStore, globals, "var")
    state.add("entry", globals)
  }

  if (!s.callStack.isEmpty) { // call stack
    val callStack = stg.getInstanceOf("callStack")
    for (cf <- s.callStack) {
      val callFrame = stg.getInstanceOf("callFrame")
      callFrame.add("loc", cf.locationIndex)
      if (cf.locationUri.isDefined)
        callFrame.add("loc", s" [${cf.locationUri.get}]")
      callFrame.add("sig", cf.procedure)
      vars(cf.store, callFrame, "localVar")
      callStack.add("frame", callFrame)
    }
    state.add("entry", callStack)
  }

  if (pc && !s.pathConditions.isEmpty) { // path conditions
    val pcs = stg.getInstanceOf("pathConditions")
    for (e <- s.pathConditions) {
      val pc = stg.getInstanceOf("pc")
      pc.add("text", eviz(e))
      pcs.add("element", pc)
    }
    state.add("entry", pcs)
  }

  state.write(new AutoIndentWriter(w))

  def vars(m : IMap[ResourceUri, Value], st : ST, element : String) {
    for ((varUri, value) <- m if (filter(varUri))) {
      val slot = stg.getInstanceOf("slot")
      slot.add("name", varUri)
      slot.add("value", vviz(value))
      applyIfInstance[Heap.RV](value, composite(slot)(_))
      st.add(element, slot)
    }
  }

  def composite(slot : ST)(implicit rv : Heap.RV) {
    if (KiasanValCompositeExtension.Record.is(s)) {
      import KiasanValCompositeExtension.Record.Field.Access._
      vars(s.fieldMapping, slot, "element")
    } else if (KonkritValCompositeExtension.Record.is(s)) {
      import KonkritValCompositeExtension.Record.Field.Access._
      vars(s.fields, slot, "element")
    } else if (KiasanValCompositeExtension.Array.is(s)) {
      import KiasanValCompositeExtension.Array.Element.Access._
      slot.add("element", s"* min=${vviz(s.minIndex)}; length=${vviz(s.length)}; max=${vviz(s.maxIndex)}")
      for ((index, value) <- s.concreteMapping) {
        val arrayElement = stg.getInstanceOf("slot")
        arrayElement.add("name", s"[${index.toBigInt}]")
        arrayElement.add("value", vviz(value))
        applyIfInstance[Heap.RV](value, composite(arrayElement)(_))
        slot.add("element", arrayElement)
      }
      for ((index, value) <- s.symbolicMapping) {
        val arrayElement = stg.getInstanceOf("slot")
        arrayElement.add("name", s"[${vviz(index)}]")
        arrayElement.add("value", vviz(value))
        applyIfInstance[Heap.RV](value, composite(arrayElement)(_))
        slot.add("element", arrayElement)
      }
    } else if (KonkritValCompositeExtension.Array.is(s)) {
      import KonkritValCompositeExtension.Array.Element.Access._
      val minIndex = s.minIndex
      val length = s.length
      slot.add("element", s"* min=${minIndex.toBigInt}; length=${length.toBigInt}; max=${s.maxIndex.toBigInt}")
      val a = s.nativeArray
      for (i <- 0 until length.toInt) {
        val arrayElement = stg.getInstanceOf("slot")
        arrayElement.add("name", s"[${(minIndex + i).toBigInt}]")
        val value = a(i)
        arrayElement.add("value", vviz(value))
        applyIfInstance[Heap.RV](value, composite(arrayElement)(_))
        slot.add("element", arrayElement)
      }
    }
  }
}