/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.extension

import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object FieldAccessExtension {

}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait FieldAccessExtension[S, V, R, C, SR] extends Extension[S, V, R, C, SR] {
  //  expdef 'b fieldLookup<'a, 'b>(Object.Type<'a> r, f) @Exp r.f;
  @FieldLookup
  def fieldLookup : (S, V, NameUser) --> R

  //  actiondef fieldUpdate<'a, 'b, 'c>(Object.Type<'a> r, f, 'c v) @Action "r.f := v;";
  @FieldUpdate
  def fieldUpdate : (S, V, NameUser, V) --> SR

  //  actiondef fieldUpdate<'a, 'b, 'c>(Object.Type<'a> r, f, 'c v) @Action "r.f := v;";
  @FieldUpdateVar
  def fieldUpdateVar : (S, NameUser, NameUser, V) --> SR
}