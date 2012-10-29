/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class NumericLiteral extends Literal {
  protected String theNumberString;

  public final static int DESCRIPTOR = 65;

  public NumericLiteral() {
  }

  @Override
  public final int getDescriptor() {
    return NumericLiteral.DESCRIPTOR;
  }

  public final String getTheNumberString() {
    return this.theNumberString;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheNumberString(final String theNumberString) {
    assert theNumberString != null;
    if (theNumberString != null) {
      this.theNumberString = theNumberString.intern();
    } else {
      this.theNumberString = null;
    }
  }
}
