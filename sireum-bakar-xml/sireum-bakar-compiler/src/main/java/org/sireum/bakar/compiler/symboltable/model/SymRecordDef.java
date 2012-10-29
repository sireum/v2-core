/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

import org.sireum.bakar.util.Util;

public final class SymRecordDef extends SymCompositeDef {
  protected java.util.LinkedHashMap<String, SymRecordComponentDef> theElements = new java.util.LinkedHashMap<String, SymRecordComponentDef>();

  protected boolean isRecordTagged = false;

  protected SymRecordDef theOptionalParentDef;

  public final static int DESCRIPTOR = 17;

  public SymRecordDef() {
  }

  @Override
  public final int getDescriptor() {
    return SymRecordDef.DESCRIPTOR;
  }

  public final boolean getIsRecordTagged() {
    return this.isRecordTagged;
  }

  public final java.util.LinkedHashMap<String, SymRecordComponentDef> getTheElements() {
    return this.theElements;
  }

  public final SymRecordDef getTheOptionalParentDef() {
    return this.theOptionalParentDef;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * false
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setIsRecordTagged(final boolean isRecordTagged) {
    this.isRecordTagged = isRecordTagged;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashMap&lt;String, SymRecordComponentDef&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheElements(
      final java.util.LinkedHashMap<String, SymRecordComponentDef> theElements) {
    assert Util.nonNullElements(theElements);
    this.theElements = theElements;
  }

  public final void setTheOptionalParentDef(
      final SymRecordDef theOptionalParentDef) {
    this.theOptionalParentDef = theOptionalParentDef;
  }
}
