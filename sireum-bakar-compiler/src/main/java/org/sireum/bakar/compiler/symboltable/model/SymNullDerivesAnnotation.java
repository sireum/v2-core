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

public final class SymNullDerivesAnnotation extends SymDerivesAnnotationModel {
  protected ArrayList<SymSimpleAnnotation> theInVars = new ArrayList<SymSimpleAnnotation>();

  public final static int DESCRIPTOR = 5;

  public SymNullDerivesAnnotation() {
  }

  @Override
  public final int getDescriptor() {
    return SymNullDerivesAnnotation.DESCRIPTOR;
  }

  public final ArrayList<SymSimpleAnnotation> getTheInVars() {
    return this.theInVars;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
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
    assert theInVars != null;

    assert Util.nonNullElements(theInVars);
    this.theInVars = theInVars;
  }
}
