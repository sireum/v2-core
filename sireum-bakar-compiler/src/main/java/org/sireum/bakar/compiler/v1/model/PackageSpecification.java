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

public final class PackageSpecification extends Node {
  protected Name theName;

  protected PackageAnnotation thePackageAnnotation;

  protected ArrayList<RenamingDeclaration> theOptionalVisiblePartDeclaration;

  protected ArrayList<DeclarativePartMember> theOptionalVisiblePartDeclarativePartMember;

  protected ArrayList<RenamingDeclaration> theOptionalPrivatePartDeclaration;

  protected ArrayList<DeclarativePartMember> theOptionalPrivatePartDeclarativePartMember;

  public final static int DESCRIPTOR = 114;

  public PackageSpecification() {
  }

  @Override
  public final int getDescriptor() {
    return PackageSpecification.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final ArrayList<RenamingDeclaration> getTheOptionalPrivatePartDeclaration() {
    return this.theOptionalPrivatePartDeclaration;
  }

  public final ArrayList<DeclarativePartMember> getTheOptionalPrivatePartDeclarativePartMember() {
    return this.theOptionalPrivatePartDeclarativePartMember;
  }

  public final ArrayList<RenamingDeclaration> getTheOptionalVisiblePartDeclaration() {
    return this.theOptionalVisiblePartDeclaration;
  }

  public final ArrayList<DeclarativePartMember> getTheOptionalVisiblePartDeclarativePartMember() {
    return this.theOptionalVisiblePartDeclarativePartMember;
  }

  public final PackageAnnotation getThePackageAnnotation() {
    return this.thePackageAnnotation;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheName(final Name theName) {
    assert theName != null;
    this.theName = theName;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalPrivatePartDeclaration(
      final ArrayList<RenamingDeclaration> theOptionalPrivatePartDeclaration) {
    assert Util.nonNullElements(theOptionalPrivatePartDeclaration);
    this.theOptionalPrivatePartDeclaration = theOptionalPrivatePartDeclaration;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalPrivatePartDeclarativePartMember(
      final ArrayList<DeclarativePartMember> theOptionalPrivatePartDeclarativePartMember) {
    assert Util.nonNullElements(theOptionalPrivatePartDeclarativePartMember);
    this.theOptionalPrivatePartDeclarativePartMember = theOptionalPrivatePartDeclarativePartMember;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalVisiblePartDeclaration(
      final ArrayList<RenamingDeclaration> theOptionalVisiblePartDeclaration) {
    assert Util.nonNullElements(theOptionalVisiblePartDeclaration);
    this.theOptionalVisiblePartDeclaration = theOptionalVisiblePartDeclaration;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalVisiblePartDeclarativePartMember(
      final ArrayList<DeclarativePartMember> theOptionalVisiblePartDeclarativePartMember) {
    assert Util.nonNullElements(theOptionalVisiblePartDeclarativePartMember);
    this.theOptionalVisiblePartDeclarativePartMember = theOptionalVisiblePartDeclarativePartMember;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePackageAnnotation(
      final PackageAnnotation thePackageAnnotation) {
    assert thePackageAnnotation != null;
    this.thePackageAnnotation = thePackageAnnotation;
  }
}
