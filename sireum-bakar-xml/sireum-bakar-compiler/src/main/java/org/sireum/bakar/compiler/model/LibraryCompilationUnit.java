/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class LibraryCompilationUnit extends CompilationUnit {
  protected LibraryItem theLibraryItem;

  public final static int DESCRIPTOR = 130;

  public LibraryCompilationUnit() {
  }

  @Override
  public final int getDescriptor() {
    return LibraryCompilationUnit.DESCRIPTOR;
  }

  public final LibraryItem getTheLibraryItem() {
    return this.theLibraryItem;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheLibraryItem(final LibraryItem theLibraryItem) {
    assert theLibraryItem != null;
    this.theLibraryItem = theLibraryItem;
  }
}
