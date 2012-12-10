/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class UnaryExp extends Exp {
  protected UnaryOp theUnaryOp;

  protected Exp theExp;

  public final static int DESCRIPTOR = 61;

  public UnaryExp() {
  }

  @Override
  public final int getDescriptor() {
    return UnaryExp.DESCRIPTOR;
  }

  public final Exp getTheExp() {
    return this.theExp;
  }

  public final UnaryOp getTheUnaryOp() {
    return this.theUnaryOp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheExp(final Exp theExp) {
    assert theExp != null;
    this.theExp = theExp;
  }

  public final void setTheUnaryOp(final UnaryOp theUnaryOp) {
    this.theUnaryOp = theUnaryOp;
  }
}
