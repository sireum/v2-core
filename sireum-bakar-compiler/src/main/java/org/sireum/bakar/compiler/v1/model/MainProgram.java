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

public final class MainProgram extends LibraryUnitDeclaration {
  protected ArrayList<Name> theOptionalInheritClauses;

  protected SubProgramBody theSubProgramBody;

  public final static int DESCRIPTOR = 133;

  public MainProgram() {
  }

  @Override
  public final int getDescriptor() {
    return MainProgram.DESCRIPTOR;
  }

  public final ArrayList<Name> getTheOptionalInheritClauses() {
    return this.theOptionalInheritClauses;
  }

  public final SubProgramBody getTheSubProgramBody() {
    return this.theSubProgramBody;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalInheritClauses(
      final ArrayList<Name> theOptionalInheritClauses) {
    assert Util.nonNullElements(theOptionalInheritClauses);
    this.theOptionalInheritClauses = theOptionalInheritClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSubProgramBody(final SubProgramBody theSubProgramBody) {
    assert theSubProgramBody != null;
    this.theSubProgramBody = theSubProgramBody;
  }
}
