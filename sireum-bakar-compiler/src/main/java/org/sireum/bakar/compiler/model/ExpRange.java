/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class ExpRange extends Range {
  protected Exp theLowRangeExp;

  protected Exp theHighRangeExp;

  public final static int DESCRIPTOR = 11;

  public ExpRange() {
  }

  @Override
  public final int getDescriptor() {
    return ExpRange.DESCRIPTOR;
  }

  public final Exp getTheHighRangeExp() {
    return this.theHighRangeExp;
  }

  public final Exp getTheLowRangeExp() {
    return this.theLowRangeExp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheHighRangeExp(final Exp theHighRangeExp) {
    assert theHighRangeExp != null;
    this.theHighRangeExp = theHighRangeExp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheLowRangeExp(final Exp theLowRangeExp) {
    assert theLowRangeExp != null;
    this.theLowRangeExp = theLowRangeExp;
  }
}
