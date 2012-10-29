/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.util.message;

public abstract class Message {
  protected String theMessageText;

  public Message() {
  }

  public int getDescriptor() {
    return 0;
  }

  public final String getTheMessageText() {
    return this.theMessageText;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setTheMessageText(final String theMessageText) {
    assert theMessageText != null;
    if (theMessageText != null) {
      this.theMessageText = theMessageText.intern();
    } else {
      this.theMessageText = null;
    }
  }

}
