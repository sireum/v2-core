/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

public final class SymRecordComponentDef extends SymDef {
  protected SymRecordDef theParentRecordDef;

  protected SymDef theTypeSymDef;

  public final static int DESCRIPTOR = 18;

  public SymRecordComponentDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymRecordComponentDef.DESCRIPTOR;
  }

  public final SymRecordDef getTheParentRecordDef() {
    return this.theParentRecordDef;
  }

  public final SymDef getTheTypeSymDef() {
    return this.theTypeSymDef;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheParentRecordDef(final SymRecordDef theParentRecordDef) {
    assert theParentRecordDef != null;
    this.theParentRecordDef = theParentRecordDef;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheTypeSymDef(final SymDef theTypeSymDef) {
    assert theTypeSymDef != null;
    this.theTypeSymDef = theTypeSymDef;
  }
}
