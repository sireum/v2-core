/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class OwnVariable extends Node {
  protected Mode theMode;

  protected String theIDString;

  public final static int DESCRIPTOR = 116;

  public OwnVariable() {
  }

  @Override
  public final int getDescriptor() {
    return OwnVariable.DESCRIPTOR;
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  public final Mode getTheMode() {
    return this.theMode;
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
  public final void setTheMode(final Mode theMode) {
    assert theMode != null;
    this.theMode = theMode;
  }
}
