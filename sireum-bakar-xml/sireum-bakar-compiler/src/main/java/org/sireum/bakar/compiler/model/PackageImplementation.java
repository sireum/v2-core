/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class PackageImplementation extends Node {
  protected DeclarativePart theDeclarativePart;

  protected StatementList theOptionalStatementList;

  public final static int DESCRIPTOR = 121;

  public PackageImplementation() {
  }

  @Override
  public final int getDescriptor() {
    return PackageImplementation.DESCRIPTOR;
  }

  public final DeclarativePart getTheDeclarativePart() {
    return this.theDeclarativePart;
  }

  public final StatementList getTheOptionalStatementList() {
    return this.theOptionalStatementList;
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

  public final void setTheOptionalStatementList(
      final StatementList theOptionalStatementList) {
    this.theOptionalStatementList = theOptionalStatementList;
  }
}
