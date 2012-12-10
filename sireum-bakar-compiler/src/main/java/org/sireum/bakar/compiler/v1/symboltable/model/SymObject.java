/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public abstract class SymObject extends SymDec {
  protected SymOrigin theOrigin;

  protected SymDef theOptionalTypeSymDef;

  protected SymObjectKind theKind;

  public SymObject() {
  }

  public final SymObjectKind getTheKind() {
    return this.theKind;
  }

  public final SymDef getTheOptionalTypeSymDef() {
    return this.theOptionalTypeSymDef;
  }

  public final SymOrigin getTheOrigin() {
    return this.theOrigin;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheKind(final SymObjectKind theKind) {
    assert theKind != null;
    this.theKind = theKind;
  }

  public final void setTheOptionalTypeSymDef(final SymDef theOptionalTypeSymDef) {
    this.theOptionalTypeSymDef = theOptionalTypeSymDef;
  }

  public final void setTheOrigin(final SymOrigin theOrigin) {
    this.theOrigin = theOrigin;
  }

}
