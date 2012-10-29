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

public final class CodeSubProgramImplementation extends
    SubProgramImplementation {
  protected ArrayList<QualifiedExp> theQualifiedExps;

  public final static int DESCRIPTOR = 106;

  public CodeSubProgramImplementation() {
  }

  @Override
  public final int getDescriptor() {
    return CodeSubProgramImplementation.DESCRIPTOR;
  }

  public final ArrayList<QualifiedExp> getTheQualifiedExps() {
    return this.theQualifiedExps;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheQualifiedExps(
      final ArrayList<QualifiedExp> theQualifiedExps) {
    assert theQualifiedExps != null;

    assert Util.nonNullElements(theQualifiedExps);
    this.theQualifiedExps = theQualifiedExps;
  }
}
