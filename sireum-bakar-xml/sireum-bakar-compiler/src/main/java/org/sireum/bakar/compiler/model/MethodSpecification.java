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

public abstract class MethodSpecification extends Node {
  protected String theIDString;

  protected org.sireum.bakar.selection.model.RegionSelection theMethodNameSelection;

  protected ArrayList<ParameterSpecification> theOptionalParameterSpecification;

  public MethodSpecification() {
  }

  public final String getTheIDString() {
    return this.theIDString;
  }

  public final org.sireum.bakar.selection.model.RegionSelection getTheMethodNameSelection() {
    return this.theMethodNameSelection;
  }

  public final ArrayList<ParameterSpecification> getTheOptionalParameterSpecification() {
    return this.theOptionalParameterSpecification;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheIDString(final String theIDString) {
    assert theIDString != null;
    if (theIDString != null) {
      this.theIDString = theIDString.intern();
    } else {
      this.theIDString = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheMethodNameSelection(
      final org.sireum.bakar.selection.model.RegionSelection theMethodNameSelection) {
    assert theMethodNameSelection != null;
    this.theMethodNameSelection = theMethodNameSelection;
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalParameterSpecification(
      final ArrayList<ParameterSpecification> theOptionalParameterSpecification) {
    assert Util.nonNullElements(theOptionalParameterSpecification);
    this.theOptionalParameterSpecification = theOptionalParameterSpecification;
  }

}
