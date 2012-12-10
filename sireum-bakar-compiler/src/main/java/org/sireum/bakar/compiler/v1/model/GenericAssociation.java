/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class GenericAssociation extends Node {
  protected String theOptionalIDString;

  protected Name theName;

  public final static int DESCRIPTOR = 140;

  public GenericAssociation() {
  }

  @Override
  public final int getDescriptor() {
    return GenericAssociation.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final String getTheOptionalIDString() {
    return this.theOptionalIDString;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheName(final Name theName) {
    assert theName != null;
    this.theName = theName;
  }

  public final void setTheOptionalIDString(final String theOptionalIDString) {
    if (theOptionalIDString != null) {
      this.theOptionalIDString = theOptionalIDString.intern();
    } else {
      this.theOptionalIDString = null;
    }
  }
}
