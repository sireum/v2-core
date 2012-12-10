/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class RenamingDeclarationEmbeddedPackageDeclarationMember extends
    EmbeddedPackageDeclarationMember {
  protected RenamingDeclaration theRenamingDeclaration;

  public final static int DESCRIPTOR = 33;

  public RenamingDeclarationEmbeddedPackageDeclarationMember() {
  }

  @Override
  public final int getDescriptor() {
    return RenamingDeclarationEmbeddedPackageDeclarationMember.DESCRIPTOR;
  }

  public final RenamingDeclaration getTheRenamingDeclaration() {
    return this.theRenamingDeclaration;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheRenamingDeclaration(
      final RenamingDeclaration theRenamingDeclaration) {
    assert theRenamingDeclaration != null;
    this.theRenamingDeclaration = theRenamingDeclaration;
  }
}
