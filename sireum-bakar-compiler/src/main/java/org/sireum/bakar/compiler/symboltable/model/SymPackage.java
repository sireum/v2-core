/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.sireum.bakar.util.Util;

public final class SymPackage extends SymPackageElement {
  protected boolean isChild;

  protected boolean isEmbedded;

  protected boolean hasSpec;

  protected boolean hasBody;

  protected String specificationFile;

  protected String bodyFile;

  protected org.sireum.bakar.selection.model.RegionSelection thePackageSpecSelection;

  protected org.sireum.bakar.selection.model.RegionSelection thePackageBodySelection;

  protected java.util.LinkedHashSet<SymPackage> theSpecWithPackages = new java.util.LinkedHashSet<SymPackage>();

  protected java.util.LinkedHashSet<SymPackage> theBodyWithPackages = new java.util.LinkedHashSet<SymPackage>();

  protected java.util.LinkedHashSet<SymDef> theSpecUseTypeClauses = new java.util.LinkedHashSet<SymDef>();

  protected java.util.LinkedHashSet<SymDef> theBodyUseTypeClauses = new java.util.LinkedHashSet<SymDef>();

  protected java.util.LinkedHashSet<SymPackage> theInheritedPackages = new java.util.LinkedHashSet<SymPackage>();

  protected java.util.LinkedHashSet<SymSimpleAnnotation> theInitializeVariables = new java.util.LinkedHashSet<SymSimpleAnnotation>();

  protected ContainerOwnStatement theOptionalOwnStatement;

  protected java.util.LinkedHashSet<SymOwnVariableAnnotation> theOwnVariables = new java.util.LinkedHashSet<SymOwnVariableAnnotation>();

  protected ContainerRefinementDefinition theOptionalRefinementDefinition;

  protected ArrayList<SymRefinementAnnotation> theRefinements = new ArrayList<SymRefinementAnnotation>();

  protected HashMap<String, SymPackage> theChildPackages = new HashMap<String, SymPackage>();

  protected HashMap<String, SymPackage> theEmbeddedPackages = new HashMap<String, SymPackage>();

  protected HashMap<String, SymProcedure> theProcedures = new HashMap<String, SymProcedure>();

  protected HashMap<String, SymFunction> theFunctions = new HashMap<String, SymFunction>();

  public final static int DESCRIPTOR = 10;

  public SymPackage() {
  }

  public final String getBodyFile() {
    return this.bodyFile;
  }

  @Override
  public final int getDescriptor() {
    return SymPackage.DESCRIPTOR;
  }

  public final boolean getHasBody() {
    return this.hasBody;
  }

  public final boolean getHasSpec() {
    return this.hasSpec;
  }

  public final boolean getIsChild() {
    return this.isChild;
  }

  public final boolean getIsEmbedded() {
    return this.isEmbedded;
  }

  public final String getSpecificationFile() {
    return this.specificationFile;
  }

  public final java.util.LinkedHashSet<SymDef> getTheBodyUseTypeClauses() {
    return this.theBodyUseTypeClauses;
  }

  public final java.util.LinkedHashSet<SymPackage> getTheBodyWithPackages() {
    return this.theBodyWithPackages;
  }

  public final HashMap<String, SymPackage> getTheChildPackages() {
    return this.theChildPackages;
  }

  public final HashMap<String, SymPackage> getTheEmbeddedPackages() {
    return this.theEmbeddedPackages;
  }

  public final HashMap<String, SymFunction> getTheFunctions() {
    return this.theFunctions;
  }

  public final java.util.LinkedHashSet<SymPackage> getTheInheritedPackages() {
    return this.theInheritedPackages;
  }

  public final java.util.LinkedHashSet<SymSimpleAnnotation> getTheInitializeVariables() {
    return this.theInitializeVariables;
  }

  public final ContainerOwnStatement getTheOptionalOwnStatement() {
    return this.theOptionalOwnStatement;
  }

  public final ContainerRefinementDefinition getTheOptionalRefinementDefinition() {
    return this.theOptionalRefinementDefinition;
  }

