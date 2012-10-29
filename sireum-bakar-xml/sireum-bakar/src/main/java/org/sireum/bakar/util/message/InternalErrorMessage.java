/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.util.message;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class InternalErrorMessage extends Message {
  protected ArrayList<StackTraceElement> stackTraces;

  public final static int DESCRIPTOR = 4;

  public InternalErrorMessage() {
  }

  @Override
  public final int getDescriptor() {
    return InternalErrorMessage.DESCRIPTOR;
  }

  public final ArrayList<StackTraceElement> getStackTraces() {
    return this.stackTraces;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setStackTraces(
      final ArrayList<StackTraceElement> stackTraces) {
    assert stackTraces != null;

    assert Util.nonNullElements(stackTraces);
    this.stackTraces = stackTraces;
  }
}
