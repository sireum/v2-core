/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Triple;
import org.sireum.bakar.util.Util;

public final class IfStatement extends CompoundStatement {
  protected Exp theExp;

  protected StatementList theThen;

  protected org.sireum.bakar.selection.model.RegionSelection theIfRegionSelection;

  protected ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> theOptionalElseIfs;

  protected StatementList theOptionalElse;

  public final static int DESCRIPTOR = 81;

  public IfStatement() {
  }

  @Override
  public final int getDescriptor() {
    return IfStatement.DESCRIPTOR;
  }

  public final Exp getTheExp() {
    return this.theExp;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheIfRegionSelection() {
    return this.theIfRegionSelection;
  }

  public final StatementList getTheOptionalElse() {
    return this.theOptionalElse;
  }

  public final ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> getTheOptionalElseIfs() {
    return this.theOptionalElseIfs;
  }

  public final StatementList getTheThen() {
    return this.theThen;
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

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheIfRegionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theIfRegionSelection) {
    assert theIfRegionSelection != null;
    this.theIfRegionSelection = theIfRegionSelection;
  }

  public final void setTheOptionalElse(final StatementList theOptionalElse) {
    this.theOptionalElse = theOptionalElse;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalElseIfs(
      final ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> theOptionalElseIfs) {
    assert Util.nonNullElements(theOptionalElseIfs);
    this.theOptionalElseIfs = theOptionalElseIfs;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheThen(final StatementList theThen) {
    assert theThen != null;
    this.theThen = theThen;
  }
}
