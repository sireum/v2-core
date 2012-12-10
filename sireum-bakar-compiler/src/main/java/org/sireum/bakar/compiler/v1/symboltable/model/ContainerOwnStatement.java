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

public final class ContainerOwnStatement extends Container {
  protected ArrayList<ContainerOwnClause> theOwnClauses;

  protected org.sireum.bakar.selection.model.RegionSelection theOwnStatementSelection;

  public final static int DESCRIPTOR = 1;

  public ContainerOwnStatement() {
  }

  @Override
  public final int getDescriptor() {
    return ContainerOwnStatement.DESCRIPTOR;
  }

  public final ArrayList<ContainerOwnClause> getTheOwnClauses() {
    return this.theOwnClauses;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOwnStatementSelection() {
    return this.theOwnStatementSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOwnClauses(
      final ArrayList<ContainerOwnClause> theOwnClauses) {
    assert theOwnClauses != null;

    assert Util.nonNullElements(theOwnClauses);
    this.theOwnClauses = theOwnClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheOwnStatementSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOwnStatementSelection) {
    assert theOwnStatementSelection != null;
    this.theOwnStatementSelection = theOwnStatementSelection;
  }
}
