/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class SubUnitCompilationUnit extends CompilationUnit {
  protected Name theName;

  protected ProperBody theProperBody;

  public final static int DESCRIPTOR = 131;

  public SubUnitCompilationUnit() {
  }

  @Override
  public final int getDescriptor() {
    return SubUnitCompilationUnit.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final ProperBody getTheProperBody() {
    return this.theProperBody;
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
  public final void setTheProperBody(final ProperBody theProperBody) {
    assert theProperBody != null;
    this.theProperBody = theProperBody;
  }
}
