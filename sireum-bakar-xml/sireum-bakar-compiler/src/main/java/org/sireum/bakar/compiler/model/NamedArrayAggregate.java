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

public final class NamedArrayAggregate extends ArrayAggregate {
  protected ArrayList<ArrayComponentAssociation> theOptionalArrayComponentAssociations;

  public final static int DESCRIPTOR = 54;

  public NamedArrayAggregate() {
  }

  @Override
  public final int getDescriptor() {
    return NamedArrayAggregate.DESCRIPTOR;
  }

  public final ArrayList<ArrayComponentAssociation> getTheOptionalArrayComponentAssociations() {
    return this.theOptionalArrayComponentAssociations;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalArrayComponentAssociations(
      final ArrayList<ArrayComponentAssociation> theOptionalArrayComponentAssociations) {
    assert Util.nonNullElements(theOptionalArrayComponentAssociations);
    this.theOptionalArrayComponentAssociations = theOptionalArrayComponentAssociations;
  }
}
