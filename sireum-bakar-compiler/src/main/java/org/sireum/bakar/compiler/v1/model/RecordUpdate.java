/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Pair;
import org.sireum.bakar.util.Util;

public final class RecordUpdate extends Name {
  protected Name theName;

  protected ArrayList<Pair<String, Exp>> theSelectorToExpressionList;

  public final static int DESCRIPTOR = 40;

  public RecordUpdate() {
  }

  @Override
  public final int getDescriptor() {
    return RecordUpdate.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final ArrayList<Pair<String, Exp>> getTheSelectorToExpressionList() {
    return this.theSelectorToExpressionList;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheName(final Name theName) {
    assert theName != null;
    this.theName = theName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheSelectorToExpressionList(
      final ArrayList<Pair<String, Exp>> theSelectorToExpressionList) {
    assert theSelectorToExpressionList != null;

    assert Util.nonNullElements(theSelectorToExpressionList);
    this.theSelectorToExpressionList = theSelectorToExpressionList;
  }
}
