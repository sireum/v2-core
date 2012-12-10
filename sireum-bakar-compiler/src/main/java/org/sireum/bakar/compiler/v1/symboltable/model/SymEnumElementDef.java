/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public final class SymEnumElementDef extends SymDef {
  protected SymEnumerationDef theParentEnumDef;

  public final static int DESCRIPTOR = 22;

  public SymEnumElementDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymEnumElementDef.DESCRIPTOR;
  }

  public final SymEnumerationDef getTheParentEnumDef() {
    return this.theParentEnumDef;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheParentEnumDef(final SymEnumerationDef theParentEnumDef) {
    assert theParentEnumDef != null;
    this.theParentEnumDef = theParentEnumDef;
  }
}
