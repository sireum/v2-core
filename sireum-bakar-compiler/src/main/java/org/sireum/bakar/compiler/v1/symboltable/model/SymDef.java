/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public abstract class SymDef extends Symbol {
  protected SymOrigin theOrigin;

  protected boolean isTagged = false;

  protected boolean isLimited = false;

  protected boolean isPrivateTypeDeclaration = false;

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalPrivateTypeDeclarationSelection;

  public SymDef() {
  }

  public final boolean getIsLimited() {
    return this.isLimited;
  }

  public final boolean getIsPrivateTypeDeclaration() {
    return this.isPrivateTypeDeclaration;
  }

  public final boolean getIsTagged() {
    return this.isTagged;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalPrivateTypeDeclarationSelection() {
    return this.theOptionalPrivateTypeDeclarationSelection;
  }

  public final SymOrigin getTheOrigin() {
    return this.theOrigin;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * false
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setIsLimited(final boolean isLimited) {
    this.isLimited = isLimited;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * false
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setIsPrivateTypeDeclaration(
      final boolean isPrivateTypeDeclaration) {
    this.isPrivateTypeDeclaration = isPrivateTypeDeclaration;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * false
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setIsTagged(final boolean isTagged) {
    this.isTagged = isTagged;
  }

  public final void setTheOptionalPrivateTypeDeclarationSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalPrivateTypeDeclarationSelection) {
    this.theOptionalPrivateTypeDeclarationSelection = theOptionalPrivateTypeDeclarationSelection;
  }

  public final void setTheOrigin(final SymOrigin theOrigin) {
    this.theOrigin = theOrigin;
  }

}
