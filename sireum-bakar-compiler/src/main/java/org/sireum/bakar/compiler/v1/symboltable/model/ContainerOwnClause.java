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

public final class ContainerOwnClause extends Container {
  protected ArrayList<SymOwnVariableAnnotation> theOwnVariables;

  protected SymDef theOptionalType;

  protected org.sireum.bakar.selection.model.RegionSelection theOwnClauseSelection;

  public final static int DESCRIPTOR = 2;

  public ContainerOwnClause() {
  }

  @Override
  public final int getDescriptor() {
    return ContainerOwnClause.DESCRIPTOR;
  }

  public final SymDef getTheOptionalType() {
    return this.theOptionalType;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOwnClauseSelection() {
    return this.theOwnClauseSelection;
  }

  public final ArrayList<SymOwnVariableAnnotation> getTheOwnVariables() {
    return this.theOwnVariables;
  }

  public final void setTheOptionalType(final SymDef theOptionalType) {
    this.theOptionalType = theOptionalType;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheOwnClauseSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOwnClauseSelection) {
    assert theOwnClauseSelection != null;
    this.theOwnClauseSelection = theOwnClauseSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOwnVariables(
      final ArrayList<SymOwnVariableAnnotation> theOwnVariables) {
    assert theOwnVariables != null;

    assert Util.nonNullElements(theOwnVariables);
    this.theOwnVariables = theOwnVariables;
  }
}
