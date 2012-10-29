/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class ContainerDependencyClause extends Container {
  protected ArrayList<SymDerivesAnnotationModel> theDependencyClauseMembers;

  protected org.sireum.bakar.selection.model.RegionSelection theDependencyClauseRegionSelection;

  public final static int DESCRIPTOR = 6;

  public ContainerDependencyClause() {
  }

  @Override
  public final int getDescriptor() {
    return ContainerDependencyClause.DESCRIPTOR;
  }

  public final ArrayList<SymDerivesAnnotationModel> getTheDependencyClauseMembers() {
    return this.theDependencyClauseMembers;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheDependencyClauseRegionSelection() {
    return this.theDependencyClauseRegionSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheDependencyClauseMembers(
      final ArrayList<SymDerivesAnnotationModel> theDependencyClauseMembers) {
    assert theDependencyClauseMembers != null;

    assert Util.nonNullElements(theDependencyClauseMembers);
    this.theDependencyClauseMembers = theDependencyClauseMembers;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheDependencyClauseRegionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theDependencyClauseRegionSelection) {
    assert theDependencyClauseRegionSelection != null;
    this.theDependencyClauseRegionSelection = theDependencyClauseRegionSelection;
  }
}
