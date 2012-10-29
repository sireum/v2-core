/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import org.sireum.bakar.util.Pair;
import org.sireum.bakar.util.Triple;
import org.sireum.bakar.util.Tuple;

public class NodeComparator
    implements Comparator<Object> {

  public int compare(final AggregateQualifiedExp o1,
      final AggregateQualifiedExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theAggregate == o2.theAggregate) {
    } else {
      result = compare(o1.theAggregate, o2.theAggregate);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ArrayAggregateItem o1, final ArrayAggregateItem o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theArrayAggregate == o2.theArrayAggregate) {
    } else {
      result = compare(o1.theArrayAggregate, o2.theArrayAggregate);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ArrayComponentAssociation o1,
      final ArrayComponentAssociation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theAggregateItem == o2.theAggregateItem) {
    } else {
      result = compare(o1.theAggregateItem, o2.theAggregateItem);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Choice> theChoices1 = o1.theChoices;
      final ArrayList<Choice> theChoices2 = o2.theChoices;

      {
        if (theChoices1 == theChoices2) {
        } else {
          final int count0 = theChoices1.size();
          result = compare(count0, theChoices2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Choice e0_1 = theChoices1.get(i0);
            final Choice e0_2 = theChoices2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final ArrayUpdate o1, final ArrayUpdate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Pair<ArrayList<Exp>, Exp>> theIndexListToExpressionList1 = o1.theIndexListToExpressionList;
      final ArrayList<Pair<ArrayList<Exp>, Exp>> theIndexListToExpressionList2 = o2.theIndexListToExpressionList;

      {
        if (theIndexListToExpressionList1 == theIndexListToExpressionList2) {
        } else {
          final int count0 = theIndexListToExpressionList1.size();
          result = compare(count0, theIndexListToExpressionList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Pair<ArrayList<Exp>, Exp> e0_1 = theIndexListToExpressionList1
                .get(i0);
            final Pair<ArrayList<Exp>, Exp> e0_2 = theIndexListToExpressionList2
                .get(i0);
            {
              if (e0_1 == e0_2) {
              } else {
                {
                  if (e0_1.getE0() == e0_2.getE0()) {
                  } else {
                    final int count1 = e0_1.getE0().size();
                    result = compare(count1, e0_2.getE0().size());
                    if (result != 0) {
                      return result;
                    }
                    for (int i1 = 0; i1 < count1; i1++) {
                      final Exp e1_1 = e0_1.getE0().get(i1);
                      final Exp e1_2 = e0_2.getE0().get(i1);
                      if (e1_1 == e1_2) {
                      } else {
                        result = compare(e1_1, e1_2);
                        if (result != 0) {
                          return result;
                        }
                      }
                    }
                  }
                }
                if (e0_1.getE1() == e0_2.getE1()) {
                } else {
                  result = compare(e0_1.getE1(), e0_2.getE1());
                  if (result != 0) {
                    return result;
                  }
                }
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final AssertStatement o1, final AssertStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePredicate == o2.thePredicate) {
    } else {
      result = compare(o1.thePredicate, o2.thePredicate);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final AssignmentStatement o1, final AssignmentStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final AtClause o1, final AtClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final AttributeDefinitionClause o1,
      final AttributeDefinitionClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final AttributeReference o1, final AttributeReference o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theAttributeDesignator == o2.theAttributeDesignator) {
    } else {
      result = compare(o1.theAttributeDesignator, o2.theAttributeDesignator);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final BasicDeclarationBasicDeclarativeItem o1,
      final BasicDeclarationBasicDeclarativeItem o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theBasicDeclaration == o2.theBasicDeclaration) {
    } else {
      result = compare(o1.theBasicDeclaration, o2.theBasicDeclaration);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final BinaryExp o1, final BinaryExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theBinaryOp == o2.theBinaryOp) {
    } else if (o1.theBinaryOp == null) {
      return -1;
    } else if (o2.theBinaryOp == null) {
      return 1;
    } else {
      result = compare(o1.theBinaryOp, o2.theBinaryOp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theLeftExp == o2.theLeftExp) {
    } else {
      result = compare(o1.theLeftExp, o2.theLeftExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theRightExp == o2.theRightExp) {
    } else {
      result = compare(o1.theRightExp, o2.theRightExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final boolean b1, final boolean b2) {
    if (b1 == b2) {
      return 0;
    } else if (b1) {
      return 1;
    } else {
      return -1;
    }
  }

  public int compare(final Boolean b1, final Boolean b2) {
    if (b1 == b2) {
      return 0;
    } else if (b1 == null) {
      return -1;
    } else if (b2 == null) {
      return 1;
    }
    return compare(b1.booleanValue(), b2.booleanValue());
  }

  public int compare(final Byte b1, final Byte b2) {
    if (b1 == b2) {
      return 0;
    } else if (b1 == null) {
      return -1;
    } else if (b2 == null) {
      return 1;
    }
    return compare(b1.intValue(), b2.intValue());
  }

  public int compare(final CaseStatement o1, final CaseStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalOthers == o2.theOptionalOthers) {
    } else if (o1.theOptionalOthers == null) {
      return -1;
    } else if (o2.theOptionalOthers == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalOthers, o2.theOptionalOthers);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalOthersSelection == o2.theOptionalOthersSelection) {
    } else if (o1.theOptionalOthersSelection == null) {
      return -1;
    } else if (o2.theOptionalOthersSelection == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalOthersSelection,
          o2.theOptionalOthersSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalOthersBodySelection == o2.theOptionalOthersBodySelection) {
    } else if (o1.theOptionalOthersBodySelection == null) {
      return -1;
    } else if (o2.theOptionalOthersBodySelection == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalOthersBodySelection,
          o2.theOptionalOthersBodySelection);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<CaseStatementAlternative> theCaseStatementAlternatives1 = o1.theCaseStatementAlternatives;
      final ArrayList<CaseStatementAlternative> theCaseStatementAlternatives2 = o2.theCaseStatementAlternatives;

      {
        if (theCaseStatementAlternatives1 == theCaseStatementAlternatives2) {
        } else {
          final int count0 = theCaseStatementAlternatives1.size();
          result = compare(count0, theCaseStatementAlternatives2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final CaseStatementAlternative e0_1 = theCaseStatementAlternatives1
                .get(i0);
            final CaseStatementAlternative e0_2 = theCaseStatementAlternatives2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final CaseStatementAlternative o1,
      final CaseStatementAlternative o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementList == o2.theStatementList) {
    } else {
      result = compare(o1.theStatementList, o2.theStatementList);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theBodySelection == o2.theBodySelection) {
    } else {
      result = compare(o1.theBodySelection, o2.theBodySelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theChoiceListSelection == o2.theChoiceListSelection) {
    } else {
      result = compare(o1.theChoiceListSelection, o2.theChoiceListSelection);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Choice> theChoices1 = o1.theChoices;
      final ArrayList<Choice> theChoices2 = o2.theChoices;

      {
        if (theChoices1 == theChoices2) {
        } else {
          final int count0 = theChoices1.size();
          result = compare(count0, theChoices2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Choice e0_1 = theChoices1.get(i0);
            final Choice e0_2 = theChoices2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final Character c1, final Character c2) {
    if (c1 == c2) {
      return 0;
    } else if (c1 == null) {
      return -1;
    } else if (c2 == null) {
      return 1;
    }
    return compare(c1.charValue(), c2.charValue());
  }

  public int compare(final CharacterLiteral o1, final CharacterLiteral o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theCharacter == o2.theCharacter) {
    } else {
      result = compare(o1.theCharacter, o2.theCharacter);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final CheckStatement o1, final CheckStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePredicate == o2.thePredicate) {
    } else {
      result = compare(o1.thePredicate, o2.thePredicate);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final CodeSubProgramImplementation o1,
      final CodeSubProgramImplementation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<QualifiedExp> theQualifiedExps1 = o1.theQualifiedExps;
      final ArrayList<QualifiedExp> theQualifiedExps2 = o2.theQualifiedExps;

      {
        if (theQualifiedExps1 == theQualifiedExps2) {
        } else {
          final int count0 = theQualifiedExps1.size();
          result = compare(count0, theQualifiedExps2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final QualifiedExp e0_1 = theQualifiedExps1.get(i0);
            final QualifiedExp e0_2 = theQualifiedExps2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final Compilation o1, final Compilation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<CompilationUnit> theOptionalCompilationUnits1 = o1.theOptionalCompilationUnits;
      final ArrayList<CompilationUnit> theOptionalCompilationUnits2 = o2.theOptionalCompilationUnits;

      {
        if (theOptionalCompilationUnits1 == theOptionalCompilationUnits2) {
        } else if (theOptionalCompilationUnits1 == null) {
          return -1;
        } else if (theOptionalCompilationUnits2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalCompilationUnits1.size();
          result = compare(count0, theOptionalCompilationUnits2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final CompilationUnit e0_1 = theOptionalCompilationUnits1.get(i0);
            final CompilationUnit e0_2 = theOptionalCompilationUnits2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final ComponentClause o1, final ComponentClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePositionExp == o2.thePositionExp) {
    } else {
      result = compare(o1.thePositionExp, o2.thePositionExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theFirstBitExp == o2.theFirstBitExp) {
    } else {
      result = compare(o1.theFirstBitExp, o2.theFirstBitExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theLastBitExp == o2.theLastBitExp) {
    } else {
      result = compare(o1.theLastBitExp, o2.theLastBitExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ComponentDeclaration o1,
      final ComponentDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<IDName> theIDNames1 = o1.theIDNames;
      final ArrayList<IDName> theIDNames2 = o2.theIDNames;

      {
        if (theIDNames1 == theIDNames2) {
        } else {
          final int count0 = theIDNames1.size();
          result = compare(count0, theIDNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final IDName e0_1 = theIDNames1.get(i0);
            final IDName e0_2 = theIDNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final Constituent o1, final Constituent o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theMode == o2.theMode) {
    } else {
      result = compare(o1.theMode, o2.theMode);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ConstrainedArrayDefinition o1,
      final ConstrainedArrayDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theComponentName == o2.theComponentName) {
    } else {
      result = compare(o1.theComponentName, o2.theComponentName);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Name> theDiscreteSubTypeNames1 = o1.theDiscreteSubTypeNames;
      final ArrayList<Name> theDiscreteSubTypeNames2 = o2.theDiscreteSubTypeNames;

      {
        if (theDiscreteSubTypeNames1 == theDiscreteSubTypeNames2) {
        } else {
          final int count0 = theDiscreteSubTypeNames1.size();
          result = compare(count0, theDiscreteSubTypeNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theDiscreteSubTypeNames1.get(i0);
            final Name e0_2 = theDiscreteSubTypeNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final ContextClause o1, final ContextClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<ContextItem> theOptionalContextItems1 = o1.theOptionalContextItems;
      final ArrayList<ContextItem> theOptionalContextItems2 = o2.theOptionalContextItems;

      {
        if (theOptionalContextItems1 == theOptionalContextItems2) {
        } else if (theOptionalContextItems1 == null) {
          return -1;
        } else if (theOptionalContextItems2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalContextItems1.size();
          result = compare(count0, theOptionalContextItems2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final ContextItem e0_1 = theOptionalContextItems1.get(i0);
            final ContextItem e0_2 = theOptionalContextItems2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final DeclarativePart o1, final DeclarativePart o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<RenamingDeclaration> theOptionalRenamingDeclarations1 = o1.theOptionalRenamingDeclarations;
      final ArrayList<RenamingDeclaration> theOptionalRenamingDeclarations2 = o2.theOptionalRenamingDeclarations;

      {
        if (theOptionalRenamingDeclarations1 == theOptionalRenamingDeclarations2) {
        } else if (theOptionalRenamingDeclarations1 == null) {
          return -1;
        } else if (theOptionalRenamingDeclarations2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalRenamingDeclarations1.size();
          result = compare(count0, theOptionalRenamingDeclarations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final RenamingDeclaration e0_1 = theOptionalRenamingDeclarations1
                .get(i0);
            final RenamingDeclaration e0_2 = theOptionalRenamingDeclarations2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<DeclarativePartMember> theOptionalDeclarativePartMembers1 = o1.theOptionalDeclarativePartMembers;
      final ArrayList<DeclarativePartMember> theOptionalDeclarativePartMembers2 = o2.theOptionalDeclarativePartMembers;

      {
        if (theOptionalDeclarativePartMembers1 == theOptionalDeclarativePartMembers2) {
        } else if (theOptionalDeclarativePartMembers1 == null) {
          return -1;
        } else if (theOptionalDeclarativePartMembers2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalDeclarativePartMembers1.size();
          result = compare(count0, theOptionalDeclarativePartMembers2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final DeclarativePartMember e0_1 = theOptionalDeclarativePartMembers1
                .get(i0);
            final DeclarativePartMember e0_2 = theOptionalDeclarativePartMembers2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final DefaultLoopStatement o1,
      final DefaultLoopStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalIDString == o2.theOptionalIDString) {
    } else if (o1.theOptionalIDString == null) {
      return -1;
    } else if (o2.theOptionalIDString == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalIDString, o2.theOptionalIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalIDRegionSelection == o2.theOptionalIDRegionSelection) {
    } else if (o1.theOptionalIDRegionSelection == null) {
      return -1;
    } else if (o2.theOptionalIDRegionSelection == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalIDRegionSelection,
          o2.theOptionalIDRegionSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theLoopKeywordRegionSelection == o2.theLoopKeywordRegionSelection) {
    } else {
      result = compare(
          o1.theLoopKeywordRegionSelection,
          o2.theLoopKeywordRegionSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalLoopInvariant == o2.theOptionalLoopInvariant) {
    } else if (o1.theOptionalLoopInvariant == null) {
      return -1;
    } else if (o2.theOptionalLoopInvariant == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalLoopInvariant, o2.theOptionalLoopInvariant);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theStatementList == o2.theStatementList) {
    } else {
      result = compare(o1.theStatementList, o2.theStatementList);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final DeltaAttributeDesignator o1,
      final DeltaAttributeDesignator o2) {
    assert (o1 != null) && (o2 != null);
    return 0;
  }

  public int compare(final DependencyClause o1, final DependencyClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theImportStarFlag == o2.theImportStarFlag) {
    } else {
      result = compare(o1.theImportStarFlag, o2.theImportStarFlag);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Name> theExportedVariables1 = o1.theExportedVariables;
      final ArrayList<Name> theExportedVariables2 = o2.theExportedVariables;

      {
        if (theExportedVariables1 == theExportedVariables2) {
        } else {
          final int count0 = theExportedVariables1.size();
          result = compare(count0, theExportedVariables2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theExportedVariables1.get(i0);
            final Name e0_2 = theExportedVariables2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<Name> theOptionalImportedVariables1 = o1.theOptionalImportedVariables;
      final ArrayList<Name> theOptionalImportedVariables2 = o2.theOptionalImportedVariables;

      {
        if (theOptionalImportedVariables1 == theOptionalImportedVariables2) {
        } else if (theOptionalImportedVariables1 == null) {
          return -1;
        } else if (theOptionalImportedVariables2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalImportedVariables1.size();
          result = compare(count0, theOptionalImportedVariables2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theOptionalImportedVariables1.get(i0);
            final Name e0_2 = theOptionalImportedVariables2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final DependencyRelation o1, final DependencyRelation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<DependencyClause> theOptionalDependencyClauses1 = o1.theOptionalDependencyClauses;
      final ArrayList<DependencyClause> theOptionalDependencyClauses2 = o2.theOptionalDependencyClauses;

      {
        if (theOptionalDependencyClauses1 == theOptionalDependencyClauses2) {
        } else if (theOptionalDependencyClauses1 == null) {
          return -1;
        } else if (theOptionalDependencyClauses2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalDependencyClauses1.size();
          result = compare(count0, theOptionalDependencyClauses2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final DependencyClause e0_1 = theOptionalDependencyClauses1.get(i0);
            final DependencyClause e0_2 = theOptionalDependencyClauses2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<Name> theOptionalNullDependencyVariables1 = o1.theOptionalNullDependencyVariables;
      final ArrayList<Name> theOptionalNullDependencyVariables2 = o2.theOptionalNullDependencyVariables;

      {
        if (theOptionalNullDependencyVariables1 == theOptionalNullDependencyVariables2) {
        } else if (theOptionalNullDependencyVariables1 == null) {
          return -1;
        } else if (theOptionalNullDependencyVariables2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalNullDependencyVariables1.size();
          result = compare(count0, theOptionalNullDependencyVariables2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theOptionalNullDependencyVariables1.get(i0);
            final Name e0_2 = theOptionalNullDependencyVariables2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final DigitsAttributeDesignator o1,
      final DigitsAttributeDesignator o2) {
    assert (o1 != null) && (o2 != null);
    return 0;
  }

  public int compare(final double d1, final double d2) {
    if ((d1 < d2) && (d1 > d2)) {
      return 0;
    } else if (d1 > d2) {
      return 1;
    } else {
      return -1;
    }
  }

  public int compare(final Double d1, final Double d2) {
    if (d1 == d2) {
      return 0;
    } else if (d1 == null) {
      return -1;
    } else if (d2 == null) {
      return 1;
    }
    return compare(d1.doubleValue(), d2.doubleValue());
  }

  public int compare(final EmbeddedPackageDeclaration o1,
      final EmbeddedPackageDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.thePackageDeclaration == o2.thePackageDeclaration) {
    } else {
      result = compare(o1.thePackageDeclaration, o2.thePackageDeclaration);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<EmbeddedPackageDeclarationMember> theOptionalEmbeddedPackageDeclarationMembers1 = o1.theOptionalEmbeddedPackageDeclarationMembers;
      final ArrayList<EmbeddedPackageDeclarationMember> theOptionalEmbeddedPackageDeclarationMembers2 = o2.theOptionalEmbeddedPackageDeclarationMembers;

      {
        if (theOptionalEmbeddedPackageDeclarationMembers1 == theOptionalEmbeddedPackageDeclarationMembers2) {
        } else if (theOptionalEmbeddedPackageDeclarationMembers1 == null) {
          return -1;
        } else if (theOptionalEmbeddedPackageDeclarationMembers2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalEmbeddedPackageDeclarationMembers1
              .size();
          result = compare(
              count0,
              theOptionalEmbeddedPackageDeclarationMembers2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final EmbeddedPackageDeclarationMember e0_1 = theOptionalEmbeddedPackageDeclarationMembers1
                .get(i0);
            final EmbeddedPackageDeclarationMember e0_2 = theOptionalEmbeddedPackageDeclarationMembers2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final EnumerationRepresentationClause o1,
      final EnumerationRepresentationClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theArrayAggregate == o2.theArrayAggregate) {
    } else {
      result = compare(o1.theArrayAggregate, o2.theArrayAggregate);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final EnumerationTypeDefinition o1,
      final EnumerationTypeDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<IDName> theIDNames1 = o1.theIDNames;
      final ArrayList<IDName> theIDNames2 = o2.theIDNames;

      {
        if (theIDNames1 == theIDNames2) {
        } else {
          final int count0 = theIDNames1.size();
          result = compare(count0, theIDNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final IDName e0_1 = theIDNames1.get(i0);
            final IDName e0_2 = theIDNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final ExitStatement o1, final ExitStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalName == o2.theOptionalName) {
    } else if (o1.theOptionalName == null) {
      return -1;
    } else if (o2.theOptionalName == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalName, o2.theOptionalName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalExp == o2.theOptionalExp) {
    } else if (o1.theOptionalExp == null) {
      return -1;
    } else if (o2.theOptionalExp == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalExp, o2.theOptionalExp);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final ExpAggregateItem o1, final ExpAggregateItem o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ExpChoice o1, final ExpChoice o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ExpPragmaArgumentAssociation o1,
      final ExpPragmaArgumentAssociation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theOptionalIDString == o2.theOptionalIDString) {
    } else if (o1.theOptionalIDString == null) {
      return -1;
    } else if (o2.theOptionalIDString == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalIDString, o2.theOptionalIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ExpQualifiedExp o1, final ExpQualifiedExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ExpRange o1, final ExpRange o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theLowRangeExp == o2.theLowRangeExp) {
    } else {
      result = compare(o1.theLowRangeExp, o2.theLowRangeExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theHighRangeExp == o2.theHighRangeExp) {
    } else {
      result = compare(o1.theHighRangeExp, o2.theHighRangeExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ExternalSubProgramDeclaration o1,
      final ExternalSubProgramDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theSubProgramDeclaration == o2.theSubProgramDeclaration) {
    } else {
      result = compare(o1.theSubProgramDeclaration, o2.theSubProgramDeclaration);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePragma == o2.thePragma) {
    } else {
      result = compare(o1.thePragma, o2.thePragma);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final float f1, final float f2) {
    if ((f1 < f2) && (f1 > f2)) {
      return 0;
    } else if (f1 > f2) {
      return 1;
    } else {
      return -1;
    }
  }

  public int compare(final Float f1, final Float f2) {
    if (f1 == f2) {
      return 0;
    } else if (f1 == null) {
      return -1;
    } else if (f2 == null) {
      return 1;
    }
    return compare(f1.floatValue(), f2.floatValue());
  }

  public int compare(final FloatingPointDefinition o1,
      final FloatingPointDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalLowRangeExp == o2.theOptionalLowRangeExp) {
    } else if (o1.theOptionalLowRangeExp == null) {
      return -1;
    } else if (o2.theOptionalLowRangeExp == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalLowRangeExp, o2.theOptionalLowRangeExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalHighRangeExp == o2.theOptionalHighRangeExp) {
    } else if (o1.theOptionalHighRangeExp == null) {
      return -1;
    } else if (o2.theOptionalHighRangeExp == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalHighRangeExp, o2.theOptionalHighRangeExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ForLoopStatement o1, final ForLoopStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theReverseFlag == o2.theReverseFlag) {
    } else {
      result = compare(o1.theReverseFlag, o2.theReverseFlag);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalIDString == o2.theOptionalIDString) {
    } else if (o1.theOptionalIDString == null) {
      return -1;
    } else if (o2.theOptionalIDString == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalIDString, o2.theOptionalIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalIDRegionSelection == o2.theOptionalIDRegionSelection) {
    } else if (o1.theOptionalIDRegionSelection == null) {
      return -1;
    } else if (o2.theOptionalIDRegionSelection == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalIDRegionSelection,
          o2.theOptionalIDRegionSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theLoopKeywordRegionSelection == o2.theLoopKeywordRegionSelection) {
    } else {
      result = compare(
          o1.theLoopKeywordRegionSelection,
          o2.theLoopKeywordRegionSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalLoopInvariant == o2.theOptionalLoopInvariant) {
    } else if (o1.theOptionalLoopInvariant == null) {
      return -1;
    } else if (o2.theOptionalLoopInvariant == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalLoopInvariant, o2.theOptionalLoopInvariant);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theStatementList == o2.theStatementList) {
    } else {
      result = compare(o1.theStatementList, o2.theStatementList);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theIterationSchemeRegionSelection == o2.theIterationSchemeRegionSelection) {
    } else {
      result = compare(
          o1.theIterationSchemeRegionSelection,
          o2.theIterationSchemeRegionSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalRange == o2.theOptionalRange) {
    } else if (o1.theOptionalRange == null) {
      return -1;
    } else if (o2.theOptionalRange == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalRange, o2.theOptionalRange);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final FullTypeDeclaration o1, final FullTypeDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theTypeDefinition == o2.theTypeDefinition) {
    } else {
      result = compare(o1.theTypeDefinition, o2.theTypeDefinition);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final FunctionAnnotation o1, final FunctionAnnotation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theOptionalGlobalDefinition == o2.theOptionalGlobalDefinition) {
    } else if (o1.theOptionalGlobalDefinition == null) {
      return -1;
    } else if (o2.theOptionalGlobalDefinition == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalGlobalDefinition,
          o2.theOptionalGlobalDefinition);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalPrecondition == o2.theOptionalPrecondition) {
    } else if (o1.theOptionalPrecondition == null) {
      return -1;
    } else if (o2.theOptionalPrecondition == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalPrecondition, o2.theOptionalPrecondition);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalReturnAnnotation == o2.theOptionalReturnAnnotation) {
    } else if (o1.theOptionalReturnAnnotation == null) {
      return -1;
    } else if (o2.theOptionalReturnAnnotation == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalReturnAnnotation,
          o2.theOptionalReturnAnnotation);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final FunctionBodyStub o1, final FunctionBodyStub o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theFunctionSpecification == o2.theFunctionSpecification) {
    } else {
      result = compare(o1.theFunctionSpecification, o2.theFunctionSpecification);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalFunctionAnnotation == o2.theOptionalFunctionAnnotation) {
    } else if (o1.theOptionalFunctionAnnotation == null) {
      return -1;
    } else if (o2.theOptionalFunctionAnnotation == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalFunctionAnnotation,
          o2.theOptionalFunctionAnnotation);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final FunctionCall o1, final FunctionCall o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalParameterAssociationList == o2.theOptionalParameterAssociationList) {
    } else if (o1.theOptionalParameterAssociationList == null) {
      return -1;
    } else if (o2.theOptionalParameterAssociationList == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalParameterAssociationList,
          o2.theOptionalParameterAssociationList);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final FunctionRenamingDeclaration o1,
      final FunctionRenamingDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theDefiningStringLiteral == o2.theDefiningStringLiteral) {
    } else {
      result = compare(o1.theDefiningStringLiteral, o2.theDefiningStringLiteral);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theReturnName == o2.theReturnName) {
    } else {
      result = compare(o1.theReturnName, o2.theReturnName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePackageName == o2.thePackageName) {
    } else {
      result = compare(o1.thePackageName, o2.thePackageName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theStringLiteral == o2.theStringLiteral) {
    } else {
      result = compare(o1.theStringLiteral, o2.theStringLiteral);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<ParameterSpecification> theParameterSpecifications1 = o1.theParameterSpecifications;
      final ArrayList<ParameterSpecification> theParameterSpecifications2 = o2.theParameterSpecifications;

      {
        if (theParameterSpecifications1 == theParameterSpecifications2) {
        } else {
          final int count0 = theParameterSpecifications1.size();
          result = compare(count0, theParameterSpecifications2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final ParameterSpecification e0_1 = theParameterSpecifications1
                .get(i0);
            final ParameterSpecification e0_2 = theParameterSpecifications2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final FunctionSpecification o1,
      final FunctionSpecification o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theMethodNameSelection == o2.theMethodNameSelection) {
    } else {
      result = compare(o1.theMethodNameSelection, o2.theMethodNameSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<ParameterSpecification> theOptionalParameterSpecification1 = o1.theOptionalParameterSpecification;
      final ArrayList<ParameterSpecification> theOptionalParameterSpecification2 = o2.theOptionalParameterSpecification;

      {
        if (theOptionalParameterSpecification1 == theOptionalParameterSpecification2) {
        } else if (theOptionalParameterSpecification1 == null) {
          return -1;
        } else if (theOptionalParameterSpecification2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalParameterSpecification1.size();
          result = compare(count0, theOptionalParameterSpecification2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final ParameterSpecification e0_1 = theOptionalParameterSpecification1
                .get(i0);
            final ParameterSpecification e0_2 = theOptionalParameterSpecification2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final FunctionSpecificationRenamingDeclaration o1,
      final FunctionSpecificationRenamingDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theFunctionSpecification == o2.theFunctionSpecification) {
    } else {
      result = compare(o1.theFunctionSpecification, o2.theFunctionSpecification);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final FunctionSubProgramBody o1,
      final FunctionSubProgramBody o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theSubProgramImplementation == o2.theSubProgramImplementation) {
    } else {
      result = compare(
          o1.theSubProgramImplementation,
          o2.theSubProgramImplementation);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theFunctionSpecification == o2.theFunctionSpecification) {
    } else {
      result = compare(o1.theFunctionSpecification, o2.theFunctionSpecification);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalFunctionAnnotation == o2.theOptionalFunctionAnnotation) {
    } else if (o1.theOptionalFunctionAnnotation == null) {
      return -1;
    } else if (o2.theOptionalFunctionAnnotation == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalFunctionAnnotation,
          o2.theOptionalFunctionAnnotation);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final FunctionSubProgramDeclaration o1,
      final FunctionSubProgramDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theFunctionSpecification == o2.theFunctionSpecification) {
    } else {
      result = compare(o1.theFunctionSpecification, o2.theFunctionSpecification);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theFunctionAnnotation == o2.theFunctionAnnotation) {
    } else {
      result = compare(o1.theFunctionAnnotation, o2.theFunctionAnnotation);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final GenericAssociation o1, final GenericAssociation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theOptionalIDString == o2.theOptionalIDString) {
    } else if (o1.theOptionalIDString == null) {
      return -1;
    } else if (o2.theOptionalIDString == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalIDString, o2.theOptionalIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final GenericInstantiation o1,
      final GenericInstantiation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<GenericAssociation> theOptionalGenericAssociations1 = o1.theOptionalGenericAssociations;
      final ArrayList<GenericAssociation> theOptionalGenericAssociations2 = o2.theOptionalGenericAssociations;

      {
        if (theOptionalGenericAssociations1 == theOptionalGenericAssociations2) {
        } else if (theOptionalGenericAssociations1 == null) {
          return -1;
        } else if (theOptionalGenericAssociations2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalGenericAssociations1.size();
          result = compare(count0, theOptionalGenericAssociations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final GenericAssociation e0_1 = theOptionalGenericAssociations1
                .get(i0);
            final GenericAssociation e0_2 = theOptionalGenericAssociations2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final GlobalDeclaration o1, final GlobalDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theMode == o2.theMode) {
    } else {
      result = compare(o1.theMode, o2.theMode);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Name> theNames1 = o1.theNames;
      final ArrayList<Name> theNames2 = o2.theNames;

      {
        if (theNames1 == theNames2) {
        } else {
          final int count0 = theNames1.size();
          result = compare(count0, theNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theNames1.get(i0);
            final Name e0_2 = theNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final GlobalDefinition o1, final GlobalDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<GlobalDeclaration> theGlobalDeclarations1 = o1.theGlobalDeclarations;
      final ArrayList<GlobalDeclaration> theGlobalDeclarations2 = o2.theGlobalDeclarations;

      {
        if (theGlobalDeclarations1 == theGlobalDeclarations2) {
        } else {
          final int count0 = theGlobalDeclarations1.size();
          result = compare(count0, theGlobalDeclarations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final GlobalDeclaration e0_1 = theGlobalDeclarations1.get(i0);
            final GlobalDeclaration e0_2 = theGlobalDeclarations2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final IDAttributeDesignator o1,
      final IDAttributeDesignator o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Exp> theOptionalExps1 = o1.theOptionalExps;
      final ArrayList<Exp> theOptionalExps2 = o2.theOptionalExps;

      {
        if (theOptionalExps1 == theOptionalExps2) {
        } else if (theOptionalExps1 == null) {
          return -1;
        } else if (theOptionalExps2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalExps1.size();
          result = compare(count0, theOptionalExps2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Exp e0_1 = theOptionalExps1.get(i0);
            final Exp e0_2 = theOptionalExps2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final IDName o1, final IDName o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theDecoratedFlag == o2.theDecoratedFlag) {
    } else {
      result = compare(o1.theDecoratedFlag, o2.theDecoratedFlag);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final IfStatement o1, final IfStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theThen == o2.theThen) {
    } else {
      result = compare(o1.theThen, o2.theThen);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theIfRegionSelection == o2.theIfRegionSelection) {
    } else {
      result = compare(o1.theIfRegionSelection, o2.theIfRegionSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalElse == o2.theOptionalElse) {
    } else if (o1.theOptionalElse == null) {
      return -1;
    } else if (o2.theOptionalElse == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalElse, o2.theOptionalElse);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> theOptionalElseIfs1 = o1.theOptionalElseIfs;
      final ArrayList<Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection>> theOptionalElseIfs2 = o2.theOptionalElseIfs;

      {
        if (theOptionalElseIfs1 == theOptionalElseIfs2) {
        } else if (theOptionalElseIfs1 == null) {
          return -1;
        } else if (theOptionalElseIfs2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalElseIfs1.size();
          result = compare(count0, theOptionalElseIfs2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection> e0_1 = theOptionalElseIfs1
                .get(i0);
            final Triple<Exp, StatementList, org.sireum.bakar.selection.model.RegionSelection> e0_2 = theOptionalElseIfs2
                .get(i0);
            {
              if (e0_1 == e0_2) {
              } else {
                if (e0_1.getE0() == e0_2.getE0()) {
                } else {
                  result = compare(e0_1.getE0(), e0_2.getE0());
                  if (result != 0) {
                    return result;
                  }
                }
                if (e0_1.getE1() == e0_2.getE1()) {
                } else {
                  result = compare(e0_1.getE1(), e0_2.getE1());
                  if (result != 0) {
                    return result;
                  }
                }
                if (e0_1.getE2() == e0_2.getE2()) {
                } else {
                  result = compare(e0_1.getE2(), e0_2.getE2());
                  if (result != 0) {
                    return result;
                  }
                }
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final IndexConstraint o1, final IndexConstraint o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<Name> theDiscreteSubTypeNames1 = o1.theDiscreteSubTypeNames;
      final ArrayList<Name> theDiscreteSubTypeNames2 = o2.theDiscreteSubTypeNames;

      {
        if (theDiscreteSubTypeNames1 == theDiscreteSubTypeNames2) {
        } else {
          final int count0 = theDiscreteSubTypeNames1.size();
          result = compare(count0, theDiscreteSubTypeNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theDiscreteSubTypeNames1.get(i0);
            final Name e0_2 = theDiscreteSubTypeNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final IndexedComponent o1, final IndexedComponent o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Exp> theExps1 = o1.theExps;
      final ArrayList<Exp> theExps2 = o2.theExps;

      {
        if (theExps1 == theExps2) {
        } else {
          final int count0 = theExps1.size();
          result = compare(count0, theExps2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Exp e0_1 = theExps1.get(i0);
            final Exp e0_2 = theExps2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final InRangeExp o1, final InRangeExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theRange == o2.theRange) {
    } else {
      result = compare(o1.theRange, o2.theRange);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final int i1, final int i2) {
    if (i1 < i2) {
      return -1;
    } else if (i1 > i2) {
      return 1;
    } else {
      return 0;
    }
  }

  public int compare(final Integer i1, final Integer i2) {
    if (i1 == i2) {
      return 0;
    } else if (i1 == null) {
      return -1;
    } else if (i2 == null) {
      return 1;
    }
    return compare(i1.intValue(), i2.intValue());
  }

  public int compare(final java.lang.Enum<?> e1, final java.lang.Enum<?> e2) {
    return compare(e1.ordinal(), e2.ordinal());
  }

  public int compare(final JustificationClause o1, final JustificationClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theMessageID == o2.theMessageID) {
    } else {
      result = compare(o1.theMessageID, o2.theMessageID);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theMessage == o2.theMessage) {
    } else if (o1.theMessage == null) {
      return -1;
    } else if (o2.theMessage == null) {
      return 1;
    } else {
      result = compare(o1.theMessage, o2.theMessage);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theKind == o2.theKind) {
    } else {
      result = compare(o1.theKind, o2.theKind);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Name> theOptionalNames1 = o1.theOptionalNames;
      final ArrayList<Name> theOptionalNames2 = o2.theOptionalNames;

      {
        if (theOptionalNames1 == theOptionalNames2) {
        } else if (theOptionalNames1 == null) {
          return -1;
        } else if (theOptionalNames2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalNames1.size();
          result = compare(count0, theOptionalNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theOptionalNames1.get(i0);
            final Name e0_2 = theOptionalNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final JustificationStatement o1,
      final JustificationStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<JustificationClause> theClauses1 = o1.theClauses;
      final ArrayList<JustificationClause> theClauses2 = o2.theClauses;

      {
        if (theClauses1 == theClauses2) {
        } else {
          final int count0 = theClauses1.size();
          result = compare(count0, theClauses2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final JustificationClause e0_1 = theClauses1.get(i0);
            final JustificationClause e0_2 = theClauses2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final JustificationStatementEnd o1,
      final JustificationStatementEnd o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final LibraryCompilationUnit o1,
      final LibraryCompilationUnit o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theContextClause == o2.theContextClause) {
    } else {
      result = compare(o1.theContextClause, o2.theContextClause);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theLibraryItem == o2.theLibraryItem) {
    } else {
      result = compare(o1.theLibraryItem, o2.theLibraryItem);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final LibraryUnitBody o1, final LibraryUnitBody o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.thePackageBody == o2.thePackageBody) {
    } else {
      result = compare(o1.thePackageBody, o2.thePackageBody);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  @SuppressWarnings("rawtypes")
  public int compare(final List l1, final List l2) {
    if (l1 == l2) {
      return 0;
    } else if (l1 == null) {
      return -1;
    } else if (l2 == null) {
      return 1;
    }
    int count;
    int result = compare(count = l1.size(), l2.size());
    if (result != 0) {
      return result;
    }
    for (int i = 0; i < count; i++) {
      result = compare(l1.get(i), l2.get(i));
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

  public int compare(final LiteralExp o1, final LiteralExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theLiteral == o2.theLiteral) {
    } else {
      result = compare(o1.theLiteral, o2.theLiteral);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final long l1, final long l2) {
    if (l1 < l2) {
      return -1;
    } else if (l1 > l2) {
      return 1;
    } else {
      return 0;
    }
  }

  public int compare(final Long l1, final Long l2) {
    if (l1 == l2) {
      return 0;
    } else if (l1 == null) {
      return -1;
    } else if (l2 == null) {
      return 1;
    }
    return compare(l1.longValue(), l2.longValue());
  }

  public int compare(final MainProgram o1, final MainProgram o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theSubProgramBody == o2.theSubProgramBody) {
    } else {
      result = compare(o1.theSubProgramBody, o2.theSubProgramBody);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Name> theOptionalInheritClauses1 = o1.theOptionalInheritClauses;
      final ArrayList<Name> theOptionalInheritClauses2 = o2.theOptionalInheritClauses;

      {
        if (theOptionalInheritClauses1 == theOptionalInheritClauses2) {
        } else if (theOptionalInheritClauses1 == null) {
          return -1;
        } else if (theOptionalInheritClauses2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalInheritClauses1.size();
          result = compare(count0, theOptionalInheritClauses2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theOptionalInheritClauses1.get(i0);
            final Name e0_2 = theOptionalInheritClauses2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  @SuppressWarnings("rawtypes")
  public int compare(final Map m1, final Map m2) {
    if (m1 == m2) {
      return 0;
    } else if (m1 == null) {
      return -1;
    } else if (m2 == null) {
      return 1;
    }
    int count;
    int result = compare(count = m1.size(), m2.size());
    final Object[] ks1 = m1.keySet().toArray();
    if (!(m1 instanceof SortedMap)) {
      Arrays.sort(ks1, this);
    }
    final Object[] ks2 = m2.keySet().toArray();
    if (!(m1 instanceof SortedMap)) {
      Arrays.sort(ks2, this);
    }
    for (int i = 0; i < count; i++) {
      final Object k1 = ks1[i];
      final Object k2 = ks2[i];
      result = compare(k1, k2);
      if (result != 0) {
        return result;
      }
      result = compare(m1.get(k1), m2.get(k2));
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

  public int compare(final ModularTypeDefinition o1,
      final ModularTypeDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final NamedArrayAggregate o1, final NamedArrayAggregate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theOthersFlag == o2.theOthersFlag) {
    } else {
      result = compare(o1.theOthersFlag, o2.theOthersFlag);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalOthersAggregateItem == o2.theOptionalOthersAggregateItem) {
    } else if (o1.theOptionalOthersAggregateItem == null) {
      return -1;
    } else if (o2.theOptionalOthersAggregateItem == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalOthersAggregateItem,
          o2.theOptionalOthersAggregateItem);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<ArrayComponentAssociation> theOptionalArrayComponentAssociations1 = o1.theOptionalArrayComponentAssociations;
      final ArrayList<ArrayComponentAssociation> theOptionalArrayComponentAssociations2 = o2.theOptionalArrayComponentAssociations;

      {
        if (theOptionalArrayComponentAssociations1 == theOptionalArrayComponentAssociations2) {
        } else if (theOptionalArrayComponentAssociations1 == null) {
          return -1;
        } else if (theOptionalArrayComponentAssociations2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalArrayComponentAssociations1.size();
          result = compare(
              count0,
              theOptionalArrayComponentAssociations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final ArrayComponentAssociation e0_1 = theOptionalArrayComponentAssociations1
                .get(i0);
            final ArrayComponentAssociation e0_2 = theOptionalArrayComponentAssociations2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final NamedParameterAssociation o1,
      final NamedParameterAssociation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final NamedParameterAssociationList o1,
      final NamedParameterAssociationList o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<NamedParameterAssociation> theParameterAssociations1 = o1.theParameterAssociations;
      final ArrayList<NamedParameterAssociation> theParameterAssociations2 = o2.theParameterAssociations;

      {
        if (theParameterAssociations1 == theParameterAssociations2) {
        } else {
          final int count0 = theParameterAssociations1.size();
          result = compare(count0, theParameterAssociations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final NamedParameterAssociation e0_1 = theParameterAssociations1
                .get(i0);
            final NamedParameterAssociation e0_2 = theParameterAssociations2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final NamedRecordAggregate o1,
      final NamedRecordAggregate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<RecordComponentAssociation> theRecordComponentAssociations1 = o1.theRecordComponentAssociations;
      final ArrayList<RecordComponentAssociation> theRecordComponentAssociations2 = o2.theRecordComponentAssociations;

      {
        if (theRecordComponentAssociations1 == theRecordComponentAssociations2) {
        } else {
          final int count0 = theRecordComponentAssociations1.size();
          result = compare(count0, theRecordComponentAssociations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final RecordComponentAssociation e0_1 = theRecordComponentAssociations1
                .get(i0);
            final RecordComponentAssociation e0_2 = theRecordComponentAssociations2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final NamedRecordExtensionAggregate o1,
      final NamedRecordExtensionAggregate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<RecordComponentAssociation> theRecordComponentAssociations1 = o1.theRecordComponentAssociations;
      final ArrayList<RecordComponentAssociation> theRecordComponentAssociations2 = o2.theRecordComponentAssociations;

      {
        if (theRecordComponentAssociations1 == theRecordComponentAssociations2) {
        } else {
          final int count0 = theRecordComponentAssociations1.size();
          result = compare(count0, theRecordComponentAssociations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final RecordComponentAssociation e0_1 = theRecordComponentAssociations1
                .get(i0);
            final RecordComponentAssociation e0_2 = theRecordComponentAssociations2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final NameExp o1, final NameExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final NamePragmaArgumentAssociation o1,
      final NamePragmaArgumentAssociation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theOptionalIDString == o2.theOptionalIDString) {
    } else if (o1.theOptionalIDString == null) {
      return -1;
    } else if (o2.theOptionalIDString == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalIDString, o2.theOptionalIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final NameRangeExp o1, final NameRangeExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final Node o1, final Node o2) {
    if (o1 == o2) {
      return 0;
    } else if (o1 == null) {
      return -1;
    } else if (o2 == null) {
      return 1;
    }

    int desc;
    final int result = compare(desc = o1.getDescriptor(), o2.getDescriptor());
    if (result != 0) {
      return result;
    }

    switch (desc) {
      case AggregateQualifiedExp.DESCRIPTOR:
        return compare((AggregateQualifiedExp) o1, (AggregateQualifiedExp) o2);

      case ArrayAggregateItem.DESCRIPTOR:
        return compare((ArrayAggregateItem) o1, (ArrayAggregateItem) o2);

      case ArrayComponentAssociation.DESCRIPTOR:
        return compare(
            (ArrayComponentAssociation) o1,
            (ArrayComponentAssociation) o2);

      case ArrayUpdate.DESCRIPTOR:
        return compare((ArrayUpdate) o1, (ArrayUpdate) o2);

      case AssertStatement.DESCRIPTOR:
        return compare((AssertStatement) o1, (AssertStatement) o2);

      case AssignmentStatement.DESCRIPTOR:
        return compare((AssignmentStatement) o1, (AssignmentStatement) o2);

      case AtClause.DESCRIPTOR:
        return compare((AtClause) o1, (AtClause) o2);

      case AttributeDefinitionClause.DESCRIPTOR:
        return compare(
            (AttributeDefinitionClause) o1,
            (AttributeDefinitionClause) o2);

      case AttributeReference.DESCRIPTOR:
        return compare((AttributeReference) o1, (AttributeReference) o2);

      case BasicDeclarationBasicDeclarativeItem.DESCRIPTOR:
        return compare(
            (BasicDeclarationBasicDeclarativeItem) o1,
            (BasicDeclarationBasicDeclarativeItem) o2);

      case BinaryExp.DESCRIPTOR:
        return compare((BinaryExp) o1, (BinaryExp) o2);

      case CaseStatement.DESCRIPTOR:
        return compare((CaseStatement) o1, (CaseStatement) o2);

      case CaseStatementAlternative.DESCRIPTOR:
        return compare(
            (CaseStatementAlternative) o1,
            (CaseStatementAlternative) o2);

      case CharacterLiteral.DESCRIPTOR:
        return compare((CharacterLiteral) o1, (CharacterLiteral) o2);

      case CheckStatement.DESCRIPTOR:
        return compare((CheckStatement) o1, (CheckStatement) o2);

      case CodeSubProgramImplementation.DESCRIPTOR:
        return compare(
            (CodeSubProgramImplementation) o1,
            (CodeSubProgramImplementation) o2);

      case Compilation.DESCRIPTOR:
        return compare((Compilation) o1, (Compilation) o2);

      case ComponentClause.DESCRIPTOR:
        return compare((ComponentClause) o1, (ComponentClause) o2);

      case ComponentDeclaration.DESCRIPTOR:
        return compare((ComponentDeclaration) o1, (ComponentDeclaration) o2);

      case Constituent.DESCRIPTOR:
        return compare((Constituent) o1, (Constituent) o2);

      case ConstrainedArrayDefinition.DESCRIPTOR:
        return compare(
            (ConstrainedArrayDefinition) o1,
            (ConstrainedArrayDefinition) o2);

      case ContextClause.DESCRIPTOR:
        return compare((ContextClause) o1, (ContextClause) o2);

      case DeclarativePart.DESCRIPTOR:
        return compare((DeclarativePart) o1, (DeclarativePart) o2);

      case DefaultLoopStatement.DESCRIPTOR:
        return compare((DefaultLoopStatement) o1, (DefaultLoopStatement) o2);

      case DeltaAttributeDesignator.DESCRIPTOR:
        return compare(
            (DeltaAttributeDesignator) o1,
            (DeltaAttributeDesignator) o2);

      case DependencyClause.DESCRIPTOR:
        return compare((DependencyClause) o1, (DependencyClause) o2);

      case DependencyRelation.DESCRIPTOR:
        return compare((DependencyRelation) o1, (DependencyRelation) o2);

      case DigitsAttributeDesignator.DESCRIPTOR:
        return compare(
            (DigitsAttributeDesignator) o1,
            (DigitsAttributeDesignator) o2);

      case EmbeddedPackageDeclaration.DESCRIPTOR:
        return compare(
            (EmbeddedPackageDeclaration) o1,
            (EmbeddedPackageDeclaration) o2);

      case EnumerationRepresentationClause.DESCRIPTOR:
        return compare(
            (EnumerationRepresentationClause) o1,
            (EnumerationRepresentationClause) o2);

      case EnumerationTypeDefinition.DESCRIPTOR:
        return compare(
            (EnumerationTypeDefinition) o1,
            (EnumerationTypeDefinition) o2);

      case ExitStatement.DESCRIPTOR:
        return compare((ExitStatement) o1, (ExitStatement) o2);

      case ExpAggregateItem.DESCRIPTOR:
        return compare((ExpAggregateItem) o1, (ExpAggregateItem) o2);

      case ExpChoice.DESCRIPTOR:
        return compare((ExpChoice) o1, (ExpChoice) o2);

      case ExpPragmaArgumentAssociation.DESCRIPTOR:
        return compare(
            (ExpPragmaArgumentAssociation) o1,
            (ExpPragmaArgumentAssociation) o2);

      case ExpQualifiedExp.DESCRIPTOR:
        return compare((ExpQualifiedExp) o1, (ExpQualifiedExp) o2);

      case ExpRange.DESCRIPTOR:
        return compare((ExpRange) o1, (ExpRange) o2);

      case ExternalSubProgramDeclaration.DESCRIPTOR:
        return compare(
            (ExternalSubProgramDeclaration) o1,
            (ExternalSubProgramDeclaration) o2);

      case FloatingPointDefinition.DESCRIPTOR:
        return compare(
            (FloatingPointDefinition) o1,
            (FloatingPointDefinition) o2);

      case ForLoopStatement.DESCRIPTOR:
        return compare((ForLoopStatement) o1, (ForLoopStatement) o2);

      case FullTypeDeclaration.DESCRIPTOR:
        return compare((FullTypeDeclaration) o1, (FullTypeDeclaration) o2);

      case FunctionAnnotation.DESCRIPTOR:
        return compare((FunctionAnnotation) o1, (FunctionAnnotation) o2);

      case FunctionBodyStub.DESCRIPTOR:
        return compare((FunctionBodyStub) o1, (FunctionBodyStub) o2);

      case FunctionCall.DESCRIPTOR:
        return compare((FunctionCall) o1, (FunctionCall) o2);

      case FunctionRenamingDeclaration.DESCRIPTOR:
        return compare(
            (FunctionRenamingDeclaration) o1,
            (FunctionRenamingDeclaration) o2);

      case FunctionSpecification.DESCRIPTOR:
        return compare((FunctionSpecification) o1, (FunctionSpecification) o2);

      case FunctionSpecificationRenamingDeclaration.DESCRIPTOR:
        return compare(
            (FunctionSpecificationRenamingDeclaration) o1,
            (FunctionSpecificationRenamingDeclaration) o2);

      case FunctionSubProgramBody.DESCRIPTOR:
        return compare((FunctionSubProgramBody) o1, (FunctionSubProgramBody) o2);

      case FunctionSubProgramDeclaration.DESCRIPTOR:
        return compare(
            (FunctionSubProgramDeclaration) o1,
            (FunctionSubProgramDeclaration) o2);

      case GenericAssociation.DESCRIPTOR:
        return compare((GenericAssociation) o1, (GenericAssociation) o2);

      case GenericInstantiation.DESCRIPTOR:
        return compare((GenericInstantiation) o1, (GenericInstantiation) o2);

      case GlobalDeclaration.DESCRIPTOR:
        return compare((GlobalDeclaration) o1, (GlobalDeclaration) o2);

      case GlobalDefinition.DESCRIPTOR:
        return compare((GlobalDefinition) o1, (GlobalDefinition) o2);

      case IDAttributeDesignator.DESCRIPTOR:
        return compare((IDAttributeDesignator) o1, (IDAttributeDesignator) o2);

      case IDName.DESCRIPTOR:
        return compare((IDName) o1, (IDName) o2);

      case IfStatement.DESCRIPTOR:
        return compare((IfStatement) o1, (IfStatement) o2);

      case InRangeExp.DESCRIPTOR:
        return compare((InRangeExp) o1, (InRangeExp) o2);

      case IndexConstraint.DESCRIPTOR:
        return compare((IndexConstraint) o1, (IndexConstraint) o2);

      case IndexedComponent.DESCRIPTOR:
        return compare((IndexedComponent) o1, (IndexedComponent) o2);

      case JustificationClause.DESCRIPTOR:
        return compare((JustificationClause) o1, (JustificationClause) o2);

      case JustificationStatement.DESCRIPTOR:
        return compare((JustificationStatement) o1, (JustificationStatement) o2);

      case JustificationStatementEnd.DESCRIPTOR:
        return compare(
            (JustificationStatementEnd) o1,
            (JustificationStatementEnd) o2);

      case LibraryCompilationUnit.DESCRIPTOR:
        return compare((LibraryCompilationUnit) o1, (LibraryCompilationUnit) o2);

      case LibraryUnitBody.DESCRIPTOR:
        return compare((LibraryUnitBody) o1, (LibraryUnitBody) o2);

      case LiteralExp.DESCRIPTOR:
        return compare((LiteralExp) o1, (LiteralExp) o2);

      case MainProgram.DESCRIPTOR:
        return compare((MainProgram) o1, (MainProgram) o2);

      case ModularTypeDefinition.DESCRIPTOR:
        return compare((ModularTypeDefinition) o1, (ModularTypeDefinition) o2);

      case NameExp.DESCRIPTOR:
        return compare((NameExp) o1, (NameExp) o2);

      case NamePragmaArgumentAssociation.DESCRIPTOR:
        return compare(
            (NamePragmaArgumentAssociation) o1,
            (NamePragmaArgumentAssociation) o2);

      case NameRangeExp.DESCRIPTOR:
        return compare((NameRangeExp) o1, (NameRangeExp) o2);

      case NamedArrayAggregate.DESCRIPTOR:
        return compare((NamedArrayAggregate) o1, (NamedArrayAggregate) o2);

      case NamedParameterAssociation.DESCRIPTOR:
        return compare(
            (NamedParameterAssociation) o1,
            (NamedParameterAssociation) o2);

      case NamedParameterAssociationList.DESCRIPTOR:
        return compare(
            (NamedParameterAssociationList) o1,
            (NamedParameterAssociationList) o2);

      case NamedRecordAggregate.DESCRIPTOR:
        return compare((NamedRecordAggregate) o1, (NamedRecordAggregate) o2);

      case NamedRecordExtensionAggregate.DESCRIPTOR:
        return compare(
            (NamedRecordExtensionAggregate) o1,
            (NamedRecordExtensionAggregate) o2);

      case NullRecordExtensionAggregate.DESCRIPTOR:
        return compare(
            (NullRecordExtensionAggregate) o1,
            (NullRecordExtensionAggregate) o2);

      case NullStatement.DESCRIPTOR:
        return compare((NullStatement) o1, (NullStatement) o2);

      case NumberDeclaration.DESCRIPTOR:
        return compare((NumberDeclaration) o1, (NumberDeclaration) o2);

      case NumericLiteral.DESCRIPTOR:
        return compare((NumericLiteral) o1, (NumericLiteral) o2);

      case ObjectDeclaration.DESCRIPTOR:
        return compare((ObjectDeclaration) o1, (ObjectDeclaration) o2);

      case OrdinaryFixedPointDefinition.DESCRIPTOR:
        return compare(
            (OrdinaryFixedPointDefinition) o1,
            (OrdinaryFixedPointDefinition) o2);

      case OwnVariable.DESCRIPTOR:
        return compare((OwnVariable) o1, (OwnVariable) o2);

      case OwnVariableSpecification.DESCRIPTOR:
        return compare(
            (OwnVariableSpecification) o1,
            (OwnVariableSpecification) o2);

      case PackageAnnotation.DESCRIPTOR:
        return compare((PackageAnnotation) o1, (PackageAnnotation) o2);

      case PackageBody.DESCRIPTOR:
        return compare((PackageBody) o1, (PackageBody) o2);

      case PackageBodyStub.DESCRIPTOR:
        return compare((PackageBodyStub) o1, (PackageBodyStub) o2);

      case PackageDeclaration.DESCRIPTOR:
        return compare((PackageDeclaration) o1, (PackageDeclaration) o2);

      case PackageImplementation.DESCRIPTOR:
        return compare((PackageImplementation) o1, (PackageImplementation) o2);

      case PackageRenamingDeclaration.DESCRIPTOR:
        return compare(
            (PackageRenamingDeclaration) o1,
            (PackageRenamingDeclaration) o2);

      case PackageSpecification.DESCRIPTOR:
        return compare((PackageSpecification) o1, (PackageSpecification) o2);

      case ParameterSpecification.DESCRIPTOR:
        return compare((ParameterSpecification) o1, (ParameterSpecification) o2);

      case ParenExp.DESCRIPTOR:
        return compare((ParenExp) o1, (ParenExp) o2);

      case PositionalArrayAggregate.DESCRIPTOR:
        return compare(
            (PositionalArrayAggregate) o1,
            (PositionalArrayAggregate) o2);

      case PositionalParameterAssociationList.DESCRIPTOR:
        return compare(
            (PositionalParameterAssociationList) o1,
            (PositionalParameterAssociationList) o2);

      case PositionalRecordAggregate.DESCRIPTOR:
        return compare(
            (PositionalRecordAggregate) o1,
            (PositionalRecordAggregate) o2);

      case PositionalRecordExtensionAggregate.DESCRIPTOR:
        return compare(
            (PositionalRecordExtensionAggregate) o1,
            (PositionalRecordExtensionAggregate) o2);

      case Postcondition.DESCRIPTOR:
        return compare((Postcondition) o1, (Postcondition) o2);

      case Pragma.DESCRIPTOR:
        return compare((Pragma) o1, (Pragma) o2);

      case Precondition.DESCRIPTOR:
        return compare((Precondition) o1, (Precondition) o2);

      case Predicate.DESCRIPTOR:
        return compare((Predicate) o1, (Predicate) o2);

      case PrivateExtensionDeclaration.DESCRIPTOR:
        return compare(
            (PrivateExtensionDeclaration) o1,
            (PrivateExtensionDeclaration) o2);

      case PrivateTypeDeclaration.DESCRIPTOR:
        return compare((PrivateTypeDeclaration) o1, (PrivateTypeDeclaration) o2);

      case ProcedureAnnotation.DESCRIPTOR:
        return compare((ProcedureAnnotation) o1, (ProcedureAnnotation) o2);

      case ProcedureBodyStub.DESCRIPTOR:
        return compare((ProcedureBodyStub) o1, (ProcedureBodyStub) o2);

      case ProcedureCallStatement.DESCRIPTOR:
        return compare((ProcedureCallStatement) o1, (ProcedureCallStatement) o2);

      case ProcedureSpecification.DESCRIPTOR:
        return compare((ProcedureSpecification) o1, (ProcedureSpecification) o2);

      case ProcedureSpecificationRenamingDeclaration.DESCRIPTOR:
        return compare(
            (ProcedureSpecificationRenamingDeclaration) o1,
            (ProcedureSpecificationRenamingDeclaration) o2);

      case ProcedureSubProgramBody.DESCRIPTOR:
        return compare(
            (ProcedureSubProgramBody) o1,
            (ProcedureSubProgramBody) o2);

      case ProcedureSubProgramDeclaration.DESCRIPTOR:
        return compare(
            (ProcedureSubProgramDeclaration) o1,
            (ProcedureSubProgramDeclaration) o2);

      case ProofFunctionDeclaration.DESCRIPTOR:
        return compare(
            (ProofFunctionDeclaration) o1,
            (ProofFunctionDeclaration) o2);

      case ProofTypeDeclaration.DESCRIPTOR:
        return compare((ProofTypeDeclaration) o1, (ProofTypeDeclaration) o2);

      case QuantifiedExp.DESCRIPTOR:
        return compare((QuantifiedExp) o1, (QuantifiedExp) o2);

      case RangeAttributeDesignator.DESCRIPTOR:
        return compare(
            (RangeAttributeDesignator) o1,
            (RangeAttributeDesignator) o2);

      case RangeAttributeReference.DESCRIPTOR:
        return compare(
            (RangeAttributeReference) o1,
            (RangeAttributeReference) o2);

      case RangeChoice.DESCRIPTOR:
        return compare((RangeChoice) o1, (RangeChoice) o2);

      case RangeConstraint.DESCRIPTOR:
        return compare((RangeConstraint) o1, (RangeConstraint) o2);

      case RecordComponentAssociation.DESCRIPTOR:
        return compare(
            (RecordComponentAssociation) o1,
            (RecordComponentAssociation) o2);

      case RecordDefinition.DESCRIPTOR:
        return compare((RecordDefinition) o1, (RecordDefinition) o2);

      case RecordRepresentationClause.DESCRIPTOR:
        return compare(
            (RecordRepresentationClause) o1,
            (RecordRepresentationClause) o2);

      case RecordTypeDefinition.DESCRIPTOR:
        return compare((RecordTypeDefinition) o1, (RecordTypeDefinition) o2);

      case RecordTypeExtension.DESCRIPTOR:
        return compare((RecordTypeExtension) o1, (RecordTypeExtension) o2);

      case RecordUpdate.DESCRIPTOR:
        return compare((RecordUpdate) o1, (RecordUpdate) o2);

      case RefinementClause.DESCRIPTOR:
        return compare((RefinementClause) o1, (RefinementClause) o2);

      case RenamingDeclarationEmbeddedPackageDeclarationMember.DESCRIPTOR:
        return compare(
            (RenamingDeclarationEmbeddedPackageDeclarationMember) o1,
            (RenamingDeclarationEmbeddedPackageDeclarationMember) o2);

      case RepresentationClauseBasicDeclarativeItem.DESCRIPTOR:
        return compare(
            (RepresentationClauseBasicDeclarativeItem) o1,
            (RepresentationClauseBasicDeclarativeItem) o2);

      case ReturnAnnotationExp.DESCRIPTOR:
        return compare((ReturnAnnotationExp) o1, (ReturnAnnotationExp) o2);

      case ReturnAnnotationPred.DESCRIPTOR:
        return compare((ReturnAnnotationPred) o1, (ReturnAnnotationPred) o2);

      case ReturnStatement.DESCRIPTOR:
        return compare((ReturnStatement) o1, (ReturnStatement) o2);

      case SelectedComponent.DESCRIPTOR:
        return compare((SelectedComponent) o1, (SelectedComponent) o2);

      case SignedIntegerTypeDefinition.DESCRIPTOR:
        return compare(
            (SignedIntegerTypeDefinition) o1,
            (SignedIntegerTypeDefinition) o2);

      case StatementList.DESCRIPTOR:
        return compare((StatementList) o1, (StatementList) o2);

      case StatementSubProgramImplementation.DESCRIPTOR:
        return compare(
            (StatementSubProgramImplementation) o1,
            (StatementSubProgramImplementation) o2);

      case StringLiteral.DESCRIPTOR:
        return compare((StringLiteral) o1, (StringLiteral) o2);

      case SubTypeChoice.DESCRIPTOR:
        return compare((SubTypeChoice) o1, (SubTypeChoice) o2);

      case SubTypeDeclaration.DESCRIPTOR:
        return compare((SubTypeDeclaration) o1, (SubTypeDeclaration) o2);

      case SubTypeIndication.DESCRIPTOR:
        return compare((SubTypeIndication) o1, (SubTypeIndication) o2);

      case SubUnitCompilationUnit.DESCRIPTOR:
        return compare((SubUnitCompilationUnit) o1, (SubUnitCompilationUnit) o2);

      case TypeAssertion.DESCRIPTOR:
        return compare((TypeAssertion) o1, (TypeAssertion) o2);

      case TypeConversion.DESCRIPTOR:
        return compare((TypeConversion) o1, (TypeConversion) o2);

      case UnaryExp.DESCRIPTOR:
        return compare((UnaryExp) o1, (UnaryExp) o2);

      case UnconstrainedArrayDefinition.DESCRIPTOR:
        return compare(
            (UnconstrainedArrayDefinition) o1,
            (UnconstrainedArrayDefinition) o2);

      case UseTypeClause.DESCRIPTOR:
        return compare((UseTypeClause) o1, (UseTypeClause) o2);

      case UseTypeClauseEmbeddedPackageDeclarationMember.DESCRIPTOR:
        return compare(
            (UseTypeClauseEmbeddedPackageDeclarationMember) o1,
            (UseTypeClauseEmbeddedPackageDeclarationMember) o2);

      case WhileLoopStatement.DESCRIPTOR:
        return compare((WhileLoopStatement) o1, (WhileLoopStatement) o2);

      case WithClause.DESCRIPTOR:
        return compare((WithClause) o1, (WithClause) o2);
    }

    return 0;
  }

  public int compare(final NullRecordExtensionAggregate o1,
      final NullRecordExtensionAggregate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final NullStatement o1, final NullStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final NumberDeclaration o1, final NumberDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<IDName> theIDNames1 = o1.theIDNames;
      final ArrayList<IDName> theIDNames2 = o2.theIDNames;

      {
        if (theIDNames1 == theIDNames2) {
        } else {
          final int count0 = theIDNames1.size();
          result = compare(count0, theIDNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final IDName e0_1 = theIDNames1.get(i0);
            final IDName e0_2 = theIDNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final NumericLiteral o1, final NumericLiteral o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theNumberString == o2.theNumberString) {
    } else {
      result = compare(o1.theNumberString, o2.theNumberString);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public int compare(final Object o1, final Object o2) {
    if (o1 == o2) {
      return 0;
    } else if (o1 == null) {
      return -1;
    } else if (o2 == null) {
      return 1;
    }

    final int result = compare(o1.getClass().getName(), o2.getClass().getName());
    if (result != 0) {
      return result;
    }

    if (o1 instanceof Node) {
      return compare((Node) o1, (Node) o2);
    } else if (o1 instanceof String) {
      return compare((String) o1, (String) o2);
    } else if (o1 instanceof List) {
      return compare((List) o1, (List) o2);
    } else if (o1 instanceof Tuple) {
      return compare((Tuple) o1, (Tuple) o2);
    } else if (o1 instanceof Set) {
      return compare((Set) o1, (Set) o2);
    } else if (o1 instanceof Map) {
      return compare((Map) o1, (Map) o2);
    } else if (o1 instanceof Boolean) {
      return compare((Boolean) o1, (Boolean) o2);
    } else if (o1 instanceof Byte) {
      return compare((Byte) o1, (Byte) o2);
    } else if (o1 instanceof Character) {
      return compare((Character) o1, (Character) o2);
    } else if (o1 instanceof Short) {
      return compare((Short) o1, (Short) o2);
    } else if (o1 instanceof Integer) {
      return compare((Integer) o1, (Integer) o2);
    } else if (o1 instanceof Long) {
      return compare((Long) o1, (Long) o2);
    } else if (o1 instanceof Float) {
      return compare((Float) o1, (Float) o2);
    } else if (o1 instanceof Double) {
      return compare((Double) o1, (Double) o2);
    } else {
      return unhandledCompare(o1, o2);
    }
  }

  public int compare(final ObjectDeclaration o1, final ObjectDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theConstantFlag == o2.theConstantFlag) {
    } else {
      result = compare(o1.theConstantFlag, o2.theConstantFlag);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theSubtypeMark == o2.theSubtypeMark) {
    } else {
      result = compare(o1.theSubtypeMark, o2.theSubtypeMark);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalInitializingExp == o2.theOptionalInitializingExp) {
    } else if (o1.theOptionalInitializingExp == null) {
      return -1;
    } else if (o2.theOptionalInitializingExp == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalInitializingExp,
          o2.theOptionalInitializingExp);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<IDName> theDefiningIdentifierList1 = o1.theDefiningIdentifierList;
      final ArrayList<IDName> theDefiningIdentifierList2 = o2.theDefiningIdentifierList;

      {
        if (theDefiningIdentifierList1 == theDefiningIdentifierList2) {
        } else {
          final int count0 = theDefiningIdentifierList1.size();
          result = compare(count0, theDefiningIdentifierList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final IDName e0_1 = theDefiningIdentifierList1.get(i0);
            final IDName e0_2 = theDefiningIdentifierList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final OrdinaryFixedPointDefinition o1,
      final OrdinaryFixedPointDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theLowRangeExp == o2.theLowRangeExp) {
    } else {
      result = compare(o1.theLowRangeExp, o2.theLowRangeExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theHighRangeExp == o2.theHighRangeExp) {
    } else {
      result = compare(o1.theHighRangeExp, o2.theHighRangeExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final OwnVariable o1, final OwnVariable o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theMode == o2.theMode) {
    } else {
      result = compare(o1.theMode, o2.theMode);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final OwnVariableSpecification o1,
      final OwnVariableSpecification o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theOptionalSubtypeMark == o2.theOptionalSubtypeMark) {
    } else if (o1.theOptionalSubtypeMark == null) {
      return -1;
    } else if (o2.theOptionalSubtypeMark == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalSubtypeMark, o2.theOptionalSubtypeMark);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<OwnVariable> theOwnVariables1 = o1.theOwnVariables;
      final ArrayList<OwnVariable> theOwnVariables2 = o2.theOwnVariables;

      {
        if (theOwnVariables1 == theOwnVariables2) {
        } else {
          final int count0 = theOwnVariables1.size();
          result = compare(count0, theOwnVariables2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final OwnVariable e0_1 = theOwnVariables1.get(i0);
            final OwnVariable e0_2 = theOwnVariables2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final PackageAnnotation o1, final PackageAnnotation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<OwnVariableSpecification> theOptionalOwnVariables1 = o1.theOptionalOwnVariables;
      final ArrayList<OwnVariableSpecification> theOptionalOwnVariables2 = o2.theOptionalOwnVariables;

      {
        if (theOptionalOwnVariables1 == theOptionalOwnVariables2) {
        } else if (theOptionalOwnVariables1 == null) {
          return -1;
        } else if (theOptionalOwnVariables2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalOwnVariables1.size();
          result = compare(count0, theOptionalOwnVariables2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final OwnVariableSpecification e0_1 = theOptionalOwnVariables1
                .get(i0);
            final OwnVariableSpecification e0_2 = theOptionalOwnVariables2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<OwnVariable> theOptionalInitializeVariables1 = o1.theOptionalInitializeVariables;
      final ArrayList<OwnVariable> theOptionalInitializeVariables2 = o2.theOptionalInitializeVariables;

      {
        if (theOptionalInitializeVariables1 == theOptionalInitializeVariables2) {
        } else if (theOptionalInitializeVariables1 == null) {
          return -1;
        } else if (theOptionalInitializeVariables2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalInitializeVariables1.size();
          result = compare(count0, theOptionalInitializeVariables2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final OwnVariable e0_1 = theOptionalInitializeVariables1.get(i0);
            final OwnVariable e0_2 = theOptionalInitializeVariables2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final PackageBody o1, final PackageBody o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePackageImplementation == o2.thePackageImplementation) {
    } else {
      result = compare(o1.thePackageImplementation, o2.thePackageImplementation);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<RefinementClause> theRefinementClauses1 = o1.theRefinementClauses;
      final ArrayList<RefinementClause> theRefinementClauses2 = o2.theRefinementClauses;

      {
        if (theRefinementClauses1 == theRefinementClauses2) {
        } else {
          final int count0 = theRefinementClauses1.size();
          result = compare(count0, theRefinementClauses2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final RefinementClause e0_1 = theRefinementClauses1.get(i0);
            final RefinementClause e0_2 = theRefinementClauses2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final PackageBodyStub o1, final PackageBodyStub o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final PackageDeclaration o1, final PackageDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.thePrivateFlag == o2.thePrivateFlag) {
    } else {
      result = compare(o1.thePrivateFlag, o2.thePrivateFlag);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePackageSpecification == o2.thePackageSpecification) {
    } else {
      result = compare(o1.thePackageSpecification, o2.thePackageSpecification);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Name> theOptionalInheritClauses1 = o1.theOptionalInheritClauses;
      final ArrayList<Name> theOptionalInheritClauses2 = o2.theOptionalInheritClauses;

      {
        if (theOptionalInheritClauses1 == theOptionalInheritClauses2) {
        } else if (theOptionalInheritClauses1 == null) {
          return -1;
        } else if (theOptionalInheritClauses2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalInheritClauses1.size();
          result = compare(count0, theOptionalInheritClauses2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theOptionalInheritClauses1.get(i0);
            final Name e0_2 = theOptionalInheritClauses2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final PackageImplementation o1,
      final PackageImplementation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theDeclarativePart == o2.theDeclarativePart) {
    } else {
      result = compare(o1.theDeclarativePart, o2.theDeclarativePart);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalStatementList == o2.theOptionalStatementList) {
    } else if (o1.theOptionalStatementList == null) {
      return -1;
    } else if (o2.theOptionalStatementList == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalStatementList, o2.theOptionalStatementList);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final PackageRenamingDeclaration o1,
      final PackageRenamingDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.thePackageName == o2.thePackageName) {
    } else {
      result = compare(o1.thePackageName, o2.thePackageName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theRenamedName == o2.theRenamedName) {
    } else {
      result = compare(o1.theRenamedName, o2.theRenamedName);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final PackageSpecification o1,
      final PackageSpecification o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePackageAnnotation == o2.thePackageAnnotation) {
    } else {
      result = compare(o1.thePackageAnnotation, o2.thePackageAnnotation);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<RenamingDeclaration> theOptionalVisiblePartDeclaration1 = o1.theOptionalVisiblePartDeclaration;
      final ArrayList<RenamingDeclaration> theOptionalVisiblePartDeclaration2 = o2.theOptionalVisiblePartDeclaration;

      {
        if (theOptionalVisiblePartDeclaration1 == theOptionalVisiblePartDeclaration2) {
        } else if (theOptionalVisiblePartDeclaration1 == null) {
          return -1;
        } else if (theOptionalVisiblePartDeclaration2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalVisiblePartDeclaration1.size();
          result = compare(count0, theOptionalVisiblePartDeclaration2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final RenamingDeclaration e0_1 = theOptionalVisiblePartDeclaration1
                .get(i0);
            final RenamingDeclaration e0_2 = theOptionalVisiblePartDeclaration2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<DeclarativePartMember> theOptionalVisiblePartDeclarativePartMember1 = o1.theOptionalVisiblePartDeclarativePartMember;
      final ArrayList<DeclarativePartMember> theOptionalVisiblePartDeclarativePartMember2 = o2.theOptionalVisiblePartDeclarativePartMember;

      {
        if (theOptionalVisiblePartDeclarativePartMember1 == theOptionalVisiblePartDeclarativePartMember2) {
        } else if (theOptionalVisiblePartDeclarativePartMember1 == null) {
          return -1;
        } else if (theOptionalVisiblePartDeclarativePartMember2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalVisiblePartDeclarativePartMember1
              .size();
          result = compare(
              count0,
              theOptionalVisiblePartDeclarativePartMember2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final DeclarativePartMember e0_1 = theOptionalVisiblePartDeclarativePartMember1
                .get(i0);
            final DeclarativePartMember e0_2 = theOptionalVisiblePartDeclarativePartMember2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<RenamingDeclaration> theOptionalPrivatePartDeclaration1 = o1.theOptionalPrivatePartDeclaration;
      final ArrayList<RenamingDeclaration> theOptionalPrivatePartDeclaration2 = o2.theOptionalPrivatePartDeclaration;

      {
        if (theOptionalPrivatePartDeclaration1 == theOptionalPrivatePartDeclaration2) {
        } else if (theOptionalPrivatePartDeclaration1 == null) {
          return -1;
        } else if (theOptionalPrivatePartDeclaration2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalPrivatePartDeclaration1.size();
          result = compare(count0, theOptionalPrivatePartDeclaration2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final RenamingDeclaration e0_1 = theOptionalPrivatePartDeclaration1
                .get(i0);
            final RenamingDeclaration e0_2 = theOptionalPrivatePartDeclaration2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    {
      final ArrayList<DeclarativePartMember> theOptionalPrivatePartDeclarativePartMember1 = o1.theOptionalPrivatePartDeclarativePartMember;
      final ArrayList<DeclarativePartMember> theOptionalPrivatePartDeclarativePartMember2 = o2.theOptionalPrivatePartDeclarativePartMember;

      {
        if (theOptionalPrivatePartDeclarativePartMember1 == theOptionalPrivatePartDeclarativePartMember2) {
        } else if (theOptionalPrivatePartDeclarativePartMember1 == null) {
          return -1;
        } else if (theOptionalPrivatePartDeclarativePartMember2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalPrivatePartDeclarativePartMember1
              .size();
          result = compare(
              count0,
              theOptionalPrivatePartDeclarativePartMember2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final DeclarativePartMember e0_1 = theOptionalPrivatePartDeclarativePartMember1
                .get(i0);
            final DeclarativePartMember e0_2 = theOptionalPrivatePartDeclarativePartMember2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final ParameterSpecification o1,
      final ParameterSpecification o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theMode == o2.theMode) {
    } else if (o1.theMode == null) {
      return -1;
    } else if (o2.theMode == null) {
      return 1;
    } else {
      result = compare(o1.theMode, o2.theMode);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theSubtypeMark == o2.theSubtypeMark) {
    } else {
      result = compare(o1.theSubtypeMark, o2.theSubtypeMark);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<IDName> theParameterNames1 = o1.theParameterNames;
      final ArrayList<IDName> theParameterNames2 = o2.theParameterNames;

      {
        if (theParameterNames1 == theParameterNames2) {
        } else {
          final int count0 = theParameterNames1.size();
          result = compare(count0, theParameterNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final IDName e0_1 = theParameterNames1.get(i0);
            final IDName e0_2 = theParameterNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final ParenExp o1, final ParenExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final PositionalArrayAggregate o1,
      final PositionalArrayAggregate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theOthersFlag == o2.theOthersFlag) {
    } else {
      result = compare(o1.theOthersFlag, o2.theOthersFlag);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalOthersAggregateItem == o2.theOptionalOthersAggregateItem) {
    } else if (o1.theOptionalOthersAggregateItem == null) {
      return -1;
    } else if (o2.theOptionalOthersAggregateItem == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalOthersAggregateItem,
          o2.theOptionalOthersAggregateItem);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<AggregateItem> theAggregateItems1 = o1.theAggregateItems;
      final ArrayList<AggregateItem> theAggregateItems2 = o2.theAggregateItems;

      {
        if (theAggregateItems1 == theAggregateItems2) {
        } else {
          final int count0 = theAggregateItems1.size();
          result = compare(count0, theAggregateItems2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final AggregateItem e0_1 = theAggregateItems1.get(i0);
            final AggregateItem e0_2 = theAggregateItems2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final PositionalParameterAssociationList o1,
      final PositionalParameterAssociationList o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<Exp> theParameterAssociations1 = o1.theParameterAssociations;
      final ArrayList<Exp> theParameterAssociations2 = o2.theParameterAssociations;

      {
        if (theParameterAssociations1 == theParameterAssociations2) {
        } else {
          final int count0 = theParameterAssociations1.size();
          result = compare(count0, theParameterAssociations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Exp e0_1 = theParameterAssociations1.get(i0);
            final Exp e0_2 = theParameterAssociations2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final PositionalRecordAggregate o1,
      final PositionalRecordAggregate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<Exp> theExps1 = o1.theExps;
      final ArrayList<Exp> theExps2 = o2.theExps;

      {
        if (theExps1 == theExps2) {
        } else {
          final int count0 = theExps1.size();
          result = compare(count0, theExps2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Exp e0_1 = theExps1.get(i0);
            final Exp e0_2 = theExps2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final PositionalRecordExtensionAggregate o1,
      final PositionalRecordExtensionAggregate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Exp> theExps1 = o1.theExps;
      final ArrayList<Exp> theExps2 = o2.theExps;

      {
        if (theExps1 == theExps2) {
        } else {
          final int count0 = theExps1.size();
          result = compare(count0, theExps2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Exp e0_1 = theExps1.get(i0);
            final Exp e0_2 = theExps2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final Postcondition o1, final Postcondition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.thePredicate == o2.thePredicate) {
    } else {
      result = compare(o1.thePredicate, o2.thePredicate);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final Pragma o1, final Pragma o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.dummyObjectToGetListVisitor == o2.dummyObjectToGetListVisitor) {
    } else if (o1.dummyObjectToGetListVisitor == null) {
      return -1;
    } else if (o2.dummyObjectToGetListVisitor == null) {
      return 1;
    } else {
      result = compare(
          o1.dummyObjectToGetListVisitor,
          o2.dummyObjectToGetListVisitor);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<PragmaArgumentAssociation> theOptionalPragmaArgumentAssociations1 = o1.theOptionalPragmaArgumentAssociations;
      final ArrayList<PragmaArgumentAssociation> theOptionalPragmaArgumentAssociations2 = o2.theOptionalPragmaArgumentAssociations;

      {
        if (theOptionalPragmaArgumentAssociations1 == theOptionalPragmaArgumentAssociations2) {
        } else if (theOptionalPragmaArgumentAssociations1 == null) {
          return -1;
        } else if (theOptionalPragmaArgumentAssociations2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalPragmaArgumentAssociations1.size();
          result = compare(
              count0,
              theOptionalPragmaArgumentAssociations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final PragmaArgumentAssociation e0_1 = theOptionalPragmaArgumentAssociations1
                .get(i0);
            final PragmaArgumentAssociation e0_2 = theOptionalPragmaArgumentAssociations2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final Precondition o1, final Precondition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.thePredicate == o2.thePredicate) {
    } else {
      result = compare(o1.thePredicate, o2.thePredicate);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final Predicate o1, final Predicate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final PrivateExtensionDeclaration o1,
      final PrivateExtensionDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theSubTypeIndication == o2.theSubTypeIndication) {
    } else {
      result = compare(o1.theSubTypeIndication, o2.theSubTypeIndication);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final PrivateTypeDeclaration o1,
      final PrivateTypeDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theTaggedFlag == o2.theTaggedFlag) {
    } else {
      result = compare(o1.theTaggedFlag, o2.theTaggedFlag);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theLimitedFlag == o2.theLimitedFlag) {
    } else {
      result = compare(o1.theLimitedFlag, o2.theLimitedFlag);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ProcedureAnnotation o1, final ProcedureAnnotation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theOptionalGlobalDefinition == o2.theOptionalGlobalDefinition) {
    } else if (o1.theOptionalGlobalDefinition == null) {
      return -1;
    } else if (o2.theOptionalGlobalDefinition == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalGlobalDefinition,
          o2.theOptionalGlobalDefinition);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalDependency == o2.theOptionalDependency) {
    } else if (o1.theOptionalDependency == null) {
      return -1;
    } else if (o2.theOptionalDependency == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalDependency, o2.theOptionalDependency);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalPrecondition == o2.theOptionalPrecondition) {
    } else if (o1.theOptionalPrecondition == null) {
      return -1;
    } else if (o2.theOptionalPrecondition == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalPrecondition, o2.theOptionalPrecondition);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalPostcondition == o2.theOptionalPostcondition) {
    } else if (o1.theOptionalPostcondition == null) {
      return -1;
    } else if (o2.theOptionalPostcondition == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalPostcondition, o2.theOptionalPostcondition);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ProcedureBodyStub o1, final ProcedureBodyStub o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theProcedureSpecification == o2.theProcedureSpecification) {
    } else {
      result = compare(
          o1.theProcedureSpecification,
          o2.theProcedureSpecification);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalProcedureAnnotation == o2.theOptionalProcedureAnnotation) {
    } else if (o1.theOptionalProcedureAnnotation == null) {
      return -1;
    } else if (o2.theOptionalProcedureAnnotation == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalProcedureAnnotation,
          o2.theOptionalProcedureAnnotation);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ProcedureCallStatement o1,
      final ProcedureCallStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalParameterAssociationList == o2.theOptionalParameterAssociationList) {
    } else if (o1.theOptionalParameterAssociationList == null) {
      return -1;
    } else if (o2.theOptionalParameterAssociationList == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalParameterAssociationList,
          o2.theOptionalParameterAssociationList);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final ProcedureSpecification o1,
      final ProcedureSpecification o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theMethodNameSelection == o2.theMethodNameSelection) {
    } else {
      result = compare(o1.theMethodNameSelection, o2.theMethodNameSelection);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<ParameterSpecification> theOptionalParameterSpecification1 = o1.theOptionalParameterSpecification;
      final ArrayList<ParameterSpecification> theOptionalParameterSpecification2 = o2.theOptionalParameterSpecification;

      {
        if (theOptionalParameterSpecification1 == theOptionalParameterSpecification2) {
        } else if (theOptionalParameterSpecification1 == null) {
          return -1;
        } else if (theOptionalParameterSpecification2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalParameterSpecification1.size();
          result = compare(count0, theOptionalParameterSpecification2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final ParameterSpecification e0_1 = theOptionalParameterSpecification1
                .get(i0);
            final ParameterSpecification e0_2 = theOptionalParameterSpecification2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final ProcedureSpecificationRenamingDeclaration o1,
      final ProcedureSpecificationRenamingDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theProcedureSpecification == o2.theProcedureSpecification) {
    } else {
      result = compare(
          o1.theProcedureSpecification,
          o2.theProcedureSpecification);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ProcedureSubProgramBody o1,
      final ProcedureSubProgramBody o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theSubProgramImplementation == o2.theSubProgramImplementation) {
    } else {
      result = compare(
          o1.theSubProgramImplementation,
          o2.theSubProgramImplementation);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theProcedureSpecification == o2.theProcedureSpecification) {
    } else {
      result = compare(
          o1.theProcedureSpecification,
          o2.theProcedureSpecification);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalProcedureAnnotation == o2.theOptionalProcedureAnnotation) {
    } else if (o1.theOptionalProcedureAnnotation == null) {
      return -1;
    } else if (o2.theOptionalProcedureAnnotation == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalProcedureAnnotation,
          o2.theOptionalProcedureAnnotation);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ProcedureSubProgramDeclaration o1,
      final ProcedureSubProgramDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theProcedureSpecification == o2.theProcedureSpecification) {
    } else {
      result = compare(
          o1.theProcedureSpecification,
          o2.theProcedureSpecification);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theProcedureAnnotation == o2.theProcedureAnnotation) {
    } else {
      result = compare(o1.theProcedureAnnotation, o2.theProcedureAnnotation);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ProofFunctionDeclaration o1,
      final ProofFunctionDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theFunctionSpecification == o2.theFunctionSpecification) {
    } else {
      result = compare(o1.theFunctionSpecification, o2.theFunctionSpecification);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ProofTypeDeclaration o1,
      final ProofTypeDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theDefiningIdentifier == o2.theDefiningIdentifier) {
    } else {
      result = compare(o1.theDefiningIdentifier, o2.theDefiningIdentifier);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final QuantifiedExp o1, final QuantifiedExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIdentifier == o2.theIdentifier) {
    } else {
      result = compare(o1.theIdentifier, o2.theIdentifier);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theKind == o2.theKind) {
    } else {
      result = compare(o1.theKind, o2.theKind);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theSubTypeMark == o2.theSubTypeMark) {
    } else if (o1.theSubTypeMark == null) {
      return -1;
    } else if (o2.theSubTypeMark == null) {
      return 1;
    } else {
      result = compare(o1.theSubTypeMark, o2.theSubTypeMark);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalRange == o2.theOptionalRange) {
    } else if (o1.theOptionalRange == null) {
      return -1;
    } else if (o2.theOptionalRange == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalRange, o2.theOptionalRange);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePredicate == o2.thePredicate) {
    } else {
      result = compare(o1.thePredicate, o2.thePredicate);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final RangeAttributeDesignator o1,
      final RangeAttributeDesignator o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theOptionalExp == o2.theOptionalExp) {
    } else if (o1.theOptionalExp == null) {
      return -1;
    } else if (o2.theOptionalExp == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalExp, o2.theOptionalExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final RangeAttributeReference o1,
      final RangeAttributeReference o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theDesignator == o2.theDesignator) {
    } else if (o1.theDesignator == null) {
      return -1;
    } else if (o2.theDesignator == null) {
      return 1;
    } else {
      result = compare(o1.theDesignator, o2.theDesignator);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final RangeChoice o1, final RangeChoice o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theRange == o2.theRange) {
    } else {
      result = compare(o1.theRange, o2.theRange);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final RangeConstraint o1, final RangeConstraint o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theRange == o2.theRange) {
    } else {
      result = compare(o1.theRange, o2.theRange);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final RecordComponentAssociation o1,
      final RecordComponentAssociation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final RecordDefinition o1, final RecordDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theNullRecordFlag == o2.theNullRecordFlag) {
    } else {
      result = compare(o1.theNullRecordFlag, o2.theNullRecordFlag);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<ComponentDeclaration> theOptionalComponentDeclarations1 = o1.theOptionalComponentDeclarations;
      final ArrayList<ComponentDeclaration> theOptionalComponentDeclarations2 = o2.theOptionalComponentDeclarations;

      {
        if (theOptionalComponentDeclarations1 == theOptionalComponentDeclarations2) {
        } else if (theOptionalComponentDeclarations1 == null) {
          return -1;
        } else if (theOptionalComponentDeclarations2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalComponentDeclarations1.size();
          result = compare(count0, theOptionalComponentDeclarations2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final ComponentDeclaration e0_1 = theOptionalComponentDeclarations1
                .get(i0);
            final ComponentDeclaration e0_2 = theOptionalComponentDeclarations2
                .get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final RecordRepresentationClause o1,
      final RecordRepresentationClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalExp == o2.theOptionalExp) {
    } else if (o1.theOptionalExp == null) {
      return -1;
    } else if (o2.theOptionalExp == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalExp, o2.theOptionalExp);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<ComponentClause> theOptionalComponentClauses1 = o1.theOptionalComponentClauses;
      final ArrayList<ComponentClause> theOptionalComponentClauses2 = o2.theOptionalComponentClauses;

      {
        if (theOptionalComponentClauses1 == theOptionalComponentClauses2) {
        } else if (theOptionalComponentClauses1 == null) {
          return -1;
        } else if (theOptionalComponentClauses2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalComponentClauses1.size();
          result = compare(count0, theOptionalComponentClauses2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final ComponentClause e0_1 = theOptionalComponentClauses1.get(i0);
            final ComponentClause e0_2 = theOptionalComponentClauses2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final RecordTypeDefinition o1,
      final RecordTypeDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theTaggedFlag == o2.theTaggedFlag) {
    } else {
      result = compare(o1.theTaggedFlag, o2.theTaggedFlag);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theRecordDefinition == o2.theRecordDefinition) {
    } else {
      result = compare(o1.theRecordDefinition, o2.theRecordDefinition);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final RecordTypeExtension o1, final RecordTypeExtension o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theSubtypeMark == o2.theSubtypeMark) {
    } else {
      result = compare(o1.theSubtypeMark, o2.theSubtypeMark);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theRecordDefinition == o2.theRecordDefinition) {
    } else {
      result = compare(o1.theRecordDefinition, o2.theRecordDefinition);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final RecordUpdate o1, final RecordUpdate o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Pair<String, Exp>> theSelectorToExpressionList1 = o1.theSelectorToExpressionList;
      final ArrayList<Pair<String, Exp>> theSelectorToExpressionList2 = o2.theSelectorToExpressionList;

      {
        if (theSelectorToExpressionList1 == theSelectorToExpressionList2) {
        } else {
          final int count0 = theSelectorToExpressionList1.size();
          result = compare(count0, theSelectorToExpressionList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Pair<String, Exp> e0_1 = theSelectorToExpressionList1.get(i0);
            final Pair<String, Exp> e0_2 = theSelectorToExpressionList2.get(i0);
            {
              if (e0_1 == e0_2) {
              } else {
                if (e0_1.getE0() == e0_2.getE0()) {
                } else {
                  result = compare(e0_1.getE0(), e0_2.getE0());
                  if (result != 0) {
                    return result;
                  }
                }
                if (e0_1.getE1() == e0_2.getE1()) {
                } else {
                  result = compare(e0_1.getE1(), e0_2.getE1());
                  if (result != 0) {
                    return result;
                  }
                }
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final RefinementClause o1, final RefinementClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theSubject == o2.theSubject) {
    } else {
      result = compare(o1.theSubject, o2.theSubject);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Constituent> theConstituents1 = o1.theConstituents;
      final ArrayList<Constituent> theConstituents2 = o2.theConstituents;

      {
        if (theConstituents1 == theConstituents2) {
        } else {
          final int count0 = theConstituents1.size();
          result = compare(count0, theConstituents2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Constituent e0_1 = theConstituents1.get(i0);
            final Constituent e0_2 = theConstituents2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(
      final RenamingDeclarationEmbeddedPackageDeclarationMember o1,
      final RenamingDeclarationEmbeddedPackageDeclarationMember o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theRenamingDeclaration == o2.theRenamingDeclaration) {
    } else {
      result = compare(o1.theRenamingDeclaration, o2.theRenamingDeclaration);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final RepresentationClauseBasicDeclarativeItem o1,
      final RepresentationClauseBasicDeclarativeItem o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theRepresentationClause == o2.theRepresentationClause) {
    } else {
      result = compare(o1.theRepresentationClause, o2.theRepresentationClause);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ReturnAnnotationExp o1, final ReturnAnnotationExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ReturnAnnotationPred o1,
      final ReturnAnnotationPred o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theID == o2.theID) {
    } else {
      result = compare(o1.theID, o2.theID);
      if (result != 0) {
        return result;
      }
    }

    if (o1.thePredicate == o2.thePredicate) {
    } else {
      result = compare(o1.thePredicate, o2.thePredicate);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final ReturnStatement o1, final ReturnStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final SelectedComponent o1, final SelectedComponent o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theDecoratedFlag == o2.theDecoratedFlag) {
    } else {
      result = compare(o1.theDecoratedFlag, o2.theDecoratedFlag);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  @SuppressWarnings("rawtypes")
  public int compare(final Set s1, final Set s2) {
    if (s1 == s2) {
      return 0;
    } else if (s1 == null) {
      return -1;
    } else if (s2 == null) {
      return 1;
    }
    int count;
    int result = compare(count = s1.size(), s2.size());
    if (result != 0) {
      return result;
    }
    final Object[] os1 = s1.toArray();
    if (!(s1 instanceof SortedSet)) {
      Arrays.sort(os1, this);
    }
    final Object[] os2 = s2.toArray();
    if (!(s1 instanceof SortedSet)) {
      Arrays.sort(os2, this);
    }
    for (int i = 0; i < count; i++) {
      result = compare(os1[i], os2[i]);
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

  public int compare(final Short s1, final Short s2) {
    if (s1 == s2) {
      return 0;
    } else if (s1 == null) {
      return -1;
    } else if (s2 == null) {
      return 1;
    }
    return compare(s1.intValue(), s2.intValue());
  }

  public int compare(final SignedIntegerTypeDefinition o1,
      final SignedIntegerTypeDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theLowRangeExp == o2.theLowRangeExp) {
    } else {
      result = compare(o1.theLowRangeExp, o2.theLowRangeExp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theHighRangeExp == o2.theHighRangeExp) {
    } else {
      result = compare(o1.theHighRangeExp, o2.theHighRangeExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final StatementList o1, final StatementList o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<Statement> theStatements1 = o1.theStatements;
      final ArrayList<Statement> theStatements2 = o2.theStatements;

      {
        if (theStatements1 == theStatements2) {
        } else {
          final int count0 = theStatements1.size();
          result = compare(count0, theStatements2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Statement e0_1 = theStatements1.get(i0);
            final Statement e0_2 = theStatements2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final StatementSubProgramImplementation o1,
      final StatementSubProgramImplementation o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theDeclarativePart == o2.theDeclarativePart) {
    } else {
      result = compare(o1.theDeclarativePart, o2.theDeclarativePart);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theStatementList == o2.theStatementList) {
    } else {
      result = compare(o1.theStatementList, o2.theStatementList);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final String s1, final String s2) {
    if (s1 == s2) {
      return 0;
    } else if (s1 == null) {
      return -1;
    } else if (s2 == null) {
      return 1;
    }
    return s1.compareTo(s2);
  }

  public int compare(final StringLiteral o1, final StringLiteral o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theString == o2.theString) {
    } else {
      result = compare(o1.theString, o2.theString);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final SubTypeChoice o1, final SubTypeChoice o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theSubTypeIndication == o2.theSubTypeIndication) {
    } else {
      result = compare(o1.theSubTypeIndication, o2.theSubTypeIndication);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final SubTypeDeclaration o1, final SubTypeDeclaration o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIDString == o2.theIDString) {
    } else {
      result = compare(o1.theIDString, o2.theIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theSubTypeIndication == o2.theSubTypeIndication) {
    } else {
      result = compare(o1.theSubTypeIndication, o2.theSubTypeIndication);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final SubTypeIndication o1, final SubTypeIndication o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalConstraint == o2.theOptionalConstraint) {
    } else if (o1.theOptionalConstraint == null) {
      return -1;
    } else if (o2.theOptionalConstraint == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalConstraint, o2.theOptionalConstraint);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final SubUnitCompilationUnit o1,
      final SubUnitCompilationUnit o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theContextClause == o2.theContextClause) {
    } else {
      result = compare(o1.theContextClause, o2.theContextClause);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theProperBody == o2.theProperBody) {
    } else {
      result = compare(o1.theProperBody, o2.theProperBody);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final Tuple t1, final Tuple t2) {
    if (t1 == t2) {
      return 0;
    } else if (t1 == null) {
      return -1;
    } else if (t2 == null) {
      return 1;
    }
    int count;
    int result = compare(count = t1.size(), t2.size());
    if (result != 0) {
      return result;
    }
    for (int i = 0; i < count; i++) {
      result = compare(t1.get(i), t2.get(i));
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

  public int compare(final TypeAssertion o1, final TypeAssertion o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theIdentifier == o2.theIdentifier) {
    } else {
      result = compare(o1.theIdentifier, o2.theIdentifier);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theBase == o2.theBase) {
    } else {
      result = compare(o1.theBase, o2.theBase);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theSubtypeMark == o2.theSubtypeMark) {
    } else {
      result = compare(o1.theSubtypeMark, o2.theSubtypeMark);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final TypeConversion o1, final TypeConversion o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theName == o2.theName) {
    } else {
      result = compare(o1.theName, o2.theName);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final UnaryExp o1, final UnaryExp o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theUnaryOp == o2.theUnaryOp) {
    } else if (o1.theUnaryOp == null) {
      return -1;
    } else if (o2.theUnaryOp == null) {
      return 1;
    } else {
      result = compare(o1.theUnaryOp, o2.theUnaryOp);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final UnconstrainedArrayDefinition o1,
      final UnconstrainedArrayDefinition o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theComponentName == o2.theComponentName) {
    } else {
      result = compare(o1.theComponentName, o2.theComponentName);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<Name> theIndexSubTypeNames1 = o1.theIndexSubTypeNames;
      final ArrayList<Name> theIndexSubTypeNames2 = o2.theIndexSubTypeNames;

      {
        if (theIndexSubTypeNames1 == theIndexSubTypeNames2) {
        } else {
          final int count0 = theIndexSubTypeNames1.size();
          result = compare(count0, theIndexSubTypeNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theIndexSubTypeNames1.get(i0);
            final Name e0_2 = theIndexSubTypeNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final UseTypeClause o1, final UseTypeClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<Name> theNames1 = o1.theNames;
      final ArrayList<Name> theNames2 = o2.theNames;

      {
        if (theNames1 == theNames2) {
        } else {
          final int count0 = theNames1.size();
          result = compare(count0, theNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theNames1.get(i0);
            final Name e0_2 = theNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final UseTypeClauseEmbeddedPackageDeclarationMember o1,
      final UseTypeClauseEmbeddedPackageDeclarationMember o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theUseTypeClause == o2.theUseTypeClause) {
    } else {
      result = compare(o1.theUseTypeClause, o2.theUseTypeClause);
      if (result != 0) {
        return result;
      }
    }

    return 0;
  }

  public int compare(final WhileLoopStatement o1, final WhileLoopStatement o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    if (o1.theStatementIndex == o2.theStatementIndex) {
    } else {
      result = compare(o1.theStatementIndex, o2.theStatementIndex);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalIDString == o2.theOptionalIDString) {
    } else if (o1.theOptionalIDString == null) {
      return -1;
    } else if (o2.theOptionalIDString == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalIDString, o2.theOptionalIDString);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalIDRegionSelection == o2.theOptionalIDRegionSelection) {
    } else if (o1.theOptionalIDRegionSelection == null) {
      return -1;
    } else if (o2.theOptionalIDRegionSelection == null) {
      return 1;
    } else {
      result = compare(
          o1.theOptionalIDRegionSelection,
          o2.theOptionalIDRegionSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theLoopKeywordRegionSelection == o2.theLoopKeywordRegionSelection) {
    } else {
      result = compare(
          o1.theLoopKeywordRegionSelection,
          o2.theLoopKeywordRegionSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theOptionalLoopInvariant == o2.theOptionalLoopInvariant) {
    } else if (o1.theOptionalLoopInvariant == null) {
      return -1;
    } else if (o2.theOptionalLoopInvariant == null) {
      return 1;
    } else {
      result = compare(o1.theOptionalLoopInvariant, o2.theOptionalLoopInvariant);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theStatementList == o2.theStatementList) {
    } else {
      result = compare(o1.theStatementList, o2.theStatementList);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theIterationSchemeRegionSelection == o2.theIterationSchemeRegionSelection) {
    } else {
      result = compare(
          o1.theIterationSchemeRegionSelection,
          o2.theIterationSchemeRegionSelection);
      if (result != 0) {
        return result;
      }
    }

    if (o1.theExp == o2.theExp) {
    } else {
      result = compare(o1.theExp, o2.theExp);
      if (result != 0) {
        return result;
      }
    }

    {
      final ArrayList<String> theOptionalLabelList1 = o1.theOptionalLabelList;
      final ArrayList<String> theOptionalLabelList2 = o2.theOptionalLabelList;

      {
        if (theOptionalLabelList1 == theOptionalLabelList2) {
        } else if (theOptionalLabelList1 == null) {
          return -1;
        } else if (theOptionalLabelList2 == null) {
          return 1;
        } else {
          final int count0 = theOptionalLabelList1.size();
          result = compare(count0, theOptionalLabelList2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final String e0_1 = theOptionalLabelList1.get(i0);
            final String e0_2 = theOptionalLabelList2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  public int compare(final WithClause o1, final WithClause o2) {
    assert (o1 != null) && (o2 != null);
    int result = 0;

    {
      final ArrayList<Name> theNames1 = o1.theNames;
      final ArrayList<Name> theNames2 = o2.theNames;

      {
        if (theNames1 == theNames2) {
        } else {
          final int count0 = theNames1.size();
          result = compare(count0, theNames2.size());
          if (result != 0) {
            return result;
          }
          for (int i0 = 0; i0 < count0; i0++) {
            final Name e0_1 = theNames1.get(i0);
            final Name e0_2 = theNames2.get(i0);
            if (e0_1 == e0_2) {
            } else {
              result = compare(e0_1, e0_2);
              if (result != 0) {
                return result;
              }
            }
          }
        }
      }
    }

    return 0;
  }

  protected int unhandledCompare(final Object o1, final Object o2) {
    assert false;
    return -1;
  }
}
