/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class StatementList extends Node {
  protected ArrayList<Statement> theStatements;

  public final static int DESCRIPTOR = 73;

  public StatementList() {
  }

  @Override
  public final int getDescriptor() {
    return StatementList.DESCRIPTOR;
  }

  public final ArrayList<Statement> getTheStatements() {
    return this.theStatements;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheStatements(final ArrayList<Statement> theStatements) {
    assert theStatements != null;

    assert Util.nonNullElements(theStatements);
    this.theStatements = theStatements;
    if (theStatements != null) {
      final int count = theStatements.size();
      for (int i = 0; i < count; i++) {
        theStatements.get(i).setTheStatementIndex(i);
      }
    }
  }
}
