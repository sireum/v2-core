/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum

import scala.annotation.meta._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
package object extension {
  type ActionExt = org.sireum.extension.annotation.ActionExt @getter
  type ArrayLookup = org.sireum.extension.annotation.ArrayLookup @getter
  type ArrayUpdate = org.sireum.extension.annotation.ArrayUpdate @getter
  type ArrayUpdateVar = org.sireum.extension.annotation.ArrayUpdateVar @getter
  type Assign = org.sireum.extension.annotation.Assign @getter
  type Binaries = org.sireum.extension.annotation.Binaries @getter
  type Binary = org.sireum.extension.annotation.Binary @getter
  type Cast = org.sireum.extension.annotation.Cast @getter
  type Cond = org.sireum.extension.annotation.Cond @getter
  type DefaultValueProvider = org.sireum.extension.annotation.DefaultValueProvider @getter
  type ExpExt = org.sireum.extension.annotation.ExpExt @getter
  type FieldLookup = org.sireum.extension.annotation.FieldLookup @getter
  type FieldUpdate = org.sireum.extension.annotation.FieldUpdate @getter
  type FieldUpdateVar = org.sireum.extension.annotation.FieldUpdateVar @getter
  type LBinaries = org.sireum.extension.annotation.LBinaries @getter
  type LBinary = org.sireum.extension.annotation.LBinary @getter
  type Literal = org.sireum.extension.annotation.Literal @getter
  type NewList = org.sireum.extension.annotation.NewList @getter
  type NewObject = org.sireum.extension.annotation.NewObject @getter
  type ProcExt = org.sireum.extension.annotation.ProcExt @getter
  type RBinaries = org.sireum.extension.annotation.RBinaries @getter
  type RBinary = org.sireum.extension.annotation.RBinary @getter
  type TopLevel = org.sireum.extension.annotation.TopLevel @getter
  type TupleCon = org.sireum.extension.annotation.TupleCon @getter
  type TupleDecon = org.sireum.extension.annotation.TupleDecon @getter
  type Unaries = org.sireum.extension.annotation.Unaries @getter
  type Unary = org.sireum.extension.annotation.Unary @getter
  type UriValueProvider = org.sireum.extension.annotation.UriValueProvider @getter
  type VarLookup = org.sireum.extension.annotation.VarLookup @getter
  type VarUpdate = org.sireum.extension.annotation.VarUpdate @getter
}