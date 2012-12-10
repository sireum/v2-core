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

public final class FunctionRenamingDeclaration extends
    SubProgramRenamingDeclaration {
  protected StringLiteral theDefiningStringLiteral;

  protected ArrayList<ParameterSpecification> theParameterSpecifications;

  protected Name theReturnName;

  protected Name thePackageName;

  protected StringLiteral theStringLiteral;

  public final static int DESCRIPTOR = 126;

  public FunctionRenamingDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return FunctionRenamingDeclaration.DESCRIPTOR;
  }

  public final StringLiteral getTheDefiningStringLiteral() {
    return this.theDefiningStringLiteral;
  }

  public final Name getThePackageName() {
    return this.thePackageName;
  }

  public final ArrayList<ParameterSpecification> getTheParameterSpecifications() {
    return this.theParameterSpecifications;
  }

  public final Name getTheReturnName() {
    return this.theReturnName;
  }

  public final StringLiteral getTheStringLiteral() {
    return this.theStringLiteral;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheDefiningStringLiteral(
      final StringLiteral theDefiningStringLiteral) {
    assert theDefiningStringLiteral != null;
    this.theDefiningStringLiteral = theDefiningStringLiteral;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePackageName(final Name thePackageName) {
    assert thePackageName != null;
    this.thePackageName = thePackageName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheParameterSpecifications(
      final ArrayList<ParameterSpecification> theParameterSpecifications) {
    assert theParameterSpecifications != null;

    assert Util.nonNullElements(theParameterSpecifications);
    this.theParameterSpecifications = theParameterSpecifications;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheReturnName(final Name theReturnName) {
    assert theReturnName != null;
    this.theReturnName = theReturnName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheStringLiteral(final StringLiteral theStringLiteral) {
    assert theStringLiteral != null;
    this.theStringLiteral = theStringLiteral;
  }
}
