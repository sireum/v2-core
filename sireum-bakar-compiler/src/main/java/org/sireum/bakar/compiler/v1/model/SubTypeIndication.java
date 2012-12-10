/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class SubTypeIndication extends Node {
  protected Name theName;

  protected Constraint theOptionalConstraint;

  public final static int DESCRIPTOR = 7;

  public SubTypeIndication() {
  }

  @Override
  public final int getDescriptor() {
    return SubTypeIndication.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final Constraint getTheOptionalConstraint() {
    return this.theOptionalConstraint;
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

  public final void setTheOptionalConstraint(
      final Constraint theOptionalConstraint) {
    this.theOptionalConstraint = theOptionalConstraint;
  }
}
