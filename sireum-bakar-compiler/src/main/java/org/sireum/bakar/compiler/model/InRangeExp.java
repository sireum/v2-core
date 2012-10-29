/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class InRangeExp extends Exp {
  protected Exp theExp;

  protected Range theRange;

  public final static int DESCRIPTOR = 62;

  public InRangeExp() {
  }

  @Override
  public final int getDescriptor() {
    return InRangeExp.DESCRIPTOR;
  }

  public final Exp getTheExp() {
    return this.theExp;
  }

  public final Range getTheRange() {
    return this.theRange;
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

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheRange(final Range theRange) {
    assert theRange != null;
    this.theRange = theRange;
  }
}
