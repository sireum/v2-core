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

public final class ArrayComponentAssociation extends Node {
  protected ArrayList<Choice> theChoices;

  protected AggregateItem theAggregateItem;

  public final static int DESCRIPTOR = 55;

  public ArrayComponentAssociation() {
  }

  @Override
  public final int getDescriptor() {
    return ArrayComponentAssociation.DESCRIPTOR;
  }

  public final AggregateItem getTheAggregateItem() {
    return this.theAggregateItem;
  }

  public final ArrayList<Choice> getTheChoices() {
    return this.theChoices;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheAggregateItem(final AggregateItem theAggregateItem) {
    assert theAggregateItem != null;
    this.theAggregateItem = theAggregateItem;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheChoices(final ArrayList<Choice> theChoices) {
    assert theChoices != null;

    assert Util.nonNullElements(theChoices);
    this.theChoices = theChoices;
  }
}
