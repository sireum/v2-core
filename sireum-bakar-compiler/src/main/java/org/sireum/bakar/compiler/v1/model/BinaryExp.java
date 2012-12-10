/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class BinaryExp extends Exp {
  protected BinaryOp theBinaryOp;

  protected Exp theLeftExp;

  protected Exp theRightExp;

  public final static int DESCRIPTOR = 60;

  public BinaryExp() {
  }

  @Override
  public final int getDescriptor() {
    return BinaryExp.DESCRIPTOR;
  }

  public final BinaryOp getTheBinaryOp() {
    return this.theBinaryOp;
  }

  public final Exp getTheLeftExp() {
    return this.theLeftExp;
  }

  public final Exp getTheRightExp() {
    return this.theRightExp;
  }

  public final void setTheBinaryOp(final BinaryOp theBinaryOp) {
    this.theBinaryOp = theBinaryOp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheLeftExp(final Exp theLeftExp) {
    assert theLeftExp != null;
    this.theLeftExp = theLeftExp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheRightExp(final Exp theRightExp) {
    assert theRightExp != null;
    this.theRightExp = theRightExp;
  }
}
