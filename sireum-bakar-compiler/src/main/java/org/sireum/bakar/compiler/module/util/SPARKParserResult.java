/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.module.util;

import java.util.HashMap;

import org.sireum.bakar.util.Util;

public final class SPARKParserResult extends Node {
  protected String[] theSparkSourceFilenames;

  protected HashMap<String, SPARKSpecBodyUnit> thePackageNameToSpecBodyNodes;

  protected SPARKCompilationUnit theOptionalMainCompilationUnit;

  public final static int DESCRIPTOR = 1;

  public SPARKParserResult() {
  }

  @Override
  public final int getDescriptor() {
    return SPARKParserResult.DESCRIPTOR;
  }

  public final SPARKCompilationUnit getTheOptionalMainCompilationUnit() {
    return this.theOptionalMainCompilationUnit;
  }

  public final HashMap<String, SPARKSpecBodyUnit> getThePackageNameToSpecBodyNodes() {
    return this.thePackageNameToSpecBodyNodes;
  }

  public final String[] getTheSparkSourceFilenames() {
    return this.theSparkSourceFilenames;
  }

  public final void setTheOptionalMainCompilationUnit(
      final SPARKCompilationUnit theOptionalMainCompilationUnit) {
    this.theOptionalMainCompilationUnit = theOptionalMainCompilationUnit;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setThePackageNameToSpecBodyNodes(
      final HashMap<String, SPARKSpecBodyUnit> thePackageNameToSpecBodyNodes) {
    assert thePackageNameToSpecBodyNodes != null;

    assert Util.nonNullElements(thePackageNameToSpecBodyNodes);
    this.thePackageNameToSpecBodyNodes = thePackageNameToSpecBodyNodes;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheSparkSourceFilenames(
      final String[] theSparkSourceFilenames) {
    assert theSparkSourceFilenames != null;

    assert Util.nonNullElements(theSparkSourceFilenames);
    this.theSparkSourceFilenames = theSparkSourceFilenames;
  }
}
