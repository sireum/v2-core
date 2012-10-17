/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.extension

import org.sireum.extension.annotation._
import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object ArrayAccessExtension {

}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ArrayAccessExtension[S, V, R, C, SR] extends Extension[S, V, R, C, SR] {
  //  expdef 'c arrayLookup<'a, 'b, 'c>(Object.Type<'a> a, 'b i) @Exp a[i];
  @ArrayLookup
  def arrayLookup : (S, V, V) --> R

  //  actiondef arrayUpdate<'a, 'b, 'c>(Object.Type<'a> a, 'b i, 'c v) @Action "a[i] := v;";
  @ArrayUpdate
  def arrayUpdate : (S, V, V, V) --> SR

  //  actiondef arrayUpdate<'a, 'b, 'c>(Object.Type<'a> a, 'b i, 'c v) @Action "a[i] := v;";
  @ArrayUpdateVar
  def arrayUpdateVar : (S, NameUser, V, V) --> SR
}