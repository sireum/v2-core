/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class SubTypeChoice extends Choice {
  protected SubTypeIndication theSubTypeIndication;

  public final static int DESCRIPTOR = 24;

  public SubTypeChoice() {
  }

  @Override
  public final int getDescriptor() {
    return SubTypeChoice.DESCRIPTOR;
  }

  public final SubTypeIndication getTheSubTypeIndication() {
    return this.theSubTypeIndication;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheSubTypeIndication(
      final SubTypeIndication theSubTypeIndication) {
    assert theSubTypeIndication != null;
    this.theSubTypeIndication = theSubTypeIndication;
  }
}
