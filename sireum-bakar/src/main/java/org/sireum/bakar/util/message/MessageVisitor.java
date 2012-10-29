/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.util.message;

public class MessageVisitor<G> {
  protected G g;

  public MessageVisitor(final G g) {
    assert g != null;
    this.g = g;
  }

  public void visit(final Message o) {
    assert o != null;
    switch (o.getDescriptor()) {
      case WarningMessage.DESCRIPTOR:
        visitWarningMessage((WarningMessage) o);
        break;

      case ErrorMessage.DESCRIPTOR:
        visitErrorMessage((ErrorMessage) o);
        break;

      case InformationalMessage.DESCRIPTOR:
        visitInformationalMessage((InformationalMessage) o);
        break;

      case InternalErrorMessage.DESCRIPTOR:
        visitInternalErrorMessage((InternalErrorMessage) o);
        break;

      default:
        assert false;
        break;
    }
  }

  protected void visit(final Object o) {
    assert false;
  }

  protected void visitErrorMessage(final ErrorMessage o) {
    assert o != null;
    visitUserMessage(o);
  }

  protected void visitInformationalMessage(final InformationalMessage o) {
    assert o != null;
    visitUserMessage(o);
  }

  protected void visitInternalErrorMessage(final InternalErrorMessage o) {
    assert o != null;
    visitMessage(o);
  }

  protected void visitMessage(final Message o) {
    assert o != null;
  }

  protected void visitUserMessage(final UserMessage o) {
    assert o != null;
    visitMessage(o);
  }

  protected void visitWarningMessage(final WarningMessage o) {
    assert o != null;
    visitUserMessage(o);
  }

}
