/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class ComponentClause extends Node {
  protected Name theName;

  protected Exp thePositionExp;

  protected Exp theFirstBitExp;

  protected Exp theLastBitExp;

  public final static int DESCRIPTOR = 145;

  public ComponentClause() {
  }

  @Override
  public final int getDescriptor() {
    return ComponentClause.DESCRIPTOR;
  }

  public final Exp getTheFirstBitExp() {
    return this.theFirstBitExp;
  }

  public final Exp getTheLastBitExp() {
    return this.theLastBitExp;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final Exp getThePositionExp() {
    return this.thePositionExp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheFirstBitExp(final Exp theFirstBitExp) {
    assert theFirstBitExp != null;
    this.theFirstBitExp = theFirstBitExp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheLastBitExp(final Exp theLastBitExp) {
    assert theLastBitExp != null;
    this.theLastBitExp = theLastBitExp;
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

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePositionExp(final Exp thePositionExp) {
    assert thePositionExp != null;
    this.thePositionExp = thePositionExp;
  }
}
