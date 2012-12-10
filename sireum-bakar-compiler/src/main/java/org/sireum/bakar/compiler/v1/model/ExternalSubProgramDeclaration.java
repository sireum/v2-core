/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class ExternalSubProgramDeclaration extends DeclarativePartMember {
  protected SubProgramDeclaration theSubProgramDeclaration;

  protected Pragma thePragma;

  public final static int DESCRIPTOR = 35;

  public ExternalSubProgramDeclaration() {
  }

  @Override
  public final int getDescriptor() {
    return ExternalSubProgramDeclaration.DESCRIPTOR;
  }

  public final Pragma getThePragma() {
    return this.thePragma;
  }

  public final SubProgramDeclaration getTheSubProgramDeclaration() {
    return this.theSubProgramDeclaration;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePragma(final Pragma thePragma) {
    assert thePragma != null;
    this.thePragma = thePragma;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSubProgramDeclaration(
      final SubProgramDeclaration theSubProgramDeclaration) {
    assert theSubProgramDeclaration != null;
    this.theSubProgramDeclaration = theSubProgramDeclaration;
  }
}
