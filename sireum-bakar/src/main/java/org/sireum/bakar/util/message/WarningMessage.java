/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.util.message;

public final class WarningMessage extends UserMessage {

  public final static int DESCRIPTOR = 1;

  public WarningMessage() {
  }

  @Override
  public final int getDescriptor() {
    return WarningMessage.DESCRIPTOR;
  }
}
