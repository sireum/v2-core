/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public final class ContainerRefinementClause extends Container {
  protected SymRefinementAnnotation theRefinementClause;

  protected org.sireum.bakar.selection.model.RegionSelection theRefinementClauseSelection;

  public final static int DESCRIPTOR = 4;

  public ContainerRefinementClause() {
  }

  @Override
  public final int getDescriptor() {
    return ContainerRefinementClause.DESCRIPTOR;
  }

  public final SymRefinementAnnotation getTheRefinementClause() {
    return this.theRefinementClause;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheRefinementClauseSelection() {
    return this.theRefinementClauseSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheRefinementClause(
      final SymRefinementAnnotation theRefinementClause) {
    assert theRefinementClause != null;
    this.theRefinementClause = theRefinementClause;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheRefinementClauseSelection(
      final org.sireum.bakar.selection.model.RegionSelection theRefinementClauseSelection) {
    assert theRefinementClauseSelection != null;
    this.theRefinementClauseSelection = theRefinementClauseSelection;
  }
}
