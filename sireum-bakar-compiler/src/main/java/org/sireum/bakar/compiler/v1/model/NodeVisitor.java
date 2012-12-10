/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sireum.bakar.util.Pair;
import org.sireum.bakar.util.Triple;
import org.sireum.bakar.util.Tuple;

public class NodeVisitor<G> {
  protected G g;

  public NodeVisitor(final G g) {
    assert g != null;
    this.g = g;
  }

  protected void visit(@SuppressWarnings("rawtypes") final List l) {
    if (l == null) {
      return;
    }
    for (final Object o : l) {
      visitObject(o);
    }
  }

  protected void visit(@SuppressWarnings("rawtypes") final Map m) {
    if (m == null) {
      return;
    }
    for (final Object o : m.entrySet()) {
      @SuppressWarnings("rawtypes")
      final Map.Entry e = (Map.Entry) o;
      visitObject(e.getKey());
      visitObject(e.getValue());
    }
  }

  public void visit(final Node o) {
    assert o != null;
    switch (o.getDescriptor()) {
      case Pragma.DESCRIPTOR:
        visitPragma((Pragma) o);
        break;

      case NamePragmaArgumentAssociation.DESCRIPTOR:
        visitNamePragmaArgumentAssociation((NamePragmaArgumentAssociation) o);
        break;

      case ExpPragmaArgumentAssociation.DESCRIPTOR:
        visitExpPragmaArgumentAssociation((ExpPragmaArgumentAssociation) o);
        break;

      case FullTypeDeclaration.DESCRIPTOR:
        visitFullTypeDeclaration((FullTypeDeclaration) o);
        break;

      case RecordTypeExtension.DESCRIPTOR:
        visitRecordTypeExtension((RecordTypeExtension) o);
        break;

      case SubTypeDeclaration.DESCRIPTOR:
        visitSubTypeDeclaration((SubTypeDeclaration) o);
        break;

      case SubTypeIndication.DESCRIPTOR:
        visitSubTypeIndication((SubTypeIndication) o);
        break;

      case ObjectDeclaration.DESCRIPTOR:
        visitObjectDeclaration((ObjectDeclaration) o);
        break;

      case NumberDeclaration.DESCRIPTOR:
        visitNumberDeclaration((NumberDeclaration) o);
        break;

      case RangeConstraint.DESCRIPTOR:
        visitRangeConstraint((RangeConstraint) o);
        break;

      case ExpRange.DESCRIPTOR:
        visitExpRange((ExpRange) o);
        break;

      case EnumerationTypeDefinition.DESCRIPTOR:
        visitEnumerationTypeDefinition((EnumerationTypeDefinition) o);
        break;

      case SignedIntegerTypeDefinition.DESCRIPTOR:
        visitSignedIntegerTypeDefinition((SignedIntegerTypeDefinition) o);
        break;

      case ModularTypeDefinition.DESCRIPTOR:
        visitModularTypeDefinition((ModularTypeDefinition) o);
        break;

      case FloatingPointDefinition.DESCRIPTOR:
        visitFloatingPointDefinition((FloatingPointDefinition) o);
        break;

      case OrdinaryFixedPointDefinition.DESCRIPTOR:
        visitOrdinaryFixedPointDefinition((OrdinaryFixedPointDefinition) o);
        break;

      case UnconstrainedArrayDefinition.DESCRIPTOR:
        visitUnconstrainedArrayDefinition((UnconstrainedArrayDefinition) o);
        break;

      case ConstrainedArrayDefinition.DESCRIPTOR:
        visitConstrainedArrayDefinition((ConstrainedArrayDefinition) o);
        break;

      case IndexConstraint.DESCRIPTOR:
        visitIndexConstraint((IndexConstraint) o);
        break;

      case RecordTypeDefinition.DESCRIPTOR:
        visitRecordTypeDefinition((RecordTypeDefinition) o);
        break;

      case RecordDefinition.DESCRIPTOR:
        visitRecordDefinition((RecordDefinition) o);
        break;

      case ComponentDeclaration.DESCRIPTOR:
        visitComponentDeclaration((ComponentDeclaration) o);
        break;

      case ExpChoice.DESCRIPTOR:
        visitExpChoice((ExpChoice) o);
        break;

      case SubTypeChoice.DESCRIPTOR:
        visitSubTypeChoice((SubTypeChoice) o);
        break;

      case RangeChoice.DESCRIPTOR:
        visitRangeChoice((RangeChoice) o);
        break;

      case DeclarativePart.DESCRIPTOR:
        visitDeclarativePart((DeclarativePart) o);
        break;

      case BasicDeclarationBasicDeclarativeItem.DESCRIPTOR:
        visitBasicDeclarationBasicDeclarativeItem((BasicDeclarationBasicDeclarativeItem) o);
        break;

      case RepresentationClauseBasicDeclarativeItem.DESCRIPTOR:
        visitRepresentationClauseBasicDeclarativeItem((RepresentationClauseBasicDeclarativeItem) o);
        break;

      case ProofFunctionDeclaration.DESCRIPTOR:
        visitProofFunctionDeclaration((ProofFunctionDeclaration) o);
        break;

      case ProofTypeDeclaration.DESCRIPTOR:
        visitProofTypeDeclaration((ProofTypeDeclaration) o);
        break;

      case TypeAssertion.DESCRIPTOR:
        visitTypeAssertion((TypeAssertion) o);
        break;

      case EmbeddedPackageDeclaration.DESCRIPTOR:
        visitEmbeddedPackageDeclaration((EmbeddedPackageDeclaration) o);
        break;

      case RenamingDeclarationEmbeddedPackageDeclarationMember.DESCRIPTOR:
        visitRenamingDeclarationEmbeddedPackageDeclarationMember((RenamingDeclarationEmbeddedPackageDeclarationMember) o);
        break;

      case UseTypeClauseEmbeddedPackageDeclarationMember.DESCRIPTOR:
        visitUseTypeClauseEmbeddedPackageDeclarationMember((UseTypeClauseEmbeddedPackageDeclarationMember) o);
        break;

      case ExternalSubProgramDeclaration.DESCRIPTOR:
        visitExternalSubProgramDeclaration((ExternalSubProgramDeclaration) o);
        break;

      case IDName.DESCRIPTOR:
        visitIDName((IDName) o);
        break;

      case IndexedComponent.DESCRIPTOR:
        visitIndexedComponent((IndexedComponent) o);
        break;

      case SelectedComponent.DESCRIPTOR:
        visitSelectedComponent((SelectedComponent) o);
        break;

      case AttributeReference.DESCRIPTOR:
        visitAttributeReference((AttributeReference) o);
        break;

      case RecordUpdate.DESCRIPTOR:
        visitRecordUpdate((RecordUpdate) o);
        break;

      case ArrayUpdate.DESCRIPTOR:
        visitArrayUpdate((ArrayUpdate) o);
        break;

      case IDAttributeDesignator.DESCRIPTOR:
        visitIDAttributeDesignator((IDAttributeDesignator) o);
        break;

      case DeltaAttributeDesignator.DESCRIPTOR:
        visitDeltaAttributeDesignator((DeltaAttributeDesignator) o);
        break;

      case DigitsAttributeDesignator.DESCRIPTOR:
        visitDigitsAttributeDesignator((DigitsAttributeDesignator) o);
        break;

      case RangeAttributeReference.DESCRIPTOR:
        visitRangeAttributeReference((RangeAttributeReference) o);
        break;

      case RangeAttributeDesignator.DESCRIPTOR:
        visitRangeAttributeDesignator((RangeAttributeDesignator) o);
        break;

      case PositionalRecordAggregate.DESCRIPTOR:
        visitPositionalRecordAggregate((PositionalRecordAggregate) o);
        break;

      case NamedRecordAggregate.DESCRIPTOR:
        visitNamedRecordAggregate((NamedRecordAggregate) o);
        break;

      case RecordComponentAssociation.DESCRIPTOR:
        visitRecordComponentAssociation((RecordComponentAssociation) o);
        break;

      case PositionalRecordExtensionAggregate.DESCRIPTOR:
        visitPositionalRecordExtensionAggregate((PositionalRecordExtensionAggregate) o);
        break;

      case NamedRecordExtensionAggregate.DESCRIPTOR:
        visitNamedRecordExtensionAggregate((NamedRecordExtensionAggregate) o);
        break;

      case NullRecordExtensionAggregate.DESCRIPTOR:
        visitNullRecordExtensionAggregate((NullRecordExtensionAggregate) o);
        break;

      case PositionalArrayAggregate.DESCRIPTOR:
        visitPositionalArrayAggregate((PositionalArrayAggregate) o);
        break;

      case NamedArrayAggregate.DESCRIPTOR:
        visitNamedArrayAggregate((NamedArrayAggregate) o);
        break;

      case ArrayComponentAssociation.DESCRIPTOR:
        visitArrayComponentAssociation((ArrayComponentAssociation) o);
        break;

      case ExpAggregateItem.DESCRIPTOR:
        visitExpAggregateItem((ExpAggregateItem) o);
        break;

      case ArrayAggregateItem.DESCRIPTOR:
        visitArrayAggregateItem((ArrayAggregateItem) o);
        break;

      case Predicate.DESCRIPTOR:
        visitPredicate((Predicate) o);
        break;

      case QuantifiedExp.DESCRIPTOR:
        visitQuantifiedExp((QuantifiedExp) o);
        break;

      case BinaryExp.DESCRIPTOR:
        visitBinaryExp((BinaryExp) o);
        break;

      case UnaryExp.DESCRIPTOR:
        visitUnaryExp((UnaryExp) o);
        break;

      case InRangeExp.DESCRIPTOR:
        visitInRangeExp((InRangeExp) o);
        break;

      case NameRangeExp.DESCRIPTOR:
        visitNameRangeExp((NameRangeExp) o);
        break;

      case LiteralExp.DESCRIPTOR:
        visitLiteralExp((LiteralExp) o);
        break;

      case NumericLiteral.DESCRIPTOR:
        visitNumericLiteral((NumericLiteral) o);
        break;

      case CharacterLiteral.DESCRIPTOR:
        visitCharacterLiteral((CharacterLiteral) o);
        break;

      case StringLiteral.DESCRIPTOR:
        visitStringLiteral((StringLiteral) o);
        break;

      case NameExp.DESCRIPTOR:
        visitNameExp((NameExp) o);
        break;

      case ParenExp.DESCRIPTOR:
        visitParenExp((ParenExp) o);
        break;

      case TypeConversion.DESCRIPTOR:
        visitTypeConversion((TypeConversion) o);
        break;

      case ExpQualifiedExp.DESCRIPTOR:
        visitExpQualifiedExp((ExpQualifiedExp) o);
        break;

      case AggregateQualifiedExp.DESCRIPTOR:
        visitAggregateQualifiedExp((AggregateQualifiedExp) o);
        break;

      case StatementList.DESCRIPTOR:
        visitStatementList((StatementList) o);
        break;

      case AssertStatement.DESCRIPTOR:
        visitAssertStatement((AssertStatement) o);
        break;

      case CheckStatement.DESCRIPTOR:
        visitCheckStatement((CheckStatement) o);
        break;

      case JustificationStatement.DESCRIPTOR:
        visitJustificationStatement((JustificationStatement) o);
        break;

      case JustificationStatementEnd.DESCRIPTOR:
        visitJustificationStatementEnd((JustificationStatementEnd) o);
        break;

      case JustificationClause.DESCRIPTOR:
        visitJustificationClause((JustificationClause) o);
        break;

      case NullStatement.DESCRIPTOR:
        visitNullStatement((NullStatement) o);
        break;

      case AssignmentStatement.DESCRIPTOR:
        visitAssignmentStatement((AssignmentStatement) o);
        break;

      case IfStatement.DESCRIPTOR:
        visitIfStatement((IfStatement) o);
        break;

      case CaseStatement.DESCRIPTOR:
        visitCaseStatement((CaseStatement) o);
        break;

      case CaseStatementAlternative.DESCRIPTOR:
        visitCaseStatementAlternative((CaseStatementAlternative) o);
        break;

      case DefaultLoopStatement.DESCRIPTOR:
        visitDefaultLoopStatement((DefaultLoopStatement) o);
        break;

      case WhileLoopStatement.DESCRIPTOR:
        visitWhileLoopStatement((WhileLoopStatement) o);
        break;

      case ForLoopStatement.DESCRIPTOR:
        visitForLoopStatement((ForLoopStatement) o);
        break;

      case ExitStatement.DESCRIPTOR:
        visitExitStatement((ExitStatement) o);
        break;

      case ProcedureSubProgramDeclaration.DESCRIPTOR:
        visitProcedureSubProgramDeclaration((ProcedureSubProgramDeclaration) o);
        break;

      case FunctionSubProgramDeclaration.DESCRIPTOR:
        visitFunctionSubProgramDeclaration((FunctionSubProgramDeclaration) o);
        break;

      case ProcedureSpecification.DESCRIPTOR:
        visitProcedureSpecification((ProcedureSpecification) o);
        break;

      case FunctionSpecification.DESCRIPTOR:
        visitFunctionSpecification((FunctionSpecification) o);
        break;

      case ParameterSpecification.DESCRIPTOR:
        visitParameterSpecification((ParameterSpecification) o);
        break;

      case ProcedureAnnotation.DESCRIPTOR:
        visitProcedureAnnotation((ProcedureAnnotation) o);
        break;

      case GlobalDefinition.DESCRIPTOR:
        visitGlobalDefinition((GlobalDefinition) o);
        break;

      case ReturnAnnotationExp.DESCRIPTOR:
        visitReturnAnnotationExp((ReturnAnnotationExp) o);
        break;

      case ReturnAnnotationPred.DESCRIPTOR:
        visitReturnAnnotationPred((ReturnAnnotationPred) o);
        break;

      case Precondition.DESCRIPTOR:
        visitPrecondition((Precondition) o);
        break;

      case Postcondition.DESCRIPTOR:
        visitPostcondition((Postcondition) o);
        break;

      case GlobalDeclaration.DESCRIPTOR:
        visitGlobalDeclaration((GlobalDeclaration) o);
        break;

      case FunctionAnnotation.DESCRIPTOR:
        visitFunctionAnnotation((FunctionAnnotation) o);
        break;

      case DependencyRelation.DESCRIPTOR:
        visitDependencyRelation((DependencyRelation) o);
        break;

      case DependencyClause.DESCRIPTOR:
        visitDependencyClause((DependencyClause) o);
        break;

      case ProcedureSubProgramBody.DESCRIPTOR:
        visitProcedureSubProgramBody((ProcedureSubProgramBody) o);
        break;

      case FunctionSubProgramBody.DESCRIPTOR:
        visitFunctionSubProgramBody((FunctionSubProgramBody) o);
        break;

      case StatementSubProgramImplementation.DESCRIPTOR:
        visitStatementSubProgramImplementation((StatementSubProgramImplementation) o);
        break;

      case CodeSubProgramImplementation.DESCRIPTOR:
        visitCodeSubProgramImplementation((CodeSubProgramImplementation) o);
        break;

      case ProcedureCallStatement.DESCRIPTOR:
        visitProcedureCallStatement((ProcedureCallStatement) o);
        break;

      case FunctionCall.DESCRIPTOR:
        visitFunctionCall((FunctionCall) o);
        break;

      case NamedParameterAssociationList.DESCRIPTOR:
        visitNamedParameterAssociationList((NamedParameterAssociationList) o);
        break;

      case NamedParameterAssociation.DESCRIPTOR:
        visitNamedParameterAssociation((NamedParameterAssociation) o);
        break;

      case PositionalParameterAssociationList.DESCRIPTOR:
        visitPositionalParameterAssociationList((PositionalParameterAssociationList) o);
        break;

      case ReturnStatement.DESCRIPTOR:
        visitReturnStatement((ReturnStatement) o);
        break;

      case PackageDeclaration.DESCRIPTOR:
        visitPackageDeclaration((PackageDeclaration) o);
        break;

      case PackageSpecification.DESCRIPTOR:
        visitPackageSpecification((PackageSpecification) o);
        break;

      case PackageAnnotation.DESCRIPTOR:
        visitPackageAnnotation((PackageAnnotation) o);
        break;

      case OwnVariable.DESCRIPTOR:
        visitOwnVariable((OwnVariable) o);
        break;

      case OwnVariableSpecification.DESCRIPTOR:
        visitOwnVariableSpecification((OwnVariableSpecification) o);
        break;

      case PackageBody.DESCRIPTOR:
        visitPackageBody((PackageBody) o);
        break;

      case RefinementClause.DESCRIPTOR:
        visitRefinementClause((RefinementClause) o);
        break;

      case Constituent.DESCRIPTOR:
        visitConstituent((Constituent) o);
        break;

      case PackageImplementation.DESCRIPTOR:
        visitPackageImplementation((PackageImplementation) o);
        break;

      case PrivateTypeDeclaration.DESCRIPTOR:
        visitPrivateTypeDeclaration((PrivateTypeDeclaration) o);
        break;

      case PrivateExtensionDeclaration.DESCRIPTOR:
        visitPrivateExtensionDeclaration((PrivateExtensionDeclaration) o);
        break;

      case UseTypeClause.DESCRIPTOR:
        visitUseTypeClause((UseTypeClause) o);
        break;

      case PackageRenamingDeclaration.DESCRIPTOR:
        visitPackageRenamingDeclaration((PackageRenamingDeclaration) o);
        break;

      case FunctionRenamingDeclaration.DESCRIPTOR:
        visitFunctionRenamingDeclaration((FunctionRenamingDeclaration) o);
        break;

      case FunctionSpecificationRenamingDeclaration.DESCRIPTOR:
        visitFunctionSpecificationRenamingDeclaration((FunctionSpecificationRenamingDeclaration) o);
        break;

      case ProcedureSpecificationRenamingDeclaration.DESCRIPTOR:
        visitProcedureSpecificationRenamingDeclaration((ProcedureSpecificationRenamingDeclaration) o);
        break;

      case Compilation.DESCRIPTOR:
        visitCompilation((Compilation) o);
        break;

      case LibraryCompilationUnit.DESCRIPTOR:
        visitLibraryCompilationUnit((LibraryCompilationUnit) o);
        break;

      case SubUnitCompilationUnit.DESCRIPTOR:
        visitSubUnitCompilationUnit((SubUnitCompilationUnit) o);
        break;

      case LibraryUnitBody.DESCRIPTOR:
        visitLibraryUnitBody((LibraryUnitBody) o);
        break;

      case MainProgram.DESCRIPTOR:
        visitMainProgram((MainProgram) o);
        break;

      case ContextClause.DESCRIPTOR:
        visitContextClause((ContextClause) o);
        break;

      case WithClause.DESCRIPTOR:
        visitWithClause((WithClause) o);
        break;

      case ProcedureBodyStub.DESCRIPTOR:
        visitProcedureBodyStub((ProcedureBodyStub) o);
        break;

      case FunctionBodyStub.DESCRIPTOR:
        visitFunctionBodyStub((FunctionBodyStub) o);
        break;

      case PackageBodyStub.DESCRIPTOR:
        visitPackageBodyStub((PackageBodyStub) o);
        break;

      case GenericInstantiation.DESCRIPTOR:
        visitGenericInstantiation((GenericInstantiation) o);
        break;

      case GenericAssociation.DESCRIPTOR:
        visitGenericAssociation((GenericAssociation) o);
        break;

      case AttributeDefinitionClause.DESCRIPTOR:
        visitAttributeDefinitionClause((AttributeDefinitionClause) o);
        break;

      case EnumerationRepresentationClause.DESCRIPTOR:
        visitEnumerationRepresentationClause((EnumerationRepresentationClause) o);
        break;

      case AtClause.DESCRIPTOR:
        visitAtClause((AtClause) o);
        break;

      case RecordRepresentationClause.DESCRIPTOR:
        visitRecordRepresentationClause((RecordRepresentationClause) o);
        break;

      case ComponentClause.DESCRIPTOR:
        visitComponentClause((ComponentClause) o);
        break;

      default:
        assert false;
        break;
    }
  }

