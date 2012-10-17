/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.option

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "eval", header = """
Sireum/Kiasan Evaluator
(c) 2012, SAnToS Laboratory, Kansas State University
""")
case class KiasanEvalMode(
  exp : KiasanEvalExp = KiasanEvalExp(),
  action : KiasanEvalAction = KiasanEvalAction())

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "exp", className = "UNKNOWN", featureName = "Sireum Kiasan")
case class KiasanEvalExp(
  @Arg(index=0, value="expression") // 
  var exp : String = "0")

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "action", className = "UNKNOWN", featureName = "Sireum Kiasan")
case class KiasanEvalAction(
  @Arg(index=1, value="action") // 
  var exp : String = "0")
