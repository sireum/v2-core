/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class RecordTypeDefinition extends TypeDefinition {
  protected boolean theTaggedFlag;

  protected RecordDefinition theRecordDefinition;

  public final static int DESCRIPTOR = 20;

  public RecordTypeDefinition() {
  }

  @Override
  public final int getDescriptor() {
    return RecordTypeDefinition.DESCRIPTOR;
  }

  public final RecordDefinition getTheRecordDefinition() {
    return this.theRecordDefinition;
  }

  public final boolean getTheTaggedFlag() {
    return this.theTaggedFlag;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheRecordDefinition(
      final RecordDefinition theRecordDefinition) {
    assert theRecordDefinition != null;
    this.theRecordDefinition = theRecordDefinition;
  }

  public final void setTheTaggedFlag(final boolean theTaggedFlag) {
    this.theTaggedFlag = theTaggedFlag;
  }
}
