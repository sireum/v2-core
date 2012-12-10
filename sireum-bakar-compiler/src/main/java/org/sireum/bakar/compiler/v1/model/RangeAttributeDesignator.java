/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class RangeAttributeDesignator extends Node {
  protected Exp theOptionalExp;

  public final static int DESCRIPTOR = 46;

  public RangeAttributeDesignator() {
  }

  @Override
  public final int getDescriptor() {
    return RangeAttributeDesignator.DESCRIPTOR;
  }

  public final Exp getTheOptionalExp() {
    return this.theOptionalExp;
  }

  public final void setTheOptionalExp(final Exp theOptionalExp) {
    this.theOptionalExp = theOptionalExp;
  }
}
