/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.symboltable.model;

import java.util.Map;

public class SymbolVisitor<G> {
  protected G g;

  public SymbolVisitor(final G g) {
    assert g != null;
    this.g = g;
  }

  protected void visit(final Object o) {
    assert false;
  }

  public void visit(final Symbol o) {
    assert o != null;
    switch (o.getDescriptor()) {
      case SymVariable.DESCRIPTOR:
        visitSymVariable((SymVariable) o);
        break;

      case SymConstant.DESCRIPTOR:
        visitSymConstant((SymConstant) o);
        break;

      case SymSimpleAnnotation.DESCRIPTOR:
        visitSymSimpleAnnotation((SymSimpleAnnotation) o);
        break;

      case SymDerivesAnnotation.DESCRIPTOR:
        visitSymDerivesAnnotation((SymDerivesAnnotation) o);
        break;

      case SymNullDerivesAnnotation.DESCRIPTOR:
        visitSymNullDerivesAnnotation((SymNullDerivesAnnotation) o);
        break;

      case SymOwnVariableAnnotation.DESCRIPTOR:
        visitSymOwnVariableAnnotation((SymOwnVariableAnnotation) o);
        break;

      case SymRefinementAnnotation.DESCRIPTOR:
        visitSymRefinementAnnotation((SymRefinementAnnotation) o);
        break;

      case SymModeAnnotation.DESCRIPTOR:
        visitSymModeAnnotation((SymModeAnnotation) o);
        break;

      case SymGlobalAnnotation.DESCRIPTOR:
        visitSymGlobalAnnotation((SymGlobalAnnotation) o);
        break;

      case SymPackage.DESCRIPTOR:
        visitSymPackage((SymPackage) o);
        break;

      case SymProcedure.DESCRIPTOR:
        visitSymProcedure((SymProcedure) o);
        break;

      case SymAdaFunction.DESCRIPTOR:
        visitSymAdaFunction((SymAdaFunction) o);
        break;

      case SymProofFunction.DESCRIPTOR:
        visitSymProofFunction((SymProofFunction) o);
        break;

      case SymParameter.DESCRIPTOR:
        visitSymParameter((SymParameter) o);
        break;

      case SymAbstractDef.DESCRIPTOR:
        visitSymAbstractDef((SymAbstractDef) o);
        break;

      case SymPartialDef.DESCRIPTOR:
        visitSymPartialDef((SymPartialDef) o);
        break;

      case SymRecordDef.DESCRIPTOR:
        visitSymRecordDef((SymRecordDef) o);
        break;

      case SymRecordComponentDef.DESCRIPTOR:
        visitSymRecordComponentDef((SymRecordComponentDef) o);
        break;

      case SymArrayDef.DESCRIPTOR:
        visitSymArrayDef((SymArrayDef) o);
        break;

      case SymIndexSubTypeDef.DESCRIPTOR:
        visitSymIndexSubTypeDef((SymIndexSubTypeDef) o);
        break;

      case SymEnumerationDef.DESCRIPTOR:
        visitSymEnumerationDef((SymEnumerationDef) o);
        break;

      case SymEnumElementDef.DESCRIPTOR:
        visitSymEnumElementDef((SymEnumElementDef) o);
        break;

      case SymSignedIntegerDef.DESCRIPTOR:
        visitSymSignedIntegerDef((SymSignedIntegerDef) o);
        break;

      case SymModDef.DESCRIPTOR:
        visitSymModDef((SymModDef) o);
        break;

      case SymFixedDef.DESCRIPTOR:
        visitSymFixedDef((SymFixedDef) o);
        break;

      case SymFloatingDef.DESCRIPTOR:
        visitSymFloatingDef((SymFloatingDef) o);
        break;

      case SymRangeSubTypeDef.DESCRIPTOR:
        visitSymRangeSubTypeDef((SymRangeSubTypeDef) o);
        break;

      default:
        assert false;
        break;
    }
  }

  protected void visitSymAbstractDef(final SymAbstractDef o) {
    assert o != null;
    visitSymDef(o);
  }

  protected void visitSymAdaFunction(final SymAdaFunction o) {
    assert o != null;
    visitSymFunction(o);

    if (o.theOptionalImplicitSpecProofParameters != null) {
      visit(o.theOptionalImplicitSpecProofParameters);
    }

    if (o.theOptionalImplicitBodyProofParameters != null) {
      visit(o.theOptionalImplicitBodyProofParameters);
    }
  }

