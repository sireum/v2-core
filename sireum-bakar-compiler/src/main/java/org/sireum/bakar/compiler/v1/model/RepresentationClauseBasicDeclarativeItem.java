/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

public final class RepresentationClauseBasicDeclarativeItem extends
    BasicDeclarativeItem {
  protected RepresentationClause theRepresentationClause;

  public final static int DESCRIPTOR = 28;

  public RepresentationClauseBasicDeclarativeItem() {
  }

  @Override
  public final int getDescriptor() {
    return RepresentationClauseBasicDeclarativeItem.DESCRIPTOR;
  }

  public final RepresentationClause getTheRepresentationClause() {
    return this.theRepresentationClause;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheRepresentationClause(
      final RepresentationClause theRepresentationClause) {
    assert theRepresentationClause != null;
    this.theRepresentationClause = theRepresentationClause;
  }
}
