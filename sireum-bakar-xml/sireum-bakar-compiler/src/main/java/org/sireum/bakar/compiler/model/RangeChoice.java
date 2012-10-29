/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class RangeChoice extends Choice {
  protected Range theRange;

  public final static int DESCRIPTOR = 25;

  public RangeChoice() {
  }

  @Override
  public final int getDescriptor() {
    return RangeChoice.DESCRIPTOR;
  }

  public final Range getTheRange() {
    return this.theRange;
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
