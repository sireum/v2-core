/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class ProcedureSubProgramBody extends SubProgramBody {
  protected ProcedureSpecification theProcedureSpecification;

  protected ProcedureAnnotation theOptionalProcedureAnnotation;

  public final static int DESCRIPTOR = 103;

  public ProcedureSubProgramBody() {
  }

  @Override
  public final int getDescriptor() {
    return ProcedureSubProgramBody.DESCRIPTOR;
  }

  public final ProcedureAnnotation getTheOptionalProcedureAnnotation() {
    return this.theOptionalProcedureAnnotation;
  }

  public final ProcedureSpecification getTheProcedureSpecification() {
    return this.theProcedureSpecification;
  }

  public final void setTheOptionalProcedureAnnotation(
      final ProcedureAnnotation theOptionalProcedureAnnotation) {
    this.theOptionalProcedureAnnotation = theOptionalProcedureAnnotation;
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
