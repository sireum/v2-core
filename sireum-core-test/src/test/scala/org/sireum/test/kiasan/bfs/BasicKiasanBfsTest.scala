/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
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
import org.sireum.example.kiasan._

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

  def stateInitializer(st : SymbolTable) : ISeq[BasicKiasanState] = {
    val pst = st.procedureSymbolTables.head
    var s = BasicKiasanState()
    val locationIndex = 0
    val locationUri = pst.locations(0).name.map(_.name)
    s = s.enterCallFrame(pst.procedureUri, locationUri, locationIndex)
    ivector(s.init)
  }

  def evaluator(st : SymbolTable) =
    KiasanEvaluatorTestUtil.newEvaluator[BasicKiasanState](
      Some(st),
      extensions : _*)

  val extensions = ivector(
    KiasanBooleanExtension,
    KiasanIntegerExtension,
    KiasanVariableAccessExtension,
    KonkritBooleanExtension,
    KonkritIntegerExtension)
}
