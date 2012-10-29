/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public abstract class IterationSchemeLoopStatement extends LoopStatement {
  protected org.sireum.bakar.selection.model.RegionSelection theIterationSchemeRegionSelection;

  public IterationSchemeLoopStatement() {
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheIterationSchemeRegionSelection() {
    return this.theIterationSchemeRegionSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheIterationSchemeRegionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theIterationSchemeRegionSelection) {
    assert theIterationSchemeRegionSelection != null;
    this.theIterationSchemeRegionSelection = theIterationSchemeRegionSelection;
  }

}