  protected void visit(final Object o) {
    assert false;
  }

  protected void visit(@SuppressWarnings("rawtypes") final Set s) {
    if (s == null) {
      return;
    }
    for (final Object o : s) {
      visitObject(o);
    }
  }

  protected void visit(final Tuple t) {
    if (t == null) {
      return;
    }
    final int count = t.size();
    for (int i = 0; i < count; i++) {
      visitObject(t.get(i));
    }
  }

  protected void visitAggregate(final Aggregate o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitAggregateItem(final AggregateItem o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitAggregateQualifiedExp(final AggregateQualifiedExp o) {
    assert o != null;
    visitQualifiedExp(o);

    visit(o.theAggregate);
  }

  protected void visitArrayAggregate(final ArrayAggregate o) {
    assert o != null;
    visitAggregate(o);

    if (o.theOptionalOthersAggregateItem != null) {
      visit(o.theOptionalOthersAggregateItem);
    }
  }

  protected void visitArrayAggregateItem(final ArrayAggregateItem o) {
    assert o != null;
    visitAggregateItem(o);

    visit(o.theArrayAggregate);
  }

  protected void visitArrayComponentAssociation(
      final ArrayComponentAssociation o) {
    assert o != null;
    visitNode(o);

    for (final Choice e0 : o.theChoices) {
      visit(e0);
    }

    visit(o.theAggregateItem);
  }

  protected void visitArrayTypeDefinition(final ArrayTypeDefinition o) {
    assert o != null;
    visitTypeDefinition(o);
  }

  protected void visitArrayUpdate(final ArrayUpdate o) {
    assert o != null;
    visitName(o);

    visit(o.theName);

    for (final Pair<ArrayList<Exp>, Exp> e0 : o.theIndexListToExpressionList) {
      for (final Exp e1 : e0.getE0()) {
        visit(e1);
      }
      visit(e0.getE1());
    }
  }

  protected void visitAssertStatement(final AssertStatement o) {
    assert o != null;
    visitProofStatement(o);

    visit(o.thePredicate);
  }

  protected void visitAssignmentStatement(final AssignmentStatement o) {
    assert o != null;
    visitSimpleStatement(o);

    visit(o.theName);

    visit(o.theExp);
  }

  protected void visitAtClause(final AtClause o) {
    assert o != null;
    visitRepresentationClause(o);

    visit(o.theName);

    visit(o.theExp);
  }

  protected void visitAttributeDefinitionClause(
      final AttributeDefinitionClause o) {
    assert o != null;
    visitRepresentationClause(o);

    visit(o.theName);

    visit(o.theExp);
  }

  protected void visitAttributeDesignator(final AttributeDesignator o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitAttributeReference(final AttributeReference o) {
    assert o != null;
    visitName(o);

    visit(o.theName);

    visit(o.theAttributeDesignator);
  }

  protected void visitBasicDeclaration(final BasicDeclaration o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitBasicDeclarationBasicDeclarativeItem(
      final BasicDeclarationBasicDeclarativeItem o) {
    assert o != null;
    visitBasicDeclarativeItem(o);

    visit(o.theBasicDeclaration);
  }

  protected void visitBasicDeclarativeItem(final BasicDeclarativeItem o) {
    assert o != null;
    visitDeclarativeItem(o);
  }

  protected void visitBasicProofDeclaration(final BasicProofDeclaration o) {
    assert o != null;
    visitBasicDeclarativeItem(o);
  }

  protected void visitBinaryExp(final BinaryExp o) {
    assert o != null;
    visitExp(o);

    visit(o.theLeftExp);

    visit(o.theRightExp);
  }

  protected void visitBody(final Body o) {
    assert o != null;
    visitDeclarativeItem(o);
  }

  protected void visitBodyStub(final BodyStub o) {
    assert o != null;
    visitBody(o);
  }

  protected void visitCaseStatement(final CaseStatement o) {
    assert o != null;
    visitCompoundStatement(o);

    visit(o.theExp);

    for (final CaseStatementAlternative e0 : o.theCaseStatementAlternatives) {
      visit(e0);
    }

    if (o.theOptionalOthers != null) {
      visit(o.theOptionalOthers);
    }
  }

  protected void visitCaseStatementAlternative(final CaseStatementAlternative o) {
    assert o != null;
    visitNode(o);

    for (final Choice e0 : o.theChoices) {
      visit(e0);
    }

    visit(o.theStatementList);
  }

  protected void visitCharacterLiteral(final CharacterLiteral o) {
    assert o != null;
    visitLiteral(o);
  }

  protected void visitCheckStatement(final CheckStatement o) {
    assert o != null;
    visitProofStatement(o);

    visit(o.thePredicate);
  }

  protected void visitChoice(final Choice o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitCodeSubProgramImplementation(
      final CodeSubProgramImplementation o) {
    assert o != null;
    visitSubProgramImplementation(o);

    for (final QualifiedExp e0 : o.theQualifiedExps) {
      visit(e0);
    }
  }

  protected void visitCompilation(final Compilation o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalCompilationUnits != null) {
      for (final CompilationUnit e0 : o.theOptionalCompilationUnits) {
        visit(e0);
      }
    }
  }

  protected void visitCompilationUnit(final CompilationUnit o) {
    assert o != null;
    visitNode(o);

    visit(o.theContextClause);
  }

  protected void visitComponentClause(final ComponentClause o) {
    assert o != null;
    visitNode(o);

    visit(o.theName);

    visit(o.thePositionExp);

    visit(o.theFirstBitExp);

    visit(o.theLastBitExp);
  }

  protected void visitComponentDeclaration(final ComponentDeclaration o) {
    assert o != null;
    visitNode(o);

    for (final IDName e0 : o.theIDNames) {
      visit(e0);
    }

    visit(o.theName);
  }

  protected void visitCompoundStatement(final CompoundStatement o) {
    assert o != null;
    visitStatement(o);
  }

  protected void visitConstituent(final Constituent o) {
    assert o != null;
    visitNode(o);

    visit(o.theName);
  }

  protected void visitConstrainedArrayDefinition(
      final ConstrainedArrayDefinition o) {
    assert o != null;
    visitArrayTypeDefinition(o);

    for (final Name e0 : o.theDiscreteSubTypeNames) {
      visit(e0);
    }

    visit(o.theComponentName);
  }

  protected void visitConstraint(final Constraint o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitContextClause(final ContextClause o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalContextItems != null) {
      for (final ContextItem e0 : o.theOptionalContextItems) {
        visit(e0);
      }
    }
  }

  protected void visitContextItem(final ContextItem o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitDeclarativeItem(final DeclarativeItem o) {
    assert o != null;
    visitDeclarativePartMember(o);
  }

  protected void visitDeclarativePart(final DeclarativePart o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalRenamingDeclarations != null) {
      for (final RenamingDeclaration e0 : o.theOptionalRenamingDeclarations) {
        visit(e0);
      }
    }

    if (o.theOptionalDeclarativePartMembers != null) {
      for (final DeclarativePartMember e0 : o.theOptionalDeclarativePartMembers) {
        visit(e0);
      }
    }
  }

  protected void visitDeclarativePartMember(final DeclarativePartMember o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitDefaultLoopStatement(final DefaultLoopStatement o) {
    assert o != null;
    visitLoopStatement(o);
  }

  protected void visitDeltaAttributeDesignator(final DeltaAttributeDesignator o) {
    assert o != null;
    visitAttributeDesignator(o);
  }

  protected void visitDependencyClause(final DependencyClause o) {
    assert o != null;
    visitNode(o);

    for (final Name e0 : o.theExportedVariables) {
      visit(e0);
    }

    if (o.theOptionalImportedVariables != null) {
      for (final Name e0 : o.theOptionalImportedVariables) {
        visit(e0);
      }
    }
  }

  protected void visitDependencyRelation(final DependencyRelation o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalDependencyClauses != null) {
      for (final DependencyClause e0 : o.theOptionalDependencyClauses) {
        visit(e0);
      }
    }

    if (o.theOptionalNullDependencyVariables != null) {
      for (final Name e0 : o.theOptionalNullDependencyVariables) {
        visit(e0);
      }
    }
  }

  protected void visitDigitsAttributeDesignator(
      final DigitsAttributeDesignator o) {
    assert o != null;
    visitAttributeDesignator(o);
  }

  protected void visitEmbeddedPackageDeclaration(
      final EmbeddedPackageDeclaration o) {
    assert o != null;
    visitDeclarativePartMember(o);

    visit(o.thePackageDeclaration);

    if (o.theOptionalEmbeddedPackageDeclarationMembers != null) {
      for (final EmbeddedPackageDeclarationMember e0 : o.theOptionalEmbeddedPackageDeclarationMembers) {
        visit(e0);
      }
    }
  }

  protected void visitEmbeddedPackageDeclarationMember(
      final EmbeddedPackageDeclarationMember o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitEnumerationRepresentationClause(
      final EnumerationRepresentationClause o) {
    assert o != null;
    visitRepresentationClause(o);

    visit(o.theName);

    visit(o.theArrayAggregate);
  }

  protected void visitEnumerationTypeDefinition(
      final EnumerationTypeDefinition o) {
    assert o != null;
    visitTypeDefinition(o);

    for (final IDName e0 : o.theIDNames) {
      visit(e0);
    }
  }

  protected void visitExitStatement(final ExitStatement o) {
    assert o != null;
    visitSimpleStatement(o);

    if (o.theOptionalName != null) {
      visit(o.theOptionalName);
    }

    if (o.theOptionalExp != null) {
      visit(o.theOptionalExp);
    }
  }

  protected void visitExp(final Exp o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitExpAggregateItem(final ExpAggregateItem o) {
    assert o != null;
    visitAggregateItem(o);

    visit(o.theExp);
  }

  protected void visitExpChoice(final ExpChoice o) {
    assert o != null;
    visitChoice(o);

    visit(o.theExp);
  }

  protected void visitExpPragmaArgumentAssociation(
      final ExpPragmaArgumentAssociation o) {
    assert o != null;
    visitPragmaArgumentAssociation(o);

    visit(o.theExp);
  }

  protected void visitExpQualifiedExp(final ExpQualifiedExp o) {
    assert o != null;
    visitQualifiedExp(o);

    visit(o.theExp);
  }

  protected void visitExpRange(final ExpRange o) {
    assert o != null;
    visitRange(o);

    visit(o.theLowRangeExp);

    visit(o.theHighRangeExp);
  }

  protected void visitExtensionAggregate(final ExtensionAggregate o) {
    assert o != null;
    visitAggregate(o);

    visit(o.theExp);
  }

  protected void visitExternalSubProgramDeclaration(
      final ExternalSubProgramDeclaration o) {
    assert o != null;
    visitDeclarativePartMember(o);

    visit(o.theSubProgramDeclaration);

    visit(o.thePragma);
  }

  protected void visitFixedPointDefinition(final FixedPointDefinition o) {
    assert o != null;
    visitRealTypeDefinition(o);
  }

  protected void visitFloatingPointDefinition(final FloatingPointDefinition o) {
    assert o != null;
    visitRealTypeDefinition(o);

    visit(o.theExp);

    if (o.theOptionalLowRangeExp != null) {
      visit(o.theOptionalLowRangeExp);
    }

    if (o.theOptionalHighRangeExp != null) {
      visit(o.theOptionalHighRangeExp);
    }
  }

  protected void visitForLoopStatement(final ForLoopStatement o) {
    assert o != null;
    visitIterationSchemeLoopStatement(o);

    visit(o.theName);

    if (o.theOptionalRange != null) {
      visit(o.theOptionalRange);
    }
  }

  protected void visitFullTypeDeclaration(final FullTypeDeclaration o) {
    assert o != null;
    visitTypeDeclaration(o);

    visit(o.theTypeDefinition);
  }

  protected void visitFunctionAnnotation(final FunctionAnnotation o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalGlobalDefinition != null) {
      visit(o.theOptionalGlobalDefinition);
    }

    if (o.theOptionalPrecondition != null) {
      visit(o.theOptionalPrecondition);
    }

    if (o.theOptionalReturnAnnotation != null) {
      visit(o.theOptionalReturnAnnotation);
    }
  }

  protected void visitFunctionBodyStub(final FunctionBodyStub o) {
    assert o != null;
    visitSubProgramBodyStub(o);

    visit(o.theFunctionSpecification);

    if (o.theOptionalFunctionAnnotation != null) {
      visit(o.theOptionalFunctionAnnotation);
    }
  }

  protected void visitFunctionCall(final FunctionCall o) {
    assert o != null;
    visitName(o);

    visit(o.theName);

    if (o.theOptionalParameterAssociationList != null) {
      visit(o.theOptionalParameterAssociationList);
    }
  }

  protected void visitFunctionRenamingDeclaration(
      final FunctionRenamingDeclaration o) {
    assert o != null;
    visitSubProgramRenamingDeclaration(o);

    visit(o.theDefiningStringLiteral);

    for (final ParameterSpecification e0 : o.theParameterSpecifications) {
      visit(e0);
    }

    visit(o.theReturnName);

    visit(o.thePackageName);

    visit(o.theStringLiteral);
  }

  protected void visitFunctionSpecification(final FunctionSpecification o) {
    assert o != null;
    visitMethodSpecification(o);

    visit(o.theName);
  }

  protected void visitFunctionSpecificationRenamingDeclaration(
      final FunctionSpecificationRenamingDeclaration o) {
    assert o != null;
    visitSubProgramRenamingDeclaration(o);

    visit(o.theFunctionSpecification);

    visit(o.theName);
  }

  protected void visitFunctionSubProgramBody(final FunctionSubProgramBody o) {
    assert o != null;
    visitSubProgramBody(o);

    visit(o.theFunctionSpecification);

    if (o.theOptionalFunctionAnnotation != null) {
      visit(o.theOptionalFunctionAnnotation);
    }
  }

  protected void visitFunctionSubProgramDeclaration(
      final FunctionSubProgramDeclaration o) {
    assert o != null;
    visitSubProgramDeclaration(o);

    visit(o.theFunctionSpecification);

    visit(o.theFunctionAnnotation);
  }

  protected void visitGenericAssociation(final GenericAssociation o) {
    assert o != null;
    visitNode(o);

    visit(o.theName);
  }

  protected void visitGenericInstantiation(final GenericInstantiation o) {
    assert o != null;
    visitDeclarativeItem(o);

    visit(o.theName);

    if (o.theOptionalGenericAssociations != null) {
      for (final GenericAssociation e0 : o.theOptionalGenericAssociations) {
        visit(e0);
      }
    }
  }

  protected void visitGlobalDeclaration(final GlobalDeclaration o) {
    assert o != null;
    visitNode(o);

    for (final Name e0 : o.theNames) {
      visit(e0);
    }
  }

  protected void visitGlobalDefinition(final GlobalDefinition o) {
    assert o != null;
    visitNode(o);

    for (final GlobalDeclaration e0 : o.theGlobalDeclarations) {
      visit(e0);
    }
  }

  protected void visitIDAttributeDesignator(final IDAttributeDesignator o) {
    assert o != null;
    visitAttributeDesignator(o);

    if (o.theOptionalExps != null) {
      for (final Exp e0 : o.theOptionalExps) {
        visit(e0);
      }
    }
  }

  protected void visitIDName(final IDName o) {
    assert o != null;
    visitName(o);
  }

  protected void visitIfStatement(final IfStatement o) {
    assert o != null;
    visitCompoundStatement(o);

    visit(o.theExp);

    visit(o.theThen);

    if (o.theOptionalElseIfs != null) {
      for (final Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection> e0 : o.theOptionalElseIfs) {
        visit(e0.getE0());
        visit(e0.getE1());
      }
    }

    if (o.theOptionalElse != null) {
      visit(o.theOptionalElse);
    }
  }

  protected void visitIndexConstraint(final IndexConstraint o) {
    assert o != null;
    visitConstraint(o);

    for (final Name e0 : o.theDiscreteSubTypeNames) {
      visit(e0);
    }
  }

  protected void visitIndexedComponent(final IndexedComponent o) {
    assert o != null;
    visitName(o);

    visit(o.theName);

    for (final Exp e0 : o.theExps) {
      visit(e0);
    }
  }

  protected void visitInRangeExp(final InRangeExp o) {
    assert o != null;
    visitExp(o);

    visit(o.theExp);

    visit(o.theRange);
  }

  protected void visitIntegerTypeDefinition(final IntegerTypeDefinition o) {
    assert o != null;
    visitTypeDefinition(o);
  }

  protected void visitIterationSchemeLoopStatement(
      final IterationSchemeLoopStatement o) {
    assert o != null;
    visitLoopStatement(o);
  }

  protected void visitJustificationClause(final JustificationClause o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalNames != null) {
      for (final Name e0 : o.theOptionalNames) {
        visit(e0);
      }
    }
  }

  protected void visitJustificationStatement(final JustificationStatement o) {
    assert o != null;
    visitProofStatement(o);

    for (final JustificationClause e0 : o.theClauses) {
      visit(e0);
    }
  }

  protected void visitJustificationStatementEnd(
      final JustificationStatementEnd o) {
    assert o != null;
    visitProofStatement(o);
  }

  protected void visitLibraryCompilationUnit(final LibraryCompilationUnit o) {
    assert o != null;
    visitCompilationUnit(o);

    visit(o.theLibraryItem);
  }

  protected void visitLibraryItem(final LibraryItem o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitLibraryUnitBody(final LibraryUnitBody o) {
    assert o != null;
    visitLibraryItem(o);

    visit(o.thePackageBody);
  }

  protected void visitLibraryUnitDeclaration(final LibraryUnitDeclaration o) {
    assert o != null;
    visitLibraryItem(o);
  }

  protected void visitLiteral(final Literal o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitLiteralExp(final LiteralExp o) {
    assert o != null;
    visitExp(o);

    visit(o.theLiteral);
  }

  protected void visitLoopStatement(final LoopStatement o) {
    assert o != null;
    visitCompoundStatement(o);

    if (o.theOptionalLoopInvariant != null) {
      visit(o.theOptionalLoopInvariant);
    }

    visit(o.theStatementList);
  }

  protected void visitMainProgram(final MainProgram o) {
    assert o != null;
    visitLibraryUnitDeclaration(o);

    if (o.theOptionalInheritClauses != null) {
      for (final Name e0 : o.theOptionalInheritClauses) {
        visit(e0);
      }
    }

    visit(o.theSubProgramBody);
  }

  protected void visitMethodSpecification(final MethodSpecification o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalParameterSpecification != null) {
      for (final ParameterSpecification e0 : o.theOptionalParameterSpecification) {
        visit(e0);
      }
    }
  }

  protected void visitModularTypeDefinition(final ModularTypeDefinition o) {
    assert o != null;
    visitTypeDefinition(o);

    visit(o.theExp);
  }

  protected void visitName(final Name o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitNamedArrayAggregate(final NamedArrayAggregate o) {
    assert o != null;
    visitArrayAggregate(o);

    if (o.theOptionalArrayComponentAssociations != null) {
      for (final ArrayComponentAssociation e0 : o.theOptionalArrayComponentAssociations) {
        visit(e0);
      }
    }
  }

  protected void visitNamedParameterAssociation(
      final NamedParameterAssociation o) {
    assert o != null;
    visitNode(o);

    visit(o.theExp);
  }

  protected void visitNamedParameterAssociationList(
      final NamedParameterAssociationList o) {
    assert o != null;
    visitParameterAssociationList(o);

    for (final NamedParameterAssociation e0 : o.theParameterAssociations) {
      visit(e0);
    }
  }

  protected void visitNamedRecordAggregate(final NamedRecordAggregate o) {
    assert o != null;
    visitRecordAggregate(o);

    for (final RecordComponentAssociation e0 : o.theRecordComponentAssociations) {
      visit(e0);
    }
  }

  protected void visitNamedRecordExtensionAggregate(
      final NamedRecordExtensionAggregate o) {
    assert o != null;
    visitRecordExtensionAggregate(o);

    for (final RecordComponentAssociation e0 : o.theRecordComponentAssociations) {
      visit(e0);
    }
  }

  protected void visitNameExp(final NameExp o) {
    assert o != null;
    visitExp(o);

    visit(o.theName);
  }

  protected void visitNamePragmaArgumentAssociation(
      final NamePragmaArgumentAssociation o) {
    assert o != null;
    visitPragmaArgumentAssociation(o);

    visit(o.theName);
  }

  protected void visitNameRangeExp(final NameRangeExp o) {
    assert o != null;
    visitExp(o);

    visit(o.theExp);

    visit(o.theName);
  }

  protected void visitNode(final Node o) {
    assert o != null;
  }

  protected void visitNullRecordExtensionAggregate(
      final NullRecordExtensionAggregate o) {
    assert o != null;
    visitExtensionAggregate(o);
  }

  protected void visitNullStatement(final NullStatement o) {
    assert o != null;
    visitSimpleStatement(o);
  }

  protected void visitNumberDeclaration(final NumberDeclaration o) {
    assert o != null;
    visitBasicDeclaration(o);

    for (final IDName e0 : o.theIDNames) {
      visit(e0);
    }

    visit(o.theExp);
  }

  protected void visitNumericLiteral(final NumericLiteral o) {
    assert o != null;
    visitLiteral(o);
  }

  @SuppressWarnings("rawtypes")
  protected void visitObject(final Object o) {
    if (o instanceof Node) {
      visit((Node) o);
    } else if (o instanceof List) {
      visit((List) o);
    } else if (o instanceof Set) {
      visit((Set) o);
    } else if (o instanceof Map) {
      visit((Map) o);
    } else if (o instanceof Tuple) {
      visit((Tuple) o);
    } else {
      assert o == null;
    }
  }

  protected void visitObjectDeclaration(final ObjectDeclaration o) {
    assert o != null;
    visitBasicDeclaration(o);

    for (final IDName e0 : o.theDefiningIdentifierList) {
      visit(e0);
    }

    visit(o.theSubtypeMark);

    if (o.theOptionalInitializingExp != null) {
      visit(o.theOptionalInitializingExp);
    }
  }

  protected void visitOrdinaryFixedPointDefinition(
      final OrdinaryFixedPointDefinition o) {
    assert o != null;
    visitFixedPointDefinition(o);

    visit(o.theExp);

    visit(o.theLowRangeExp);

    visit(o.theHighRangeExp);
  }

  protected void visitOwnVariable(final OwnVariable o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitOwnVariableSpecification(final OwnVariableSpecification o) {
    assert o != null;
    visitNode(o);

    for (final OwnVariable e0 : o.theOwnVariables) {
      visit(e0);
    }

    if (o.theOptionalSubtypeMark != null) {
      visit(o.theOptionalSubtypeMark);
    }
  }

  protected void visitPackageAnnotation(final PackageAnnotation o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalOwnVariables != null) {
      for (final OwnVariableSpecification e0 : o.theOptionalOwnVariables) {
        visit(e0);
      }
    }

    if (o.theOptionalInitializeVariables != null) {
      for (final OwnVariable e0 : o.theOptionalInitializeVariables) {
        visit(e0);
      }
    }
  }

  protected void visitPackageBody(final PackageBody o) {
    assert o != null;
    visitProperBody(o);

    visit(o.theName);

    for (final RefinementClause e0 : o.theRefinementClauses) {
      visit(e0);
    }

    visit(o.thePackageImplementation);
  }

  protected void visitPackageBodyStub(final PackageBodyStub o) {
    assert o != null;
    visitBodyStub(o);
  }

  protected void visitPackageDeclaration(final PackageDeclaration o) {
    assert o != null;
    visitLibraryUnitDeclaration(o);

    if (o.theOptionalInheritClauses != null) {
      for (final Name e0 : o.theOptionalInheritClauses) {
        visit(e0);
      }
    }

    visit(o.thePackageSpecification);
  }

  protected void visitPackageImplementation(final PackageImplementation o) {
    assert o != null;
    visitNode(o);

    visit(o.theDeclarativePart);

    if (o.theOptionalStatementList != null) {
      visit(o.theOptionalStatementList);
    }
  }

  protected void visitPackageRenamingDeclaration(
      final PackageRenamingDeclaration o) {
    assert o != null;
    visitRenamingDeclaration(o);

    visit(o.thePackageName);

    visit(o.theRenamedName);
  }

  protected void visitPackageSpecification(final PackageSpecification o) {
    assert o != null;
    visitNode(o);

    visit(o.theName);

    visit(o.thePackageAnnotation);

    if (o.theOptionalVisiblePartDeclaration != null) {
      for (final RenamingDeclaration e0 : o.theOptionalVisiblePartDeclaration) {
        visit(e0);
      }
    }

    if (o.theOptionalVisiblePartDeclarativePartMember != null) {
      for (final DeclarativePartMember e0 : o.theOptionalVisiblePartDeclarativePartMember) {
        visit(e0);
      }
    }

    if (o.theOptionalPrivatePartDeclaration != null) {
      for (final RenamingDeclaration e0 : o.theOptionalPrivatePartDeclaration) {
        visit(e0);
      }
    }

    if (o.theOptionalPrivatePartDeclarativePartMember != null) {
      for (final DeclarativePartMember e0 : o.theOptionalPrivatePartDeclarativePartMember) {
        visit(e0);
      }
    }
  }

  protected void visitParameterAssociationList(final ParameterAssociationList o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitParameterSpecification(final ParameterSpecification o) {
    assert o != null;
    visitNode(o);

    for (final IDName e0 : o.theParameterNames) {
      visit(e0);
    }

    visit(o.theSubtypeMark);
  }

  protected void visitParenExp(final ParenExp o) {
    assert o != null;
    visitExp(o);

    visit(o.theExp);
  }

  protected void visitPositionalArrayAggregate(final PositionalArrayAggregate o) {
    assert o != null;
    visitArrayAggregate(o);

    for (final AggregateItem e0 : o.theAggregateItems) {
      visit(e0);
    }
  }

  protected void visitPositionalParameterAssociationList(
      final PositionalParameterAssociationList o) {
    assert o != null;
    visitParameterAssociationList(o);

    for (final Exp e0 : o.theParameterAssociations) {
      visit(e0);
    }
  }

  protected void visitPositionalRecordAggregate(
      final PositionalRecordAggregate o) {
    assert o != null;
    visitRecordAggregate(o);

    for (final Exp e0 : o.theExps) {
      visit(e0);
    }
  }

  protected void visitPositionalRecordExtensionAggregate(
      final PositionalRecordExtensionAggregate o) {
    assert o != null;
    visitRecordExtensionAggregate(o);

    for (final Exp e0 : o.theExps) {
      visit(e0);
    }
  }

  protected void visitPostcondition(final Postcondition o) {
    assert o != null;
    visitNode(o);

    visit(o.thePredicate);
  }

  protected void visitPragma(final Pragma o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalPragmaArgumentAssociations != null) {
      for (final PragmaArgumentAssociation e0 : o.theOptionalPragmaArgumentAssociations) {
        visit(e0);
      }
    }

    if (o.dummyObjectToGetListVisitor != null) {
      visitObject(o.dummyObjectToGetListVisitor);
    }
  }

  protected void visitPragmaArgumentAssociation(
      final PragmaArgumentAssociation o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitPrecondition(final Precondition o) {
    assert o != null;
    visitNode(o);

    visit(o.thePredicate);
  }

  protected void visitPredicate(final Predicate o) {
    assert o != null;
    visitNode(o);

    visit(o.theExp);
  }

  protected void visitPrivateExtensionDeclaration(
      final PrivateExtensionDeclaration o) {
    assert o != null;
    visitTypeDeclaration(o);

    visit(o.theSubTypeIndication);
  }

  protected void visitPrivateTypeDeclaration(final PrivateTypeDeclaration o) {
    assert o != null;
    visitTypeDeclaration(o);
  }

  protected void visitProcedureAnnotation(final ProcedureAnnotation o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalGlobalDefinition != null) {
      visit(o.theOptionalGlobalDefinition);
    }

    if (o.theOptionalDependency != null) {
      visit(o.theOptionalDependency);
    }

    if (o.theOptionalPrecondition != null) {
      visit(o.theOptionalPrecondition);
    }

    if (o.theOptionalPostcondition != null) {
      visit(o.theOptionalPostcondition);
    }
  }

  protected void visitProcedureBodyStub(final ProcedureBodyStub o) {
    assert o != null;
    visitSubProgramBodyStub(o);

    visit(o.theProcedureSpecification);

    if (o.theOptionalProcedureAnnotation != null) {
      visit(o.theOptionalProcedureAnnotation);
    }
  }

  protected void visitProcedureCallStatement(final ProcedureCallStatement o) {
    assert o != null;
    visitSimpleStatement(o);

    visit(o.theName);

    if (o.theOptionalParameterAssociationList != null) {
      visit(o.theOptionalParameterAssociationList);
    }
  }

  protected void visitProcedureSpecification(final ProcedureSpecification o) {
    assert o != null;
    visitMethodSpecification(o);
  }

  protected void visitProcedureSpecificationRenamingDeclaration(
      final ProcedureSpecificationRenamingDeclaration o) {
    assert o != null;
    visitSubProgramRenamingDeclaration(o);

    visit(o.theProcedureSpecification);

    visit(o.theName);
  }

  protected void visitProcedureSubProgramBody(final ProcedureSubProgramBody o) {
    assert o != null;
    visitSubProgramBody(o);

    visit(o.theProcedureSpecification);

    if (o.theOptionalProcedureAnnotation != null) {
      visit(o.theOptionalProcedureAnnotation);
    }
  }

  protected void visitProcedureSubProgramDeclaration(
      final ProcedureSubProgramDeclaration o) {
    assert o != null;
    visitSubProgramDeclaration(o);

    visit(o.theProcedureSpecification);

    visit(o.theProcedureAnnotation);
  }

  protected void visitProofFunctionDeclaration(final ProofFunctionDeclaration o) {
    assert o != null;
    visitBasicDeclarativeItem(o);

    visit(o.theFunctionSpecification);
  }

  protected void visitProofStatement(final ProofStatement o) {
    assert o != null;
    visitStatement(o);
  }

  protected void visitProofTypeDeclaration(final ProofTypeDeclaration o) {
    assert o != null;
    visitBasicProofDeclaration(o);
  }

  protected void visitProperBody(final ProperBody o) {
    assert o != null;
    visitBody(o);
  }

  protected void visitQualifiedExp(final QualifiedExp o) {
    assert o != null;
    visitExp(o);

    visit(o.theName);
  }

  protected void visitQuantifiedExp(final QuantifiedExp o) {
    assert o != null;
    visitExp(o);

    if (o.theSubTypeMark != null) {
      visit(o.theSubTypeMark);
    }

    if (o.theOptionalRange != null) {
      visit(o.theOptionalRange);
    }

    visit(o.thePredicate);
  }

  protected void visitRange(final Range o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitRangeAttributeDesignator(final RangeAttributeDesignator o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalExp != null) {
      visit(o.theOptionalExp);
    }
  }

  protected void visitRangeAttributeReference(final RangeAttributeReference o) {
    assert o != null;
    visitRange(o);

    visit(o.theName);

    if (o.theDesignator != null) {
      visit(o.theDesignator);
    }
  }

  protected void visitRangeChoice(final RangeChoice o) {
    assert o != null;
    visitChoice(o);

    visit(o.theRange);
  }

  protected void visitRangeConstraint(final RangeConstraint o) {
    assert o != null;
    visitConstraint(o);

    visit(o.theRange);
  }

  protected void visitRealTypeDefinition(final RealTypeDefinition o) {
    assert o != null;
    visitTypeDefinition(o);
  }

  protected void visitRecordAggregate(final RecordAggregate o) {
    assert o != null;
    visitAggregate(o);
  }

  protected void visitRecordComponentAssociation(
      final RecordComponentAssociation o) {
    assert o != null;
    visitNode(o);

    visit(o.theExp);
  }

  protected void visitRecordDefinition(final RecordDefinition o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalComponentDeclarations != null) {
      for (final ComponentDeclaration e0 : o.theOptionalComponentDeclarations) {
        visit(e0);
      }
    }
  }

  protected void visitRecordExtensionAggregate(final RecordExtensionAggregate o) {
    assert o != null;
    visitExtensionAggregate(o);
  }

  protected void visitRecordRepresentationClause(
      final RecordRepresentationClause o) {
    assert o != null;
    visitRepresentationClause(o);

    visit(o.theName);

    if (o.theOptionalExp != null) {
      visit(o.theOptionalExp);
    }

    if (o.theOptionalComponentClauses != null) {
      for (final ComponentClause e0 : o.theOptionalComponentClauses) {
        visit(e0);
      }
    }
  }

  protected void visitRecordTypeDefinition(final RecordTypeDefinition o) {
    assert o != null;
    visitTypeDefinition(o);

    visit(o.theRecordDefinition);
  }

  protected void visitRecordTypeExtension(final RecordTypeExtension o) {
    assert o != null;
    visitTypeDefinition(o);

    visit(o.theSubtypeMark);

    visit(o.theRecordDefinition);
  }

  protected void visitRecordUpdate(final RecordUpdate o) {
    assert o != null;
    visitName(o);

    visit(o.theName);

    for (final Pair<String, Exp> e0 : o.theSelectorToExpressionList) {
      visit(e0.getE1());
    }
  }

  protected void visitRefinementClause(final RefinementClause o) {
    assert o != null;
    visitNode(o);

    visit(o.theSubject);

    for (final Constituent e0 : o.theConstituents) {
      visit(e0);
    }
  }

  protected void visitRenamingDeclaration(final RenamingDeclaration o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitRenamingDeclarationEmbeddedPackageDeclarationMember(
      final RenamingDeclarationEmbeddedPackageDeclarationMember o) {
    assert o != null;
    visitEmbeddedPackageDeclarationMember(o);

    visit(o.theRenamingDeclaration);
  }

  protected void visitRepresentationClause(final RepresentationClause o) {
    assert o != null;
    visitBasicDeclarativeItem(o);
  }

  protected void visitRepresentationClauseBasicDeclarativeItem(
      final RepresentationClauseBasicDeclarativeItem o) {
    assert o != null;
    visitBasicDeclarativeItem(o);

    visit(o.theRepresentationClause);
  }

  protected void visitReturnAnnotation(final ReturnAnnotation o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitReturnAnnotationExp(final ReturnAnnotationExp o) {
    assert o != null;
    visitReturnAnnotation(o);

    visit(o.theExp);
  }

  protected void visitReturnAnnotationPred(final ReturnAnnotationPred o) {
    assert o != null;
    visitReturnAnnotation(o);

    visit(o.thePredicate);
  }

  protected void visitReturnStatement(final ReturnStatement o) {
    assert o != null;
    visitSimpleStatement(o);

    visit(o.theExp);
  }

  protected void visitSelectedComponent(final SelectedComponent o) {
    assert o != null;
    visitName(o);

    visit(o.theName);
  }

  protected void visitSignedIntegerTypeDefinition(
      final SignedIntegerTypeDefinition o) {
    assert o != null;
    visitIntegerTypeDefinition(o);

    visit(o.theLowRangeExp);

    visit(o.theHighRangeExp);
  }

  protected void visitSimpleStatement(final SimpleStatement o) {
    assert o != null;
    visitStatement(o);
  }

  protected void visitStatement(final Statement o) {
    assert o != null;
    visitNode(o);

    if (o.theOptionalLabelList != null) {
      for (final String e0 : o.theOptionalLabelList) {
      }
    }
  }

  protected void visitStatementList(final StatementList o) {
    assert o != null;
    visitNode(o);

    for (final Statement e0 : o.theStatements) {
      visit(e0);
    }
  }

  protected void visitStatementSubProgramImplementation(
      final StatementSubProgramImplementation o) {
    assert o != null;
    visitSubProgramImplementation(o);

    visit(o.theDeclarativePart);

    visit(o.theStatementList);
  }

  protected void visitStringLiteral(final StringLiteral o) {
    assert o != null;
    visitLiteral(o);
  }

  protected void visitSubProgramBody(final SubProgramBody o) {
    assert o != null;
    visitProperBody(o);

    visit(o.theSubProgramImplementation);
  }

  protected void visitSubProgramBodyStub(final SubProgramBodyStub o) {
    assert o != null;
    visitBodyStub(o);
  }

  protected void visitSubProgramDeclaration(final SubProgramDeclaration o) {
    assert o != null;
    visitDeclarativePartMember(o);
  }

  protected void visitSubProgramImplementation(final SubProgramImplementation o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitSubProgramRenamingDeclaration(
      final SubProgramRenamingDeclaration o) {
    assert o != null;
    visitRenamingDeclaration(o);
  }

  protected void visitSubTypeChoice(final SubTypeChoice o) {
    assert o != null;
    visitChoice(o);

    visit(o.theSubTypeIndication);
  }

  protected void visitSubTypeDeclaration(final SubTypeDeclaration o) {
    assert o != null;
    visitBasicDeclaration(o);

    visit(o.theSubTypeIndication);
  }

  protected void visitSubTypeIndication(final SubTypeIndication o) {
    assert o != null;
    visitNode(o);

    visit(o.theName);

    if (o.theOptionalConstraint != null) {
      visit(o.theOptionalConstraint);
    }
  }

  protected void visitSubUnitCompilationUnit(final SubUnitCompilationUnit o) {
    assert o != null;
    visitCompilationUnit(o);

    visit(o.theName);

    visit(o.theProperBody);
  }

  protected void visitTypeAssertion(final TypeAssertion o) {
    assert o != null;
    visitBasicProofDeclaration(o);

    visit(o.theBase);

    visit(o.theSubtypeMark);
  }

  protected void visitTypeConversion(final TypeConversion o) {
    assert o != null;
    visitExp(o);

    visit(o.theName);

    visit(o.theExp);
  }

  protected void visitTypeDeclaration(final TypeDeclaration o) {
    assert o != null;
    visitBasicDeclaration(o);
  }

  protected void visitTypeDefinition(final TypeDefinition o) {
    assert o != null;
    visitNode(o);
  }

  protected void visitUnaryExp(final UnaryExp o) {
    assert o != null;
    visitExp(o);

    visit(o.theExp);
  }

  protected void visitUnconstrainedArrayDefinition(
      final UnconstrainedArrayDefinition o) {
    assert o != null;
    visitArrayTypeDefinition(o);

    for (final Name e0 : o.theIndexSubTypeNames) {
      visit(e0);
    }

    visit(o.theComponentName);
  }

  protected void visitUseTypeClause(final UseTypeClause o) {
    assert o != null;
    visitContextItem(o);

    for (final Name e0 : o.theNames) {
      visit(e0);
    }
  }

  protected void visitUseTypeClauseEmbeddedPackageDeclarationMember(
      final UseTypeClauseEmbeddedPackageDeclarationMember o) {
    assert o != null;
    visitEmbeddedPackageDeclarationMember(o);

    visit(o.theUseTypeClause);
  }

  protected void visitWhileLoopStatement(final WhileLoopStatement o) {
    assert o != null;
    visitIterationSchemeLoopStatement(o);

    visit(o.theExp);
  }

  protected void visitWithClause(final WithClause o) {
    assert o != null;
    visitContextItem(o);

    for (final Name e0 : o.theNames) {
      visit(e0);
    }
  }
}
