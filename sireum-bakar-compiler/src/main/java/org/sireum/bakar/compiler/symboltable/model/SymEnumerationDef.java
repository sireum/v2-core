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

public final class SymEnumerationDef extends SymDiscreteDef {
  protected ArrayList<SymEnumElementDef> theElements = new ArrayList<SymEnumElementDef>();

  public final static int DESCRIPTOR = 21;

  public SymEnumerationDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymEnumerationDef.DESCRIPTOR;
  }

  public final ArrayList<SymEnumElementDef> getTheElements() {
    return this.theElements;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new ArrayList&lt;SymEnumElementDef&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheElements(
      final ArrayList<SymEnumElementDef> theElements) {
    assert Util.nonNullElements(theElements);
    this.theElements = theElements;
  }
}
