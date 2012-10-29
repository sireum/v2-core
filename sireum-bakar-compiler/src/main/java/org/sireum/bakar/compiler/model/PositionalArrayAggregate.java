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

public final class PositionalArrayAggregate extends ArrayAggregate {
  protected ArrayList<AggregateItem> theAggregateItems;

  public final static int DESCRIPTOR = 53;

  public PositionalArrayAggregate() {
  }

  @Override
  public final int getDescriptor() {
    return PositionalArrayAggregate.DESCRIPTOR;
  }

  public final ArrayList<AggregateItem> getTheAggregateItems() {
    return this.theAggregateItems;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheAggregateItems(
      final ArrayList<AggregateItem> theAggregateItems) {
    assert theAggregateItems != null;

    assert Util.nonNullElements(theAggregateItems);
    this.theAggregateItems = theAggregateItems;
  }
}
