/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.extension

import org.sireum.extension.annotation._
import org.sireum.pilar.ast.NameUser
import org.sireum.pilar.state._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object VariableAccessExtension {

}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait VariableAccessExtension[S, V, R, C, SR] extends Extension[S, V, R, C, SR]  {
  //  expdef 'a variableLookup<'a>(lazy 'a x) @Exp x;
  @VarLookup
  def variableLookup : (S, NameUser) --> R

  //  actiondef variableUpdate<'a, 'b>(lazy 'a x, 'b v) @Action "x := v;" @TypeConstraint 'a <: 'b;
  @VarUpdate
  def variableUpdate : (S, NameUser, V) --> SR
}