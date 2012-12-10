/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class ProofTypeDeclaration extends BasicProofDeclaration {
  protected String theDefiningIdentifier;

  public final static int DESCRIPTOR = 30;

  public ProofTypeDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return ProofTypeDeclaration.DESCRIPTOR;
  }

  public final String getTheDefiningIdentifier() {
    return this.theDefiningIdentifier;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheDefiningIdentifier(final String theDefiningIdentifier) {
    assert theDefiningIdentifier != null;
    if (theDefiningIdentifier != null) {
      this.theDefiningIdentifier = theDefiningIdentifier.intern();
    } else {
      this.theDefiningIdentifier = null;
    }
  }
}
