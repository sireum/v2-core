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

public final class JustificationStatement extends ProofStatement {
  protected ArrayList<JustificationClause> theClauses;

  public final static int DESCRIPTOR = 76;

  public JustificationStatement() {
  }

  @Override
  public final int getDescriptor() {
    return JustificationStatement.DESCRIPTOR;
  }

  public final ArrayList<JustificationClause> getTheClauses() {
    return this.theClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheClauses(
      final ArrayList<JustificationClause> theClauses) {
    assert theClauses != null;

    assert Util.nonNullElements(theClauses);
    this.theClauses = theClauses;
  }
}
