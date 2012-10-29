/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public abstract class LoopStatement extends CompoundStatement {
  protected String theOptionalIDString;

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalIDRegionSelection;

  protected org.sireum.bakar.selection.model.RegionSelection theLoopKeywordRegionSelection;

  protected AssertStatement theOptionalLoopInvariant;

  protected StatementList theStatementList;

  public LoopStatement() {
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheLoopKeywordRegionSelection() {
    return this.theLoopKeywordRegionSelection;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalIDRegionSelection() {
    return this.theOptionalIDRegionSelection;
  }

  public final String getTheOptionalIDString() {
    return this.theOptionalIDString;
  }

  public final AssertStatement getTheOptionalLoopInvariant() {
    return this.theOptionalLoopInvariant;
  }

  public final StatementList getTheStatementList() {
    return this.theStatementList;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheLoopKeywordRegionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theLoopKeywordRegionSelection) {
    assert theLoopKeywordRegionSelection != null;
    this.theLoopKeywordRegionSelection = theLoopKeywordRegionSelection;
  }

  public final void setTheOptionalIDRegionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalIDRegionSelection) {
    this.theOptionalIDRegionSelection = theOptionalIDRegionSelection;
  }

  public final void setTheOptionalIDString(final String theOptionalIDString) {
    if (theOptionalIDString != null) {
      this.theOptionalIDString = theOptionalIDString.intern();
    } else {
      this.theOptionalIDString = null;
    }
  }

  public final void setTheOptionalLoopInvariant(
      final AssertStatement theOptionalLoopInvariant) {
    this.theOptionalLoopInvariant = theOptionalLoopInvariant;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheStatementList(final StatementList theStatementList) {
    assert theStatementList != null;
    this.theStatementList = theStatementList;
  }

}
