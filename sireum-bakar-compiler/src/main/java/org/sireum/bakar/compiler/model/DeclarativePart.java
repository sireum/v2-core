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

public final class DeclarativePart extends Node {
  protected ArrayList<RenamingDeclaration> theOptionalRenamingDeclarations;

  protected ArrayList<DeclarativePartMember> theOptionalDeclarativePartMembers;

  public final static int DESCRIPTOR = 26;

  public DeclarativePart() {
  }

  @Override
  public final int getDescriptor() {
    return DeclarativePart.DESCRIPTOR;
  }

  public final ArrayList<DeclarativePartMember> getTheOptionalDeclarativePartMembers() {
    return this.theOptionalDeclarativePartMembers;
  }

  public final ArrayList<RenamingDeclaration> getTheOptionalRenamingDeclarations() {
    return this.theOptionalRenamingDeclarations;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalDeclarativePartMembers(
      final ArrayList<DeclarativePartMember> theOptionalDeclarativePartMembers) {
    assert Util.nonNullElements(theOptionalDeclarativePartMembers);
    this.theOptionalDeclarativePartMembers = theOptionalDeclarativePartMembers;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalRenamingDeclarations(
      final ArrayList<RenamingDeclaration> theOptionalRenamingDeclarations) {
    assert Util.nonNullElements(theOptionalRenamingDeclarations);
    this.theOptionalRenamingDeclarations = theOptionalRenamingDeclarations;
  }
}
