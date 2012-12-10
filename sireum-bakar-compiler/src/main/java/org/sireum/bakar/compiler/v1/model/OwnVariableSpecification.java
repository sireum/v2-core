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

public final class OwnVariableSpecification extends Node {
  protected ArrayList<OwnVariable> theOwnVariables;

  protected Name theOptionalSubtypeMark;

  public final static int DESCRIPTOR = 117;

  public OwnVariableSpecification() {
  }

  @Override
  public final int getDescriptor() {
    return OwnVariableSpecification.DESCRIPTOR;
  }

  public final Name getTheOptionalSubtypeMark() {
    return this.theOptionalSubtypeMark;
  }

  public final ArrayList<OwnVariable> getTheOwnVariables() {
    return this.theOwnVariables;
  }

  public final void setTheOptionalSubtypeMark(final Name theOptionalSubtypeMark) {
    this.theOptionalSubtypeMark = theOptionalSubtypeMark;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOwnVariables(
      final ArrayList<OwnVariable> theOwnVariables) {
    assert theOwnVariables != null;

    assert Util.nonNullElements(theOwnVariables);
    this.theOwnVariables = theOwnVariables;
  }
}
