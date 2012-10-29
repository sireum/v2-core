/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

public final class SymPartialDef extends SymDef {
  protected SymDef theElaborationSymDef;

  public final static int DESCRIPTOR = 16;

  public SymPartialDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymPartialDef.DESCRIPTOR;
  }

  public final SymDef getTheElaborationSymDef() {
    return this.theElaborationSymDef;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheElaborationSymDef(final SymDef theElaborationSymDef) {
    assert theElaborationSymDef != null;
    this.theElaborationSymDef = theElaborationSymDef;
  }
}
