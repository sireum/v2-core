/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.extension

import org.sireum.extension.annotation._
import org.sireum.pilar.ast.Exp
import org.sireum.pilar.state._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object BooleanExtension {
  //  typedef Type @NonReferenceType;
  val Type = "pilar://typeext/" + UriUtil.classUri(this) + "/Type"
    
  //  typedef Type @NonReferenceType;
  trait BooleanValue extends NonReferenceValue
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait BooleanExtension[S, V, R, C, SR] extends Extension[S, V, R, C, SR] {
  //  expdef Boolean.Type not (Boolean.Type b) @Exp !b;
  @Unary("!")
  def not : (S, V) --> R

  //  expdef Boolean.Type cond_and (Boolean.Type b1, lazy Boolean.Type b2) @Exp b1 && b2;
  @RBinary("&&")
  def cond_and : (S, V, S => R) --> R

  //  expdef Boolean.Type cond_or (Boolean.Type b1, lazy Boolean.Type b2) @Exp b1 || b2;
  @RBinary("||")
  def cond_or : (S, V, S => R) --> R

  //  expdef Boolean.Type cond_implies (Boolean.Type b1, lazy Boolean.Type b2) @Exp b1 ==> b2;
  @RBinary("==>")
  def cond_implies : (S, V, S => R) --> R

  //  expdef Boolean.Type cond_implied (Boolean.Type b1, lazy Boolean.Type b2) @Exp b1 <== b2;
  @RBinary("<==")
  def cond_implied : (S, V, S => R) --> R

  //  expdef Boolean.Type eq (Boolean.Type b1, Boolean.Type b2) @Exp b1 == b2;
  @Binary("==")
  def eq : (S, V, V) --> R

  //  expdef Boolean.Type ne (Boolean.Type b1, Boolean.Type b2) @Exp b1 != b2;
  @Binary("!=")
  def ne : (S, V, V) --> R

  //  expdef Boolean.Type and (Boolean.Type b1, Boolean.Type b2) @Exp b1 &&& b2;
  @Binary("&&&")
  def and : (S, V, V) --> R

  //  expdef Boolean.Type or (Boolean.Type b1, Boolean.Type b2) @Exp b1 ||| b2;
  @Binary("|||")
  def or : (S, V, V) --> R

  //  expdef Boolean.Type implies (Boolean.Type b1, Boolean.Type b2) @Exp b1 ===> b2;
  @Binary("===>")
  def implies : (S, V, V) --> R

  //  expdef Boolean.Type implied (Boolean.Type b1, Boolean.Type b2) @Exp b1 <=== b2;
  @Binary("<===")
  def implied : (S, V, V) --> R
}
