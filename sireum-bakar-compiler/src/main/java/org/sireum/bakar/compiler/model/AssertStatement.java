/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class AssertStatement extends ProofStatement {
  protected Predicate thePredicate;

  public final static int DESCRIPTOR = 74;

  public AssertStatement() {
  }

  @Override
  public final int getDescriptor() {
    return AssertStatement.DESCRIPTOR;
  }

  public final Predicate getThePredicate() {
    return this.thePredicate;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePredicate(final Predicate thePredicate) {
    assert thePredicate != null;
    this.thePredicate = thePredicate;
  }
}
