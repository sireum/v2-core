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

public final class CaseStatementAlternative extends Node {
  protected ArrayList<Choice> theChoices;

  protected StatementList theStatementList;

  protected org.sireum.bakar.selection.model.RegionSelection theBodySelection;

  protected org.sireum.bakar.selection.model.RegionSelection theChoiceListSelection;

  public final static int DESCRIPTOR = 83;

  public CaseStatementAlternative() {
  }

  @Override
  public final int getDescriptor() {
    return CaseStatementAlternative.DESCRIPTOR;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheBodySelection() {
    return this.theBodySelection;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheChoiceListSelection() {
    return this.theChoiceListSelection;
  }

  public final ArrayList<Choice> getTheChoices() {
    return this.theChoices;
  }

  public final StatementList getTheStatementList() {
    return this.theStatementList;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheBodySelection(
      final org.sireum.bakar.selection.model.RegionSelection theBodySelection) {
    assert theBodySelection != null;
    this.theBodySelection = theBodySelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheChoiceListSelection(
      final org.sireum.bakar.selection.model.RegionSelection theChoiceListSelection) {
    assert theChoiceListSelection != null;
    this.theChoiceListSelection = theChoiceListSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheChoices(final ArrayList<Choice> theChoices) {
    assert theChoices != null;

    assert Util.nonNullElements(theChoices);
    this.theChoices = theChoices;
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
