/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class BasicDeclarationBasicDeclarativeItem extends
    BasicDeclarativeItem {
  protected BasicDeclaration theBasicDeclaration;

  public final static int DESCRIPTOR = 27;

  public BasicDeclarationBasicDeclarativeItem() {
  }

  @Override
  public final int getDescriptor() {
    return BasicDeclarationBasicDeclarativeItem.DESCRIPTOR;
  }

  public final BasicDeclaration getTheBasicDeclaration() {
    return this.theBasicDeclaration;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheBasicDeclaration(
      final BasicDeclaration theBasicDeclaration) {
    assert theBasicDeclaration != null;
    this.theBasicDeclaration = theBasicDeclaration;
  }
}
