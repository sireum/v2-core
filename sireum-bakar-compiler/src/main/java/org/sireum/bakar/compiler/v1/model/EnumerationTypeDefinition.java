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

public final class EnumerationTypeDefinition extends TypeDefinition {
  protected ArrayList<IDName> theIDNames;

  public final static int DESCRIPTOR = 12;

  public EnumerationTypeDefinition() {
  }

  @Override
  public final int getDescriptor() {
    return EnumerationTypeDefinition.DESCRIPTOR;
  }

  public final ArrayList<IDName> getTheIDNames() {
    return this.theIDNames;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheIDNames(final ArrayList<IDName> theIDNames) {
    assert theIDNames != null;

    assert Util.nonNullElements(theIDNames);
    this.theIDNames = theIDNames;
  }
}
