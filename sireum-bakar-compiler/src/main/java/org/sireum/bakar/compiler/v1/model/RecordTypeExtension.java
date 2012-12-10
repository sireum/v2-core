/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class RecordTypeExtension extends TypeDefinition {
  protected Name theSubtypeMark;

  protected RecordDefinition theRecordDefinition;

  public final static int DESCRIPTOR = 5;

  public RecordTypeExtension() {
  }

  @Override
  public final int getDescriptor() {
    return RecordTypeExtension.DESCRIPTOR;
  }

  public final RecordDefinition getTheRecordDefinition() {
    return this.theRecordDefinition;
  }

  public final Name getTheSubtypeMark() {
    return this.theSubtypeMark;
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

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSubtypeMark(final Name theSubtypeMark) {
    assert theSubtypeMark != null;
    this.theSubtypeMark = theSubtypeMark;
  }
}
