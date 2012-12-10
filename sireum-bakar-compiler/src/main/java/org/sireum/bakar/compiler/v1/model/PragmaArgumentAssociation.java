/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public abstract class PragmaArgumentAssociation extends Node {
  protected String theOptionalIDString;

  public PragmaArgumentAssociation() {
  }

  public final String getTheOptionalIDString() {
    return this.theOptionalIDString;
  }

  public final void setTheOptionalIDString(final String theOptionalIDString) {
    if (theOptionalIDString != null) {
      this.theOptionalIDString = theOptionalIDString.intern();
    } else {
      this.theOptionalIDString = null;
    }
  }

}
