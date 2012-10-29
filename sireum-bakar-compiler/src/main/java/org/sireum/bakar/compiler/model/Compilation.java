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

public final class Compilation extends Node {
  protected ArrayList<CompilationUnit> theOptionalCompilationUnits;

  public final static int DESCRIPTOR = 129;

  public Compilation() {
  }

  @Override
  public final int getDescriptor() {
    return Compilation.DESCRIPTOR;
  }

  public final ArrayList<CompilationUnit> getTheOptionalCompilationUnits() {
    return this.theOptionalCompilationUnits;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalCompilationUnits(
      final ArrayList<CompilationUnit> theOptionalCompilationUnits) {
    assert Util.nonNullElements(theOptionalCompilationUnits);
    this.theOptionalCompilationUnits = theOptionalCompilationUnits;
  }
}
