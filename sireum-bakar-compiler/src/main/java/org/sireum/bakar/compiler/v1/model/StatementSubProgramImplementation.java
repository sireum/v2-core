/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class StatementSubProgramImplementation extends
    SubProgramImplementation {
  protected DeclarativePart theDeclarativePart;

  protected StatementList theStatementList;

  public final static int DESCRIPTOR = 105;

  public StatementSubProgramImplementation() {
  }

  @Override
  public final int getDescriptor() {
    return StatementSubProgramImplementation.DESCRIPTOR;
  }

  public final DeclarativePart getTheDeclarativePart() {
    return this.theDeclarativePart;
  }

  public final StatementList getTheStatementList() {
    return this.theStatementList;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheDeclarativePart(
      final DeclarativePart theDeclarativePart) {
    assert theDeclarativePart != null;
    this.theDeclarativePart = theDeclarativePart;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheStatementList(final StatementList theStatementList) {
    assert theStatementList != null;
    this.theStatementList = theStatementList;
  }
}
