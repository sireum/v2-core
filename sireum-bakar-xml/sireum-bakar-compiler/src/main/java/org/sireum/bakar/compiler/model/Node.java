/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

public abstract class Node {
  protected org.sireum.bakar.selection.model.RegionSelection theOptionalRegionSelection;

  public Node() {
  }

  public int getDescriptor() {
    return 0;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalRegionSelection() {
    return this.theOptionalRegionSelection;
  }

  public final void setTheOptionalRegionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalRegionSelection) {
    this.theOptionalRegionSelection = theOptionalRegionSelection;
  }

}
