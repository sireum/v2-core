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

public final class CaseStatement extends CompoundStatement {
  protected Exp theExp;

  protected ArrayList<CaseStatementAlternative> theCaseStatementAlternatives;

  protected StatementList theOptionalOthers;

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalOthersSelection;

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalOthersBodySelection;

  public final static int DESCRIPTOR = 82;

  public CaseStatement() {
  }

  @Override
  public final int getDescriptor() {
    return CaseStatement.DESCRIPTOR;
  }

  public final ArrayList<CaseStatementAlternative> getTheCaseStatementAlternatives() {
    return this.theCaseStatementAlternatives;
  }

  public final Exp getTheExp() {
    return this.theExp;
  }

  public final StatementList getTheOptionalOthers() {
    return this.theOptionalOthers;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalOthersBodySelection() {
    return this.theOptionalOthersBodySelection;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalOthersSelection() {
    return this.theOptionalOthersSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheCaseStatementAlternatives(
      final ArrayList<CaseStatementAlternative> theCaseStatementAlternatives) {
    assert theCaseStatementAlternatives != null;

    assert Util.nonNullElements(theCaseStatementAlternatives);
    this.theCaseStatementAlternatives = theCaseStatementAlternatives;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheExp(final Exp theExp) {
    assert theExp != null;
    this.theExp = theExp;
  }

  public final void setTheOptionalOthers(final StatementList theOptionalOthers) {
    this.theOptionalOthers = theOptionalOthers;
  }

  public final void setTheOptionalOthersBodySelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalOthersBodySelection) {
    this.theOptionalOthersBodySelection = theOptionalOthersBodySelection;
  }

  public final void setTheOptionalOthersSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalOthersSelection) {
    this.theOptionalOthersSelection = theOptionalOthersSelection;
  }
}
