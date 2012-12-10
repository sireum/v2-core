/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public final class SymGlobalAnnotation extends SymAnnotation {
  protected org.sireum.bakar.compiler.v1.model.GlobalMode theMode;

  public final static int DESCRIPTOR = 9;

  public SymGlobalAnnotation() {
  }

  @Override
  public final int getDescriptor() {
    return SymGlobalAnnotation.DESCRIPTOR;
  }

  public final org.sireum.bakar.compiler.v1.model.GlobalMode getTheMode() {
    return this.theMode;
  }

  public final void setTheMode(
      final org.sireum.bakar.compiler.v1.model.GlobalMode theMode) {
    this.theMode = theMode;
  }
}
