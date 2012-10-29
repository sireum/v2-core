/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.module.util;

import java.util.Map;

public class NodeVisitor<G> {
  protected G g;

  public NodeVisitor(final G g) {
    assert g != null;
    this.g = g;
  }

  public void visit(final Node o) {
    assert o != null;
    switch (o.getDescriptor()) {
      case SPARKParserResult.DESCRIPTOR:
        visitSPARKParserResult((SPARKParserResult) o);
        break;

      case SPARKCompilationUnit.DESCRIPTOR:
        visitSPARKCompilationUnit((SPARKCompilationUnit) o);
        break;

      case SPARKSpecBodyUnit.DESCRIPTOR:
        visitSPARKSpecBodyUnit((SPARKSpecBodyUnit) o);
        break;

      default:
        assert false;
        break;
    }
  }

  protected void visit(final Object o) {
    assert false;
  }

  protected void visitNode(final Node o) {
    assert o != null;
  }

  protected void visitSPARKCompilationUnit(final SPARKCompilationUnit o) {
    assert o != null;
    visitNode(o);

    visit(o.theContextClause);

    visit(o.theCompilationUnit);

    if (o.theOptionalWithNames != null) {
      for (final String e0 : o.theOptionalWithNames) {
      }
    }

    if (o.theOptionalUseNames != null) {
      for (final String e0 : o.theOptionalUseNames) {
      }
    }

    if (o.theOptionalInheritNames != null) {
      for (final String e0 : o.theOptionalInheritNames) {
      }
    }
  }

  protected void visitSPARKParserResult(final SPARKParserResult o) {
    assert o != null;
    visitNode(o);

    for (final String e0 : o.theSparkSourceFilenames) {
    }

    for (final Map.Entry<String, SPARKSpecBodyUnit> e0 : o.thePackageNameToSpecBodyNodes
        .entrySet()) {
      final SPARKSpecBodyUnit v0 = e0.getValue();
      visit(v0);
    }

    if (o.theOptionalMainCompilationUnit != null) {
      visit(o.theOptionalMainCompilationUnit);
    }
  }

  protected void visitSPARKSpecBodyUnit(final SPARKSpecBodyUnit o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalPackageSpecification != null) {
      visit(o.theOptionalPackageSpecification);
    }

    if (o.theOptionalPackageBody != null) {
      visit(o.theOptionalPackageBody);
    }
  }

}
