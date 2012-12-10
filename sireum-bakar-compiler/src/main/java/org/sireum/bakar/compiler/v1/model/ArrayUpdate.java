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

public final class ArrayUpdate extends Name {
  protected Name theName;

  protected ArrayList<Pair<ArrayList<Exp>, Exp>> theIndexListToExpressionList;

  public final static int DESCRIPTOR = 41;

  public ArrayUpdate() {
  }

  @Override
  public final int getDescriptor() {
    return ArrayUpdate.DESCRIPTOR;
  }

  public final ArrayList<Pair<ArrayList<Exp>, Exp>> getTheIndexListToExpressionList() {
    return this.theIndexListToExpressionList;
  }

  public final Name getTheName() {
    return this.theName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheIndexListToExpressionList(
      final ArrayList<Pair<ArrayList<Exp>, Exp>> theIndexListToExpressionList) {
    assert theIndexListToExpressionList != null;

    assert Util.nonNullElements(theIndexListToExpressionList);
    this.theIndexListToExpressionList = theIndexListToExpressionList;
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
}
