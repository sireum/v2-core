/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public final class SymSignedIntegerDef extends SymIntegerDef {
  protected org.sireum.bakar.compiler.v1.model.Exp theHighRangeExp;

  protected org.sireum.bakar.compiler.v1.model.Exp theLowRangeExp;

  public final static int DESCRIPTOR = 23;

  public SymSignedIntegerDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymSignedIntegerDef.DESCRIPTOR;
  }

  public final org.sireum.bakar.compiler.v1.model.Exp getTheHighRangeExp() {
    return this.theHighRangeExp;
  }

  public final org.sireum.bakar.compiler.v1.model.Exp getTheLowRangeExp() {
    return this.theLowRangeExp;
  }

  public final void setTheHighRangeExp(
      final org.sireum.bakar.compiler.v1.model.Exp theHighRangeExp) {
    this.theHighRangeExp = theHighRangeExp;
  }

  public final void setTheLowRangeExp(
      final org.sireum.bakar.compiler.v1.model.Exp theLowRangeExp) {
    this.theLowRangeExp = theLowRangeExp;
  }
}
