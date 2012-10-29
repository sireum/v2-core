/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class ArrayAggregateItem extends AggregateItem {
  protected ArrayAggregate theArrayAggregate;

  public final static int DESCRIPTOR = 57;

  public ArrayAggregateItem() {
  }

  @Override
  public final int getDescriptor() {
    return ArrayAggregateItem.DESCRIPTOR;
  }

  public final ArrayAggregate getTheArrayAggregate() {
    return this.theArrayAggregate;
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
}
