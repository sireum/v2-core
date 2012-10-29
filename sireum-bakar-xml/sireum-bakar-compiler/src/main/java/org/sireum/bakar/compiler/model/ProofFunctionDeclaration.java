/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class ProofFunctionDeclaration extends BasicDeclarativeItem {
  protected FunctionSpecification theFunctionSpecification;

  public final static int DESCRIPTOR = 29;

  public ProofFunctionDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return ProofFunctionDeclaration.DESCRIPTOR;
  }

  public final FunctionSpecification getTheFunctionSpecification() {
    return this.theFunctionSpecification;
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
