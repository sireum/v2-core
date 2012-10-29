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

public final class PositionalRecordExtensionAggregate extends
    RecordExtensionAggregate {
  protected ArrayList<Exp> theExps;

  public final static int DESCRIPTOR = 50;

  public PositionalRecordExtensionAggregate() {
  }

  @Override
  public final int getDescriptor() {
    return PositionalRecordExtensionAggregate.DESCRIPTOR;
  }

  public final ArrayList<Exp> getTheExps() {
    return this.theExps;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheExps(final ArrayList<Exp> theExps) {
    assert theExps != null;

    assert Util.nonNullElements(theExps);
    this.theExps = theExps;
  }
}
