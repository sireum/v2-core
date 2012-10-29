/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.module.util;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class SPARKCompilationUnit extends Node {
  protected String thePackageName;

  protected String theSparkSourceFilename;

  protected org.sireum.bakar.compiler.model.ContextClause theContextClause;

  protected org.sireum.bakar.compiler.model.CompilationUnit theCompilationUnit;

  protected SPARKCompilationUnitType theCompilationUnitType;

  protected SPARKLibraryItemType theOptionalLibraryItemType;

  protected ArrayList<String> theOptionalWithNames;

  protected ArrayList<String> theOptionalUseNames;

  protected ArrayList<String> theOptionalInheritNames;

  public final static int DESCRIPTOR = 2;

  public SPARKCompilationUnit() {
  }

  @Override
  public final int getDescriptor() {
    return SPARKCompilationUnit.DESCRIPTOR;
  }

  public final org.sireum.bakar.compiler.model.CompilationUnit getTheCompilationUnit() {
    return this.theCompilationUnit;
  }

  public final SPARKCompilationUnitType getTheCompilationUnitType() {
    return this.theCompilationUnitType;
  }

  public final org.sireum.bakar.compiler.model.ContextClause getTheContextClause() {
    return this.theContextClause;
  }

  public final ArrayList<String> getTheOptionalInheritNames() {
    return this.theOptionalInheritNames;
  }

  public final SPARKLibraryItemType getTheOptionalLibraryItemType() {
    return this.theOptionalLibraryItemType;
  }

  public final ArrayList<String> getTheOptionalUseNames() {
    return this.theOptionalUseNames;
  }

  public final ArrayList<String> getTheOptionalWithNames() {
    return this.theOptionalWithNames;
  }

  public final String getThePackageName() {
    return this.thePackageName;
  }

  public final String getTheSparkSourceFilename() {
    return this.theSparkSourceFilename;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheCompilationUnit(
      final org.sireum.bakar.compiler.model.CompilationUnit theCompilationUnit) {
    assert theCompilationUnit != null;
    this.theCompilationUnit = theCompilationUnit;
  }

  public final void setTheCompilationUnitType(
      final SPARKCompilationUnitType theCompilationUnitType) {
    this.theCompilationUnitType = theCompilationUnitType;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheContextClause(
      final org.sireum.bakar.compiler.model.ContextClause theContextClause) {
    assert theContextClause != null;
    this.theContextClause = theContextClause;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalInheritNames(
      final ArrayList<String> theOptionalInheritNames) {
    assert Util.nonNullElements(theOptionalInheritNames);
    this.theOptionalInheritNames = theOptionalInheritNames;
  }

  public final void setTheOptionalLibraryItemType(
      final SPARKLibraryItemType theOptionalLibraryItemType) {
    this.theOptionalLibraryItemType = theOptionalLibraryItemType;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalUseNames(
      final ArrayList<String> theOptionalUseNames) {
    assert Util.nonNullElements(theOptionalUseNames);
    this.theOptionalUseNames = theOptionalUseNames;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalWithNames(
      final ArrayList<String> theOptionalWithNames) {
    assert Util.nonNullElements(theOptionalWithNames);
    this.theOptionalWithNames = theOptionalWithNames;
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

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSparkSourceFilename(
      final String theSparkSourceFilename) {
    assert theSparkSourceFilename != null;
    if (theSparkSourceFilename != null) {
      this.theSparkSourceFilename = theSparkSourceFilename.intern();
    } else {
      this.theSparkSourceFilename = null;
    }
  }
}
