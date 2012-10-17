/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.state

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object State {
  type Store = IMap[ResourceUri, Value]
  type StoreStack = ISeq[Store]
  type CallStack = ISeq[CallFrame]
  type ThreadId = Int
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait CallFrame {
  import State._

  def procedure : ResourceUri
  def locationUri : Option[ResourceUri]
  def locationIndex : Int
  def store : Store
  def result : Option[Value]
  def returnLocationUri : Option[ResourceUri]
  def returnLocationIndex : Int
  def returnVariableUri : Option[ResourceUri]

  def location(locationUri : Option[ResourceUri], locationIndex : Int) : CallFrame =
    make(procedure, locationUri, locationIndex, store, result,
      returnLocationUri, returnLocationIndex, returnVariableUri)

  def variable(varUri : ResourceUri) : Value = store(varUri)

  def hasVariableValue(varUri : ResourceUri) : Boolean = store.contains(varUri)

  def variable(varUri : ResourceUri, value : Value) : CallFrame =
    make(procedure, locationUri, locationIndex, store + (varUri -> value),
      result, returnLocationUri, returnLocationIndex, returnVariableUri)

  def result(v : Value) =
    make(procedure, locationUri, locationIndex, store, Some(v),
      returnLocationUri, returnLocationIndex, returnVariableUri)

  protected def make(procedure : ResourceUri,
                     locationUri : Option[ResourceUri], locationIndex : Int,
                     store : Store, result : Option[Value],
                     returnLocationUri : Option[ResourceUri], returnLocationIndex : Int,
                     returnVariableUri : Option[ResourceUri]) : CallFrame
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait LocationInfo {
  def locUri : Option[ResourceUri]
  def locIndex : Int
  def transIndex : Int
  def commandIndex : Int
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ProcInfo {
  def procUri : ResourceUri
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AssertionViolationInfo extends LocationInfo {
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ExceptionInfo {
  def exceptionValue : Value
  def locationInfos : ISeq[LocationInfo]
  def addLocationInfo(li : LocationInfo) : ExceptionInfo
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AssertionViolationStatePart[Self <: AssertionViolationStatePart[Self]] {
  def assertionViolation : Option[AssertionViolationInfo]
  def assertionViolation(avi : AssertionViolationInfo) : Self
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait RaisedExceptionStatePart[Self <: RaisedExceptionStatePart[Self]] {
  def raisedException : Option[ExceptionInfo]
  def raiseException(exceptionValue : Value, li : LocationInfo) : Self
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait State[Self <: State[Self]]
    extends Immutable
    with SelfType[Self]
    with ImmutablePropertyProvider[Self]
    with AssertionViolationStatePart[Self]
    with RaisedExceptionStatePart[Self] {
  import State._

  def apply(varUri : ResourceUri) : Value = variable(varUri)
  def apply(varUriPairs : (ResourceUri, Value)*) : Self = {
    var s : Self = self
    varUriPairs.foreach { p => s = s.variable(p._1, p._2) }
    s
  }

  def globalStore : Store
  def closureStoreStack : StoreStack
  def callStack : CallStack

  def make(globalStore : Store,
           closureStoreStack : StoreStack,
           callStack : CallStack,
           properties : Property.ImmutableProperties) : Self

  def variable(varUri : ResourceUri) : Value = {
    import org.sireum.pilar.symbol.H._

    if (isGlobalVar(varUri))
      globalStore(varUri)
    else {
      if (!closureStoreStack.isEmpty)
        for (s <- closureStoreStack)
          if (s.contains(varUri))
            return s(varUri)
      callStack.head.variable(varUri)
    }
  }

  def hasVariableValue(varUri : ResourceUri) : Boolean = {
    import org.sireum.pilar.symbol.H._

    if (isGlobalVar(varUri))
      globalStore.contains(varUri)
    else {
      if (!closureStoreStack.isEmpty)
        for (s <- closureStoreStack)
          if (s.contains(varUri))
            return true
      callStack.head.hasVariableValue(varUri)
    }
  }

  def variable(varUri : ResourceUri, value : Value) : Self = {
    import org.sireum.pilar.symbol.H._

    if (isGlobalVar(varUri))
      make(globalStore + (varUri -> value), closureStoreStack, callStack, properties)
    else {
      if (!closureStoreStack.isEmpty) {
        val s = closureStoreStack.head
        make(globalStore, (s + (varUri -> value)) +: closureStoreStack.tail,
          callStack.head.variable(varUri, value) +: callStack.tail,
          properties)
      } else
        make(globalStore, closureStoreStack,
          callStack.head.variable(varUri, value) +: callStack.tail,
          properties)
    }
  }

  def location(locationUri : Option[ResourceUri], locationIndex : Int) : Self = {
    val cf = callStack.head.location(locationUri, locationIndex)
    make(globalStore, closureStoreStack, cf +: callStack.tail, properties)
  }

  def enterClosure(store : Store = imapEmpty) : Self =
    make(globalStore, store +: closureStoreStack, callStack, properties)

  def exitClosure : Self =
    make(globalStore, closureStoreStack.tail, callStack, properties)

  def enterCallFrame(procedureUri : ResourceUri, locationUri : Option[ResourceUri],
                     locationIndex : Int, store : Store = imapEmpty,
                     returnLocationUri : Option[ResourceUri],
                     returnLocationIndex : Int = -1,
                     returnVariableUri : Option[ResourceUri] = None) : Self =
    make(globalStore, closureStoreStack,
      make(procedureUri, locationUri, locationIndex, store, None,
        returnLocationUri, returnLocationIndex, returnVariableUri) +: callStack,
      properties)

  def exitCallFrame : (Self, CallFrame) =
    (make(globalStore, closureStoreStack, callStack.tail, properties), callStack.head)

  def make(properties : Property.ImmutableProperties) : Self =
    make(globalStore, closureStoreStack, callStack, properties)

  protected def make(procedure : ResourceUri,
                     locationUri : Option[ResourceUri], locationIndex : Int,
                     store : Store, result : Option[Value],
                     returnLocationUri : Option[ResourceUri],
                     returnLocationIndex : Int,
                     returnVariableUri : Option[ResourceUri]) : CallFrame
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait MultiThreadState[Self <: MultiThreadState[Self]] {
  import State._

  def threadIds : ISeq[ThreadId]
  def threadClosureStack(tid : ThreadId) : StoreStack
  def threadCallStack(tid : ThreadId) : CallStack
  def make(tid : Option[ThreadId]) : Self
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ScheduleRecordingState[Self <: ScheduleRecordingState[Self]] {
  import State._

  def recordSchedule(numChoices : Int, chosen : Int) : Self
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class BasicCallFrame(
    procedure : ResourceUri,
    locationUri : Option[ResourceUri],
    locationIndex : Int,
    store : State.Store = imapEmpty,
    result : Option[Value] = None,
    returnLocationUri : Option[ResourceUri] = None,
    returnLocationIndex : Int = -1,
    returnVariableUri : Option[ResourceUri] = None) extends CallFrame {

  import State._

  protected def make(procedure : ResourceUri,
                     locationUri : Option[ResourceUri], locationIndex : Int,
                     store : Store, result : Option[Value],
                     returnLocationUri : Option[ResourceUri],
                     returnLocationIndex : Int,
                     returnVariableUri : Option[ResourceUri]) : CallFrame =
    BasicCallFrame(procedure, locationUri, locationIndex, store, result,
      returnLocationUri, returnLocationIndex, returnVariableUri)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class BasicExceptionInfo(
  exceptionValue : Value, locationInfos : ISeq[LocationInfo])
    extends ExceptionInfo {
  def addLocationInfo(li : LocationInfo) =
    BasicExceptionInfo(exceptionValue, li +: locationInfos)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class BasicState(
  globalStore : State.Store = imapEmpty,
  closureStoreStack : State.StoreStack = ilistEmpty,
  callStack : State.CallStack = ilistEmpty,
  properties : Property.ImmutableProperties = imapEmpty,
  assertionViolation : Option[AssertionViolationInfo] = None,
  raisedException : Option[BasicExceptionInfo] = None)
    extends State[BasicState] {

  import State._

  def raiseException(exceptionValue : Value, li : LocationInfo) : BasicState =
    BasicState(globalStore, closureStoreStack, callStack, properties,
      assertionViolation, Some(BasicExceptionInfo(exceptionValue, ilist(li))))

  def assertionViolation(avi : AssertionViolationInfo) : BasicState =
    BasicState(globalStore, closureStoreStack, callStack, properties,
      Some(avi), raisedException)

  def make(globalStore : Store, closureStoreStack : StoreStack, callStack : CallStack,
           properties : Property.ImmutableProperties) : BasicState =
    BasicState(globalStore, closureStoreStack, callStack, properties,
      assertionViolation, raisedException)

  protected def make(procedure : ResourceUri,
                     locationUri : Option[ResourceUri], locationIndex : Int,
                     store : Store, result : Option[Value],
                     returnLocationUri : Option[ResourceUri],
                     returnLocationIndex : Int,
                     returnVariableUri : Option[ResourceUri]) : CallFrame =
    BasicCallFrame(procedure, locationUri, locationIndex, store, result,
      returnVariableUri, returnLocationIndex, returnVariableUri)

  def self = this
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class Thread(
  closureStoreStack : State.StoreStack = ilistEmpty,
  callStack : State.CallStack = ilistEmpty,
  assertionViolation : Option[AssertionViolationInfo] = None,
  raisedException : Option[BasicExceptionInfo] = None)

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class BasicMultiThreadState(
  globalStore : State.Store = imapEmpty,
  currentThreadId : Option[State.ThreadId],
  threads : ISortedMap[State.ThreadId, Thread] = isortedMapEmpty[State.ThreadId, Thread],
  properties : Property.ImmutableProperties = imapEmpty,
  schedule : ISeq[(Int, Int)] = ilistEmpty)
    extends State[BasicMultiThreadState]
    with MultiThreadState[BasicMultiThreadState]
    with ScheduleRecordingState[BasicMultiThreadState] {

  import State._

  @inline
  private def thread : Thread = threads(currentThreadId.get)

  def threadIds = threads.keySet.toList
  def closureStoreStack : StoreStack = thread.closureStoreStack
  def callStack : CallStack = thread.callStack

  def threadClosureStack(tid : State.ThreadId) : State.StoreStack =
    threads(tid).closureStoreStack

  def threadCallStack(tid : State.ThreadId) : State.CallStack =
    threads(tid).callStack

  def raiseException(exceptionValue : Value, li : LocationInfo) : BasicMultiThreadState = {
    val t = thread
    val tid = currentThreadId.get
    val newThreads = threads + (tid ->
      Thread(t.closureStoreStack, t.callStack,
        t.assertionViolation,
        Some(BasicExceptionInfo(exceptionValue, ilist(li)))))
    BasicMultiThreadState(globalStore, currentThreadId, newThreads, properties, schedule)
  }

  def raisedException : Option[BasicExceptionInfo] = thread.raisedException

  def assertionViolation : Option[AssertionViolationInfo] = thread.assertionViolation

  def assertionViolation(avi : AssertionViolationInfo) : BasicMultiThreadState = {
    val t = thread
    val tid = currentThreadId.get
    val newThreads = threads + (tid ->
      Thread(t.closureStoreStack, t.callStack, Some(avi), t.raisedException))
    BasicMultiThreadState(globalStore, currentThreadId, newThreads, properties, schedule)
  }

  def recordSchedule(numChoices : Int, chosen : Int) : BasicMultiThreadState =
    BasicMultiThreadState(globalStore, currentThreadId, threads,
      properties, ((numChoices, chosen)) +: schedule)

  def make(globalStore : Store, closureStoreStack : StoreStack, callStack : CallStack,
           properties : Property.ImmutableProperties) : BasicMultiThreadState = {
    val t = thread
    val tid = currentThreadId.get
    val newThreads = threads + (tid ->
      Thread(closureStoreStack, callStack, t.assertionViolation, t.raisedException))
    BasicMultiThreadState(globalStore, currentThreadId, newThreads, properties, schedule)
  }

  def make(tid : Option[ThreadId]) : BasicMultiThreadState =
    BasicMultiThreadState(globalStore, tid, threads, properties, schedule)

  protected def make(procedure : ResourceUri,
                     locationUri : Option[ResourceUri], locationIndex : Int,
                     store : Store, result : Option[Value],
                     returnLocationUri : Option[ResourceUri],
                     returnLocationIndex : Int,
                     returnVariableUri : Option[ResourceUri]) : CallFrame =
    BasicCallFrame(procedure, locationUri, locationIndex, store, result,
      returnVariableUri, returnLocationIndex, returnVariableUri)

  def self = this
}
