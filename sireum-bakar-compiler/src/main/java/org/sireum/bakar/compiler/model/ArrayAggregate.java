/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public abstract class ArrayAggregate extends Aggregate {
  protected boolean theOthersFlag;

  protected AggregateItem theOptionalOthersAggregateItem;

  public ArrayAggregate() {
  }

  public final AggregateItem getTheOptionalOthersAggregateItem() {
    return this.theOptionalOthersAggregateItem;
  }

  public final boolean getTheOthersFlag() {
    return this.theOthersFlag;
  }

  public final void setTheOptionalOthersAggregateItem(
      final AggregateItem theOptionalOthersAggregateItem) {
    this.theOptionalOthersAggregateItem = theOptionalOthersAggregateItem;
  }

  public final void setTheOthersFlag(final boolean theOthersFlag) {
    this.theOthersFlag = theOthersFlag;
  }

}
