/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.konkrit.extension.composite.value

import org.sireum.extension._
import org.sireum.extension.composite._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.util.math._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritValCompositeExtension {
  final val HeapIdKey = getClass.getName + ".heapId"

  private type V = Value
  private type RV = ReferenceValue
  private type H[S <: H[S]] = Heap[S]
  private type SH[S <: SH[S]] = State[S] with Heap[S]

  import language.implicitConversions
  @inline private implicit def t2tl[T](t : T) = ivector(t)

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  object Record {
    final val FIELDS_KEY = ".fields"

    @inline
    def fieldsKey(implicit rv : RV) = (rv, FIELDS_KEY)

    /**
     * @author <a href="mailto:robby@k-state.edu">Robby</a>
     */
    object Field {
      /**
       * @author <a href="mailto:robby@k-state.edu">Robby</a>
       */
      object Access {
        final implicit class StateWithKonkritRecordAccess[S <: H[S]](val s : S)
            extends AnyVal {

          @inline
          def fields(implicit rv : RV) : ILinkedMap[ResourceUri, V] =
            Heap.ElementAccess.H(s)(fieldsKey)

          @inline
          def update(f : ResourceUri, v : V)(implicit rv : RV) : S = {
            Heap.ElementAccess.H(s)(fieldsKey) = s.fields + (f -> v)
          }
        }
      }

      /**
       * @author <a href="mailto:robby@k-state.edu">Robby</a>
       */
      final implicit class StateKonkritFieldAccess[S <: H[S]](val s : S)
          extends AnyVal {

        @inline
        def apply(f : NameUser)(
          implicit rv : RV,
          defVal : (S, ResourceUri) => ISeq[(S, V)]) : ISeq[(S, V)] = {
          import Access._

          val fUri = State.uri(f)

          s.fields.get(fUri) match {
            case Some(v) => (s, v)
            case _ =>
              for {
                (s2, dflt) <- defVal(s, fUri)
              } yield (StateWithKonkritRecordAccess(s2)(fUri) = dflt, dflt)
          }
        }

        @inline
        def update(f : NameUser, v : V)(implicit rv : RV) : (S, RV) = {
          val (s2, rv2) = s.duplicate(rv)
          ((Access.StateWithKonkritRecordAccess(s2)(State.uri(f)) = v)(rv2), rv2)
        }
      }
    }

    @inline
    def create[S <: H[S]](
      hid : Int, s : S, initPairs : (ResourceUri, V)*) : (S, RV) =
      s.newObject(hid,
        (FIELDS_KEY, ilinkedMap(initPairs : _*)))

    @inline
    def is[S <: H[S]](s : S)(implicit rv : RV) =
      Heap.ElementAccess.H(s) ? fieldsKey

    @inline
    def fieldLookup[S <: H[S]](implicit hid : Int,
                               defVal : (S, ResourceUri) => ISeq[(S, V)]) : //
                               (S, V, NameUser) --> ISeq[(S, V)] = {
      case (s, rv @ Heap.RV(rvhid, _), f) if rvhid == hid =>
        implicit val irv = rv
        Field.StateKonkritFieldAccess(s)(f)
    }

    @inline
    def fieldUpdate[S <: SH[S]](implicit hid : Int) : //
    (S, NameUser, NameUser, V) --> ISeq[S] = {
      case (s, x, f, v) if Heap.isVarHid(s, x, hid) =>
        import State.NameAccess._
        implicit val rv = s(x).asInstanceOf[RV]
        val (s2, rv2) = (Field.StateKonkritFieldAccess(s)(f) = v)
        s2(x) = rv2
    }
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  object Array {
    final val NATIVE_ARRAY_KEY = ".nativearray"
    final val MIN_INDEX_KEY = ".min"

    @inline
    def nativeArrayKey(implicit rv : RV) = (rv, NATIVE_ARRAY_KEY)

    @inline
    def minIndexKey(implicit rv : RV) = (rv, MIN_INDEX_KEY)

    @inline
    private def createHelper[S <: H[S]](hid : Int, s : S, min : Integer,
                                        elements : Array[V]) =
      s.newObject(hid,
        (NATIVE_ARRAY_KEY, elements),
        (MIN_INDEX_KEY, min))

    @inline
    def create[S <: H[S]](hid : Int, s : S, min : Integer, elements : V*) = {
      val len = elements.length
      val a = new Array[V](len)
      for (i <- 0 until len)
        a(i) = elements(i)

      createHelper(hid, s, min, a)
    }

    @inline
    def create[S <: H[S]](s : S, min : Integer, length : Int,
                          defaultValue : V)(implicit hid : Int) = {
      val a = new Array[Value](length)
      for (i <- 0 until length)
        a(i) = defaultValue

      createHelper(hid, s, min, a)
    }

    @inline
    def isKonkrit[S <: H[S]](s : S, v : V) =
      v match {
        case rv : RV =>
          implicit val irv = rv
          Heap.ElementAccess.H(s) ? nativeArrayKey
        case _ => false
      }

    @inline
    def is[S <: H[S]](s : S)(implicit rv : RV) =
      Heap.ElementAccess.H(s) ? nativeArrayKey

    /**
     * @author <a href="mailto:robby@k-state.edu">Robby</a>
     */
    object Element {
      /**
       * @author <a href="mailto:robby@k-state.edu">Robby</a>
       */
      object Access {
        final implicit class StateWithKonkritArrayAccess[S <: H[S]](val s : S)
            extends AnyVal {

          @inline
          def nativeArray(implicit rv : RV) : Array[V] =
            Heap.ElementAccess.H(s)(nativeArrayKey)

          @inline
          def minIndex(implicit rv : RV) : Integer =
            Heap.ElementAccess.H(s)(minIndexKey)

          @inline
          def length(implicit rv : RV) : Integer = SireumNumber(nativeArray.length)

          @inline
          def maxIndex(implicit rv : RV) : Integer = minIndex + length - 1

          @inline
          def update(rvi : (RV, Integer), v : V) = {
            implicit val irv = rvi._1
            val i = rvi._2
            val a = s.nativeArray
            val min = s.minIndex
            assert(min <= i && i <= s.maxIndex)
            val newA = a.clone
            newA((i - min).toInt) = v
            (Heap.ElementAccess.H(s)(nativeArrayKey) = newA, irv)
          }
        }
      }

      implicit class StateKonkritElementAccess[S <: H[S]](
          val s : S) extends AnyVal {

        @inline
        def apply(i : Integer)(implicit rv : RV) : (S, V) = {
          import Access._
          val a = s.nativeArray
          val min = s.minIndex
          val max = s.maxIndex
          assert(min <= i && i <= max)
          val index = (i - min).toInt
          (s, a(index))
        }

        @inline
        def update(i : Integer, v : V)(implicit rv : RV) : (S, RV) = {
          val (s2, rv2) = s.duplicate(rv)
          Access.StateWithKonkritArrayAccess(s2)((rv2, i)) = v
        }
      }
    }

    @inline
    def elementLookup[S <: H[S]](implicit hid : Int) : //
    (S, V, V) --> ISeq[(S, V)] = {
      case (s, rv @ Heap.RV(rvhid, _), i : IsInteger) if rvhid == hid =>
        implicit val irv = rv
        Element.StateKonkritElementAccess(s)(i.asInteger)
    }

    @inline
    def elementUpdate[S <: SH[S]](implicit hid : Int) : //
    (S, NameUser, V, V) --> ISeq[S] = {
      case (s, x, i : IsInteger, v) if Heap.isVarHid(s, x, hid) =>
        import State.NameAccess._
        implicit val rv = s(x).asInstanceOf[RV]
        val (s2, rv2) =
          (Element.StateKonkritElementAccess(s)(i.asInteger) = v)
        s2(x) = rv2
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KonkritValCompositeExtension[S <: State[S] with Heap[S]]
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  type V = Value
  type R = ISeq[(S, Value)]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]
  type RV = Heap.RV

  def config : EvaluatorConfiguration[S, V, R, C, SR]

  val uriPath = UriUtil.classUri(this)

  val sec = config.semanticsExtension

  @inline
  implicit def defValue(s : S, uri : ResourceUri) = sec.defaultValue(s, uri)

  implicit val hid = {
    import EvaluatorHeapConfiguration._
    config.heapConfig.heapId(KonkritValCompositeExtension.HeapIdKey)
  }

  @FieldLookup
  val fieldLookup = KonkritValCompositeExtension.Record.fieldLookup

  @FieldUpdateVar
  val fieldUpdateVar = KonkritValCompositeExtension.Record.fieldUpdate

  @ArrayLookup
  val arrayLookup = KonkritValCompositeExtension.Array.elementLookup

  @ArrayUpdateVar
  val arrayUpdateVar = KonkritValCompositeExtension.Array.elementUpdate
}
