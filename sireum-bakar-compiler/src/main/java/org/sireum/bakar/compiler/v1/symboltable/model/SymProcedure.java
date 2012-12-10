/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class SymProcedure extends SymCall {
  protected ArrayList<SymGlobalAnnotation> theOutGlobalsSpec = new ArrayList<SymGlobalAnnotation>();

  protected ContainerDependencyRelation theOptionalDependencyRelationSpec;

  protected ArrayList<SymDerivesAnnotationModel> theDerivesClausesSpec = new ArrayList<SymDerivesAnnotationModel>();

  protected ArrayList<SymGlobalAnnotation> theOutGlobalsBody = new ArrayList<SymGlobalAnnotation>();

  protected ContainerDependencyRelation theOptionalDependencyRelationBody;

  protected ArrayList<SymDerivesAnnotationModel> theDerivesClausesBody = new ArrayList<SymDerivesAnnotationModel>();

  public final static int DESCRIPTOR = 11;

  public SymProcedure() {
  }

  @Override
  public final int getDescriptor() {
    return SymProcedure.DESCRIPTOR;
  }

  public final ArrayList<SymDerivesAnnotationModel> getTheDerivesClausesBody() {
    return this.theDerivesClausesBody;
  }

  public final ArrayList<SymDerivesAnnotationModel> getTheDerivesClausesSpec() {
    return this.theDerivesClausesSpec;
  }

  public final ContainerDependencyRelation getTheOptionalDependencyRelationBody() {
    return this.theOptionalDependencyRelationBody;
  }

  public final ContainerDependencyRelation getTheOptionalDependencyRelationSpec() {
    return this.theOptionalDependencyRelationSpec;
  }

  public final ArrayList<SymGlobalAnnotation> getTheOutGlobalsBody() {
    return this.theOutGlobalsBody;
  }

  public final ArrayList<SymGlobalAnnotation> getTheOutGlobalsSpec() {
    return this.theOutGlobalsSpec;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new ArrayList&lt;SymDerivesAnnotationModel&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheDerivesClausesBody(
      final ArrayList<SymDerivesAnnotationModel> theDerivesClausesBody) {
    assert Util.nonNullElements(theDerivesClausesBody);
    this.theDerivesClausesBody = theDerivesClausesBody;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new ArrayList&lt;SymDerivesAnnotationModel&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheDerivesClausesSpec(
      final ArrayList<SymDerivesAnnotationModel> theDerivesClausesSpec) {
    assert Util.nonNullElements(theDerivesClausesSpec);
    this.theDerivesClausesSpec = theDerivesClausesSpec;
  }

  public final void setTheOptionalDependencyRelationBody(
      final ContainerDependencyRelation theOptionalDependencyRelationBody) {
    this.theOptionalDependencyRelationBody = theOptionalDependencyRelationBody;
  }

  public final void setTheOptionalDependencyRelationSpec(
      final ContainerDependencyRelation theOptionalDependencyRelationSpec) {
    this.theOptionalDependencyRelationSpec = theOptionalDependencyRelationSpec;
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
  public final void setTheOutGlobalsBody(
      final ArrayList<SymGlobalAnnotation> theOutGlobalsBody) {
    assert Util.nonNullElements(theOutGlobalsBody);
    this.theOutGlobalsBody = theOutGlobalsBody;
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
  public final void setTheOutGlobalsSpec(
      final ArrayList<SymGlobalAnnotation> theOutGlobalsSpec) {
    assert Util.nonNullElements(theOutGlobalsSpec);
    this.theOutGlobalsSpec = theOutGlobalsSpec;
  }
}
