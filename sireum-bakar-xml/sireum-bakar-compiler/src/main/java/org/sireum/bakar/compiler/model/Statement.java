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

public abstract class Statement extends Node {
  protected int theStatementIndex;

  protected ArrayList<String> theOptionalLabelList;

  public Statement() {
  }

  public final ArrayList<String> getTheOptionalLabelList() {
    return this.theOptionalLabelList;
  }

  public final int getTheStatementIndex() {
    return this.theStatementIndex;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalLabelList(
      final ArrayList<String> theOptionalLabelList) {
    assert Util.nonNullElements(theOptionalLabelList);
    this.theOptionalLabelList = theOptionalLabelList;
  }

  public final void setTheStatementIndex(final int theStatementIndex) {
    this.theStatementIndex = theStatementIndex;
  }

}
