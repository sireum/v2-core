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

public final class RecordRepresentationClause extends RepresentationClause {
  protected Name theName;

  protected Exp theOptionalExp;

  protected ArrayList<ComponentClause> theOptionalComponentClauses;

  public final static int DESCRIPTOR = 144;

  public RecordRepresentationClause() {
  }

  @Override
  public final int getDescriptor() {
    return RecordRepresentationClause.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final ArrayList<ComponentClause> getTheOptionalComponentClauses() {
    return this.theOptionalComponentClauses;
  }

  public final Exp getTheOptionalExp() {
    return this.theOptionalExp;
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
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalComponentClauses(
      final ArrayList<ComponentClause> theOptionalComponentClauses) {
    assert Util.nonNullElements(theOptionalComponentClauses);
    this.theOptionalComponentClauses = theOptionalComponentClauses;
  }

  public final void setTheOptionalExp(final Exp theOptionalExp) {
    this.theOptionalExp = theOptionalExp;
  }
}
