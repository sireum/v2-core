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

public final class IndexConstraint extends Constraint {
  protected ArrayList<Name> theDiscreteSubTypeNames;

  public final static int DESCRIPTOR = 19;

  public IndexConstraint() {
  }

  @Override
  public final int getDescriptor() {
    return IndexConstraint.DESCRIPTOR;
  }

  public final ArrayList<Name> getTheDiscreteSubTypeNames() {
    return this.theDiscreteSubTypeNames;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheDiscreteSubTypeNames(
      final ArrayList<Name> theDiscreteSubTypeNames) {
    assert theDiscreteSubTypeNames != null;

    assert Util.nonNullElements(theDiscreteSubTypeNames);
    this.theDiscreteSubTypeNames = theDiscreteSubTypeNames;
  }
}
