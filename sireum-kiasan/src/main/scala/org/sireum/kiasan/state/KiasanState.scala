/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan.state

import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.kiasan.extension._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanState {
  def updateCounters(m : MMap[ResourceUri, Int], v : Value) {
    v match {
      case kv : KiasanExtension.KiasanValue with ScalarValue =>
        val turi = kv.typeUri
        val counter = m.getOrElse(kv.typeUri, 0)
        if (kv.num > counter)
          m(turi) = kv.num
      case _ =>
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanStatePart[Self <: KiasanStatePart[Self]] extends SelfType[Self] {
  def pathConditions : ISeq[Exp]

  def counters : IMap[ResourceUri, Int]

  def init : Self

  def requestInconsistencyCheck(shouldCheck : Boolean = true) : Self

  def inconsistencyCheckRequested : Boolean

  def addPathCondition(e : Exp) : Self =
    make(e +: pathConditions, counters)

  def fresh(uri : ResourceUri) : (Self, Int) = {
    val last = counters.getOrElse(uri, 0)
    val r = last + 1
    (make(pathConditions, counters + (uri -> r)), r)
  }

  protected def make(pathConditions : ISeq[Exp], counters : IMap[ResourceUri, Int]) : Self
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object BasicKiasanState {
  def apply() : BasicKiasanState =
    BasicKiasanState(imapEmpty, ivectorEmpty, ivectorEmpty, ivectorEmpty, imapEmpty,
      imapEmpty, None, None, false, ivectorEmpty)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class BasicKiasanState(
  globalStore : State.Store,
  closureStoreStack : State.StoreStack,
  callStack : State.CallStack,
  pathConditions : ISeq[Exp],
  counters : IMap[ResourceUri, Int],
  properties : Property.ImmutableProperties,
  assertionViolation : Option[AssertionViolationInfo],
  raisedException : Option[BasicExceptionInfo],
  inconsistencyCheckRequested : Boolean,
  schedule : ISeq[(Int, Int)])
    extends State[BasicKiasanState] with KiasanStatePart[BasicKiasanState]
    with ScheduleRecordingState[BasicKiasanState] {

  import State._

  def init : BasicKiasanState = {
    val newCounters : MMap[ResourceUri, Int] = mmapEmpty

    import KiasanState._

    Visitor.build({
      case v : Value =>
        updateCounters(newCounters, v)
        true
    })(this)

    make(pathConditions, newCounters.toMap)
  }

  def raiseException(exceptionValue : Value, li : LocationInfo) : BasicKiasanState =
    BasicKiasanState(globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties,
      assertionViolation, Some(BasicExceptionInfo(exceptionValue, ivector(li))),
      inconsistencyCheckRequested, schedule)

  def assertionViolation(avi : AssertionViolationInfo) : BasicKiasanState =
    BasicKiasanState(globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties, Some(avi), raisedException,
      inconsistencyCheckRequested, schedule)

  def make(globalStore : Store, closureStoreStack : StoreStack, callStack : CallStack,
           properties : Property.ImmutableProperties) : BasicKiasanState =
    BasicKiasanState(globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties, assertionViolation, raisedException,
      inconsistencyCheckRequested, schedule)

  def requestInconsistencyCheck(shouldCheck : Boolean) : BasicKiasanState =
    BasicKiasanState(globalStore, closureStoreStack, callStack, pathConditions,
      counters, properties, assertionViolation, raisedException,
      shouldCheck, schedule)

  def recordSchedule(numChoices : Int, chosen : Int) : BasicKiasanState =
    BasicKiasanState(globalStore, closureStoreStack, callStack, pathConditions,
      counters, properties, assertionViolation, raisedException,
      inconsistencyCheckRequested, ((numChoices, chosen)) +: schedule)

  protected def make(procedure : ResourceUri,
                     locationUri : Option[ResourceUri], locationIndex : Int,
                     store : Store, result : Option[Value],
                     returnLocationUri : Option[ResourceUri],
                     returnLocationIndex : Int,
                     returnVariableUri : Option[ResourceUri]) : CallFrame =
    BasicCallFrame(procedure, locationUri, locationIndex, store, result,
      returnLocationUri, returnLocationIndex, returnVariableUri)

  protected def make(pathConditions : ISeq[Exp], counters : IMap[ResourceUri, Int]) =
    BasicKiasanState(globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties, assertionViolation, raisedException,
      inconsistencyCheckRequested, schedule)

  def self = this
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanStateWithHeap {
  def apply(heaps : ISeq[ISeq[HeapObject]]) : KiasanStateWithHeap =
    KiasanStateWithHeap(heaps, imapEmpty, ivectorEmpty, ivectorEmpty, ivectorEmpty,
      imapEmpty, imapEmpty, None, None, false, ivectorEmpty)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class KiasanStateWithHeap(
  heaps : ISeq[ISeq[HeapObject]],
  globalStore : State.Store,
  closureStoreStack : State.StoreStack,
  callStack : State.CallStack,
  pathConditions : ISeq[Exp],
  counters : IMap[ResourceUri, Int],
  properties : Property.ImmutableProperties,
  assertionViolation : Option[AssertionViolationInfo],
  raisedException : Option[BasicExceptionInfo],
  inconsistencyCheckRequested : Boolean,
  schedule : ISeq[(Int, Int)])
    extends State[KiasanStateWithHeap] with KiasanStatePart[KiasanStateWithHeap]
    with HeapPart[KiasanStateWithHeap] with DummyDuplicateHeap[KiasanStateWithHeap]
    with ScheduleRecordingState[KiasanStateWithHeap] {

  import State._
  import Heap._

  def make(hid : HeapId, objects : ISeq[HeapObject]) : KiasanStateWithHeap =
    KiasanStateWithHeap(heaps.updated(hid, objects), globalStore,
      closureStoreStack, callStack, pathConditions, counters, properties,
      assertionViolation, raisedException, inconsistencyCheckRequested,
      schedule)

  def init : KiasanStateWithHeap = {
    val newCounters : MMap[ResourceUri, Int] = mmapEmpty

    import KiasanState._

    Visitor.build({
      case v : Value =>
        updateCounters(newCounters, v)
        true
    })(this)

    make(pathConditions, newCounters.toMap)
  }

  def raiseException(exceptionValue : Value, li : LocationInfo) : KiasanStateWithHeap =
    KiasanStateWithHeap(heaps, globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties,
      assertionViolation, Some(BasicExceptionInfo(exceptionValue, ivector(li))),
      inconsistencyCheckRequested, schedule)

  def assertionViolation(avi : AssertionViolationInfo) : KiasanStateWithHeap =
    KiasanStateWithHeap(heaps, globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties, Some(avi), raisedException,
      inconsistencyCheckRequested, schedule)

  def make(globalStore : Store, closureStoreStack : StoreStack, callStack : CallStack,
           properties : Property.ImmutableProperties) : KiasanStateWithHeap =
    KiasanStateWithHeap(heaps, globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties, assertionViolation, raisedException,
      inconsistencyCheckRequested, schedule)

  def requestInconsistencyCheck(shouldCheck : Boolean) : KiasanStateWithHeap =
    KiasanStateWithHeap(heaps, globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties, assertionViolation,
      raisedException, shouldCheck, schedule)

  def recordSchedule(numChoices : Int, chosen : Int) : KiasanStateWithHeap =
    KiasanStateWithHeap(heaps, globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties, assertionViolation, raisedException,
      inconsistencyCheckRequested, ((numChoices, chosen)) +: schedule)

  protected def make(procedure : ResourceUri,
                     locationUri : Option[ResourceUri], locationIndex : Int,
                     store : Store, result : Option[Value],
                     returnLocationUri : Option[ResourceUri],
                     returnLocationIndex : Int,
                     returnVariableUri : Option[ResourceUri]) : CallFrame =
    BasicCallFrame(procedure, locationUri, locationIndex, store, result,
      returnLocationUri, returnLocationIndex, returnVariableUri)

  protected def make(pathConditions : ISeq[Exp], counters : IMap[ResourceUri, Int]) =
    KiasanStateWithHeap(heaps, globalStore, closureStoreStack, callStack,
      pathConditions, counters, properties, assertionViolation, raisedException,
      inconsistencyCheckRequested, schedule)

  def self = this
}