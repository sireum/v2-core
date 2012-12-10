/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public final class SymModDef extends SymIntegerDef {
  protected org.sireum.bakar.compiler.v1.model.Exp theExp;

  public final static int DESCRIPTOR = 24;

  public SymModDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymModDef.DESCRIPTOR;
  }

  public final org.sireum.bakar.compiler.v1.model.Exp getTheExp() {
    return this.theExp;
  }

  public final void setTheExp(final org.sireum.bakar.compiler.v1.model.Exp theExp) {
    this.theExp = theExp;
  }
}
