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

public final class ObjectDeclaration extends BasicDeclaration {
  protected ArrayList<IDName> theDefiningIdentifierList;

  protected boolean theConstantFlag;

  protected Name theSubtypeMark;

  protected Exp theOptionalInitializingExp;

  public final static int DESCRIPTOR = 8;

  public ObjectDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return ObjectDeclaration.DESCRIPTOR;
  }

  public final boolean getTheConstantFlag() {
    return this.theConstantFlag;
  }

  public final ArrayList<IDName> getTheDefiningIdentifierList() {
    return this.theDefiningIdentifierList;
  }

  public final Exp getTheOptionalInitializingExp() {
    return this.theOptionalInitializingExp;
  }

  public final Name getTheSubtypeMark() {
    return this.theSubtypeMark;
  }

  public final void setTheConstantFlag(final boolean theConstantFlag) {
    this.theConstantFlag = theConstantFlag;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheDefiningIdentifierList(
      final ArrayList<IDName> theDefiningIdentifierList) {
    assert theDefiningIdentifierList != null;

    assert Util.nonNullElements(theDefiningIdentifierList);
    this.theDefiningIdentifierList = theDefiningIdentifierList;
  }

  public final void setTheOptionalInitializingExp(
      final Exp theOptionalInitializingExp) {
    this.theOptionalInitializingExp = theOptionalInitializingExp;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSubtypeMark(final Name theSubtypeMark) {
    assert theSubtypeMark != null;
    this.theSubtypeMark = theSubtypeMark;
  }
}
