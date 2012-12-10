/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public abstract class SymFunction extends SymCall {
  protected SymDef theReturnType;

  public SymFunction() {
  }

  public final SymDef getTheReturnType() {
    return this.theReturnType;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheReturnType(final SymDef theReturnType) {
    assert theReturnType != null;
    this.theReturnType = theReturnType;
  }

}
