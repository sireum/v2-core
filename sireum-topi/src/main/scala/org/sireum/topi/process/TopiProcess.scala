/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.topi.process

import java.io._
import org.sireum.extension._
import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.topi._
import org.sireum.topi.annotation._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object TopiProcess {
  type ExpTranslator = Exp --> Unit

  trait BackEndPart {
    def expTranslator(sb : StringBuilder, m : MMap[Immutable, Int]) : ExpTranslator
    def stateRewriter(m : IMap[String, Value]) : RewriteFunction
  }

  def mine(solver : TopiSolver.Type,
           mode : TopiMode.Type,
           extensions : ExtensionCompanion*) : ISeq[BackEndPart] = {
    var result = ilistEmpty[BackEndPart]
    for (ext <- extensions)
      for (m <- ext.getClass.getDeclaredMethods)
        for (ann <- m.getDeclaredAnnotations)
          ann match {
            case ann : BackEnd =>
              if (solver.toString.replace("$", "") == ann.value &&
                mode.toString.replace("$", "") == ann.mode)
                result = m.invoke(ext).asInstanceOf[BackEndPart] :: result
          }
    result.reverse
  }
}