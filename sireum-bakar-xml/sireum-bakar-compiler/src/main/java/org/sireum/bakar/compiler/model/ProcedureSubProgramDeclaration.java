/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class ProcedureSubProgramDeclaration extends SubProgramDeclaration {
  protected ProcedureSpecification theProcedureSpecification;

  protected ProcedureAnnotation theProcedureAnnotation;

  public final static int DESCRIPTOR = 88;

  public ProcedureSubProgramDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return ProcedureSubProgramDeclaration.DESCRIPTOR;
  }

  public final ProcedureAnnotation getTheProcedureAnnotation() {
    return this.theProcedureAnnotation;
  }

  public final ProcedureSpecification getTheProcedureSpecification() {
    return this.theProcedureSpecification;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheProcedureAnnotation(
      final ProcedureAnnotation theProcedureAnnotation) {
    assert theProcedureAnnotation != null;
    this.theProcedureAnnotation = theProcedureAnnotation;
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
