/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class StringLiteral extends Literal {
  protected String theString;

  public final static int DESCRIPTOR = 67;

  public StringLiteral() {
  }

  @Override
  public final int getDescriptor() {
    return StringLiteral.DESCRIPTOR;
  }

  public final String getTheString() {
    return this.theString;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheString(final String theString) {
    assert theString != null;
    if (theString != null) {
      this.theString = theString.intern();
    } else {
      this.theString = null;
    }
  }
}
