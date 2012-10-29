/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public abstract class SymCall extends SymPackageElement {
  protected int theNumberOfParams = 0;

  protected java.util.LinkedHashSet<SymGlobalAnnotation> theOptionalDeclaredSpecGlobals = new java.util.LinkedHashSet<SymGlobalAnnotation>();

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalDeclaredSpecGlobalsSelection;

  protected java.util.LinkedHashSet<SymGlobalAnnotation> theOptionalDeclaredBodyGlobals = new java.util.LinkedHashSet<SymGlobalAnnotation>();

  protected org.sireum.bakar.selection.model.RegionSelection theOptionalDeclaredBodyGlobalsSelection;

  protected java.util.LinkedHashSet<SymDec> theOptionalInferredBodyGlobals = new java.util.LinkedHashSet<SymDec>();

  protected ArrayList<SymGlobalAnnotation> theInGlobalsSpec = new ArrayList<SymGlobalAnnotation>();

  protected ArrayList<SymGlobalAnnotation> theInGlobalsBody = new ArrayList<SymGlobalAnnotation>();

  protected java.util.LinkedHashMap<String, SymParameter> theParameters = new java.util.LinkedHashMap<String, SymParameter>();

  public SymCall() {
  }

  public final ArrayList<SymGlobalAnnotation> getTheInGlobalsBody() {
    return this.theInGlobalsBody;
  }

  public final ArrayList<SymGlobalAnnotation> getTheInGlobalsSpec() {
    return this.theInGlobalsSpec;
  }

  public final int getTheNumberOfParams() {
    return this.theNumberOfParams;
  }

  public final java.util.LinkedHashSet<SymGlobalAnnotation> getTheOptionalDeclaredBodyGlobals() {
    return this.theOptionalDeclaredBodyGlobals;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalDeclaredBodyGlobalsSelection() {
    return this.theOptionalDeclaredBodyGlobalsSelection;
  }

  public final java.util.LinkedHashSet<SymGlobalAnnotation> getTheOptionalDeclaredSpecGlobals() {
    return this.theOptionalDeclaredSpecGlobals;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheOptionalDeclaredSpecGlobalsSelection() {
    return this.theOptionalDeclaredSpecGlobalsSelection;
  }

  public final java.util.LinkedHashSet<SymDec> getTheOptionalInferredBodyGlobals() {
    return this.theOptionalInferredBodyGlobals;
  }

  public final java.util.LinkedHashMap<String, SymParameter> getTheParameters() {
    return this.theParameters;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new ArrayList&lt;SymGlobalAnnotation&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheInGlobalsBody(
      final ArrayList<SymGlobalAnnotation> theInGlobalsBody) {
    assert Util.nonNullElements(theInGlobalsBody);
    this.theInGlobalsBody = theInGlobalsBody;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new ArrayList&lt;SymGlobalAnnotation&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheInGlobalsSpec(
      final ArrayList<SymGlobalAnnotation> theInGlobalsSpec) {
    assert Util.nonNullElements(theInGlobalsSpec);
    this.theInGlobalsSpec = theInGlobalsSpec;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * 0
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheNumberOfParams(final int theNumberOfParams) {
    this.theNumberOfParams = theNumberOfParams;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymGlobalAnnotation&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheOptionalDeclaredBodyGlobals(
      final java.util.LinkedHashSet<SymGlobalAnnotation> theOptionalDeclaredBodyGlobals) {
    assert Util.nonNullElements(theOptionalDeclaredBodyGlobals);
    this.theOptionalDeclaredBodyGlobals = theOptionalDeclaredBodyGlobals;
  }

  public final void setTheOptionalDeclaredBodyGlobalsSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalDeclaredBodyGlobalsSelection) {
    this.theOptionalDeclaredBodyGlobalsSelection = theOptionalDeclaredBodyGlobalsSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymGlobalAnnotation&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheOptionalDeclaredSpecGlobals(
      final java.util.LinkedHashSet<SymGlobalAnnotation> theOptionalDeclaredSpecGlobals) {
    assert Util.nonNullElements(theOptionalDeclaredSpecGlobals);
    this.theOptionalDeclaredSpecGlobals = theOptionalDeclaredSpecGlobals;
  }

  public final void setTheOptionalDeclaredSpecGlobalsSelection(
      final org.sireum.bakar.selection.model.RegionSelection theOptionalDeclaredSpecGlobalsSelection) {
    this.theOptionalDeclaredSpecGlobalsSelection = theOptionalDeclaredSpecGlobalsSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymDec&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheOptionalInferredBodyGlobals(
      final java.util.LinkedHashSet<SymDec> theOptionalInferredBodyGlobals) {
    assert Util.nonNullElements(theOptionalInferredBodyGlobals);
    this.theOptionalInferredBodyGlobals = theOptionalInferredBodyGlobals;
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
  public final void setTheParameters(
      final java.util.LinkedHashMap<String, SymParameter> theParameters) {
    assert Util.nonNullElements(theParameters);
    this.theParameters = theParameters;
  }

}
