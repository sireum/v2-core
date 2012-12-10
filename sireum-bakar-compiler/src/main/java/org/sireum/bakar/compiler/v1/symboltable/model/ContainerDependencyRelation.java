/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class ContainerDependencyRelation extends Container {
  protected ArrayList<ContainerDependencyClause> theDependencyClauses;

  protected org.sireum.bakar.selection.model.RegionSelection theDependencyRelationRegionSelection;

  public final static int DESCRIPTOR = 5;

  public ContainerDependencyRelation() {
  }

  @Override
  public final int getDescriptor() {
    return ContainerDependencyRelation.DESCRIPTOR;
  }

  public final ArrayList<ContainerDependencyClause> getTheDependencyClauses() {
    return this.theDependencyClauses;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheDependencyRelationRegionSelection() {
    return this.theDependencyRelationRegionSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheDependencyClauses(
      final ArrayList<ContainerDependencyClause> theDependencyClauses) {
    assert theDependencyClauses != null;

    assert Util.nonNullElements(theDependencyClauses);
    this.theDependencyClauses = theDependencyClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheDependencyRelationRegionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theDependencyRelationRegionSelection) {
    assert theDependencyRelationRegionSelection != null;
    this.theDependencyRelationRegionSelection = theDependencyRelationRegionSelection;
  }
}
