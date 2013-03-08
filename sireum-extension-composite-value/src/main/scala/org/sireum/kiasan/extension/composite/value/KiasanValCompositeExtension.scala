/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.kiasan.extension.composite.value

import org.sireum.extension._
import org.sireum.extension.composite._
import org.sireum.kiasan.state._
import org.sireum.pilar.ast._
import org.sireum.pilar.eval._
import org.sireum.pilar.state._
import org.sireum.util._
import org.sireum.util.math._
import org.sireum.konkrit.extension.composite.value.KonkritValCompositeExtension
import org.sireum.konkrit.extension.KonkritIntegerExtension
import org.sireum.kiasan.extension.KiasanSemanticsExtensionConsumer

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KiasanValCompositeExtension {
  final val HeapIdKey = getClass.getName + ".heapId"
  final val BaseHeapIdKey = getClass.getName + ".baseHeapId"

  private type V = Value
  private type RV = ReferenceValue
  private type H[S <: H[S]] = Heap[S]
  private type HK[S <: HK[S]] = Heap[S] with KiasanStatePart[S]
  private type SH[S <: SH[S]] = State[S] with Heap[S]
  private type SHK[S <: SHK[S]] = State[S] with Heap[S] with KiasanStatePart[S]

  final val BASE_KEY = ".base"

  @inline
  def baseKey(implicit rv : RV) = (rv, BASE_KEY)

  @inline
  def base[S <: H[S]](s : S)(implicit rv : RV) : RV =
    Heap.ElementAccess.H(s)(baseKey)

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
        final implicit class StateWithKiasanRecordAccess[S <: H[S]](val s : S)
            extends AnyVal {
          @inline
          def fields(implicit rv : RV) : ILinkedMap[ResourceUri, V] =
            Heap.ElementAccess.H(s)(fieldsKey)

          @inline
          def apply(f : ResourceUri)(implicit rv : RV) : Option[V] =
            fields.get(f) match {
              case r @ Some(v) => r
              case _           => baseFields.get(f)
            }

          @inline
          def base(implicit rv : RV) : RV = KiasanValCompositeExtension.base(s)

          @inline
          def baseFields(implicit rv : RV) : ILinkedMap[ResourceUri, V] =
            fields(base)

          @inline
          def fieldMapping(implicit rv : RV) = {
            val flds = fields
            flds ++ (baseFields -- flds.keys)
          }

          @inline
          def update(f : ResourceUri, v : V)(implicit rv : RV) : S = {
            Heap.ElementAccess.H(s)(fieldsKey) = s.fields + (f -> v)
          }

          @inline
          def lazyInit(f : ResourceUri, v : V)(implicit rv : RV) : S =
            (s(f) = v)(base)
        }
      }

      /**
       * @author <a href="mailto:robby@k-state.edu">Robby</a>
       */
      final implicit class StateWithKiasanFieldAccess[S <: H[S]](val s : S)
          extends AnyVal {

        def apply(f : NameUser)(
          implicit rv : RV, fresh : (S, ResourceUri) --> (S, V),
          rep : (S, Value) --> Value, tp : TypeProvider) = {
          val fieldUri = State.uri(f)

          Access.StateWithKiasanRecordAccess(s)(fieldUri) match {
            case Some(v) => (s, rep(s, v))
            case _ =>
              val typeUri = tp.typeUri(fieldUri)
              val (s2, v) = fresh(s, typeUri)
              (Access.StateWithKiasanRecordAccess(s2).lazyInit(fieldUri, v), v)
          }
        }

        def update(f : NameUser, v : V)(
          implicit rv : RV, fresh : (S, ResourceUri) --> (S, V),
          rep : (S, Value) --> Value, tp : TypeProvider) = {
          val (s2, _) = s(f)
          val (s3, rv2) = s2.duplicate(rv)
          assert(rep(s3, v) eq v)
          val s4 = (Access.StateWithKiasanRecordAccess(s3)(State.uri(f)) = v)(rv2)
          (s4, rv2)
        }
      }
    }

    @inline
    def create[S <: H[S]](khid : Int, bkhid : Int, s : S) = {
      val (s2, rv) = s.newObject(khid)
      val (s3, rvBase) = s2.newObject(bkhid)
      import Heap.ElementAccess._
      val s4 = (((
        s3(fieldsKey(rv)) = ilinkedMapEmpty[ResourceUri, V])
        (baseKey(rv)) = rvBase)
        (fieldsKey(rvBase)) = ilinkedMapEmpty[ResourceUri, V])
      (s4, rv)
    }

    @inline
    def is[S <: H[S]](s : S)(implicit rv : RV) = {
      import Heap.ElementAccess._
      s ? fieldsKey && s ? baseKey
    }

    @inline
    def fieldLookup[S <: H[S]](
      implicit khid : Int, fresh : (S, ResourceUri) --> (S, V),
      rep : (S, Value) --> Value, tp : TypeProvider) : // 
      (S, V, NameUser) --> ISeq[(S, V)] = {
      case (s, rv @ Heap.RV(hid, _), f) if hid == khid =>
        implicit val irv = rv
        Field.StateWithKiasanFieldAccess(s)(f)
    }

    @inline
    def fieldUpdate[S <: SH[S]](
      implicit khid : Int, fresh : (S, ResourceUri) --> (S, V),
      rep : (S, Value) --> Value, tp : TypeProvider) : //
      (S, NameUser, NameUser, V) --> ISeq[S] = {
      case (s, x, f, v) if Heap.isVarHid(s, x, khid) =>
        import State.NameAccess._
        implicit val rv = s(x).asInstanceOf[RV]
        val (s2, rv2) = (Field.StateWithKiasanFieldAccess(s)(f) = v)
        s2(x) = rv2
    }
  }

  /**
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  object Array {
    final val CONCRETE_ARRAY_KEY = ".concrete"
    final val SYMBOLIC_ARRAY_KEY = ".symbolic"
    final val MIN_INDEX_KEY = ".min"
    final val MAX_INDEX_KEY = ".max"
    final val LENGTH_KEY = ".length"
    final val ELEM_TYPE_KEY = ".elemtype"

    @inline
    def concreteArrayKey(implicit rv : RV) = (rv, CONCRETE_ARRAY_KEY)

    @inline
    def symbolicArrayKey(implicit rv : RV) = (rv, SYMBOLIC_ARRAY_KEY)

    @inline
    def minIndexKey(implicit rv : RV) = (rv, MIN_INDEX_KEY)

    @inline
    def maxIndexKey(implicit rv : RV) = (rv, MAX_INDEX_KEY)

    @inline
    def lengthKey(implicit rv : RV) = (rv, LENGTH_KEY)

    @inline
    def elementTypeKey(implicit rv : RV) = (rv, ELEM_TYPE_KEY)

    @inline
    implicit def i2e(i : Integer) = ValueExp(KonkritIntegerExtension.integer2v(i))

    @inline
    implicit def i2e(i : BigInt) = ValueExp(KonkritIntegerExtension.bigint2v(i))

    @inline
    implicit def v2e(v : V) = ValueExp(v)

    /**
     * @author <a href="mailto:robby@k-state.edu">Robby</a>
     */
    object Element {

      /**
       * @author <a href="mailto:robby@k-state.edu">Robby</a>
       */
      object Access {
        final implicit class StateWithKiasanArrayAccess[S <: HK[S]](val s : S)
            extends AnyVal {

          @inline
          def base(implicit rv : RV) : RV = KiasanValCompositeExtension.base(s)

          @inline
          def concreteArray(implicit rv : RV) : ILinkedMap[Integer, V] =
            Heap.ElementAccess.H(s)(concreteArrayKey)

          @inline
          def symbolicArray(implicit rv : RV) : ILinkedMap[V, V] =
            Heap.ElementAccess.H(s)(symbolicArrayKey)

          @inline
          def minIndex(implicit rv : RV) : V =
            Heap.ElementAccess.H(s)(minIndexKey(base))

          @inline
          def maxIndex(implicit rv : RV) : V =
            Heap.ElementAccess.H(s)(maxIndexKey(base))

          @inline
          def length(implicit rv : RV) : V =
            Heap.ElementAccess.H(s)(lengthKey(base))

          @inline
          def elementType(implicit rv : RV) : ResourceUri =
            Heap.ElementAccess.H(s)(elementTypeKey(base))

          @inline
          def baseConcreteArray(implicit rv : RV) : ILinkedMap[Integer, V] =
            concreteArray(base)

          @inline
          def baseSymbolicArray(implicit rv : RV) : ILinkedMap[V, V] =
            symbolicArray(base)

          @inline
          def element(i : Integer)(implicit rv : RV) : Option[V] = {
            concreteArray.get(i) match {
              case r @ Some(v) => r
              case _           => baseConcreteArray.get(i)
            }
          }

          def normHelper(s : S)(implicit rv : RV, rep : (S, V) --> V) = {
            var ca = s.concreteArray
            var sa = s.symbolicArray
            var changed = false
            for ((i, v) <- sa) {
              val repI = rep(s, i)
              if (repI != i) {
                changed = true
                repI match {
                  case repI : IsInteger =>
                    sa = sa - i
                    ca = ca + (repI.asInteger -> v)
                  case _ =>
                    sa = sa - i
                    sa = sa + (repI -> v)
                }
              }
            }
            if (changed) {
              Heap.ElementAccess.H(
                Heap.ElementAccess.H(s)(concreteArrayKey) = ca)(
                  symbolicArrayKey) = sa
            } else s
          }

          @inline
          def norm(implicit rv : RV, rep : (S, V) --> V) : S = {
            normHelper(normHelper(s))(s.base, rep)
          }

          @inline
          def elementHelper(i : V)(implicit rv : RV) : Option[V] = {
            symbolicArray.get(i) match {
              case r : Some[_] => r
              case _           => baseSymbolicArray.get(i)
            }
          }

          @inline
          def element(i : V)(implicit rv : RV, rep : (S, V) --> V) : (S, Option[V]) = {
            assert(rep(s, i) eq i)
            elementHelper(i) match {
              case r @ Some(v) => (s, Some(rep(s, v)))
              case _ =>
                val s2 = norm(rv, rep)
                (s2, s2.elementHelper(i).map { rep(s, _) })
            }
          }

          @inline
          def concreteMapping(implicit rv : RV) = {
            val ca = concreteArray
            if (Heap.ElementAccess.H(s) ? baseKey)
              ca ++ (baseConcreteArray -- ca.keys)
            else ca
          }

          @inline
          def symbolicMapping(implicit rv : RV) = {
            val sa = symbolicArray
            if (Heap.ElementAccess.H(s) ? baseKey)
              sa ++ (baseSymbolicArray -- sa.keys)
            else sa
          }

          @inline
          def -(i : V)(implicit rv : RV) : S = {
            Heap.ElementAccess.H(
              (Heap.ElementAccess.H(s)(symbolicArrayKey) = s.symbolicArray - i))(
                symbolicArrayKey(base)) = s.baseSymbolicArray - i
          }

          @inline
          def update[I](i : I, v : V)(implicit rv : RV) : S = {
            i match {
              case i : Integer =>
                Heap.ElementAccess.H(s)(concreteArrayKey) = s.concreteArray + (i -> v)
              case i : V =>
                Heap.ElementAccess.H(s)(symbolicArrayKey) = s.symbolicArray + (i -> v)
            }
          }

          def lazyInit(i : Exp)(implicit rv : RV, rep : (S, V) --> V) = {
            val cm = concreteMapping
            val sm = symbolicMapping
            val newSize = cm.size + sm.size + 1
            val len = rep(s, s.length)
            val distinctArgs =
              (i match {
                case ValueExp(i : IsInteger) => ivectorEmpty
                case _                       => cm.map { e => i2e(e._1) }
              }) ++ sm.map { e => v2e(e._1) }

            import KiasanState.PathCondition._
            var newS = s +? BinaryExp("<=", BigInt(newSize), len)
            for (e <- distinctArgs) {
              newS = newS + BinaryExp("!=", i, e)
            }
            newS
          }
        }
      }

      /**
       * @author <a href="mailto:robby@k-state.edu">Robby</a>
       */
      final implicit class StateWithKiasanElementAccess[S <: SHK[S]](val s : S)
          extends AnyVal {

        @inline
        def apply(i : Integer)(
          implicit rv : RV, freshValue : (S, ResourceUri) --> (S, V),
          rep : (S, V) --> V) : ISeq[(S, V)] = {
          import Access._
          s.element(i) match {
            case Some(v) => (s, v)
            case _ =>
              val existingChoices =
                for { (index, v) <- s.symbolicMapping.toVector } yield {
                  val s2 = (StateWithKiasanArrayAccess(s - index)(i) = v)
                  val s3 = (StateWithKiasanArrayAccess(s2)(i) = v)(s2.base)
                  import KiasanState.PathCondition._
                  (s3 +? BinaryExp("==", i, index), v)
                }
              val (s4, v) = freshValue(s, s.elementType)
              ((Access.StateWithKiasanArrayAccess(
                s4.lazyInit(i))(i) = v)(s4.base), v) +: existingChoices
          }
        }

        @inline
        def update(i : Integer, v : V)(
          implicit rv : RV, freshValue : (S, ResourceUri) --> (S, V),
          rep : (S, V) --> V) : ISeq[(S, RV)] = {
          for {
            (s2, _) <- s(i)
          } yield {
            val (s3, rv2) = s2.duplicate(rv)
            ((Access.StateWithKiasanArrayAccess(s3)(i) = v)(rv2), rv2)
          }
        }

        @inline
        def apply(i : V)(
          implicit rv : RV,
          freshValue : (S, ResourceUri) --> (S, V), rep : (S, V) --> V,
          rep2 : (S, V, V) --> V) : ISeq[(S, Either[Integer, V], V)] =
          if (KonkritValCompositeExtension.Array.isKonkrit(s, rv)) {
            val skaa =
              KonkritValCompositeExtension.Array.Element.Access.
                StateWithKonkritArrayAccess(s)
            val a = skaa.nativeArray
            val min = skaa.minIndex
            val len = skaa.length.toInt
            import KiasanState.PathCondition._
            for { j <- Vector.range(0, len) } yield {
              val index = min + j
              (s +? BinaryExp("==", index, i), Left(index), a(j))
            }
          } else {
            import Access._
            s.element(i) match {
              case (s2, Some(v)) => (s2, Right(i), v)
              case (s2, _) =>
                import KiasanState.PathCondition._
                val existingChoicesC =
                  for { (index, v) <- s2.concreteMapping.toVector } yield {
                    (s2 +? BinaryExp("==", i, index), Left(index), v)
                  }
                val existingChoicesS =
                  for { (index, v) <- s2.symbolicMapping.toVector } yield {
                    val repIndex = rep2(s2, i, index)
                    val s4 =
                      if (index eq repIndex) s2
                      else {
                        val s3 = (StateWithKiasanArrayAccess(s2 - index)(repIndex) = v)
                        (StateWithKiasanArrayAccess(s3)(repIndex) = v)(s3.base)
                      }
                    (s4 +? BinaryExp("==", i, index), Right(repIndex), v)
                  }
                val (s5, v) = freshValue(s2, s2.elementType)
                val s6 =
                  (StateWithKiasanArrayAccess(
                    s5.lazyInit(i))(i) = v)(s5.base)
                (s6, Right(i), v) +: (
                  existingChoicesC ++ existingChoicesS)
            }
          }

        @inline
        def update(i : V, v : V)(
          implicit rv : RV, freshValue : (S, ResourceUri) --> (S, V),
          rep : (S, V) --> V, rep2 : (S, V, V) --> V) : ISeq[(S, RV)] = {
          for {
            (s2, repIndex, _) <- s(i)
          } yield {
            val (s3, rv2) = s2.duplicate(rv)
            repIndex match {
              case Left(repIndex) =>
                ((Access.StateWithKiasanArrayAccess(s3)(repIndex) = v)(rv2), rv2)
              case Right(repIndex) =>
                ((Access.StateWithKiasanArrayAccess(s3)(repIndex) = v)(rv2), rv2)
            }
          }
        }
      }
    }

    @inline
    def create[S <: H[S]](
      khid : Int, bkhid : Int, s : S, indexType : ResourceUri,
      elementType : ResourceUri, minIndex : V, maxIndex : V, length : V) = {
      val (s2, rv) = s.newObject(khid)
      val (s3, rvBase) = s2.newObject(bkhid)
      import Heap.ElementAccess._
      val s4 = {
        implicit val irv = rvBase
        ((((((
          s3(concreteArrayKey) = ilinkedMapEmpty[Integer, V])
          (symbolicArrayKey) = ilinkedMapEmpty[V, V])
          (elementTypeKey) = elementType)
          (minIndexKey) = minIndex)
          (maxIndexKey) = maxIndex)
          (lengthKey) = length)
      }
      val s5 = {
        implicit val irv = rv
        (((
          s4(concreteArrayKey) = ilinkedMapEmpty[Integer, V])
          (symbolicArrayKey) = ilinkedMapEmpty[V, V])
          (baseKey) = rvBase)
      }
      (s5, rv)
    }

    @inline
    def is[S <: H[S]](s : S)(implicit rv : RV) =
      Heap.ElementAccess.H(s) ? symbolicArrayKey

    @inline
    def elementLookup[S <: SHK[S]](
      implicit hid : Int, freshValue : (S, ResourceUri) --> (S, V),
      rep : (S, V) --> V, rep2 : (S, V, V) --> V) : (S, V, V) --> ISeq[(S, V)] = {
      case (s, rv @ Heap.RV(rvhid, _), i) if !i.isInstanceOf[IsInteger] &&
        KonkritValCompositeExtension.Array.isKonkrit(s, rv) =>
        implicit val irv = rv
        Element.StateWithKiasanElementAccess(s)(i).map { t => (t._1, t._3) }
      case (s, rv @ Heap.RV(rvhid, _), i) if rvhid == hid =>
        implicit val irv = rv
        assert(i eq rep(s, i))
        i match {
          case i : IsInteger =>
            Element.StateWithKiasanElementAccess(s)(i.asInteger)
          case i : V =>
            Element.StateWithKiasanElementAccess(s)(i).map { t => (t._1, t._3) }
        }
    }

    @inline
    def elementUpdate[S <: SHK[S]](
      implicit hid : Int, freshValue : (S, ResourceUri) --> (S, V),
      rep : (S, V) --> V, rep2 : (S, V, V) --> V) : //
      (S, NameUser, V, V) --> ISeq[S] = {

      case (s, x, i, v) if !i.isInstanceOf[IsInteger] &&
        KonkritValCompositeExtension.Array.isKonkrit(s,
          State.NameAccess.StateWithNameUserAccess(s)(x)) =>
        import State.NameAccess._
        assert(i eq rep(s, i))
        implicit val rv = s(x).asInstanceOf[RV]
        for {
          (s2, rv2) <- (Element.StateWithKiasanElementAccess(s)(i) = v)
        } yield s2(x) = rv2

      case (s, x, i, v) if Heap.isVarHid(s, x, hid) =>
        import State.NameAccess._
        implicit val rv = s(x).asInstanceOf[RV]
        assert(i eq rep(s, i))
        for {
          (s2, rv2) <- (i match {
            case i : IsInteger =>
              Element.StateWithKiasanElementAccess(s)(i.asInteger) = v
            case i : V =>
              Element.StateWithKiasanElementAccess(s)(i) = v
          })
        } yield s2(x) = rv2
    }
  }
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait KiasanValCompositeExtension[S <: State[S] with Heap[S] with KiasanStatePart[S]]
    extends Extension[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {

  type V = Value
  type R = ISeq[(S, Value)]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]
  type RV = Heap.RV

  def config : EvaluatorConfiguration[S, V, R, C, SR]

  val uriPath = UriUtil.classUri(this)

  val sec = config.semanticsExtension
  implicit val typeProvider = config.typeProvider

  implicit val (fresh, rep, rep2) = {
    import KiasanSemanticsExtensionConsumer._
    (sec.freshKiasanValue, sec.rep, sec.rep2)
  }

  implicit val hid = {
    import EvaluatorHeapConfiguration._
    config.heapConfig.heapId(KiasanValCompositeExtension.HeapIdKey)
  }

  val bhid = {
    import EvaluatorHeapConfiguration._
    config.heapConfig.heapId(KiasanValCompositeExtension.BaseHeapIdKey)
  }

  @FieldLookup
  val fieldLookup = KiasanValCompositeExtension.Record.fieldLookup

  @FieldUpdateVar
  val fieldUpdateVar = KiasanValCompositeExtension.Record.fieldUpdate

  @ArrayLookup
  val arrayLookup = KiasanValCompositeExtension.Array.elementLookup

  @ArrayUpdateVar
  val arrayUpdateVar = KiasanValCompositeExtension.Array.elementUpdate
}
