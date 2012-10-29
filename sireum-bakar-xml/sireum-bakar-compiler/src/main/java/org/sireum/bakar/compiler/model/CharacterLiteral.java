/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class CharacterLiteral extends Literal {
  protected char theCharacter;

  public final static int DESCRIPTOR = 66;

  public CharacterLiteral() {
  }

  @Override
  public final int getDescriptor() {
    return CharacterLiteral.DESCRIPTOR;
  }

  public final char getTheCharacter() {
    return this.theCharacter;
  }

  public final void setTheCharacter(final char theCharacter) {
    this.theCharacter = theCharacter;
  }
}
