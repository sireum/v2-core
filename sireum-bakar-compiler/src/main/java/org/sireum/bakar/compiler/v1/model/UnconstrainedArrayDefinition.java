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

public final class UnconstrainedArrayDefinition extends ArrayTypeDefinition {
  protected ArrayList<Name> theIndexSubTypeNames;

  protected Name theComponentName;

  public final static int DESCRIPTOR = 17;

  public UnconstrainedArrayDefinition() {
  }

  @Override
  public final int getDescriptor() {
    return UnconstrainedArrayDefinition.DESCRIPTOR;
  }

  public final Name getTheComponentName() {
    return this.theComponentName;
  }

  public final ArrayList<Name> getTheIndexSubTypeNames() {
    return this.theIndexSubTypeNames;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheComponentName(final Name theComponentName) {
    assert theComponentName != null;
    this.theComponentName = theComponentName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheIndexSubTypeNames(
      final ArrayList<Name> theIndexSubTypeNames) {
    assert theIndexSubTypeNames != null;

    assert Util.nonNullElements(theIndexSubTypeNames);
    this.theIndexSubTypeNames = theIndexSubTypeNames;
  }
}
