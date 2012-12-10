/******************************************************************************
 * Copyright (c) 2007 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 *                                                                            *
 * Contributors:                                                              *
 *     Robby - initial API and implementation                                 *
 ******************************************************************************/

package org.sireum.bakar.compiler.v1.parser;

import java.io.IOException;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.RewriteEmptyStreamException;
import org.sireum.bakar.compiler.v1.model.Compilation;
import org.sireum.bakar.util.message.Message;

public class CompilationParser {
  public static Compilation parse(final String filename,
      final ANTLRStringStream antlrss, final List<Message> errors)
      throws RecognitionException, IOException {
    final SPARKLexer plexer = new SPARKLexer(antlrss);
    final CommonTokenStream cts = new CommonTokenStream(plexer);
    final SPARKParser sparser = new SPARKParser(cts);
    sparser.setSource(filename);
    Compilation result = null;
    try {
      result = sparser.compilationFile();
    } catch (final RewriteEmptyStreamException e) {
    }
    final List<Message> e = sparser.popErrors();
    errors.addAll(e);
    return result;
  }

}
