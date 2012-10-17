/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.extension

import org.sireum.extension.annotation._
import org.sireum.pilar.state._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object ObjectExtension {
  //  typedef Type<'a> @ObjectType;
  val Type = "pilar://typeext/org/sireum/pilar/state/extension/Object/Type"

  //  typedef RefType<'a> extends org::sireum::extension::Object.Type<'a>;
  val RefType = "pilar://typeext/org/sireum/pilar/state/extension/Object/RefType"
    
  //  typedef ValType<'a> extends org::sireum::extension::Object.Type<'a>;
  val ValType = "pilar://typeext/org/sireum/pilar/state/extension/Object/ValType"
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait ObjectExtension[S, V, R, C, SR] extends Extension[S, V, R, C, SR] {
  //  expdef Boolean.Type eq<'a, 'b>(Object.Type<'a> o1, Object.Type<'b> o2) @Exp o1 == o2;
  @Binary("==")
  def eq : (S, V, V) --> R

  //  expdef Boolean.Type ne<'a, 'b>(Object.Type<'a> o1, Object.Type<'b> o2) @Exp o1 != o2;
  @Binary("!=")
  def ne : (S, V, V) --> R
}