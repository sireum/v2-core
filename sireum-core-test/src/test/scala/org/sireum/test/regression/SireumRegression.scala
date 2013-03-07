/*
Copyright (c) 2011-2013 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.regression

import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses
import org.sireum.test.kiasan.eval.jump.KiasanJumpEvaluatorTest
import org.sireum.test.konkrit.eval.action.KonkritActionEvaluatorTest
import org.sireum.test.konkrit.eval.jump.KonkritJumpEvaluatorTest
import org.sireum.test.pilar.symbol.SymbolResolverTest
import org.sireum.test.kiasan.eval.transformation.KiasanTransformationEvaluatorTest
import org.sireum.test.kiasan.eval.action.KiasanActionEvaluatorTest
import org.sireum.test.konkrit.eval.transformation.KonkritTransformationEvaluatorTest
import org.sireum.test.kiasan.eval.exp.KiasanScalarExpEvaluatorTest
import org.sireum.test.pilar.parser.ParserTest
import org.sireum.test.alir.AlirIntraProceduralTest
import org.sireum.test.konkrit.eval.location.KonkritLocationEvaluatorTest
import org.junit.runners.Suite
import org.sireum.test.extension.eval.exp.ExtensionExpEvaluatorTest
import org.sireum.test.extension.eval.action.ExtensionActionEvaluatorTest
import org.sireum.test.konkrit.eval.exp.KonkritScalarExpEvaluatorTest
import org.sireum.test.kiasan.bfs.BasicKiasanBfsTest
import org.sireum.test.cli.Clitest
import org.sireum.test.extension.composite.eval.exp.ValCompositeExpEvaluatorTest
import org.sireum.test.extension.composite.eval.action.ValCompositeActionEvaluatorTest

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[Suite])
@SuiteClasses(Array(
  classOf[AlirIntraProceduralTest],
  classOf[Clitest], //
  classOf[KiasanTests],
  classOf[KonkritTests],
  classOf[ExtensionTests],
  classOf[ParserTest],
  classOf[SymbolResolverTest]
))
class SireumRegression {}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[Suite])
@SuiteClasses(Array(
  classOf[KiasanActionEvaluatorTest],
  classOf[KiasanScalarExpEvaluatorTest],
  classOf[KiasanJumpEvaluatorTest],
  classOf[KiasanTransformationEvaluatorTest],
  classOf[BasicKiasanBfsTest]
))
class KiasanTests {}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[Suite])
@SuiteClasses(Array(
  classOf[KonkritActionEvaluatorTest],
  classOf[KonkritScalarExpEvaluatorTest],
  classOf[KonkritJumpEvaluatorTest],
  classOf[KonkritLocationEvaluatorTest],
  classOf[KonkritTransformationEvaluatorTest]
))
class KonkritTests {}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[Suite])
@SuiteClasses(Array(
  classOf[ExtensionExpEvaluatorTest],
  classOf[ExtensionActionEvaluatorTest],
  classOf[ValCompositeExpEvaluatorTest],
  classOf[ValCompositeActionEvaluatorTest]
))
class ExtensionTests {}
