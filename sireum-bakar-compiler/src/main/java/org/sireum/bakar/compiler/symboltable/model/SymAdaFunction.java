/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

import org.sireum.bakar.util.Util;

public final class SymAdaFunction extends SymFunction {
  protected String theOptionalImplicitSpecProofTranslation;

  protected java.util.LinkedHashMap<String, SymParameter> theOptionalImplicitSpecProofParameters = new java.util.LinkedHashMap<String, SymParameter>();

  protected String theOptionalImplicitBodyProofTranslation;

  protected java.util.LinkedHashMap<String, SymParameter> theOptionalImplicitBodyProofParameters = new java.util.LinkedHashMap<String, SymParameter>();

  public final static int DESCRIPTOR = 12;

  public SymAdaFunction() {
  }

  @Override
  public final int getDescriptor() {
    return SymAdaFunction.DESCRIPTOR;
  }

  public final java.util.LinkedHashMap<String, SymParameter> getTheOptionalImplicitBodyProofParameters() {
    return this.theOptionalImplicitBodyProofParameters;
  }

  public final String getTheOptionalImplicitBodyProofTranslation() {
    return this.theOptionalImplicitBodyProofTranslation;
  }

  public final java.util.LinkedHashMap<String, SymParameter> getTheOptionalImplicitSpecProofParameters() {
    return this.theOptionalImplicitSpecProofParameters;
  }

  public final String getTheOptionalImplicitSpecProofTranslation() {
    return this.theOptionalImplicitSpecProofTranslation;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashMap&lt;String, SymParameter&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheOptionalImplicitBodyProofParameters(
      final java.util.LinkedHashMap<String, SymParameter> theOptionalImplicitBodyProofParameters) {
    assert Util.nonNullElements(theOptionalImplicitBodyProofParameters);
    this.theOptionalImplicitBodyProofParameters = theOptionalImplicitBodyProofParameters;
  }

  public final void setTheOptionalImplicitBodyProofTranslation(
      final String theOptionalImplicitBodyProofTranslation) {
    if (theOptionalImplicitBodyProofTranslation != null) {
      this.theOptionalImplicitBodyProofTranslation = theOptionalImplicitBodyProofTranslation
          .intern();
    } else {
      this.theOptionalImplicitBodyProofTranslation = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashMap&lt;String, SymParameter&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheOptionalImplicitSpecProofParameters(
      final java.util.LinkedHashMap<String, SymParameter> theOptionalImplicitSpecProofParameters) {
    assert Util.nonNullElements(theOptionalImplicitSpecProofParameters);
    this.theOptionalImplicitSpecProofParameters = theOptionalImplicitSpecProofParameters;
  }

  public final void setTheOptionalImplicitSpecProofTranslation(
      final String theOptionalImplicitSpecProofTranslation) {
    if (theOptionalImplicitSpecProofTranslation != null) {
      this.theOptionalImplicitSpecProofTranslation = theOptionalImplicitSpecProofTranslation
          .intern();
    } else {
      this.theOptionalImplicitSpecProofTranslation = null;
    }
  }
}
