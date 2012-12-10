/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class PrivateExtensionDeclaration extends TypeDeclaration {
  protected String theIDString;

  protected SubTypeIndication theSubTypeIndication;

  public final static int DESCRIPTOR = 123;

  public PrivateExtensionDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return PrivateExtensionDeclaration.DESCRIPTOR;
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
