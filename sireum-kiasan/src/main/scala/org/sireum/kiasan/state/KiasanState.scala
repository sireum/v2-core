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

  object PathCondition {
    final implicit class KiasanStateWithPcAccess[S <: KiasanStatePart[S]](
        val s : S) extends AnyVal {

      @inline
      def +(v : Value) : S = s.addPathCondition(ValueExp(v))

      @inline
      def +(e : Exp) : S = s.addPathCondition(e)

      @inline
      def +?(e : Exp) : S = s.addPathCondition(e).requestInconsistencyCheck()

      @inline
      def ++(es : Exp*) : S = es.foldLeft(s)(_ + _)
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
    make(pathConditions :+ e, counters)

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
      imapEmpty, None, None, false, ivectorEmpty, ivectorEmpty, 0)
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
  schedule : ISeq[(Option[Any], Int, Int)],
  schedulePrefix : ISeq[(Option[Any], Int, Int)],
  currentScheduleIndex : Int)
    extends State[BasicKiasanState] with KiasanStatePart[BasicKiasanState]
    with ScheduleReplayState[BasicKiasanState]
    with BasicCallFrameState[BasicKiasanState] {

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
    copy(raisedException = Some(BasicExceptionInfo(exceptionValue, ivector(li))))

  def assertionViolation(avi : AssertionViolationInfo) : BasicKiasanState =
    copy(assertionViolation = Some(avi))

  def make(globalStore : Store, closureStoreStack : StoreStack, callStack : CallStack,
           properties : Property.ImmutableProperties) : BasicKiasanState =
    copy(globalStore = globalStore, closureStoreStack = closureStoreStack,
      callStack = callStack, properties = properties)

  def requestInconsistencyCheck(shouldCheck : Boolean) : BasicKiasanState =
    copy(inconsistencyCheckRequested = shouldCheck)

  def popSchedule =
    if (currentScheduleIndex < schedulePrefix.length)
      copy(currentScheduleIndex = currentScheduleIndex + 1)
    else this

  def recordSchedule(numChoices : Int, chosen : Int) : BasicKiasanState =
    copy(schedule = ((None, numChoices, chosen)) +: schedule)

  def recordSchedule(source : Any, numChoices : Int, chosen : Int) : BasicKiasanState =
    copy(schedule = ((Some(source), numChoices, chosen)) +: schedule)

  protected def make(pathConditions : ISeq[Exp], counters : IMap[ResourceUri, Int]) =
    copy(pathConditions = pathConditions, counters = counters)

  def self = this
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanStateWithHeap {
  def apply(heaps : ISeq[ISeq[HeapObject]]) : KiasanStateWithHeap =
    KiasanStateWithHeap(heaps, imapEmpty, ivectorEmpty, ivectorEmpty, ivectorEmpty,
      imapEmpty, imapEmpty, None, None, false, ivectorEmpty, ivectorEmpty, 0)
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
  schedule : ISeq[(Option[Any], Int, Int)],
  schedulePrefix : ISeq[(Option[Any], Int, Int)],
  currentScheduleIndex : Int)
    extends State[KiasanStateWithHeap]
    with KiasanStatePart[KiasanStateWithHeap]
    with HeapPart[KiasanStateWithHeap]
    with ScheduleReplayState[KiasanStateWithHeap]
    with BasicCallFrameState[KiasanStateWithHeap] {

  import State._
  import Heap._

  def make(hid : HeapId, objects : ISeq[HeapObject]) : KiasanStateWithHeap =
    copy(heaps = heaps.updated(hid, objects))

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
    copy(raisedException = Some(BasicExceptionInfo(exceptionValue, ivector(li))))

  def assertionViolation(avi : AssertionViolationInfo) : KiasanStateWithHeap =
    copy(assertionViolation = Some(avi))

  def make(globalStore : Store, closureStoreStack : StoreStack, callStack : CallStack,
           properties : Property.ImmutableProperties) : KiasanStateWithHeap =
    copy(globalStore = globalStore, closureStoreStack = closureStoreStack,
      callStack = callStack, properties = properties)

  def requestInconsistencyCheck(shouldCheck : Boolean) : KiasanStateWithHeap =
    copy(inconsistencyCheckRequested = shouldCheck)

  def popSchedule =
    if (currentScheduleIndex < schedulePrefix.length)
      copy(currentScheduleIndex = currentScheduleIndex + 1)
    else this

  def recordSchedule(source : Any, numChoices : Int, chosen : Int) : KiasanStateWithHeap =
    copy(schedule = ((Some(source), numChoices, chosen)) +: schedule)

  def recordSchedule(numChoices : Int, chosen : Int) : KiasanStateWithHeap =
    copy(schedule = ((None, numChoices, chosen)) +: schedule)

  protected def make(pathConditions : ISeq[Exp], counters : IMap[ResourceUri, Int]) =
    copy(pathConditions = pathConditions, counters = counters)

  def self = this
}