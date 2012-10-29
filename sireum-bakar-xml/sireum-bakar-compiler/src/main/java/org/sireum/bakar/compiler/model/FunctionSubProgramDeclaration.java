/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class FunctionSubProgramDeclaration extends SubProgramDeclaration {
  protected FunctionSpecification theFunctionSpecification;

  protected FunctionAnnotation theFunctionAnnotation;

  public final static int DESCRIPTOR = 89;

  public FunctionSubProgramDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return FunctionSubProgramDeclaration.DESCRIPTOR;
  }

  public final FunctionAnnotation getTheFunctionAnnotation() {
    return this.theFunctionAnnotation;
  }

  public final FunctionSpecification getTheFunctionSpecification() {
    return this.theFunctionSpecification;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheFunctionAnnotation(
      final FunctionAnnotation theFunctionAnnotation) {
    assert theFunctionAnnotation != null;
    this.theFunctionAnnotation = theFunctionAnnotation;
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
}
