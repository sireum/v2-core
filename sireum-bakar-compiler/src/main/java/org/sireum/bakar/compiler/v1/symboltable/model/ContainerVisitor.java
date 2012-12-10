/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.symboltable.model;

public class ContainerVisitor<G> {
  protected G g;

  public ContainerVisitor(final G g) {
    assert g != null;
    this.g = g;
  }

  public void visit(final Container o) {
    assert o != null;
    switch (o.getDescriptor()) {
      case ContainerOwnStatement.DESCRIPTOR:
        visitContainerOwnStatement((ContainerOwnStatement) o);
        break;

      case ContainerOwnClause.DESCRIPTOR:
        visitContainerOwnClause((ContainerOwnClause) o);
        break;

      case ContainerRefinementDefinition.DESCRIPTOR:
        visitContainerRefinementDefinition((ContainerRefinementDefinition) o);
        break;

      case ContainerRefinementClause.DESCRIPTOR:
        visitContainerRefinementClause((ContainerRefinementClause) o);
        break;

      case ContainerDependencyRelation.DESCRIPTOR:
        visitContainerDependencyRelation((ContainerDependencyRelation) o);
        break;

      case ContainerDependencyClause.DESCRIPTOR:
        visitContainerDependencyClause((ContainerDependencyClause) o);
        break;

      default:
        assert false;
        break;
    }
  }

  protected void visit(final Object o) {
    assert false;
  }

  protected void visitContainer(final Container o) {
    assert o != null;
  }

  protected void visitContainerDependencyClause(
      final ContainerDependencyClause o) {
    assert o != null;
    visitContainer(o);

    for (final SymDerivesAnnotationModel e0 : o.theDependencyClauseMembers) {
      visit(e0);
    }

    visit(o.theDependencyClauseRegionSelection);
  }

  protected void visitContainerDependencyRelation(
      final ContainerDependencyRelation o) {
    assert o != null;
    visitContainer(o);

    for (final ContainerDependencyClause e0 : o.theDependencyClauses) {
      visit(e0);
    }

    visit(o.theDependencyRelationRegionSelection);
  }

  protected void visitContainerOwnClause(final ContainerOwnClause o) {
    assert o != null;
    visitContainer(o);

    for (final SymOwnVariableAnnotation e0 : o.theOwnVariables) {
      visit(e0);
    }

    if (o.theOptionalType != null) {
      visit(o.theOptionalType);
    }

    visit(o.theOwnClauseSelection);
  }

  protected void visitContainerOwnStatement(final ContainerOwnStatement o) {
    assert o != null;
    visitContainer(o);

    for (final ContainerOwnClause e0 : o.theOwnClauses) {
    }

    visit(o.theOwnStatementSelection);
  }

  protected void visitContainerRefinementClause(
      final ContainerRefinementClause o) {
    assert o != null;
    visitContainer(o);

    visit(o.theRefinementClause);

    visit(o.theRefinementClauseSelection);
  }

  protected void visitContainerRefinementDefinition(
      final ContainerRefinementDefinition o) {
    assert o != null;
    visitContainer(o);

    for (final ContainerRefinementClause e0 : o.theRefinementClauses) {
      visit(e0);
    }

    visit(o.theRefinementDefinitionSelection);
  }

}
