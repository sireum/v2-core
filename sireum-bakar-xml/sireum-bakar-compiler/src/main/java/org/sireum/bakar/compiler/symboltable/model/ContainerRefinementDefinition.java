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

public final class ContainerRefinementDefinition extends Container {
  protected ArrayList<ContainerRefinementClause> theRefinementClauses;

  protected org.sireum.bakar.selection.model.RegionSelection theRefinementDefinitionSelection;

  public final static int DESCRIPTOR = 3;

  public ContainerRefinementDefinition() {
  }

  @Override
  public final int getDescriptor() {
    return ContainerRefinementDefinition.DESCRIPTOR;
  }

  public final ArrayList<ContainerRefinementClause> getTheRefinementClauses() {
    return this.theRefinementClauses;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheRefinementDefinitionSelection() {
    return this.theRefinementDefinitionSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheRefinementClauses(
      final ArrayList<ContainerRefinementClause> theRefinementClauses) {
    assert theRefinementClauses != null;

    assert Util.nonNullElements(theRefinementClauses);
    this.theRefinementClauses = theRefinementClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheRefinementDefinitionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theRefinementDefinitionSelection) {
    assert theRefinementDefinitionSelection != null;
    this.theRefinementDefinitionSelection = theRefinementDefinitionSelection;
  }
}
