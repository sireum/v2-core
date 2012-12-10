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

public final class RefinementClause extends Node {
  protected IDName theSubject;

  protected ArrayList<Constituent> theConstituents;

  public final static int DESCRIPTOR = 119;

  public RefinementClause() {
  }

  @Override
  public final int getDescriptor() {
    return RefinementClause.DESCRIPTOR;
  }

  public final ArrayList<Constituent> getTheConstituents() {
    return this.theConstituents;
  }

  public final IDName getTheSubject() {
    return this.theSubject;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheConstituents(
      final ArrayList<Constituent> theConstituents) {
    assert theConstituents != null;

    assert Util.nonNullElements(theConstituents);
    this.theConstituents = theConstituents;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSubject(final IDName theSubject) {
    assert theSubject != null;
    this.theSubject = theSubject;
  }
}
