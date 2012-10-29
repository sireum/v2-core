/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.selection.model;

public final class RegionSelection extends SelectionNode {
  protected Caret theStartCaret;

  protected Caret theEndCaret;

  protected String theOptionalSource;

  public final static int DESCRIPTOR = 3;

  public RegionSelection() {
  }

  @Override
  public final int getDescriptor() {
    return RegionSelection.DESCRIPTOR;
  }

  public final Caret getTheEndCaret() {
    return this.theEndCaret;
  }

  public final String getTheOptionalSource() {
    return this.theOptionalSource;
  }

  public final Caret getTheStartCaret() {
    return this.theStartCaret;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheEndCaret(final Caret theEndCaret) {
    assert theEndCaret != null;
    this.theEndCaret = theEndCaret;
  }

  public final void setTheOptionalSource(final String theOptionalSource) {
    if (theOptionalSource != null) {
      this.theOptionalSource = theOptionalSource.intern();
    } else {
      this.theOptionalSource = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheStartCaret(final Caret theStartCaret) {
    assert theStartCaret != null;
    this.theStartCaret = theStartCaret;
  }
}
