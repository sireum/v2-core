/******************************************************************************
 * Copyright (c) 2008 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 *                                                                            *
 * Contributors:                                                              *
 *     Robby - initial API and implementation                                 *
 ******************************************************************************/

package org.sireum.bakar.util.message;

import java.util.ArrayList;
import java.util.List;

public class MessageFactory {
  public final static boolean hasErrorMessage(final List<Message> messages) {
    for (final Message message : messages) {
      if (MessageFactory.isErrorMessage(message)) {
        return true;
      }
    }
    return false;
  }

  public final static boolean isErrorMessage(final Message message) {
    switch (message.getDescriptor()) {
      case ErrorMessage.DESCRIPTOR:
      case InternalErrorMessage.DESCRIPTOR:
        return true;
      default:
        return false;
    }
  }

  public final static ErrorMessage newErrorMessage(final String messageText) {
    return MessageFactory.newErrorMessage(null, 0, 0, messageText);
  }

  public final static ErrorMessage newErrorMessage(final String source,
      final int lineNumber, final int columnNumber, final String messageText) {
    final ErrorMessage result = new ErrorMessage();
    result.setTheMessageText(messageText);
    result.setTheColumnNumber(columnNumber);
    result.setTheLineNumber(lineNumber);
    result.setTheOptionalSource(source);
    return result;
  }

  public final static ErrorMessage newErrorMessage(final String source,
      final int lineNumber, final String messageText) {
    return MessageFactory.newErrorMessage(source, lineNumber, 0, messageText);
  }

  public final static InformationalMessage newInformationalMessage(
      final String messageText) {
    return MessageFactory.newInformationalMessage(null, 0, 0, messageText);
  }

  public final static InformationalMessage newInformationalMessage(
      final String source, final int lineNumber, final int columnNumber,
      final String messageText) {
    final InformationalMessage result = new InformationalMessage();
    result.setTheMessageText(messageText);
    result.setTheColumnNumber(columnNumber);
    result.setTheLineNumber(lineNumber);
    result.setTheOptionalSource(source);
    return result;
  }

  public final static InformationalMessage newInformationalMessage(
      final String source, final int lineNumber, final String messageText) {
    return MessageFactory.newInformationalMessage(
        source,
        lineNumber,
        0,
        messageText);
  }

  public final static InternalErrorMessage newInternalErrorMessage(
      final String messageText, final int numToSkip) {
    final InternalErrorMessage result = new InternalErrorMessage();
    result.setTheMessageText(messageText);
    final Throwable t = new Throwable();
    t.fillInStackTrace();
    final StackTraceElement[] temp = t.getStackTrace();
    final ArrayList<StackTraceElement> stes = new ArrayList<StackTraceElement>(
        temp.length);
    int i = 0;
    for (final StackTraceElement ste : temp) {
      if (i < numToSkip) {
        i++;
        continue;
      }
      stes.add(ste);
    }
    result.setStackTraces(stes);
    return result;
  }

  public final static WarningMessage newWarningMessage(final String messageText) {
    return MessageFactory.newWarningMessage(null, 0, 0, messageText);
  }

  public final static WarningMessage newWarningMessage(final String source,
      final int lineNumber, final int columnNumber, final String messageText) {
    final WarningMessage result = new WarningMessage();
    result.setTheMessageText(messageText);
    result.setTheColumnNumber(columnNumber);
    result.setTheLineNumber(lineNumber);
    result.setTheOptionalSource(source);
    return result;
  }

  public final static WarningMessage newWarningMessage(final String source,
      final int lineNumber, final String messageText) {
    return MessageFactory.newWarningMessage(source, lineNumber, 0, messageText);
  }
}
