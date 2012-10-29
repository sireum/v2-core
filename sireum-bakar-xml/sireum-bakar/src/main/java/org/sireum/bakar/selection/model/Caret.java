/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.selection.model;

public final class Caret extends SelectionNode {
  protected int theLine = -1;

  protected int theCol = -1;

  protected int theOffset = -1;

  public final static int DESCRIPTOR = 1;

  public Caret() {
  }

  @Override
  public final int getDescriptor() {
    return Caret.DESCRIPTOR;
  }

  public final int getTheCol() {
    return this.theCol;
  }

  public final int getTheLine() {
    return this.theLine;
  }

  public final int getTheOffset() {
    return this.theOffset;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * -1
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheCol(final int theCol) {
    this.theCol = theCol;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * -1
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheLine(final int theLine) {
    this.theLine = theLine;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * -1
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheOffset(final int theOffset) {
    this.theOffset = theOffset;
  }
}
