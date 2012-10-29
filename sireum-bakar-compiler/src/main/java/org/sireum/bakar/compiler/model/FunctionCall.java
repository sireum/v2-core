/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class FunctionCall extends Name {
  protected Name theName;

  protected ParameterAssociationList theOptionalParameterAssociationList;

  public final static int DESCRIPTOR = 108;

  public FunctionCall() {
  }

  @Override
  public final int getDescriptor() {
    return FunctionCall.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final ParameterAssociationList getTheOptionalParameterAssociationList() {
    return this.theOptionalParameterAssociationList;
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

  public final void setTheOptionalParameterAssociationList(
      final ParameterAssociationList theOptionalParameterAssociationList) {
    this.theOptionalParameterAssociationList = theOptionalParameterAssociationList;
  }
}
