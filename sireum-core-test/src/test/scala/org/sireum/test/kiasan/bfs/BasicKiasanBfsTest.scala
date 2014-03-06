/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.kiasan.bfs

import org.sireum.kiasan.state._
import org.sireum.test.framework.kiasan.bfs._
import org.sireum.pilar.state._
import org.sireum.util._
import org.junit.runner._
import org.scalatest.junit._
import org.sireum.kiasan._
import org.sireum.pilar.symbol._
import org.sireum.test.kiasan.eval._
import org.sireum.kiasan.extension._
import org.sireum.konkrit.extension._
import org.sireum.example.pilarv3.kiasan._
import org.sireum.pilar.eval.EvaluatorConfiguration
import org.sireum.pilar.ast.NameUser

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class BasicKiasanBfsTest
    extends KiasanBfsTestFramework[BasicKiasanState, ISeq[(BasicKiasanState, Value)], ISeq[(BasicKiasanState, Boolean)]] {

  KiasanExamples.goodModelFiles.foreach { fUri =>
    Executing file fUri on stateInitializer gives "sat models" satisfying { r =>
      r.errorStates should be('empty)
      if (fUri.endsWith("sum.plr"))
        r.depthBoundExStates.size should be > 0
    }
  }

  type S = BasicKiasanState
  type V = Value
  type R = ISeq[(S, V)]
  type C = ISeq[(S, Boolean)]
  type SR = ISeq[S]

  def stateInitializer(st : SymbolTable, config : EvaluatorConfiguration) : ISeq[S] = {
    val sec = config.semanticsExtension[S, V, R, C, SR]
      def lazyInit(ss : ISeq[S], varUri : ResourceUri) =
        for {
          s1 <- ss
          (s2, _) <- sec.variable(s1, NameUser(varUri))
        } yield s2

    val pst = st.procedureSymbolTables.head
    val locationIndex = 0
    val locationUri = pst.locations(0).name.map(_.name)

    val s = BasicKiasanState()
    var ss : ISeq[S] = ivector(s.enterCallFrame(pst.procedureUri, locationUri, locationIndex))

    for (paramUri <- pst.params)
      ss = lazyInit(ss, paramUri)

    for (globalVarUri <- st.globalVars)
      ss = lazyInit(ss, globalVarUri)

    for { s <- ss } yield {
      val s1 = s.init
      s1.setProperty(".initstate", s1)
    }
  }

  def config(st : SymbolTable) =
    KiasanEvaluatorTestUtil.newConfig[BasicKiasanState](
      Some(st),
      extensions : _*)

  val extensions = ivector(
    KiasanBooleanExtension,
    KiasanIntegerExtension,
    KiasanVariableAccessExtension,
    KonkritBooleanExtension,
    KonkritIntegerExtension)
}
