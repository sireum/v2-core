/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class AttributeReference extends Name {
  protected Name theName;

  protected AttributeDesignator theAttributeDesignator;

  public final static int DESCRIPTOR = 39;

  public AttributeReference() {
  }

  @Override
  public final int getDescriptor() {
    return AttributeReference.DESCRIPTOR;
  }

  public final AttributeDesignator getTheAttributeDesignator() {
    return this.theAttributeDesignator;
  }

  public final Name getTheName() {
    return this.theName;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheAttributeDesignator(
      final AttributeDesignator theAttributeDesignator) {
    assert theAttributeDesignator != null;
    this.theAttributeDesignator = theAttributeDesignator;
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
