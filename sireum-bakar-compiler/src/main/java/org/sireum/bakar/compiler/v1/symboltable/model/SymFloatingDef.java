/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public final class SymFloatingDef extends SymRealDef {
  protected org.sireum.bakar.compiler.v1.model.Exp theExp;

  protected org.sireum.bakar.compiler.v1.model.Exp theOptionalLowRangeExp;

  protected org.sireum.bakar.compiler.v1.model.Exp theOptionalHighRangeExp;

  public final static int DESCRIPTOR = 26;

  public SymFloatingDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymFloatingDef.DESCRIPTOR;
  }

  public final org.sireum.bakar.compiler.v1.model.Exp getTheExp() {
    return this.theExp;
  }

  public final org.sireum.bakar.compiler.v1.model.Exp getTheOptionalHighRangeExp() {
    return this.theOptionalHighRangeExp;
  }

  public final org.sireum.bakar.compiler.v1.model.Exp getTheOptionalLowRangeExp() {
    return this.theOptionalLowRangeExp;
  }

  public final void setTheExp(final org.sireum.bakar.compiler.v1.model.Exp theExp) {
    this.theExp = theExp;
  }

  public final void setTheOptionalHighRangeExp(
      final org.sireum.bakar.compiler.v1.model.Exp theOptionalHighRangeExp) {
    this.theOptionalHighRangeExp = theOptionalHighRangeExp;
  }

  public final void setTheOptionalLowRangeExp(
      final org.sireum.bakar.compiler.v1.model.Exp theOptionalLowRangeExp) {
    this.theOptionalLowRangeExp = theOptionalLowRangeExp;
  }
}