  public final java.util.LinkedHashSet<SymOwnVariableAnnotation> getTheOwnVariables() {
    return this.theOwnVariables;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getThePackageBodySelection() {
    return this.thePackageBodySelection;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getThePackageSpecSelection() {
    return this.thePackageSpecSelection;
  }

  public final HashMap<String, SymProcedure> getTheProcedures() {
    return this.theProcedures;
  }

  public final ArrayList<SymRefinementAnnotation> getTheRefinements() {
    return this.theRefinements;
  }

  public final java.util.LinkedHashSet<SymDef> getTheSpecUseTypeClauses() {
    return this.theSpecUseTypeClauses;
  }

  public final java.util.LinkedHashSet<SymPackage> getTheSpecWithPackages() {
    return this.theSpecWithPackages;
  }

  public final void setBodyFile(final String bodyFile) {
    if (bodyFile != null) {
      this.bodyFile = bodyFile.intern();
    } else {
      this.bodyFile = null;
    }
  }

  public final void setHasBody(final boolean hasBody) {
    this.hasBody = hasBody;
  }

  public final void setHasSpec(final boolean hasSpec) {
    this.hasSpec = hasSpec;
  }

  public final void setIsChild(final boolean isChild) {
    this.isChild = isChild;
  }

  public final void setIsEmbedded(final boolean isEmbedded) {
    this.isEmbedded = isEmbedded;
  }

  public final void setSpecificationFile(final String specificationFile) {
    if (specificationFile != null) {
      this.specificationFile = specificationFile.intern();
    } else {
      this.specificationFile = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymDef&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheBodyUseTypeClauses(
      final java.util.LinkedHashSet<SymDef> theBodyUseTypeClauses) {
    assert Util.nonNullElements(theBodyUseTypeClauses);
    this.theBodyUseTypeClauses = theBodyUseTypeClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymPackage&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheBodyWithPackages(
      final java.util.LinkedHashSet<SymPackage> theBodyWithPackages) {
    assert Util.nonNullElements(theBodyWithPackages);
    this.theBodyWithPackages = theBodyWithPackages;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new HashMap&lt;String, SymPackage&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheChildPackages(
      final HashMap<String, SymPackage> theChildPackages) {
    assert Util.nonNullElements(theChildPackages);
    this.theChildPackages = theChildPackages;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new HashMap&lt;String, SymPackage&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheEmbeddedPackages(
      final HashMap<String, SymPackage> theEmbeddedPackages) {
    assert Util.nonNullElements(theEmbeddedPackages);
    this.theEmbeddedPackages = theEmbeddedPackages;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new HashMap&lt;String, SymFunction&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheFunctions(
      final HashMap<String, SymFunction> theFunctions) {
    assert Util.nonNullElements(theFunctions);
    this.theFunctions = theFunctions;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymPackage&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheInheritedPackages(
      final java.util.LinkedHashSet<SymPackage> theInheritedPackages) {
    assert Util.nonNullElements(theInheritedPackages);
    this.theInheritedPackages = theInheritedPackages;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymSimpleAnnotation&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheInitializeVariables(
      final java.util.LinkedHashSet<SymSimpleAnnotation> theInitializeVariables) {
    assert Util.nonNullElements(theInitializeVariables);
    this.theInitializeVariables = theInitializeVariables;
  }

  public final void setTheOptionalOwnStatement(
      final ContainerOwnStatement theOptionalOwnStatement) {
    this.theOptionalOwnStatement = theOptionalOwnStatement;
  }

  public final void setTheOptionalRefinementDefinition(
      final ContainerRefinementDefinition theOptionalRefinementDefinition) {
    this.theOptionalRefinementDefinition = theOptionalRefinementDefinition;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymOwnVariableAnnotation&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheOwnVariables(
      final java.util.LinkedHashSet<SymOwnVariableAnnotation> theOwnVariables) {
    assert Util.nonNullElements(theOwnVariables);
    this.theOwnVariables = theOwnVariables;
  }

  public final void setThePackageBodySelection(
      final org.sireum.bakar.selection.model.RegionSelection thePackageBodySelection) {
    this.thePackageBodySelection = thePackageBodySelection;
  }

  public final void setThePackageSpecSelection(
      final org.sireum.bakar.selection.model.RegionSelection thePackageSpecSelection) {
    this.thePackageSpecSelection = thePackageSpecSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new HashMap&lt;String, SymProcedure&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheProcedures(
      final HashMap<String, SymProcedure> theProcedures) {
    assert Util.nonNullElements(theProcedures);
    this.theProcedures = theProcedures;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new ArrayList&lt;SymRefinementAnnotation&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheRefinements(
      final ArrayList<SymRefinementAnnotation> theRefinements) {
    assert Util.nonNullElements(theRefinements);
    this.theRefinements = theRefinements;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymDef&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheSpecUseTypeClauses(
      final java.util.LinkedHashSet<SymDef> theSpecUseTypeClauses) {
    assert Util.nonNullElements(theSpecUseTypeClauses);
    this.theSpecUseTypeClauses = theSpecUseTypeClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashSet&lt;SymPackage&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheSpecWithPackages(
      final java.util.LinkedHashSet<SymPackage> theSpecWithPackages) {
    assert Util.nonNullElements(theSpecWithPackages);
    this.theSpecWithPackages = theSpecWithPackages;
  }
}
