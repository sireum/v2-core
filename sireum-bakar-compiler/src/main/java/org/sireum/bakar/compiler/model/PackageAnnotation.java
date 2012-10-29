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

public final class PackageAnnotation extends Node {
  protected ArrayList<OwnVariableSpecification> theOptionalOwnVariables;

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalOwnVariablesRegionSelection;

  protected ArrayList<OwnVariable> theOptionalInitializeVariables;

  public final static int DESCRIPTOR = 115;

  public PackageAnnotation() {
  }

  @Override
  public final int getDescriptor() {
    return PackageAnnotation.DESCRIPTOR;
  }

  public final ArrayList<OwnVariable> getTheOptionalInitializeVariables() {
    return this.theOptionalInitializeVariables;
  }

  public final ArrayList<OwnVariableSpecification> getTheOptionalOwnVariables() {
    return this.theOptionalOwnVariables;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalOwnVariablesRegionSelection() {
    return this.theOptionalOwnVariablesRegionSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalInitializeVariables(
      final ArrayList<OwnVariable> theOptionalInitializeVariables) {
    assert Util.nonNullElements(theOptionalInitializeVariables);
    this.theOptionalInitializeVariables = theOptionalInitializeVariables;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalOwnVariables(
      final ArrayList<OwnVariableSpecification> theOptionalOwnVariables) {
    assert Util.nonNullElements(theOptionalOwnVariables);
    this.theOptionalOwnVariables = theOptionalOwnVariables;
  }

  public final void setTheOptionalOwnVariablesRegionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalOwnVariablesRegionSelection) {
    this.theOptionalOwnVariablesRegionSelection = theOptionalOwnVariablesRegionSelection;
  }
}
