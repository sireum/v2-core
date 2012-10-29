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

import java.io.PrintWriter;
import java.util.List;

public class Util {
  public static void displayMessages(final PrintWriter pwOut,
      final PrintWriter pwErr, final List<Message> messages) {
    Util.displayMessages(pwOut, pwErr, messages, 0, messages.size() - 1);
  }

  public static int displayMessages(final PrintWriter pwOut,
      final PrintWriter pwErr, final List<Message> messages,
      final int startIndex) {
    return Util.displayMessages(
        pwOut,
        pwErr,
        messages,
        startIndex,
        messages.size() - 1);
  }

  public static int displayMessages(final PrintWriter pwOut,
      final PrintWriter pwErr, final List<Message> messages,
      final int startIndex, final int lastIndex) {
    assert (pwOut != null) && (pwErr != null) && (messages != null);
    if (messages.isEmpty()) {
      return startIndex;
    }
    final int size = Math.min(messages.size() - 1, lastIndex);
    int i = startIndex;
    while (i <= size) {
      final Message m = messages.get(i);
      switch (m.getDescriptor()) {
        case ErrorMessage.DESCRIPTOR:
        case WarningMessage.DESCRIPTOR:
        case InformationalMessage.DESCRIPTOR: {
          final UserMessage um = (UserMessage) m;
          final PrintWriter pw = m instanceof ErrorMessage ? pwErr : pwOut;
          pw.println();
          if (!((um.getTheLineNumber() == 0) && (um.getTheColumnNumber() == 0))) {
            pw.print("[");
            pw.print(um.getTheLineNumber());
            pw.print(", ");
            pw.print(um.getTheColumnNumber());
            pw.print("] ");
          }
          pw.print(m.getTheMessageText());
          if (um.getTheOptionalSource() != null) {
            pw.print(" (");
            pw.print(um.getTheOptionalSource());
            pw.print(")");
            pw.println();
          }
          pw.flush();
          break;
        }
        case InternalErrorMessage.DESCRIPTOR: {
          final InternalErrorMessage iem = (InternalErrorMessage) m;
          pwErr.println();
          pwErr.println("Internal error: " + iem.getTheMessageText());
          pwErr.println("---");
          for (final StackTraceElement ste : iem.getStackTraces()) {
            pwErr.print(ste.getClassName() + '.' + ste.getMethodName());
            pwErr.println(Util.getSourceLineInfo(
                ste.getFileName(),
                ste.getLineNumber()));
          }
          pwErr.println("---");
          pwErr.println("Please report this problem at http://www.sireum.org");
          pwErr.flush();
          break;
        }
      }
      i++;
    }
    pwOut.println();
    pwOut.flush();
    return i;
  }

  public static String getSourceLineInfo(final String source, final int lineNo) {
    if (source != null) {
      if (lineNo > 0) {
        return " (" + source + ":" + lineNo + ')';
      } else {
        return " (" + source + ')';
      }
    } else {
      if (lineNo > 0) {
        return ":" + lineNo;
      } else {
        return "";
      }
    }
  }
}
