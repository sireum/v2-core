/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.state

import org.sireum.util._
import org.sireum.pilar.ast.NameUser

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Heap {
  import Value._

  val NULL_HEAP_ID = -1
  val NULL_ADDRESS_ID = -1
  val NULL = RV(NULL_HEAP_ID, NULL_ADDRESS_ID)

  type HeapId = Int
  type AddressId = Int
  type OCon = (HeapId, AddressId)

  def canon(hid : HeapId, ocon : OCon) =
    if (ocon._1 == NULL_HEAP_ID) (hid, ocon._2)
    else ocon

  def rv(ocon : OCon) : RV = {
    RV(ocon._1, ocon._2)
  }

  def rv(hid : HeapId, ocon : OCon) : RV = {
    val cocon = canon(hid, ocon)
    rv(cocon)
  }

  @inline
  def isVarHid[S <: State[S] with Heap[S]](s : S, x : NameUser, hid : Int) = {
    val o = s.variable(x)
    o match {
      case Heap.RV(rvhid, _) => rvhid == hid
      case _                 => false
    }
  }

  final case class RV(hid : HeapId, aid : AddressId)
    extends ReferenceValue

  final case class O(m : ILinkedMap[AnyRef, Any])
      extends HeapObject {
    def ?(k : AnyRef) : Boolean = m.contains(k)
    def apply(k : AnyRef) : Any = lookup(k)
    def lookup(k : AnyRef) : Any = m(k)
    def update(kv : (AnyRef, Any)) : O = make(m + kv)
    def make(m : ILinkedMap[AnyRef, Any]) : O = O(m)
  }

  object NameAccess {

    implicit class H[S <: Heap[S]](val s : S) extends AnyVal {
      @inline
      def hasFieldValue(rv : ReferenceValue, f : NameUser) =
        s ? (rv, State.uri(f))

      @inline
      def lookup(rv : ReferenceValue, f : NameUser) =
        s.lookup(rv, State.uri(f))

      @inline
      def lookupOrElse(rv : ReferenceValue, f : NameUser, dflt : Value) =
        s.lookupOrElse(rv, State.uri(f), dflt)

      @inline
      def lookupOrElseUpdate(rv : ReferenceValue, f : NameUser, dflt : Value) =
        s.lookupOrElse(rv, State.uri(f), dflt)

      @inline
      def update(rv : ReferenceValue, f : NameUser, v : Value) =
        s.updt(rv, State.uri(f), v)
    }
  }

  object InfoAccess {
    implicit class H[S <: Heap[S]](val s : S) extends AnyVal {
      @inline
      def apply[T](rvk : (ReferenceValue, AnyRef)) : T =
        s.lookup(rvk._1, rvk._2)

      @inline
      def lookupOrElse[T](rv : ReferenceValue, k : AnyRef, dflt : => T) =
        s.lookupOrElse(rv, k, dflt)

      @inline
      def lookupOrElseUpdate[T](rv : ReferenceValue, k : AnyRef, dflt : => T) =
        s.lookupOrElseUpdate(rv, k, dflt)

      @inline
      def update[T](rvk : (ReferenceValue, AnyRef), v : T) =
        s.updt(rvk._1, rvk._2, v)

      @inline
      def ?(rvk : (ReferenceValue, AnyRef)) = s ? (rvk._1, rvk._2)
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait HeapObject

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Heap[Self <: Heap[Self]] extends SelfType[Self] {
  import Heap._
  import Value._

  def heap(hs : Map[OCon, ILinkedMap[AnyRef, Any]]*) : Self = {
    val dangling = msetEmpty[OCon]
    val cache = msetEmpty[OCon]
    var r = self
    for (i <- 0 until hs.length) {
      val h = hs(i)
      val heap = new Array[HeapObject](h.size)
      for (p <- h.iterator) {
        val cocon = canon(i, p._1)
        val ref = rv(cocon)
        val m = p._2.map { kv =>
          val (k, v) = kv
          v match {
            case ocon : OCon =>
              val cocon2 = canon(i, ocon)
              if (!cache.contains(cocon2))
                dangling += cocon2
              (k, rv(cocon2))
            case _ => kv
          }
        }
        val o = O(m)
        cache += cocon
        dangling -= cocon
        heap(ref.aid) = o
      }
      r = r.make(i, seq2ivect(heap))
    }
    assert(dangling.isEmpty)
    r
  }

  def heaps : ISeq[ISeq[HeapObject]]
  def make(hid : HeapId, objects : ISeq[HeapObject]) : Self

  def make(hid : HeapId, aid : AddressId, o : HeapObject) : Self =
    make(hid, heaps(hid).updated(aid, o))

  def isNull(rv : ReferenceValue) : Boolean
  def nullValue() : ReferenceValue

  def objects(hid : HeapId) : Iterable[ReferenceValue]
  def newObject(hid : HeapId, initPairs : (AnyRef, Any)*) : (Self, ReferenceValue)

  def ?(rv : ReferenceValue, k : AnyRef) : Boolean
  def lookup[T](rv : ReferenceValue, k : AnyRef) : T
  def lookupOrElse[T](rv : ReferenceValue, k : AnyRef, dflt : => T) : T
  def get[T](rv : ReferenceValue, k : AnyRef) : Option[T]
  def updt[T](rv : ReferenceValue, k : AnyRef, v : T) : Self
  def lookupOrElseUpdate[T](rv : ReferenceValue, k : AnyRef, dflt : => T) : (Self, T)

  def elements(rv : ReferenceValue) : Iterator[(AnyRef, Any)]

  def duplicate(rv : ReferenceValue) : (Self, ReferenceValue)
}

trait DummyDuplicateHeap[Self <: DummyDuplicateHeap[Self]] {
  def duplicate(rv : ReferenceValue) : (Self, ReferenceValue) = {
    sys.error("unhandled")
  }
}

trait HeapPart[Self <: Heap[Self]] extends Heap[Self] {
  import Heap._
  import Value._

  def isNull(rv : ReferenceValue) : Boolean =
    rv.asInstanceOf[RV].aid == NULL_ADDRESS_ID

  def nullValue() : ReferenceValue = NULL

  def objects(hid : HeapId) : Iterable[ReferenceValue] = {
    val heap = heaps(hid)
    val numOfObjects = heap.size
    val r = new Array[ReferenceValue](numOfObjects)
    for (i <- 0 until numOfObjects) {
      r(i) = RV(hid, i)
    }
    r
  }

  def newObject(hid : HeapId, initPairs : (AnyRef, Any)*) : (Self, ReferenceValue) = {
    var m = ilinkedMapEmpty[AnyRef, Any]
    for (p <- initPairs) {
      m = m + p
    }
    val heap = heaps(hid)
    val numOfObjects = heap.size
    val o = O(m)
    (make(hid, heap :+ o), RV(hid, numOfObjects))
  }

  def ?(rv : ReferenceValue, k : AnyRef) : Boolean = {
    val ref = rv.asInstanceOf[RV]
    val heap = heaps(ref.hid)
    val o = heap(ref.aid).asInstanceOf[O]
    o ? k
  }

  def lookup[T](rv : ReferenceValue, k : AnyRef) : T = {
    val ref = rv.asInstanceOf[RV]
    val heap = heaps(ref.hid)
    val o = heap(ref.aid).asInstanceOf[O]
    o(k).asInstanceOf[T]
  }

  def get[T](rv : ReferenceValue, k : AnyRef) : Option[T] = {
    val ref = rv.asInstanceOf[RV]
    val heap = heaps(ref.hid)
    val o = heap(ref.aid).asInstanceOf[O]
    if (o ? k) Some(o(k).asInstanceOf[T])
    else None
  }

  def updt[T](rv : ReferenceValue, k : AnyRef, v : T) : Self = {
    val ref = rv.asInstanceOf[RV]
    val hid = ref.hid
    val aid = ref.aid
    val heap = heaps(hid)
    val o = heap(aid).asInstanceOf[O]
    make(hid, aid, o(k) = v)
  }

  def lookupOrElse[T](rv : ReferenceValue, k : AnyRef,
                          dflt : => T) : T = {
    val ref = rv.asInstanceOf[RV]
    val heap = heaps(ref.hid)
    val o = heap(ref.aid).asInstanceOf[O]
    if (o ? k) o(k).asInstanceOf[T]
    else dflt
  }

  def lookupOrElseUpdate[T](rv : ReferenceValue, k : AnyRef,
                                dflt : => T) : (Self, T) = {
    val ref = rv.asInstanceOf[RV]
    val hid = ref.hid
    val aid = ref.aid
    val heap = heaps(hid)
    val o = heap(aid).asInstanceOf[O]
    if (o ? k) (self, o(k).asInstanceOf[T])
    else (make(hid, aid, o(k) = dflt), dflt)
  }

  def elements(rv : ReferenceValue) : Iterator[(AnyRef, Any)] = {
    val ref = rv.asInstanceOf[RV]
    val heap = heaps(ref.hid)
    val o = heap(ref.aid).asInstanceOf[O]
    o.m.iterator
  }
}