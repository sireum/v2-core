/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class RangeAttributeReference extends Range {
  protected Name theName;

  protected RangeAttributeDesignator theDesignator;

  public final static int DESCRIPTOR = 45;

  public RangeAttributeReference() {
  }

  @Override
  public final int getDescriptor() {
    return RangeAttributeReference.DESCRIPTOR;
  }

  public final RangeAttributeDesignator getTheDesignator() {
    return this.theDesignator;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final void setTheDesignator(
      final RangeAttributeDesignator theDesignator) {
    this.theDesignator = theDesignator;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheName(final Name theName) {
    assert theName != null;
    this.theName = theName;
  }
}
