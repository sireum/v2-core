/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class IDAttributeDesignator extends AttributeDesignator {
  protected String theIDString;

  protected ArrayList<Exp> theOptionalExps;

  public final static int DESCRIPTOR = 42;

  public IDAttributeDesignator() {
  }

  @Override
  public final int getDescriptor() {
    return IDAttributeDesignator.DESCRIPTOR;
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  public final ArrayList<Exp> getTheOptionalExps() {
    return this.theOptionalExps;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheIDString(final String theIDString) {
    assert theIDString != null;
    if (theIDString != null) {
      this.theIDString = theIDString.intern();
    } else {
      this.theIDString = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalExps(final ArrayList<Exp> theOptionalExps) {
    assert Util.nonNullElements(theOptionalExps);
    this.theOptionalExps = theOptionalExps;
  }
}