  protected void visitSymAnnotation(final SymAnnotation o) {
    assert o != null;
    visitSymbol(o);

    visit(o.theObject);
  }

  protected void visitSymArrayDef(final SymArrayDef o) {
    assert o != null;
    visitSymCompositeDef(o);

    if (o.theIndexSubtypeDefs != null) {
      for (final SymDef e0 : o.theIndexSubtypeDefs) {
        visit(e0);
      }
    }

    if (o.theComponentSubtypeDef != null) {
      visit(o.theComponentSubtypeDef);
    }
  }

  protected void visitSymbol(final Symbol o) {
    assert o != null;
    if (o.theSelection != null) {
      visit(o.theSelection);
    }

    if (o.theOptionalParent != null) {
      visit(o.theOptionalParent);
    }
  }

  protected void visitSymCall(final SymCall o) {
    assert o != null;
    visitSymPackageElement(o);

    if (o.theOptionalDeclaredSpecGlobals != null) {
      visit(o.theOptionalDeclaredSpecGlobals);
    }

    if (o.theOptionalDeclaredSpecGlobalsSelection != null) {
      visit(o.theOptionalDeclaredSpecGlobalsSelection);
    }

    if (o.theOptionalDeclaredBodyGlobals != null) {
      visit(o.theOptionalDeclaredBodyGlobals);
    }

    if (o.theOptionalDeclaredBodyGlobalsSelection != null) {
      visit(o.theOptionalDeclaredBodyGlobalsSelection);
    }

    if (o.theOptionalInferredBodyGlobals != null) {
      visit(o.theOptionalInferredBodyGlobals);
    }

    if (o.theInGlobalsSpec != null) {
      for (final SymGlobalAnnotation e0 : o.theInGlobalsSpec) {
        visit(e0);
      }
    }

    if (o.theInGlobalsBody != null) {
      for (final SymGlobalAnnotation e0 : o.theInGlobalsBody) {
        visit(e0);
      }
    }

    if (o.theParameters != null) {
      visit(o.theParameters);
    }
  }

  protected void visitSymCompositeDef(final SymCompositeDef o) {
    assert o != null;
    visitSymDef(o);
  }

  protected void visitSymConstant(final SymConstant o) {
    assert o != null;
    visitSymObject(o);

    if (o.theExp != null) {
      visit(o.theExp);
    }
  }

  protected void visitSymDec(final SymDec o) {
    assert o != null;
    visitSymbol(o);
  }

  protected void visitSymDef(final SymDef o) {
    assert o != null;
    visitSymbol(o);

    if (o.theOptionalPrivateTypeDeclarationSelection != null) {
      visit(o.theOptionalPrivateTypeDeclarationSelection);
    }
  }

  protected void visitSymDerivesAnnotation(final SymDerivesAnnotation o) {
    assert o != null;
    visitSymDerivesAnnotationModel(o);

    if (o.theOutVar != null) {
      visit(o.theOutVar);
    }

    if (o.theInVars != null) {
      for (final SymSimpleAnnotation e0 : o.theInVars) {
        visit(e0);
      }
    }
  }

  protected void visitSymDerivesAnnotationModel(
      final SymDerivesAnnotationModel o) {
    assert o != null;
    visitSymAnnotation(o);
  }

  protected void visitSymDiscreteDef(final SymDiscreteDef o) {
    assert o != null;
    visitSymScalarDef(o);
  }

  protected void visitSymEnumElementDef(final SymEnumElementDef o) {
    assert o != null;
    visitSymDef(o);

    visit(o.theParentEnumDef);
  }

  protected void visitSymEnumerationDef(final SymEnumerationDef o) {
    assert o != null;
    visitSymDiscreteDef(o);

    if (o.theElements != null) {
      for (final SymEnumElementDef e0 : o.theElements) {
        visit(e0);
      }
    }
  }

  protected void visitSymFixedDef(final SymFixedDef o) {
    assert o != null;
    visitSymRealDef(o);

    if (o.theExp != null) {
      visit(o.theExp);
    }

    if (o.theHighRangeExp != null) {
      visit(o.theHighRangeExp);
    }

    if (o.theLowRangeExp != null) {
      visit(o.theLowRangeExp);
    }
  }

  protected void visitSymFloatingDef(final SymFloatingDef o) {
    assert o != null;
    visitSymRealDef(o);

    if (o.theExp != null) {
      visit(o.theExp);
    }

    if (o.theOptionalLowRangeExp != null) {
      visit(o.theOptionalLowRangeExp);
    }

    if (o.theOptionalHighRangeExp != null) {
      visit(o.theOptionalHighRangeExp);
    }
  }

