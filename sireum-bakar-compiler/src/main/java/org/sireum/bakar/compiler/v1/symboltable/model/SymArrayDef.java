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

public final class SymArrayDef extends SymCompositeDef {
  protected int theDimensions = 0;

  protected boolean isConstrained = false;

  protected ArrayList<SymDef> theIndexSubtypeDefs = new ArrayList<SymDef>();

  protected SymDef theComponentSubtypeDef;

  public final static int DESCRIPTOR = 19;

  public SymArrayDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymArrayDef.DESCRIPTOR;
  }

  public final boolean getIsConstrained() {
    return this.isConstrained;
  }

  public final SymDef getTheComponentSubtypeDef() {
    return this.theComponentSubtypeDef;
  }

  public final int getTheDimensions() {
    return this.theDimensions;
  }

  public final ArrayList<SymDef> getTheIndexSubtypeDefs() {
    return this.theIndexSubtypeDefs;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * false
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setIsConstrained(final boolean isConstrained) {
    this.isConstrained = isConstrained;
  }

  public final void setTheComponentSubtypeDef(
      final SymDef theComponentSubtypeDef) {
    this.theComponentSubtypeDef = theComponentSubtypeDef;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * 0
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheDimensions(final int theDimensions) {
    this.theDimensions = theDimensions;
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
  public final void setTheIndexSubtypeDefs(
      final ArrayList<SymDef> theIndexSubtypeDefs) {
    assert Util.nonNullElements(theIndexSubtypeDefs);
    this.theIndexSubtypeDefs = theIndexSubtypeDefs;
  }
}
