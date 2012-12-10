/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class NamedRecordAggregate extends RecordAggregate {
  protected ArrayList<RecordComponentAssociation> theRecordComponentAssociations;

  public final static int DESCRIPTOR = 48;

  public NamedRecordAggregate() {
  }

  @Override
  public final int getDescriptor() {
    return NamedRecordAggregate.DESCRIPTOR;
  }

  public final ArrayList<RecordComponentAssociation> getTheRecordComponentAssociations() {
    return this.theRecordComponentAssociations;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheRecordComponentAssociations(
      final ArrayList<RecordComponentAssociation> theRecordComponentAssociations) {
    assert theRecordComponentAssociations != null;

    assert Util.nonNullElements(theRecordComponentAssociations);
    this.theRecordComponentAssociations = theRecordComponentAssociations;
  }
}
