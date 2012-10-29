/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class FunctionSubProgramBody extends SubProgramBody {
  protected FunctionSpecification theFunctionSpecification;

  protected FunctionAnnotation theOptionalFunctionAnnotation;

  public final static int DESCRIPTOR = 104;

  public FunctionSubProgramBody() {
  }

  @Override
  public final int getDescriptor() {
    return FunctionSubProgramBody.DESCRIPTOR;
  }

  public final FunctionSpecification getTheFunctionSpecification() {
    return this.theFunctionSpecification;
  }

  public final FunctionAnnotation getTheOptionalFunctionAnnotation() {
    return this.theOptionalFunctionAnnotation;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheFunctionSpecification(
      final FunctionSpecification theFunctionSpecification) {
    assert theFunctionSpecification != null;
    this.theFunctionSpecification = theFunctionSpecification;
  }

  public final void setTheOptionalFunctionAnnotation(
      final FunctionAnnotation theOptionalFunctionAnnotation) {
    this.theOptionalFunctionAnnotation = theOptionalFunctionAnnotation;
  }
}
