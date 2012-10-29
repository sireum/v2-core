/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class FullTypeDeclaration extends TypeDeclaration {
  protected String theIDString;

  protected TypeDefinition theTypeDefinition;

  public final static int DESCRIPTOR = 4;

  public FullTypeDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return FullTypeDeclaration.DESCRIPTOR;
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  public final TypeDefinition getTheTypeDefinition() {
    return this.theTypeDefinition;
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
  public final void setTheTypeDefinition(final TypeDefinition theTypeDefinition) {
    assert theTypeDefinition != null;
    this.theTypeDefinition = theTypeDefinition;
  }
}
