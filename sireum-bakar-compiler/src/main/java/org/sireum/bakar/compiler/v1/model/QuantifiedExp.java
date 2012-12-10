/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class QuantifiedExp extends Exp {
  protected QuantifierKind theKind;

  protected String theIdentifier;

  protected Name theSubTypeMark;

  protected Range theOptionalRange;

  protected Predicate thePredicate;

  public final static int DESCRIPTOR = 59;

  public QuantifiedExp() {
  }

  @Override
  public final int getDescriptor() {
    return QuantifiedExp.DESCRIPTOR;
  }

  public final String getTheIdentifier() {
    return this.theIdentifier;
  }

  public final QuantifierKind getTheKind() {
    return this.theKind;
  }

  public final Range getTheOptionalRange() {
    return this.theOptionalRange;
  }

  public final Predicate getThePredicate() {
    return this.thePredicate;
  }

  public final Name getTheSubTypeMark() {
    return this.theSubTypeMark;
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
  public final void setTheKind(final QuantifierKind theKind) {
    assert theKind != null;
    this.theKind = theKind;
  }

  public final void setTheOptionalRange(final Range theOptionalRange) {
    this.theOptionalRange = theOptionalRange;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePredicate(final Predicate thePredicate) {
    assert thePredicate != null;
    this.thePredicate = thePredicate;
  }

  public final void setTheSubTypeMark(final Name theSubTypeMark) {
    this.theSubTypeMark = theSubTypeMark;
  }
}
