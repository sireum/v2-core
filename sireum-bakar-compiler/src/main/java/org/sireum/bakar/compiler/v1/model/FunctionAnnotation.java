/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class FunctionAnnotation extends Node {
  protected GlobalDefinition theOptionalGlobalDefinition;

  protected Precondition theOptionalPrecondition;

  protected ReturnAnnotation theOptionalReturnAnnotation;

  public final static int DESCRIPTOR = 100;

  public FunctionAnnotation() {
  }

  @Override
  public final int getDescriptor() {
    return FunctionAnnotation.DESCRIPTOR;
  }

  public final GlobalDefinition getTheOptionalGlobalDefinition() {
    return this.theOptionalGlobalDefinition;
  }

  public final Precondition getTheOptionalPrecondition() {
    return this.theOptionalPrecondition;
  }

  public final ReturnAnnotation getTheOptionalReturnAnnotation() {
    return this.theOptionalReturnAnnotation;
  }

  public final void setTheOptionalGlobalDefinition(
      final GlobalDefinition theOptionalGlobalDefinition) {
    this.theOptionalGlobalDefinition = theOptionalGlobalDefinition;
  }

  public final void setTheOptionalPrecondition(
      final Precondition theOptionalPrecondition) {
    this.theOptionalPrecondition = theOptionalPrecondition;
  }

  public final void setTheOptionalReturnAnnotation(
      final ReturnAnnotation theOptionalReturnAnnotation) {
    this.theOptionalReturnAnnotation = theOptionalReturnAnnotation;
  }
}
