/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class PrivateTypeDeclaration extends TypeDeclaration {
  protected String theIDString;

  protected boolean theTaggedFlag;

  protected boolean theLimitedFlag;

  public final static int DESCRIPTOR = 122;

  public PrivateTypeDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return PrivateTypeDeclaration.DESCRIPTOR;
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  public final boolean getTheLimitedFlag() {
    return this.theLimitedFlag;
  }

  public final boolean getTheTaggedFlag() {
    return this.theTaggedFlag;
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

  public final void setTheLimitedFlag(final boolean theLimitedFlag) {
    this.theLimitedFlag = theLimitedFlag;
  }

  public final void setTheTaggedFlag(final boolean theTaggedFlag) {
    this.theTaggedFlag = theTaggedFlag;
  }
}
