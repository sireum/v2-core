/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class NameExp extends Exp {
  protected Name theName;

  public final static int DESCRIPTOR = 68;

  public NameExp() {
  }

  @Override
  public final int getDescriptor() {
    return NameExp.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
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
