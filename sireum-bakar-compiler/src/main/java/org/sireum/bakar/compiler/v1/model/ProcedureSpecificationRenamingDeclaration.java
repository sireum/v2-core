/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class ProcedureSpecificationRenamingDeclaration extends
    SubProgramRenamingDeclaration {
  protected ProcedureSpecification theProcedureSpecification;

  protected Name theName;

  public final static int DESCRIPTOR = 128;

  public ProcedureSpecificationRenamingDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return ProcedureSpecificationRenamingDeclaration.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final ProcedureSpecification getTheProcedureSpecification() {
    return this.theProcedureSpecification;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheName(final Name theName) {
    assert theName != null;
    this.theName = theName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheProcedureSpecification(
      final ProcedureSpecification theProcedureSpecification) {
    assert theProcedureSpecification != null;
    this.theProcedureSpecification = theProcedureSpecification;
  }
}
