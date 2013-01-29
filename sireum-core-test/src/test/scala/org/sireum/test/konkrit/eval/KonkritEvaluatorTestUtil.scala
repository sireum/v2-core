/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.konkrit.eval

import org.sireum.pilar.state._
import org.sireum.konkrit.extension._
import org.sireum.pilar.eval._
import org.sireum.util._
import org.sireum.extension._
import org.sireum.pilar.symbol._
import org.sireum.test.util.pilar.eval.PilarEvalUtil._
import org.sireum.util.math.SireumNumber

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object KonkritEvaluatorTestUtil {
  case class RC(value : Value)

  import language.implicitConversions
  
  implicit def b(v : Boolean) : Value = KonkritBooleanExtension.b2v(v)
  implicit def n(i : Int) : Value = KonkritIntegerExtension.CI(SireumNumber(i))
  implicit def n(l : Long) : Value = KonkritIntegerExtension.CI(SireumNumber(l))
  implicit def n(s : String) : Value = KonkritIntegerExtension.CI(SireumNumber(BigInt(s)))

  def newEvaluator[S <: State[S]](
    st : Option[SymbolTable],
    extCompanions : ExtensionCompanion*) =
    new EvaluatorConfigurationImpl[S](st, new EvaluatorImpl,
      new SemanticsExtensionModule[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {
        val sei = new SemanticsExtensionInitImpl[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {}
        val miners = ivector(ExtensionMiner.mine[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] _)
      },
      extCompanions : _*).evaluator.mainEvaluator

  def newEvaluator[S <: State[S]](
    extCompanions : ExtensionCompanion*) =
    new EvaluatorConfigurationImpl[S](None, new EvaluatorImpl,
      new SemanticsExtensionModule[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {
        val sei = new SemanticsExtensionInitImpl[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] {}
        val miners = ivector(ExtensionMiner.mine[S, Value, ISeq[(S, Value)], ISeq[(S, Boolean)], ISeq[S]] _)
      },
      extCompanions : _*).evaluator.mainEvaluator
}