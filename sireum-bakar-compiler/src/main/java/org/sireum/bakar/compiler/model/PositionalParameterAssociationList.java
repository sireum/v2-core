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

public final class PositionalParameterAssociationList extends
    ParameterAssociationList {
  protected ArrayList<Exp> theParameterAssociations;

  public final static int DESCRIPTOR = 111;

  public PositionalParameterAssociationList() {
  }

  @Override
  public final int getDescriptor() {
    return PositionalParameterAssociationList.DESCRIPTOR;
  }

  public final ArrayList<Exp> getTheParameterAssociations() {
    return this.theParameterAssociations;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheParameterAssociations(
      final ArrayList<Exp> theParameterAssociations) {
    assert theParameterAssociations != null;

    assert Util.nonNullElements(theParameterAssociations);
    this.theParameterAssociations = theParameterAssociations;
  }
}
