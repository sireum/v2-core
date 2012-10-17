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
object IntegerExtension {
  //  typedef Type @NonReferenceType;
  val Type = "pilar://typeext/org/sireum/extension/Integer/Type"
    
  //  typedef Type @NonReferenceType;
  trait IntegerValue extends NonReferenceValue
}

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait IntegerExtension[S, V, R, C, SR] extends Extension[S, V, R, C, SR] {
  //  expdef Boolean.Type isPositive(Integer.Type);
  @ExpExt
  def isPositive : (S, V) --> R

  //  expdef Boolean.Type isZero(Integer.Type);
  @ExpExt
  def isZero : (S, V) --> R

  //  expdef Boolean.Type isNegative(Integer.Type);
  @ExpExt
  def isNegative : (S, V) --> R

  //  expdef Integer.Type pos (Integer.Type) @BinaryOp "+";
  @Unary("+")
  def pos : (S, V) --> R

  //  expdef Integer.Type neg (Integer.Type) @BinaryOp "-";
  @Unary("-")
  def neg : (S, V) --> R

  //  expdef Integer.Type add (Integer.Type, Integer.Type) @BinaryOp "+";
  @Binary("+")
  def add : (S, V, V) --> R

  //  expdef Integer.Type sub (Integer.Type, Integer.Type) @BinaryOp "-";
  @Binary("-")
  def sub : (S, V, V) --> R

  //  expdef Integer.Type mul (Integer.Type, Integer.Type) @BinaryOp "*";
  @Binary("*")
  def mul : (S, V, V) --> R

  //  expdef Integer.Type div (Integer.Type, Integer.Type) @BinaryOp "/";
  @Binary("/")
  def div : (S, V, V) --> R

  //  expdef Integer.Type rem (Integer.Type, Integer.Type) @BinaryOp "%";
  @Binary("%")
  def rem : (S, V, V) --> R

  //  expdef Boolean.Type eq (Integer.Type, Integer.Type) @BinaryOp "==";
  @Binary("==")
  def eq : (S, V, V) --> R

  //  expdef Boolean.Type ne (Integer.Type, Integer.Type) @BinaryOp "!=";
  @Binary("!=")
  def ne : (S, V, V) --> R

  //  expdef Boolean.Type lt (Integer.Type, Integer.Type) @BinaryOp "<";
  @Binary("<")
  def lt : (S, V, V) --> R

  //  expdef Boolean.Type gt (Integer.Type, Integer.Type) @BinaryOp ">";
  @Binary(">")
  def gt : (S, V, V) --> R

  //  expdef Boolean.Type le (Integer.Type, Integer.Type) @BinaryOp "<=";
  @Binary("<=")
  def le : (S, V, V) --> R

  //  expdef Boolean.Type ge (Integer.Type, Integer.Type) @BinaryOp ">=";
  @Binary(">=")
  def ge : (S, V, V) --> R
}