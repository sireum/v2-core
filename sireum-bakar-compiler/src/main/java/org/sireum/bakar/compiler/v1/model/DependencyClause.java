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

public final class DependencyClause extends Node {
  protected ArrayList<Name> theExportedVariables;

  protected boolean theImportStarFlag;

  protected ArrayList<Name> theOptionalImportedVariables;

  public final static int DESCRIPTOR = 102;

  public DependencyClause() {
  }

  @Override
  public final int getDescriptor() {
    return DependencyClause.DESCRIPTOR;
  }

  public final ArrayList<Name> getTheExportedVariables() {
    return this.theExportedVariables;
  }

  public final boolean getTheImportStarFlag() {
    return this.theImportStarFlag;
  }

  public final ArrayList<Name> getTheOptionalImportedVariables() {
    return this.theOptionalImportedVariables;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheExportedVariables(
      final ArrayList<Name> theExportedVariables) {
    assert theExportedVariables != null;

    assert Util.nonNullElements(theExportedVariables);
    this.theExportedVariables = theExportedVariables;
  }

  public final void setTheImportStarFlag(final boolean theImportStarFlag) {
    this.theImportStarFlag = theImportStarFlag;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalImportedVariables(
      final ArrayList<Name> theOptionalImportedVariables) {
    assert Util.nonNullElements(theOptionalImportedVariables);
    this.theOptionalImportedVariables = theOptionalImportedVariables;
  }
}
