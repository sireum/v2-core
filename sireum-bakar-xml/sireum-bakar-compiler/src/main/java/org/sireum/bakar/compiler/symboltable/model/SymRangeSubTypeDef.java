/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

public final class SymRangeSubTypeDef extends SymScalarDef {
  protected SymDef theParentTypeDef;

  protected org.sireum.bakar.compiler.model.Exp theHighRangeExp;

  protected org.sireum.bakar.compiler.model.Exp theLowRangeExp;

  public final static int DESCRIPTOR = 27;

  public SymRangeSubTypeDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymRangeSubTypeDef.DESCRIPTOR;
  }

  public final org.sireum.bakar.compiler.model.Exp getTheHighRangeExp() {
    return this.theHighRangeExp;
  }

  public final org.sireum.bakar.compiler.model.Exp getTheLowRangeExp() {
    return this.theLowRangeExp;
  }

  public final SymDef getTheParentTypeDef() {
    return this.theParentTypeDef;
  }

  public final void setTheHighRangeExp(
      final org.sireum.bakar.compiler.model.Exp theHighRangeExp) {
    this.theHighRangeExp = theHighRangeExp;
  }

  public final void setTheLowRangeExp(
      final org.sireum.bakar.compiler.model.Exp theLowRangeExp) {
    this.theLowRangeExp = theLowRangeExp;
  }

  public final void setTheParentTypeDef(final SymDef theParentTypeDef) {
    this.theParentTypeDef = theParentTypeDef;
  }
}
