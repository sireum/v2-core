/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class ProcedureAnnotation extends Node {
  protected GlobalDefinition theOptionalGlobalDefinition;

  protected DependencyRelation theOptionalDependency;

  protected Precondition theOptionalPrecondition;

  protected Postcondition theOptionalPostcondition;

  public final static int DESCRIPTOR = 93;

  public ProcedureAnnotation() {
  }

  @Override
  public final int getDescriptor() {
    return ProcedureAnnotation.DESCRIPTOR;
  }

  public final DependencyRelation getTheOptionalDependency() {
    return this.theOptionalDependency;
  }

  public final GlobalDefinition getTheOptionalGlobalDefinition() {
    return this.theOptionalGlobalDefinition;
  }

  public final Postcondition getTheOptionalPostcondition() {
    return this.theOptionalPostcondition;
  }

  public final Precondition getTheOptionalPrecondition() {
    return this.theOptionalPrecondition;
  }

  public final void setTheOptionalDependency(
      final DependencyRelation theOptionalDependency) {
    this.theOptionalDependency = theOptionalDependency;
  }

  public final void setTheOptionalGlobalDefinition(
      final GlobalDefinition theOptionalGlobalDefinition) {
    this.theOptionalGlobalDefinition = theOptionalGlobalDefinition;
  }

  public final void setTheOptionalPostcondition(
      final Postcondition theOptionalPostcondition) {
    this.theOptionalPostcondition = theOptionalPostcondition;
  }

  public final void setTheOptionalPrecondition(
      final Precondition theOptionalPrecondition) {
    this.theOptionalPrecondition = theOptionalPrecondition;
  }
}
