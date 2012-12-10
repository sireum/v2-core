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

public final class GlobalDeclaration extends Node {
  protected GlobalMode theMode;

  protected ArrayList<Name> theNames;

  public final static int DESCRIPTOR = 99;

  public GlobalDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return GlobalDeclaration.DESCRIPTOR;
  }

  public final GlobalMode getTheMode() {
    return this.theMode;
  }

  public final ArrayList<Name> getTheNames() {
    return this.theNames;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheMode(final GlobalMode theMode) {
    assert theMode != null;
    this.theMode = theMode;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheNames(final ArrayList<Name> theNames) {
    assert theNames != null;

    assert Util.nonNullElements(theNames);
    this.theNames = theNames;
  }
}
