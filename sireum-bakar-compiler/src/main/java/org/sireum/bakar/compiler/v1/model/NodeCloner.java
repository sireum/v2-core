/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import org.sireum.bakar.util.NTuple;
import org.sireum.bakar.util.Pair;
import org.sireum.bakar.util.Triple;

public class NodeCloner {

  protected static class Visitor extends NodeVisitor<VisitorContext> {

    public Visitor(final VisitorContext g) {
      super(g);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected ArrayList clone(final ArrayList o) {
      if (o == null) {
        return null;
      }
      final int size = o.size();
      final ArrayList result = new ArrayList(size);
      for (final Object e : o) {
        result.add(clone(e));
      }
      return result;
    }

    protected Boolean clone(final Boolean o) {
      return o;
    }

    protected Byte clone(final Byte o) {
      return o;
    }

    protected Character clone(final Character o) {
      return o;
    }

    protected Double clone(final Double o) {
      return o;
    }

    protected Float clone(final Float o) {
      return o;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected HashMap clone(final HashMap o) {
      if (o == null) {
        return null;
      }
      final int size = o.size();
      final HashMap result = new HashMap(size);
      for (final Object k : o.keySet()) {
        final Object v = o.get(k);
        result.put(clone(k), clone(v));
      }
      return result;
    }

    protected Integer clone(final Integer o) {
      return o;
    }

    protected Long clone(final Long o) {
      return o;
    }

    protected Node clone(final Node o) {
      if (o == null) {
        return null;
      }
      if (this.g.canCreateNode) {
        final Node result = (Node) this.g.cloneMap.get(o);
        if (result != null) {
          return result;
        } else {
          visit(o);
          return (Node) this.g.popResult();
        }
      } else {
        final Node n = (Node) this.g.cloneMap.get(o);
        if (n == null) {
          return o;
        } else if (n == o) {
          visit(o);
          return (Node) this.g.popResult();
        } else {
          return n;
        }
      }
    }

    protected NTuple clone(final NTuple o) {
      if (o == null) {
        return null;
      }
      final NTuple result = new NTuple();
      final int size = o.size();
      final Object[] elements = new Object[size];
      for (int i = 0; i < size; i++) {
        elements[i] = clone(o.get(i));
      }
      result.setElements(elements);
      return result;
    }

    @SuppressWarnings("rawtypes")
    protected Object clone(final Object o) {
      if (o == null) {
        return null;
      }
      if (o instanceof Node) {
        return clone((Node) o);
      } else if (o instanceof TreeSet) {
        return clone((TreeSet) o);
      } else if (o instanceof ArrayList) {
        return clone((ArrayList) o);
      } else if (o instanceof HashMap) {
        return clone((HashMap) o);
      } else if (o instanceof Pair) {
        return clone((Pair) o);
      } else if (o instanceof Triple) {
        return clone((Triple) o);
      } else if (o instanceof NTuple) {
        return clone((NTuple) o);
      } else if (o instanceof String) {
        return o;
      } else if (o instanceof Byte) {
        return o;
      } else if (o instanceof Short) {
        return o;
      } else if (o instanceof Integer) {
        return o;
      } else if (o instanceof Long) {
        return o;
      } else if (o instanceof Float) {
        return o;
      } else if (o instanceof Double) {
        return o;
      } else if (o instanceof Character) {
        return o;
      } else if (o instanceof Boolean) {
        return o;
      } else {
        assert false;
        throw new Error("Unhandled case in clone");
      }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Pair clone(final Pair o) {
      if (o == null) {
        return null;
      }
      final Pair result = new Pair(clone(o.getE0()), clone(o.getE1()));
      return result;
    }

    protected Short clone(final Short o) {
      return o;
    }

    protected String clone(final String o) {
      return o;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TreeSet clone(final TreeSet o) {
      if (o == null) {
        return null;
      }
      final TreeSet result = new TreeSet();
      for (final Object e : o) {
        result.add(clone(e));
      }
      return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Triple clone(final Triple o) {
      if (o == null) {
        return null;
      }
      final Triple result = new Triple(clone(o.getE0()), clone(o.getE1()),
          clone(o.getE2()));
      return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitAggregateQualifiedExp(final AggregateQualifiedExp o) {
      assert o != null;
      final AggregateQualifiedExp result = new AggregateQualifiedExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theAggregate = (Aggregate) clone(o.theAggregate);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitArrayAggregateItem(final ArrayAggregateItem o) {
      assert o != null;
      final ArrayAggregateItem result = new ArrayAggregateItem();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theArrayAggregate = (ArrayAggregate) clone(o.theArrayAggregate);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitArrayComponentAssociation(
        final ArrayComponentAssociation o) {
      assert o != null;
      final ArrayComponentAssociation result = new ArrayComponentAssociation();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theAggregateItem = (AggregateItem) clone(o.theAggregateItem);

      {
        final ArrayList<Choice> theChoices = o.theChoices;

        final ArrayList<Choice> temp0 = new ArrayList<Choice>(theChoices.size());
        for (final Choice e0 : theChoices) {
          final Choice newE0 = (Choice) clone(e0);
          temp0.add(newE0);
        }
        result.theChoices = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitArrayUpdate(final ArrayUpdate o) {
      assert o != null;
      final ArrayUpdate result = new ArrayUpdate();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      {
        final ArrayList<Pair<ArrayList<Exp>, Exp>> theIndexListToExpressionList = o.theIndexListToExpressionList;

        final ArrayList<Pair<ArrayList<Exp>, Exp>> temp0 = new ArrayList<Pair<ArrayList<Exp>, Exp>>(
            theIndexListToExpressionList.size());
        for (final Pair<ArrayList<Exp>, Exp> e0 : theIndexListToExpressionList) {
          Pair<ArrayList<Exp>, Exp> newE0 = null;
          ArrayList<Exp> pe1_0 = null;
          final ArrayList<Exp> temp2 = new ArrayList<Exp>(e0.getE0().size());
          for (final Exp e2 : e0.getE0()) {
            final Exp newE2 = (Exp) clone(e2);
            temp2.add(newE2);
          }
          pe1_0 = temp2;

          final Exp pe1_1 = (Exp) clone(e0.getE1());
          newE0 = new Pair<ArrayList<Exp>, Exp>(pe1_0, pe1_1);

          temp0.add(newE0);
        }
        result.theIndexListToExpressionList = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitAssertStatement(final AssertStatement o) {
      assert o != null;
      final AssertStatement result = new AssertStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.thePredicate = (Predicate) clone(o.thePredicate);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitAssignmentStatement(final AssignmentStatement o) {
      assert o != null;
      final AssignmentStatement result = new AssignmentStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theExp = (Exp) clone(o.theExp);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitAtClause(final AtClause o) {
      assert o != null;
      final AtClause result = new AtClause();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitAttributeDefinitionClause(
        final AttributeDefinitionClause o) {
      assert o != null;
      final AttributeDefinitionClause result = new AttributeDefinitionClause();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitAttributeReference(final AttributeReference o) {
      assert o != null;
      final AttributeReference result = new AttributeReference();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theAttributeDesignator = (AttributeDesignator) clone(o.theAttributeDesignator);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitBasicDeclarationBasicDeclarativeItem(
        final BasicDeclarationBasicDeclarativeItem o) {
      assert o != null;
      final BasicDeclarationBasicDeclarativeItem result = new BasicDeclarationBasicDeclarativeItem();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theBasicDeclaration = (BasicDeclaration) clone(o.theBasicDeclaration);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitBinaryExp(final BinaryExp o) {
      assert o != null;
      final BinaryExp result = new BinaryExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theBinaryOp = o.theBinaryOp;

      result.theLeftExp = (Exp) clone(o.theLeftExp);

      result.theRightExp = (Exp) clone(o.theRightExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitCaseStatement(final CaseStatement o) {
      assert o != null;
      final CaseStatement result = new CaseStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      result.theOptionalOthers = (StatementList) clone(o.theOptionalOthers);

      result.theOptionalOthersSelection = o.theOptionalOthersSelection;

      result.theOptionalOthersBodySelection = o.theOptionalOthersBodySelection;

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      {
        final ArrayList<CaseStatementAlternative> theCaseStatementAlternatives = o.theCaseStatementAlternatives;

        final ArrayList<CaseStatementAlternative> temp0 = new ArrayList<CaseStatementAlternative>(
            theCaseStatementAlternatives.size());
        for (final CaseStatementAlternative e0 : theCaseStatementAlternatives) {
          final CaseStatementAlternative newE0 = (CaseStatementAlternative) clone(e0);
          temp0.add(newE0);
        }
        result.theCaseStatementAlternatives = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitCaseStatementAlternative(
        final CaseStatementAlternative o) {
      assert o != null;
      final CaseStatementAlternative result = new CaseStatementAlternative();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theStatementList = (StatementList) clone(o.theStatementList);

      result.theBodySelection = o.theBodySelection;

      result.theChoiceListSelection = o.theChoiceListSelection;

      {
        final ArrayList<Choice> theChoices = o.theChoices;

        final ArrayList<Choice> temp0 = new ArrayList<Choice>(theChoices.size());
        for (final Choice e0 : theChoices) {
          final Choice newE0 = (Choice) clone(e0);
          temp0.add(newE0);
        }
        result.theChoices = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitCharacterLiteral(final CharacterLiteral o) {
      assert o != null;
      final CharacterLiteral result = new CharacterLiteral();
      this.g.cloneMap.put(o, result);

      result.theCharacter = o.theCharacter;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitCheckStatement(final CheckStatement o) {
      assert o != null;
      final CheckStatement result = new CheckStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.thePredicate = (Predicate) clone(o.thePredicate);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitCodeSubProgramImplementation(
        final CodeSubProgramImplementation o) {
      assert o != null;
      final CodeSubProgramImplementation result = new CodeSubProgramImplementation();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<QualifiedExp> theQualifiedExps = o.theQualifiedExps;

        final ArrayList<QualifiedExp> temp0 = new ArrayList<QualifiedExp>(
            theQualifiedExps.size());
        for (final QualifiedExp e0 : theQualifiedExps) {
          final QualifiedExp newE0 = (QualifiedExp) clone(e0);
          temp0.add(newE0);
        }
        result.theQualifiedExps = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitCompilation(final Compilation o) {
      assert o != null;
      final Compilation result = new Compilation();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<CompilationUnit> theOptionalCompilationUnits = o.theOptionalCompilationUnits;

        if (theOptionalCompilationUnits != null) {
          final ArrayList<CompilationUnit> temp0 = new ArrayList<CompilationUnit>(
              theOptionalCompilationUnits.size());
          for (final CompilationUnit e0 : theOptionalCompilationUnits) {
            final CompilationUnit newE0 = (CompilationUnit) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalCompilationUnits = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitComponentClause(final ComponentClause o) {
      assert o != null;
      final ComponentClause result = new ComponentClause();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.thePositionExp = (Exp) clone(o.thePositionExp);

      result.theFirstBitExp = (Exp) clone(o.theFirstBitExp);

      result.theLastBitExp = (Exp) clone(o.theLastBitExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitComponentDeclaration(final ComponentDeclaration o) {
      assert o != null;
      final ComponentDeclaration result = new ComponentDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      {
        final ArrayList<IDName> theIDNames = o.theIDNames;

        final ArrayList<IDName> temp0 = new ArrayList<IDName>(theIDNames.size());
        for (final IDName e0 : theIDNames) {
          final IDName newE0 = (IDName) clone(e0);
          temp0.add(newE0);
        }
        result.theIDNames = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitConstituent(final Constituent o) {
      assert o != null;
      final Constituent result = new Constituent();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theMode = o.theMode;

      result.theName = (Name) clone(o.theName);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitConstrainedArrayDefinition(
        final ConstrainedArrayDefinition o) {
      assert o != null;
      final ConstrainedArrayDefinition result = new ConstrainedArrayDefinition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theComponentName = (Name) clone(o.theComponentName);

      {
        final ArrayList<Name> theDiscreteSubTypeNames = o.theDiscreteSubTypeNames;

        final ArrayList<Name> temp0 = new ArrayList<Name>(
            theDiscreteSubTypeNames.size());
        for (final Name e0 : theDiscreteSubTypeNames) {
          final Name newE0 = (Name) clone(e0);
          temp0.add(newE0);
        }
        result.theDiscreteSubTypeNames = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitContextClause(final ContextClause o) {
      assert o != null;
      final ContextClause result = new ContextClause();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<ContextItem> theOptionalContextItems = o.theOptionalContextItems;

        if (theOptionalContextItems != null) {
          final ArrayList<ContextItem> temp0 = new ArrayList<ContextItem>(
              theOptionalContextItems.size());
          for (final ContextItem e0 : theOptionalContextItems) {
            final ContextItem newE0 = (ContextItem) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalContextItems = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitDeclarativePart(final DeclarativePart o) {
      assert o != null;
      final DeclarativePart result = new DeclarativePart();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<RenamingDeclaration> theOptionalRenamingDeclarations = o.theOptionalRenamingDeclarations;

        if (theOptionalRenamingDeclarations != null) {
          final ArrayList<RenamingDeclaration> temp0 = new ArrayList<RenamingDeclaration>(
              theOptionalRenamingDeclarations.size());
          for (final RenamingDeclaration e0 : theOptionalRenamingDeclarations) {
            final RenamingDeclaration newE0 = (RenamingDeclaration) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalRenamingDeclarations = temp0;
        }
      }

      {
        final ArrayList<DeclarativePartMember> theOptionalDeclarativePartMembers = o.theOptionalDeclarativePartMembers;

        if (theOptionalDeclarativePartMembers != null) {
          final ArrayList<DeclarativePartMember> temp0 = new ArrayList<DeclarativePartMember>(
              theOptionalDeclarativePartMembers.size());
          for (final DeclarativePartMember e0 : theOptionalDeclarativePartMembers) {
            final DeclarativePartMember newE0 = (DeclarativePartMember) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalDeclarativePartMembers = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitDefaultLoopStatement(final DefaultLoopStatement o) {
      assert o != null;
      final DefaultLoopStatement result = new DefaultLoopStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalIDString = o.theOptionalIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalIDRegionSelection = o.theOptionalIDRegionSelection;

      result.theLoopKeywordRegionSelection = o.theLoopKeywordRegionSelection;

      result.theOptionalLoopInvariant = (AssertStatement) clone(o.theOptionalLoopInvariant);

      result.theStatementList = (StatementList) clone(o.theStatementList);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitDeltaAttributeDesignator(
        final DeltaAttributeDesignator o) {
      assert o != null;
      final DeltaAttributeDesignator result = new DeltaAttributeDesignator();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitDependencyClause(final DependencyClause o) {
      assert o != null;
      final DependencyClause result = new DependencyClause();
      this.g.cloneMap.put(o, result);

      result.theImportStarFlag = o.theImportStarFlag;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<Name> theExportedVariables = o.theExportedVariables;

        final ArrayList<Name> temp0 = new ArrayList<Name>(
            theExportedVariables.size());
        for (final Name e0 : theExportedVariables) {
          final Name newE0 = (Name) clone(e0);
          temp0.add(newE0);
        }
        result.theExportedVariables = temp0;

      }

      {
        final ArrayList<Name> theOptionalImportedVariables = o.theOptionalImportedVariables;

        if (theOptionalImportedVariables != null) {
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalImportedVariables.size());
          for (final Name e0 : theOptionalImportedVariables) {
            final Name newE0 = (Name) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalImportedVariables = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitDependencyRelation(final DependencyRelation o) {
      assert o != null;
      final DependencyRelation result = new DependencyRelation();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<DependencyClause> theOptionalDependencyClauses = o.theOptionalDependencyClauses;

        if (theOptionalDependencyClauses != null) {
          final ArrayList<DependencyClause> temp0 = new ArrayList<DependencyClause>(
              theOptionalDependencyClauses.size());
          for (final DependencyClause e0 : theOptionalDependencyClauses) {
            final DependencyClause newE0 = (DependencyClause) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalDependencyClauses = temp0;
        }
      }

      {
        final ArrayList<Name> theOptionalNullDependencyVariables = o.theOptionalNullDependencyVariables;

        if (theOptionalNullDependencyVariables != null) {
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalNullDependencyVariables.size());
          for (final Name e0 : theOptionalNullDependencyVariables) {
            final Name newE0 = (Name) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalNullDependencyVariables = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitDigitsAttributeDesignator(
        final DigitsAttributeDesignator o) {
      assert o != null;
      final DigitsAttributeDesignator result = new DigitsAttributeDesignator();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitEmbeddedPackageDeclaration(
        final EmbeddedPackageDeclaration o) {
      assert o != null;
      final EmbeddedPackageDeclaration result = new EmbeddedPackageDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.thePackageDeclaration = (PackageDeclaration) clone(o.thePackageDeclaration);

      {
        final ArrayList<EmbeddedPackageDeclarationMember> theOptionalEmbeddedPackageDeclarationMembers = o.theOptionalEmbeddedPackageDeclarationMembers;

        if (theOptionalEmbeddedPackageDeclarationMembers != null) {
          final ArrayList<EmbeddedPackageDeclarationMember> temp0 = new ArrayList<EmbeddedPackageDeclarationMember>(
              theOptionalEmbeddedPackageDeclarationMembers.size());
          for (final EmbeddedPackageDeclarationMember e0 : theOptionalEmbeddedPackageDeclarationMembers) {
            final EmbeddedPackageDeclarationMember newE0 = (EmbeddedPackageDeclarationMember) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalEmbeddedPackageDeclarationMembers = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitEnumerationRepresentationClause(
        final EnumerationRepresentationClause o) {
      assert o != null;
      final EnumerationRepresentationClause result = new EnumerationRepresentationClause();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theArrayAggregate = (ArrayAggregate) clone(o.theArrayAggregate);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitEnumerationTypeDefinition(
        final EnumerationTypeDefinition o) {
      assert o != null;
      final EnumerationTypeDefinition result = new EnumerationTypeDefinition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<IDName> theIDNames = o.theIDNames;

        final ArrayList<IDName> temp0 = new ArrayList<IDName>(theIDNames.size());
        for (final IDName e0 : theIDNames) {
          final IDName newE0 = (IDName) clone(e0);
          temp0.add(newE0);
        }
        result.theIDNames = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitExitStatement(final ExitStatement o) {
      assert o != null;
      final ExitStatement result = new ExitStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalName = (Name) clone(o.theOptionalName);

      result.theOptionalExp = (Exp) clone(o.theOptionalExp);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitExpAggregateItem(final ExpAggregateItem o) {
      assert o != null;
      final ExpAggregateItem result = new ExpAggregateItem();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitExpChoice(final ExpChoice o) {
      assert o != null;
      final ExpChoice result = new ExpChoice();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitExpPragmaArgumentAssociation(
        final ExpPragmaArgumentAssociation o) {
      assert o != null;
      final ExpPragmaArgumentAssociation result = new ExpPragmaArgumentAssociation();
      this.g.cloneMap.put(o, result);

      result.theOptionalIDString = o.theOptionalIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitExpQualifiedExp(final ExpQualifiedExp o) {
      assert o != null;
      final ExpQualifiedExp result = new ExpQualifiedExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitExpRange(final ExpRange o) {
      assert o != null;
      final ExpRange result = new ExpRange();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theLowRangeExp = (Exp) clone(o.theLowRangeExp);

      result.theHighRangeExp = (Exp) clone(o.theHighRangeExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitExternalSubProgramDeclaration(
        final ExternalSubProgramDeclaration o) {
      assert o != null;
      final ExternalSubProgramDeclaration result = new ExternalSubProgramDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubProgramDeclaration = (SubProgramDeclaration) clone(o.theSubProgramDeclaration);

      result.thePragma = (Pragma) clone(o.thePragma);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFloatingPointDefinition(final FloatingPointDefinition o) {
      assert o != null;
      final FloatingPointDefinition result = new FloatingPointDefinition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      result.theOptionalLowRangeExp = (Exp) clone(o.theOptionalLowRangeExp);

      result.theOptionalHighRangeExp = (Exp) clone(o.theOptionalHighRangeExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitForLoopStatement(final ForLoopStatement o) {
      assert o != null;
      final ForLoopStatement result = new ForLoopStatement();
      this.g.cloneMap.put(o, result);

      result.theReverseFlag = o.theReverseFlag;

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalIDString = o.theOptionalIDString;

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalIDRegionSelection = o.theOptionalIDRegionSelection;

      result.theLoopKeywordRegionSelection = o.theLoopKeywordRegionSelection;

      result.theOptionalLoopInvariant = (AssertStatement) clone(o.theOptionalLoopInvariant);

      result.theStatementList = (StatementList) clone(o.theStatementList);

      result.theIterationSchemeRegionSelection = o.theIterationSchemeRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theOptionalRange = (Range) clone(o.theOptionalRange);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFullTypeDeclaration(final FullTypeDeclaration o) {
      assert o != null;
      final FullTypeDeclaration result = new FullTypeDeclaration();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theTypeDefinition = (TypeDefinition) clone(o.theTypeDefinition);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFunctionAnnotation(final FunctionAnnotation o) {
      assert o != null;
      final FunctionAnnotation result = new FunctionAnnotation();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalGlobalDefinition = (GlobalDefinition) clone(o.theOptionalGlobalDefinition);

      result.theOptionalPrecondition = (Precondition) clone(o.theOptionalPrecondition);

      result.theOptionalReturnAnnotation = (ReturnAnnotation) clone(o.theOptionalReturnAnnotation);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFunctionBodyStub(final FunctionBodyStub o) {
      assert o != null;
      final FunctionBodyStub result = new FunctionBodyStub();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theFunctionSpecification = (FunctionSpecification) clone(o.theFunctionSpecification);

      result.theOptionalFunctionAnnotation = (FunctionAnnotation) clone(o.theOptionalFunctionAnnotation);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFunctionCall(final FunctionCall o) {
      assert o != null;
      final FunctionCall result = new FunctionCall();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theOptionalParameterAssociationList = (ParameterAssociationList) clone(o.theOptionalParameterAssociationList);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFunctionRenamingDeclaration(
        final FunctionRenamingDeclaration o) {
      assert o != null;
      final FunctionRenamingDeclaration result = new FunctionRenamingDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theDefiningStringLiteral = (StringLiteral) clone(o.theDefiningStringLiteral);

      result.theReturnName = (Name) clone(o.theReturnName);

      result.thePackageName = (Name) clone(o.thePackageName);

      result.theStringLiteral = (StringLiteral) clone(o.theStringLiteral);

      {
        final ArrayList<ParameterSpecification> theParameterSpecifications = o.theParameterSpecifications;

        final ArrayList<ParameterSpecification> temp0 = new ArrayList<ParameterSpecification>(
            theParameterSpecifications.size());
        for (final ParameterSpecification e0 : theParameterSpecifications) {
          final ParameterSpecification newE0 = (ParameterSpecification) clone(e0);
          temp0.add(newE0);
        }
        result.theParameterSpecifications = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFunctionSpecification(final FunctionSpecification o) {
      assert o != null;
      final FunctionSpecification result = new FunctionSpecification();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theMethodNameSelection = o.theMethodNameSelection;

      result.theName = (Name) clone(o.theName);

      {
        final ArrayList<ParameterSpecification> theOptionalParameterSpecification = o.theOptionalParameterSpecification;

        if (theOptionalParameterSpecification != null) {
          final ArrayList<ParameterSpecification> temp0 = new ArrayList<ParameterSpecification>(
              theOptionalParameterSpecification.size());
          for (final ParameterSpecification e0 : theOptionalParameterSpecification) {
            final ParameterSpecification newE0 = (ParameterSpecification) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalParameterSpecification = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFunctionSpecificationRenamingDeclaration(
        final FunctionSpecificationRenamingDeclaration o) {
      assert o != null;
      final FunctionSpecificationRenamingDeclaration result = new FunctionSpecificationRenamingDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theFunctionSpecification = (FunctionSpecification) clone(o.theFunctionSpecification);

      result.theName = (Name) clone(o.theName);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFunctionSubProgramBody(final FunctionSubProgramBody o) {
      assert o != null;
      final FunctionSubProgramBody result = new FunctionSubProgramBody();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubProgramImplementation = (SubProgramImplementation) clone(o.theSubProgramImplementation);

      result.theFunctionSpecification = (FunctionSpecification) clone(o.theFunctionSpecification);

      result.theOptionalFunctionAnnotation = (FunctionAnnotation) clone(o.theOptionalFunctionAnnotation);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitFunctionSubProgramDeclaration(
        final FunctionSubProgramDeclaration o) {
      assert o != null;
      final FunctionSubProgramDeclaration result = new FunctionSubProgramDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theFunctionSpecification = (FunctionSpecification) clone(o.theFunctionSpecification);

      result.theFunctionAnnotation = (FunctionAnnotation) clone(o.theFunctionAnnotation);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitGenericAssociation(final GenericAssociation o) {
      assert o != null;
      final GenericAssociation result = new GenericAssociation();
      this.g.cloneMap.put(o, result);

      result.theOptionalIDString = o.theOptionalIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitGenericInstantiation(final GenericInstantiation o) {
      assert o != null;
      final GenericInstantiation result = new GenericInstantiation();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      {
        final ArrayList<GenericAssociation> theOptionalGenericAssociations = o.theOptionalGenericAssociations;

        if (theOptionalGenericAssociations != null) {
          final ArrayList<GenericAssociation> temp0 = new ArrayList<GenericAssociation>(
              theOptionalGenericAssociations.size());
          for (final GenericAssociation e0 : theOptionalGenericAssociations) {
            final GenericAssociation newE0 = (GenericAssociation) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalGenericAssociations = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitGlobalDeclaration(final GlobalDeclaration o) {
      assert o != null;
      final GlobalDeclaration result = new GlobalDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theMode = o.theMode;

      {
        final ArrayList<Name> theNames = o.theNames;

        final ArrayList<Name> temp0 = new ArrayList<Name>(theNames.size());
        for (final Name e0 : theNames) {
          final Name newE0 = (Name) clone(e0);
          temp0.add(newE0);
        }
        result.theNames = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitGlobalDefinition(final GlobalDefinition o) {
      assert o != null;
      final GlobalDefinition result = new GlobalDefinition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<GlobalDeclaration> theGlobalDeclarations = o.theGlobalDeclarations;

        final ArrayList<GlobalDeclaration> temp0 = new ArrayList<GlobalDeclaration>(
            theGlobalDeclarations.size());
        for (final GlobalDeclaration e0 : theGlobalDeclarations) {
          final GlobalDeclaration newE0 = (GlobalDeclaration) clone(e0);
          temp0.add(newE0);
        }
        result.theGlobalDeclarations = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitIDAttributeDesignator(final IDAttributeDesignator o) {
      assert o != null;
      final IDAttributeDesignator result = new IDAttributeDesignator();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<Exp> theOptionalExps = o.theOptionalExps;

        if (theOptionalExps != null) {
          final ArrayList<Exp> temp0 = new ArrayList<Exp>(
              theOptionalExps.size());
          for (final Exp e0 : theOptionalExps) {
            final Exp newE0 = (Exp) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalExps = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitIDName(final IDName o) {
      assert o != null;
      final IDName result = new IDName();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theDecoratedFlag = o.theDecoratedFlag;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitIfStatement(final IfStatement o) {
      assert o != null;
      final IfStatement result = new IfStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      result.theThen = (StatementList) clone(o.theThen);

      result.theIfRegionSelection = o.theIfRegionSelection;

      result.theOptionalElse = (StatementList) clone(o.theOptionalElse);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      {
        final ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> theOptionalElseIfs = o.theOptionalElseIfs;

        if (theOptionalElseIfs != null) {
          final ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> temp0 = new ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>>(
              theOptionalElseIfs.size());
          for (final Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection> e0 : theOptionalElseIfs) {
            Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection> newE0 = null;
            final Exp pe1_0 = (Exp) clone(e0.getE0());
            final StatementList pe1_1 = (StatementList) clone(e0.getE1());
            final org.sireum.bakar.selection.model.RegionSelection pe1_2 = e0
                .getE2();
            newE0 = new Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>(
                pe1_0, pe1_1, pe1_2);

            temp0.add(newE0);
          }
          result.theOptionalElseIfs = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitIndexConstraint(final IndexConstraint o) {
      assert o != null;
      final IndexConstraint result = new IndexConstraint();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<Name> theDiscreteSubTypeNames = o.theDiscreteSubTypeNames;

        final ArrayList<Name> temp0 = new ArrayList<Name>(
            theDiscreteSubTypeNames.size());
        for (final Name e0 : theDiscreteSubTypeNames) {
          final Name newE0 = (Name) clone(e0);
          temp0.add(newE0);
        }
        result.theDiscreteSubTypeNames = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitIndexedComponent(final IndexedComponent o) {
      assert o != null;
      final IndexedComponent result = new IndexedComponent();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      {
        final ArrayList<Exp> theExps = o.theExps;

        final ArrayList<Exp> temp0 = new ArrayList<Exp>(theExps.size());
        for (final Exp e0 : theExps) {
          final Exp newE0 = (Exp) clone(e0);
          temp0.add(newE0);
        }
        result.theExps = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitInRangeExp(final InRangeExp o) {
      assert o != null;
      final InRangeExp result = new InRangeExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      result.theRange = (Range) clone(o.theRange);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitJustificationClause(final JustificationClause o) {
      assert o != null;
      final JustificationClause result = new JustificationClause();
      this.g.cloneMap.put(o, result);

      result.theMessageID = o.theMessageID;

      result.theMessage = o.theMessage;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theKind = o.theKind;

      {
        final ArrayList<Name> theOptionalNames = o.theOptionalNames;

        if (theOptionalNames != null) {
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalNames.size());
          for (final Name e0 : theOptionalNames) {
            final Name newE0 = (Name) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalNames = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitJustificationStatement(final JustificationStatement o) {
      assert o != null;
      final JustificationStatement result = new JustificationStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      {
        final ArrayList<JustificationClause> theClauses = o.theClauses;

        final ArrayList<JustificationClause> temp0 = new ArrayList<JustificationClause>(
            theClauses.size());
        for (final JustificationClause e0 : theClauses) {
          final JustificationClause newE0 = (JustificationClause) clone(e0);
          temp0.add(newE0);
        }
        result.theClauses = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitJustificationStatementEnd(
        final JustificationStatementEnd o) {
      assert o != null;
      final JustificationStatementEnd result = new JustificationStatementEnd();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitLibraryCompilationUnit(final LibraryCompilationUnit o) {
      assert o != null;
      final LibraryCompilationUnit result = new LibraryCompilationUnit();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theContextClause = (ContextClause) clone(o.theContextClause);

      result.theLibraryItem = (LibraryItem) clone(o.theLibraryItem);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitLibraryUnitBody(final LibraryUnitBody o) {
      assert o != null;
      final LibraryUnitBody result = new LibraryUnitBody();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.thePackageBody = (PackageBody) clone(o.thePackageBody);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitLiteralExp(final LiteralExp o) {
      assert o != null;
      final LiteralExp result = new LiteralExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theLiteral = (Literal) clone(o.theLiteral);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitMainProgram(final MainProgram o) {
      assert o != null;
      final MainProgram result = new MainProgram();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubProgramBody = (SubProgramBody) clone(o.theSubProgramBody);

      {
        final ArrayList<Name> theOptionalInheritClauses = o.theOptionalInheritClauses;

        if (theOptionalInheritClauses != null) {
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalInheritClauses.size());
          for (final Name e0 : theOptionalInheritClauses) {
            final Name newE0 = (Name) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalInheritClauses = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitModularTypeDefinition(final ModularTypeDefinition o) {
      assert o != null;
      final ModularTypeDefinition result = new ModularTypeDefinition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNamedArrayAggregate(final NamedArrayAggregate o) {
      assert o != null;
      final NamedArrayAggregate result = new NamedArrayAggregate();
      this.g.cloneMap.put(o, result);

      result.theOthersFlag = o.theOthersFlag;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalOthersAggregateItem = (AggregateItem) clone(o.theOptionalOthersAggregateItem);

      {
        final ArrayList<ArrayComponentAssociation> theOptionalArrayComponentAssociations = o.theOptionalArrayComponentAssociations;

        if (theOptionalArrayComponentAssociations != null) {
          final ArrayList<ArrayComponentAssociation> temp0 = new ArrayList<ArrayComponentAssociation>(
              theOptionalArrayComponentAssociations.size());
          for (final ArrayComponentAssociation e0 : theOptionalArrayComponentAssociations) {
            final ArrayComponentAssociation newE0 = (ArrayComponentAssociation) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalArrayComponentAssociations = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNamedParameterAssociation(
        final NamedParameterAssociation o) {
      assert o != null;
      final NamedParameterAssociation result = new NamedParameterAssociation();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNamedParameterAssociationList(
        final NamedParameterAssociationList o) {
      assert o != null;
      final NamedParameterAssociationList result = new NamedParameterAssociationList();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<NamedParameterAssociation> theParameterAssociations = o.theParameterAssociations;

        final ArrayList<NamedParameterAssociation> temp0 = new ArrayList<NamedParameterAssociation>(
            theParameterAssociations.size());
        for (final NamedParameterAssociation e0 : theParameterAssociations) {
          final NamedParameterAssociation newE0 = (NamedParameterAssociation) clone(e0);
          temp0.add(newE0);
        }
        result.theParameterAssociations = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNamedRecordAggregate(final NamedRecordAggregate o) {
      assert o != null;
      final NamedRecordAggregate result = new NamedRecordAggregate();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<RecordComponentAssociation> theRecordComponentAssociations = o.theRecordComponentAssociations;

        final ArrayList<RecordComponentAssociation> temp0 = new ArrayList<RecordComponentAssociation>(
            theRecordComponentAssociations.size());
        for (final RecordComponentAssociation e0 : theRecordComponentAssociations) {
          final RecordComponentAssociation newE0 = (RecordComponentAssociation) clone(e0);
          temp0.add(newE0);
        }
        result.theRecordComponentAssociations = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNamedRecordExtensionAggregate(
        final NamedRecordExtensionAggregate o) {
      assert o != null;
      final NamedRecordExtensionAggregate result = new NamedRecordExtensionAggregate();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      {
        final ArrayList<RecordComponentAssociation> theRecordComponentAssociations = o.theRecordComponentAssociations;

        final ArrayList<RecordComponentAssociation> temp0 = new ArrayList<RecordComponentAssociation>(
            theRecordComponentAssociations.size());
        for (final RecordComponentAssociation e0 : theRecordComponentAssociations) {
          final RecordComponentAssociation newE0 = (RecordComponentAssociation) clone(e0);
          temp0.add(newE0);
        }
        result.theRecordComponentAssociations = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNameExp(final NameExp o) {
      assert o != null;
      final NameExp result = new NameExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNamePragmaArgumentAssociation(
        final NamePragmaArgumentAssociation o) {
      assert o != null;
      final NamePragmaArgumentAssociation result = new NamePragmaArgumentAssociation();
      this.g.cloneMap.put(o, result);

      result.theOptionalIDString = o.theOptionalIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNameRangeExp(final NameRangeExp o) {
      assert o != null;
      final NameRangeExp result = new NameRangeExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      result.theName = (Name) clone(o.theName);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNullRecordExtensionAggregate(
        final NullRecordExtensionAggregate o) {
      assert o != null;
      final NullRecordExtensionAggregate result = new NullRecordExtensionAggregate();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNullStatement(final NullStatement o) {
      assert o != null;
      final NullStatement result = new NullStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNumberDeclaration(final NumberDeclaration o) {
      assert o != null;
      final NumberDeclaration result = new NumberDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      {
        final ArrayList<IDName> theIDNames = o.theIDNames;

        final ArrayList<IDName> temp0 = new ArrayList<IDName>(theIDNames.size());
        for (final IDName e0 : theIDNames) {
          final IDName newE0 = (IDName) clone(e0);
          temp0.add(newE0);
        }
        result.theIDNames = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitNumericLiteral(final NumericLiteral o) {
      assert o != null;
      final NumericLiteral result = new NumericLiteral();
      this.g.cloneMap.put(o, result);

      result.theNumberString = o.theNumberString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitObjectDeclaration(final ObjectDeclaration o) {
      assert o != null;
      final ObjectDeclaration result = new ObjectDeclaration();
      this.g.cloneMap.put(o, result);

      result.theConstantFlag = o.theConstantFlag;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubtypeMark = (Name) clone(o.theSubtypeMark);

      result.theOptionalInitializingExp = (Exp) clone(o.theOptionalInitializingExp);

      {
        final ArrayList<IDName> theDefiningIdentifierList = o.theDefiningIdentifierList;

        final ArrayList<IDName> temp0 = new ArrayList<IDName>(
            theDefiningIdentifierList.size());
        for (final IDName e0 : theDefiningIdentifierList) {
          final IDName newE0 = (IDName) clone(e0);
          temp0.add(newE0);
        }
        result.theDefiningIdentifierList = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitOrdinaryFixedPointDefinition(
        final OrdinaryFixedPointDefinition o) {
      assert o != null;
      final OrdinaryFixedPointDefinition result = new OrdinaryFixedPointDefinition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      result.theLowRangeExp = (Exp) clone(o.theLowRangeExp);

      result.theHighRangeExp = (Exp) clone(o.theHighRangeExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitOwnVariable(final OwnVariable o) {
      assert o != null;
      final OwnVariable result = new OwnVariable();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theMode = o.theMode;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitOwnVariableSpecification(
        final OwnVariableSpecification o) {
      assert o != null;
      final OwnVariableSpecification result = new OwnVariableSpecification();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalSubtypeMark = (Name) clone(o.theOptionalSubtypeMark);

      {
        final ArrayList<OwnVariable> theOwnVariables = o.theOwnVariables;

        final ArrayList<OwnVariable> temp0 = new ArrayList<OwnVariable>(
            theOwnVariables.size());
        for (final OwnVariable e0 : theOwnVariables) {
          final OwnVariable newE0 = (OwnVariable) clone(e0);
          temp0.add(newE0);
        }
        result.theOwnVariables = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPackageAnnotation(final PackageAnnotation o) {
      assert o != null;
      final PackageAnnotation result = new PackageAnnotation();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalOwnVariablesRegionSelection = o.theOptionalOwnVariablesRegionSelection;

      {
        final ArrayList<OwnVariableSpecification> theOptionalOwnVariables = o.theOptionalOwnVariables;

        if (theOptionalOwnVariables != null) {
          final ArrayList<OwnVariableSpecification> temp0 = new ArrayList<OwnVariableSpecification>(
              theOptionalOwnVariables.size());
          for (final OwnVariableSpecification e0 : theOptionalOwnVariables) {
            final OwnVariableSpecification newE0 = (OwnVariableSpecification) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalOwnVariables = temp0;
        }
      }

      {
        final ArrayList<OwnVariable> theOptionalInitializeVariables = o.theOptionalInitializeVariables;

        if (theOptionalInitializeVariables != null) {
          final ArrayList<OwnVariable> temp0 = new ArrayList<OwnVariable>(
              theOptionalInitializeVariables.size());
          for (final OwnVariable e0 : theOptionalInitializeVariables) {
            final OwnVariable newE0 = (OwnVariable) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalInitializeVariables = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPackageBody(final PackageBody o) {
      assert o != null;
      final PackageBody result = new PackageBody();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theOptionalRefinementClausesRegionSelection = o.theOptionalRefinementClausesRegionSelection;

      result.thePackageImplementation = (PackageImplementation) clone(o.thePackageImplementation);

      {
        final ArrayList<RefinementClause> theRefinementClauses = o.theRefinementClauses;

        final ArrayList<RefinementClause> temp0 = new ArrayList<RefinementClause>(
            theRefinementClauses.size());
        for (final RefinementClause e0 : theRefinementClauses) {
          final RefinementClause newE0 = (RefinementClause) clone(e0);
          temp0.add(newE0);
        }
        result.theRefinementClauses = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPackageBodyStub(final PackageBodyStub o) {
      assert o != null;
      final PackageBodyStub result = new PackageBodyStub();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPackageDeclaration(final PackageDeclaration o) {
      assert o != null;
      final PackageDeclaration result = new PackageDeclaration();
      this.g.cloneMap.put(o, result);

      result.thePrivateFlag = o.thePrivateFlag;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.thePackageSpecification = (PackageSpecification) clone(o.thePackageSpecification);

      {
        final ArrayList<Name> theOptionalInheritClauses = o.theOptionalInheritClauses;

        if (theOptionalInheritClauses != null) {
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalInheritClauses.size());
          for (final Name e0 : theOptionalInheritClauses) {
            final Name newE0 = (Name) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalInheritClauses = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPackageImplementation(final PackageImplementation o) {
      assert o != null;
      final PackageImplementation result = new PackageImplementation();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theDeclarativePart = (DeclarativePart) clone(o.theDeclarativePart);

      result.theOptionalStatementList = (StatementList) clone(o.theOptionalStatementList);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPackageRenamingDeclaration(
        final PackageRenamingDeclaration o) {
      assert o != null;
      final PackageRenamingDeclaration result = new PackageRenamingDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.thePackageName = (Name) clone(o.thePackageName);

      result.theRenamedName = (Name) clone(o.theRenamedName);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPackageSpecification(final PackageSpecification o) {
      assert o != null;
      final PackageSpecification result = new PackageSpecification();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.thePackageAnnotation = (PackageAnnotation) clone(o.thePackageAnnotation);

      {
        final ArrayList<RenamingDeclaration> theOptionalVisiblePartDeclaration = o.theOptionalVisiblePartDeclaration;

        if (theOptionalVisiblePartDeclaration != null) {
          final ArrayList<RenamingDeclaration> temp0 = new ArrayList<RenamingDeclaration>(
              theOptionalVisiblePartDeclaration.size());
          for (final RenamingDeclaration e0 : theOptionalVisiblePartDeclaration) {
            final RenamingDeclaration newE0 = (RenamingDeclaration) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalVisiblePartDeclaration = temp0;
        }
      }

      {
        final ArrayList<DeclarativePartMember> theOptionalVisiblePartDeclarativePartMember = o.theOptionalVisiblePartDeclarativePartMember;

        if (theOptionalVisiblePartDeclarativePartMember != null) {
          final ArrayList<DeclarativePartMember> temp0 = new ArrayList<DeclarativePartMember>(
              theOptionalVisiblePartDeclarativePartMember.size());
          for (final DeclarativePartMember e0 : theOptionalVisiblePartDeclarativePartMember) {
            final DeclarativePartMember newE0 = (DeclarativePartMember) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalVisiblePartDeclarativePartMember = temp0;
        }
      }

      {
        final ArrayList<RenamingDeclaration> theOptionalPrivatePartDeclaration = o.theOptionalPrivatePartDeclaration;

        if (theOptionalPrivatePartDeclaration != null) {
          final ArrayList<RenamingDeclaration> temp0 = new ArrayList<RenamingDeclaration>(
              theOptionalPrivatePartDeclaration.size());
          for (final RenamingDeclaration e0 : theOptionalPrivatePartDeclaration) {
            final RenamingDeclaration newE0 = (RenamingDeclaration) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalPrivatePartDeclaration = temp0;
        }
      }

      {
        final ArrayList<DeclarativePartMember> theOptionalPrivatePartDeclarativePartMember = o.theOptionalPrivatePartDeclarativePartMember;

        if (theOptionalPrivatePartDeclarativePartMember != null) {
          final ArrayList<DeclarativePartMember> temp0 = new ArrayList<DeclarativePartMember>(
              theOptionalPrivatePartDeclarativePartMember.size());
          for (final DeclarativePartMember e0 : theOptionalPrivatePartDeclarativePartMember) {
            final DeclarativePartMember newE0 = (DeclarativePartMember) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalPrivatePartDeclarativePartMember = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitParameterSpecification(final ParameterSpecification o) {
      assert o != null;
      final ParameterSpecification result = new ParameterSpecification();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theMode = o.theMode;

      result.theSubtypeMark = (Name) clone(o.theSubtypeMark);

      {
        final ArrayList<IDName> theParameterNames = o.theParameterNames;

        final ArrayList<IDName> temp0 = new ArrayList<IDName>(
            theParameterNames.size());
        for (final IDName e0 : theParameterNames) {
          final IDName newE0 = (IDName) clone(e0);
          temp0.add(newE0);
        }
        result.theParameterNames = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitParenExp(final ParenExp o) {
      assert o != null;
      final ParenExp result = new ParenExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPositionalArrayAggregate(
        final PositionalArrayAggregate o) {
      assert o != null;
      final PositionalArrayAggregate result = new PositionalArrayAggregate();
      this.g.cloneMap.put(o, result);

      result.theOthersFlag = o.theOthersFlag;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalOthersAggregateItem = (AggregateItem) clone(o.theOptionalOthersAggregateItem);

      {
        final ArrayList<AggregateItem> theAggregateItems = o.theAggregateItems;

        final ArrayList<AggregateItem> temp0 = new ArrayList<AggregateItem>(
            theAggregateItems.size());
        for (final AggregateItem e0 : theAggregateItems) {
          final AggregateItem newE0 = (AggregateItem) clone(e0);
          temp0.add(newE0);
        }
        result.theAggregateItems = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPositionalParameterAssociationList(
        final PositionalParameterAssociationList o) {
      assert o != null;
      final PositionalParameterAssociationList result = new PositionalParameterAssociationList();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<Exp> theParameterAssociations = o.theParameterAssociations;

        final ArrayList<Exp> temp0 = new ArrayList<Exp>(
            theParameterAssociations.size());
        for (final Exp e0 : theParameterAssociations) {
          final Exp newE0 = (Exp) clone(e0);
          temp0.add(newE0);
        }
        result.theParameterAssociations = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPositionalRecordAggregate(
        final PositionalRecordAggregate o) {
      assert o != null;
      final PositionalRecordAggregate result = new PositionalRecordAggregate();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<Exp> theExps = o.theExps;

        final ArrayList<Exp> temp0 = new ArrayList<Exp>(theExps.size());
        for (final Exp e0 : theExps) {
          final Exp newE0 = (Exp) clone(e0);
          temp0.add(newE0);
        }
        result.theExps = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPositionalRecordExtensionAggregate(
        final PositionalRecordExtensionAggregate o) {
      assert o != null;
      final PositionalRecordExtensionAggregate result = new PositionalRecordExtensionAggregate();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      {
        final ArrayList<Exp> theExps = o.theExps;

        final ArrayList<Exp> temp0 = new ArrayList<Exp>(theExps.size());
        for (final Exp e0 : theExps) {
          final Exp newE0 = (Exp) clone(e0);
          temp0.add(newE0);
        }
        result.theExps = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPostcondition(final Postcondition o) {
      assert o != null;
      final Postcondition result = new Postcondition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.thePredicate = (Predicate) clone(o.thePredicate);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPragma(final Pragma o) {
      assert o != null;
      final Pragma result = new Pragma();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.dummyObjectToGetListVisitor = clone(o.dummyObjectToGetListVisitor);

      {
        final ArrayList<PragmaArgumentAssociation> theOptionalPragmaArgumentAssociations = o.theOptionalPragmaArgumentAssociations;

        if (theOptionalPragmaArgumentAssociations != null) {
          final ArrayList<PragmaArgumentAssociation> temp0 = new ArrayList<PragmaArgumentAssociation>(
              theOptionalPragmaArgumentAssociations.size());
          for (final PragmaArgumentAssociation e0 : theOptionalPragmaArgumentAssociations) {
            final PragmaArgumentAssociation newE0 = (PragmaArgumentAssociation) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalPragmaArgumentAssociations = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPrecondition(final Precondition o) {
      assert o != null;
      final Precondition result = new Precondition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.thePredicate = (Predicate) clone(o.thePredicate);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPredicate(final Predicate o) {
      assert o != null;
      final Predicate result = new Predicate();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPrivateExtensionDeclaration(
        final PrivateExtensionDeclaration o) {
      assert o != null;
      final PrivateExtensionDeclaration result = new PrivateExtensionDeclaration();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubTypeIndication = (SubTypeIndication) clone(o.theSubTypeIndication);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitPrivateTypeDeclaration(final PrivateTypeDeclaration o) {
      assert o != null;
      final PrivateTypeDeclaration result = new PrivateTypeDeclaration();
      this.g.cloneMap.put(o, result);

      result.theTaggedFlag = o.theTaggedFlag;

      result.theLimitedFlag = o.theLimitedFlag;

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitProcedureAnnotation(final ProcedureAnnotation o) {
      assert o != null;
      final ProcedureAnnotation result = new ProcedureAnnotation();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalGlobalDefinition = (GlobalDefinition) clone(o.theOptionalGlobalDefinition);

      result.theOptionalDependency = (DependencyRelation) clone(o.theOptionalDependency);

      result.theOptionalPrecondition = (Precondition) clone(o.theOptionalPrecondition);

      result.theOptionalPostcondition = (Postcondition) clone(o.theOptionalPostcondition);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitProcedureBodyStub(final ProcedureBodyStub o) {
      assert o != null;
      final ProcedureBodyStub result = new ProcedureBodyStub();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theProcedureSpecification = (ProcedureSpecification) clone(o.theProcedureSpecification);

      result.theOptionalProcedureAnnotation = (ProcedureAnnotation) clone(o.theOptionalProcedureAnnotation);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitProcedureCallStatement(final ProcedureCallStatement o) {
      assert o != null;
      final ProcedureCallStatement result = new ProcedureCallStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theOptionalParameterAssociationList = (ParameterAssociationList) clone(o.theOptionalParameterAssociationList);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitProcedureSpecification(final ProcedureSpecification o) {
      assert o != null;
      final ProcedureSpecification result = new ProcedureSpecification();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theMethodNameSelection = o.theMethodNameSelection;

      {
        final ArrayList<ParameterSpecification> theOptionalParameterSpecification = o.theOptionalParameterSpecification;

        if (theOptionalParameterSpecification != null) {
          final ArrayList<ParameterSpecification> temp0 = new ArrayList<ParameterSpecification>(
              theOptionalParameterSpecification.size());
          for (final ParameterSpecification e0 : theOptionalParameterSpecification) {
            final ParameterSpecification newE0 = (ParameterSpecification) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalParameterSpecification = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitProcedureSpecificationRenamingDeclaration(
        final ProcedureSpecificationRenamingDeclaration o) {
      assert o != null;
      final ProcedureSpecificationRenamingDeclaration result = new ProcedureSpecificationRenamingDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theProcedureSpecification = (ProcedureSpecification) clone(o.theProcedureSpecification);

      result.theName = (Name) clone(o.theName);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitProcedureSubProgramBody(final ProcedureSubProgramBody o) {
      assert o != null;
      final ProcedureSubProgramBody result = new ProcedureSubProgramBody();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubProgramImplementation = (SubProgramImplementation) clone(o.theSubProgramImplementation);

      result.theProcedureSpecification = (ProcedureSpecification) clone(o.theProcedureSpecification);

      result.theOptionalProcedureAnnotation = (ProcedureAnnotation) clone(o.theOptionalProcedureAnnotation);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitProcedureSubProgramDeclaration(
        final ProcedureSubProgramDeclaration o) {
      assert o != null;
      final ProcedureSubProgramDeclaration result = new ProcedureSubProgramDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theProcedureSpecification = (ProcedureSpecification) clone(o.theProcedureSpecification);

      result.theProcedureAnnotation = (ProcedureAnnotation) clone(o.theProcedureAnnotation);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitProofFunctionDeclaration(
        final ProofFunctionDeclaration o) {
      assert o != null;
      final ProofFunctionDeclaration result = new ProofFunctionDeclaration();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theFunctionSpecification = (FunctionSpecification) clone(o.theFunctionSpecification);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitProofTypeDeclaration(final ProofTypeDeclaration o) {
      assert o != null;
      final ProofTypeDeclaration result = new ProofTypeDeclaration();
      this.g.cloneMap.put(o, result);

      result.theDefiningIdentifier = o.theDefiningIdentifier;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitQuantifiedExp(final QuantifiedExp o) {
      assert o != null;
      final QuantifiedExp result = new QuantifiedExp();
      this.g.cloneMap.put(o, result);

      result.theIdentifier = o.theIdentifier;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theKind = o.theKind;

      result.theSubTypeMark = (Name) clone(o.theSubTypeMark);

      result.theOptionalRange = (Range) clone(o.theOptionalRange);

      result.thePredicate = (Predicate) clone(o.thePredicate);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRangeAttributeDesignator(
        final RangeAttributeDesignator o) {
      assert o != null;
      final RangeAttributeDesignator result = new RangeAttributeDesignator();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalExp = (Exp) clone(o.theOptionalExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRangeAttributeReference(final RangeAttributeReference o) {
      assert o != null;
      final RangeAttributeReference result = new RangeAttributeReference();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theDesignator = (RangeAttributeDesignator) clone(o.theDesignator);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRangeChoice(final RangeChoice o) {
      assert o != null;
      final RangeChoice result = new RangeChoice();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theRange = (Range) clone(o.theRange);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRangeConstraint(final RangeConstraint o) {
      assert o != null;
      final RangeConstraint result = new RangeConstraint();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theRange = (Range) clone(o.theRange);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRecordComponentAssociation(
        final RecordComponentAssociation o) {
      assert o != null;
      final RecordComponentAssociation result = new RecordComponentAssociation();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRecordDefinition(final RecordDefinition o) {
      assert o != null;
      final RecordDefinition result = new RecordDefinition();
      this.g.cloneMap.put(o, result);

      result.theNullRecordFlag = o.theNullRecordFlag;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<ComponentDeclaration> theOptionalComponentDeclarations = o.theOptionalComponentDeclarations;

        if (theOptionalComponentDeclarations != null) {
          final ArrayList<ComponentDeclaration> temp0 = new ArrayList<ComponentDeclaration>(
              theOptionalComponentDeclarations.size());
          for (final ComponentDeclaration e0 : theOptionalComponentDeclarations) {
            final ComponentDeclaration newE0 = (ComponentDeclaration) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalComponentDeclarations = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRecordRepresentationClause(
        final RecordRepresentationClause o) {
      assert o != null;
      final RecordRepresentationClause result = new RecordRepresentationClause();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theOptionalExp = (Exp) clone(o.theOptionalExp);

      {
        final ArrayList<ComponentClause> theOptionalComponentClauses = o.theOptionalComponentClauses;

        if (theOptionalComponentClauses != null) {
          final ArrayList<ComponentClause> temp0 = new ArrayList<ComponentClause>(
              theOptionalComponentClauses.size());
          for (final ComponentClause e0 : theOptionalComponentClauses) {
            final ComponentClause newE0 = (ComponentClause) clone(e0);
            temp0.add(newE0);
          }
          result.theOptionalComponentClauses = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRecordTypeDefinition(final RecordTypeDefinition o) {
      assert o != null;
      final RecordTypeDefinition result = new RecordTypeDefinition();
      this.g.cloneMap.put(o, result);

      result.theTaggedFlag = o.theTaggedFlag;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theRecordDefinition = (RecordDefinition) clone(o.theRecordDefinition);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRecordTypeExtension(final RecordTypeExtension o) {
      assert o != null;
      final RecordTypeExtension result = new RecordTypeExtension();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubtypeMark = (Name) clone(o.theSubtypeMark);

      result.theRecordDefinition = (RecordDefinition) clone(o.theRecordDefinition);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRecordUpdate(final RecordUpdate o) {
      assert o != null;
      final RecordUpdate result = new RecordUpdate();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      {
        final ArrayList<Pair<String, Exp>> theSelectorToExpressionList = o.theSelectorToExpressionList;

        final ArrayList<Pair<String, Exp>> temp0 = new ArrayList<Pair<String, Exp>>(
            theSelectorToExpressionList.size());
        for (final Pair<String, Exp> e0 : theSelectorToExpressionList) {
          Pair<String, Exp> newE0 = null;
          final String pe1_0 = e0.getE0();
          final Exp pe1_1 = (Exp) clone(e0.getE1());
          newE0 = new Pair<String, Exp>(pe1_0, pe1_1);

          temp0.add(newE0);
        }
        result.theSelectorToExpressionList = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRefinementClause(final RefinementClause o) {
      assert o != null;
      final RefinementClause result = new RefinementClause();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubject = (IDName) clone(o.theSubject);

      {
        final ArrayList<Constituent> theConstituents = o.theConstituents;

        final ArrayList<Constituent> temp0 = new ArrayList<Constituent>(
            theConstituents.size());
        for (final Constituent e0 : theConstituents) {
          final Constituent newE0 = (Constituent) clone(e0);
          temp0.add(newE0);
        }
        result.theConstituents = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRenamingDeclarationEmbeddedPackageDeclarationMember(
        final RenamingDeclarationEmbeddedPackageDeclarationMember o) {
      assert o != null;
      final RenamingDeclarationEmbeddedPackageDeclarationMember result = new RenamingDeclarationEmbeddedPackageDeclarationMember();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theRenamingDeclaration = (RenamingDeclaration) clone(o.theRenamingDeclaration);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitRepresentationClauseBasicDeclarativeItem(
        final RepresentationClauseBasicDeclarativeItem o) {
      assert o != null;
      final RepresentationClauseBasicDeclarativeItem result = new RepresentationClauseBasicDeclarativeItem();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theRepresentationClause = (RepresentationClause) clone(o.theRepresentationClause);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitReturnAnnotationExp(final ReturnAnnotationExp o) {
      assert o != null;
      final ReturnAnnotationExp result = new ReturnAnnotationExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitReturnAnnotationPred(final ReturnAnnotationPred o) {
      assert o != null;
      final ReturnAnnotationPred result = new ReturnAnnotationPred();
      this.g.cloneMap.put(o, result);

      result.theID = o.theID;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.thePredicate = (Predicate) clone(o.thePredicate);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitReturnStatement(final ReturnStatement o) {
      assert o != null;
      final ReturnStatement result = new ReturnStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitSelectedComponent(final SelectedComponent o) {
      assert o != null;
      final SelectedComponent result = new SelectedComponent();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theDecoratedFlag = o.theDecoratedFlag;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitSignedIntegerTypeDefinition(
        final SignedIntegerTypeDefinition o) {
      assert o != null;
      final SignedIntegerTypeDefinition result = new SignedIntegerTypeDefinition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theLowRangeExp = (Exp) clone(o.theLowRangeExp);

      result.theHighRangeExp = (Exp) clone(o.theHighRangeExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitStatementList(final StatementList o) {
      assert o != null;
      final StatementList result = new StatementList();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<Statement> theStatements = o.theStatements;

        final ArrayList<Statement> temp0 = new ArrayList<Statement>(
            theStatements.size());
        for (final Statement e0 : theStatements) {
          final Statement newE0 = (Statement) clone(e0);
          temp0.add(newE0);
        }
        result.theStatements = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitStatementSubProgramImplementation(
        final StatementSubProgramImplementation o) {
      assert o != null;
      final StatementSubProgramImplementation result = new StatementSubProgramImplementation();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theDeclarativePart = (DeclarativePart) clone(o.theDeclarativePart);

      result.theStatementList = (StatementList) clone(o.theStatementList);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitStringLiteral(final StringLiteral o) {
      assert o != null;
      final StringLiteral result = new StringLiteral();
      this.g.cloneMap.put(o, result);

      result.theString = o.theString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitSubTypeChoice(final SubTypeChoice o) {
      assert o != null;
      final SubTypeChoice result = new SubTypeChoice();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubTypeIndication = (SubTypeIndication) clone(o.theSubTypeIndication);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitSubTypeDeclaration(final SubTypeDeclaration o) {
      assert o != null;
      final SubTypeDeclaration result = new SubTypeDeclaration();
      this.g.cloneMap.put(o, result);

      result.theIDString = o.theIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theSubTypeIndication = (SubTypeIndication) clone(o.theSubTypeIndication);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitSubTypeIndication(final SubTypeIndication o) {
      assert o != null;
      final SubTypeIndication result = new SubTypeIndication();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theOptionalConstraint = (Constraint) clone(o.theOptionalConstraint);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitSubUnitCompilationUnit(final SubUnitCompilationUnit o) {
      assert o != null;
      final SubUnitCompilationUnit result = new SubUnitCompilationUnit();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theContextClause = (ContextClause) clone(o.theContextClause);

      result.theName = (Name) clone(o.theName);

      result.theProperBody = (ProperBody) clone(o.theProperBody);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitTypeAssertion(final TypeAssertion o) {
      assert o != null;
      final TypeAssertion result = new TypeAssertion();
      this.g.cloneMap.put(o, result);

      result.theIdentifier = o.theIdentifier;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theBase = (Name) clone(o.theBase);

      result.theSubtypeMark = (Name) clone(o.theSubtypeMark);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitTypeConversion(final TypeConversion o) {
      assert o != null;
      final TypeConversion result = new TypeConversion();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theName = (Name) clone(o.theName);

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitUnaryExp(final UnaryExp o) {
      assert o != null;
      final UnaryExp result = new UnaryExp();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theUnaryOp = o.theUnaryOp;

      result.theExp = (Exp) clone(o.theExp);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitUnconstrainedArrayDefinition(
        final UnconstrainedArrayDefinition o) {
      assert o != null;
      final UnconstrainedArrayDefinition result = new UnconstrainedArrayDefinition();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theComponentName = (Name) clone(o.theComponentName);

      {
        final ArrayList<Name> theIndexSubTypeNames = o.theIndexSubTypeNames;

        final ArrayList<Name> temp0 = new ArrayList<Name>(
            theIndexSubTypeNames.size());
        for (final Name e0 : theIndexSubTypeNames) {
          final Name newE0 = (Name) clone(e0);
          temp0.add(newE0);
        }
        result.theIndexSubTypeNames = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitUseTypeClause(final UseTypeClause o) {
      assert o != null;
      final UseTypeClause result = new UseTypeClause();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<Name> theNames = o.theNames;

        final ArrayList<Name> temp0 = new ArrayList<Name>(theNames.size());
        for (final Name e0 : theNames) {
          final Name newE0 = (Name) clone(e0);
          temp0.add(newE0);
        }
        result.theNames = temp0;

      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitUseTypeClauseEmbeddedPackageDeclarationMember(
        final UseTypeClauseEmbeddedPackageDeclarationMember o) {
      assert o != null;
      final UseTypeClauseEmbeddedPackageDeclarationMember result = new UseTypeClauseEmbeddedPackageDeclarationMember();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theUseTypeClause = (UseTypeClause) clone(o.theUseTypeClause);

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitWhileLoopStatement(final WhileLoopStatement o) {
      assert o != null;
      final WhileLoopStatement result = new WhileLoopStatement();
      this.g.cloneMap.put(o, result);

      result.theStatementIndex = o.theStatementIndex;

      result.theOptionalIDString = o.theOptionalIDString;

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      result.theOptionalIDRegionSelection = o.theOptionalIDRegionSelection;

      result.theLoopKeywordRegionSelection = o.theLoopKeywordRegionSelection;

      result.theOptionalLoopInvariant = (AssertStatement) clone(o.theOptionalLoopInvariant);

      result.theStatementList = (StatementList) clone(o.theStatementList);

      result.theIterationSchemeRegionSelection = o.theIterationSchemeRegionSelection;

      result.theExp = (Exp) clone(o.theExp);

      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            temp0.add(newE0);
          }
          result.theOptionalLabelList = temp0;
        }
      }

      this.g.pushResult(result);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void visitWithClause(final WithClause o) {
      assert o != null;
      final WithClause result = new WithClause();
      this.g.cloneMap.put(o, result);

      result.theOptionalRegionSelection = o.theOptionalRegionSelection;

      {
        final ArrayList<Name> theNames = o.theNames;

        final ArrayList<Name> temp0 = new ArrayList<Name>(theNames.size());
        for (final Name e0 : theNames) {
          final Name newE0 = (Name) clone(e0);
          temp0.add(newE0);
        }
        result.theNames = temp0;

      }

      this.g.pushResult(result);
    }
  }

  protected static class VisitorContext {
    @SuppressWarnings("rawtypes")
    public Map cloneMap;
    protected Node result;
    public boolean canCreateNode;

    protected Object peekResult() {
      assert this.result != null;
      return this.result;
    }

    protected Object popResult() {
      assert this.result != null;
      final Object temp = this.result;
      this.result = null;
      return temp;
    }

    protected void pushResult(final Node o) {
      assert (this.result == null) && (o != null);
      this.result = o;
    }
  }

  @SuppressWarnings("rawtypes")
  public static <O extends Node> O clone(final O o, final Map cloneMap) {
    return NodeCloner.clone(o, cloneMap, true);
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static <O extends Node> O clone(final O o, final Map cloneMap,
      final boolean canCreateNode) {
    assert (o != null) && (cloneMap != null);
    final VisitorContext vc = new VisitorContext();
    vc.cloneMap = cloneMap;
    vc.canCreateNode = canCreateNode;
    final Visitor v = new Visitor(vc);
    v.visit(o);
    vc.cloneMap = null;
    return (O) vc.popResult();
  }
}
