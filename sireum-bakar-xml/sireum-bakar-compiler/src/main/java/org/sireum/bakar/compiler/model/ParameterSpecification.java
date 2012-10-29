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

public final class ParameterSpecification extends Node {
  protected ArrayList<IDName> theParameterNames;

  protected Mode theMode;

  protected Name theSubtypeMark;

  public final static int DESCRIPTOR = 92;

  public ParameterSpecification() {
  }

  @Override
  public final int getDescriptor() {
    return ParameterSpecification.DESCRIPTOR;
  }

  public final Mode getTheMode() {
    return this.theMode;
  }

  public final ArrayList<IDName> getTheParameterNames() {
    return this.theParameterNames;
  }

  public final Name getTheSubtypeMark() {
    return this.theSubtypeMark;
  }

  public final void setTheMode(final Mode theMode) {
    this.theMode = theMode;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheParameterNames(
      final ArrayList<IDName> theParameterNames) {
    assert theParameterNames != null;

    assert Util.nonNullElements(theParameterNames);
    this.theParameterNames = theParameterNames;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSubtypeMark(final Name theSubtypeMark) {
    assert theSubtypeMark != null;
    this.theSubtypeMark = theSubtypeMark;
  }
}
