/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class SymRefinementAnnotation extends SymAnnotation {
  protected SymOwnVariableAnnotation theOwn;

  protected ArrayList<SymModeAnnotation> theConstituents = new ArrayList<SymModeAnnotation>();

  public final static int DESCRIPTOR = 7;

  public SymRefinementAnnotation() {
  }

  @Override
  public final int getDescriptor() {
    return SymRefinementAnnotation.DESCRIPTOR;
  }

  public final ArrayList<SymModeAnnotation> getTheConstituents() {
    return this.theConstituents;
  }

  public final SymOwnVariableAnnotation getTheOwn() {
    return this.theOwn;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new ArrayList&lt;SymModeAnnotation&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheConstituents(
      final ArrayList<SymModeAnnotation> theConstituents) {
    assert Util.nonNullElements(theConstituents);
    this.theConstituents = theConstituents;
  }

  public final void setTheOwn(final SymOwnVariableAnnotation theOwn) {
    this.theOwn = theOwn;
  }
}
