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

public final class DependencyRelation extends Node {
  protected ArrayList<DependencyClause> theOptionalDependencyClauses;

  protected ArrayList<Name> theOptionalNullDependencyVariables;

  public final static int DESCRIPTOR = 101;

  public DependencyRelation() {
  }

  @Override
  public final int getDescriptor() {
    return DependencyRelation.DESCRIPTOR;
  }

  public final ArrayList<DependencyClause> getTheOptionalDependencyClauses() {
    return this.theOptionalDependencyClauses;
  }

  public final ArrayList<Name> getTheOptionalNullDependencyVariables() {
    return this.theOptionalNullDependencyVariables;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalDependencyClauses(
      final ArrayList<DependencyClause> theOptionalDependencyClauses) {
    assert Util.nonNullElements(theOptionalDependencyClauses);
    this.theOptionalDependencyClauses = theOptionalDependencyClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalNullDependencyVariables(
      final ArrayList<Name> theOptionalNullDependencyVariables) {
    assert Util.nonNullElements(theOptionalNullDependencyVariables);
    this.theOptionalNullDependencyVariables = theOptionalNullDependencyVariables;
  }
}
