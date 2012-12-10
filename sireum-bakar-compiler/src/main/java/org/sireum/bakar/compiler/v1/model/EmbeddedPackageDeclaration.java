/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class EmbeddedPackageDeclaration extends DeclarativePartMember {
  protected PackageDeclaration thePackageDeclaration;

  protected ArrayList<EmbeddedPackageDeclarationMember> theOptionalEmbeddedPackageDeclarationMembers;

  public final static int DESCRIPTOR = 32;

  public EmbeddedPackageDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return EmbeddedPackageDeclaration.DESCRIPTOR;
  }

  public final ArrayList<EmbeddedPackageDeclarationMember> getTheOptionalEmbeddedPackageDeclarationMembers() {
    return this.theOptionalEmbeddedPackageDeclarationMembers;
  }

  public final PackageDeclaration getThePackageDeclaration() {
    return this.thePackageDeclaration;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalEmbeddedPackageDeclarationMembers(
      final ArrayList<EmbeddedPackageDeclarationMember> theOptionalEmbeddedPackageDeclarationMembers) {
    assert Util.nonNullElements(theOptionalEmbeddedPackageDeclarationMembers);
    this.theOptionalEmbeddedPackageDeclarationMembers = theOptionalEmbeddedPackageDeclarationMembers;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePackageDeclaration(
      final PackageDeclaration thePackageDeclaration) {
    assert thePackageDeclaration != null;
    this.thePackageDeclaration = thePackageDeclaration;
  }
}