  protected void visitSymFunction(final SymFunction o) {
    assert o != null;
    visitSymCall(o);

    visit(o.theReturnType);
  }

  protected void visitSymGlobalAnnotation(final SymGlobalAnnotation o) {
    assert o != null;
    visitSymAnnotation(o);

    if (o.theMode != null) {
      visit(o.theMode);
    }
  }

  protected void visitSymIndexSubTypeDef(final SymIndexSubTypeDef o) {
    assert o != null;
    visitSymCompositeDef(o);

    if (o.theParentTypeDef != null) {
      visit(o.theParentTypeDef);
    }

    if (o.theSubTypeDefs != null) {
      for (final SymDef e0 : o.theSubTypeDefs) {
        visit(e0);
      }
    }
  }

  protected void visitSymIntegerDef(final SymIntegerDef o) {
    assert o != null;
    visitSymScalarDef(o);
  }

  protected void visitSymModDef(final SymModDef o) {
    assert o != null;
    visitSymIntegerDef(o);

    if (o.theExp != null) {
      visit(o.theExp);
    }
  }

  protected void visitSymModeAnnotation(final SymModeAnnotation o) {
    assert o != null;
    visitSymAnnotation(o);

    if (o.theMode != null) {
      visit(o.theMode);
    }
  }

  protected void visitSymNullDerivesAnnotation(final SymNullDerivesAnnotation o) {
    assert o != null;
    visitSymDerivesAnnotationModel(o);

    for (final SymSimpleAnnotation e0 : o.theInVars) {
      visit(e0);
    }
  }

  protected void visitSymObject(final SymObject o) {
    assert o != null;
    visitSymDec(o);

    if (o.theOptionalTypeSymDef != null) {
      visit(o.theOptionalTypeSymDef);
    }

    visit(o.theKind);
  }

  protected void visitSymOwnVariableAnnotation(final SymOwnVariableAnnotation o) {
    assert o != null;
    visitSymAnnotation(o);

    if (o.theMode != null) {
      visit(o.theMode);
    }

    if (o.theFakeGlobal != null) {
      visit(o.theFakeGlobal);
    }

    if (o.theOptionalRefinements != null) {
      visit(o.theOptionalRefinements);
    }

    if (o.theOptionalType != null) {
      visit(o.theOptionalType);
    }
  }

  protected void visitSymPackage(final SymPackage o) {
    assert o != null;
    visitSymPackageElement(o);

    if (o.thePackageSpecSelection != null) {
      visit(o.thePackageSpecSelection);
    }

    if (o.thePackageBodySelection != null) {
      visit(o.thePackageBodySelection);
    }

    if (o.theSpecWithPackages != null) {
      visit(o.theSpecWithPackages);
    }

    if (o.theBodyWithPackages != null) {
      visit(o.theBodyWithPackages);
    }

    if (o.theSpecUseTypeClauses != null) {
      visit(o.theSpecUseTypeClauses);
    }

    if (o.theBodyUseTypeClauses != null) {
      visit(o.theBodyUseTypeClauses);
    }

    if (o.theInheritedPackages != null) {
      visit(o.theInheritedPackages);
    }

    if (o.theInitializeVariables != null) {
      visit(o.theInitializeVariables);
    }

    if (o.theOwnVariables != null) {
      visit(o.theOwnVariables);
    }

    if (o.theOptionalRefinementDefinition != null) {
      visit(o.theOptionalRefinementDefinition);
    }

    if (o.theRefinements != null) {
      for (final SymRefinementAnnotation e0 : o.theRefinements) {
        visit(e0);
      }
    }

    if (o.theChildPackages != null) {
      for (final Map.Entry<String, SymPackage> e0 : o.theChildPackages
          .entrySet()) {
        final SymPackage v0 = e0.getValue();
        visit(v0);
      }
    }

    if (o.theEmbeddedPackages != null) {
      for (final Map.Entry<String, SymPackage> e0 : o.theEmbeddedPackages
          .entrySet()) {
        final SymPackage v0 = e0.getValue();
        visit(v0);
      }
    }

    if (o.theProcedures != null) {
      for (final Map.Entry<String, SymProcedure> e0 : o.theProcedures
          .entrySet()) {
        final SymProcedure v0 = e0.getValue();
        visit(v0);
      }
    }

    if (o.theFunctions != null) {
      for (final Map.Entry<String, SymFunction> e0 : o.theFunctions.entrySet()) {
        final SymFunction v0 = e0.getValue();
        visit(v0);
      }
    }
  }

