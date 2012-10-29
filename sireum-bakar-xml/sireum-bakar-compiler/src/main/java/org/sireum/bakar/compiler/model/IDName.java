/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class IDName extends Name {
  protected String theIDString;

  protected boolean theDecoratedFlag = false;

  public final static int DESCRIPTOR = 36;

  public IDName() {
  }

  @Override
  public final int getDescriptor() {
    return IDName.DESCRIPTOR;
  }

  public final boolean getTheDecoratedFlag() {
    return this.theDecoratedFlag;
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * false
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheDecoratedFlag(final boolean theDecoratedFlag) {
    this.theDecoratedFlag = theDecoratedFlag;
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
}
