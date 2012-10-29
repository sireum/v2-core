/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class RecordDefinition extends Node {
  protected boolean theNullRecordFlag;

  protected ArrayList<ComponentDeclaration> theOptionalComponentDeclarations;

  public final static int DESCRIPTOR = 21;

  public RecordDefinition() {
  }

  @Override
  public final int getDescriptor() {
    return RecordDefinition.DESCRIPTOR;
  }

  public final boolean getTheNullRecordFlag() {
    return this.theNullRecordFlag;
  }

  public final ArrayList<ComponentDeclaration> getTheOptionalComponentDeclarations() {
    return this.theOptionalComponentDeclarations;
  }

  public final void setTheNullRecordFlag(final boolean theNullRecordFlag) {
    this.theNullRecordFlag = theNullRecordFlag;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalComponentDeclarations(
      final ArrayList<ComponentDeclaration> theOptionalComponentDeclarations) {
    assert Util.nonNullElements(theOptionalComponentDeclarations);
    this.theOptionalComponentDeclarations = theOptionalComponentDeclarations;
  }
}
