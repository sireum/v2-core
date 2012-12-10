/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class ModularTypeDefinition extends TypeDefinition {
  protected Exp theExp;

  public final static int DESCRIPTOR = 14;

  public ModularTypeDefinition() {
  }

  @Override
  public final int getDescriptor() {
    return ModularTypeDefinition.DESCRIPTOR;
  }

  public final Exp getTheExp() {
    return this.theExp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheExp(final Exp theExp) {
    assert theExp != null;
    this.theExp = theExp;
  }
}
