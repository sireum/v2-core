/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

import java.util.ArrayList;

import org.sireum.bakar.util.Util;

public final class JustificationClause extends Node {
  protected MessageKind theKind;

  protected String theMessageID;

  protected ArrayList<Name> theOptionalNames;

  protected String theMessage;

  public final static int DESCRIPTOR = 78;

  public JustificationClause() {
  }

  @Override
  public final int getDescriptor() {
    return JustificationClause.DESCRIPTOR;
  }

  public final MessageKind getTheKind() {
    return this.theKind;
  }

  public final String getTheMessage() {
    return this.theMessage;
  }

  public final String getTheMessageID() {
    return this.theMessageID;
  }

  public final ArrayList<Name> getTheOptionalNames() {
    return this.theOptionalNames;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheKind(final MessageKind theKind) {
    assert theKind != null;
    this.theKind = theKind;
  }

  public final void setTheMessage(final String theMessage) {
    if (theMessage != null) {
      this.theMessage = theMessage.intern();
    } else {
      this.theMessage = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheMessageID(final String theMessageID) {
    assert theMessageID != null;
    if (theMessageID != null) {
      this.theMessageID = theMessageID.intern();
    } else {
      this.theMessageID = null;
    }
  }

  /**
   * <ul>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setTheOptionalNames(final ArrayList<Name> theOptionalNames) {
    assert Util.nonNullElements(theOptionalNames);
    this.theOptionalNames = theOptionalNames;
  }
}
