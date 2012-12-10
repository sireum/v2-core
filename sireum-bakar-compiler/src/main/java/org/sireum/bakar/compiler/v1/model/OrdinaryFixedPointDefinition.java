/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class OrdinaryFixedPointDefinition extends FixedPointDefinition {
  protected Exp theExp;

  protected Exp theLowRangeExp;

  protected Exp theHighRangeExp;

  public final static int DESCRIPTOR = 16;

  public OrdinaryFixedPointDefinition() {
  }

  @Override
  public final int getDescriptor() {
    return OrdinaryFixedPointDefinition.DESCRIPTOR;
  }

  public final Exp getTheExp() {
    return this.theExp;
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
  public final void setTheExp(final Exp theExp) {
    assert theExp != null;
    this.theExp = theExp;
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
