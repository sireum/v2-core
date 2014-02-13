/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.state

import org.sireum.pilar.ast._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object State {
  type Store = IMap[ResourceUri, Value]
  type StoreStack = ISeq[Store]
  type CallStack = ISeq[CallFrame]
  type ThreadId = Int

  def uri(x : NameUser) = if (x.hasResourceInfo) x.uri else x.name

  object NameAccess {
    implicit class StateWithNameUserAccess[S <: State[S]](val s : S) extends AnyVal {
      def apply(x : NameUser) = variable(x)
      def update(x : NameUser, value : Value) = variable(x, value)
      def variable(x : NameUser) : Value = s.variable(uri(x))
      def variable(x : NameUser, value : Value) : S = s.variable(uri(x), value)
      def variableOpt(x : NameUser) = {
        val varUri = uri(x)
        if (s.hasVariableValue(varUri)) Some(s.variable(varUri))
        else None
      }
    }
  }

  object UriAccess {
    implicit class StateWithUriAccess[S <: State[S]](val s : S) extends AnyVal {
      def apply(varUri : ResourceUri) : Value = s.variable(varUri)
      def apply(varUriPairs : (ResourceUri, Value)*) : S = {
        var s2 = s
        varUriPairs.foreach { p => s2 = s2.variable(p._1, p._2) }
        s2
      }
      def update(varUri : ResourceUri, v : Value) = s.variable(varUri, v)
    }
  }
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
  def returnVariableUris : ISeq[ResourceUri]

  def location(locationUri : Option[ResourceUri], locationIndex : Int) : CallFrame =
    make(procedure, locationUri, locationIndex, store, result,
      returnLocationUri, returnLocationIndex, returnVariableUris)

  def variable(varUri : ResourceUri) : Value = store(varUri)

  def hasVariableValue(varUri : ResourceUri) : Boolean = store.contains(varUri)

  def variable(varUri : ResourceUri, value : Value) : CallFrame =
    make(procedure, locationUri, locationIndex, store + (varUri -> value),
      result, returnLocationUri, returnLocationIndex, returnVariableUris)

  def result(v : Value) =
    make(procedure, locationUri, locationIndex, store, Some(v),
      returnLocationUri, returnLocationIndex, returnVariableUris)

  protected def make(procedure : ResourceUri,
                     locationUri : Option[ResourceUri], locationIndex : Int,
                     store : Store, result : Option[Value],
                     returnLocationUri : Option[ResourceUri], returnLocationIndex : Int,
                     returnVariableUris : ISeq[ResourceUri]) : CallFrame
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

  def globalStore : Store
  def closureStoreStack : StoreStack
  def callStack : CallStack

  def make(globalStore : Store,
           closureStoreStack : StoreStack,
           callStack : CallStack,
           properties : Property.ImmutableProperties) : Self

  def procedure = {
    require(!callStack.isEmpty)
    callStack.head.procedure
  }

  def locationUri = {
    require(!callStack.isEmpty)
    callStack.head.locationUri
  }

  def locationIndex = {
    require(!callStack.isEmpty)
    callStack.head.locationIndex
  }

  def variable(varUri : ResourceUri) : Value = {
    if (isGlobalVar(varUri))
      globalVar(varUri)
    else {
      if (!closureStoreStack.isEmpty)
        for (s <- closureStoreStack)
          if (s.contains(varUri))
            return s(varUri)
      callStack.head.variable(varUri)
    }
  }

  def globalVar(varUri : ResourceUri) : Value =
    globalStore(varUri)

  def isGlobalVar(varUri : ResourceUri) =
    org.sireum.pilar.symbol.H.isGlobalVar(varUri)

  def hasGlobalVarValue(varUri : ResourceUri) =
    globalStore.contains(varUri)

  def hasVariableValue(varUri : ResourceUri) : Boolean = {
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

  def globalVar(varUri : ResourceUri, value : Value) : Self =
    make(globalStore + (varUri -> value), closureStoreStack, callStack, properties)

  def variable(varUri : ResourceUri, value : Value) : Self = {
    if (isGlobalVar(varUri))
      globalVar(varUri, value)
    else {
      val i = closureStoreStack.indexWhere(_.contains(varUri))
      if (i >= 0) {
        val begin = closureStoreStack.slice(0, i)
        val str = closureStoreStack(i)
        val end = closureStoreStack.slice(i + 1, closureStoreStack.length)
        make(globalStore, (begin :+ (str + (varUri -> value))) ++ end,
          callStack, properties)
      } else if (!closureStoreStack.isEmpty) {
        val s = closureStoreStack.head
        make(globalStore, (s + (varUri -> value)) +: closureStoreStack.tail,
          callStack, properties)
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
                     returnLocationUri : Option[ResourceUri] = None,
                     returnLocationIndex : Int = -1,
                     returnVariableUris : ISeq[ResourceUri] = ivectorEmpty) : Self =
    make(globalStore, closureStoreStack,
      make(procedureUri, locationUri, locationIndex, store, None,
        returnLocationUri, returnLocationIndex, returnVariableUris) +: callStack,
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
                     returnVariableUris : ISeq[ResourceUri]) : CallFrame
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
trait ScheduleRecordingState[S <: ScheduleRecordingState[S]] {
  import State._

  def peekSchedule : Option[(Option[Any], Int, Int)]
  def popSchedule : S
  def recordSchedule(numChoices : Int, chosen : Int) : S
  def recordSchedule(source : Any, numChoices : Int, chosen : Int) : S
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object BasicCallFrame {
  def apply(procLoc : (ResourceUri, Option[ResourceUri], Int)) : BasicCallFrame =
    BasicCallFrame(procLoc._1, procLoc._2, procLoc._3, imapEmpty, None, None,
      -1, ivectorEmpty)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class BasicCallFrame(
    procedure : ResourceUri,
    locationUri : Option[ResourceUri],
    locationIndex : Int,
    store : State.Store,
    result : Option[Value],
    returnLocationUri : Option[ResourceUri],
    returnLocationIndex : Int,
    returnVariableUris : ISeq[ResourceUri]) extends CallFrame {

  import State._

  protected def make(procedure : ResourceUri,
                     locationUri : Option[ResourceUri], locationIndex : Int,
                     store : Store, result : Option[Value],
                     returnLocationUri : Option[ResourceUri],
                     returnLocationIndex : Int,
                     returnVariableUris : ISeq[ResourceUri]) : CallFrame =
    BasicCallFrame(procedure, locationUri, locationIndex, store, result,
      returnLocationUri, returnLocationIndex, returnVariableUris)
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
object BasicState {
  def apply() : BasicState =
    BasicState(imapEmpty, ivectorEmpty, ivectorEmpty, imapEmpty, None, None)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class BasicState(
  globalStore : State.Store,
  closureStoreStack : State.StoreStack,
  callStack : State.CallStack,
  properties : Property.ImmutableProperties,
  assertionViolation : Option[AssertionViolationInfo],
  raisedException : Option[BasicExceptionInfo])
    extends State[BasicState] {

  import State._

  def raiseException(exceptionValue : Value, li : LocationInfo) : BasicState =
    BasicState(globalStore, closureStoreStack, callStack, properties,
      assertionViolation, Some(BasicExceptionInfo(exceptionValue, ivector(li))))

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
                     returnVariableUris : ISeq[ResourceUri]) : CallFrame =
    BasicCallFrame(procedure, locationUri, locationIndex, store, result,
      returnLocationUri, returnLocationIndex, returnVariableUris)

  def self = this
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object Thread {
  def apply() : Thread = Thread(ivectorEmpty, ivectorEmpty, None, None)
  def apply(stacks : (State.StoreStack, State.CallStack)) : Thread =
    Thread(stacks._1, stacks._2, None, None)
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class Thread(
  closureStoreStack : State.StoreStack,
  callStack : State.CallStack,
  assertionViolation : Option[AssertionViolationInfo],
  raisedException : Option[BasicExceptionInfo])

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object BasicMultiThreadState {
  def apply() : BasicMultiThreadState =
    BasicMultiThreadState(imapEmpty, None,
      isortedMapEmpty[State.ThreadId, Thread], imapEmpty, ivectorEmpty)
  def apply(storeThreads : (State.Store, Option[State.ThreadId], ISortedMap[State.ThreadId, Thread])) : BasicMultiThreadState =
    BasicMultiThreadState(storeThreads._1, storeThreads._2,
      storeThreads._3, imapEmpty, ivectorEmpty)

}
/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
final case class BasicMultiThreadState(
  globalStore : State.Store,
  currentThreadId : Option[State.ThreadId],
  threads : ISortedMap[State.ThreadId, Thread],
  properties : Property.ImmutableProperties,
  schedule : ISeq[(Option[Any], Int, Int)])
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
        Some(BasicExceptionInfo(exceptionValue, ivector(li)))))
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
  
  def peekSchedule = None
  
  def popSchedule = this

  def recordSchedule(source : Any, numChoices : Int, chosen : Int) : BasicMultiThreadState =
    BasicMultiThreadState(globalStore, currentThreadId, threads,
      properties, ((Some(source), numChoices, chosen)) +: schedule)

  def recordSchedule(numChoices : Int, chosen : Int) : BasicMultiThreadState =
    BasicMultiThreadState(globalStore, currentThreadId, threads,
      properties, ((None, numChoices, chosen)) +: schedule)

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
                     returnVariableUris : ISeq[ResourceUri]) : CallFrame =
    BasicCallFrame(procedure, locationUri, locationIndex, store, result,
      returnLocationUri, returnLocationIndex, returnVariableUris)

  def self = this
}
