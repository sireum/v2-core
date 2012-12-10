/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class ContextClause extends Node {
  protected ArrayList<ContextItem> theOptionalContextItems;

  public final static int DESCRIPTOR = 134;

  public ContextClause() {
  }

  @Override
  public final int getDescriptor() {
    return ContextClause.DESCRIPTOR;
  }

  public final ArrayList<ContextItem> getTheOptionalContextItems() {
    return this.theOptionalContextItems;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalContextItems(
      final ArrayList<ContextItem> theOptionalContextItems) {
    assert Util.nonNullElements(theOptionalContextItems);
    this.theOptionalContextItems = theOptionalContextItems;
  }
}
