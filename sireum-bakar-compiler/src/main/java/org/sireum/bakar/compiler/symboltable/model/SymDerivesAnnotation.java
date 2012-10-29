/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class SymDerivesAnnotation extends SymDerivesAnnotationModel {
  protected SymSimpleAnnotation theOutVar;

  protected ArrayList<SymSimpleAnnotation> theInVars = new ArrayList<SymSimpleAnnotation>();

  public final static int DESCRIPTOR = 4;

  public SymDerivesAnnotation() {
  }

  @Override
  public final int getDescriptor() {
    return SymDerivesAnnotation.DESCRIPTOR;
  }

  public final ArrayList<SymSimpleAnnotation> getTheInVars() {
    return this.theInVars;
  }

  public final SymSimpleAnnotation getTheOutVar() {
    return this.theOutVar;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new ArrayList&lt;SymSimpleAnnotation&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheInVars(final ArrayList<SymSimpleAnnotation> theInVars) {
    assert Util.nonNullElements(theInVars);
    this.theInVars = theInVars;
  }

  public final void setTheOutVar(final SymSimpleAnnotation theOutVar) {
    this.theOutVar = theOutVar;
  }
}
