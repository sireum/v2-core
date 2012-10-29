/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class PackageDeclaration extends LibraryUnitDeclaration {
  protected ArrayList<Name> theOptionalInheritClauses;

  protected PackageSpecification thePackageSpecification;

  protected boolean thePrivateFlag;

  public final static int DESCRIPTOR = 113;

  public PackageDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return PackageDeclaration.DESCRIPTOR;
  }

  public final ArrayList<Name> getTheOptionalInheritClauses() {
    return this.theOptionalInheritClauses;
  }

  public final PackageSpecification getThePackageSpecification() {
    return this.thePackageSpecification;
  }

  public final boolean getThePrivateFlag() {
    return this.thePrivateFlag;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalInheritClauses(
      final ArrayList<Name> theOptionalInheritClauses) {
    assert Util.nonNullElements(theOptionalInheritClauses);
    this.theOptionalInheritClauses = theOptionalInheritClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePackageSpecification(
      final PackageSpecification thePackageSpecification) {
    assert thePackageSpecification != null;
    this.thePackageSpecification = thePackageSpecification;
  }

  public final void setThePrivateFlag(final boolean thePrivateFlag) {
    this.thePrivateFlag = thePrivateFlag;
  }
}
