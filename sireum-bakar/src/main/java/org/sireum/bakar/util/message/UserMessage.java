/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.util.message;

public abstract class UserMessage extends Message {
  protected String theOptionalSource;

  protected int theLineNumber;

  protected int theColumnNumber;

  protected int theOptionalEndLineNumber = -1;

  protected int theOptionalEndColumnNumber = -1;

  public UserMessage() {
  }

  public final int getTheColumnNumber() {
    return this.theColumnNumber;
  }

  public final int getTheLineNumber() {
    return this.theLineNumber;
  }

  public final int getTheOptionalEndColumnNumber() {
    return this.theOptionalEndColumnNumber;
  }

  public final int getTheOptionalEndLineNumber() {
    return this.theOptionalEndLineNumber;
  }

  public final String getTheOptionalSource() {
    return this.theOptionalSource;
  }

  public final void setTheColumnNumber(final int theColumnNumber) {
    this.theColumnNumber = theColumnNumber;
  }

  public final void setTheLineNumber(final int theLineNumber) {
    this.theLineNumber = theLineNumber;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * -1
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheOptionalEndColumnNumber(
      final int theOptionalEndColumnNumber) {
    this.theOptionalEndColumnNumber = theOptionalEndColumnNumber;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * -1
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setTheOptionalEndLineNumber(
      final int theOptionalEndLineNumber) {
    this.theOptionalEndLineNumber = theOptionalEndLineNumber;
  }

  public final void setTheOptionalSource(final String theOptionalSource) {
    if (theOptionalSource != null) {
      this.theOptionalSource = theOptionalSource.intern();
    } else {
      this.theOptionalSource = null;
    }
  }

}
