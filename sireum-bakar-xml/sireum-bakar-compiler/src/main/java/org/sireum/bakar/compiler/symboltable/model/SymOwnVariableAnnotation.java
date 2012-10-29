/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

public final class SymOwnVariableAnnotation extends SymAnnotation {
  protected org.sireum.bakar.compiler.model.Mode theMode;

  protected SymOwnCategory theOwnCategory;

  protected SymObject theFakeGlobal;

  protected SymRefinementAnnotation theOptionalRefinements;

  protected SymDef theOptionalType;

  public final static int DESCRIPTOR = 6;

  public SymOwnVariableAnnotation() {
  }

  @Override
  public final int getDescriptor() {
    return SymOwnVariableAnnotation.DESCRIPTOR;
  }

  public final SymObject getTheFakeGlobal() {
    return this.theFakeGlobal;
  }

  public final org.sireum.bakar.compiler.model.Mode getTheMode() {
    return this.theMode;
  }

  public final SymRefinementAnnotation getTheOptionalRefinements() {
    return this.theOptionalRefinements;
  }

  public final SymDef getTheOptionalType() {
    return this.theOptionalType;
  }

  public final SymOwnCategory getTheOwnCategory() {
    return this.theOwnCategory;
  }

  public final void setTheFakeGlobal(final SymObject theFakeGlobal) {
    this.theFakeGlobal = theFakeGlobal;
  }

  public final void setTheMode(
      final org.sireum.bakar.compiler.model.Mode theMode) {
    this.theMode = theMode;
  }

  public final void setTheOptionalRefinements(
      final SymRefinementAnnotation theOptionalRefinements) {
    this.theOptionalRefinements = theOptionalRefinements;
  }

  public final void setTheOptionalType(final SymDef theOptionalType) {
    this.theOptionalType = theOptionalType;
  }

  public final void setTheOwnCategory(final SymOwnCategory theOwnCategory) {
    this.theOwnCategory = theOwnCategory;
  }
}
