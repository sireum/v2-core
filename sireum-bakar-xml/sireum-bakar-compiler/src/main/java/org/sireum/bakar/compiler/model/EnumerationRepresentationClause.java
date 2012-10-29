/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class EnumerationRepresentationClause extends RepresentationClause {
  protected Name theName;

  protected ArrayAggregate theArrayAggregate;

  public final static int DESCRIPTOR = 142;

  public EnumerationRepresentationClause() {
  }

  @Override
  public final int getDescriptor() {
    return EnumerationRepresentationClause.DESCRIPTOR;
  }

  public final ArrayAggregate getTheArrayAggregate() {
    return this.theArrayAggregate;
  }

  public final Name getTheName() {
    return this.theName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheArrayAggregate(final ArrayAggregate theArrayAggregate) {
    assert theArrayAggregate != null;
    this.theArrayAggregate = theArrayAggregate;
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
}
