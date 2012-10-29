/******************************************************************************
 * Copyright (c) 2009 Belt, Kansas State University, and others.              *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar;

public enum SparkUIF {
  /*****************************************************************************
   * General uif's
   ****************************************************************************/
  $AMP, // string concat

  /*****************************************************************************
   * Arithmetic ops
   ****************************************************************************/
  $POW, // 
  $REM, //

  /*****************************************************************************
   * Conditional Binary ops
   ****************************************************************************/
  $AND, // non-short circuit and
  $OR, // non-short circuit or
  $XOR, //

  /*****************************************************************************
   * Standard Package def uif's.
   * 
   * We could probably get away with a single $UNKNOWN for these
   ****************************************************************************/
  $POS_INFTY, // 
  $NEG_INFTY, // 
  $UNDEFINED, //

  /*****************************************************************************
   * Proof context uif's
   ****************************************************************************/
  $OLD, //
  $FOR_ALL, //
  $EXISTS, //
  $ARRAY_UPDATE, //
  $RECORD_UPDATE;

  public boolean isArithmeticOp() {
    return (this == $POW) || (this == $REM);
  }

  public boolean isCondBinaryExpOp() {
    return (this == $AND) || (this == $OR) || (this == $XOR);
  }

  public boolean isProofUIF() {
    switch (this) {
      case $OLD:
      case $FOR_ALL:
      case $EXISTS:
      case $ARRAY_UPDATE:
        return true;
      default:
        return false;
    }
  }

  public boolean isQuantifier() {
    return (this == $FOR_ALL) || (this == $EXISTS);
  }
}
