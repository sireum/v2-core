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

public final class Pragma extends Node {
  protected String theIDString;

  protected ArrayList<PragmaArgumentAssociation> theOptionalPragmaArgumentAssociations;

  protected Object dummyObjectToGetListVisitor;

  public final static int DESCRIPTOR = 1;

  public Pragma() {
  }

  @Override
  public final int getDescriptor() {
    return Pragma.DESCRIPTOR;
  }

  public final Object getDummyObjectToGetListVisitor() {
    return this.dummyObjectToGetListVisitor;
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  public final ArrayList<PragmaArgumentAssociation> getTheOptionalPragmaArgumentAssociations() {
    return this.theOptionalPragmaArgumentAssociations;
  }

  public final void setDummyObjectToGetListVisitor(
      final Object dummyObjectToGetListVisitor) {
    this.dummyObjectToGetListVisitor = dummyObjectToGetListVisitor;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheIDString(final String theIDString) {
    assert theIDString != null;
    if (theIDString != null) {
      this.theIDString = theIDString.intern();
    } else {
      this.theIDString = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalPragmaArgumentAssociations(
      final ArrayList<PragmaArgumentAssociation> theOptionalPragmaArgumentAssociations) {
    assert Util.nonNullElements(theOptionalPragmaArgumentAssociations);
    this.theOptionalPragmaArgumentAssociations = theOptionalPragmaArgumentAssociations;
  }
}
