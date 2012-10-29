/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public final class UseTypeClauseEmbeddedPackageDeclarationMember extends
    EmbeddedPackageDeclarationMember {
  protected UseTypeClause theUseTypeClause;

  public final static int DESCRIPTOR = 34;

  public UseTypeClauseEmbeddedPackageDeclarationMember() {
  }

  @Override
  public final int getDescriptor() {
    return UseTypeClauseEmbeddedPackageDeclarationMember.DESCRIPTOR;
  }

  public final UseTypeClause getTheUseTypeClause() {
    return this.theUseTypeClause;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheUseTypeClause(final UseTypeClause theUseTypeClause) {
    assert theUseTypeClause != null;
    this.theUseTypeClause = theUseTypeClause;
  }
}
