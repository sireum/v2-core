/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class PackageRenamingDeclaration extends RenamingDeclaration {
  protected Name thePackageName;

  protected Name theRenamedName;

  public final static int DESCRIPTOR = 125;

  public PackageRenamingDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return PackageRenamingDeclaration.DESCRIPTOR;
  }

  public final Name getThePackageName() {
    return this.thePackageName;
  }

  public final Name getTheRenamedName() {
    return this.theRenamedName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePackageName(final Name thePackageName) {
    assert thePackageName != null;
    this.thePackageName = thePackageName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheRenamedName(final Name theRenamedName) {
    assert theRenamedName != null;
    this.theRenamedName = theRenamedName;
  }
}
