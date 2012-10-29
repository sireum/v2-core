/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

public abstract class Symbol {
  protected org.sireum.bakar.selection.model.RegionSelection theSelection;

  protected String theName;

  protected String theTranslation;

  protected SymPackageElement theOptionalParent;

  public Symbol() {
  }

  public int getDescriptor() {
    return 0;
  }

  public final String getTheName() {
    return this.theName;
  }

  public final SymPackageElement getTheOptionalParent() {
    return this.theOptionalParent;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheSelection() {
    return this.theSelection;
  }

  public final String getTheTranslation() {
    return this.theTranslation;
  }

  public final void setTheName(final String theName) {
    if (theName != null) {
      this.theName = theName.intern();
    } else {
      this.theName = null;
    }
  }

  public final void setTheOptionalParent(
      final SymPackageElement theOptionalParent) {
    this.theOptionalParent = theOptionalParent;
  }

  public final void setTheSelection(
      final org.sireum.bakar.selection.model.RegionSelection theSelection) {
    this.theSelection = theSelection;
  }

  public final void setTheTranslation(final String theTranslation) {
    if (theTranslation != null) {
      this.theTranslation = theTranslation.intern();
    } else {
      this.theTranslation = null;
    }
  }

}
