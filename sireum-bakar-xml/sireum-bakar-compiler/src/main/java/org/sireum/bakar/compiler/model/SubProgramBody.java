/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public abstract class SubProgramBody extends ProperBody {
  protected SubProgramImplementation theSubProgramImplementation;

  public SubProgramBody() {
  }

  public final SubProgramImplementation getTheSubProgramImplementation() {
    return this.theSubProgramImplementation;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSubProgramImplementation(
      final SubProgramImplementation theSubProgramImplementation) {
    assert theSubProgramImplementation != null;
    this.theSubProgramImplementation = theSubProgramImplementation;
  }

}
