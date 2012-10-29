/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class ReturnAnnotationPred extends ReturnAnnotation {
  protected String theID;

  protected Predicate thePredicate;

  public final static int DESCRIPTOR = 96;

  public ReturnAnnotationPred() {
  }

  @Override
  public final int getDescriptor() {
    return ReturnAnnotationPred.DESCRIPTOR;
  }

  public final String getTheID() {
    return this.theID;
  }

  public final Predicate getThePredicate() {
    return this.thePredicate;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheID(final String theID) {
    assert theID != null;
    if (theID != null) {
      this.theID = theID.intern();
    } else {
      this.theID = null;
    }
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
}
