/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class FloatingPointDefinition extends RealTypeDefinition {
  protected Exp theExp;

  protected Exp theOptionalLowRangeExp;

  protected Exp theOptionalHighRangeExp;

  public final static int DESCRIPTOR = 15;

  public FloatingPointDefinition() {
  }

  @Override
  public final int getDescriptor() {
    return FloatingPointDefinition.DESCRIPTOR;
  }

  public final Exp getTheExp() {
    return this.theExp;
  }

  public final Exp getTheOptionalHighRangeExp() {
    return this.theOptionalHighRangeExp;
  }

  public final Exp getTheOptionalLowRangeExp() {
    return this.theOptionalLowRangeExp;
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

  public final void setTheOptionalHighRangeExp(final Exp theOptionalHighRangeExp) {
    this.theOptionalHighRangeExp = theOptionalHighRangeExp;
  }

  public final void setTheOptionalLowRangeExp(final Exp theOptionalLowRangeExp) {
    this.theOptionalLowRangeExp = theOptionalLowRangeExp;
  }
}