  protected void visitSymPackageElement(final SymPackageElement o) {
    assert o != null;
    visitSymDec(o);

    if (o.theDefinitions != null) {
      visit(o.theDefinitions);
    }

    if (o.theObjects != null) {
      visit(o.theObjects);
    }

    if (o.theOptionalRepresentationClauses != null) {
      for (final org.sireum.bakar.compiler.model.RepresentationClause e0 : o.theOptionalRepresentationClauses) {
        visit(e0);
      }
    }
  }

  protected void visitSymParameter(final SymParameter o) {
    assert o != null;
    visitSymObject(o);

    if (o.theMode != null) {
      visit(o.theMode);
    }

    if (o.theOptionalSpecSelection != null) {
      visit(o.theOptionalSpecSelection);
    }

    if (o.theOptionalBodySelection != null) {
      visit(o.theOptionalBodySelection);
    }
  }

  protected void visitSymPartialDef(final SymPartialDef o) {
    assert o != null;
    visitSymDef(o);

    visit(o.theElaborationSymDef);
  }

  protected void visitSymProcedure(final SymProcedure o) {
    assert o != null;
    visitSymCall(o);

    if (o.theOutGlobalsSpec != null) {
      for (final SymGlobalAnnotation e0 : o.theOutGlobalsSpec) {
        visit(e0);
      }
    }

    if (o.theOptionalDependencyRelationSpec != null) {
      visit(o.theOptionalDependencyRelationSpec);
    }

    if (o.theDerivesClausesSpec != null) {
      for (final SymDerivesAnnotationModel e0 : o.theDerivesClausesSpec) {
        visit(e0);
      }
    }

    if (o.theOutGlobalsBody != null) {
      for (final SymGlobalAnnotation e0 : o.theOutGlobalsBody) {
        visit(e0);
      }
    }

    if (o.theOptionalDependencyRelationBody != null) {
      visit(o.theOptionalDependencyRelationBody);
    }

    if (o.theDerivesClausesBody != null) {
      for (final SymDerivesAnnotationModel e0 : o.theDerivesClausesBody) {
        visit(e0);
      }
    }
  }

  protected void visitSymProofFunction(final SymProofFunction o) {
    assert o != null;
    visitSymFunction(o);
  }

  protected void visitSymRangeSubTypeDef(final SymRangeSubTypeDef o) {
    assert o != null;
    visitSymScalarDef(o);

    if (o.theParentTypeDef != null) {
      visit(o.theParentTypeDef);
    }

    if (o.theHighRangeExp != null) {
      visit(o.theHighRangeExp);
    }

    if (o.theLowRangeExp != null) {
      visit(o.theLowRangeExp);
    }
  }

  protected void visitSymRealDef(final SymRealDef o) {
    assert o != null;
    visitSymScalarDef(o);
  }

  protected void visitSymRecordComponentDef(final SymRecordComponentDef o) {
    assert o != null;
    visitSymDef(o);

    visit(o.theParentRecordDef);

    visit(o.theTypeSymDef);
  }

  protected void visitSymRecordDef(final SymRecordDef o) {
    assert o != null;
    visitSymCompositeDef(o);

    if (o.theElements != null) {
      visit(o.theElements);
    }

    if (o.theOptionalParentDef != null) {
      visit(o.theOptionalParentDef);
    }
  }

  protected void visitSymRefinementAnnotation(final SymRefinementAnnotation o) {
    assert o != null;
    visitSymAnnotation(o);

    if (o.theOwn != null) {
      visit(o.theOwn);
    }

    if (o.theConstituents != null) {
      for (final SymModeAnnotation e0 : o.theConstituents) {
        visit(e0);
      }
    }
  }

  protected void visitSymScalarDef(final SymScalarDef o) {
    assert o != null;
    visitSymDef(o);
  }

  protected void visitSymSignedIntegerDef(final SymSignedIntegerDef o) {
    assert o != null;
    visitSymIntegerDef(o);

    if (o.theHighRangeExp != null) {
      visit(o.theHighRangeExp);
    }

    if (o.theLowRangeExp != null) {
      visit(o.theLowRangeExp);
    }
  }

  protected void visitSymSimpleAnnotation(final SymSimpleAnnotation o) {
    assert o != null;
    visitSymAnnotation(o);
  }

  protected void visitSymVariable(final SymVariable o) {
    assert o != null;
    visitSymObject(o);

    if (o.theExp != null) {
      visit(o.theExp);
    }
  }

}
