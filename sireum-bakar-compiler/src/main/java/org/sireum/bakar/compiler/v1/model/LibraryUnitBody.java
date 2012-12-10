/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class LibraryUnitBody extends LibraryItem {
  protected PackageBody thePackageBody;

  public final static int DESCRIPTOR = 132;

  public LibraryUnitBody() {
  }

  @Override
  public final int getDescriptor() {
    return LibraryUnitBody.DESCRIPTOR;
  }

  public final PackageBody getThePackageBody() {
    return this.thePackageBody;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePackageBody(final PackageBody thePackageBody) {
    assert thePackageBody != null;
    this.thePackageBody = thePackageBody;
  }
}
