/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class TypeAssertion extends BasicProofDeclaration {
  protected String theIdentifier;

  protected Name theBase;

  protected Name theSubtypeMark;

  public final static int DESCRIPTOR = 31;

  public TypeAssertion() {
  }

  @Override
  public final int getDescriptor() {
    return TypeAssertion.DESCRIPTOR;
  }

  public final Name getTheBase() {
    return this.theBase;
  }

  public final String getTheIdentifier() {
    return this.theIdentifier;
  }

  public final Name getTheSubtypeMark() {
    return this.theSubtypeMark;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheBase(final Name theBase) {
    assert theBase != null;
    this.theBase = theBase;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheIdentifier(final String theIdentifier) {
    assert theIdentifier != null;
    if (theIdentifier != null) {
      this.theIdentifier = theIdentifier.intern();
    } else {
      this.theIdentifier = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSubtypeMark(final Name theSubtypeMark) {
    assert theSubtypeMark != null;
    this.theSubtypeMark = theSubtypeMark;
  }
}
