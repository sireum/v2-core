/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public abstract class CompilationUnit extends Node {
  protected ContextClause theContextClause;

  public CompilationUnit() {
  }

  public final ContextClause getTheContextClause() {
    return this.theContextClause;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheContextClause(final ContextClause theContextClause) {
    assert theContextClause != null;
    this.theContextClause = theContextClause;
  }

}
