/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.module.util;

public final class SPARKSpecBodyUnit extends Node {
  protected String thePackageName;

  protected SPARKCompilationUnit theOptionalPackageSpecification;

  protected SPARKCompilationUnit theOptionalPackageBody;

  public final static int DESCRIPTOR = 3;

  public SPARKSpecBodyUnit() {
  }

  @Override
  public final int getDescriptor() {
    return SPARKSpecBodyUnit.DESCRIPTOR;
  }

  public final SPARKCompilationUnit getTheOptionalPackageBody() {
    return this.theOptionalPackageBody;
  }

  public final SPARKCompilationUnit getTheOptionalPackageSpecification() {
    return this.theOptionalPackageSpecification;
  }

  public final String getThePackageName() {
    return this.thePackageName;
  }

  public final void setTheOptionalPackageBody(
      final SPARKCompilationUnit theOptionalPackageBody) {
    this.theOptionalPackageBody = theOptionalPackageBody;
  }

  public final void setTheOptionalPackageSpecification(
      final SPARKCompilationUnit theOptionalPackageSpecification) {
    this.theOptionalPackageSpecification = theOptionalPackageSpecification;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePackageName(final String thePackageName) {
    assert thePackageName != null;
    if (thePackageName != null) {
      this.thePackageName = thePackageName.intern();
    } else {
      this.thePackageName = null;
    }
  }
}
