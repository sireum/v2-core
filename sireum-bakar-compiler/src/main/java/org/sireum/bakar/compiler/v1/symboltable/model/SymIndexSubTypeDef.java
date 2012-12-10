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

public final class SymIndexSubTypeDef extends SymCompositeDef {
  protected SymArrayDef theParentTypeDef;

  protected ArrayList<SymDef> theSubTypeDefs = new ArrayList<SymDef>();

  public final static int DESCRIPTOR = 20;

  public SymIndexSubTypeDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymIndexSubTypeDef.DESCRIPTOR;
  }

  public final SymArrayDef getTheParentTypeDef() {
    return this.theParentTypeDef;
  }

  public final ArrayList<SymDef> getTheSubTypeDefs() {
    return this.theSubTypeDefs;
  }

  public final void setTheParentTypeDef(final SymArrayDef theParentTypeDef) {
    this.theParentTypeDef = theParentTypeDef;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new ArrayList&lt;SymDef&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheSubTypeDefs(final ArrayList<SymDef> theSubTypeDefs) {
    assert Util.nonNullElements(theSubTypeDefs);
    this.theSubTypeDefs = theSubTypeDefs;
  }
}
