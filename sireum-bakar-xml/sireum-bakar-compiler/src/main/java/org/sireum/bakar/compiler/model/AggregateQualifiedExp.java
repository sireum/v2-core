/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class AggregateQualifiedExp extends QualifiedExp {
  protected Aggregate theAggregate;

  public final static int DESCRIPTOR = 72;

  public AggregateQualifiedExp() {
  }

  @Override
  public final int getDescriptor() {
    return AggregateQualifiedExp.DESCRIPTOR;
  }

  public final Aggregate getTheAggregate() {
    return this.theAggregate;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheAggregate(final Aggregate theAggregate) {
    assert theAggregate != null;
    this.theAggregate = theAggregate;
  }
}
