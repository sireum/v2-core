/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class PackageBody extends ProperBody {
  protected Name theName;

  protected ArrayList<RefinementClause> theRefinementClauses;

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalRefinementClausesRegionSelection;

  protected PackageImplementation thePackageImplementation;

  public final static int DESCRIPTOR = 118;

  public PackageBody() {
  }

  @Override
  public final int getDescriptor() {
    return PackageBody.DESCRIPTOR;
  }

  public final Name getTheName() {
    return this.theName;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalRefinementClausesRegionSelection() {
    return this.theOptionalRefinementClausesRegionSelection;
  }

  public final PackageImplementation getThePackageImplementation() {
    return this.thePackageImplementation;
  }

  public final ArrayList<RefinementClause> getTheRefinementClauses() {
    return this.theRefinementClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheName(final Name theName) {
    assert theName != null;
    this.theName = theName;
  }

  public final void setTheOptionalRefinementClausesRegionSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalRefinementClausesRegionSelection) {
    this.theOptionalRefinementClausesRegionSelection = theOptionalRefinementClausesRegionSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setThePackageImplementation(
      final PackageImplementation thePackageImplementation) {
    assert thePackageImplementation != null;
    this.thePackageImplementation = thePackageImplementation;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheRefinementClauses(
      final ArrayList<RefinementClause> theRefinementClauses) {
    assert theRefinementClauses != null;

    assert Util.nonNullElements(theRefinementClauses);
    this.theRefinementClauses = theRefinementClauses;
  }
}
