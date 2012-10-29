/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class ForLoopStatement extends IterationSchemeLoopStatement {
  protected String theIDString;

  protected boolean theReverseFlag;

  protected Name theName;

  protected Range theOptionalRange;

  public final static int DESCRIPTOR = 86;

  public ForLoopStatement() {
  }

  @Override
  public final int getDescriptor() {
    return ForLoopStatement.DESCRIPTOR;
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final Range getTheOptionalRange() {
    return this.theOptionalRange;
  }

  public final boolean getTheReverseFlag() {
    return this.theReverseFlag;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheIDString(final String theIDString) {
    assert theIDString != null;
    if (theIDString != null) {
      this.theIDString = theIDString.intern();
    } else {
      this.theIDString = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheName(final Name theName) {
    assert theName != null;
    this.theName = theName;
  }

  public final void setTheOptionalRange(final Range theOptionalRange) {
    this.theOptionalRange = theOptionalRange;
  }

  public final void setTheReverseFlag(final boolean theReverseFlag) {
    this.theReverseFlag = theReverseFlag;
  }
}
