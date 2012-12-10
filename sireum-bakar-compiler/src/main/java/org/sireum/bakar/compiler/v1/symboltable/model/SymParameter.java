/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public final class SymParameter extends SymObject {
  protected org.sireum.bakar.compiler.v1.model.Mode theMode;

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalSpecSelection;

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalBodySelection;

  public final static int DESCRIPTOR = 14;

  public SymParameter() {
  }

  @Override
  public final int getDescriptor() {
    return SymParameter.DESCRIPTOR;
  }

  public final org.sireum.bakar.compiler.v1.model.Mode getTheMode() {
    return this.theMode;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalBodySelection() {
    return this.theOptionalBodySelection;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalSpecSelection() {
    return this.theOptionalSpecSelection;
  }

  public final void setTheMode(
      final org.sireum.bakar.compiler.v1.model.Mode theMode) {
    this.theMode = theMode;
  }

  public final void setTheOptionalBodySelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalBodySelection) {
    this.theOptionalBodySelection = theOptionalBodySelection;
  }

  public final void setTheOptionalSpecSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalSpecSelection) {
    this.theOptionalSpecSelection = theOptionalSpecSelection;
  }
}
