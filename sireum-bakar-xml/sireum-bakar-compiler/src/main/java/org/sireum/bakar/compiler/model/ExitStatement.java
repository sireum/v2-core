/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class ExitStatement extends SimpleStatement {
  protected Name theOptionalName;

  protected Exp theOptionalExp;

  public final static int DESCRIPTOR = 87;

  public ExitStatement() {
  }

  @Override
  public final int getDescriptor() {
    return ExitStatement.DESCRIPTOR;
  }

  public final Exp getTheOptionalExp() {
    return this.theOptionalExp;
  }

  public final Name getTheOptionalName() {
    return this.theOptionalName;
  }

  public final void setTheOptionalExp(final Exp theOptionalExp) {
    this.theOptionalExp = theOptionalExp;
  }

  public final void setTheOptionalName(final Name theOptionalName) {
    this.theOptionalName = theOptionalName;
  }
}
