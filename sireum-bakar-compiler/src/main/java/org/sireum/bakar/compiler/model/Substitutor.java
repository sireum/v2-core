/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

import java.util.ArrayList;
import java.util.HashMap;

import org.sireum.bakar.util.NTuple;
import org.sireum.bakar.util.Pair;
import org.sireum.bakar.util.Triple;

public class Substitutor {
  public static interface SubstituteFunction {
    public Node substitute(Node o);
  }

  protected static class Visitor extends NodeVisitor<VisitorContext> {

    public Visitor(final VisitorContext g) {
      super(g);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected ArrayList substitute(final ArrayList o) {
      if (o == null) {
        return null;
      }
      boolean changed = false;
      final ArrayList result = new ArrayList();
      for (final Object e : o) {
        final Object e2 = substitute(e);
        result.add(e2);
        if (e != e2) {
          changed = true;
        }
      }
      if (changed) {
        return result;
      } else {
        return o;
      }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected HashMap substitute(final HashMap o) {
      if (o == null) {
        return null;
      }
      boolean changed = false;
      final int size = o.size();
      final HashMap result = new HashMap(size);
      for (final Object k : o.keySet()) {
        final Object v = o.get(k);
        final Object k2 = substitute(k);
        final Object v2 = substitute(v);
        result.put(k2, v2);
        if ((k != k2) || (v != v2)) {
          changed = true;
        }
      }
      if (changed) {
        return result;
      } else {
        return o;
      }
    }

    protected Node substitute(final Node o) {
      if (o == null) {
        return null;
      }
      final Node o2 = this.g.sf.substitute(o);
      if (o2 != null) {
        return o2;
      }
      visit(o);
      return this.g.popResult();
    }

    protected NTuple substitute(final NTuple o) {
      if (o == null) {
        return null;
      }
      boolean changed = false;
      final int size = o.size();
      final NTuple result = new NTuple();
      final Object[] elements = new Object[size];
      for (int i = 0; i < size; i++) {
        final Object e = o.get(i);
        final Object e2 = substitute(e);
        elements[i] = e2;
        if (e != e2) {
          changed = true;
        }
      }
      if (changed) {
        return result;
      } else {
        return o;
      }
    }

    @SuppressWarnings("rawtypes")
    protected Object substitute(final Object o) {
      if (o == null) {
        return null;
      }
      if (o instanceof Node) {
        return substitute((Node) o);
      } else if (o instanceof ArrayList) {
        return substitute((ArrayList) o);
      } else if (o instanceof HashMap) {
        return substitute((HashMap) o);
      } else if (o instanceof Pair) {
        return substitute((Pair) o);
      } else if (o instanceof Triple) {
        return substitute((Triple) o);
      } else if (o instanceof NTuple) {
        return substitute((NTuple) o);
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
    protected Pair substitute(final Pair o) {
      if (o == null) {
        return null;
      }
      final Object e0 = o.getE0();
      final Object e1 = o.getE1();
      final Object e02 = substitute(e0);
      final Object e12 = substitute(e1);

      if ((e02 != e0) || (e12 != e1)) {
        return new Pair(e02, e12);
      }
      return o;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Triple substitute(final Triple o) {
      if (o == null) {
        return null;
      }
      final Object e0 = o.getE0();
      final Object e1 = o.getE1();
      final Object e2 = o.getE2();
      final Object e02 = substitute(e0);
      final Object e12 = substitute(e1);
      final Object e22 = substitute(e2);

      if ((e02 != e0) || (e12 != e1) || (e22 != e2)) {
        return new Triple(e02, e12, e22);
      }
      return o;
    }

    @Override
    protected void visitAggregateQualifiedExp(final AggregateQualifiedExp o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Aggregate theAggregate2 = null;
      {
        final Aggregate theAggregate = o.theAggregate;

        theAggregate2 = (Aggregate) substitute(theAggregate);

        if (theAggregate != theAggregate2) {
          substituted = true;
        }
      }

      if (substituted) {
        final AggregateQualifiedExp result = new AggregateQualifiedExp();
        result.theName = theName2;
        result.theAggregate = theAggregate2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitArrayAggregateItem(final ArrayAggregateItem o) {
      assert o != null;
      boolean substituted = false;

      ArrayAggregate theArrayAggregate2 = null;
      {
        final ArrayAggregate theArrayAggregate = o.theArrayAggregate;

        theArrayAggregate2 = (ArrayAggregate) substitute(theArrayAggregate);

        if (theArrayAggregate != theArrayAggregate2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ArrayAggregateItem result = new ArrayAggregateItem();
        result.theArrayAggregate = theArrayAggregate2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitArrayComponentAssociation(
        final ArrayComponentAssociation o) {
      assert o != null;
      boolean substituted = false;

      AggregateItem theAggregateItem2 = null;
      {
        final AggregateItem theAggregateItem = o.theAggregateItem;

        theAggregateItem2 = (AggregateItem) substitute(theAggregateItem);

        if (theAggregateItem != theAggregateItem2) {
          substituted = true;
        }
      }

      ArrayList<Choice> theChoices2 = null;
      {
        final ArrayList<Choice> theChoices = o.theChoices;

        boolean substituted0 = false;
        final ArrayList<Choice> temp0 = new ArrayList<Choice>(theChoices.size());
        for (final Choice e0 : theChoices) {
          final Choice newE0 = (Choice) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theChoices2 = substituted0 ? temp0 : theChoices;

        if (theChoices != theChoices2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ArrayComponentAssociation result = new ArrayComponentAssociation();
        result.theAggregateItem = theAggregateItem2;
        result.theChoices = theChoices2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitArrayUpdate(final ArrayUpdate o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ArrayList<Pair<ArrayList<Exp>, Exp>> theIndexListToExpressionList2 = null;
      {
        final ArrayList<Pair<ArrayList<Exp>, Exp>> theIndexListToExpressionList = o.theIndexListToExpressionList;

        boolean substituted0 = false;
        final ArrayList<Pair<ArrayList<Exp>, Exp>> temp0 = new ArrayList<Pair<ArrayList<Exp>, Exp>>(
            theIndexListToExpressionList.size());
        for (final Pair<ArrayList<Exp>, Exp> e0 : theIndexListToExpressionList) {
          Pair<ArrayList<Exp>, Exp> newE0 = null;
          final ArrayList<Exp> pe1_0 = e0.getE0();
          final Exp pe1_1 = e0.getE1();
          ArrayList<Exp> newPe1_0 = null;
          boolean substituted2 = false;
          final ArrayList<Exp> temp2 = new ArrayList<Exp>(pe1_0.size());
          for (final Exp e2 : pe1_0) {
            final Exp newE2 = (Exp) substitute(e2);
            if (e2 != newE2) {
              substituted2 = true;
            }
            temp2.add(newE2);
          }
          newPe1_0 = substituted2 ? temp2 : pe1_0;

          final Exp newPe1_1 = (Exp) substitute(pe1_1);
          if ((newPe1_0 != pe1_0) || (newPe1_1 != pe1_1)) {
            newE0 = new Pair<ArrayList<Exp>, Exp>(newPe1_0, newPe1_1);
          } else {
            newE0 = e0;
          }

          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theIndexListToExpressionList2 = substituted0 ? temp0
            : theIndexListToExpressionList;

        if (theIndexListToExpressionList != theIndexListToExpressionList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ArrayUpdate result = new ArrayUpdate();
        result.theName = theName2;
        result.theIndexListToExpressionList = theIndexListToExpressionList2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitAssertStatement(final AssertStatement o) {
      assert o != null;
      boolean substituted = false;

      Predicate thePredicate2 = null;
      {
        final Predicate thePredicate = o.thePredicate;

        thePredicate2 = (Predicate) substitute(thePredicate);

        if (thePredicate != thePredicate2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final AssertStatement result = new AssertStatement();
        result.thePredicate = thePredicate2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitAssignmentStatement(final AssignmentStatement o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final AssignmentStatement result = new AssignmentStatement();
        result.theName = theName2;
        result.theExp = theExp2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitAtClause(final AtClause o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final AtClause result = new AtClause();
        result.theName = theName2;
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitAttributeDefinitionClause(
        final AttributeDefinitionClause o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final AttributeDefinitionClause result = new AttributeDefinitionClause();
        result.theName = theName2;
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitAttributeReference(final AttributeReference o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      AttributeDesignator theAttributeDesignator2 = null;
      {
        final AttributeDesignator theAttributeDesignator = o.theAttributeDesignator;

        theAttributeDesignator2 = (AttributeDesignator) substitute(theAttributeDesignator);

        if (theAttributeDesignator != theAttributeDesignator2) {
          substituted = true;
        }
      }

      if (substituted) {
        final AttributeReference result = new AttributeReference();
        result.theName = theName2;
        result.theAttributeDesignator = theAttributeDesignator2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitBasicDeclarationBasicDeclarativeItem(
        final BasicDeclarationBasicDeclarativeItem o) {
      assert o != null;
      boolean substituted = false;

      BasicDeclaration theBasicDeclaration2 = null;
      {
        final BasicDeclaration theBasicDeclaration = o.theBasicDeclaration;

        theBasicDeclaration2 = (BasicDeclaration) substitute(theBasicDeclaration);

        if (theBasicDeclaration != theBasicDeclaration2) {
          substituted = true;
        }
      }

      if (substituted) {
        final BasicDeclarationBasicDeclarativeItem result = new BasicDeclarationBasicDeclarativeItem();
        result.theBasicDeclaration = theBasicDeclaration2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitBinaryExp(final BinaryExp o) {
      assert o != null;
      boolean substituted = false;

      Exp theLeftExp2 = null;
      {
        final Exp theLeftExp = o.theLeftExp;

        theLeftExp2 = (Exp) substitute(theLeftExp);

        if (theLeftExp != theLeftExp2) {
          substituted = true;
        }
      }

      Exp theRightExp2 = null;
      {
        final Exp theRightExp = o.theRightExp;

        theRightExp2 = (Exp) substitute(theRightExp);

        if (theRightExp != theRightExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final BinaryExp result = new BinaryExp();
        result.theLeftExp = theLeftExp2;
        result.theRightExp = theRightExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theBinaryOp = o.theBinaryOp;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitCaseStatement(final CaseStatement o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      StatementList theOptionalOthers2 = null;
      {
        final StatementList theOptionalOthers = o.theOptionalOthers;

        theOptionalOthers2 = (StatementList) substitute(theOptionalOthers);

        if (theOptionalOthers != theOptionalOthers2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      ArrayList<CaseStatementAlternative> theCaseStatementAlternatives2 = null;
      {
        final ArrayList<CaseStatementAlternative> theCaseStatementAlternatives = o.theCaseStatementAlternatives;

        boolean substituted0 = false;
        final ArrayList<CaseStatementAlternative> temp0 = new ArrayList<CaseStatementAlternative>(
            theCaseStatementAlternatives.size());
        for (final CaseStatementAlternative e0 : theCaseStatementAlternatives) {
          final CaseStatementAlternative newE0 = (CaseStatementAlternative) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theCaseStatementAlternatives2 = substituted0 ? temp0
            : theCaseStatementAlternatives;

        if (theCaseStatementAlternatives != theCaseStatementAlternatives2) {
          substituted = true;
        }
      }

      if (substituted) {
        final CaseStatement result = new CaseStatement();
        result.theExp = theExp2;
        result.theOptionalOthers = theOptionalOthers2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theCaseStatementAlternatives = theCaseStatementAlternatives2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theOptionalOthersSelection = o.theOptionalOthersSelection;
        result.theOptionalOthersBodySelection = o.theOptionalOthersBodySelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitCaseStatementAlternative(
        final CaseStatementAlternative o) {
      assert o != null;
      boolean substituted = false;

      StatementList theStatementList2 = null;
      {
        final StatementList theStatementList = o.theStatementList;

        theStatementList2 = (StatementList) substitute(theStatementList);

        if (theStatementList != theStatementList2) {
          substituted = true;
        }
      }

      ArrayList<Choice> theChoices2 = null;
      {
        final ArrayList<Choice> theChoices = o.theChoices;

        boolean substituted0 = false;
        final ArrayList<Choice> temp0 = new ArrayList<Choice>(theChoices.size());
        for (final Choice e0 : theChoices) {
          final Choice newE0 = (Choice) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theChoices2 = substituted0 ? temp0 : theChoices;

        if (theChoices != theChoices2) {
          substituted = true;
        }
      }

      if (substituted) {
        final CaseStatementAlternative result = new CaseStatementAlternative();
        result.theStatementList = theStatementList2;
        result.theChoices = theChoices2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theBodySelection = o.theBodySelection;
        result.theChoiceListSelection = o.theChoiceListSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitCharacterLiteral(final CharacterLiteral o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitCheckStatement(final CheckStatement o) {
      assert o != null;
      boolean substituted = false;

      Predicate thePredicate2 = null;
      {
        final Predicate thePredicate = o.thePredicate;

        thePredicate2 = (Predicate) substitute(thePredicate);

        if (thePredicate != thePredicate2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final CheckStatement result = new CheckStatement();
        result.thePredicate = thePredicate2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitCodeSubProgramImplementation(
        final CodeSubProgramImplementation o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<QualifiedExp> theQualifiedExps2 = null;
      {
        final ArrayList<QualifiedExp> theQualifiedExps = o.theQualifiedExps;

        boolean substituted0 = false;
        final ArrayList<QualifiedExp> temp0 = new ArrayList<QualifiedExp>(
            theQualifiedExps.size());
        for (final QualifiedExp e0 : theQualifiedExps) {
          final QualifiedExp newE0 = (QualifiedExp) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theQualifiedExps2 = substituted0 ? temp0 : theQualifiedExps;

        if (theQualifiedExps != theQualifiedExps2) {
          substituted = true;
        }
      }

      if (substituted) {
        final CodeSubProgramImplementation result = new CodeSubProgramImplementation();
        result.theQualifiedExps = theQualifiedExps2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitCompilation(final Compilation o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<CompilationUnit> theOptionalCompilationUnits2 = null;
      {
        final ArrayList<CompilationUnit> theOptionalCompilationUnits = o.theOptionalCompilationUnits;

        if (theOptionalCompilationUnits != null) {
          boolean substituted0 = false;
          final ArrayList<CompilationUnit> temp0 = new ArrayList<CompilationUnit>(
              theOptionalCompilationUnits.size());
          for (final CompilationUnit e0 : theOptionalCompilationUnits) {
            final CompilationUnit newE0 = (CompilationUnit) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalCompilationUnits2 = substituted0 ? temp0
              : theOptionalCompilationUnits;
        }

        if (theOptionalCompilationUnits != theOptionalCompilationUnits2) {
          substituted = true;
        }
      }

      if (substituted) {
        final Compilation result = new Compilation();
        result.theOptionalCompilationUnits = theOptionalCompilationUnits2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitComponentClause(final ComponentClause o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Exp thePositionExp2 = null;
      {
        final Exp thePositionExp = o.thePositionExp;

        thePositionExp2 = (Exp) substitute(thePositionExp);

        if (thePositionExp != thePositionExp2) {
          substituted = true;
        }
      }

      Exp theFirstBitExp2 = null;
      {
        final Exp theFirstBitExp = o.theFirstBitExp;

        theFirstBitExp2 = (Exp) substitute(theFirstBitExp);

        if (theFirstBitExp != theFirstBitExp2) {
          substituted = true;
        }
      }

      Exp theLastBitExp2 = null;
      {
        final Exp theLastBitExp = o.theLastBitExp;

        theLastBitExp2 = (Exp) substitute(theLastBitExp);

        if (theLastBitExp != theLastBitExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ComponentClause result = new ComponentClause();
        result.theName = theName2;
        result.thePositionExp = thePositionExp2;
        result.theFirstBitExp = theFirstBitExp2;
        result.theLastBitExp = theLastBitExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitComponentDeclaration(final ComponentDeclaration o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ArrayList<IDName> theIDNames2 = null;
      {
        final ArrayList<IDName> theIDNames = o.theIDNames;

        boolean substituted0 = false;
        final ArrayList<IDName> temp0 = new ArrayList<IDName>(theIDNames.size());
        for (final IDName e0 : theIDNames) {
          final IDName newE0 = (IDName) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theIDNames2 = substituted0 ? temp0 : theIDNames;

        if (theIDNames != theIDNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ComponentDeclaration result = new ComponentDeclaration();
        result.theName = theName2;
        result.theIDNames = theIDNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitConstituent(final Constituent o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      if (substituted) {
        final Constituent result = new Constituent();
        result.theName = theName2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theMode = o.theMode;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitConstrainedArrayDefinition(
        final ConstrainedArrayDefinition o) {
      assert o != null;
      boolean substituted = false;

      Name theComponentName2 = null;
      {
        final Name theComponentName = o.theComponentName;

        theComponentName2 = (Name) substitute(theComponentName);

        if (theComponentName != theComponentName2) {
          substituted = true;
        }
      }

      ArrayList<Name> theDiscreteSubTypeNames2 = null;
      {
        final ArrayList<Name> theDiscreteSubTypeNames = o.theDiscreteSubTypeNames;

        boolean substituted0 = false;
        final ArrayList<Name> temp0 = new ArrayList<Name>(
            theDiscreteSubTypeNames.size());
        for (final Name e0 : theDiscreteSubTypeNames) {
          final Name newE0 = (Name) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theDiscreteSubTypeNames2 = substituted0 ? temp0
            : theDiscreteSubTypeNames;

        if (theDiscreteSubTypeNames != theDiscreteSubTypeNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ConstrainedArrayDefinition result = new ConstrainedArrayDefinition();
        result.theComponentName = theComponentName2;
        result.theDiscreteSubTypeNames = theDiscreteSubTypeNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitContextClause(final ContextClause o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<ContextItem> theOptionalContextItems2 = null;
      {
        final ArrayList<ContextItem> theOptionalContextItems = o.theOptionalContextItems;

        if (theOptionalContextItems != null) {
          boolean substituted0 = false;
          final ArrayList<ContextItem> temp0 = new ArrayList<ContextItem>(
              theOptionalContextItems.size());
          for (final ContextItem e0 : theOptionalContextItems) {
            final ContextItem newE0 = (ContextItem) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalContextItems2 = substituted0 ? temp0
              : theOptionalContextItems;
        }

        if (theOptionalContextItems != theOptionalContextItems2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ContextClause result = new ContextClause();
        result.theOptionalContextItems = theOptionalContextItems2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitDeclarativePart(final DeclarativePart o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<RenamingDeclaration> theOptionalRenamingDeclarations2 = null;
      {
        final ArrayList<RenamingDeclaration> theOptionalRenamingDeclarations = o.theOptionalRenamingDeclarations;

        if (theOptionalRenamingDeclarations != null) {
          boolean substituted0 = false;
          final ArrayList<RenamingDeclaration> temp0 = new ArrayList<RenamingDeclaration>(
              theOptionalRenamingDeclarations.size());
          for (final RenamingDeclaration e0 : theOptionalRenamingDeclarations) {
            final RenamingDeclaration newE0 = (RenamingDeclaration) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalRenamingDeclarations2 = substituted0 ? temp0
              : theOptionalRenamingDeclarations;
        }

        if (theOptionalRenamingDeclarations != theOptionalRenamingDeclarations2) {
          substituted = true;
        }
      }

      ArrayList<DeclarativePartMember> theOptionalDeclarativePartMembers2 = null;
      {
        final ArrayList<DeclarativePartMember> theOptionalDeclarativePartMembers = o.theOptionalDeclarativePartMembers;

        if (theOptionalDeclarativePartMembers != null) {
          boolean substituted0 = false;
          final ArrayList<DeclarativePartMember> temp0 = new ArrayList<DeclarativePartMember>(
              theOptionalDeclarativePartMembers.size());
          for (final DeclarativePartMember e0 : theOptionalDeclarativePartMembers) {
            final DeclarativePartMember newE0 = (DeclarativePartMember) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalDeclarativePartMembers2 = substituted0 ? temp0
              : theOptionalDeclarativePartMembers;
        }

        if (theOptionalDeclarativePartMembers != theOptionalDeclarativePartMembers2) {
          substituted = true;
        }
      }

      if (substituted) {
        final DeclarativePart result = new DeclarativePart();
        result.theOptionalRenamingDeclarations = theOptionalRenamingDeclarations2;
        result.theOptionalDeclarativePartMembers = theOptionalDeclarativePartMembers2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitDefaultLoopStatement(final DefaultLoopStatement o) {
      assert o != null;
      boolean substituted = false;

      AssertStatement theOptionalLoopInvariant2 = null;
      {
        final AssertStatement theOptionalLoopInvariant = o.theOptionalLoopInvariant;

        theOptionalLoopInvariant2 = (AssertStatement) substitute(theOptionalLoopInvariant);

        if (theOptionalLoopInvariant != theOptionalLoopInvariant2) {
          substituted = true;
        }
      }

      StatementList theStatementList2 = null;
      {
        final StatementList theStatementList = o.theStatementList;

        theStatementList2 = (StatementList) substitute(theStatementList);

        if (theStatementList != theStatementList2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final DefaultLoopStatement result = new DefaultLoopStatement();
        result.theOptionalLoopInvariant = theOptionalLoopInvariant2;
        result.theStatementList = theStatementList2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalIDString = o.theOptionalIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theOptionalIDRegionSelection = o.theOptionalIDRegionSelection;
        result.theLoopKeywordRegionSelection = o.theLoopKeywordRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitDeltaAttributeDesignator(
        final DeltaAttributeDesignator o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitDependencyClause(final DependencyClause o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Name> theExportedVariables2 = null;
      {
        final ArrayList<Name> theExportedVariables = o.theExportedVariables;

        boolean substituted0 = false;
        final ArrayList<Name> temp0 = new ArrayList<Name>(
            theExportedVariables.size());
        for (final Name e0 : theExportedVariables) {
          final Name newE0 = (Name) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theExportedVariables2 = substituted0 ? temp0 : theExportedVariables;

        if (theExportedVariables != theExportedVariables2) {
          substituted = true;
        }
      }

      ArrayList<Name> theOptionalImportedVariables2 = null;
      {
        final ArrayList<Name> theOptionalImportedVariables = o.theOptionalImportedVariables;

        if (theOptionalImportedVariables != null) {
          boolean substituted0 = false;
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalImportedVariables.size());
          for (final Name e0 : theOptionalImportedVariables) {
            final Name newE0 = (Name) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalImportedVariables2 = substituted0 ? temp0
              : theOptionalImportedVariables;
        }

        if (theOptionalImportedVariables != theOptionalImportedVariables2) {
          substituted = true;
        }
      }

      if (substituted) {
        final DependencyClause result = new DependencyClause();
        result.theExportedVariables = theExportedVariables2;
        result.theOptionalImportedVariables = theOptionalImportedVariables2;
        result.theImportStarFlag = o.theImportStarFlag;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitDependencyRelation(final DependencyRelation o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<DependencyClause> theOptionalDependencyClauses2 = null;
      {
        final ArrayList<DependencyClause> theOptionalDependencyClauses = o.theOptionalDependencyClauses;

        if (theOptionalDependencyClauses != null) {
          boolean substituted0 = false;
          final ArrayList<DependencyClause> temp0 = new ArrayList<DependencyClause>(
              theOptionalDependencyClauses.size());
          for (final DependencyClause e0 : theOptionalDependencyClauses) {
            final DependencyClause newE0 = (DependencyClause) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalDependencyClauses2 = substituted0 ? temp0
              : theOptionalDependencyClauses;
        }

        if (theOptionalDependencyClauses != theOptionalDependencyClauses2) {
          substituted = true;
        }
      }

      ArrayList<Name> theOptionalNullDependencyVariables2 = null;
      {
        final ArrayList<Name> theOptionalNullDependencyVariables = o.theOptionalNullDependencyVariables;

        if (theOptionalNullDependencyVariables != null) {
          boolean substituted0 = false;
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalNullDependencyVariables.size());
          for (final Name e0 : theOptionalNullDependencyVariables) {
            final Name newE0 = (Name) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalNullDependencyVariables2 = substituted0 ? temp0
              : theOptionalNullDependencyVariables;
        }

        if (theOptionalNullDependencyVariables != theOptionalNullDependencyVariables2) {
          substituted = true;
        }
      }

      if (substituted) {
        final DependencyRelation result = new DependencyRelation();
        result.theOptionalDependencyClauses = theOptionalDependencyClauses2;
        result.theOptionalNullDependencyVariables = theOptionalNullDependencyVariables2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitDigitsAttributeDesignator(
        final DigitsAttributeDesignator o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitEmbeddedPackageDeclaration(
        final EmbeddedPackageDeclaration o) {
      assert o != null;
      boolean substituted = false;

      PackageDeclaration thePackageDeclaration2 = null;
      {
        final PackageDeclaration thePackageDeclaration = o.thePackageDeclaration;

        thePackageDeclaration2 = (PackageDeclaration) substitute(thePackageDeclaration);

        if (thePackageDeclaration != thePackageDeclaration2) {
          substituted = true;
        }
      }

      ArrayList<EmbeddedPackageDeclarationMember> theOptionalEmbeddedPackageDeclarationMembers2 = null;
      {
        final ArrayList<EmbeddedPackageDeclarationMember> theOptionalEmbeddedPackageDeclarationMembers = o.theOptionalEmbeddedPackageDeclarationMembers;

        if (theOptionalEmbeddedPackageDeclarationMembers != null) {
          boolean substituted0 = false;
          final ArrayList<EmbeddedPackageDeclarationMember> temp0 = new ArrayList<EmbeddedPackageDeclarationMember>(
              theOptionalEmbeddedPackageDeclarationMembers.size());
          for (final EmbeddedPackageDeclarationMember e0 : theOptionalEmbeddedPackageDeclarationMembers) {
            final EmbeddedPackageDeclarationMember newE0 = (EmbeddedPackageDeclarationMember) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalEmbeddedPackageDeclarationMembers2 = substituted0 ? temp0
              : theOptionalEmbeddedPackageDeclarationMembers;
        }

        if (theOptionalEmbeddedPackageDeclarationMembers != theOptionalEmbeddedPackageDeclarationMembers2) {
          substituted = true;
        }
      }

      if (substituted) {
        final EmbeddedPackageDeclaration result = new EmbeddedPackageDeclaration();
        result.thePackageDeclaration = thePackageDeclaration2;
        result.theOptionalEmbeddedPackageDeclarationMembers = theOptionalEmbeddedPackageDeclarationMembers2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitEnumerationRepresentationClause(
        final EnumerationRepresentationClause o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ArrayAggregate theArrayAggregate2 = null;
      {
        final ArrayAggregate theArrayAggregate = o.theArrayAggregate;

        theArrayAggregate2 = (ArrayAggregate) substitute(theArrayAggregate);

        if (theArrayAggregate != theArrayAggregate2) {
          substituted = true;
        }
      }

      if (substituted) {
        final EnumerationRepresentationClause result = new EnumerationRepresentationClause();
        result.theName = theName2;
        result.theArrayAggregate = theArrayAggregate2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitEnumerationTypeDefinition(
        final EnumerationTypeDefinition o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<IDName> theIDNames2 = null;
      {
        final ArrayList<IDName> theIDNames = o.theIDNames;

        boolean substituted0 = false;
        final ArrayList<IDName> temp0 = new ArrayList<IDName>(theIDNames.size());
        for (final IDName e0 : theIDNames) {
          final IDName newE0 = (IDName) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theIDNames2 = substituted0 ? temp0 : theIDNames;

        if (theIDNames != theIDNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final EnumerationTypeDefinition result = new EnumerationTypeDefinition();
        result.theIDNames = theIDNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitExitStatement(final ExitStatement o) {
      assert o != null;
      boolean substituted = false;

      Name theOptionalName2 = null;
      {
        final Name theOptionalName = o.theOptionalName;

        theOptionalName2 = (Name) substitute(theOptionalName);

        if (theOptionalName != theOptionalName2) {
          substituted = true;
        }
      }

      Exp theOptionalExp2 = null;
      {
        final Exp theOptionalExp = o.theOptionalExp;

        theOptionalExp2 = (Exp) substitute(theOptionalExp);

        if (theOptionalExp != theOptionalExp2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ExitStatement result = new ExitStatement();
        result.theOptionalName = theOptionalName2;
        result.theOptionalExp = theOptionalExp2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitExpAggregateItem(final ExpAggregateItem o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ExpAggregateItem result = new ExpAggregateItem();
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitExpChoice(final ExpChoice o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ExpChoice result = new ExpChoice();
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitExpPragmaArgumentAssociation(
        final ExpPragmaArgumentAssociation o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ExpPragmaArgumentAssociation result = new ExpPragmaArgumentAssociation();
        result.theExp = theExp2;
        result.theOptionalIDString = o.theOptionalIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitExpQualifiedExp(final ExpQualifiedExp o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ExpQualifiedExp result = new ExpQualifiedExp();
        result.theName = theName2;
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitExpRange(final ExpRange o) {
      assert o != null;
      boolean substituted = false;

      Exp theLowRangeExp2 = null;
      {
        final Exp theLowRangeExp = o.theLowRangeExp;

        theLowRangeExp2 = (Exp) substitute(theLowRangeExp);

        if (theLowRangeExp != theLowRangeExp2) {
          substituted = true;
        }
      }

      Exp theHighRangeExp2 = null;
      {
        final Exp theHighRangeExp = o.theHighRangeExp;

        theHighRangeExp2 = (Exp) substitute(theHighRangeExp);

        if (theHighRangeExp != theHighRangeExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ExpRange result = new ExpRange();
        result.theLowRangeExp = theLowRangeExp2;
        result.theHighRangeExp = theHighRangeExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitExternalSubProgramDeclaration(
        final ExternalSubProgramDeclaration o) {
      assert o != null;
      boolean substituted = false;

      SubProgramDeclaration theSubProgramDeclaration2 = null;
      {
        final SubProgramDeclaration theSubProgramDeclaration = o.theSubProgramDeclaration;

        theSubProgramDeclaration2 = (SubProgramDeclaration) substitute(theSubProgramDeclaration);

        if (theSubProgramDeclaration != theSubProgramDeclaration2) {
          substituted = true;
        }
      }

      Pragma thePragma2 = null;
      {
        final Pragma thePragma = o.thePragma;

        thePragma2 = (Pragma) substitute(thePragma);

        if (thePragma != thePragma2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ExternalSubProgramDeclaration result = new ExternalSubProgramDeclaration();
        result.theSubProgramDeclaration = theSubProgramDeclaration2;
        result.thePragma = thePragma2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFloatingPointDefinition(final FloatingPointDefinition o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      Exp theOptionalLowRangeExp2 = null;
      {
        final Exp theOptionalLowRangeExp = o.theOptionalLowRangeExp;

        theOptionalLowRangeExp2 = (Exp) substitute(theOptionalLowRangeExp);

        if (theOptionalLowRangeExp != theOptionalLowRangeExp2) {
          substituted = true;
        }
      }

      Exp theOptionalHighRangeExp2 = null;
      {
        final Exp theOptionalHighRangeExp = o.theOptionalHighRangeExp;

        theOptionalHighRangeExp2 = (Exp) substitute(theOptionalHighRangeExp);

        if (theOptionalHighRangeExp != theOptionalHighRangeExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FloatingPointDefinition result = new FloatingPointDefinition();
        result.theExp = theExp2;
        result.theOptionalLowRangeExp = theOptionalLowRangeExp2;
        result.theOptionalHighRangeExp = theOptionalHighRangeExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitForLoopStatement(final ForLoopStatement o) {
      assert o != null;
      boolean substituted = false;

      AssertStatement theOptionalLoopInvariant2 = null;
      {
        final AssertStatement theOptionalLoopInvariant = o.theOptionalLoopInvariant;

        theOptionalLoopInvariant2 = (AssertStatement) substitute(theOptionalLoopInvariant);

        if (theOptionalLoopInvariant != theOptionalLoopInvariant2) {
          substituted = true;
        }
      }

      StatementList theStatementList2 = null;
      {
        final StatementList theStatementList = o.theStatementList;

        theStatementList2 = (StatementList) substitute(theStatementList);

        if (theStatementList != theStatementList2) {
          substituted = true;
        }
      }

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Range theOptionalRange2 = null;
      {
        final Range theOptionalRange = o.theOptionalRange;

        theOptionalRange2 = (Range) substitute(theOptionalRange);

        if (theOptionalRange != theOptionalRange2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ForLoopStatement result = new ForLoopStatement();
        result.theOptionalLoopInvariant = theOptionalLoopInvariant2;
        result.theStatementList = theStatementList2;
        result.theName = theName2;
        result.theOptionalRange = theOptionalRange2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theReverseFlag = o.theReverseFlag;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalIDString = o.theOptionalIDString;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theOptionalIDRegionSelection = o.theOptionalIDRegionSelection;
        result.theLoopKeywordRegionSelection = o.theLoopKeywordRegionSelection;
        result.theIterationSchemeRegionSelection = o.theIterationSchemeRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFullTypeDeclaration(final FullTypeDeclaration o) {
      assert o != null;
      boolean substituted = false;

      TypeDefinition theTypeDefinition2 = null;
      {
        final TypeDefinition theTypeDefinition = o.theTypeDefinition;

        theTypeDefinition2 = (TypeDefinition) substitute(theTypeDefinition);

        if (theTypeDefinition != theTypeDefinition2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FullTypeDeclaration result = new FullTypeDeclaration();
        result.theTypeDefinition = theTypeDefinition2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFunctionAnnotation(final FunctionAnnotation o) {
      assert o != null;
      boolean substituted = false;

      GlobalDefinition theOptionalGlobalDefinition2 = null;
      {
        final GlobalDefinition theOptionalGlobalDefinition = o.theOptionalGlobalDefinition;

        theOptionalGlobalDefinition2 = (GlobalDefinition) substitute(theOptionalGlobalDefinition);

        if (theOptionalGlobalDefinition != theOptionalGlobalDefinition2) {
          substituted = true;
        }
      }

      Precondition theOptionalPrecondition2 = null;
      {
        final Precondition theOptionalPrecondition = o.theOptionalPrecondition;

        theOptionalPrecondition2 = (Precondition) substitute(theOptionalPrecondition);

        if (theOptionalPrecondition != theOptionalPrecondition2) {
          substituted = true;
        }
      }

      ReturnAnnotation theOptionalReturnAnnotation2 = null;
      {
        final ReturnAnnotation theOptionalReturnAnnotation = o.theOptionalReturnAnnotation;

        theOptionalReturnAnnotation2 = (ReturnAnnotation) substitute(theOptionalReturnAnnotation);

        if (theOptionalReturnAnnotation != theOptionalReturnAnnotation2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FunctionAnnotation result = new FunctionAnnotation();
        result.theOptionalGlobalDefinition = theOptionalGlobalDefinition2;
        result.theOptionalPrecondition = theOptionalPrecondition2;
        result.theOptionalReturnAnnotation = theOptionalReturnAnnotation2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFunctionBodyStub(final FunctionBodyStub o) {
      assert o != null;
      boolean substituted = false;

      FunctionSpecification theFunctionSpecification2 = null;
      {
        final FunctionSpecification theFunctionSpecification = o.theFunctionSpecification;

        theFunctionSpecification2 = (FunctionSpecification) substitute(theFunctionSpecification);

        if (theFunctionSpecification != theFunctionSpecification2) {
          substituted = true;
        }
      }

      FunctionAnnotation theOptionalFunctionAnnotation2 = null;
      {
        final FunctionAnnotation theOptionalFunctionAnnotation = o.theOptionalFunctionAnnotation;

        theOptionalFunctionAnnotation2 = (FunctionAnnotation) substitute(theOptionalFunctionAnnotation);

        if (theOptionalFunctionAnnotation != theOptionalFunctionAnnotation2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FunctionBodyStub result = new FunctionBodyStub();
        result.theFunctionSpecification = theFunctionSpecification2;
        result.theOptionalFunctionAnnotation = theOptionalFunctionAnnotation2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFunctionCall(final FunctionCall o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ParameterAssociationList theOptionalParameterAssociationList2 = null;
      {
        final ParameterAssociationList theOptionalParameterAssociationList = o.theOptionalParameterAssociationList;

        theOptionalParameterAssociationList2 = (ParameterAssociationList) substitute(theOptionalParameterAssociationList);

        if (theOptionalParameterAssociationList != theOptionalParameterAssociationList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FunctionCall result = new FunctionCall();
        result.theName = theName2;
        result.theOptionalParameterAssociationList = theOptionalParameterAssociationList2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFunctionRenamingDeclaration(
        final FunctionRenamingDeclaration o) {
      assert o != null;
      boolean substituted = false;

      StringLiteral theDefiningStringLiteral2 = null;
      {
        final StringLiteral theDefiningStringLiteral = o.theDefiningStringLiteral;

        theDefiningStringLiteral2 = (StringLiteral) substitute(theDefiningStringLiteral);

        if (theDefiningStringLiteral != theDefiningStringLiteral2) {
          substituted = true;
        }
      }

      Name theReturnName2 = null;
      {
        final Name theReturnName = o.theReturnName;

        theReturnName2 = (Name) substitute(theReturnName);

        if (theReturnName != theReturnName2) {
          substituted = true;
        }
      }

      Name thePackageName2 = null;
      {
        final Name thePackageName = o.thePackageName;

        thePackageName2 = (Name) substitute(thePackageName);

        if (thePackageName != thePackageName2) {
          substituted = true;
        }
      }

      StringLiteral theStringLiteral2 = null;
      {
        final StringLiteral theStringLiteral = o.theStringLiteral;

        theStringLiteral2 = (StringLiteral) substitute(theStringLiteral);

        if (theStringLiteral != theStringLiteral2) {
          substituted = true;
        }
      }

      ArrayList<ParameterSpecification> theParameterSpecifications2 = null;
      {
        final ArrayList<ParameterSpecification> theParameterSpecifications = o.theParameterSpecifications;

        boolean substituted0 = false;
        final ArrayList<ParameterSpecification> temp0 = new ArrayList<ParameterSpecification>(
            theParameterSpecifications.size());
        for (final ParameterSpecification e0 : theParameterSpecifications) {
          final ParameterSpecification newE0 = (ParameterSpecification) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theParameterSpecifications2 = substituted0 ? temp0
            : theParameterSpecifications;

        if (theParameterSpecifications != theParameterSpecifications2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FunctionRenamingDeclaration result = new FunctionRenamingDeclaration();
        result.theDefiningStringLiteral = theDefiningStringLiteral2;
        result.theReturnName = theReturnName2;
        result.thePackageName = thePackageName2;
        result.theStringLiteral = theStringLiteral2;
        result.theParameterSpecifications = theParameterSpecifications2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFunctionSpecification(final FunctionSpecification o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ArrayList<ParameterSpecification> theOptionalParameterSpecification2 = null;
      {
        final ArrayList<ParameterSpecification> theOptionalParameterSpecification = o.theOptionalParameterSpecification;

        if (theOptionalParameterSpecification != null) {
          boolean substituted0 = false;
          final ArrayList<ParameterSpecification> temp0 = new ArrayList<ParameterSpecification>(
              theOptionalParameterSpecification.size());
          for (final ParameterSpecification e0 : theOptionalParameterSpecification) {
            final ParameterSpecification newE0 = (ParameterSpecification) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalParameterSpecification2 = substituted0 ? temp0
              : theOptionalParameterSpecification;
        }

        if (theOptionalParameterSpecification != theOptionalParameterSpecification2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FunctionSpecification result = new FunctionSpecification();
        result.theName = theName2;
        result.theOptionalParameterSpecification = theOptionalParameterSpecification2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theMethodNameSelection = o.theMethodNameSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFunctionSpecificationRenamingDeclaration(
        final FunctionSpecificationRenamingDeclaration o) {
      assert o != null;
      boolean substituted = false;

      FunctionSpecification theFunctionSpecification2 = null;
      {
        final FunctionSpecification theFunctionSpecification = o.theFunctionSpecification;

        theFunctionSpecification2 = (FunctionSpecification) substitute(theFunctionSpecification);

        if (theFunctionSpecification != theFunctionSpecification2) {
          substituted = true;
        }
      }

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FunctionSpecificationRenamingDeclaration result = new FunctionSpecificationRenamingDeclaration();
        result.theFunctionSpecification = theFunctionSpecification2;
        result.theName = theName2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFunctionSubProgramBody(final FunctionSubProgramBody o) {
      assert o != null;
      boolean substituted = false;

      SubProgramImplementation theSubProgramImplementation2 = null;
      {
        final SubProgramImplementation theSubProgramImplementation = o.theSubProgramImplementation;

        theSubProgramImplementation2 = (SubProgramImplementation) substitute(theSubProgramImplementation);

        if (theSubProgramImplementation != theSubProgramImplementation2) {
          substituted = true;
        }
      }

      FunctionSpecification theFunctionSpecification2 = null;
      {
        final FunctionSpecification theFunctionSpecification = o.theFunctionSpecification;

        theFunctionSpecification2 = (FunctionSpecification) substitute(theFunctionSpecification);

        if (theFunctionSpecification != theFunctionSpecification2) {
          substituted = true;
        }
      }

      FunctionAnnotation theOptionalFunctionAnnotation2 = null;
      {
        final FunctionAnnotation theOptionalFunctionAnnotation = o.theOptionalFunctionAnnotation;

        theOptionalFunctionAnnotation2 = (FunctionAnnotation) substitute(theOptionalFunctionAnnotation);

        if (theOptionalFunctionAnnotation != theOptionalFunctionAnnotation2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FunctionSubProgramBody result = new FunctionSubProgramBody();
        result.theSubProgramImplementation = theSubProgramImplementation2;
        result.theFunctionSpecification = theFunctionSpecification2;
        result.theOptionalFunctionAnnotation = theOptionalFunctionAnnotation2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitFunctionSubProgramDeclaration(
        final FunctionSubProgramDeclaration o) {
      assert o != null;
      boolean substituted = false;

      FunctionSpecification theFunctionSpecification2 = null;
      {
        final FunctionSpecification theFunctionSpecification = o.theFunctionSpecification;

        theFunctionSpecification2 = (FunctionSpecification) substitute(theFunctionSpecification);

        if (theFunctionSpecification != theFunctionSpecification2) {
          substituted = true;
        }
      }

      FunctionAnnotation theFunctionAnnotation2 = null;
      {
        final FunctionAnnotation theFunctionAnnotation = o.theFunctionAnnotation;

        theFunctionAnnotation2 = (FunctionAnnotation) substitute(theFunctionAnnotation);

        if (theFunctionAnnotation != theFunctionAnnotation2) {
          substituted = true;
        }
      }

      if (substituted) {
        final FunctionSubProgramDeclaration result = new FunctionSubProgramDeclaration();
        result.theFunctionSpecification = theFunctionSpecification2;
        result.theFunctionAnnotation = theFunctionAnnotation2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitGenericAssociation(final GenericAssociation o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      if (substituted) {
        final GenericAssociation result = new GenericAssociation();
        result.theName = theName2;
        result.theOptionalIDString = o.theOptionalIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitGenericInstantiation(final GenericInstantiation o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ArrayList<GenericAssociation> theOptionalGenericAssociations2 = null;
      {
        final ArrayList<GenericAssociation> theOptionalGenericAssociations = o.theOptionalGenericAssociations;

        if (theOptionalGenericAssociations != null) {
          boolean substituted0 = false;
          final ArrayList<GenericAssociation> temp0 = new ArrayList<GenericAssociation>(
              theOptionalGenericAssociations.size());
          for (final GenericAssociation e0 : theOptionalGenericAssociations) {
            final GenericAssociation newE0 = (GenericAssociation) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalGenericAssociations2 = substituted0 ? temp0
              : theOptionalGenericAssociations;
        }

        if (theOptionalGenericAssociations != theOptionalGenericAssociations2) {
          substituted = true;
        }
      }

      if (substituted) {
        final GenericInstantiation result = new GenericInstantiation();
        result.theName = theName2;
        result.theOptionalGenericAssociations = theOptionalGenericAssociations2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitGlobalDeclaration(final GlobalDeclaration o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Name> theNames2 = null;
      {
        final ArrayList<Name> theNames = o.theNames;

        boolean substituted0 = false;
        final ArrayList<Name> temp0 = new ArrayList<Name>(theNames.size());
        for (final Name e0 : theNames) {
          final Name newE0 = (Name) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theNames2 = substituted0 ? temp0 : theNames;

        if (theNames != theNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final GlobalDeclaration result = new GlobalDeclaration();
        result.theNames = theNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theMode = o.theMode;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitGlobalDefinition(final GlobalDefinition o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<GlobalDeclaration> theGlobalDeclarations2 = null;
      {
        final ArrayList<GlobalDeclaration> theGlobalDeclarations = o.theGlobalDeclarations;

        boolean substituted0 = false;
        final ArrayList<GlobalDeclaration> temp0 = new ArrayList<GlobalDeclaration>(
            theGlobalDeclarations.size());
        for (final GlobalDeclaration e0 : theGlobalDeclarations) {
          final GlobalDeclaration newE0 = (GlobalDeclaration) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theGlobalDeclarations2 = substituted0 ? temp0 : theGlobalDeclarations;

        if (theGlobalDeclarations != theGlobalDeclarations2) {
          substituted = true;
        }
      }

      if (substituted) {
        final GlobalDefinition result = new GlobalDefinition();
        result.theGlobalDeclarations = theGlobalDeclarations2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitIDAttributeDesignator(final IDAttributeDesignator o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Exp> theOptionalExps2 = null;
      {
        final ArrayList<Exp> theOptionalExps = o.theOptionalExps;

        if (theOptionalExps != null) {
          boolean substituted0 = false;
          final ArrayList<Exp> temp0 = new ArrayList<Exp>(
              theOptionalExps.size());
          for (final Exp e0 : theOptionalExps) {
            final Exp newE0 = (Exp) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalExps2 = substituted0 ? temp0 : theOptionalExps;
        }

        if (theOptionalExps != theOptionalExps2) {
          substituted = true;
        }
      }

      if (substituted) {
        final IDAttributeDesignator result = new IDAttributeDesignator();
        result.theOptionalExps = theOptionalExps2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitIDName(final IDName o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitIfStatement(final IfStatement o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      StatementList theThen2 = null;
      {
        final StatementList theThen = o.theThen;

        theThen2 = (StatementList) substitute(theThen);

        if (theThen != theThen2) {
          substituted = true;
        }
      }

      StatementList theOptionalElse2 = null;
      {
        final StatementList theOptionalElse = o.theOptionalElse;

        theOptionalElse2 = (StatementList) substitute(theOptionalElse);

        if (theOptionalElse != theOptionalElse2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> theOptionalElseIfs2 = null;
      {
        final ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> theOptionalElseIfs = o.theOptionalElseIfs;

        if (theOptionalElseIfs != null) {
          boolean substituted0 = false;
          final ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> temp0 = new ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>>(
              theOptionalElseIfs.size());
          for (final Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection> e0 : theOptionalElseIfs) {
            Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection> newE0 = null;
            final Exp te1_0 = e0.getE0();
            final StatementList te1_1 = e0.getE1();
            final org.sireum.bakar.selection.model.RegionSelection te1_2 = e0
                .getE2();
            final Exp newTe1_0 = (Exp) substitute(te1_0);
            final StatementList newTe1_1 = (StatementList) substitute(te1_1);
            final org.sireum.bakar.selection.model.RegionSelection newTe1_2 = te1_2;
            if ((newTe1_0 != te1_0) || (newTe1_1 != te1_1)
                || (newTe1_2 != te1_2)) {
              newE0 = new Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>(
                  newTe1_0, newTe1_1, newTe1_2);
            }

            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalElseIfs2 = substituted0 ? temp0 : theOptionalElseIfs;
        }

        if (theOptionalElseIfs != theOptionalElseIfs2) {
          substituted = true;
        }
      }

      if (substituted) {
        final IfStatement result = new IfStatement();
        result.theExp = theExp2;
        result.theThen = theThen2;
        result.theOptionalElse = theOptionalElse2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theOptionalElseIfs = theOptionalElseIfs2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theIfRegionSelection = o.theIfRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitIndexConstraint(final IndexConstraint o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Name> theDiscreteSubTypeNames2 = null;
      {
        final ArrayList<Name> theDiscreteSubTypeNames = o.theDiscreteSubTypeNames;

        boolean substituted0 = false;
        final ArrayList<Name> temp0 = new ArrayList<Name>(
            theDiscreteSubTypeNames.size());
        for (final Name e0 : theDiscreteSubTypeNames) {
          final Name newE0 = (Name) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theDiscreteSubTypeNames2 = substituted0 ? temp0
            : theDiscreteSubTypeNames;

        if (theDiscreteSubTypeNames != theDiscreteSubTypeNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final IndexConstraint result = new IndexConstraint();
        result.theDiscreteSubTypeNames = theDiscreteSubTypeNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitIndexedComponent(final IndexedComponent o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ArrayList<Exp> theExps2 = null;
      {
        final ArrayList<Exp> theExps = o.theExps;

        boolean substituted0 = false;
        final ArrayList<Exp> temp0 = new ArrayList<Exp>(theExps.size());
        for (final Exp e0 : theExps) {
          final Exp newE0 = (Exp) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theExps2 = substituted0 ? temp0 : theExps;

        if (theExps != theExps2) {
          substituted = true;
        }
      }

      if (substituted) {
        final IndexedComponent result = new IndexedComponent();
        result.theName = theName2;
        result.theExps = theExps2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitInRangeExp(final InRangeExp o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      Range theRange2 = null;
      {
        final Range theRange = o.theRange;

        theRange2 = (Range) substitute(theRange);

        if (theRange != theRange2) {
          substituted = true;
        }
      }

      if (substituted) {
        final InRangeExp result = new InRangeExp();
        result.theExp = theExp2;
        result.theRange = theRange2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitJustificationClause(final JustificationClause o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Name> theOptionalNames2 = null;
      {
        final ArrayList<Name> theOptionalNames = o.theOptionalNames;

        if (theOptionalNames != null) {
          boolean substituted0 = false;
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalNames.size());
          for (final Name e0 : theOptionalNames) {
            final Name newE0 = (Name) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalNames2 = substituted0 ? temp0 : theOptionalNames;
        }

        if (theOptionalNames != theOptionalNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final JustificationClause result = new JustificationClause();
        result.theOptionalNames = theOptionalNames2;
        result.theMessageID = o.theMessageID;
        result.theMessage = o.theMessage;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theKind = o.theKind;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitJustificationStatement(final JustificationStatement o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      ArrayList<JustificationClause> theClauses2 = null;
      {
        final ArrayList<JustificationClause> theClauses = o.theClauses;

        boolean substituted0 = false;
        final ArrayList<JustificationClause> temp0 = new ArrayList<JustificationClause>(
            theClauses.size());
        for (final JustificationClause e0 : theClauses) {
          final JustificationClause newE0 = (JustificationClause) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theClauses2 = substituted0 ? temp0 : theClauses;

        if (theClauses != theClauses2) {
          substituted = true;
        }
      }

      if (substituted) {
        final JustificationStatement result = new JustificationStatement();
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theClauses = theClauses2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitJustificationStatementEnd(
        final JustificationStatementEnd o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final JustificationStatementEnd result = new JustificationStatementEnd();
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitLibraryCompilationUnit(final LibraryCompilationUnit o) {
      assert o != null;
      boolean substituted = false;

      ContextClause theContextClause2 = null;
      {
        final ContextClause theContextClause = o.theContextClause;

        theContextClause2 = (ContextClause) substitute(theContextClause);

        if (theContextClause != theContextClause2) {
          substituted = true;
        }
      }

      LibraryItem theLibraryItem2 = null;
      {
        final LibraryItem theLibraryItem = o.theLibraryItem;

        theLibraryItem2 = (LibraryItem) substitute(theLibraryItem);

        if (theLibraryItem != theLibraryItem2) {
          substituted = true;
        }
      }

      if (substituted) {
        final LibraryCompilationUnit result = new LibraryCompilationUnit();
        result.theContextClause = theContextClause2;
        result.theLibraryItem = theLibraryItem2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitLibraryUnitBody(final LibraryUnitBody o) {
      assert o != null;
      boolean substituted = false;

      PackageBody thePackageBody2 = null;
      {
        final PackageBody thePackageBody = o.thePackageBody;

        thePackageBody2 = (PackageBody) substitute(thePackageBody);

        if (thePackageBody != thePackageBody2) {
          substituted = true;
        }
      }

      if (substituted) {
        final LibraryUnitBody result = new LibraryUnitBody();
        result.thePackageBody = thePackageBody2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitLiteralExp(final LiteralExp o) {
      assert o != null;
      boolean substituted = false;

      Literal theLiteral2 = null;
      {
        final Literal theLiteral = o.theLiteral;

        theLiteral2 = (Literal) substitute(theLiteral);

        if (theLiteral != theLiteral2) {
          substituted = true;
        }
      }

      if (substituted) {
        final LiteralExp result = new LiteralExp();
        result.theLiteral = theLiteral2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitMainProgram(final MainProgram o) {
      assert o != null;
      boolean substituted = false;

      SubProgramBody theSubProgramBody2 = null;
      {
        final SubProgramBody theSubProgramBody = o.theSubProgramBody;

        theSubProgramBody2 = (SubProgramBody) substitute(theSubProgramBody);

        if (theSubProgramBody != theSubProgramBody2) {
          substituted = true;
        }
      }

      ArrayList<Name> theOptionalInheritClauses2 = null;
      {
        final ArrayList<Name> theOptionalInheritClauses = o.theOptionalInheritClauses;

        if (theOptionalInheritClauses != null) {
          boolean substituted0 = false;
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalInheritClauses.size());
          for (final Name e0 : theOptionalInheritClauses) {
            final Name newE0 = (Name) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalInheritClauses2 = substituted0 ? temp0
              : theOptionalInheritClauses;
        }

        if (theOptionalInheritClauses != theOptionalInheritClauses2) {
          substituted = true;
        }
      }

      if (substituted) {
        final MainProgram result = new MainProgram();
        result.theSubProgramBody = theSubProgramBody2;
        result.theOptionalInheritClauses = theOptionalInheritClauses2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitModularTypeDefinition(final ModularTypeDefinition o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ModularTypeDefinition result = new ModularTypeDefinition();
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNamedArrayAggregate(final NamedArrayAggregate o) {
      assert o != null;
      boolean substituted = false;

      AggregateItem theOptionalOthersAggregateItem2 = null;
      {
        final AggregateItem theOptionalOthersAggregateItem = o.theOptionalOthersAggregateItem;

        theOptionalOthersAggregateItem2 = (AggregateItem) substitute(theOptionalOthersAggregateItem);

        if (theOptionalOthersAggregateItem != theOptionalOthersAggregateItem2) {
          substituted = true;
        }
      }

      ArrayList<ArrayComponentAssociation> theOptionalArrayComponentAssociations2 = null;
      {
        final ArrayList<ArrayComponentAssociation> theOptionalArrayComponentAssociations = o.theOptionalArrayComponentAssociations;

        if (theOptionalArrayComponentAssociations != null) {
          boolean substituted0 = false;
          final ArrayList<ArrayComponentAssociation> temp0 = new ArrayList<ArrayComponentAssociation>(
              theOptionalArrayComponentAssociations.size());
          for (final ArrayComponentAssociation e0 : theOptionalArrayComponentAssociations) {
            final ArrayComponentAssociation newE0 = (ArrayComponentAssociation) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalArrayComponentAssociations2 = substituted0 ? temp0
              : theOptionalArrayComponentAssociations;
        }

        if (theOptionalArrayComponentAssociations != theOptionalArrayComponentAssociations2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NamedArrayAggregate result = new NamedArrayAggregate();
        result.theOptionalOthersAggregateItem = theOptionalOthersAggregateItem2;
        result.theOptionalArrayComponentAssociations = theOptionalArrayComponentAssociations2;
        result.theOthersFlag = o.theOthersFlag;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNamedParameterAssociation(
        final NamedParameterAssociation o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NamedParameterAssociation result = new NamedParameterAssociation();
        result.theExp = theExp2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNamedParameterAssociationList(
        final NamedParameterAssociationList o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<NamedParameterAssociation> theParameterAssociations2 = null;
      {
        final ArrayList<NamedParameterAssociation> theParameterAssociations = o.theParameterAssociations;

        boolean substituted0 = false;
        final ArrayList<NamedParameterAssociation> temp0 = new ArrayList<NamedParameterAssociation>(
            theParameterAssociations.size());
        for (final NamedParameterAssociation e0 : theParameterAssociations) {
          final NamedParameterAssociation newE0 = (NamedParameterAssociation) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theParameterAssociations2 = substituted0 ? temp0
            : theParameterAssociations;

        if (theParameterAssociations != theParameterAssociations2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NamedParameterAssociationList result = new NamedParameterAssociationList();
        result.theParameterAssociations = theParameterAssociations2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNamedRecordAggregate(final NamedRecordAggregate o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<RecordComponentAssociation> theRecordComponentAssociations2 = null;
      {
        final ArrayList<RecordComponentAssociation> theRecordComponentAssociations = o.theRecordComponentAssociations;

        boolean substituted0 = false;
        final ArrayList<RecordComponentAssociation> temp0 = new ArrayList<RecordComponentAssociation>(
            theRecordComponentAssociations.size());
        for (final RecordComponentAssociation e0 : theRecordComponentAssociations) {
          final RecordComponentAssociation newE0 = (RecordComponentAssociation) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theRecordComponentAssociations2 = substituted0 ? temp0
            : theRecordComponentAssociations;

        if (theRecordComponentAssociations != theRecordComponentAssociations2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NamedRecordAggregate result = new NamedRecordAggregate();
        result.theRecordComponentAssociations = theRecordComponentAssociations2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNamedRecordExtensionAggregate(
        final NamedRecordExtensionAggregate o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      ArrayList<RecordComponentAssociation> theRecordComponentAssociations2 = null;
      {
        final ArrayList<RecordComponentAssociation> theRecordComponentAssociations = o.theRecordComponentAssociations;

        boolean substituted0 = false;
        final ArrayList<RecordComponentAssociation> temp0 = new ArrayList<RecordComponentAssociation>(
            theRecordComponentAssociations.size());
        for (final RecordComponentAssociation e0 : theRecordComponentAssociations) {
          final RecordComponentAssociation newE0 = (RecordComponentAssociation) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theRecordComponentAssociations2 = substituted0 ? temp0
            : theRecordComponentAssociations;

        if (theRecordComponentAssociations != theRecordComponentAssociations2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NamedRecordExtensionAggregate result = new NamedRecordExtensionAggregate();
        result.theExp = theExp2;
        result.theRecordComponentAssociations = theRecordComponentAssociations2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNameExp(final NameExp o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NameExp result = new NameExp();
        result.theName = theName2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNamePragmaArgumentAssociation(
        final NamePragmaArgumentAssociation o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NamePragmaArgumentAssociation result = new NamePragmaArgumentAssociation();
        result.theName = theName2;
        result.theOptionalIDString = o.theOptionalIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNameRangeExp(final NameRangeExp o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NameRangeExp result = new NameRangeExp();
        result.theExp = theExp2;
        result.theName = theName2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNullRecordExtensionAggregate(
        final NullRecordExtensionAggregate o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NullRecordExtensionAggregate result = new NullRecordExtensionAggregate();
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNullStatement(final NullStatement o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NullStatement result = new NullStatement();
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNumberDeclaration(final NumberDeclaration o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      ArrayList<IDName> theIDNames2 = null;
      {
        final ArrayList<IDName> theIDNames = o.theIDNames;

        boolean substituted0 = false;
        final ArrayList<IDName> temp0 = new ArrayList<IDName>(theIDNames.size());
        for (final IDName e0 : theIDNames) {
          final IDName newE0 = (IDName) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theIDNames2 = substituted0 ? temp0 : theIDNames;

        if (theIDNames != theIDNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final NumberDeclaration result = new NumberDeclaration();
        result.theExp = theExp2;
        result.theIDNames = theIDNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitNumericLiteral(final NumericLiteral o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitObjectDeclaration(final ObjectDeclaration o) {
      assert o != null;
      boolean substituted = false;

      Name theSubtypeMark2 = null;
      {
        final Name theSubtypeMark = o.theSubtypeMark;

        theSubtypeMark2 = (Name) substitute(theSubtypeMark);

        if (theSubtypeMark != theSubtypeMark2) {
          substituted = true;
        }
      }

      Exp theOptionalInitializingExp2 = null;
      {
        final Exp theOptionalInitializingExp = o.theOptionalInitializingExp;

        theOptionalInitializingExp2 = (Exp) substitute(theOptionalInitializingExp);

        if (theOptionalInitializingExp != theOptionalInitializingExp2) {
          substituted = true;
        }
      }

      ArrayList<IDName> theDefiningIdentifierList2 = null;
      {
        final ArrayList<IDName> theDefiningIdentifierList = o.theDefiningIdentifierList;

        boolean substituted0 = false;
        final ArrayList<IDName> temp0 = new ArrayList<IDName>(
            theDefiningIdentifierList.size());
        for (final IDName e0 : theDefiningIdentifierList) {
          final IDName newE0 = (IDName) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theDefiningIdentifierList2 = substituted0 ? temp0
            : theDefiningIdentifierList;

        if (theDefiningIdentifierList != theDefiningIdentifierList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ObjectDeclaration result = new ObjectDeclaration();
        result.theSubtypeMark = theSubtypeMark2;
        result.theOptionalInitializingExp = theOptionalInitializingExp2;
        result.theDefiningIdentifierList = theDefiningIdentifierList2;
        result.theConstantFlag = o.theConstantFlag;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitOrdinaryFixedPointDefinition(
        final OrdinaryFixedPointDefinition o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      Exp theLowRangeExp2 = null;
      {
        final Exp theLowRangeExp = o.theLowRangeExp;

        theLowRangeExp2 = (Exp) substitute(theLowRangeExp);

        if (theLowRangeExp != theLowRangeExp2) {
          substituted = true;
        }
      }

      Exp theHighRangeExp2 = null;
      {
        final Exp theHighRangeExp = o.theHighRangeExp;

        theHighRangeExp2 = (Exp) substitute(theHighRangeExp);

        if (theHighRangeExp != theHighRangeExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final OrdinaryFixedPointDefinition result = new OrdinaryFixedPointDefinition();
        result.theExp = theExp2;
        result.theLowRangeExp = theLowRangeExp2;
        result.theHighRangeExp = theHighRangeExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitOwnVariable(final OwnVariable o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitOwnVariableSpecification(
        final OwnVariableSpecification o) {
      assert o != null;
      boolean substituted = false;

      Name theOptionalSubtypeMark2 = null;
      {
        final Name theOptionalSubtypeMark = o.theOptionalSubtypeMark;

        theOptionalSubtypeMark2 = (Name) substitute(theOptionalSubtypeMark);

        if (theOptionalSubtypeMark != theOptionalSubtypeMark2) {
          substituted = true;
        }
      }

      ArrayList<OwnVariable> theOwnVariables2 = null;
      {
        final ArrayList<OwnVariable> theOwnVariables = o.theOwnVariables;

        boolean substituted0 = false;
        final ArrayList<OwnVariable> temp0 = new ArrayList<OwnVariable>(
            theOwnVariables.size());
        for (final OwnVariable e0 : theOwnVariables) {
          final OwnVariable newE0 = (OwnVariable) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theOwnVariables2 = substituted0 ? temp0 : theOwnVariables;

        if (theOwnVariables != theOwnVariables2) {
          substituted = true;
        }
      }

      if (substituted) {
        final OwnVariableSpecification result = new OwnVariableSpecification();
        result.theOptionalSubtypeMark = theOptionalSubtypeMark2;
        result.theOwnVariables = theOwnVariables2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPackageAnnotation(final PackageAnnotation o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<OwnVariableSpecification> theOptionalOwnVariables2 = null;
      {
        final ArrayList<OwnVariableSpecification> theOptionalOwnVariables = o.theOptionalOwnVariables;

        if (theOptionalOwnVariables != null) {
          boolean substituted0 = false;
          final ArrayList<OwnVariableSpecification> temp0 = new ArrayList<OwnVariableSpecification>(
              theOptionalOwnVariables.size());
          for (final OwnVariableSpecification e0 : theOptionalOwnVariables) {
            final OwnVariableSpecification newE0 = (OwnVariableSpecification) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalOwnVariables2 = substituted0 ? temp0
              : theOptionalOwnVariables;
        }

        if (theOptionalOwnVariables != theOptionalOwnVariables2) {
          substituted = true;
        }
      }

      ArrayList<OwnVariable> theOptionalInitializeVariables2 = null;
      {
        final ArrayList<OwnVariable> theOptionalInitializeVariables = o.theOptionalInitializeVariables;

        if (theOptionalInitializeVariables != null) {
          boolean substituted0 = false;
          final ArrayList<OwnVariable> temp0 = new ArrayList<OwnVariable>(
              theOptionalInitializeVariables.size());
          for (final OwnVariable e0 : theOptionalInitializeVariables) {
            final OwnVariable newE0 = (OwnVariable) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalInitializeVariables2 = substituted0 ? temp0
              : theOptionalInitializeVariables;
        }

        if (theOptionalInitializeVariables != theOptionalInitializeVariables2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PackageAnnotation result = new PackageAnnotation();
        result.theOptionalOwnVariables = theOptionalOwnVariables2;
        result.theOptionalInitializeVariables = theOptionalInitializeVariables2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theOptionalOwnVariablesRegionSelection = o.theOptionalOwnVariablesRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPackageBody(final PackageBody o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      PackageImplementation thePackageImplementation2 = null;
      {
        final PackageImplementation thePackageImplementation = o.thePackageImplementation;

        thePackageImplementation2 = (PackageImplementation) substitute(thePackageImplementation);

        if (thePackageImplementation != thePackageImplementation2) {
          substituted = true;
        }
      }

      ArrayList<RefinementClause> theRefinementClauses2 = null;
      {
        final ArrayList<RefinementClause> theRefinementClauses = o.theRefinementClauses;

        boolean substituted0 = false;
        final ArrayList<RefinementClause> temp0 = new ArrayList<RefinementClause>(
            theRefinementClauses.size());
        for (final RefinementClause e0 : theRefinementClauses) {
          final RefinementClause newE0 = (RefinementClause) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theRefinementClauses2 = substituted0 ? temp0 : theRefinementClauses;

        if (theRefinementClauses != theRefinementClauses2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PackageBody result = new PackageBody();
        result.theName = theName2;
        result.thePackageImplementation = thePackageImplementation2;
        result.theRefinementClauses = theRefinementClauses2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theOptionalRefinementClausesRegionSelection = o.theOptionalRefinementClausesRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPackageBodyStub(final PackageBodyStub o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitPackageDeclaration(final PackageDeclaration o) {
      assert o != null;
      boolean substituted = false;

      PackageSpecification thePackageSpecification2 = null;
      {
        final PackageSpecification thePackageSpecification = o.thePackageSpecification;

        thePackageSpecification2 = (PackageSpecification) substitute(thePackageSpecification);

        if (thePackageSpecification != thePackageSpecification2) {
          substituted = true;
        }
      }

      ArrayList<Name> theOptionalInheritClauses2 = null;
      {
        final ArrayList<Name> theOptionalInheritClauses = o.theOptionalInheritClauses;

        if (theOptionalInheritClauses != null) {
          boolean substituted0 = false;
          final ArrayList<Name> temp0 = new ArrayList<Name>(
              theOptionalInheritClauses.size());
          for (final Name e0 : theOptionalInheritClauses) {
            final Name newE0 = (Name) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalInheritClauses2 = substituted0 ? temp0
              : theOptionalInheritClauses;
        }

        if (theOptionalInheritClauses != theOptionalInheritClauses2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PackageDeclaration result = new PackageDeclaration();
        result.thePackageSpecification = thePackageSpecification2;
        result.theOptionalInheritClauses = theOptionalInheritClauses2;
        result.thePrivateFlag = o.thePrivateFlag;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPackageImplementation(final PackageImplementation o) {
      assert o != null;
      boolean substituted = false;

      DeclarativePart theDeclarativePart2 = null;
      {
        final DeclarativePart theDeclarativePart = o.theDeclarativePart;

        theDeclarativePart2 = (DeclarativePart) substitute(theDeclarativePart);

        if (theDeclarativePart != theDeclarativePart2) {
          substituted = true;
        }
      }

      StatementList theOptionalStatementList2 = null;
      {
        final StatementList theOptionalStatementList = o.theOptionalStatementList;

        theOptionalStatementList2 = (StatementList) substitute(theOptionalStatementList);

        if (theOptionalStatementList != theOptionalStatementList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PackageImplementation result = new PackageImplementation();
        result.theDeclarativePart = theDeclarativePart2;
        result.theOptionalStatementList = theOptionalStatementList2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPackageRenamingDeclaration(
        final PackageRenamingDeclaration o) {
      assert o != null;
      boolean substituted = false;

      Name thePackageName2 = null;
      {
        final Name thePackageName = o.thePackageName;

        thePackageName2 = (Name) substitute(thePackageName);

        if (thePackageName != thePackageName2) {
          substituted = true;
        }
      }

      Name theRenamedName2 = null;
      {
        final Name theRenamedName = o.theRenamedName;

        theRenamedName2 = (Name) substitute(theRenamedName);

        if (theRenamedName != theRenamedName2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PackageRenamingDeclaration result = new PackageRenamingDeclaration();
        result.thePackageName = thePackageName2;
        result.theRenamedName = theRenamedName2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPackageSpecification(final PackageSpecification o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      PackageAnnotation thePackageAnnotation2 = null;
      {
        final PackageAnnotation thePackageAnnotation = o.thePackageAnnotation;

        thePackageAnnotation2 = (PackageAnnotation) substitute(thePackageAnnotation);

        if (thePackageAnnotation != thePackageAnnotation2) {
          substituted = true;
        }
      }

      ArrayList<RenamingDeclaration> theOptionalVisiblePartDeclaration2 = null;
      {
        final ArrayList<RenamingDeclaration> theOptionalVisiblePartDeclaration = o.theOptionalVisiblePartDeclaration;

        if (theOptionalVisiblePartDeclaration != null) {
          boolean substituted0 = false;
          final ArrayList<RenamingDeclaration> temp0 = new ArrayList<RenamingDeclaration>(
              theOptionalVisiblePartDeclaration.size());
          for (final RenamingDeclaration e0 : theOptionalVisiblePartDeclaration) {
            final RenamingDeclaration newE0 = (RenamingDeclaration) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalVisiblePartDeclaration2 = substituted0 ? temp0
              : theOptionalVisiblePartDeclaration;
        }

        if (theOptionalVisiblePartDeclaration != theOptionalVisiblePartDeclaration2) {
          substituted = true;
        }
      }

      ArrayList<DeclarativePartMember> theOptionalVisiblePartDeclarativePartMember2 = null;
      {
        final ArrayList<DeclarativePartMember> theOptionalVisiblePartDeclarativePartMember = o.theOptionalVisiblePartDeclarativePartMember;

        if (theOptionalVisiblePartDeclarativePartMember != null) {
          boolean substituted0 = false;
          final ArrayList<DeclarativePartMember> temp0 = new ArrayList<DeclarativePartMember>(
              theOptionalVisiblePartDeclarativePartMember.size());
          for (final DeclarativePartMember e0 : theOptionalVisiblePartDeclarativePartMember) {
            final DeclarativePartMember newE0 = (DeclarativePartMember) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalVisiblePartDeclarativePartMember2 = substituted0 ? temp0
              : theOptionalVisiblePartDeclarativePartMember;
        }

        if (theOptionalVisiblePartDeclarativePartMember != theOptionalVisiblePartDeclarativePartMember2) {
          substituted = true;
        }
      }

      ArrayList<RenamingDeclaration> theOptionalPrivatePartDeclaration2 = null;
      {
        final ArrayList<RenamingDeclaration> theOptionalPrivatePartDeclaration = o.theOptionalPrivatePartDeclaration;

        if (theOptionalPrivatePartDeclaration != null) {
          boolean substituted0 = false;
          final ArrayList<RenamingDeclaration> temp0 = new ArrayList<RenamingDeclaration>(
              theOptionalPrivatePartDeclaration.size());
          for (final RenamingDeclaration e0 : theOptionalPrivatePartDeclaration) {
            final RenamingDeclaration newE0 = (RenamingDeclaration) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalPrivatePartDeclaration2 = substituted0 ? temp0
              : theOptionalPrivatePartDeclaration;
        }

        if (theOptionalPrivatePartDeclaration != theOptionalPrivatePartDeclaration2) {
          substituted = true;
        }
      }

      ArrayList<DeclarativePartMember> theOptionalPrivatePartDeclarativePartMember2 = null;
      {
        final ArrayList<DeclarativePartMember> theOptionalPrivatePartDeclarativePartMember = o.theOptionalPrivatePartDeclarativePartMember;

        if (theOptionalPrivatePartDeclarativePartMember != null) {
          boolean substituted0 = false;
          final ArrayList<DeclarativePartMember> temp0 = new ArrayList<DeclarativePartMember>(
              theOptionalPrivatePartDeclarativePartMember.size());
          for (final DeclarativePartMember e0 : theOptionalPrivatePartDeclarativePartMember) {
            final DeclarativePartMember newE0 = (DeclarativePartMember) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalPrivatePartDeclarativePartMember2 = substituted0 ? temp0
              : theOptionalPrivatePartDeclarativePartMember;
        }

        if (theOptionalPrivatePartDeclarativePartMember != theOptionalPrivatePartDeclarativePartMember2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PackageSpecification result = new PackageSpecification();
        result.theName = theName2;
        result.thePackageAnnotation = thePackageAnnotation2;
        result.theOptionalVisiblePartDeclaration = theOptionalVisiblePartDeclaration2;
        result.theOptionalVisiblePartDeclarativePartMember = theOptionalVisiblePartDeclarativePartMember2;
        result.theOptionalPrivatePartDeclaration = theOptionalPrivatePartDeclaration2;
        result.theOptionalPrivatePartDeclarativePartMember = theOptionalPrivatePartDeclarativePartMember2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitParameterSpecification(final ParameterSpecification o) {
      assert o != null;
      boolean substituted = false;

      Name theSubtypeMark2 = null;
      {
        final Name theSubtypeMark = o.theSubtypeMark;

        theSubtypeMark2 = (Name) substitute(theSubtypeMark);

        if (theSubtypeMark != theSubtypeMark2) {
          substituted = true;
        }
      }

      ArrayList<IDName> theParameterNames2 = null;
      {
        final ArrayList<IDName> theParameterNames = o.theParameterNames;

        boolean substituted0 = false;
        final ArrayList<IDName> temp0 = new ArrayList<IDName>(
            theParameterNames.size());
        for (final IDName e0 : theParameterNames) {
          final IDName newE0 = (IDName) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theParameterNames2 = substituted0 ? temp0 : theParameterNames;

        if (theParameterNames != theParameterNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ParameterSpecification result = new ParameterSpecification();
        result.theSubtypeMark = theSubtypeMark2;
        result.theParameterNames = theParameterNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theMode = o.theMode;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitParenExp(final ParenExp o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ParenExp result = new ParenExp();
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPositionalArrayAggregate(
        final PositionalArrayAggregate o) {
      assert o != null;
      boolean substituted = false;

      AggregateItem theOptionalOthersAggregateItem2 = null;
      {
        final AggregateItem theOptionalOthersAggregateItem = o.theOptionalOthersAggregateItem;

        theOptionalOthersAggregateItem2 = (AggregateItem) substitute(theOptionalOthersAggregateItem);

        if (theOptionalOthersAggregateItem != theOptionalOthersAggregateItem2) {
          substituted = true;
        }
      }

      ArrayList<AggregateItem> theAggregateItems2 = null;
      {
        final ArrayList<AggregateItem> theAggregateItems = o.theAggregateItems;

        boolean substituted0 = false;
        final ArrayList<AggregateItem> temp0 = new ArrayList<AggregateItem>(
            theAggregateItems.size());
        for (final AggregateItem e0 : theAggregateItems) {
          final AggregateItem newE0 = (AggregateItem) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theAggregateItems2 = substituted0 ? temp0 : theAggregateItems;

        if (theAggregateItems != theAggregateItems2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PositionalArrayAggregate result = new PositionalArrayAggregate();
        result.theOptionalOthersAggregateItem = theOptionalOthersAggregateItem2;
        result.theAggregateItems = theAggregateItems2;
        result.theOthersFlag = o.theOthersFlag;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPositionalParameterAssociationList(
        final PositionalParameterAssociationList o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Exp> theParameterAssociations2 = null;
      {
        final ArrayList<Exp> theParameterAssociations = o.theParameterAssociations;

        boolean substituted0 = false;
        final ArrayList<Exp> temp0 = new ArrayList<Exp>(
            theParameterAssociations.size());
        for (final Exp e0 : theParameterAssociations) {
          final Exp newE0 = (Exp) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theParameterAssociations2 = substituted0 ? temp0
            : theParameterAssociations;

        if (theParameterAssociations != theParameterAssociations2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PositionalParameterAssociationList result = new PositionalParameterAssociationList();
        result.theParameterAssociations = theParameterAssociations2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPositionalRecordAggregate(
        final PositionalRecordAggregate o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Exp> theExps2 = null;
      {
        final ArrayList<Exp> theExps = o.theExps;

        boolean substituted0 = false;
        final ArrayList<Exp> temp0 = new ArrayList<Exp>(theExps.size());
        for (final Exp e0 : theExps) {
          final Exp newE0 = (Exp) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theExps2 = substituted0 ? temp0 : theExps;

        if (theExps != theExps2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PositionalRecordAggregate result = new PositionalRecordAggregate();
        result.theExps = theExps2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPositionalRecordExtensionAggregate(
        final PositionalRecordExtensionAggregate o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      ArrayList<Exp> theExps2 = null;
      {
        final ArrayList<Exp> theExps = o.theExps;

        boolean substituted0 = false;
        final ArrayList<Exp> temp0 = new ArrayList<Exp>(theExps.size());
        for (final Exp e0 : theExps) {
          final Exp newE0 = (Exp) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theExps2 = substituted0 ? temp0 : theExps;

        if (theExps != theExps2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PositionalRecordExtensionAggregate result = new PositionalRecordExtensionAggregate();
        result.theExp = theExp2;
        result.theExps = theExps2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPostcondition(final Postcondition o) {
      assert o != null;
      boolean substituted = false;

      Predicate thePredicate2 = null;
      {
        final Predicate thePredicate = o.thePredicate;

        thePredicate2 = (Predicate) substitute(thePredicate);

        if (thePredicate != thePredicate2) {
          substituted = true;
        }
      }

      if (substituted) {
        final Postcondition result = new Postcondition();
        result.thePredicate = thePredicate2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPragma(final Pragma o) {
      assert o != null;
      boolean substituted = false;

      Object dummyObjectToGetListVisitor2 = null;
      {
        final Object dummyObjectToGetListVisitor = o.dummyObjectToGetListVisitor;

        dummyObjectToGetListVisitor2 = substitute(dummyObjectToGetListVisitor);

        if (dummyObjectToGetListVisitor != dummyObjectToGetListVisitor2) {
          substituted = true;
        }
      }

      ArrayList<PragmaArgumentAssociation> theOptionalPragmaArgumentAssociations2 = null;
      {
        final ArrayList<PragmaArgumentAssociation> theOptionalPragmaArgumentAssociations = o.theOptionalPragmaArgumentAssociations;

        if (theOptionalPragmaArgumentAssociations != null) {
          boolean substituted0 = false;
          final ArrayList<PragmaArgumentAssociation> temp0 = new ArrayList<PragmaArgumentAssociation>(
              theOptionalPragmaArgumentAssociations.size());
          for (final PragmaArgumentAssociation e0 : theOptionalPragmaArgumentAssociations) {
            final PragmaArgumentAssociation newE0 = (PragmaArgumentAssociation) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalPragmaArgumentAssociations2 = substituted0 ? temp0
              : theOptionalPragmaArgumentAssociations;
        }

        if (theOptionalPragmaArgumentAssociations != theOptionalPragmaArgumentAssociations2) {
          substituted = true;
        }
      }

      if (substituted) {
        final Pragma result = new Pragma();
        result.dummyObjectToGetListVisitor = dummyObjectToGetListVisitor2;
        result.theOptionalPragmaArgumentAssociations = theOptionalPragmaArgumentAssociations2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPrecondition(final Precondition o) {
      assert o != null;
      boolean substituted = false;

      Predicate thePredicate2 = null;
      {
        final Predicate thePredicate = o.thePredicate;

        thePredicate2 = (Predicate) substitute(thePredicate);

        if (thePredicate != thePredicate2) {
          substituted = true;
        }
      }

      if (substituted) {
        final Precondition result = new Precondition();
        result.thePredicate = thePredicate2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPredicate(final Predicate o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final Predicate result = new Predicate();
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPrivateExtensionDeclaration(
        final PrivateExtensionDeclaration o) {
      assert o != null;
      boolean substituted = false;

      SubTypeIndication theSubTypeIndication2 = null;
      {
        final SubTypeIndication theSubTypeIndication = o.theSubTypeIndication;

        theSubTypeIndication2 = (SubTypeIndication) substitute(theSubTypeIndication);

        if (theSubTypeIndication != theSubTypeIndication2) {
          substituted = true;
        }
      }

      if (substituted) {
        final PrivateExtensionDeclaration result = new PrivateExtensionDeclaration();
        result.theSubTypeIndication = theSubTypeIndication2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitPrivateTypeDeclaration(final PrivateTypeDeclaration o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitProcedureAnnotation(final ProcedureAnnotation o) {
      assert o != null;
      boolean substituted = false;

      GlobalDefinition theOptionalGlobalDefinition2 = null;
      {
        final GlobalDefinition theOptionalGlobalDefinition = o.theOptionalGlobalDefinition;

        theOptionalGlobalDefinition2 = (GlobalDefinition) substitute(theOptionalGlobalDefinition);

        if (theOptionalGlobalDefinition != theOptionalGlobalDefinition2) {
          substituted = true;
        }
      }

      DependencyRelation theOptionalDependency2 = null;
      {
        final DependencyRelation theOptionalDependency = o.theOptionalDependency;

        theOptionalDependency2 = (DependencyRelation) substitute(theOptionalDependency);

        if (theOptionalDependency != theOptionalDependency2) {
          substituted = true;
        }
      }

      Precondition theOptionalPrecondition2 = null;
      {
        final Precondition theOptionalPrecondition = o.theOptionalPrecondition;

        theOptionalPrecondition2 = (Precondition) substitute(theOptionalPrecondition);

        if (theOptionalPrecondition != theOptionalPrecondition2) {
          substituted = true;
        }
      }

      Postcondition theOptionalPostcondition2 = null;
      {
        final Postcondition theOptionalPostcondition = o.theOptionalPostcondition;

        theOptionalPostcondition2 = (Postcondition) substitute(theOptionalPostcondition);

        if (theOptionalPostcondition != theOptionalPostcondition2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ProcedureAnnotation result = new ProcedureAnnotation();
        result.theOptionalGlobalDefinition = theOptionalGlobalDefinition2;
        result.theOptionalDependency = theOptionalDependency2;
        result.theOptionalPrecondition = theOptionalPrecondition2;
        result.theOptionalPostcondition = theOptionalPostcondition2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitProcedureBodyStub(final ProcedureBodyStub o) {
      assert o != null;
      boolean substituted = false;

      ProcedureSpecification theProcedureSpecification2 = null;
      {
        final ProcedureSpecification theProcedureSpecification = o.theProcedureSpecification;

        theProcedureSpecification2 = (ProcedureSpecification) substitute(theProcedureSpecification);

        if (theProcedureSpecification != theProcedureSpecification2) {
          substituted = true;
        }
      }

      ProcedureAnnotation theOptionalProcedureAnnotation2 = null;
      {
        final ProcedureAnnotation theOptionalProcedureAnnotation = o.theOptionalProcedureAnnotation;

        theOptionalProcedureAnnotation2 = (ProcedureAnnotation) substitute(theOptionalProcedureAnnotation);

        if (theOptionalProcedureAnnotation != theOptionalProcedureAnnotation2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ProcedureBodyStub result = new ProcedureBodyStub();
        result.theProcedureSpecification = theProcedureSpecification2;
        result.theOptionalProcedureAnnotation = theOptionalProcedureAnnotation2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitProcedureCallStatement(final ProcedureCallStatement o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ParameterAssociationList theOptionalParameterAssociationList2 = null;
      {
        final ParameterAssociationList theOptionalParameterAssociationList = o.theOptionalParameterAssociationList;

        theOptionalParameterAssociationList2 = (ParameterAssociationList) substitute(theOptionalParameterAssociationList);

        if (theOptionalParameterAssociationList != theOptionalParameterAssociationList2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ProcedureCallStatement result = new ProcedureCallStatement();
        result.theName = theName2;
        result.theOptionalParameterAssociationList = theOptionalParameterAssociationList2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitProcedureSpecification(final ProcedureSpecification o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<ParameterSpecification> theOptionalParameterSpecification2 = null;
      {
        final ArrayList<ParameterSpecification> theOptionalParameterSpecification = o.theOptionalParameterSpecification;

        if (theOptionalParameterSpecification != null) {
          boolean substituted0 = false;
          final ArrayList<ParameterSpecification> temp0 = new ArrayList<ParameterSpecification>(
              theOptionalParameterSpecification.size());
          for (final ParameterSpecification e0 : theOptionalParameterSpecification) {
            final ParameterSpecification newE0 = (ParameterSpecification) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalParameterSpecification2 = substituted0 ? temp0
              : theOptionalParameterSpecification;
        }

        if (theOptionalParameterSpecification != theOptionalParameterSpecification2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ProcedureSpecification result = new ProcedureSpecification();
        result.theOptionalParameterSpecification = theOptionalParameterSpecification2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theMethodNameSelection = o.theMethodNameSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitProcedureSpecificationRenamingDeclaration(
        final ProcedureSpecificationRenamingDeclaration o) {
      assert o != null;
      boolean substituted = false;

      ProcedureSpecification theProcedureSpecification2 = null;
      {
        final ProcedureSpecification theProcedureSpecification = o.theProcedureSpecification;

        theProcedureSpecification2 = (ProcedureSpecification) substitute(theProcedureSpecification);

        if (theProcedureSpecification != theProcedureSpecification2) {
          substituted = true;
        }
      }

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ProcedureSpecificationRenamingDeclaration result = new ProcedureSpecificationRenamingDeclaration();
        result.theProcedureSpecification = theProcedureSpecification2;
        result.theName = theName2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitProcedureSubProgramBody(final ProcedureSubProgramBody o) {
      assert o != null;
      boolean substituted = false;

      SubProgramImplementation theSubProgramImplementation2 = null;
      {
        final SubProgramImplementation theSubProgramImplementation = o.theSubProgramImplementation;

        theSubProgramImplementation2 = (SubProgramImplementation) substitute(theSubProgramImplementation);

        if (theSubProgramImplementation != theSubProgramImplementation2) {
          substituted = true;
        }
      }

      ProcedureSpecification theProcedureSpecification2 = null;
      {
        final ProcedureSpecification theProcedureSpecification = o.theProcedureSpecification;

        theProcedureSpecification2 = (ProcedureSpecification) substitute(theProcedureSpecification);

        if (theProcedureSpecification != theProcedureSpecification2) {
          substituted = true;
        }
      }

      ProcedureAnnotation theOptionalProcedureAnnotation2 = null;
      {
        final ProcedureAnnotation theOptionalProcedureAnnotation = o.theOptionalProcedureAnnotation;

        theOptionalProcedureAnnotation2 = (ProcedureAnnotation) substitute(theOptionalProcedureAnnotation);

        if (theOptionalProcedureAnnotation != theOptionalProcedureAnnotation2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ProcedureSubProgramBody result = new ProcedureSubProgramBody();
        result.theSubProgramImplementation = theSubProgramImplementation2;
        result.theProcedureSpecification = theProcedureSpecification2;
        result.theOptionalProcedureAnnotation = theOptionalProcedureAnnotation2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitProcedureSubProgramDeclaration(
        final ProcedureSubProgramDeclaration o) {
      assert o != null;
      boolean substituted = false;

      ProcedureSpecification theProcedureSpecification2 = null;
      {
        final ProcedureSpecification theProcedureSpecification = o.theProcedureSpecification;

        theProcedureSpecification2 = (ProcedureSpecification) substitute(theProcedureSpecification);

        if (theProcedureSpecification != theProcedureSpecification2) {
          substituted = true;
        }
      }

      ProcedureAnnotation theProcedureAnnotation2 = null;
      {
        final ProcedureAnnotation theProcedureAnnotation = o.theProcedureAnnotation;

        theProcedureAnnotation2 = (ProcedureAnnotation) substitute(theProcedureAnnotation);

        if (theProcedureAnnotation != theProcedureAnnotation2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ProcedureSubProgramDeclaration result = new ProcedureSubProgramDeclaration();
        result.theProcedureSpecification = theProcedureSpecification2;
        result.theProcedureAnnotation = theProcedureAnnotation2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitProofFunctionDeclaration(
        final ProofFunctionDeclaration o) {
      assert o != null;
      boolean substituted = false;

      FunctionSpecification theFunctionSpecification2 = null;
      {
        final FunctionSpecification theFunctionSpecification = o.theFunctionSpecification;

        theFunctionSpecification2 = (FunctionSpecification) substitute(theFunctionSpecification);

        if (theFunctionSpecification != theFunctionSpecification2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ProofFunctionDeclaration result = new ProofFunctionDeclaration();
        result.theFunctionSpecification = theFunctionSpecification2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitProofTypeDeclaration(final ProofTypeDeclaration o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitQuantifiedExp(final QuantifiedExp o) {
      assert o != null;
      boolean substituted = false;

      Name theSubTypeMark2 = null;
      {
        final Name theSubTypeMark = o.theSubTypeMark;

        theSubTypeMark2 = (Name) substitute(theSubTypeMark);

        if (theSubTypeMark != theSubTypeMark2) {
          substituted = true;
        }
      }

      Range theOptionalRange2 = null;
      {
        final Range theOptionalRange = o.theOptionalRange;

        theOptionalRange2 = (Range) substitute(theOptionalRange);

        if (theOptionalRange != theOptionalRange2) {
          substituted = true;
        }
      }

      Predicate thePredicate2 = null;
      {
        final Predicate thePredicate = o.thePredicate;

        thePredicate2 = (Predicate) substitute(thePredicate);

        if (thePredicate != thePredicate2) {
          substituted = true;
        }
      }

      if (substituted) {
        final QuantifiedExp result = new QuantifiedExp();
        result.theSubTypeMark = theSubTypeMark2;
        result.theOptionalRange = theOptionalRange2;
        result.thePredicate = thePredicate2;
        result.theIdentifier = o.theIdentifier;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theKind = o.theKind;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRangeAttributeDesignator(
        final RangeAttributeDesignator o) {
      assert o != null;
      boolean substituted = false;

      Exp theOptionalExp2 = null;
      {
        final Exp theOptionalExp = o.theOptionalExp;

        theOptionalExp2 = (Exp) substitute(theOptionalExp);

        if (theOptionalExp != theOptionalExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RangeAttributeDesignator result = new RangeAttributeDesignator();
        result.theOptionalExp = theOptionalExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRangeAttributeReference(final RangeAttributeReference o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      RangeAttributeDesignator theDesignator2 = null;
      {
        final RangeAttributeDesignator theDesignator = o.theDesignator;

        theDesignator2 = (RangeAttributeDesignator) substitute(theDesignator);

        if (theDesignator != theDesignator2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RangeAttributeReference result = new RangeAttributeReference();
        result.theName = theName2;
        result.theDesignator = theDesignator2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRangeChoice(final RangeChoice o) {
      assert o != null;
      boolean substituted = false;

      Range theRange2 = null;
      {
        final Range theRange = o.theRange;

        theRange2 = (Range) substitute(theRange);

        if (theRange != theRange2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RangeChoice result = new RangeChoice();
        result.theRange = theRange2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRangeConstraint(final RangeConstraint o) {
      assert o != null;
      boolean substituted = false;

      Range theRange2 = null;
      {
        final Range theRange = o.theRange;

        theRange2 = (Range) substitute(theRange);

        if (theRange != theRange2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RangeConstraint result = new RangeConstraint();
        result.theRange = theRange2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRecordComponentAssociation(
        final RecordComponentAssociation o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RecordComponentAssociation result = new RecordComponentAssociation();
        result.theExp = theExp2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRecordDefinition(final RecordDefinition o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<ComponentDeclaration> theOptionalComponentDeclarations2 = null;
      {
        final ArrayList<ComponentDeclaration> theOptionalComponentDeclarations = o.theOptionalComponentDeclarations;

        if (theOptionalComponentDeclarations != null) {
          boolean substituted0 = false;
          final ArrayList<ComponentDeclaration> temp0 = new ArrayList<ComponentDeclaration>(
              theOptionalComponentDeclarations.size());
          for (final ComponentDeclaration e0 : theOptionalComponentDeclarations) {
            final ComponentDeclaration newE0 = (ComponentDeclaration) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalComponentDeclarations2 = substituted0 ? temp0
              : theOptionalComponentDeclarations;
        }

        if (theOptionalComponentDeclarations != theOptionalComponentDeclarations2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RecordDefinition result = new RecordDefinition();
        result.theOptionalComponentDeclarations = theOptionalComponentDeclarations2;
        result.theNullRecordFlag = o.theNullRecordFlag;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRecordRepresentationClause(
        final RecordRepresentationClause o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Exp theOptionalExp2 = null;
      {
        final Exp theOptionalExp = o.theOptionalExp;

        theOptionalExp2 = (Exp) substitute(theOptionalExp);

        if (theOptionalExp != theOptionalExp2) {
          substituted = true;
        }
      }

      ArrayList<ComponentClause> theOptionalComponentClauses2 = null;
      {
        final ArrayList<ComponentClause> theOptionalComponentClauses = o.theOptionalComponentClauses;

        if (theOptionalComponentClauses != null) {
          boolean substituted0 = false;
          final ArrayList<ComponentClause> temp0 = new ArrayList<ComponentClause>(
              theOptionalComponentClauses.size());
          for (final ComponentClause e0 : theOptionalComponentClauses) {
            final ComponentClause newE0 = (ComponentClause) substitute(e0);
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalComponentClauses2 = substituted0 ? temp0
              : theOptionalComponentClauses;
        }

        if (theOptionalComponentClauses != theOptionalComponentClauses2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RecordRepresentationClause result = new RecordRepresentationClause();
        result.theName = theName2;
        result.theOptionalExp = theOptionalExp2;
        result.theOptionalComponentClauses = theOptionalComponentClauses2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRecordTypeDefinition(final RecordTypeDefinition o) {
      assert o != null;
      boolean substituted = false;

      RecordDefinition theRecordDefinition2 = null;
      {
        final RecordDefinition theRecordDefinition = o.theRecordDefinition;

        theRecordDefinition2 = (RecordDefinition) substitute(theRecordDefinition);

        if (theRecordDefinition != theRecordDefinition2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RecordTypeDefinition result = new RecordTypeDefinition();
        result.theRecordDefinition = theRecordDefinition2;
        result.theTaggedFlag = o.theTaggedFlag;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRecordTypeExtension(final RecordTypeExtension o) {
      assert o != null;
      boolean substituted = false;

      Name theSubtypeMark2 = null;
      {
        final Name theSubtypeMark = o.theSubtypeMark;

        theSubtypeMark2 = (Name) substitute(theSubtypeMark);

        if (theSubtypeMark != theSubtypeMark2) {
          substituted = true;
        }
      }

      RecordDefinition theRecordDefinition2 = null;
      {
        final RecordDefinition theRecordDefinition = o.theRecordDefinition;

        theRecordDefinition2 = (RecordDefinition) substitute(theRecordDefinition);

        if (theRecordDefinition != theRecordDefinition2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RecordTypeExtension result = new RecordTypeExtension();
        result.theSubtypeMark = theSubtypeMark2;
        result.theRecordDefinition = theRecordDefinition2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRecordUpdate(final RecordUpdate o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ArrayList<Pair<String, Exp>> theSelectorToExpressionList2 = null;
      {
        final ArrayList<Pair<String, Exp>> theSelectorToExpressionList = o.theSelectorToExpressionList;

        boolean substituted0 = false;
        final ArrayList<Pair<String, Exp>> temp0 = new ArrayList<Pair<String, Exp>>(
            theSelectorToExpressionList.size());
        for (final Pair<String, Exp> e0 : theSelectorToExpressionList) {
          Pair<String, Exp> newE0 = null;
          final String pe1_0 = e0.getE0();
          final Exp pe1_1 = e0.getE1();
          final String newPe1_0 = pe1_0;
          final Exp newPe1_1 = (Exp) substitute(pe1_1);
          if ((newPe1_0 != pe1_0) || (newPe1_1 != pe1_1)) {
            newE0 = new Pair<String, Exp>(newPe1_0, newPe1_1);
          } else {
            newE0 = e0;
          }

          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theSelectorToExpressionList2 = substituted0 ? temp0
            : theSelectorToExpressionList;

        if (theSelectorToExpressionList != theSelectorToExpressionList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RecordUpdate result = new RecordUpdate();
        result.theName = theName2;
        result.theSelectorToExpressionList = theSelectorToExpressionList2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRefinementClause(final RefinementClause o) {
      assert o != null;
      boolean substituted = false;

      IDName theSubject2 = null;
      {
        final IDName theSubject = o.theSubject;

        theSubject2 = (IDName) substitute(theSubject);

        if (theSubject != theSubject2) {
          substituted = true;
        }
      }

      ArrayList<Constituent> theConstituents2 = null;
      {
        final ArrayList<Constituent> theConstituents = o.theConstituents;

        boolean substituted0 = false;
        final ArrayList<Constituent> temp0 = new ArrayList<Constituent>(
            theConstituents.size());
        for (final Constituent e0 : theConstituents) {
          final Constituent newE0 = (Constituent) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theConstituents2 = substituted0 ? temp0 : theConstituents;

        if (theConstituents != theConstituents2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RefinementClause result = new RefinementClause();
        result.theSubject = theSubject2;
        result.theConstituents = theConstituents2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRenamingDeclarationEmbeddedPackageDeclarationMember(
        final RenamingDeclarationEmbeddedPackageDeclarationMember o) {
      assert o != null;
      boolean substituted = false;

      RenamingDeclaration theRenamingDeclaration2 = null;
      {
        final RenamingDeclaration theRenamingDeclaration = o.theRenamingDeclaration;

        theRenamingDeclaration2 = (RenamingDeclaration) substitute(theRenamingDeclaration);

        if (theRenamingDeclaration != theRenamingDeclaration2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RenamingDeclarationEmbeddedPackageDeclarationMember result = new RenamingDeclarationEmbeddedPackageDeclarationMember();
        result.theRenamingDeclaration = theRenamingDeclaration2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitRepresentationClauseBasicDeclarativeItem(
        final RepresentationClauseBasicDeclarativeItem o) {
      assert o != null;
      boolean substituted = false;

      RepresentationClause theRepresentationClause2 = null;
      {
        final RepresentationClause theRepresentationClause = o.theRepresentationClause;

        theRepresentationClause2 = (RepresentationClause) substitute(theRepresentationClause);

        if (theRepresentationClause != theRepresentationClause2) {
          substituted = true;
        }
      }

      if (substituted) {
        final RepresentationClauseBasicDeclarativeItem result = new RepresentationClauseBasicDeclarativeItem();
        result.theRepresentationClause = theRepresentationClause2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitReturnAnnotationExp(final ReturnAnnotationExp o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ReturnAnnotationExp result = new ReturnAnnotationExp();
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitReturnAnnotationPred(final ReturnAnnotationPred o) {
      assert o != null;
      boolean substituted = false;

      Predicate thePredicate2 = null;
      {
        final Predicate thePredicate = o.thePredicate;

        thePredicate2 = (Predicate) substitute(thePredicate);

        if (thePredicate != thePredicate2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ReturnAnnotationPred result = new ReturnAnnotationPred();
        result.thePredicate = thePredicate2;
        result.theID = o.theID;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitReturnStatement(final ReturnStatement o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final ReturnStatement result = new ReturnStatement();
        result.theExp = theExp2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitSelectedComponent(final SelectedComponent o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      if (substituted) {
        final SelectedComponent result = new SelectedComponent();
        result.theName = theName2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theDecoratedFlag = o.theDecoratedFlag;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitSignedIntegerTypeDefinition(
        final SignedIntegerTypeDefinition o) {
      assert o != null;
      boolean substituted = false;

      Exp theLowRangeExp2 = null;
      {
        final Exp theLowRangeExp = o.theLowRangeExp;

        theLowRangeExp2 = (Exp) substitute(theLowRangeExp);

        if (theLowRangeExp != theLowRangeExp2) {
          substituted = true;
        }
      }

      Exp theHighRangeExp2 = null;
      {
        final Exp theHighRangeExp = o.theHighRangeExp;

        theHighRangeExp2 = (Exp) substitute(theHighRangeExp);

        if (theHighRangeExp != theHighRangeExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final SignedIntegerTypeDefinition result = new SignedIntegerTypeDefinition();
        result.theLowRangeExp = theLowRangeExp2;
        result.theHighRangeExp = theHighRangeExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitStatementList(final StatementList o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Statement> theStatements2 = null;
      {
        final ArrayList<Statement> theStatements = o.theStatements;

        boolean substituted0 = false;
        final ArrayList<Statement> temp0 = new ArrayList<Statement>(
            theStatements.size());
        for (final Statement e0 : theStatements) {
          final Statement newE0 = (Statement) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theStatements2 = substituted0 ? temp0 : theStatements;

        if (theStatements != theStatements2) {
          substituted = true;
        }
      }

      if (substituted) {
        final StatementList result = new StatementList();
        result.theStatements = theStatements2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitStatementSubProgramImplementation(
        final StatementSubProgramImplementation o) {
      assert o != null;
      boolean substituted = false;

      DeclarativePart theDeclarativePart2 = null;
      {
        final DeclarativePart theDeclarativePart = o.theDeclarativePart;

        theDeclarativePart2 = (DeclarativePart) substitute(theDeclarativePart);

        if (theDeclarativePart != theDeclarativePart2) {
          substituted = true;
        }
      }

      StatementList theStatementList2 = null;
      {
        final StatementList theStatementList = o.theStatementList;

        theStatementList2 = (StatementList) substitute(theStatementList);

        if (theStatementList != theStatementList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final StatementSubProgramImplementation result = new StatementSubProgramImplementation();
        result.theDeclarativePart = theDeclarativePart2;
        result.theStatementList = theStatementList2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitStringLiteral(final StringLiteral o) {
      assert o != null;
      this.g.pushResult(o);
    }

    @Override
    protected void visitSubTypeChoice(final SubTypeChoice o) {
      assert o != null;
      boolean substituted = false;

      SubTypeIndication theSubTypeIndication2 = null;
      {
        final SubTypeIndication theSubTypeIndication = o.theSubTypeIndication;

        theSubTypeIndication2 = (SubTypeIndication) substitute(theSubTypeIndication);

        if (theSubTypeIndication != theSubTypeIndication2) {
          substituted = true;
        }
      }

      if (substituted) {
        final SubTypeChoice result = new SubTypeChoice();
        result.theSubTypeIndication = theSubTypeIndication2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitSubTypeDeclaration(final SubTypeDeclaration o) {
      assert o != null;
      boolean substituted = false;

      SubTypeIndication theSubTypeIndication2 = null;
      {
        final SubTypeIndication theSubTypeIndication = o.theSubTypeIndication;

        theSubTypeIndication2 = (SubTypeIndication) substitute(theSubTypeIndication);

        if (theSubTypeIndication != theSubTypeIndication2) {
          substituted = true;
        }
      }

      if (substituted) {
        final SubTypeDeclaration result = new SubTypeDeclaration();
        result.theSubTypeIndication = theSubTypeIndication2;
        result.theIDString = o.theIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitSubTypeIndication(final SubTypeIndication o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Constraint theOptionalConstraint2 = null;
      {
        final Constraint theOptionalConstraint = o.theOptionalConstraint;

        theOptionalConstraint2 = (Constraint) substitute(theOptionalConstraint);

        if (theOptionalConstraint != theOptionalConstraint2) {
          substituted = true;
        }
      }

      if (substituted) {
        final SubTypeIndication result = new SubTypeIndication();
        result.theName = theName2;
        result.theOptionalConstraint = theOptionalConstraint2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitSubUnitCompilationUnit(final SubUnitCompilationUnit o) {
      assert o != null;
      boolean substituted = false;

      ContextClause theContextClause2 = null;
      {
        final ContextClause theContextClause = o.theContextClause;

        theContextClause2 = (ContextClause) substitute(theContextClause);

        if (theContextClause != theContextClause2) {
          substituted = true;
        }
      }

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      ProperBody theProperBody2 = null;
      {
        final ProperBody theProperBody = o.theProperBody;

        theProperBody2 = (ProperBody) substitute(theProperBody);

        if (theProperBody != theProperBody2) {
          substituted = true;
        }
      }

      if (substituted) {
        final SubUnitCompilationUnit result = new SubUnitCompilationUnit();
        result.theContextClause = theContextClause2;
        result.theName = theName2;
        result.theProperBody = theProperBody2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitTypeAssertion(final TypeAssertion o) {
      assert o != null;
      boolean substituted = false;

      Name theBase2 = null;
      {
        final Name theBase = o.theBase;

        theBase2 = (Name) substitute(theBase);

        if (theBase != theBase2) {
          substituted = true;
        }
      }

      Name theSubtypeMark2 = null;
      {
        final Name theSubtypeMark = o.theSubtypeMark;

        theSubtypeMark2 = (Name) substitute(theSubtypeMark);

        if (theSubtypeMark != theSubtypeMark2) {
          substituted = true;
        }
      }

      if (substituted) {
        final TypeAssertion result = new TypeAssertion();
        result.theBase = theBase2;
        result.theSubtypeMark = theSubtypeMark2;
        result.theIdentifier = o.theIdentifier;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitTypeConversion(final TypeConversion o) {
      assert o != null;
      boolean substituted = false;

      Name theName2 = null;
      {
        final Name theName = o.theName;

        theName2 = (Name) substitute(theName);

        if (theName != theName2) {
          substituted = true;
        }
      }

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final TypeConversion result = new TypeConversion();
        result.theName = theName2;
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitUnaryExp(final UnaryExp o) {
      assert o != null;
      boolean substituted = false;

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      if (substituted) {
        final UnaryExp result = new UnaryExp();
        result.theExp = theExp2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theUnaryOp = o.theUnaryOp;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitUnconstrainedArrayDefinition(
        final UnconstrainedArrayDefinition o) {
      assert o != null;
      boolean substituted = false;

      Name theComponentName2 = null;
      {
        final Name theComponentName = o.theComponentName;

        theComponentName2 = (Name) substitute(theComponentName);

        if (theComponentName != theComponentName2) {
          substituted = true;
        }
      }

      ArrayList<Name> theIndexSubTypeNames2 = null;
      {
        final ArrayList<Name> theIndexSubTypeNames = o.theIndexSubTypeNames;

        boolean substituted0 = false;
        final ArrayList<Name> temp0 = new ArrayList<Name>(
            theIndexSubTypeNames.size());
        for (final Name e0 : theIndexSubTypeNames) {
          final Name newE0 = (Name) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theIndexSubTypeNames2 = substituted0 ? temp0 : theIndexSubTypeNames;

        if (theIndexSubTypeNames != theIndexSubTypeNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final UnconstrainedArrayDefinition result = new UnconstrainedArrayDefinition();
        result.theComponentName = theComponentName2;
        result.theIndexSubTypeNames = theIndexSubTypeNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitUseTypeClause(final UseTypeClause o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Name> theNames2 = null;
      {
        final ArrayList<Name> theNames = o.theNames;

        boolean substituted0 = false;
        final ArrayList<Name> temp0 = new ArrayList<Name>(theNames.size());
        for (final Name e0 : theNames) {
          final Name newE0 = (Name) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theNames2 = substituted0 ? temp0 : theNames;

        if (theNames != theNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final UseTypeClause result = new UseTypeClause();
        result.theNames = theNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitUseTypeClauseEmbeddedPackageDeclarationMember(
        final UseTypeClauseEmbeddedPackageDeclarationMember o) {
      assert o != null;
      boolean substituted = false;

      UseTypeClause theUseTypeClause2 = null;
      {
        final UseTypeClause theUseTypeClause = o.theUseTypeClause;

        theUseTypeClause2 = (UseTypeClause) substitute(theUseTypeClause);

        if (theUseTypeClause != theUseTypeClause2) {
          substituted = true;
        }
      }

      if (substituted) {
        final UseTypeClauseEmbeddedPackageDeclarationMember result = new UseTypeClauseEmbeddedPackageDeclarationMember();
        result.theUseTypeClause = theUseTypeClause2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitWhileLoopStatement(final WhileLoopStatement o) {
      assert o != null;
      boolean substituted = false;

      AssertStatement theOptionalLoopInvariant2 = null;
      {
        final AssertStatement theOptionalLoopInvariant = o.theOptionalLoopInvariant;

        theOptionalLoopInvariant2 = (AssertStatement) substitute(theOptionalLoopInvariant);

        if (theOptionalLoopInvariant != theOptionalLoopInvariant2) {
          substituted = true;
        }
      }

      StatementList theStatementList2 = null;
      {
        final StatementList theStatementList = o.theStatementList;

        theStatementList2 = (StatementList) substitute(theStatementList);

        if (theStatementList != theStatementList2) {
          substituted = true;
        }
      }

      Exp theExp2 = null;
      {
        final Exp theExp = o.theExp;

        theExp2 = (Exp) substitute(theExp);

        if (theExp != theExp2) {
          substituted = true;
        }
      }

      ArrayList<String> theOptionalLabelList2 = null;
      {
        final ArrayList<String> theOptionalLabelList = o.theOptionalLabelList;

        if (theOptionalLabelList != null) {
          boolean substituted0 = false;
          final ArrayList<String> temp0 = new ArrayList<String>(
              theOptionalLabelList.size());
          for (final String e0 : theOptionalLabelList) {
            final String newE0 = e0;
            if (e0 != newE0) {
              substituted0 = true;
            }
            temp0.add(newE0);
          }
          theOptionalLabelList2 = substituted0 ? temp0 : theOptionalLabelList;
        }

        if (theOptionalLabelList != theOptionalLabelList2) {
          substituted = true;
        }
      }

      if (substituted) {
        final WhileLoopStatement result = new WhileLoopStatement();
        result.theOptionalLoopInvariant = theOptionalLoopInvariant2;
        result.theStatementList = theStatementList2;
        result.theExp = theExp2;
        result.theOptionalLabelList = theOptionalLabelList2;
        result.theStatementIndex = o.theStatementIndex;
        result.theOptionalIDString = o.theOptionalIDString;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        result.theOptionalIDRegionSelection = o.theOptionalIDRegionSelection;
        result.theLoopKeywordRegionSelection = o.theLoopKeywordRegionSelection;
        result.theIterationSchemeRegionSelection = o.theIterationSchemeRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }

    @Override
    protected void visitWithClause(final WithClause o) {
      assert o != null;
      boolean substituted = false;

      ArrayList<Name> theNames2 = null;
      {
        final ArrayList<Name> theNames = o.theNames;

        boolean substituted0 = false;
        final ArrayList<Name> temp0 = new ArrayList<Name>(theNames.size());
        for (final Name e0 : theNames) {
          final Name newE0 = (Name) substitute(e0);
          if (e0 != newE0) {
            substituted0 = true;
          }
          temp0.add(newE0);
        }
        theNames2 = substituted0 ? temp0 : theNames;

        if (theNames != theNames2) {
          substituted = true;
        }
      }

      if (substituted) {
        final WithClause result = new WithClause();
        result.theNames = theNames2;
        result.theOptionalRegionSelection = o.theOptionalRegionSelection;
        this.g.pushResult(result);
      } else {
        this.g.pushResult(o);
      }
    }
  }

  protected static class VisitorContext {
    protected Node result;
    public SubstituteFunction sf;

    protected Node peekResult() {
      assert this.result != null;
      return this.result;
    }

    protected Node popResult() {
      assert this.result != null;
      final Node temp = this.result;
      this.result = null;
      return temp;
    }

    protected void pushResult(final Node o) {
      assert (this.result == null) && (o != null);
      this.result = o;
    }
  }

  @SuppressWarnings("unchecked")
  public static <O extends Node> ArrayList<O> substitute(final ArrayList<O> os,
      final SubstituteFunction sf) {
    final VisitorContext vc = new VisitorContext();
    vc.sf = sf;
    final Visitor v = new Visitor(vc);
    final ArrayList<O> result = new ArrayList<O>();
    for (final O o : os) {
      final O o2 = (O) sf.substitute(o);
      if (o2 != null) {
        result.add(o2);
      } else {
        v.visit(o);
        result.add((O) vc.popResult());
      }
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <O extends Node> O substitute(final O o,
      final SubstituteFunction sf) {
    final O result = (O) sf.substitute(o);
    if (result != null) {
      return result;
    }
    final VisitorContext vc = new VisitorContext();
    vc.sf = sf;
    final Visitor v = new Visitor(vc);
    v.visit(o);
    return (O) vc.popResult();
  }
}
