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

public abstract class SymPackageElement extends SymDec {
  protected java.util.LinkedHashMap<String, SymDef> theDefinitions = new java.util.LinkedHashMap<String, SymDef>();

  protected org.sireum.bakar.util.Stack<java.util.LinkedHashMap<String, SymObject>> theObjects = new org.sireum.bakar.util.Stack<java.util.LinkedHashMap<String, SymObject>>();

  protected ArrayList<org.sireum.bakar.compiler.v1.model.RepresentationClause> theOptionalRepresentationClauses;

  public SymPackageElement() {
  }

  public final java.util.LinkedHashMap<String, SymDef> getTheDefinitions() {
    return this.theDefinitions;
  }

  public final org.sireum.bakar.util.Stack<java.util.LinkedHashMap<String, SymObject>> getTheObjects() {
    return this.theObjects;
  }

  public final ArrayList<org.sireum.bakar.compiler.v1.model.RepresentationClause> getTheOptionalRepresentationClauses() {
    return this.theOptionalRepresentationClauses;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new java.util.LinkedHashMap&lt;String, SymDef&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheDefinitions(
      final java.util.LinkedHashMap<String, SymDef> theDefinitions) {
    assert Util.nonNullElements(theDefinitions);
    this.theDefinitions = theDefinitions;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * new org.sireum.util.Stack&lt;java.util.LinkedHashMap&lt;String, SymObject&gt;&gt;()
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheObjects(
      final org.sireum.bakar.util.Stack<java.util.LinkedHashMap<String, SymObject>> theObjects) {
    assert Util.nonNullElements(theObjects);
    this.theObjects = theObjects;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalRepresentationClauses(
      final ArrayList<org.sireum.bakar.compiler.v1.model.RepresentationClause> theOptionalRepresentationClauses) {
    assert Util.nonNullElements(theOptionalRepresentationClauses);
    this.theOptionalRepresentationClauses = theOptionalRepresentationClauses;
  }

}
