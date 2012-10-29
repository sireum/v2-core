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

public final class GlobalDefinition extends Node {
  protected ArrayList<GlobalDeclaration> theGlobalDeclarations;

  public final static int DESCRIPTOR = 94;

  public GlobalDefinition() {
  }

  @Override
  public final int getDescriptor() {
    return GlobalDefinition.DESCRIPTOR;
  }

  public final ArrayList<GlobalDeclaration> getTheGlobalDeclarations() {
    return this.theGlobalDeclarations;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheGlobalDeclarations(
      final ArrayList<GlobalDeclaration> theGlobalDeclarations) {
    assert theGlobalDeclarations != null;

    assert Util.nonNullElements(theGlobalDeclarations);
    this.theGlobalDeclarations = theGlobalDeclarations;
  }
}
