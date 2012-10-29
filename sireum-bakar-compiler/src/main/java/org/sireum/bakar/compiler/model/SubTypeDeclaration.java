/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class SubTypeDeclaration extends BasicDeclaration {
  protected String theIDString;

  protected SubTypeIndication theSubTypeIndication;

  public final static int DESCRIPTOR = 6;

  public SubTypeDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return SubTypeDeclaration.DESCRIPTOR;
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  public final SubTypeIndication getTheSubTypeIndication() {
    return this.theSubTypeIndication;
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
  public final void setTheSubTypeIndication(
      final SubTypeIndication theSubTypeIndication) {
    assert theSubTypeIndication != null;
    this.theSubTypeIndication = theSubTypeIndication;
  }
}
