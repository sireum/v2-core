/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

public final class SymFixedDef extends SymRealDef {
  protected org.sireum.bakar.compiler.model.Exp theExp;

  protected org.sireum.bakar.compiler.model.Exp theHighRangeExp;

  protected org.sireum.bakar.compiler.model.Exp theLowRangeExp;

  public final static int DESCRIPTOR = 25;

  public SymFixedDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymFixedDef.DESCRIPTOR;
  }

  public final org.sireum.bakar.compiler.model.Exp getTheExp() {
    return this.theExp;
  }

  public final org.sireum.bakar.compiler.model.Exp getTheHighRangeExp() {
    return this.theHighRangeExp;
  }

  public final org.sireum.bakar.compiler.model.Exp getTheLowRangeExp() {
    return this.theLowRangeExp;
  }

  public final void setTheExp(final org.sireum.bakar.compiler.model.Exp theExp) {
    this.theExp = theExp;
  }

  public final void setTheHighRangeExp(
      final org.sireum.bakar.compiler.model.Exp theHighRangeExp) {
    this.theHighRangeExp = theHighRangeExp;
  }

  public final void setTheLowRangeExp(
      final org.sireum.bakar.compiler.model.Exp theLowRangeExp) {
    this.theLowRangeExp = theLowRangeExp;
  }
}
