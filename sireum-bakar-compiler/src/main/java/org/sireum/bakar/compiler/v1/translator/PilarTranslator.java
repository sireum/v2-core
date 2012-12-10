/******************************************************************************
 * Copyright (c) 2008 Kansas State University, and others.                    *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 *                                                                            *
 * Contributors:                                                              *
 *     Jon Hoag, Jason Belt - initial design and implementation               *
 ******************************************************************************/

package org.sireum.bakar.compiler.v1.translator;

/********************************************************************************
 * Current Anonymous functions translated into Pilar
 * - pow(2,4) for 2 to the 4th power
 * - rem(4,7) for remainder (same as modulus for positive numbers)
 * - abs(-1) for absolute value
 * - amp(4,6) for the binary adding operator & from SPARK
 * - xor(true,false) for the binary operator xor from SPARK 
 ********************************************************************************/

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.antlr.stringtemplate.AutoIndentWriter;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.sireum.bakar.SparkAttribute;
import org.sireum.bakar.SparkUIF;
import org.sireum.bakar.SparkUtil;
import org.sireum.bakar.compiler.v1.model.AggregateItem;
import org.sireum.bakar.compiler.v1.model.AggregateQualifiedExp;
import org.sireum.bakar.compiler.v1.model.ArrayComponentAssociation;
import org.sireum.bakar.compiler.v1.model.ArrayUpdate;
import org.sireum.bakar.compiler.v1.model.AssertStatement;
import org.sireum.bakar.compiler.v1.model.AssignmentStatement;
import org.sireum.bakar.compiler.v1.model.AtClause;
import org.sireum.bakar.compiler.v1.model.AttributeDefinitionClause;
import org.sireum.bakar.compiler.v1.model.AttributeReference;
import org.sireum.bakar.compiler.v1.model.BinaryExp;
import org.sireum.bakar.compiler.v1.model.BinaryOp;
import org.sireum.bakar.compiler.v1.model.Body;
import org.sireum.bakar.compiler.v1.model.CaseStatement;
import org.sireum.bakar.compiler.v1.model.CaseStatementAlternative;
import org.sireum.bakar.compiler.v1.model.CharacterLiteral;
import org.sireum.bakar.compiler.v1.model.CheckStatement;
import org.sireum.bakar.compiler.v1.model.Choice;
import org.sireum.bakar.compiler.v1.model.Compilation;
import org.sireum.bakar.compiler.v1.model.CompilationUnit;
import org.sireum.bakar.compiler.v1.model.ContextClause;
import org.sireum.bakar.compiler.v1.model.DeclarativePart;
import org.sireum.bakar.compiler.v1.model.DeclarativePartMember;
import org.sireum.bakar.compiler.v1.model.DefaultLoopStatement;
import org.sireum.bakar.compiler.v1.model.DeltaAttributeDesignator;
import org.sireum.bakar.compiler.v1.model.DigitsAttributeDesignator;
import org.sireum.bakar.compiler.v1.model.EmbeddedPackageDeclaration;
import org.sireum.bakar.compiler.v1.model.EnumerationRepresentationClause;
import org.sireum.bakar.compiler.v1.model.ExitStatement;
import org.sireum.bakar.compiler.v1.model.Exp;
import org.sireum.bakar.compiler.v1.model.ExpAggregateItem;
import org.sireum.bakar.compiler.v1.model.ExpChoice;
import org.sireum.bakar.compiler.v1.model.ExpQualifiedExp;
import org.sireum.bakar.compiler.v1.model.ExpRange;
import org.sireum.bakar.compiler.v1.model.ForLoopStatement;
import org.sireum.bakar.compiler.v1.model.FunctionCall;
import org.sireum.bakar.compiler.v1.model.FunctionSubProgramBody;
import org.sireum.bakar.compiler.v1.model.FunctionSubProgramDeclaration;
import org.sireum.bakar.compiler.v1.model.IDAttributeDesignator;
import org.sireum.bakar.compiler.v1.model.IDName;
import org.sireum.bakar.compiler.v1.model.IfStatement;
import org.sireum.bakar.compiler.v1.model.InRangeExp;
import org.sireum.bakar.compiler.v1.model.IndexConstraint;
import org.sireum.bakar.compiler.v1.model.IndexedComponent;
import org.sireum.bakar.compiler.v1.model.JustificationClause;
import org.sireum.bakar.compiler.v1.model.JustificationStatement;
import org.sireum.bakar.compiler.v1.model.JustificationStatementEnd;
import org.sireum.bakar.compiler.v1.model.LiteralExp;
import org.sireum.bakar.compiler.v1.model.LoopStatement;
import org.sireum.bakar.compiler.v1.model.MainProgram;
import org.sireum.bakar.compiler.v1.model.Mode;
import org.sireum.bakar.compiler.v1.model.NameExp;
import org.sireum.bakar.compiler.v1.model.NameRangeExp;
import org.sireum.bakar.compiler.v1.model.NamedArrayAggregate;
import org.sireum.bakar.compiler.v1.model.NamedRecordAggregate;
import org.sireum.bakar.compiler.v1.model.Node;
import org.sireum.bakar.compiler.v1.model.NodeVisitor;
import org.sireum.bakar.compiler.v1.model.NullStatement;
import org.sireum.bakar.compiler.v1.model.NumericLiteral;
import org.sireum.bakar.compiler.v1.model.PackageBody;
import org.sireum.bakar.compiler.v1.model.PackageDeclaration;
import org.sireum.bakar.compiler.v1.model.PositionalArrayAggregate;
import org.sireum.bakar.compiler.v1.model.PositionalRecordAggregate;
import org.sireum.bakar.compiler.v1.model.Postcondition;
import org.sireum.bakar.compiler.v1.model.Precondition;
import org.sireum.bakar.compiler.v1.model.Predicate;
import org.sireum.bakar.compiler.v1.model.ProcedureCallStatement;
import org.sireum.bakar.compiler.v1.model.ProcedureSubProgramBody;
import org.sireum.bakar.compiler.v1.model.ProcedureSubProgramDeclaration;
import org.sireum.bakar.compiler.v1.model.ProofFunctionDeclaration;
import org.sireum.bakar.compiler.v1.model.ProofStatement;
import org.sireum.bakar.compiler.v1.model.QuantifiedExp;
import org.sireum.bakar.compiler.v1.model.Range;
import org.sireum.bakar.compiler.v1.model.RangeAttributeReference;
import org.sireum.bakar.compiler.v1.model.RangeChoice;
import org.sireum.bakar.compiler.v1.model.RangeConstraint;
import org.sireum.bakar.compiler.v1.model.RecordComponentAssociation;
import org.sireum.bakar.compiler.v1.model.RecordRepresentationClause;
import org.sireum.bakar.compiler.v1.model.RecordUpdate;
import org.sireum.bakar.compiler.v1.model.RepresentationClause;
import org.sireum.bakar.compiler.v1.model.ReturnAnnotation;
import org.sireum.bakar.compiler.v1.model.ReturnAnnotationExp;
import org.sireum.bakar.compiler.v1.model.ReturnAnnotationPred;
import org.sireum.bakar.compiler.v1.model.ReturnStatement;
import org.sireum.bakar.compiler.v1.model.SelectedComponent;
import org.sireum.bakar.compiler.v1.model.SimpleStatement;
import org.sireum.bakar.compiler.v1.model.Statement;
import org.sireum.bakar.compiler.v1.model.StatementList;
import org.sireum.bakar.compiler.v1.model.StatementSubProgramImplementation;
import org.sireum.bakar.compiler.v1.model.StringLiteral;
import org.sireum.bakar.compiler.v1.model.SubTypeChoice;
import org.sireum.bakar.compiler.v1.model.SubTypeIndication;
import org.sireum.bakar.compiler.v1.model.UnaryExp;
import org.sireum.bakar.compiler.v1.model.WhileLoopStatement;
import org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit;
import org.sireum.bakar.compiler.v1.option.CompilerModuleOptions;
import org.sireum.bakar.compiler.v1.option.SparkCompilerOptions;
import org.sireum.bakar.compiler.v1.symboltable.SymMethodFactory;
import org.sireum.bakar.compiler.v1.symboltable.SymbolTable;
import org.sireum.bakar.compiler.v1.symboltable.model.ContainerDependencyClause;
import org.sireum.bakar.compiler.v1.symboltable.model.ContainerDependencyRelation;
import org.sireum.bakar.compiler.v1.symboltable.model.ContainerOwnClause;
import org.sireum.bakar.compiler.v1.symboltable.model.ContainerOwnStatement;
import org.sireum.bakar.compiler.v1.symboltable.model.ContainerRefinementClause;
import org.sireum.bakar.compiler.v1.symboltable.model.ContainerRefinementDefinition;
import org.sireum.bakar.compiler.v1.symboltable.model.SPARKPredicateType;
import org.sireum.bakar.compiler.v1.symboltable.model.SymAbstractDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymAdaFunction;
import org.sireum.bakar.compiler.v1.symboltable.model.SymArrayDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymCall;
import org.sireum.bakar.compiler.v1.symboltable.model.SymCompositeDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymConstant;
import org.sireum.bakar.compiler.v1.symboltable.model.SymDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymDerivesAnnotation;
import org.sireum.bakar.compiler.v1.symboltable.model.SymDerivesAnnotationModel;
import org.sireum.bakar.compiler.v1.symboltable.model.SymEnumElementDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymEnumerationDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymFixedDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymFloatingDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymFunction;
import org.sireum.bakar.compiler.v1.symboltable.model.SymGlobalAnnotation;
import org.sireum.bakar.compiler.v1.symboltable.model.SymIndexSubTypeDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymIntegerDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymModDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymModeAnnotation;
import org.sireum.bakar.compiler.v1.symboltable.model.SymNullDerivesAnnotation;
import org.sireum.bakar.compiler.v1.symboltable.model.SymObject;
import org.sireum.bakar.compiler.v1.symboltable.model.SymObjectKind;
import org.sireum.bakar.compiler.v1.symboltable.model.SymOrigin;
import org.sireum.bakar.compiler.v1.symboltable.model.SymOwnVariableAnnotation;
import org.sireum.bakar.compiler.v1.symboltable.model.SymPackage;
import org.sireum.bakar.compiler.v1.symboltable.model.SymPackageElement;
import org.sireum.bakar.compiler.v1.symboltable.model.SymPackageHierarchyKind;
import org.sireum.bakar.compiler.v1.symboltable.model.SymPackageKind;
import org.sireum.bakar.compiler.v1.symboltable.model.SymParameter;
import org.sireum.bakar.compiler.v1.symboltable.model.SymPartialDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymProcedure;
import org.sireum.bakar.compiler.v1.symboltable.model.SymProofFunction;
import org.sireum.bakar.compiler.v1.symboltable.model.SymRangeSubTypeDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymRecordComponentDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymRecordDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymRefinementAnnotation;
import org.sireum.bakar.compiler.v1.symboltable.model.SymScalarDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymSignedIntegerDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymSimpleAnnotation;
import org.sireum.bakar.compiler.v1.symboltable.model.SymVariable;
import org.sireum.bakar.compiler.v1.symboltable.model.Symbol;
import org.sireum.bakar.compiler.v1.translator.PilarTranslator.PilarTranslatorContext.EvalResult;
import org.sireum.bakar.selection.SelectionUtil;
import org.sireum.bakar.selection.model.RegionSelection;
import org.sireum.bakar.util.PackageKind;
import org.sireum.bakar.util.Pair;
import org.sireum.bakar.util.Path;
import org.sireum.bakar.util.Stack;
import org.sireum.bakar.util.Triple;
import org.sireum.bakar.util.message.ErrorMessage;
import org.sireum.bakar.util.message.Message;
import org.sireum.bakar.util.message.MessageFactory;
import org.sireum.bakar.util.template.SparkTemplateAnchor;

/**
 * 
 * @author jon hoag
 * @author jason belt
 * 
 */
public class PilarTranslator {
  public static class NLWriter extends AutoIndentWriter {
    public NLWriter(final Writer arg0) {
      super(arg0);
      this.newline = "\n";
    }
  }

  public static class PilarTranslatorContext {

    public static class EvalResult {
      private final Symbol theResolvedSymbol;
      private final SymDef theReturnType;
      private StringTemplate theTemplate;

      public EvalResult(final StringTemplate theTemplate,
          final Symbol theResolvedSymbol, final SymDef theRetType) {
        this.theTemplate = theTemplate;
        this.theResolvedSymbol = theResolvedSymbol;
        this.theReturnType = theRetType;
      }

      public Symbol getTheResolvedSymbol() {
        return this.theResolvedSymbol;
      }

      public SymDef getTheReturnType() {
        return this.theReturnType;
      }

      public StringTemplate getTheTemplate() {
        return this.theTemplate;
      }

      public void setTheTemplate(final StringTemplate st) {
        assert st != null;
        this.theTemplate = st;
      }

      @Override
      public String toString() {
        return this.theTemplate.toString();
      }
    }

    private static final String REGRESSION_STRING = "REGRESSION_SUPPRESSED";

    public boolean visitingEmbeddedPackage;

    SparkCompilerOptions sco;

    final String filepath;
    final String filename;

    int pilarLocIndex = 0;
    int templocal = 0;
    public int currentArrayDim = -1;

    final StringTemplate NONE;
    final StringTemplate NOT_IMPLEMENTED_YET;

    final StringTemplateGroup stg;
    StringTemplate stLastAddedLocation;
    Stack<StringTemplate> currentStMethodStack;
    Stack<StringTemplate> currentStPackageStack;

    Stack<EvalResult> theResultStack = new Stack<EvalResult>();
    Object theOptionalResult;

    private final Stack<String> exitLoc = new Stack<String>();

    final SymbolTable st;
    final Stack<Symbol> symbolStack = new Stack<Symbol>();
    final Map<String, String> packageMap;

    List<Message> messages;

    // we can switch in and out of specs and bodies as we process embedded
    // children
    public Stack<Boolean> inSpecPart = new Stack<Boolean>();

    public boolean inProofContext = false;
    public int loopCounter;

    boolean desugarArrayAggregates = false;
    boolean desugarRecordAggregates = false;
    boolean suppressThreeAddressCode = false;

    // shouldn't access this stack directly, use helper methods instead
    Stack<Boolean> genThreeAddCode = new Stack<Boolean>();

    public static SymDef EXPECTED_NULL = new SymPartialDef();

    String stmtEndLocLabel;

    final static String MARK_PLACEHOLDER = "@" + SparkUtil.MARK_PLACEHOLDER;

    public PilarTranslatorContext(final Map<String, String> packageMap,
        final StringTemplateGroup stg, final SymbolTable table,
        final String filepath, final String filename,
        final List<Message> messages, final SparkCompilerOptions options) {
      this.sco = options;

      if (this.sco.getOptCompilerOptions() != null) {
        final CompilerModuleOptions cmo = this.sco.getOptCompilerOptions();
        this.suppressThreeAddressCode = !cmo.getGenThreeAddressCode();
        this.desugarArrayAggregates = cmo.getDesugarArrayAggregates();
        this.desugarRecordAggregates = cmo.getDesugarRecordAggregates();
      }

      this.packageMap = packageMap;
      this.stg = stg;
      this.st = table;
      this.filepath = filepath;
      this.filename = filename;
      this.messages = messages;
      this.currentStMethodStack = new Stack<StringTemplate>();
      this.currentStPackageStack = new Stack<StringTemplate>();

      this.NONE = this.stg.getInstanceOf("NONE");
      this.NOT_IMPLEMENTED_YET = this.stg.getInstanceOf("NOT_IMPLEMENTED_YET");
    }

    /**
     * Add compiler metadata annotation to the pilar translation
     * 
     * @param pilarTranslatorVisitor
     *          TODO
     * @param st
     *          the string template that contains the model string template
     */
    protected void addCompilerMetaDataAnnotation(final StringTemplate st) {
      final StringTemplate stMetaData = this.stg
          .getInstanceOf("SPARKCompilerMetaDataFile");
      stMetaData.setAttribute("version", PilarTranslator.CompilerVersion);
      final Date date = new Date();
      final DateFormat format = new SimpleDateFormat(
          "EEE MMM d HH:mm:ss z yyyy");
      if (this.sco.getRegressionTesting()) {
        stMetaData.setAttribute(
            "compileTime",
            PilarTranslatorContext.REGRESSION_STRING);
      } else {
        stMetaData.setAttribute("compileTime", format.format(date));
      }
      final ArrayList<StringTemplate> options = new ArrayList<StringTemplate>();
      //TODO: Add in compiler options here
      final StringTemplate stOptions = createListFromTemplate(options);
      stMetaData.setAttribute("options", stOptions);
      st.setAttribute("annotationList", stMetaData);
    }

    public void addLocationToCurrentMethod(final StringTemplate theLocation) {
      assert this.currentStMethodStack != null;
      final StringTemplate currentMeth = this.currentStMethodStack.peek();
      currentMeth.setAttribute("location", theLocation);
      this.stLastAddedLocation = theLocation;
    }

    public SymVariable addLoopIterVariable(final SymCall s,
        final String iterVarName, final SymDef theIterVarType) {
      return this.st.addVariable(
          s,
          iterVarName,
          theIterVarType,
          SymOrigin.METHOD,
          SymObjectKind.CONCRETE_VARIABLE,
          null,
          null);
    }

    public void addPackageElement(final StringTemplate thePackageElement) {
      assert this.currentStPackageStack != null;

      this.currentStPackageStack.peek().setAttribute(
          "packageElement",
          thePackageElement);
    }

    /**
     * Add profile annotation to the start of the translation
     * 
     * @param pilarTranslatorVisitor
     *          TODO
     * @param st
     *          the string template that contains the model string template
     */
    protected void addProfileAnnotation(final StringTemplate st) {
      final StringTemplate stProfile = this.stg.getInstanceOf("Profile");
      stProfile.setAttribute("profile", "org::sireum::profile::spark");
      st.setAttribute("annotationList", stProfile);
    }

    /**
     * helper method which adds a local variable to the the method. This is
     * intended to be used when processing proof context expressions which
     * declare an indexing or jml style result variable. The idea is you add the
     * variable to the method's context so that future references to it can be
     * resolved. You should remove the variable when done translating the exp.
     * 
     * @param s
     * @param localName
     * @param theType
     * @return
     */
    public SymVariable addTemporaryLocal(final SymCall s,
        final String localName, final SymDef theType) {
      return this.st.addVariable(
          s,
          localName,
          theType,
          SymOrigin.METHOD,
          SymObjectKind.TEMPORARY_VARIABLE,
          null,
          null);
    }

    public StringTemplate constructAssignment(final String lhs,
        final String rhs, final RegionSelection optionalRS,
        final StringTemplate... optLineAnnotation) {
      final StringTemplate ass = this.stg.getInstanceOf("assignmentStatement");
      ass.setAttribute("lhs", lhs);
      ass.setAttribute("rhs", rhs);

      final StringTemplate stLocRSAnnot = this.stg
          .getInstanceOf("locLabeledWithOptAnnot");
      stLocRSAnnot.setAttribute("theLocLabel", generateLocLabel_Next());

      if (optionalRS != null) {
        stLocRSAnnot.setAttribute(
            "theOptLocAnnotation",
            getLineAnnotation(optionalRS));
      }

      final StringTemplate[] pvargs = pacifyVargs(optLineAnnotation);
      if (pvargs != null) {
        for (final StringTemplate stAnnot : pvargs) {
          stLocRSAnnot.setAttribute("theOptLocAnnotation", stAnnot);
        }
      }

      ass.setAttribute("locLabel", stLocRSAnnot);

      return ass;
    }

    public RecordUpdate convertArrayUpdateToRecordUpdate(final ArrayUpdate o) {
      final RecordUpdate ru = new RecordUpdate();
      ru.setTheName(o.getTheName());
      ru.setTheOptionalRegionSelection(o.getTheOptionalRegionSelection());
      final ArrayList<Pair<String, Exp>> s2el = new ArrayList<Pair<String, Exp>>();

      for (final Pair<ArrayList<Exp>, Exp> p : o
          .getTheIndexListToExpressionList()) {
        assert p.getE0().size() == 1;
        final NameExp selector = (NameExp) p.getE0().get(0);
        final String name = ((IDName) selector.getTheName()).getTheIDString();
        s2el.add(new Pair<String, Exp>(name, p.getE1()));
      }

      ru.setTheSelectorToExpressionList(s2el);
      return ru;
    }

    /**
     * this is a hack to work around a grammar ambiguity. The parser doesn't
     * have enough information to distinguish between
     * 
     * S := some_record_type(record_component => 7) and S :=
     * some_array_type(some_subtype_indication => 7)
     * 
     * That is, unless it sees something like 'others => ..', it will always
     * assume the aggregate is a NamedRecordAggregate.
     * 
     * This method allows us to rewrite the AST on the fly so that the correct
     * aggregate type is used
     * 
     * @param o
     */
    public void convertNamedRecordAggregateToNamedArrayAggregate(
        final AggregateQualifiedExp o) {
      final NamedRecordAggregate nra = (NamedRecordAggregate) o
          .getTheAggregate();

      final NamedArrayAggregate naa = new NamedArrayAggregate();
      naa.setTheOptionalRegionSelection(nra.getTheOptionalRegionSelection());

      final ArrayList<ArrayComponentAssociation> thelist = new ArrayList<ArrayComponentAssociation>(
          nra.getTheRecordComponentAssociations().size());
      naa.setTheOptionalArrayComponentAssociations(thelist);

      for (final RecordComponentAssociation rca : nra
          .getTheRecordComponentAssociations()) {
        final String theChoices = rca.getTheIDString();

        final ArrayComponentAssociation aca = new ArrayComponentAssociation();
        {
          final ExpAggregateItem eai = new ExpAggregateItem();
          {
            eai.setTheExp(rca.getTheExp());
          }
          aca.setTheAggregateItem(eai);

          final ArrayList<Choice> theChoiceList = new ArrayList<Choice>();
          {
            final SubTypeChoice stc = new SubTypeChoice();
            {
              final SubTypeIndication sti = new SubTypeIndication();
              {
                final IDName idn = new IDName();
                idn.setTheIDString(theChoices);
                sti.setTheName(idn);
              }
              stc.setTheSubTypeIndication(sti);
            }
            theChoiceList.add(stc);
          }
          aca.setTheChoices(theChoiceList);
        }
        thelist.add(aca);
      }
      o.setTheAggregate(naa);
    }

    /**
     * this is a hack to work around a grammar ambiguity. The parser doesn't
     * have enough information to distinguish between
     * 
     * S := some_record_type(7, 8, 9) and S := some_array_type(7, 8, 9)
     * 
     * That is, unless it sees something like 'others => ..', it will always
     * assume the aggregate is a PositionalRecordAggregate.
     * 
     * This method allows us to rewrite the AST on the fly so that the correct
     * aggregate type is used
     * 
     * @param o
     */
    public void convertPositionalRecordAggregateToPositionalArrayAggregate(
        final AggregateQualifiedExp o) {
      final PositionalRecordAggregate pra = (PositionalRecordAggregate) o
          .getTheAggregate();

      final PositionalArrayAggregate paa = new PositionalArrayAggregate();
      paa.setTheOptionalRegionSelection(pra.getTheOptionalRegionSelection());

      final ArrayList<AggregateItem> theList = new ArrayList<AggregateItem>();

      for (final Exp e : pra.getTheExps()) {
        final ExpAggregateItem eai = new ExpAggregateItem();
        eai.setTheOptionalRegionSelection(e.getTheOptionalRegionSelection());
        eai.setTheExp(e);
        theList.add(eai);
      }
      paa.setTheAggregateItems(theList);

      o.setTheAggregate(paa);
    }

    public StringTemplate createAnnotationVariable(final String id,
        final RegionSelection theSelection) {
      final StringTemplate stAnnotationVariable = this.stg
          .getInstanceOf("AnnotationVariable");
      stAnnotationVariable.setAttribute("id", id);

      final StringTemplate annot = getLineAnnotation(theSelection);
      stAnnotationVariable.setAttribute("loc", annot);

      return stAnnotationVariable;
    }

    public StringTemplate createCommentBlock(final boolean bigComment,
        final String comment) {
      StringTemplate toRet = null;
      if (comment.contains("\n") || bigComment) {
        toRet = this.stg.getInstanceOf("BlockComment");
      } else {
        toRet = this.stg.getInstanceOf("SingleLineComment");
      }
      toRet.setAttribute("comment", comment);
      return toRet;
    }

    public StringTemplate createListFromString(final Collection<String> list) {
      final StringTemplate stList = this.stg.getInstanceOf("ListAnnotation");
      for (String theString : list) {
        //Make sure the annotations have pilar compliant names
        theString = SymbolTable.returnPilarCompliantName(theString);
        stList.setAttribute("element", theString);
      }
      return stList;
    }

    public StringTemplate createListFromTemplate(
        final ArrayList<StringTemplate> sts) {
      final StringTemplate stList = this.stg.getInstanceOf("ListAnnotation");
      for (final StringTemplate stItem : sts) {
        stList.setAttribute("element", stItem);
      }
      return stList;
    }

    public StringTemplate createLoopStatementAnnotation(final LoopStatement o,
        final String startLoc, final String endLoc) {

      final StringTemplate stLSA = this.stg
          .getInstanceOf("LoopStatementAnnotation");

      stLSA.setAttribute("theStartLoc", startLoc);
      stLSA.setAttribute("theEndLoc", endLoc);

      String theIdentifier = null;
      if (o.getTheOptionalIDString() != null) {
        theIdentifier = o.getTheOptionalIDString();
      } else {
        theIdentifier = "loop" + SparkUtil.PROGRAM_ELEMENT_ID_SEPARATOR
            + this.loopCounter++;
      }
      stLSA.setAttribute("theIdentifier", theIdentifier);

      return stLSA;
    }

    public StringTemplate createLoopStatementPilarLocation(
        final LoopStatement o, final StringTemplate theLoopStatementAnnotation) {
      return this.createLoopStatementPilarLocation(
          null,
          o,
          theLoopStatementAnnotation);
    }

    public StringTemplate createLoopStatementPilarLocation(
        final String theOptionalStartLoc, final LoopStatement o,
        final StringTemplate theLoopStatementAnnotation) {

      // create rs which includes everything to the end of 'loop' keyword
      final RegionSelection rs = SelectionUtil.newRegionSelection(o
          .getTheOptionalRegionSelection().getTheStartCaret(), o
          .getTheLoopKeywordRegionSelection().getTheEndCaret(), o
          .getTheOptionalRegionSelection().getTheOptionalSource());

      final StringTemplate ret = this.stg
          .getInstanceOf("locLabeledWithOptAnnot");
      if (theOptionalStartLoc != null) {
        assert !(o instanceof ForLoopStatement);
        ret.setAttribute("theLocLabel", theOptionalStartLoc);
      } else {
        ret.setAttribute("theLocLabel", generateLocLabel_Next());
      }

      ret.setAttribute("theOptLocAnnotation", getLineAnnotation(rs));
      ret.setAttribute("theOptLocAnnotation", theLoopStatementAnnotation);

      return ret;
    }

    public StringTemplate createPackageAnnotation(final SymPackage symPackage,
        final RegionSelection packageHeaderRS, final StringTemplate stPHA,
        final boolean body) {

      final StringTemplate stPackageAnnot = this.stg
          .getInstanceOf("PackageAnnotation");

      stPackageAnnot.setAttribute("theSourceFileName", emitFileName());

      stPackageAnnot.setAttribute("theSparkName", symPackage.getTheName());

      stPackageAnnot.setAttribute(
          "thePilarSparkName",
          symPackage.getTheTranslation());

      if (symPackage.getTheOptionalParent() != null) {
        stPackageAnnot.setAttribute("theOptionalParent", symPackage
            .getTheOptionalParent().getTheTranslation());
      }

      if (this.inSpecPart.peek().booleanValue()) {
        stPackageAnnot.setAttribute("thePackageKind", "@"
            + SymPackageKind.SPECIFICATION);
      } else {
        stPackageAnnot
            .setAttribute("thePackageKind", "@" + SymPackageKind.BODY);
      }

      SymPackageHierarchyKind sht = null;
      if (symPackage.getIsChild()) {
        sht = SymPackageHierarchyKind.CHILD;
      } else if (symPackage.getIsEmbedded()) {
        sht = SymPackageHierarchyKind.EMBEDDED;
      } else {
        assert symPackage.getTheOptionalParent() == null;
        sht = SymPackageHierarchyKind.ROOT;
      }
      stPackageAnnot.setAttribute("theHierarchyType", '@' + sht.toString());

      final StringTemplate selection = body ? getLineAnnotation(symPackage
          .getThePackageBodySelection()) : getLineAnnotation(symPackage
          .getThePackageSpecSelection());

      assert selection != null;
      stPackageAnnot.setAttribute("thePackageSelection", selection);

      assert packageHeaderRS != null;
      stPackageAnnot.setAttribute(
          "thePackageHeaderSelection",
          getLineAnnotation(packageHeaderRS));

      /************************************************************************
       * OPTIONAL SYMBOL MAPPINGS
       ***********************************************************************/
      {
        final ArrayList<StringTemplate> symMappings = new ArrayList<StringTemplate>();

        for (final SymObject ob : SymbolTable.linearizeObjects(symPackage
            .getTheObjects())) {//.values()) {
          final boolean add = (body && (ob.getTheOrigin() == SymOrigin.PACKAGE_BODY))
              || (!body && (ob.getTheOrigin() != SymOrigin.PACKAGE_BODY));

          if (!(ob instanceof SymConstant) && add) {
            final StringTemplate stSymbolEntry = this.stg
                .getInstanceOf("SymbolEntry");
            stSymbolEntry.setAttribute("sparkID", ob.getTheName());
            stSymbolEntry.setAttribute("pilarSparkID", ob.getTheTranslation());
            stSymbolEntry.setAttribute("originTag", "@"
                + ob.getTheOrigin().toString());
            stSymbolEntry.setAttribute("kindTag", '@' + ob.getTheKind()
                .toString());
            symMappings.add(stSymbolEntry);
          }
        }

        if (!symMappings.isEmpty()) {
          stPackageAnnot.setAttribute(
              "theOptionalSymbolMapping",
              createListFromTemplate(symMappings));
        }
      }

      stPackageAnnot.setAttribute("thePackageHeaderAnnotation", stPHA);

      return stPackageAnnot;
    }

    public StringTemplate createPackageModel(final SymPackage symPackage,
        final boolean body) {

      final StringTemplate stModel = this.stg.getInstanceOf("model");

      addProfileAnnotation(stModel);

      addCompilerMetaDataAnnotation(stModel);

      //Add Package comment block
      final StringTemplate stPackageBlockComment = createCommentBlock(
          true,
          "S P A R K   P a c k a g e   " + (body ? "B o d y" : "S p e c")
              + "M e t a d a t a");
      stModel.setAttribute("annotationList", stPackageBlockComment);

      return stModel;
    }

    public StringTemplate createStmtAnnot(
        final CaseStatement o,
        final ArrayList<Pair<StringTemplate, StringTemplate>> caseStatementAlternatives,
        final StringTemplate optWhenOthers, final StringTemplate mark) {
      final StringTemplate stcs = this.stg
          .getInstanceOf("CaseStatementAnnotation");

      {
        final StringTemplate stLA = this.stg.getInstanceOf("ListAnnotation");
        for (final Pair<StringTemplate, StringTemplate> p : caseStatementAlternatives) {
          final StringTemplate pair = this.stg.getInstanceOf("pair");
          pair.setAttribute("fst", p.getE0());
          pair.setAttribute("snd", PilarTranslatorContext.MARK_PLACEHOLDER);
          stLA.setAttribute("element", pair);
        }
        stcs.setAttribute("caseStatementAlternatives", stLA);
      }

      if (optWhenOthers != null) {
        stcs.setAttribute(
            "optWhenOthers",
            PilarTranslatorContext.MARK_PLACEHOLDER);
      }

      if (o.getTheOptionalLabelList() != null) {
        final StringTemplate stLA = this.stg.getInstanceOf("ListAnnotation");
        for (final String s : o.getTheOptionalLabelList()) {
          stLA.setAttribute("element", "\"" + s + "\"");
        }
        stcs.setAttribute("optSparkLabelList", stLA);
      }

      stcs.setAttribute("mark", mark);

      return stcs;
    }

    public StringTemplate createStmtAnnot(final DefaultLoopStatement o,
        final StringTemplate body, final StringTemplate mark) {
      final StringTemplate std = this.stg
          .getInstanceOf("DefaultLoopStatementAnnotation");
      createStmtAnnot_fillInLoopInfo(std, o, mark);
      return std;
    }

    public StringTemplate createStmtAnnot(final ForLoopStatement o,
        final String pilarIterVarID, final StringTemplate initPart,
        final StringTemplate body, final StringTemplate mark) {
      final StringTemplate stf = this.stg
          .getInstanceOf("ForLoopStatementAnnotation");
      stf.setAttribute("sparkIterVarID", o.getTheIDString());
      stf.setAttribute("pilarIterVarID", pilarIterVarID);
      stf.setAttribute("initPart", initPart);
      stf.setAttribute("isRev", Boolean.valueOf(o.getTheReverseFlag()));
      createStmtAnnot_fillInLoopInfo(stf, o, mark);
      return stf;
    }

    public StringTemplate createStmtAnnot(final IfStatement o,
        final StringTemplate ifPart, //
        final StringTemplate thenPart, //
        final ArrayList<Pair<StringTemplate, StringTemplate>> optElsIfParts, //
        final StringTemplate optElsePart, //
        final StringTemplate mark) {

      final StringTemplate stif = this.stg
          .getInstanceOf("IfStatementAnnotation");
      stif.setAttribute("ifPart", ifPart);
      stif.setAttribute("thenPart", PilarTranslatorContext.MARK_PLACEHOLDER);
      if (optElsIfParts != null) {
        final StringTemplate stLA = this.stg.getInstanceOf("ListAnnotation");
        for (final Pair<StringTemplate, StringTemplate> p : optElsIfParts) {
          final StringTemplate pair = this.stg.getInstanceOf("pair");
          pair.setAttribute("fst", p.getE0());
          pair.setAttribute("snd", PilarTranslatorContext.MARK_PLACEHOLDER);
          stLA.setAttribute("element", pair);
        }
        stif.setAttribute("optElsIfParts", stLA);
      }

      if (optElsePart != null) {
        stif.setAttribute(
            "optElsePart",
            PilarTranslatorContext.MARK_PLACEHOLDER);
      }

      stif.setAttribute("mark", mark);

      if (o.getTheOptionalLabelList() != null) {
        final StringTemplate stLA = this.stg.getInstanceOf("ListAnnotation");
        for (final String s : o.getTheOptionalLabelList()) {
          stLA.setAttribute("element", "\"" + s + "\"");
        }
        stif.setAttribute("optSparkLabelList", stLA);
      }

      return stif;

    }

    public StringTemplate createStmtAnnot(final ProofStatement o,
        final StringTemplate mark) {
      final String stname = o.getClass().getSimpleName() + "Annotation";
      final StringTemplate stret = this.stg.getInstanceOf(stname);
      stret.setAttribute("mark", mark);

      // proof statements can't have spark label's associated with them
      assert o.getTheOptionalLabelList() == null;

      return stret;
    }

    public StringTemplate createStmtAnnot(final SimpleStatement o,
        final StringTemplate mark) {
      final String stname = o.getClass().getSimpleName() + "Annotation";
      final StringTemplate stret = this.stg.getInstanceOf(stname);
      stret.setAttribute("mark", mark);

      if (o.getTheOptionalLabelList() != null) {
        final StringTemplate stLA = this.stg.getInstanceOf("ListAnnotation");
        for (final String s : o.getTheOptionalLabelList()) {
          stLA.setAttribute("element", "\"" + s + "\"");
        }
        stret.setAttribute("optSparkLabelList", stLA);
      }

      if (o instanceof ExitStatement) {
        if (((ExitStatement) o).getTheOptionalExp() != null) {
          stret.setAttribute("hasWhen", Boolean.TRUE);
        } else {
          stret.setAttribute("hasWhen", Boolean.FALSE);
        }
      }
      return stret;
    }

    public StringTemplate createStmtAnnot(final String startLabel,
        final String endLocLabel, final List<String> theOptLabelList) {
      if (theOptLabelList == null) {
        return null;
      }

      final StringTemplate stssla = this.stg
          .getInstanceOf("SparkStatementLabelAnnotation");
      stssla.setAttribute("theStartLocLabel", startLabel);
      stssla.setAttribute("theEndLocLabel", endLocLabel);

      final StringTemplate stLA = this.stg.getInstanceOf("ListAnnotation");
      for (final String s : theOptLabelList) {
        stLA.setAttribute("element", "\"" + s + "\"");
      }
      stssla.setAttribute("theLabelList", stLA);

      return stssla;
    }

    public StringTemplate createStmtAnnot(final SymVariable o,
        final StringTemplate mark) {
      final StringTemplate stret = this.stg
          .getInstanceOf("DeclarativePartStatementAnnotation");
      stret.setAttribute("mark", mark);
      return stret;
    }

    public StringTemplate createStmtAnnot(final WhileLoopStatement o,
        final StringTemplate condPart, final StringTemplate body,
        final StringTemplate mark) {
      final StringTemplate std = this.stg
          .getInstanceOf("WhileLoopStatementAnnotation");
      createStmtAnnot_fillInLoopInfo(std, o, mark);
      std.setAttribute("condPart", condPart);
      return std;
    }

    private void createStmtAnnot_fillInLoopInfo(final StringTemplate stl,
        final LoopStatement o, final StringTemplate mark) {
      if (o.getTheOptionalIDString() != null) {
        stl.setAttribute("optLoopId", o.getTheOptionalIDString());
      }
      stl.setAttribute("body", PilarTranslatorContext.MARK_PLACEHOLDER);
      stl.setAttribute("mark", mark);

      if (o.getTheOptionalLabelList() != null) {
        final StringTemplate stLA = this.stg.getInstanceOf("ListAnnotation");
        for (final String s : o.getTheOptionalLabelList()) {
          stLA.setAttribute("element", "\"" + s + "\"");
        }
        stl.setAttribute("optSparkLabelList", stLA);
      }
    }

    public void emitErrorMessage(final Node n, final String messageText) {
      final int l = n.getTheOptionalRegionSelection().getTheStartCaret()
          .getTheLine();
      final int c = n.getTheOptionalRegionSelection().getTheStartCaret()
          .getTheCol();
      this.messages.add(MessageFactory.newErrorMessage(
          this.filepath,
          l,
          c,
          PilarTranslator.CLASS_NAME_PRETTY + messageText));
    }

    String emitFileName() {
      String ret = formatPath(this.filepath);
      if (this.sco.getRegressionTesting()) {
        ret = Path.getFilename(ret);
      }
      return ret;
    }

    public void emitLogMessage(final String string) {
      System.err.println(PilarTranslator.CLASS_NAME_PRETTY + string);
      System.err.flush();
    }

    public void emitWarningMessage(final Node n, final String messageText) {
      final int l = n.getTheOptionalRegionSelection().getTheStartCaret()
          .getTheLine();
      final int c = n.getTheOptionalRegionSelection().getTheStartCaret()
          .getTheCol();
      this.messages.add(MessageFactory.newWarningMessage(
          this.filepath,
          l,
          c,
          PilarTranslator.CLASS_NAME_PRETTY + messageText));
    }

    public String formatPath(final String f) {
      // we should eventually convert this into a URI.  For now, just convert
      // windows use of '\' characters
      return f.replaceAll("\\\\", "/");
    }

    public SymVariable generateFreshTempVar(final SymDef theType,
        final SymOrigin theOrigin) {

      this.templocal++;
      final String tempName = SparkUtil.TEMP_VAR_PREFIX + this.templocal;

      final SymPackageElement origin = getFirstSymPackageElement();

      assert theType != null;
      assert origin != null;

      final SymVariable sv = this.st.addVariable(
          origin,
          tempName,
          theType,
          theOrigin,
          SymObjectKind.TEMPORARY_VARIABLE,
          null,
          null);
      return sv;
    }

    public String generateLocLabel(final int i) {
      return "l" + i;
    }

    public String generateLocLabel_Exit() {
      this.pilarLocIndex++;
      this.exitLoc.push(generateLocLabel(this.pilarLocIndex));
      return this.exitLoc.peek();
    }

    public String generateLocLabel_Next() {
      this.pilarLocIndex++;
      return generateLocLabel(this.pilarLocIndex);
    }

    public StringTemplate generateNewAnnotatedLoc(final RegionSelection rs) {
      return this.generateNewAnnotatedLoc(getLineAnnotation(rs));
    }

    public StringTemplate generateNewAnnotatedLoc(
        final StringTemplate... theAnnotations) {
      return generateNewLabeledAnnotatedLoc(
          generateLocLabel_Next(),
          theAnnotations);
    }

    public StringTemplate generateNewLabeledAnnotatedLoc(final String locLabel,
        final StringTemplate... theOptAnnotations) {
      assert locLabel.startsWith("l");

      final StringTemplate ret = this.stg
          .getInstanceOf("locLabeledWithOptAnnot");
      ret.setAttribute("theLocLabel", locLabel);

      final StringTemplate[] pvargs = pacifyVargs(theOptAnnotations);

      if (pvargs != null) {
        for (final StringTemplate s : pvargs) {
          ret.setAttribute("theOptLocAnnotation", s);
        }
      }
      return ret;
    }

    public StringTemplate generateNewSTLoc() {
      return this.generateNewSTLoc(null);
    }

    public StringTemplate generateNewSTLoc(final RegionSelection optRS) {
      if (optRS != null) {
        return this.generateNewAnnotatedLoc(getLineAnnotation(optRS));
      } else {
        final StringTemplate stRet = this.stg.getInstanceOf("locLabeled");
        stRet.setAttribute("label", generateLocLabel_Next());
        return stRet;
      }
    }

    public String getBinaryOpString(final BinaryOp o) {
      switch (o) {
        case ADD:
          return "+";
        case AMP:
          return SparkUIF.$AMP.toString();
        case AND:
          return SparkUIF.$AND.toString();
        case AND_THEN:
          return "&&";
        case DIV:
          return "/";
        case EQUAL:
        case IS_EQUIVALENT_TO:
          return "==";
        case GREATER:
          return ">";
        case GREATER_EQUAL:
          return ">=";
        case IMPLIES:
          return "==>";
        case LESS:
          return "<";
        case LESS_EQUAL:
          return "<=";
        case MOD:
          return "%";
        case MUL:
          return "*";
        case NOT_EQUAL:
          return "!=";
        case OR:
          return SparkUIF.$OR.toString();
        case OR_ELSE:
          return "||";
        case POW:
          return SparkUIF.$POW.toString();
        case REM:
          return SparkUIF.$REM.toString();
        case SUB:
          return "-";
        case XOR:
          return SparkUIF.$XOR.toString();
      }
      throw new RuntimeException("Unexpected");
    }

    public StringTemplate getCallTransTemplate() {
      StringTemplate stRet = null;
      if (peekGenThreeAddress()) {
        stRet = this.stg.getInstanceOf("callTransformation");
      } else {
        stRet = this.stg.getInstanceOf("callExp");
      }
      return stRet;
    }

    public String getCurrentExitLoc() {
      return this.exitLoc.peek();
    }

    public String getCurrLocLabel() {
      return generateLocLabel(this.pilarLocIndex);
    }

    public String getFileName() {
      String ret;
      if (this.sco.getRegressionTesting()) {
        ret = this.filename;
      } else {
        ret = this.filepath;
      }
      return ret;
    }

    SymPackageElement getFirstSymPackageElement() {
      if (this.symbolStack.isEmpty()) {
        return null;
      }
      for (int i = 0; i < this.symbolStack.size(); i++) {
        if (this.symbolStack.peekFromTop(i) instanceof SymPackageElement) {
          return (SymPackageElement) this.symbolStack.peekFromTop(i);
        }
      }
      return null;
    }

    public StringTemplate getLastAddedLocation() {
      assert this.stLastAddedLocation != null;
      return this.stLastAddedLocation;
    }

    protected StringTemplate getLineAnnotation(final RegionSelection s) {
      return wrapString(SelectionUtil.getPilarAnnotation(
          s,
          this.sco.getRegressionTesting(),
          !SelectionUtil.isEmpty(s)));
    }

    public String getNextLocLabel() {
      return generateLocLabel(this.pilarLocIndex + 1);
    }

    public Map<String, String> getPackageMap() {
      return this.packageMap;
    }

    public void handleExp(final EvalResult theST, final Exp e) {
      assert theST.getTheReturnType() != null;

      if (peekGenThreeAddress()) {
        final SymDef theType = theST.getTheReturnType();

        final SymVariable temp = generateFreshTempVar(
            theType,
            SymOrigin.PACKAGE_BODY);

        // spark function calls are converted to call transformations in 3 add
        // code 
        StringTemplate toAdd = null;
        if (theST.getTheTemplate().getName().equals("callTransformation")) {
          final StringTemplate stCallTrans = theST.getTheTemplate();
          stCallTrans.setAttribute("assVar", temp.getTheTranslation());

          toAdd = this.stg.getInstanceOf("callWrapper");
          toAdd.setAttribute("theCallExp", stCallTrans);

          toAdd.setAttribute("locLabel", this.generateNewSTLoc());
          //this.generateNewSTLoc(e.getTheOptionalRegionSelection()));

        } else {
          toAdd = constructAssignment(
              temp.getTheTranslation(),
              theST.toString(),
              null);
        }
        addLocationToCurrentMethod(toAdd);

        this.pushResult(new EvalResult(wrapString(temp.getTheTranslation()),
            temp, theType));
      } else {
        this.pushResult(theST);
      }
    }

    public EvalResult helperAssignToTempVar(final EvalResult er) {
      return helperAssignToTempVar(er.getTheReturnType(), er.getTheTemplate()
          .toString());
    }

    public EvalResult helperAssignToTempVar(final SymDef type,
        final String trans) {
      assert peekGenThreeAddress();

      final SymVariable svTemp = generateFreshTempVar(type, SymOrigin.METHOD);

      // build the assignment stmt and add it to the current method
      addLocationToCurrentMethod(constructAssignment(
          svTemp.getTheTranslation(),
          trans,
          null));

      // return the temp variable
      return new EvalResult(wrapString(svTemp.getTheTranslation()), svTemp,
          type);
    }

    public SymDef inferNumericType(final String theNumberString) {

      if (theNumberString.contains(".")) {
        return this.st.getBaseType("float");
      } else {
        return this.st.getBaseType("integer");
      }
    }

    public boolean isIntegralType(final SymDef sd) {
      return sd instanceof SymIntegerDef;
    }

    public boolean isTypeEquivalent(final Class<?> clazz, final SymDef theSymDef) {
      SymDef temp = theSymDef;
      if (theSymDef instanceof SymPartialDef) {
        temp = ((SymPartialDef) theSymDef).getTheElaborationSymDef();
      }
      return clazz.isInstance(temp);
    }

    public StringTemplate newMark(final String startLocLabel,
        final String endLocLabel, final RegionSelection optRS) {
      return newMark(
          startLocLabel,
          endLocLabel,
          optRS != null ? getLineAnnotation(optRS) : null);
    }

    public StringTemplate newMark(final String start, final String end,
        final StringTemplate optRS) {
      final StringTemplate stm = this.stg.getInstanceOf("Mark");
      stm.setAttribute("startLabel", start);
      stm.setAttribute("endLabel", end);
      if (optRS != null) {
        stm.setAttribute("optRS", optRS);
      }
      return stm;
    }

    StringTemplate[] pacifyVargs(final StringTemplate[] vargs) {
      if ((vargs == null) || (vargs.length == 0)) {
        return null;
      }
      final List<StringTemplate> stl = new ArrayList<StringTemplate>();
      for (final StringTemplate s : vargs) {
        if (s != null) {
          stl.add(s);
        }
      }
      if (stl.isEmpty()) {
        return null;
      }
      return stl.toArray(new StringTemplate[stl.size()]);
    }

    public boolean peekGenThreeAddress() {
      if (this.suppressThreeAddressCode) {
        return false;
      }
      return this.genThreeAddCode.peek().booleanValue();
    }

    public StringTemplate popCurrentMethodStack() {
      assert !this.currentStMethodStack.isEmpty();
      this.stLastAddedLocation = null;
      return this.currentStMethodStack.pop();
    }

    public StringTemplate popCurrentPackageStack() {
      assert !this.currentStPackageStack.isEmpty();
      final StringTemplate temp = this.currentStPackageStack.pop();
      return temp;
    }

    public String popExitLoc() {
      return this.exitLoc.pop();
    }

    public boolean popGenThreeAddress() {
      if (this.suppressThreeAddressCode) {
        return false;
      }
      return this.genThreeAddCode.pop().booleanValue();
    }

    public EvalResult popResult() {
      return this.popResult(1);
    }

    public EvalResult popResult(final int expectedSize) {
      assert this.theResultStack.size() > 0;
      assert this.theResultStack.size() == expectedSize;
      return this.theResultStack.pop();
    }

    public String popStmtEndLocLabel() {
      assert this.stmtEndLocLabel != null;
      final String ret = this.stmtEndLocLabel;
      this.stmtEndLocLabel = null;
      return ret;
    }

    public Object popTheOptionalResult() {
      final Object oldResult = this.theOptionalResult;
      this.theOptionalResult = null;
      return oldResult;
    }

    public void pushCurrentMethodStack(final StringTemplate stInitProc) {
      assert stInitProc != null;
      this.currentStMethodStack.push(stInitProc);
      assert this.stLastAddedLocation == null;
    }

    public void pushCurrentPackageStack(final StringTemplate stPackage) {
      assert stPackage != null;
      this.currentStPackageStack.push(stPackage);
    }

    public void pushGenThreeAddress(final boolean b) {
      if (this.suppressThreeAddressCode) {
        return;
      }
      this.genThreeAddCode.push(Boolean.valueOf(b));
    }

    public void pushResult(final EvalResult e) {
      this.pushResult(e, 0);
    }

    public void pushResult(final EvalResult e, final int expectedSize) {
      assert this.theResultStack.size() == expectedSize;
      assert (e != null) && (e.getTheTemplate() != null);
      this.theResultStack.push(e);
    }

    public void pushResult(final StringTemplate stArg,
        final Symbol theResolvedSymbol, final SymDef theRetType) {
      this.pushResult(stArg, theResolvedSymbol, theRetType, 0);
    }

    public void pushResult(final StringTemplate stArg,
        final Symbol theResolvedSymbol, final SymDef theRetType,
        final int theExpectedSize) {
      this.pushResult(
          new EvalResult(stArg, theResolvedSymbol, theRetType),
          theExpectedSize);
    }

    public void pushStmtEndLocLabel(final String endLoc) {
      //assert this.locLabelStack == null;
      assert endLoc != null;
      this.stmtEndLocLabel = endLoc;
    }

    public void pushTheOptionalResult(final Object o) {
      assert (this.theOptionalResult == null) && (o != null);
      this.theOptionalResult = o;
    }

    /**
     * A call to this function should have been preceded by a call to
     * addTemporaryLocal
     * 
     * @param theCall
     * @param temp
     */
    public void removeTemporaryLocal(final SymCall theCall,
        final SymVariable temp) {
      final String lowerName = temp.getTheName().toLowerCase();
      final SymVariable removedObject = (SymVariable) SymbolTable
          .removeSymObject(theCall, lowerName);
      assert temp == removedObject;
      //assert theCall.getTheObjects().get(lowerName) == temp;
      //theCall.getTheObjects().remove(lowerName);
    }

    public SymObject removeTempVar(final SymVariable sv) {
      final String lowerName = sv.getTheName().toLowerCase();
      assert sv.getTheOptionalParent() != null;
      final SymPackageElement spe = sv.getTheOptionalParent();
      final SymVariable removedObject = (SymVariable) SymbolTable
          .removeSymObject(spe, lowerName);
      assert sv == removedObject;
      //assert spe.getTheObjects().containsKey(lowerName);
      //return spe.getTheObjects().remove(lowerName);
      return removedObject;
    }

    public void resetMethodFactory() {
      this.templocal = 0;
    }

    public Symbol resolveSymbol(final Node theNode) {
      return this.st.resolveSymbol(theNode);
    }

    public Symbol resolveSymbol(final Symbol theSymbol, final String theName,
        final Node theNode) {
      Symbol ret = resolveSymbol(theNode);
      if (ret == null) {
        ret = this.st.resolveSymbol(theSymbol, theName);
      }
      assert ret != null;
      return ret;
    }

    public boolean shouldAddElement(final SymOrigin theOrigin,
        final boolean inBody, final SymCall theOptionalCall) {
      switch (theOrigin) {
        case PACKAGE_BODY:
          return inBody;
        case METHOD:
          return inBody && (theOptionalCall != null);
        default:
          return !inBody;
      }
    }

    public boolean typeCompatible(final Symbol a, final Symbol b) {
      final SymDef aType = SymbolTable.getSymDef(a);
      final SymDef bType = SymbolTable.getSymDef(b);

      assert (aType != null) && (bType != null);

      // TODO: add type checking
      return true;
    }

    public SymDef unifyTypes(final Symbol expected, final Symbol a,
        final Symbol b) {

      assert typeCompatible(a, b);

      final SymDef ed = SymbolTable.getSymDef(expected);
      final SymDef ad = SymbolTable.getSymDef(a);
      //final SymDef bd = SymbolTable.getSymDef(b);

      // TODO: unify a and b and assign to ret
      final SymDef ret = ad;

      assert ret != null;

      assert (ed == null) || typeCompatible(ed, ret);

      return ed != null ? ed : ret;
    }

    public StringTemplate wrapString(final String theString) {
      final StringTemplate ret = this.stg.getInstanceOf("same");
      ret.setAttribute("ID", theString);
      return ret;
    }
  }

  protected static class PilarTranslatorVisitor extends
      NodeVisitor<PilarTranslatorContext> {

    public PilarTranslatorVisitor(final PilarTranslatorContext g) {
      super(g);
    }

    /**
     * Add constant declaration annotation to the package declaration
     * 
     * @param stp
     *          the string template of the package declaration
     * @param p
     *          the current package from the symbol table
     * @param body
     *          the body flag indicating whether or not this is in the package
     *          body
     */
    protected void helperAddConstDeclaration(final StringTemplate stp,
        final SymPackage p, final boolean body) {

      {
        // constants can be introduced during the translation phase so collect
        // the symconstants first to prevent concurrent mod errors
        final List<SymConstant> theDeclaredConstants = new ArrayList<SymConstant>();
        for (final SymObject so : SymbolTable.linearizeObjects(p
            .getTheObjects())) {//.values()) {
          if (so instanceof SymConstant) {
            theDeclaredConstants.add((SymConstant) so);
          }
        }

        // first add the package level constants
        helperAddConstDeclarationHelper(stp, body, theDeclaredConstants, null);
      }

      {
        // now add constants declared in the procedures
        for (final SymProcedure sp : p.getTheProcedures().values()) {
          final List<SymConstant> theDeclaredConstants = new ArrayList<SymConstant>();

          for (final SymObject so : SymbolTable.linearizeObjects(sp
              .getTheObjects())) {//.values()) {
            if (so instanceof SymConstant) {
              theDeclaredConstants.add((SymConstant) so);
            }
          }

          this.g.symbolStack.push(sp);

          helperAddConstDeclarationHelper(stp, body, theDeclaredConstants, sp);

          this.g.symbolStack.pop();
        }
      }

      {
        // now add constants declared in the functions
        for (final SymFunction sf : p.getTheFunctions().values()) {
          final List<SymConstant> theDeclaredConstants = new ArrayList<SymConstant>();
          for (final SymObject so : SymbolTable.linearizeObjects(sf
              .getTheObjects())) {
            if (so instanceof SymConstant) {
              theDeclaredConstants.add((SymConstant) so);
            }
          }

          this.g.symbolStack.push(sf);

          helperAddConstDeclarationHelper(stp, body, theDeclaredConstants, sf);

          this.g.symbolStack.pop();
        }
      }
    }

    void helperAddConstDeclarationHelper(final StringTemplate stp,
        final boolean body, final List<SymConstant> theList,
        final SymCall theOptionalCall) {

      final StringTemplate stConst = this.g.stg
          .getInstanceOf("ConstantDeclaration");

      final String constName = theOptionalCall == null ? SparkUtil.SPARK_CONSTANT_PREFIX
          : SparkUtil.SPARK_CONSTANT_METHOD_DEF_PREFIX
              + theOptionalCall.getTheName();

      stConst.setAttribute("constName", constName);

      boolean modified = false;

      for (final SymConstant c : theList) {

        //If the origin equals body and the bodyFlag is set add constant
        //If the origin is not body and the bodyFlag is false add constant
        if (this.g.shouldAddElement(c.getTheOrigin(), body, theOptionalCall)) {
          modified = true;
          final StringTemplate stConstElement = this.g.stg
              .getInstanceOf("constElement");

          // lhs
          {
            stConstElement.setAttribute(
                "constElementName",
                SymbolTable.returnPilarCompliantName(c.getTheName()));
          }

          visit(c.getTheExp());
          final EvalResult theResult = this.g.popResult();

          if (c.getTheOptionalTypeSymDef() == null) {
            assert theResult.getTheReturnType() != null;
            c.setTheOptionalTypeSymDef(theResult.getTheReturnType());
          }

          stConstElement.setAttribute("castType", c.getTheOptionalTypeSymDef()
              .getTheTranslation());

          stConstElement.setAttribute("staticInitializingExp", theResult);

          final StringTemplate stCA = this.g.stg
              .getInstanceOf("ConstantAnnotation");
          {
            stCA.setAttribute("theOrigin", '@' + c.getTheOrigin().toString());

            stCA.setAttribute(
                "theLoc",
                this.g.getLineAnnotation(c.getTheSelection()));
          }
          stConstElement.setAttribute("annotation", stCA);

          stConst.setAttribute("constElement", stConstElement);
        }
      }

      if (modified) {
        stp.setAttribute("packageElement", stConst);
      }
    }

    /**
     * Add globals and initialization procedure to the package declaration
     * 
     * @param stp
     *          the string template of the package declaration
     * @param p
     *          the current package from the symbol table
     * @param body
     *          the body flag indicating whether or not this is in the package
     *          body
     * @param optionalStatementList
     *          the optional statement list found in the Package Body that needs
     *          to be added to the initialization procedure
     */
    protected void helperAddGlobalsAndInit(final StringTemplate stp,
        final SymPackage p, final boolean body,
        final StatementList optionalStatementList) {

      //create global string template
      final StringTemplate stGlobal = this.g.stg.getInstanceOf("globals");
      //create init string template and init Symbol
      final StringTemplate stInitProc = this.g.stg
          .getInstanceOf("procedureImplementation");

      final String methodName = body ? SparkUtil.INIT_BODY_PROC_NAME
          : SparkUtil.INIT_SPEC_PROC_NAME;

      final SymProcedure initProc = SymMethodFactory.newSymProcedure(
          methodName,
          methodName,
          p);

      boolean nonEmptyInitProc = false;

      //push the init Procedure
      this.g.symbolStack.push(initProc);

      this.g.pushCurrentMethodStack(stInitProc);
      this.g.pushGenThreeAddress(true);
      //add globals and their initializations
      for (final SymObject ob : SymbolTable.linearizeObjects(p.getTheObjects())) {
        if (ob instanceof SymVariable) {
          final SymVariable var = (SymVariable) ob;
          if (this.g.shouldAddElement(var.getTheOrigin(), body, null)) {

            /******************************************************************
             * ADD GLOBALS
             *****************************************************************/
            final StringTemplate stSingleGlobal = this.g.stg
                .getInstanceOf("global");

            // NOTE: here we have to use the original spark name since pilar
            // does not allow '::' to appear in global names.  Instead we'll
            // stick it's fully qualified name into a GlobalVarAnnotation
            if (ob.getTheOptionalTypeSymDef() != null) {
              final String type = ob.getTheOptionalTypeSymDef()
                  .getTheTranslation();

              if (ob.getTheKind() == SymObjectKind.ABSTRACT_VARIABLE) {
                stSingleGlobal.setAttribute("global", type + " "
                    + SparkUtil.GLOBAL_TEMP_VAR_MARKER + ob.getTheName());
              } else {
                stSingleGlobal.setAttribute("global", type + " "
                    + SparkUtil.GLOBAL_VAR_MARKER + ob.getTheName());
              }
            } else {
              // we should never get here anymore
              throw new RuntimeException("Unexpected");
            }

            final StringTemplate stGlobalAnnot = this.g.stg
                .getInstanceOf("GlobalVarAnnotion");
            {
              final StringTemplate stSymbolEntry = this.g.stg
                  .getInstanceOf("SymbolEntry");
              stSymbolEntry.setAttribute("sparkID", ob.getTheName());
              stSymbolEntry
                  .setAttribute("pilarSparkID", ob.getTheTranslation());
              stSymbolEntry.setAttribute("originTag", '@' + ob.getTheOrigin()
                  .toString());
              stSymbolEntry.setAttribute("kindTag", '@' + ob.getTheKind()
                  .toString());

              stGlobalAnnot.setAttribute("symbolEntry", stSymbolEntry);

              assert ob.getTheSelection() != null;
              stGlobalAnnot.setAttribute(
                  "loc",
                  this.g.getLineAnnotation(ob.getTheSelection()));

              stSingleGlobal.setAttribute("annotation", stGlobalAnnot);
            }
            stGlobal.setAttribute("global", stSingleGlobal);

            /******************************************************************
             * ADD GLOBALS INIT
             *****************************************************************/
            if (var.getTheExp() != null) {
              nonEmptyInitProc = true;
              final String startLocLabel = this.g.getNextLocLabel();
              visit(var.getTheExp());
              final EvalResult theResult = this.g.popResult();

              assert this.g.typeCompatible(
                  ob.getTheOptionalTypeSymDef(),
                  theResult.getTheReturnType());

              final RegionSelection rs = var.getTheExp()
                  .getTheOptionalRegionSelection() != null ? var.getTheExp()
                  .getTheOptionalRegionSelection() : var.getTheSelection();

              final StringTemplate stdpsa = this.g.createStmtAnnot(
                  var,
                  this.g.newMark(startLocLabel, this.g.getNextLocLabel(), rs));

              final StringTemplate stAssStatement = this.g.constructAssignment(
                  ob.getTheTranslation(),
                  theResult.getTheTemplate().toString(),
                  (RegionSelection) null,
                  stdpsa);

              this.g.addLocationToCurrentMethod(stAssStatement);
            }
          }
        }
      }

      //add the optional statement list to the init
      if (optionalStatementList != null) {
        nonEmptyInitProc = true;
        visit(optionalStatementList);
      }

      stInitProc.setAttribute("location", "#locret. return;");
      //pop off the init procedure
      this.g.symbolStack.pop();

      /************************************************************************
       * add the locals to the init procedure body
       ***********************************************************************/
      for (final SymObject local : SymbolTable.linearizeObjects(initProc
          .getTheObjects())) {
        assert local.getTheOptionalTypeSymDef() != null;

        final StringTemplate stLocVar = this.g.stg
            .getInstanceOf("localVarDeclaration");
        stLocVar.setAttribute("type", local.getTheOptionalTypeSymDef()
            .getTheTranslation());
        stLocVar.setAttribute(
            "name",
            SymbolTable.returnPilarCompliantName(local.getTheName()));

        final StringTemplate stLVDA = this.g.stg
            .getInstanceOf("localVarDeclarationAnnotation");
        stLVDA.setAttribute("theKind", "@" + local.getTheKind().toString());

        if (local.getTheKind() == SymObjectKind.CONCRETE_VARIABLE) {
          stLVDA.setAttribute(
              "theOptionalSelection",
              this.g.getLineAnnotation(local.getTheSelection()));
        }
        stLocVar.setAttribute("annotation", stLVDA);

        stInitProc.setAttribute("localVarDeclaration", stLocVar);
      }
      this.g.popGenThreeAddress();

      /************************************************************************
       * Add Globals Declaration comment block
       ***********************************************************************/
      final StringTemplate stGlobalsBlockComment = this.g.createCommentBlock(
          true,
          "G l o b a l   D e c l a r a t i o n s");
      stp.setAttribute("packageElement", stGlobalsBlockComment);
      //add the globals to the package declaration string template
      stp.setAttribute("packageElement", stGlobal);
      //Add Initialization Procedure comment block
      final StringTemplate stInitBlockComment = this.g.createCommentBlock(
          true,
          "I n i t i a l i z a t i o n   P r o c e d u r e");
      stp.setAttribute("packageElement", stInitBlockComment);
      //create init procedure and add it to the package declaration string template
      stInitProc.setAttribute("ID", initProc.getTheName());

      if (nonEmptyInitProc) {
        stp.setAttribute("packageElement", stInitProc);
      }

      this.g.popCurrentMethodStack();
    }

    private void helperAddParameters(final StringTemplate st,
        final Collection<SymParameter> theParams, final boolean inSpec) {

      for (final SymParameter p : theParams) {
        final String type = p.getTheOptionalTypeSymDef().getTheTranslation();

        final RegionSelection rs = inSpec ? p.getTheOptionalSpecSelection() : p
            .getTheOptionalBodySelection();

        st.setAttribute("params", type + " " + p.getTheTranslation() + " "
            + this.g.getLineAnnotation(rs));
      }
    }

    private void helperAddRepresentations(final StringTemplate stStore,
        final SymPackageElement sym) {
      if (sym.getTheOptionalRepresentationClauses() == null) {
        return;
      }

      for (final RepresentationClause rc : sym
          .getTheOptionalRepresentationClauses()) {
        visit(rc);
        System.out.println("What should we do with this?");
        System.out.println(this.g.popResult());
      }
    }

    /**
     * Add the type declaration annotations to the package declaration
     * 
     * @param stp
     *          the string template of the package declaration
     * @param p
     *          the current package from the symbol table
     * @param body
     *          the body flag indicating whether or not this is in the package
     *          body
     */
    protected void helperAddTypeDeclarationAnnotations(
        final StringTemplate stp, final SymPackage p, final boolean body) {
      //Add Type Declaration comment block
      final StringTemplate stTypeBlockComment = this.g.createCommentBlock(
          true,
          "T y p e   D e c l a r a t i o n s");
      stp.setAttribute("packageElement", stTypeBlockComment);

      // emit package level types
      helperAddTypeDeclarationAnnotationsHelper(
          stp,
          p.getTheDefinitions(),
          p,
          null,
          body);

      // emit procedure level types
      if (body) {
        for (final SymProcedure sp : p.getTheProcedures().values()) {
          this.g.symbolStack.push(sp);
          helperAddTypeDeclarationAnnotationsHelper(
              stp,
              sp.getTheDefinitions(),
              p,
              sp,
              body);
          this.g.symbolStack.pop();
        }
      }
    }

    void helperAddTypeDeclarationAnnotationsHelper(final StringTemplate stp,
        final Map<String, SymDef> theDefinitions, final SymPackage thePackage,
        final SymCall theOptionalCall, final boolean body) {

      for (final SymDef def : theDefinitions.values()) {
        if (this.g.shouldAddElement(def.getTheOrigin(), body, theOptionalCall)) {
          //((def.getTheOrigin() == SymOrigin.PACKAGE_BODY) && body)
          //|| ((def.getTheOrigin() != SymOrigin.PACKAGE_BODY) && !body)
          //|| (((def.getTheOrigin() == SymOrigin.METHOD) && body && (theOptionalCall != null)))) {

          final StringTemplate stTypeDeclaration = this.g.stg
              .getInstanceOf("TypeDeclaration");
          StringTemplate stAnnotatedTypeDeclaration = null;
          stTypeDeclaration.setAttribute(
              "id",
              SymbolTable.returnPilarCompliantName(def.getTheName()));

          if (def.getIsPrivateTypeDeclaration()) {
            stAnnotatedTypeDeclaration = this.g.stg
                .getInstanceOf("PrivateTypeDeclaration");
            stAnnotatedTypeDeclaration.setAttribute("origin", "@"
                + def.getTheOrigin().toString());
            stAnnotatedTypeDeclaration.setAttribute(
                "loc",
                this.g.getLineAnnotation(def.getTheSelection()));

            stAnnotatedTypeDeclaration.setAttribute("theDeclarationLoc", this.g
                .getLineAnnotation(def
                    .getTheOptionalPrivateTypeDeclarationSelection()));

            stAnnotatedTypeDeclaration.setAttribute(
                "tagged",
                Boolean.valueOf(def.getIsTagged()));
            stAnnotatedTypeDeclaration.setAttribute(
                "limited",
                Boolean.valueOf(def.getIsLimited()));
          } else {
            stAnnotatedTypeDeclaration = this.g.stg
                .getInstanceOf("FullTypeDeclaration");
            stAnnotatedTypeDeclaration.setAttribute("origin", "@"
                + def.getTheOrigin().toString());
            stAnnotatedTypeDeclaration.setAttribute(
                "loc",
                this.g.getLineAnnotation(def.getTheSelection()));
          }
          ;

          switch (def.getDescriptor()) {
            case SymArrayDef.DESCRIPTOR: {
              final SymArrayDef a = (SymArrayDef) def;
              StringTemplate stArrayTypeDefinition = null;

              String subTypeKey = null;
              if (a.getIsConstrained()) {
                stArrayTypeDefinition = this.g.stg
                    .getInstanceOf("ConstrainedArrayDefinition");
                subTypeKey = "theDiscreteSubTypes";
              } else {
                stArrayTypeDefinition = this.g.stg
                    .getInstanceOf("UnconstrainedArrayDefinition");
                subTypeKey = "theIndexSubTypes";
              }
              assert stArrayTypeDefinition != null;

              stArrayTypeDefinition.setAttribute(
                  "theDimensions",
                  a.getTheDimensions());

              final StringTemplate stList = this.g.stg
                  .getInstanceOf("ListAnnotation");

              assert !a.getTheIndexSubtypeDefs().isEmpty();
              for (final SymDef sd : a.getTheIndexSubtypeDefs()) {
                stList.setAttribute("element", "`" + sd.getTheTranslation());
              }
              stArrayTypeDefinition.setAttribute(subTypeKey, stList);

              stArrayTypeDefinition.setAttribute("theComponentSubType", a
                  .getTheComponentSubtypeDef().getTheTranslation());

              stAnnotatedTypeDeclaration.setAttribute(
                  "typeDefinition",
                  stArrayTypeDefinition);
              break;
            }
            case SymEnumerationDef.DESCRIPTOR: {
              final SymEnumerationDef e = (SymEnumerationDef) def;
              final StringTemplate stEnumerationTypeDefinition = this.g.stg
                  .getInstanceOf("EnumerationTypeDefinition");
              final ArrayList<StringTemplate> stEnumerationValues = new ArrayList<StringTemplate>();
              for (final SymEnumElementDef val : e.getTheElements()) {
                final StringTemplate stEnumerationValue = this.g.stg
                    .getInstanceOf("EnumerationValue");
                stEnumerationValue.setAttribute(
                    "tag",
                    SymbolTable.returnPilarCompliantName(val.getTheName()));
                stEnumerationValues.add(stEnumerationValue);
              }
              stEnumerationTypeDefinition.setAttribute(
                  "enumerationValues",
                  this.g.createListFromTemplate(stEnumerationValues));
              stAnnotatedTypeDeclaration.setAttribute(
                  "typeDefinition",
                  stEnumerationTypeDefinition);
              break;
            }
            case SymFixedDef.DESCRIPTOR: {
              final SymFixedDef sfd = (SymFixedDef) def;

              final StringTemplate stFTD = this.g.stg
                  .getInstanceOf("OrdinaryFixedPointDefinition");

              visit(sfd.getTheExp());
              final EvalResult theExp = this.g.popResult();
              stFTD.setAttribute("theDigitsExp", theExp.toString());

              visit(sfd.getTheLowRangeExp());
              final EvalResult lbound = this.g.popResult();
              stFTD.setAttribute("theLowRangeExp", lbound.toString());

              visit(sfd.getTheHighRangeExp());
              final EvalResult hbound = this.g.popResult();
              stFTD.setAttribute("theHighRangeExp", hbound.toString());

              stAnnotatedTypeDeclaration.setAttribute("typeDefinition", stFTD);
              break;
            }
            case SymFloatingDef.DESCRIPTOR: {
              final SymFloatingDef sfd = (SymFloatingDef) def;
              final StringTemplate stFTD = this.g.stg
                  .getInstanceOf("FloatingPointDefinition");

              visit(sfd.getTheExp());
              final EvalResult theExp = this.g.popResult();
              stFTD.setAttribute("theDigitsExp", theExp.toString());

              if (sfd.getTheOptionalLowRangeExp() != null) {
                visit(sfd.getTheOptionalLowRangeExp());
                final EvalResult lbound = this.g.popResult();
                stFTD.setAttribute("theOptionalLowRangeExp", lbound.toString());
              }

              if (sfd.getTheOptionalHighRangeExp() != null) {
                visit(sfd.getTheOptionalHighRangeExp());
                final EvalResult hbound = this.g.popResult();

                stFTD
                    .setAttribute("theOptionalHighRangeExp", hbound.toString());
              }

              stAnnotatedTypeDeclaration.setAttribute("typeDefinition", stFTD);
              break;
            }
            case SymIndexSubTypeDef.DESCRIPTOR: {
              final SymIndexSubTypeDef i = (SymIndexSubTypeDef) def;
              stAnnotatedTypeDeclaration = this.g.stg
                  .getInstanceOf("SubTypeDeclaration");
              stAnnotatedTypeDeclaration.setAttribute("origin", "@"
                  + def.getTheOrigin().toString());
              stAnnotatedTypeDeclaration.setAttribute(
                  "loc",
                  this.g.getLineAnnotation(def.getTheSelection()));

              stAnnotatedTypeDeclaration.setAttribute("type", i
                  .getTheParentTypeDef().getTheTranslation());

              final StringTemplate stIndexConstraint = this.g.stg
                  .getInstanceOf("IndexConstraint");
              final StringTemplate stList = this.g.stg
                  .getInstanceOf("ListAnnotation");

              for (final SymDef sd : i.getTheSubTypeDefs()) {
                stList.setAttribute("element", "`" + sd.getTheTranslation());
              }
              stIndexConstraint.setAttribute("subtypeMarks", stList);
              stAnnotatedTypeDeclaration.setAttribute(
                  "constraint",
                  stIndexConstraint);
              break;
            }
            case SymSignedIntegerDef.DESCRIPTOR: {
              final SymSignedIntegerDef i = (SymSignedIntegerDef) def;
              final StringTemplate stIntegerTypeDefinition = this.g.stg
                  .getInstanceOf("SignedIntegerTypeDefinition");
              {
                visit(i.getTheLowRangeExp());
                final EvalResult lbound = this.g.popResult();
                stIntegerTypeDefinition.setAttribute("theLowRangeExp", lbound);
              }
              {
                visit(i.getTheHighRangeExp());
                final EvalResult hbound = this.g.popResult();

                stIntegerTypeDefinition.setAttribute("theHighRangeExp", hbound);
              }
              stAnnotatedTypeDeclaration.setAttribute(
                  "typeDefinition",
                  stIntegerTypeDefinition);
              break;
            }
            case SymModDef.DESCRIPTOR: {
              final SymModDef m = (SymModDef) def;
              final StringTemplate stModularTypeDefinition = this.g.stg
                  .getInstanceOf("ModularTypeDefinition");
              visit(m.getTheExp());
              stModularTypeDefinition.setAttribute("theModExp", this.g
                  .popResult().toString());
              stAnnotatedTypeDeclaration.setAttribute(
                  "typeDefinition",
                  stModularTypeDefinition);
              break;
            }
            case SymPartialDef.DESCRIPTOR: {
              final SymPartialDef spd = (SymPartialDef) def;
              throw new UnsupportedOperationException(
                  "SymPartialDefs not handled yet: " + spd.getTheName());
            }
            case SymRangeSubTypeDef.DESCRIPTOR: {
              final SymRangeSubTypeDef r = (SymRangeSubTypeDef) def;
              stAnnotatedTypeDeclaration = this.g.stg
                  .getInstanceOf("SubTypeDeclaration");
              stAnnotatedTypeDeclaration.setAttribute("origin", "@"
                  + def.getTheOrigin().toString());
              stAnnotatedTypeDeclaration.setAttribute(
                  "loc",
                  this.g.getLineAnnotation(def.getTheSelection()));

              stAnnotatedTypeDeclaration.setAttribute("type", r
                  .getTheParentTypeDef().getTheTranslation());

              final StringTemplate stRangeConstraint = this.g.stg
                  .getInstanceOf("SimpleRangeConstraint");
              {
                visit(r.getTheLowRangeExp());
                final EvalResult lbound = this.g.popResult();
                stRangeConstraint.setAttribute("lowerBound", lbound);
              }
              {
                visit(r.getTheHighRangeExp());
                final EvalResult hbound = this.g.popResult();
                stRangeConstraint.setAttribute("upperBound", hbound);
              }
              stAnnotatedTypeDeclaration.setAttribute(
                  "constraint",
                  stRangeConstraint);

              break;
            }
            case SymRecordDef.DESCRIPTOR: {
              final SymRecordDef r = (SymRecordDef) def;
              final StringTemplate stRecordTypeDefinition = this.g.stg
                  .getInstanceOf("RecordTypeDefinition");
              stRecordTypeDefinition.setAttribute(
                  "tagged",
                  Boolean.valueOf(r.getIsRecordTagged()));

              if (r.getTheOptionalParentDef() != null) {
                stRecordTypeDefinition.setAttribute("optParent", r
                    .getTheOptionalParentDef().getTheTranslation());
              }

              stAnnotatedTypeDeclaration.setAttribute(
                  "typeDefinition",
                  stRecordTypeDefinition);

              for (final SymRecordComponentDef c : r.getTheElements().values()) {
                final StringTemplate rtdc = this.g.stg
                    .getInstanceOf("RecordTypeDefinitionComponent");
                rtdc.setAttribute("componentType", c.getTheTypeSymDef()
                    .getTheTranslation());
                rtdc.setAttribute("componentID", c.getTheTranslation());

                final StringTemplate rca = this.g.stg
                    .getInstanceOf("RecordComponentAnnotation");
                rca.setAttribute("sparkID", c.getTheName());
                rtdc.setAttribute("annotation", rca);

                stTypeDeclaration.setAttribute("records", rtdc);
              }
              break;
            }
            case SymAbstractDef.DESCRIPTOR: {
              final StringTemplate stAbstractDef = this.g.stg
                  .getInstanceOf("AbstractTypeDefinition");

              stAnnotatedTypeDeclaration.setAttribute(
                  "typeDefinition",
                  stAbstractDef);
              break;
            }
            default: {
              throw new RuntimeException("Unexpected");
            }
          }
          stAnnotatedTypeDeclaration.setAttribute(
              "fullyQualifiedName",
              def.getTheTranslation());

          stTypeDeclaration.setAttribute("type", stAnnotatedTypeDeclaration);
          if (!(def instanceof SymPartialDef)) {
            stp.setAttribute("packageElement", stTypeDeclaration);
          } else {
            throw new RuntimeException("Unexpected");
          }
        }
      }
    }

    /**
     * @param sa
     * @param o
     *          the object or type which the attribute is being applied to
     * @param retType
     *          the resultant type
     * @param theOptionalExps
     * @return
     */
    private EvalResult helperApplyAttribute(final SparkAttribute sa,
        final Symbol o, final SymDef retType, final String[] theOptionalExps) {

      assert sa != null;
      assert o != null;

      final StringTemplate stAtt = this.g.stg.getInstanceOf("attribute");

      if (o instanceof SymObject) {
        // if o is a SymConstant, SymParameter, or a SymVariable then don't
        // create a pilar TypeExp, just use the object's name
        stAtt.setAttribute("isNameExp", Boolean.TRUE);
      }

      stAtt.setAttribute("attribute", sa.toString());
      stAtt.setAttribute("typeName", o.getTheTranslation());

      if (theOptionalExps != null) {
        for (final String s : theOptionalExps) {
          stAtt.setAttribute("optExp", s);
        }
      }

      return new EvalResult(stAtt, o, retType);
    }

    private EvalResult helperApplyAttribute(final SparkAttribute sa,
        final SymDef o, final String... theOptionalExps) {
      return this.helperApplyAttribute(sa, o, o, theOptionalExps);
    }

    private StringTemplate helperAssertCheckStatement(final ProofStatement o,
        final Predicate p) { // 
      final String startLabel = this.g.getNextLocLabel();

      assert !this.g.inProofContext;

      this.g.pushGenThreeAddress(false);
      this.g.inProofContext = true;
      visit(p);
      this.g.inProofContext = false;
      this.g.popGenThreeAddress();

      final EvalResult theExp = this.g.popResult();

      final StringTemplate stAssertStatement = this.g.stg
          .getInstanceOf("assertStatement");
      stAssertStatement.setAttribute("theExp", theExp);

      final String endLabel = this.g.getNextLocLabel();
      final StringTemplate stl2 = this.g.createStmtAnnot(
          o,
          this.g.newMark(
              startLabel,
              endLabel,
              o.getTheOptionalRegionSelection()));

      final StringTemplate stLocationAnnotation = this.g
          .generateNewAnnotatedLoc(stl2);

      stAssertStatement.setAttribute("theAnnotatedLoc", stLocationAnnotation);

      this.g.pushStmtEndLocLabel(endLabel);

      return stAssertStatement;
    }

    private void helperBuildAdaFunctionAnnotation(final StringTemplate stAFA,
        final SymFunction symFunc) {
      helperBuildAdaMethodAnnotation(stAFA, symFunc);
    }

    private void helperBuildAdaFunctionAnnotationForProofFunc(
        final StringTemplate stAFA, final SymFunction symFunc,
        final Collection<SymParameter> theParamsWithInGlobals) {
      helperBuildAdaMethodAnnotation(stAFA, symFunc, theParamsWithInGlobals);
    }

    private void helperBuildAdaMethodAnnotation(final StringTemplate stAMA,
        final SymCall symCall) {
      this.helperBuildAdaMethodAnnotation(stAMA, symCall, null);
    }

    private void helperBuildAdaMethodAnnotation(final StringTemplate stAMA,
        final SymCall symCall, final Collection<SymParameter> optionalParams) {

      final SymOrigin so = this.g.inSpecPart.peek().booleanValue() ? (symCall
          .getIsPrivate() ? SymOrigin.PACKAGE_SPEC_PRIVATE
          : SymOrigin.PACKAGE_SPEC_PUBLIC) : SymOrigin.PACKAGE_BODY;

      stAMA.setAttribute("theOrigin", "@" + so.toString());

      final ArrayList<String> specInParams = new ArrayList<String>();

      final Collection<SymParameter> c = optionalParams != null ? optionalParams
          : symCall.getTheParameters().values();

      /************************************************************************
       * DETERMINE MODES FOR THE PARAMETERS
       ***********************************************************************/
      for (final SymParameter p : c) {
        switch (p.getTheMode()) {
          case IN:
          case IN_OUT:
            specInParams.add(p.getTheTranslation());
            break;
          default:
        }
      }

      if (!specInParams.isEmpty()) {
        stAMA.setAttribute(
            "theOptionalInParameters",
            this.g.createListFromString(specInParams));
      }
    }

    private void helperBuildAdaProcedureAnnotation(final StringTemplate stAPA,
        final SymProcedure symProc) {

      helperBuildAdaMethodAnnotation(stAPA, symProc);

      final ArrayList<String> specOutParams = new ArrayList<String>();
      for (final SymParameter p : symProc.getTheParameters().values()) {
        switch (p.getTheMode()) {
          case OUT:
          case IN_OUT:
            specOutParams.add(p.getTheTranslation());
            break;
          default:
        }
      }

      if (!specOutParams.isEmpty()) {
        stAPA.setAttribute(
            "theOptionalOutParameters",
            this.g.createListFromString(specOutParams));
      }
    }

    private boolean helperBuildFunctionAnnotation(final StringTemplate stFA,
        final LinkedHashSet<SymGlobalAnnotation> theOptionalDeclaredGlobals,
        final RegionSelection theOptionalDeclaredGlobalsSelection,
        final ArrayList<SymGlobalAnnotation> theInGlobals,
        final Precondition theOptionalPrecondition,
        final ReturnAnnotation theOptionalReturnAnnotation) {

      boolean didSomething = helperBuildSparkMethodAnnotation(
          stFA,
          theOptionalDeclaredGlobals,
          theOptionalDeclaredGlobalsSelection,
          theInGlobals,
          theOptionalPrecondition);

      if (theOptionalReturnAnnotation != null) {
        visit(theOptionalReturnAnnotation);
        final EvalResult returns = this.g.popResult();

        final StringTemplate stReturn = this.g.stg
            .getInstanceOf("ContractPredicate");
        stReturn.setAttribute(
            "theType",
            '@' + SPARKPredicateType.RETURN.toString());
        stReturn.setAttribute("theExp", returns.getTheTemplate());
        stReturn.setAttribute("theLoc", this.g
            .getLineAnnotation(theOptionalReturnAnnotation
                .getTheOptionalRegionSelection()));
        returns.setTheTemplate(stReturn);

        stFA.setAttribute("theOptionalReturnAnnotation", stReturn);

        didSomething = true;
      }

      return didSomething;
    }

    private StringTemplate helperBuildMethodAnnotation(
        final StringTemplate stAdaProcedureAnnotation,
        final StringTemplate stPA, final RegionSelection theMethodSelection,
        final RegionSelection theMethodNameSelection,
        final RegionSelection theMethodSpecificationSelection) {
      final StringTemplate stMethodAnnotation = this.g.stg
          .getInstanceOf("MethodAnnotation");
      {
        stMethodAnnotation.setAttribute(
            "theSourceFileName",
            this.g.emitFileName());

        stMethodAnnotation.setAttribute(
            "theAdaMethodAnnotation",
            stAdaProcedureAnnotation);

        if (stPA != null) {
          stMethodAnnotation.setAttribute(
              "theOptionalSPARKMethodAnnotation",
              stPA);
        }

        stMethodAnnotation.setAttribute(
            "theMethodSelection",
            this.g.getLineAnnotation(theMethodSelection));
        stMethodAnnotation.setAttribute(
            "theMethodNameSelection",
            this.g.getLineAnnotation(theMethodNameSelection));
        stMethodAnnotation.setAttribute(
            "theMethodSpecificationSelection",
            this.g.getLineAnnotation(theMethodSpecificationSelection));
      }
      return stMethodAnnotation;
    }

    private void helperBuildPackageHeaderAnnotation(final StringTemplate stPHA,
        final LinkedHashSet<SymPackage> theWithPackages,
        final LinkedHashSet<SymDef> theUseTypeClauses) {

      /**********************************************************************
       * OPTIONAL CONTEXT CLAUSE
       *********************************************************************/
      if (!theWithPackages.isEmpty() || !theUseTypeClauses.isEmpty()) {

        final StringTemplate stContextClause = this.g.stg
            .getInstanceOf("ContextClause");

        /**********************************************************************
         * OPTIONAL WITH CLAUSES
         *********************************************************************/
        if (!theWithPackages.isEmpty()) {
          final ArrayList<String> withClauses = new ArrayList<String>();

          for (final SymPackage withed : theWithPackages) {
            withClauses.add(withed.getTheTranslation());
          }
          stContextClause.setAttribute(
              "theOptionalWithClauses",
              this.g.createListFromString(withClauses));
        }

        /**********************************************************************
         * OPTIONAL USE TYPE CLAUSES
         *********************************************************************/
        if (!theUseTypeClauses.isEmpty()) {
          final ArrayList<String> theUseTypes = new ArrayList<String>();

          for (final SymDef theUseType : theUseTypeClauses) {
            theUseTypes.add(theUseType.getTheTranslation());
          }
          stContextClause.setAttribute(
              "theOptionalUseTypeClauses",
              this.g.createListFromString(theUseTypes));
        }
        stPHA.setAttribute("theOptionalContextClause", stContextClause);
      }
    }

    private boolean helperBuildProcedureAnnotation(final StringTemplate stPA,
        final ContainerDependencyRelation theOptionalDependencyRelation,
        final Postcondition theOptionalPostcondition,
        final ArrayList<SymGlobalAnnotation> theOutGlobals) {

      boolean didSomething = false;

      if (!theOutGlobals.isEmpty()) {
        /*********************************************************************
         * OPTIONAL MODED OUT SPEC GLOBALS
         ********************************************************************/
        final ArrayList<String> specOutGlobals = new ArrayList<String>();
        for (final SymGlobalAnnotation sgaOutSpec : theOutGlobals) {
          specOutGlobals.add(sgaOutSpec.getTheObject().getTheTranslation());
        }
        stPA.setAttribute(
            "theOptionalOutGlobals",
            this.g.createListFromString(specOutGlobals));
        didSomething = true;
      }

      if (theOptionalDependencyRelation != null) {
        /*********************************************************************
         * OPTIONAL SPEC DERIVES
         ********************************************************************/
        final StringTemplate stTheDependencyRelation = helperCreateDependencyRelationAnnotation(theOptionalDependencyRelation);
        stPA.setAttribute(
            "theOptionalDependencyRelation",
            stTheDependencyRelation);
        didSomething = true;
      }

      if (theOptionalPostcondition != null) {
        /*********************************************************************
         * OPTIONAL SPEC POST CONDITION
         ********************************************************************/
        final SymDef boolType = this.g.st.getBaseType("boolean");

        EvalResult postCond = new EvalResult(this.g.NONE, null, boolType);

        visit(theOptionalPostcondition);
        postCond = this.g.popResult();
        assert postCond.getTheReturnType() == boolType;

        final StringTemplate stPostCond = this.g.stg
            .getInstanceOf("ContractPredicate");
        stPostCond.setAttribute(
            "theType",
            '@' + SPARKPredicateType.POST.toString());
        stPostCond.setAttribute("theExp", postCond.getTheTemplate());

        stPostCond.setAttribute("theLoc", this.g
            .getLineAnnotation(theOptionalPostcondition
                .getTheOptionalRegionSelection()));
        postCond.setTheTemplate(stPostCond);

        stPA.setAttribute("theOptionalPostcondition", stPostCond);
        didSomething = true;
      }

      return didSomething;
    }

    private boolean helperBuildProcedureAnnotation(final StringTemplate stPA,
        final LinkedHashSet<SymGlobalAnnotation> theOptionalDeclaredGlobals,
        final RegionSelection theOptionalDeclaredGlobalsSelection,
        final ArrayList<SymGlobalAnnotation> theInGlobals,
        final Precondition theOptionalPrecondition,
        final ContainerDependencyRelation theOptionalDependencyRelation,
        final Postcondition theOptionalPostcondition,
        final ArrayList<SymGlobalAnnotation> theOutGlobals) {

      final boolean a = helperBuildSparkMethodAnnotation(
          stPA,
          theOptionalDeclaredGlobals,
          theOptionalDeclaredGlobalsSelection,
          theInGlobals,
          theOptionalPrecondition);

      final boolean b = helperBuildProcedureAnnotation(
          stPA,
          theOptionalDependencyRelation,
          theOptionalPostcondition,
          theOutGlobals);

      return a || b;
    }

    private boolean helperBuildSparkMethodAnnotation(final StringTemplate stPA,
        final LinkedHashSet<SymGlobalAnnotation> theOptionalDeclaredGlobals,
        final RegionSelection theOptionalDeclaredGlobalsSelection,
        final ArrayList<SymGlobalAnnotation> theInGlobals,
        final Precondition theOptionalPrecondition) {

      boolean didSomething = false;

      if ((theOptionalDeclaredGlobals != null)
          && !theOptionalDeclaredGlobals.isEmpty()) {
        /***********************************************************************
         * OPTIONAL GLOBAL DEFINITION
         **********************************************************************/
        final ArrayList<StringTemplate> specGlobals = new ArrayList<StringTemplate>();

        for (final SymGlobalAnnotation sgaSpecDec : theOptionalDeclaredGlobals) {
          final StringTemplate stAnnotationVariable = this.g
              .createAnnotationVariable(sgaSpecDec.getTheObject()
                  .getTheTranslation(), sgaSpecDec.getTheSelection());
          specGlobals.add(stAnnotationVariable);
        }
        stPA.setAttribute(
            "theOptionalGlobalDefinitions",
            this.g.createListFromTemplate(specGlobals));

        assert theOptionalDeclaredGlobalsSelection != null;
        stPA.setAttribute(
            "theOptionalGlobalDefinitionsSelection",
            this.g.getLineAnnotation(theOptionalDeclaredGlobalsSelection));

        didSomething = true;
      }

      if (!theInGlobals.isEmpty()) {
        /*********************************************************************
         * OPTIONAL IN GLOBALS
         ********************************************************************/
        assert theOptionalDeclaredGlobals != null;

        final ArrayList<String> inGlobals = new ArrayList<String>();
        for (final SymGlobalAnnotation sgaIn : theInGlobals) {
          inGlobals.add(sgaIn.getTheObject().getTheTranslation());
        }
        stPA.setAttribute(
            "theOptionalInGlobals",
            this.g.createListFromString(inGlobals));

        didSomething = true;
      }

      if (theOptionalPrecondition != null) {

        visit(theOptionalPrecondition);
        final EvalResult preCond = this.g.popResult();

        final SymDef boolType = this.g.st.getBaseType("boolean");
        assert preCond.getTheReturnType() == boolType;

        final StringTemplate stPreCond = this.g.stg
            .getInstanceOf("ContractPredicate");
        stPreCond.setAttribute(
            "theType",
            '@' + SPARKPredicateType.PRE.toString());
        stPreCond.setAttribute("theExp", preCond.getTheTemplate());

        stPreCond.setAttribute("theLoc", this.g
            .getLineAnnotation(theOptionalPrecondition
                .getTheOptionalRegionSelection()));
        preCond.setTheTemplate(stPreCond);

        stPA.setAttribute("theOptionalPrecondition", stPreCond);
        didSomething = true;
      }

      return didSomething;
    }

    private EvalResult helperChoiceHandler(final Choice o) {
      return helperChoiceHandler(o, null);
    }

    private EvalResult helperChoiceHandler(final Choice o,
        final EvalResult theOptionalExp) {
      switch (o.getDescriptor()) {
        case ExpChoice.DESCRIPTOR: {
          // e.g.
          // when 8 => ...
          // when 1 + 2 => ...
          visit(((ExpChoice) o).getTheExp());
          final EvalResult er = this.g.popResult();

          if (this.g.peekGenThreeAddress()) {
            return helperCreateSingleElementComparison(theOptionalExp, er, o);
          } else {
            return er;
          }
        }
        case RangeChoice.DESCRIPTOR: {
          // e.g.
          // 1..3 => x  -- i.e. positions 1, 2, and 3 get x
          return helperChoiceRangeChoice(
              ((RangeChoice) o).getTheRange(),
              theOptionalExp,
              o);
        }
        case SubTypeChoice.DESCRIPTOR: {
          // e.g. 
          //      Index => x
          //   or Index range 11 .. 20 => x
          //   or Index range A'Range => x -- where A is an array variable
          //   or Index range A(1)'Range => x -- where A is an array variable
          return helperChoiceSubType((SubTypeChoice) o, theOptionalExp);
        }
        default:
          throw new RuntimeException("Unexpected");
      }
    }

    EvalResult helperChoiceRangeChoice(final Range o,
        final EvalResult theOptionalExp, final Choice theOptionalChoice) {
      visit(o);

      final EvalResult highTemp = this.g.popResult(2);
      final EvalResult lowTemp = this.g.popResult(1);

      if (this.g.peekGenThreeAddress()) {
        assert theOptionalExp != null;
        return helperRangeMembershipTest(
            theOptionalExp,
            lowTemp,
            highTemp,
            theOptionalChoice);
      } else {
        assert theOptionalExp == null;
        final StringTemplate stChoiceRange = this.g.stg
            .getInstanceOf("choiceRange");
        stChoiceRange.setAttribute("low", lowTemp);
        stChoiceRange.setAttribute("high", highTemp);

        final SymDef retType = this.g.unifyTypes(
            null,
            lowTemp.getTheReturnType(),
            highTemp.getTheReturnType());

        return new EvalResult(stChoiceRange, null, retType);
      }
    }

    private EvalResult helperChoiceSubType(final SubTypeChoice o,
        final EvalResult theOptionalExp) {

      final SubTypeIndication sti = (o).getTheSubTypeIndication();
      visit(sti.getTheName());
      final EvalResult theType = this.g.popResult();
      assert theType.getTheReturnType() != null;

      if (sti.getTheOptionalConstraint() != null) {
        assert !(theType.getTheResolvedSymbol() instanceof SymEnumElementDef);

        switch (sti.getTheOptionalConstraint().getDescriptor()) {
          case RangeConstraint.DESCRIPTOR:
            // e.e. when IntType range 1 .. 3 => ...
            final Range r = ((RangeConstraint) sti.getTheOptionalConstraint())
                .getTheRange();
            // TODO: Do we care about theType when an optional range is given?
            //       for now I'm going to assume we don't

            return helperChoiceRangeChoice(r, theOptionalExp, o);
          case IndexConstraint.DESCRIPTOR:
          default:
            throw new RuntimeException("Unexpected");
        }
      } else {
        // e.g. when IntType => ...
        final EvalResult low = helperApplyAttribute(
            SparkAttribute.First,
            theType.getTheReturnType());
        final EvalResult high = helperApplyAttribute(
            SparkAttribute.Last,
            theType.getTheReturnType());

        if (this.g.peekGenThreeAddress()) {
          switch (theType.getTheResolvedSymbol().getDescriptor()) {
            case SymEnumElementDef.DESCRIPTOR:
              return helperCreateSingleElementComparison(
                  theOptionalExp,
                  theType,
                  o);
            default:
              return helperRangeMembershipTest(theOptionalExp, low, high, o);
          }
        } else {
          // I think the only way we can get here is when processing an
          // array component aggregation and I doubt that you could use
          // enum elements as valid choices
          assert !(theType.getTheResolvedSymbol() instanceof SymEnumElementDef);

          final StringTemplate stChoiceRange = this.g.stg
              .getInstanceOf("choiceRange");
          stChoiceRange.setAttribute("low", low);
          stChoiceRange.setAttribute("high", high);
          final EvalResult er = new EvalResult(stChoiceRange, null,
              theType.getTheReturnType());
          return er;
        }
      }
    }

    private StringTemplate helperCreateDependencyRelationAnnotation(
        final ContainerDependencyRelation theOptionalRelation) {
      if (theOptionalRelation == null) {
        return this.g.NONE;
      }

      final StringTemplate stDP = this.g.stg
          .getInstanceOf("DependencyRelation");
      stDP.setAttribute("theDependencyRelationRegionSelection", this.g
          .getLineAnnotation(theOptionalRelation
              .getTheDependencyRelationRegionSelection()));

      final ArrayList<StringTemplate> stTheDCList = new ArrayList<StringTemplate>();
      for (final ContainerDependencyClause cdc : theOptionalRelation
          .getTheDependencyClauses()) {
        final StringTemplate stDC = this.g.stg
            .getInstanceOf("DependencyClause");
        stDC.setAttribute("theDependencyClauseRegionSelection", this.g
            .getLineAnnotation(cdc.getTheDependencyClauseRegionSelection()));

        final ArrayList<StringTemplate> stTheDCM = new ArrayList<StringTemplate>();
        for (final SymDerivesAnnotationModel sdam : cdc
            .getTheDependencyClauseMembers()) {

          switch (sdam.getDescriptor()) {
            case SymDerivesAnnotation.DESCRIPTOR: {
              final SymDerivesAnnotation sda = (SymDerivesAnnotation) sdam;

              final StringTemplate stDerives = this.g.stg
                  .getInstanceOf("DependencyClauseMember");

              final SymSimpleAnnotation ssaOut = sda.getTheOutVar();

              final Symbol theAdaObject = ssaOut.getTheObject();
              assert theAdaObject != null;

              final StringTemplate stAnnotationVariable = this.g
                  .createAnnotationVariable(
                      theAdaObject.getTheTranslation(),
                      ssaOut.getTheSelection());
              stDerives.setAttribute("theOutVariable", stAnnotationVariable);

              if (!sda.getTheInVars().isEmpty()) {
                final ArrayList<StringTemplate> stInVars = new ArrayList<StringTemplate>();
                for (final SymSimpleAnnotation inVar : sda.getTheInVars()) {

                  final Symbol theAdaObjectIn = inVar.getTheObject();
                  assert theAdaObjectIn != null;

                  final StringTemplate stAnnotationVariableI = this.g
                      .createAnnotationVariable(
                          theAdaObjectIn.getTheTranslation(),
                          inVar.getTheSelection());
                  stInVars.add(stAnnotationVariableI);
                }
                stDerives.setAttribute(
                    "theOptionalInVariables",
                    this.g.createListFromTemplate(stInVars));
              }

              //stDC.setAttribute("theDependencyClauseMembers", stDerives);
              stTheDCM.add(stDerives);
              break;
            }
            case SymNullDerivesAnnotation.DESCRIPTOR: {
              final SymNullDerivesAnnotation snda = (SymNullDerivesAnnotation) sdam;
              final StringTemplate stNullDerives = this.g.stg
                  .getInstanceOf("NullDependencyClauseMember");

              final ArrayList<StringTemplate> stInVars = new ArrayList<StringTemplate>();
              for (final SymSimpleAnnotation inVar : snda.getTheInVars()) {
                final Symbol theAdaObjectIn = inVar.getTheObject();
                assert theAdaObjectIn != null;

                final StringTemplate stAnnotationVariableI = this.g
                    .createAnnotationVariable(
                        theAdaObjectIn.getTheTranslation(),
                        inVar.getTheSelection());
                stInVars.add(stAnnotationVariableI);
              }
              stNullDerives.setAttribute(
                  "theInVariables",
                  this.g.createListFromTemplate(stInVars));
              stTheDCM.add(stNullDerives);
              break;
            }
            default:
              throw new RuntimeException("Unexpected");
          }

        }
        stDC.setAttribute(
            "theDependencyClauseMembers",
            this.g.createListFromTemplate(stTheDCM));

        stTheDCList.add(stDC);
        //stDP.setAttribute("theDependencyClauses", stDC);
      }
      stDP.setAttribute(
          "theDependencyClauses",
          this.g.createListFromTemplate(stTheDCList));

      return stDP;
    }

    private void helperCreateProofFunction(final SymFunction symFunc,
        final Map<String, SymParameter> theParamsWithInGlobals,
        final boolean isImplicit, final RegionSelection rs) {

      final StringTemplate stFuncDecl = this.g.stg
          .getInstanceOf("procedureSpecification");

      final boolean isSpec = this.g.inSpecPart.peek().booleanValue();

      stFuncDecl.setAttribute(
          "ID",
          SymbolTable.returnPilarCompliantName(symFunc.getTheName()));

      stFuncDecl.setAttribute("type", symFunc.getTheReturnType()
          .getTheTranslation());

      /************************************************************************
       * ADD SPEC PARAMETERS
       ***********************************************************************/
      helperAddParameters(stFuncDecl, theParamsWithInGlobals.values(), isSpec);

      /************************************************************************
       * ADA FUNCTION ANNOTATION
       ***********************************************************************/
      final StringTemplate stAFA = this.g.stg
          .getInstanceOf("AdaFunctionAnnotation");
      helperBuildAdaFunctionAnnotationForProofFunc(
          stAFA,
          symFunc,
          theParamsWithInGlobals.values());

      /************************************************************************
       * SPARK PROOF FUNCTION ANNOTATION
       ***********************************************************************/
      final StringTemplate stPFA = this.g.stg
          .getInstanceOf("ProofFunctionAnnotation");
      stPFA.setAttribute("isImplicit", Boolean.valueOf(isImplicit));

      final StringTemplate stMA = helperBuildMethodAnnotation(
          stAFA,
          stPFA,
          rs,
          rs,
          rs);

      stFuncDecl.setAttribute("annotationList", stMA);

      this.g.addPackageElement(stFuncDecl);
    }

    private EvalResult helperCreateSingleElementComparison(
        final EvalResult theExp, final EvalResult theValue,
        final Choice theOptionalChoice) {
      assert this.g.peekGenThreeAddress();
      assert (theExp != null) && (theValue != null);

      final StringTemplate stBE = this.g.stg.getInstanceOf("binaryExp");
      stBE.setAttribute("binop", this.g.getBinaryOpString(BinaryOp.EQUAL));
      stBE.setAttribute("exp1", theExp.getTheTemplate());
      stBE.setAttribute("exp2", theValue.getTheTemplate());

      final SymDef boolType = this.g.st.getBaseType("Boolean");
      final SymVariable resultVar = this.g.generateFreshTempVar(
          boolType,
          SymOrigin.METHOD);

      final StringTemplate stAS = this.g.stg
          .getInstanceOf("assignmentStatement");
      stAS.setAttribute("lhs", resultVar.getTheTranslation());
      stAS.setAttribute("rhs", stBE);

      if (theOptionalChoice != null) {
        stAS.setAttribute("locLabel", this.g.generateNewAnnotatedLoc(this.g
            .getLineAnnotation(theOptionalChoice
                .getTheOptionalRegionSelection())));

        stAS.setAttribute("annotationList", this.g.stg.getInstanceOf("Choice"));
      } else {
        stAS.setAttribute("locLabel", this.g.generateNewSTLoc());
      }

      this.g.addLocationToCurrentMethod(stAS);

      return new EvalResult(this.g.wrapString(resultVar.getTheTranslation()),
          resultVar, boolType);
    }

    /**
     * 
     * @param theExp
     * @param lowVal
     * @param highVal
     * @param theOptionalRS
     * @param theOptionalActionAnnotation
     * @return
     */
    EvalResult helperRangeMembershipTest(final EvalResult theExp,
        final EvalResult lowVal, final EvalResult highVal,
        final Choice theOptionalChoice) {

      assert (theExp != null) && (lowVal != null) && (highVal != null);
      final SymDef retType = this.g.st.getBaseType("Boolean");

      final StringTemplate stLhsComp = this.g.stg.getInstanceOf("binaryExp");
      {
        stLhsComp.setAttribute("exp1", theExp.getTheTemplate());
        stLhsComp.setAttribute(
            "binop",
            this.g.getBinaryOpString(BinaryOp.GREATER_EQUAL));
        stLhsComp.setAttribute("exp2", lowVal.getTheTemplate());
      }

      final StringTemplate stRhsComp = this.g.stg.getInstanceOf("binaryExp");
      {
        stRhsComp.setAttribute("exp1", theExp.getTheTemplate());
        stRhsComp.setAttribute(
            "binop",
            this.g.getBinaryOpString(BinaryOp.LESS_EQUAL));
        stRhsComp.setAttribute("exp2", highVal.getTheTemplate());
      }

      StringTemplate ret = null;
      if (this.g.peekGenThreeAddress()) {

        ret = this.g.stg.getInstanceOf("condBinary_AND_OR");
        ret.setAttribute("op", "&&");

        final SymVariable retVar = this.g.generateFreshTempVar(
            retType,
            SymOrigin.METHOD);
        ret.setAttribute("resultVar", retVar.getTheTranslation());

        { // assign lhsComp to temp var
          final StringTemplate lhsAss = this.g.stg
              .getInstanceOf("assignmentStatement");
          final SymVariable lhsVar = this.g.generateFreshTempVar(
              retType,
              SymOrigin.METHOD);
          lhsAss.setAttribute("lhs", lhsVar.getTheTranslation());
          lhsAss.setAttribute("rhs", stLhsComp);
          lhsAss.setAttribute("locLabel", this.g.generateNewSTLoc());
          ret.setAttribute("lhsVar", lhsVar.getTheTranslation());
          ret.setAttribute("lhsStatements", lhsAss);
        }

        { // assign rhsComp to temp var
          final StringTemplate rhsAss = this.g.stg
              .getInstanceOf("assignmentStatement");
          final SymVariable rhsVar = this.g.generateFreshTempVar(
              retType,
              SymOrigin.METHOD);
          rhsAss.setAttribute("lhs", rhsVar.getTheTranslation());
          rhsAss.setAttribute("rhs", stRhsComp);
          rhsAss.setAttribute("locLabel", this.g.generateNewSTLoc());
          ret.setAttribute("rhsStatements", rhsAss);
          ret.setAttribute("rhsVar", rhsVar.getTheTranslation());
        }

        ret.setAttribute("loc2", this.g.generateLocLabel_Next());

        if (theOptionalChoice != null) {
          ret.setAttribute("locAnnot", this.g
              .getLineAnnotation(theOptionalChoice
                  .getTheOptionalRegionSelection()));

          ret.setAttribute("annotationList", this.g.stg.getInstanceOf("Choice"));
        }

        this.g.addLocationToCurrentMethod(ret);

        ret = this.g.wrapString(retVar.getTheTranslation());

      } else {
        ret = this.g.stg.getInstanceOf("binaryExp");
        ret.setAttribute("exp1", stLhsComp);
        ret.setAttribute("exp2", stRhsComp);
        ret.setAttribute("binop", "&&");
      }

      return new EvalResult(ret, retType, retType);
    }

    /**
     * helper function to fake a sequence of OR ELSE expressions
     * 
     * @param theChoices
     * @param erTheCaseExp
     * @return
     */
    private EvalResult helperSimulateOrElseSequence(
        final List<Choice> theChoices, final EvalResult erTheCaseExp) {
      assert theChoices.size() >= 2;
      final StringTemplate stCurrentMethod = this.g.currentStMethodStack.peek();
      final SymCall sc = (SymCall) this.g.getFirstSymPackageElement();
      final EvalResult er = helperSimulateOrElseSequenceHelper(
          sc,
          theChoices.get(0),
          theChoices,
          1,
          erTheCaseExp);

      // make sure we're back where we started
      assert this.g.currentStMethodStack.peek() == stCurrentMethod;

      return er;
    }

    /**
     * the helper of the helper
     * 
     * @param sc
     * @param lhs
     * @param rhsChoices
     * @param index
     * @param erTheCaseExp
     * @return
     */
    private EvalResult helperSimulateOrElseSequenceHelper(final SymCall sc,
        final Choice lhs, final List<Choice> rhsChoices, int index,
        final EvalResult erTheCaseExp) {

      final StringTemplate stLCLHS = this.g.stg
          .getInstanceOf("LocationContainer");
      this.g.currentStMethodStack.push(stLCLHS);
      final EvalResult erLhs = this.helperChoiceHandler(lhs, erTheCaseExp);
      this.g.currentStMethodStack.pop();

      final StringTemplate stLCRHS = this.g.stg
          .getInstanceOf("LocationContainer");
      this.g.currentStMethodStack.push(stLCRHS);
      EvalResult rhs = null;
      final Choice rhsChoice = rhsChoices.get(index++);
      {
        if (index == rhsChoices.size()) {
          rhs = this.helperChoiceHandler(rhsChoice, erTheCaseExp);
        } else {
          rhs = helperSimulateOrElseSequenceHelper(
              sc,
              rhsChoice,
              rhsChoices,
              index,
              erTheCaseExp);
        }
      }
      this.g.currentStMethodStack.pop();

      final StringTemplate stCBOE = this.g.stg
          .getInstanceOf("condBinary_OR_ELSE");

      final SymDef boolType = this.g.st.getBaseType("Boolean");
      final SymVariable resultVar = this.g.generateFreshTempVar(
          boolType,
          SymOrigin.METHOD);
      stCBOE.setAttribute("resultVar", resultVar.getTheTranslation());

      stCBOE.setAttribute("lhsVar", erLhs.getTheTemplate());
      stCBOE.setAttribute("lhsStatements", stLCLHS);

      stCBOE.setAttribute("rhsVar", rhs.getTheTemplate());
      stCBOE.setAttribute("rhsStatements", stLCRHS);

      stCBOE.setAttribute("loc1", this.g.generateLocLabel_Next());

      stCBOE.setAttribute(
          "lhsLoc",
          this.g.generateNewSTLoc(lhs.getTheOptionalRegionSelection()));
      stCBOE.setAttribute(
          "rhsLoc",
          this.g.generateNewSTLoc(rhsChoice.getTheOptionalRegionSelection()));

      stCBOE.setAttribute("loc2", this.g.generateLocLabel_Next());
      stCBOE.setAttribute("endLoc", this.g.generateLocLabel_Next());
      stCBOE.setAttribute("trueLoc", this.g.generateLocLabel_Next());

      this.g.addLocationToCurrentMethod(stCBOE);

      return new EvalResult(this.g.wrapString(resultVar.getTheTranslation()),
          resultVar, boolType);
    }

    @Override
    protected void visitAggregateQualifiedExp(final AggregateQualifiedExp o) {
      assert o != null;

      this.g.pushGenThreeAddress(false);
      {
        visit(o.getTheName()); // the SPARK type to apply aggregate to
        final EvalResult theObject = this.g.popResult();

        final SymDef sd = (SymDef) theObject.getTheResolvedSymbol();

        assert (sd instanceof SymArrayDef)
            || (sd instanceof SymIndexSubTypeDef)
            || (sd instanceof SymRecordDef);

        StringTemplate aqe = null;
        if (sd instanceof SymRecordDef) {
          aqe = this.g.stg.getInstanceOf("recordAggregateQualifiedExp");
        } else {
          aqe = this.g.stg.getInstanceOf("arrayAggregateQualifiedExp");
        }

        aqe.setAttribute("theType", sd.getTheTranslation());

        if (sd instanceof SymArrayDef) {
          if (o.getTheAggregate() instanceof NamedRecordAggregate) {
            this.g.convertNamedRecordAggregateToNamedArrayAggregate(o);
          } else if (o.getTheAggregate() instanceof PositionalRecordAggregate) {
            this.g
                .convertPositionalRecordAggregateToPositionalArrayAggregate(o);
          }
        }

        this.g.symbolStack.push(sd);
        visit(o.getTheAggregate());
        this.g.symbolStack.pop();

        final EvalResult attResult = this.g.popResult();
        aqe.setAttribute("theAggregate", attResult);

        assert this.g.typeCompatible(sd, attResult.getTheReturnType());

        this.g.handleExp(new EvalResult(aqe, null, sd), o);
      }
      this.g.popGenThreeAddress();
    }

    @Override
    protected void visitArrayUpdate(final ArrayUpdate o) {

      visit(o.getTheName());
      final EvalResult theArrayName = this.g.popResult();

      final SymObject so = (SymObject) theArrayName.getTheResolvedSymbol();
      final SymDef sd = so.getTheOptionalTypeSymDef();

      assert sd != null;

      ArrayList<SymDef> theDimDefs = null;

      switch (sd.getDescriptor()) {
        case SymArrayDef.DESCRIPTOR:
          final SymArrayDef sad = (SymArrayDef) sd;
          theDimDefs = sad.getTheIndexSubtypeDefs();
          break;
        case SymIndexSubTypeDef.DESCRIPTOR:
          final SymIndexSubTypeDef sistd = (SymIndexSubTypeDef) sd;
          theDimDefs = sistd.getTheSubTypeDefs();
          break;
        case SymRecordDef.DESCRIPTOR: {
          // NOTE: the parser doesn't have enough info to differentiate array
          // and record updates so they all end up as array updates.  Here
          // we'll rewrite the AST and ...
          visit(this.g.convertArrayUpdateToRecordUpdate(o));
          return;
        }
        default:
          throw new RuntimeException("Unexpected");
      }

      // NOTE: array updates are only seen in proof contexts
      assert !this.g.peekGenThreeAddress() && this.g.inProofContext;
      //this.g.pushGenThreeAddress(false);
      //this.g.inProofContext = true;

      assert o.getTheIndexListToExpressionList() != null;

      final StringTemplate stcue = this.g.stg
          .getInstanceOf("CompositeUpdateExp");
      stcue.setAttribute(
          "componentUpdateUIF",
          SparkUIF.$ARRAY_UPDATE.toString());
      stcue.setAttribute("thePrefix", theArrayName.getTheTemplate());

      for (final Pair<ArrayList<Exp>, Exp> p : o
          .getTheIndexListToExpressionList()) {

        assert p.getE0().size() == theDimDefs.size();

        final List<StringTemplate> indices = new ArrayList<StringTemplate>();
        for (final Exp indexExp : p.getE0()) {
          visit(indexExp);
          indices.add(this.g.popResult().getTheTemplate());
        }

        final StringTemplate stcuee = this.g.stg
            .getInstanceOf("CompositeUpdateExpEntry");
        if (indices.size() > 1) {
          throw new RuntimeException("Multidim array udpates not handled yet");
        } else {
          stcuee.setAttribute("choice", indices.get(0));
        }

        visit(p.getE1());
        final EvalResult erExp = this.g.popResult();

        stcuee.setAttribute("value", erExp.getTheTemplate());
        stcue.setAttribute("entry", stcuee);
      }

      //this.g.inProofContext = false;
      //this.g.popGenThreeAddress();

      final SymDef boolType = this.g.st.getBaseType("Boolean");
      this.g.pushResult(new EvalResult(stcue, null, boolType));
    }

    @Override
    protected void visitAssertStatement(final AssertStatement o) {
      this.g.addLocationToCurrentMethod(helperAssertCheckStatement(
          o,
          o.getThePredicate()));
    }

    @Override
    protected void visitAssignmentStatement(final AssignmentStatement o) {
      final String startLabel = this.g.getNextLocLabel();

      visit(o.getTheName());
      final EvalResult lhs = this.g.popResult();

      visit(o.getTheExp());
      final EvalResult assExp = this.g.popResult();

      assert this.g.typeCompatible(
          lhs.getTheReturnType(),
          assExp.getTheReturnType());

      final String endLabel = this.g.getNextLocLabel();
      final StringTemplate stStmtAnnot2 = this.g.createStmtAnnot(
          o,
          this.g.newMark(
              startLabel,
              endLabel,
              this.g.getLineAnnotation(o.getTheOptionalRegionSelection())));

      final StringTemplate stAssign = this.g.constructAssignment(
          lhs.toString(),
          assExp.toString(),
          null,
          stStmtAnnot2);

      this.g.pushStmtEndLocLabel(endLabel);

      this.g.addLocationToCurrentMethod(stAssign);
    }

    @Override
    protected void visitAtClause(final AtClause o) {
      throw new UnsupportedOperationException("At Clause not handled yet");
    }

    @Override
    protected void visitAttributeDefinitionClause(
        final AttributeDefinitionClause o) {
      StringTemplate stADC = null;
      if (o.getTheExp() != null) {
        stADC = this.g.stg.getInstanceOf("AttributeRepresentationClauseUseExp");
        visit(o.getTheExp());
      } else {
        stADC = this.g.stg
            .getInstanceOf("AttributeRepresentationClauseUseName");
        visit(o.getTheName());
      }

      final EvalResult er2 = this.g.popResult();
      stADC.setAttribute("arg", er2);

      final AttributeReference ar = (AttributeReference) o.getTheName();
      visit(ar.getTheName());
      final EvalResult er = this.g.popResult();
      stADC.setAttribute("theName", er);

      final IDAttributeDesignator iad = (IDAttributeDesignator) ar
          .getTheAttributeDesignator();
      assert iad.getTheOptionalExps() == null;
      stADC.setAttribute("theAttribute", iad.getTheIDString());

      this.g.pushResult(stADC, null, null);
    }

    @Override
    protected void visitAttributeReference(final AttributeReference o) {
      visit(o.getTheName());
      final EvalResult prefix = this.g.popResult();

      // FIXME: the type of the expression is actually what we get by resolving
      // the attribute (e.g. I'Last -> universal_int if I is integer)
      SymDef theSymDef = prefix.getTheReturnType();
      Symbol theSym = theSymDef;

      SparkAttribute sa = null;
      String[] optExp = null;
      switch (o.getTheAttributeDesignator().getDescriptor()) {
        case DeltaAttributeDesignator.DESCRIPTOR:
          sa = SparkAttribute.Delta;
          break;
        case DigitsAttributeDesignator.DESCRIPTOR:
          sa = SparkAttribute.Digits;
          break;
        case IDAttributeDesignator.DESCRIPTOR:
          final IDAttributeDesignator iad = (IDAttributeDesignator) o
              .getTheAttributeDesignator();

          sa = SparkUtil.getSparkAttribute(iad.getTheIDString());
          assert sa != null;

          if (theSymDef instanceof SymArrayDef) {
            final SymArrayDef sad = (SymArrayDef) theSymDef;
            if (sad.getIsConstrained()) {
              if (iad.getTheOptionalExps() == null) {
                // e.g. A'Last where A is a single dim array 

                switch (sa) {
                  case First:
                  case Last: {
                    // Sec 3.6.2 of ARM 95 states return type of First and Last
                    // attributes is the corresponding index type
                    theSymDef = sad.getTheIndexSubtypeDefs().get(0);
                    theSym = theSymDef;
                    break;
                  }
                  default:
                    // FIXME: what about A'Range and A'Length?? 
                }
              } else {
                /* FIXME: here we have something like "... A'Last(2) ..." where
                 * A is a multidim array.  Section 3.6.2 of ARM 95 states
                 * "The argument N used in the attribute_designators for the N-th 
                 * dimension of an array shall be a static expression of some 
                 * integer type. The value of N shall be positive (nonzero) and no
                 * greater than the dimensionality of the array." so we should be
                 * able to statically determine which dimension is being referenced
                 * and return the corresponding type
                 *  
                 * @see <a href="http://www.adaic.org/resources/add_content/standards/05rm/html/RM-3-6-2.html">A.3.6.2</a>
                 */
              }
            } else {
              /* e.g. type Vector is array(Integer range<>) of Integer
               *      A : Vector;
               *      ...
               *      Z := A'First;
               *      
               * Applying an attribute against an unconstrained array A.  In 
               * order to get a discreet range we'll have to wait until runtime 
               * at which time an instance A should hold an value which is a 
               * constrained subtype of A.
               */
              theSym = prefix.getTheResolvedSymbol();
            }
          }

          if (iad.getTheOptionalExps() != null) {
            optExp = new String[iad.getTheOptionalExps().size()];
            int i = 0;
            for (final Exp e : iad.getTheOptionalExps()) {
              visit(e);
              optExp[i++] = this.g.popResult().toString();
            }
          }
          break;
        default:
          throw new RuntimeException("Unexpected");
      }
      this.g.pushResult(this
          .helperApplyAttribute(sa, theSym, theSymDef, optExp));
    }

    @Override
    protected void visitBinaryExp(final BinaryExp o) {
      SymDef theResultType = null;

      StringTemplate stBinaryExp = null;
      switch (o.getTheBinaryOp()) {
        case EQUAL:
        case GREATER_EQUAL:
        case GREATER:
        case LESS_EQUAL:
        case LESS:
        case NOT_EQUAL:
        case IMPLIES:
        case IS_EQUIVALENT_TO: {
          theResultType = this.g.st.getBaseType("boolean");
          stBinaryExp = this.g.stg.getInstanceOf("binaryExp");
          break;
        }

        case OR:
        case AND: {
          if (this.g.peekGenThreeAddress()) {
            visitBinaryExpConditional(o);
            return;
          } else {
            theResultType = this.g.st.getBaseType("boolean");
            stBinaryExp = this.g.stg.getInstanceOf("funBinaryExp");
          }
          break;
        }

        case AND_THEN:
        case OR_ELSE: {
          if (this.g.peekGenThreeAddress()) {
            visitBinaryExpConditional(o);
            return;
          } else {
            theResultType = this.g.st.getBaseType("boolean");
            stBinaryExp = this.g.stg.getInstanceOf("binaryExp");
          }
          break;
        }

        case ADD:
        case DIV:
        case MUL:
        case MOD:
        case SUB: {
          stBinaryExp = this.g.stg.getInstanceOf("binaryExp");
          break;
        }

        case XOR:
        case POW:
        case REM:
        case AMP: // string concatenation op Barnes p105
          stBinaryExp = this.g.stg.getInstanceOf("funBinaryExp");
          break;

        default:
          throw new UnsupportedOperationException("BinaryOp is not supported "
              + o.getTheBinaryOp());
      }

      EvalResult theLhsAssExp;
      {
        visit(o.getTheLeftExp());
        theLhsAssExp = this.g.popResult();
        stBinaryExp.setAttribute("exp1", theLhsAssExp);
      }
      EvalResult theRhsExp;
      {
        visit(o.getTheRightExp());
        theRhsExp = this.g.popResult();
        stBinaryExp.setAttribute("exp2", theRhsExp);
      }
      assert theLhsAssExp.getTheReturnType() != null;
      assert theRhsExp.getTheReturnType() != null;

      assert this.g.typeCompatible(
          theLhsAssExp.getTheReturnType(),
          theRhsExp.getTheReturnType());

      String binop;
      if ((o.getTheBinaryOp() == BinaryOp.DIV)
          && this.g.isIntegralType(theLhsAssExp.getTheReturnType())
          && this.g.isIntegralType(theRhsExp.getTheReturnType())) {
        // if the op is '/' and both sides are integers then use the integer 
        // division op.  The semantics are divide and truncate toward zero
        // (throw away any fractional part)
        binop = "div";
      } else {
        binop = this.g.getBinaryOpString(o.getTheBinaryOp());
      }
      stBinaryExp.setAttribute("binop", binop);

      if (theResultType == null) {
        // FIXME: would be much better to know what the expected type is!!!!
        theResultType = this.g.unifyTypes(
            null,
            theLhsAssExp.getTheReturnType(),
            theRhsExp.getTheReturnType());
      }

      assert theResultType != null;

      this.g.handleExp(new EvalResult(stBinaryExp, null, theResultType), o);
    }

    //@ requires this.g.genThreeAddress.peek().booleanValue();
    private void visitBinaryExpConditional(final BinaryExp o) {

      assert this.g.peekGenThreeAddress();

      final String reservedFirstLabel = //
      ((o.getTheBinaryOp() == BinaryOp.AND_THEN) || (o.getTheBinaryOp() == BinaryOp.OR_ELSE)) // 
      ? this.g.generateLocLabel_Next()
          : null;

      final StringTemplate currentMethod = this.g.popCurrentMethodStack();

      final StringTemplate stLHSLocationContainer = this.g.stg
          .getInstanceOf("LocationContainer");
      this.g.pushCurrentMethodStack(stLHSLocationContainer);

      EvalResult theLhsAssExp;
      {
        visit(o.getTheLeftExp());
        theLhsAssExp = this.g.popResult();
      }
      this.g.popCurrentMethodStack();

      final StringTemplate stRHSLocationContainer = this.g.stg
          .getInstanceOf("LocationContainer");
      this.g.pushCurrentMethodStack(stRHSLocationContainer);

      EvalResult theRhsAssExp;
      {
        visit(o.getTheRightExp());
        theRhsAssExp = this.g.popResult();
      }
      this.g.popCurrentMethodStack();

      // now restore the method template
      this.g.pushCurrentMethodStack(currentMethod);

      assert theLhsAssExp.getTheReturnType() != null;
      assert theRhsAssExp.getTheReturnType() != null;

      assert this.g.typeCompatible(
          theLhsAssExp.getTheReturnType(),
          theRhsAssExp.getTheReturnType());

      final SymDef boolType = this.g.st.getBaseType("Boolean");
      final SymVariable resultVar = this.g.generateFreshTempVar(
          boolType,
          SymOrigin.METHOD);

      StringTemplate stBinaryExp = null;
      switch (o.getTheBinaryOp()) {
        case AND:
        case OR: {
          stBinaryExp = this.g.stg.getInstanceOf("condBinary_AND_OR");
          stBinaryExp.setAttribute("resultVar", resultVar.getTheTranslation());
          stBinaryExp.setAttribute("lhsStatements", stLHSLocationContainer);
          stBinaryExp.setAttribute("rhsStatements", stRHSLocationContainer);
          stBinaryExp.setAttribute("loc2", this.g.generateLocLabel_Next());
          stBinaryExp.setAttribute("lhsVar", theLhsAssExp.getTheTemplate());
          stBinaryExp.setAttribute("rhsVar", theRhsAssExp.getTheTemplate());
          final String op = o.getTheBinaryOp() == BinaryOp.AND ? "&&" : "||";
          stBinaryExp.setAttribute("op", op);
          break;
        }
        case AND_THEN:
        case OR_ELSE: {
          if (o.getTheBinaryOp() == BinaryOp.AND_THEN) {
            stBinaryExp = this.g.stg.getInstanceOf("condBinary_AND_THEN");
          } else {
            stBinaryExp = this.g.stg.getInstanceOf("condBinary_OR_ELSE");
            stBinaryExp.setAttribute("trueLoc", this.g.generateLocLabel_Next());
          }
          stBinaryExp.setAttribute("loc1", reservedFirstLabel);
          stBinaryExp.setAttribute("resultVar", resultVar.getTheTranslation());

          //stBinaryExp.setAttribute("lhsLoc", this.g.generateNewAnnotatedLoc(o
          //    .getTheLeftExp().getTheOptionalRegionSelection()));
          stBinaryExp.setAttribute("lhsLoc", this.g.generateNewSTLoc());
          stBinaryExp.setAttribute("lhsStatements", stLHSLocationContainer);
          stBinaryExp.setAttribute("lhsVar", theLhsAssExp.getTheTemplate());

          //stBinaryExp.setAttribute("rhsLoc", this.g.generateNewAnnotatedLoc(o
          //    .getTheRightExp().getTheOptionalRegionSelection()));
          stBinaryExp.setAttribute("rhsLoc", this.g.generateNewSTLoc());
          stBinaryExp.setAttribute("rhsStatements", stRHSLocationContainer);
          stBinaryExp.setAttribute("rhsVar", theRhsAssExp.getTheTemplate());

          stBinaryExp.setAttribute("loc2", this.g.generateLocLabel_Next());
          stBinaryExp.setAttribute("endLoc", this.g.generateLocLabel_Next());
          break;
        }
        default:
          throw new RuntimeException("Unexpected");
      }

      this.g.addLocationToCurrentMethod(stBinaryExp);

      this.g.handleExp(
          new EvalResult(this.g.wrapString(resultVar.getTheTranslation()),
              null, boolType),
          o);
    }

    @Override
    protected void visitCaseStatement(final CaseStatement o) {
      assert this.g.peekGenThreeAddress();

      final String startLocLabel = this.g.getNextLocLabel();
      StringTemplate optWhenOthers = null;
      final ArrayList<Pair<StringTemplate, StringTemplate>> caseStatementAlternatives = new ArrayList<Pair<StringTemplate, StringTemplate>>();
      final List<StringTemplate> fillInMyEnd = new ArrayList<StringTemplate>();

      visit(o.getTheExp());
      final EvalResult erTheCaseExp = this.g.popResult();

      String elseLoc = this.g.generateLocLabel_Next();

      // spark grammar requires there be at least one case
      assert !o.getTheCaseStatementAlternatives().isEmpty();

      for (final CaseStatementAlternative csa : o
          .getTheCaseStatementAlternatives()) {

        final List<Choice> theChoices = csa.getTheChoices();

        final StringTemplate condPart;
        final String startLoc = this.g.getNextLocLabel();
        EvalResult theChoiceResult = null;
        if (theChoices.size() == 1) {
          // only one choice
          theChoiceResult = this.helperChoiceHandler(
              theChoices.get(0),
              erTheCaseExp);
        } else {
          // multiple choices so OR ELSE these together
          theChoiceResult = helperSimulateOrElseSequence(
              theChoices,
              erTheCaseExp);
        }

        { // add the choice check
          condPart = this.g.newMark(
              startLoc,
              this.g.getNextLocLabel(),
              csa.getTheChoiceListSelection());

          final StringTemplate stIS = this.g.stg.getInstanceOf("ifStatement");
          stIS.setAttribute("locLabel", this.g.generateNewSTLoc());
          stIS.setAttribute("ifCond", theChoiceResult.getTheTemplate());
          stIS.setAttribute("elseBranchLoc", elseLoc);

          this.g.addLocationToCurrentMethod(stIS);
        }

        // add the body of the when case
        final String startBodyLoc = this.g.getNextLocLabel();
        visit(csa.getTheStatementList());
        caseStatementAlternatives.add(new Pair<StringTemplate, StringTemplate>(
            condPart, this.g.newMark(
                startBodyLoc,
                this.g.popStmtEndLocLabel(),
                (RegionSelection) null)));

        // jump around the other cases
        final StringTemplate stLG = this.g.stg.getInstanceOf("locGoto");
        stLG.setAttribute("locLabel", this.g.generateNewSTLoc());
        fillInMyEnd.add(stLG);
        this.g.addLocationToCurrentMethod(stLG);

        // insert the else location
        final StringTemplate stLLelse = this.g.stg.getInstanceOf("locLabeled");
        stLLelse.setAttribute("label", elseLoc);
        this.g.addLocationToCurrentMethod(stLLelse);

        elseLoc = this.g.generateLocLabel_Next();
      }

      if (o.getTheOptionalOthers() != null) {
        final String startLoc = this.g.getNextLocLabel();
        visit(o.getTheOptionalOthers());
        optWhenOthers = this.g.newMark(
            startLoc,
            this.g.getCurrLocLabel(),
            (RegionSelection) null);
      }

      final String endLoc = this.g.generateLocLabel_Next();

      for (final StringTemplate st : fillInMyEnd) {
        st.setAttribute("gotoLoc", endLoc);
      }

      final StringTemplate stl2 = this.g.createStmtAnnot(
          o,
          caseStatementAlternatives,
          optWhenOthers,
          this.g.newMark(
              startLocLabel,
              endLoc,
              o.getTheOptionalRegionSelection()));

      // now insert the end location
      final StringTemplate stEndLoc = this.g.generateNewLabeledAnnotatedLoc(
          endLoc,
          stl2);

      this.g.pushStmtEndLocLabel(endLoc);
      this.g.addLocationToCurrentMethod(stEndLoc);
    }

    @Override
    protected void visitCharacterLiteral(final CharacterLiteral o) {
      final StringTemplate st = this.g.stg.getInstanceOf("same");
      st.setAttribute("ID", "'" + o.getTheCharacter() + "'");
      final SymDef charType = this.g.st.getBaseType("char");
      this.g.pushResult(st, null, charType);
    }

    @Override
    protected void visitCheckStatement(final CheckStatement o) {
      this.g.addLocationToCurrentMethod(helperAssertCheckStatement(
          o,
          o.getThePredicate()));
    }

    @Override
    protected void visitChoice(final Choice o) {
      this.g.pushResult(helperChoiceHandler(o));
    }

    @Override
    protected void visitCompilation(final Compilation o) {
      if (o.getTheOptionalCompilationUnits() != null) {
        for (final CompilationUnit cu : o.getTheOptionalCompilationUnits()) {
          visit(cu);
        }
      }
    }

    @Override
    protected void visitContextClause(final ContextClause o) {
      // we're going to use fully qualified names so we don't need the 'with'
      // or 'use type' clauses
    }

    @Override
    protected void visitDeclarativePart(final DeclarativePart o) {

      if (o.getTheOptionalDeclarativePartMembers() != null) {
        for (final DeclarativePartMember m : o
            .getTheOptionalDeclarativePartMembers()) {
          if ((m instanceof Body) || (m instanceof ProofFunctionDeclaration)) {
            visit(m);
          } else if (m instanceof EmbeddedPackageDeclaration) {
            // Make sure that embedded package declarations are visited 
            // so that _$$.plr files are generated
            final boolean oldVisitingEmbedded = this.g.visitingEmbeddedPackage;
            this.g.visitingEmbeddedPackage = true;
            final EmbeddedPackageDeclaration epd = (EmbeddedPackageDeclaration) m;
            visitEmbeddedPackageDeclaration(epd);
            this.g.visitingEmbeddedPackage = oldVisitingEmbedded;
          }
        }
      }
    }

    @Override
    protected void visitDefaultLoopStatement(final DefaultLoopStatement o) {

      final String startLoc = this.g.getNextLocLabel();
      this.g.addLocationToCurrentMethod(this.g.generateNewSTLoc());

      final String endLoc = this.g.generateLocLabel_Exit();

      if (o.getTheOptionalLoopInvariant() != null) {
        this.g.emitErrorMessage(o, "currently ignoring loop invariants!!!!");
      }

      final String startBody = this.g.getNextLocLabel();
      visit(o.getTheStatementList());
      final StringTemplate body = this.g.newMark(
          startBody,
          this.g.popStmtEndLocLabel(),
          (RegionSelection) null);

      final StringTemplate gotoLoc = this.g.stg.getInstanceOf("locGoto");
      gotoLoc.setAttribute("locLabel", this.g.generateNewSTLoc());
      gotoLoc.setAttribute("gotoLoc", startLoc);
      this.g.addLocationToCurrentMethod(gotoLoc);

      final RegionSelection rsMinusOpLoopId = SelectionUtil.newRegionSelection(
          o.getTheLoopKeywordRegionSelection().getTheStartCaret(),
          o.getTheOptionalRegionSelection().getTheEndCaret(),
          o.getTheOptionalRegionSelection().getTheOptionalSource());

      final StringTemplate stl2 = this.g.createStmtAnnot(
          o,
          body,
          this.g.newMark(startLoc, endLoc, rsMinusOpLoopId));

      final StringTemplate stEndLoc = this.g.stg
          .getInstanceOf("locLabeledWithOptAnnot");

      stEndLoc.setAttribute("theOptLocAnnotation", stl2);

      // TODO: is the exit location idea needed anymore
      final String poppedExitLoc = this.g.popExitLoc();
      assert poppedExitLoc.equals(endLoc);

      stEndLoc.setAttribute("theLocLabel", endLoc);

      this.g.pushStmtEndLocLabel(endLoc);
      this.g.addLocationToCurrentMethod(stEndLoc);
    }

    @Override
    protected void visitEnumerationRepresentationClause(
        final EnumerationRepresentationClause o) {
      throw new UnsupportedOperationException(
          "Enumeration Representation Clause not handled yet");
    }

    @Override
    protected void visitExitStatement(final ExitStatement o) {

      final String startLabel = this.g.getNextLocLabel();

      if (o.getTheOptionalName() != null) {
        /*
        visit(o.getTheOptionalName());
        final EvalResult stLoopName = this.g.popResult();

        final StringTemplate stLL = this.g.stg.getInstanceOf("labeledLoopName");
        stLL.setAttribute("locLabel", this.g.generateNewLoc());
        stLL.setAttribute("name", stLoopName);

        final RegionSelection selection = SelectionCreation.create(o,
            this.g.filepath);
        final String annotation = this.g.getLineAnnotation(selection, false);
        stLL.setAttribute("loc", annotation);

        this.g.addLocationToCurrentMethod(stLL);
        //assert false;
         */
        this.g.emitWarningMessage(
            o,
            "In visitExitStatement: currently ignoring loop labels");
      }

      final StringTemplate mark = this.g.newMark(
          startLabel,
          this.g.getNextLocLabel(),
          this.g.getLineAnnotation(o.getTheOptionalRegionSelection()));
      final StringTemplate stl2 = this.g.createStmtAnnot(o, mark);

      StringTemplate toEmit;

      if (o.getTheOptionalExp() != null) {
        // the when part
        visit(o.getTheOptionalExp());
        final EvalResult exp = this.g.popResult();

        mark.removeAttribute("endLabel");
        mark.setAttribute("endLabel", this.g.getNextLocLabel());

        toEmit = this.g.stg.getInstanceOf("locCondJump");

        toEmit.setAttribute("locLabel", this.g.generateNewAnnotatedLoc(stl2));
        toEmit.setAttribute("cond", exp);
        toEmit.setAttribute("jumpLoc", this.g.getCurrentExitLoc());

      } else {
        toEmit = this.g.stg.getInstanceOf("locGoto");

        toEmit.setAttribute("locLabel", this.g.generateNewAnnotatedLoc(stl2));
        toEmit.setAttribute("gotoLoc", this.g.getCurrentExitLoc());
      }
      final String endLoc = this.g.getCurrLocLabel();

      this.g.pushStmtEndLocLabel(endLoc);
      this.g.addLocationToCurrentMethod(toEmit);
    }

    @Override
    protected void visitExpRange(final ExpRange o) {
      visit(o.getTheHighRangeExp());
      final EvalResult erHigh = this.g.popResult();

      visit(o.getTheLowRangeExp());
      final EvalResult erLow = this.g.popResult();

      assert (erLow != null) && (erHigh != null);
      assert this.g.typeCompatible(
          erLow.getTheReturnType(),
          erHigh.getTheReturnType());

      this.g.pushResult(erLow, 0);
      this.g.pushResult(erHigh, 1);
    }

    /**
     * Note: The loop bounds are only evaluated once. Refer to Examiner_GenVCs
     * Issue:8.11, <blockquote> ... semantic rules of Ada that require the loop
     * exit bound expressions to be evaluated once only, and remain fixed, even
     * if variables appearing in them are modified in the loop body
     * </blockquote>
     * <p>
     * Also, according to ARM'95 5.5 <blockquote>If the
     * discrete_subtype_definition defines a subtype with a null range, the
     * execution of the loop_statement is complete</blockquote> and <blockquote>
     * A loop parameter is a constant; it cannot be updated within the
     * sequence_of_statements of the loop (see 3.3).</blockquote>
     * <p>
     * As of 3/17/10 we still need to add support for the '%' decorator
     */
    @Override
    protected void visitForLoopStatement(final ForLoopStatement o) {

      // kind of yucky but the whole idea of the last added location is 
      // yucky to begin with
      this.g.stLastAddedLocation = null;

      StringTemplate initPart;
      StringTemplate body;
      String exitLoc;

      final StringTemplate stLocContainer = this.g.stg
          .getInstanceOf("LocationContainer");
      this.g.pushCurrentMethodStack(stLocContainer);
      {
        final String startLoopLoc = this.g.generateLocLabel_Next();
        exitLoc = this.g.generateLocLabel_Exit();

        String pilarIterVarID = SymbolTable.returnPilarCompliantName(o
            .getTheIDString());

        final String startInitPart = this.g.getNextLocLabel();
        visit(o.getTheName());
        final EvalResult theType = this.g.popResult();

        final SymCall sc = (SymCall) this.g.getFirstSymPackageElement();
        final SymVariable svIter = this.g.addTemporaryLocal(
            sc,
            pilarIterVarID,
            theType.getTheReturnType());

        pilarIterVarID = svIter.getTheTranslation();

        EvalResult highTemp = null;
        EvalResult lowTemp = null;

        if (o.getTheOptionalRange() != null) {
          // e.g. for I in Index range 1..(5-1) => 3
          //      or
          //      for I in Index range 11..20 => 20

          // TODO: do we care about the declared iter type when an optional 
          //       range is applied.  For now I'm assuming we don't

          visit(o.getTheOptionalRange());
          highTemp = this.g.popResult(2);
          lowTemp = this.g.popResult(1);

          this.g.pushGenThreeAddress(false);
          {
            visit(o.getTheOptionalRange());
            this.g.popResult(2);
            this.g.popResult(1);
          }
          this.g.popGenThreeAddress();

        } else {
          // e.g. for I in subIndex => 3
          //      which is equivalent to 
          //      for I in subIndex'First..SubIndex'Last => 3
          lowTemp = this.g.helperAssignToTempVar(helperApplyAttribute(
              SparkAttribute.First,
              theType.getTheReturnType()));

          highTemp = this.g.helperAssignToTempVar(helperApplyAttribute(
              SparkAttribute.Last,
              theType.getTheReturnType()));
        }
        assert (highTemp != null) && (lowTemp != null);

        final boolean isRevIter = o.getTheReverseFlag();

        { // add the null range check
          final StringTemplate stBE = this.g.stg.getInstanceOf("binaryExp");

          stBE.setAttribute("binop", this.g.getBinaryOpString(BinaryOp.GREATER));

          stBE.setAttribute("exp1", lowTemp);
          stBE.setAttribute("exp2", highTemp);

          final StringTemplate stCond = this.g.stg.getInstanceOf("locCondJump");
          stCond.setAttribute("locLabel", this.g.generateNewSTLoc());
          stCond.setAttribute("jumpLoc", exitLoc);
          stCond.setAttribute("cond", stBE);
          this.g.addLocationToCurrentMethod(stCond);
        }

        { // assign initial value to iter var
          final StringTemplate ass = this.g.stg
              .getInstanceOf("assignmentStatement");

          ass.setAttribute("locLabel", this.g.generateNewSTLoc());
          ass.setAttribute("lhs", pilarIterVarID);
          ass.setAttribute("rhs", isRevIter ? highTemp : lowTemp);

          this.g.addLocationToCurrentMethod(ass);
        }

        initPart = this.g.newMark(
            startInitPart,
            this.g.getCurrLocLabel(),
            SelectionUtil.intersect(
                o.getTheIterationSchemeRegionSelection(),
                o.getTheLoopKeywordRegionSelection()));

        { // add start location to jump back to during iter's
          final StringTemplate stStartLoc = this.g.stg
              .getInstanceOf("locLabeled");
          stStartLoc.setAttribute("label", startLoopLoc);
          this.g.addLocationToCurrentMethod(stStartLoc);
        }

        final String startBody = this.g.getNextLocLabel();
        visit(o.getTheStatementList()); // visit body of for loop
        body = this.g.newMark(
            startBody,
            this.g.popStmtEndLocLabel(),
            (RegionSelection) null);

        { // exit condition
          final StringTemplate stBE = this.g.stg.getInstanceOf("binaryExp");
          stBE.setAttribute("binop", this.g.getBinaryOpString(BinaryOp.EQUAL));
          stBE.setAttribute("exp1", pilarIterVarID);

          // NOTE: in ADA the loop bounds are fixed by the initial values of the
          //       low and high ranges assigned to the iter var.  Refer to
          //       the javadoc for this method for more info

          stBE.setAttribute("exp2", isRevIter ? lowTemp : highTemp);

          final EvalResult erBool = this.g.helperAssignToTempVar(
              this.g.st.getBaseType("boolean"),
              stBE.toString());

          final StringTemplate stLocCondJump = this.g.stg
              .getInstanceOf("locCondJump");
          stLocCondJump.setAttribute("locLabel", this.g.generateNewSTLoc());
          stLocCondJump.setAttribute("jumpLoc", exitLoc);
          stLocCondJump.setAttribute("cond", erBool.getTheTemplate());

          this.g.addLocationToCurrentMethod(stLocCondJump);
        }

        {
          /* update iter var.  The translation will look like (if not rev)
           * 
           *   # tempX := iterVar + 1;
           *   # iterVar := tempX;
           *   
           * we could just directly assign the binexp to iterVar but that would 
           * be the only place where the translator emits 3add code in which
           * the rhs of an assignment to an actual variable on the lhs is not a 
           * temp var 
           */

          // Create the binexp and assign it to a temp var.  
          final EvalResult erRHS = this.g.helperAssignToTempVar(
              theType.getTheReturnType(),
              pilarIterVarID + (isRevIter ? " - 1" : " + 1"));

          // assign the temp var to the iter var
          this.g.addLocationToCurrentMethod(this.g.constructAssignment(
              pilarIterVarID,
              erRHS.getTheTemplate().toString(),
              null));
        }

        { // jump back to start location
          final StringTemplate stGotoLoc = this.g.stg.getInstanceOf("locGoto");
          stGotoLoc.setAttribute("locLabel", this.g.generateNewSTLoc());
          stGotoLoc.setAttribute("gotoLoc", startLoopLoc);
          this.g.addLocationToCurrentMethod(stGotoLoc);
        }

        { // add end location

          final StringTemplate stl2 = this.g.createStmtAnnot(
              o,
              pilarIterVarID,
              initPart,
              body,
              this.g.newMark(
                  startInitPart,
                  exitLoc,
                  o.getTheOptionalRegionSelection()));

          final StringTemplate stEndLoc = this.g
              .generateNewLabeledAnnotatedLoc(exitLoc, stl2);

          this.g.addLocationToCurrentMethod(stEndLoc);
        }

        // TODO: is the exit location idea needed anymore??
        final String poppedExitLoc = this.g.popExitLoc();
        assert poppedExitLoc.equals(exitLoc);
      }

      final StringTemplate stPopped = this.g.currentStMethodStack.pop();
      assert stPopped == stLocContainer;

      this.g.pushStmtEndLocLabel(exitLoc);
      this.g.addLocationToCurrentMethod(stPopped);
      //this.g.addLocationToCurrentMethod(this.g.generateNewSTLoc());
    }

    @Override
    protected void visitFunctionCall(final FunctionCall o) {
      // NOTE: the parser doesn't have enough information to realize when an
      //       expression is a function call (it always treats them as indexed
      //       components or as selected components if the SymCall does not 
      //       have any parameters).  So FunctionCall nodes can only come from
      //       our predefined libraries
      final String uifName = ((IDName) o.getTheName()).getTheIDString();

      final StringTemplate stIndexedComponent = this.g.getCallTransTemplate();

      stIndexedComponent.setAttribute("annotation", this.g.NONE);

      stIndexedComponent.setAttribute("componentName", uifName);

      this.g.pushResult(
          stIndexedComponent,
          PilarTranslatorContext.EXPECTED_NULL,
          PilarTranslatorContext.EXPECTED_NULL);
    }

    @Override
    protected void visitFunctionSubProgramBody(final FunctionSubProgramBody o) {
      //get the function name from the function specification
      final String funcName = o.getTheFunctionSpecification().getTheIDString();

      final SymPackageElement parent = this.g.getFirstSymPackageElement();

      final SymAdaFunction symFunc = (SymAdaFunction) this.g.resolveSymbol(
          parent,
          funcName,
          o);
      if (symFunc == null) {
        this.g.emitErrorMessage(o, "Could not resolve procedure: " + funcName);
      }
      //push the function onto the symbol stack
      this.g.symbolStack.push(symFunc);

      this.g.resetMethodFactory();

      //IMPORTANT: Must visit subprogram implementation to populate globals list
      //           This is used to generate in, out, and derives when they don't exist
      visit(o.getTheSubProgramImplementation());
      final EvalResult result = this.g.popResult();
      final StringTemplate stFuncImpl = result.getTheTemplate();

      stFuncImpl.setAttribute(
          "ID",
          SymbolTable.returnPilarCompliantName(symFunc.getTheName()));

      stFuncImpl.setAttribute("type", symFunc.getTheReturnType()
          .getTheTranslation());

      /************************************************************************
       * ADD PARAMETERS
       ***********************************************************************/
      helperAddParameters(
          stFuncImpl,
          symFunc.getTheParameters().values(),
          false);

      /************************************************************************
       * ADA FUNCTION ANNOTATION
       ***********************************************************************/
      final StringTemplate stAFA = this.g.stg
          .getInstanceOf("AdaFunctionAnnotation");

      helperBuildAdaFunctionAnnotation(stAFA, symFunc);

      /************************************************************************
       * OPTIONAL SPARK FUNCTION ANNOTATION
       ***********************************************************************/
      StringTemplate stFA = null;
      {
        final StringTemplate stFAtemp = this.g.stg
            .getInstanceOf("FunctionAnnotation");

        Precondition precond = null;
        ReturnAnnotation retAnnot = null;
        if (o.getTheOptionalFunctionAnnotation() != null) {
          precond = o.getTheOptionalFunctionAnnotation()
              .getTheOptionalPrecondition();
          retAnnot = o.getTheOptionalFunctionAnnotation()
              .getTheOptionalReturnAnnotation();
        }

        final boolean didSomething = helperBuildFunctionAnnotation(
            stFAtemp,
            symFunc.getTheOptionalDeclaredBodyGlobals(),
            symFunc.getTheOptionalDeclaredBodyGlobalsSelection(),
            symFunc.getTheInGlobalsBody(),
            precond,
            retAnnot);

        if (didSomething) {
          stFA = stFAtemp;
        }
      }

      final StringTemplate stMA = helperBuildMethodAnnotation(
          stAFA,
          stFA,
          o.getTheOptionalRegionSelection(),
          o.getTheFunctionSpecification().getTheMethodNameSelection(),
          o.getTheFunctionSpecification().getTheOptionalRegionSelection());

      stFuncImpl.setAttribute("annotationList", stMA);

      //remove the function from the symbol stack hierarchy
      this.g.symbolStack.pop();

      this.g.addPackageElement(stFuncImpl);

      helperCreateProofFunction(
          symFunc,
          symFunc.getTheOptionalImplicitBodyProofParameters(),
          true,
          o.getTheOptionalRegionSelection());
    }

    @Override
    protected void visitFunctionSubProgramDeclaration(
        final FunctionSubProgramDeclaration o) {
      //get the function name from the function specification
      final String funcName = o.getTheFunctionSpecification().getTheIDString();

      final SymAdaFunction symFunc = (SymAdaFunction) this.g.resolveSymbol(
          this.g.getFirstSymPackageElement(),
          funcName,
          o);
      if (symFunc == null) {
        throw new RuntimeException("Could not resolve procedure: " + funcName);
      }

      //push the function onto the symbol stack
      this.g.symbolStack.push(symFunc);

      this.g.resetMethodFactory();

      final StringTemplate stFuncDecl = this.g.stg
          .getInstanceOf("procedureSpecification");

      stFuncDecl.setAttribute(
          "ID",
          SymbolTable.returnPilarCompliantName(symFunc.getTheName()));

      stFuncDecl.setAttribute("type", symFunc.getTheReturnType()
          .getTheTranslation());

      /************************************************************************
       * ADD SPEC PARAMETERS
       ***********************************************************************/
      helperAddParameters(stFuncDecl, symFunc.getTheParameters().values(), true);

      /************************************************************************
       * ADA FUNCTION ANNOTATION
       ***********************************************************************/
      final StringTemplate stAFA = this.g.stg
          .getInstanceOf("AdaFunctionAnnotation");

      helperBuildAdaFunctionAnnotation(stAFA, symFunc);

      /************************************************************************
       * OPTIONAL SPARK FUNCTION ANNOTATION
       ***********************************************************************/
      StringTemplate stFA = null;
      {
        final StringTemplate stFAtemp = this.g.stg
            .getInstanceOf("FunctionAnnotation");

        final boolean didSomething = helperBuildFunctionAnnotation(
            stFAtemp,
            symFunc.getTheOptionalDeclaredSpecGlobals(),
            symFunc.getTheOptionalDeclaredSpecGlobalsSelection(),
            symFunc.getTheInGlobalsSpec(),
            o.getTheFunctionAnnotation().getTheOptionalPrecondition(),
            o.getTheFunctionAnnotation().getTheOptionalReturnAnnotation());

        if (didSomething) {
          stFA = stFAtemp;
        }
      }

      final StringTemplate stMA = helperBuildMethodAnnotation(
          stAFA,
          stFA,
          o.getTheOptionalRegionSelection(),
          o.getTheFunctionSpecification().getTheMethodNameSelection(),
          o.getTheFunctionSpecification().getTheOptionalRegionSelection());

      stFuncDecl.setAttribute("annotationList", stMA);

      //remove the function from the symbol stack hierarchy
      this.g.symbolStack.pop();

      this.g.addPackageElement(stFuncDecl);

      helperCreateProofFunction(
          symFunc,
          symFunc.getTheOptionalImplicitSpecProofParameters(),
          true,
          o.getTheOptionalRegionSelection());
    }

    @Override
    protected void visitIDName(final IDName o) {

      final String id = o.getTheIDString();

      final SymPackageElement topElement = this.g.getFirstSymPackageElement();
      final Symbol s = this.g.resolveSymbol(topElement, id, o);
      assert s != null;

      StringTemplate stResult = null;
      if (s instanceof SymCall) {
        stResult = this.g.getCallTransTemplate();
        stResult.setAttribute("componentName", s.getTheTranslation());
      } else {
        stResult = this.g.stg.getInstanceOf("same");
        stResult.setAttribute("ID", s.getTheTranslation());
      }
      // now determine what id's type is
      final SymDef theRetType = SymbolTable.getSymDef(s);

      if (o.getTheDecoratedFlag()) {
        final StringTemplate stDS = this.g.stg.getInstanceOf("decorated_name");
        stDS.setAttribute("decorated_uif_name", SparkUIF.$OLD.toString());
        stDS.setAttribute("name", stResult);
        stResult = stDS;
      }

      this.g.pushResult(stResult, s, theRetType);
    }

    @Override
    protected void visitIfStatement(final IfStatement o) {

      String elseLoc;

      // first pilar loc for the conditional
      final String startLabel = this.g.getNextLocLabel();

      StringTemplate ifPartMark = null;
      StringTemplate thenPartMark = null;
      ArrayList<Pair<StringTemplate, StringTemplate>> optElsIfParts = null;
      StringTemplate optElsePartMark = null;

      final List<StringTemplate> fillInMyEnd = new ArrayList<StringTemplate>();

      { // if then part
        visit(o.getTheExp());

        final StringTemplate stIfStatement = this.g.stg
            .getInstanceOf("ifStatement");
        stIfStatement.setAttribute("locLabel", this.g.generateNewSTLoc());
        stIfStatement.setAttribute("ifCond", this.g.popResult());

        ifPartMark = this.g.newMark(
            startLabel,
            this.g.getCurrLocLabel(),
            o.getTheIfRegionSelection());

        elseLoc = this.g.generateLocLabel_Next();
        stIfStatement.setAttribute("elseBranchLoc", elseLoc);

        this.g.addLocationToCurrentMethod(stIfStatement);

        final String startThen = this.g.getNextLocLabel();
        visit(o.getTheThen()); // statement list
        thenPartMark = this.g.newMark(
            startThen,
            this.g.popStmtEndLocLabel(),
            (RegionSelection) null);

        // jump around else block
        final StringTemplate stLocGoto = this.g.stg.getInstanceOf("locGoto");
        stLocGoto.setAttribute("locLabel", this.g.generateNewSTLoc());

        fillInMyEnd.add(stLocGoto);

        this.g.addLocationToCurrentMethod(stLocGoto);
      }

      // add the location to jump to when condition fails
      StringTemplate stElseBlock = this.g.stg.getInstanceOf("locLabeled");
      stElseBlock.setAttribute("label", elseLoc);
      this.g.addLocationToCurrentMethod(stElseBlock);

      if (o.getTheOptionalElseIfs() != null) {
        //optElsIfParts = new ArrayList<Pair<StringTemplate, StringTemplate>>();
        optElsIfParts = new ArrayList<Pair<StringTemplate, StringTemplate>>();

        for (final Triple<Exp, StatementList, RegionSelection> elsif : o
            .getTheOptionalElseIfs()) {

          elseLoc = this.g.generateLocLabel_Next();

          final String startCondLocLabel = this.g.getNextLocLabel();
          visit(elsif.getE0());

          final StringTemplate stIfStatement = this.g.stg
              .getInstanceOf("ifStatement");
          stIfStatement.setAttribute("locLabel", this.g.generateNewSTLoc());
          stIfStatement.setAttribute("ifCond", this.g.popResult());
          stIfStatement.setAttribute("elseBranchLoc", elseLoc);

          final StringTemplate elsIfCondPartMark = this.g.newMark(
              startCondLocLabel,
              this.g.getCurrLocLabel(),
              elsif.getE2());

          this.g.addLocationToCurrentMethod(stIfStatement);

          final String startThen = this.g.getNextLocLabel();
          visit(elsif.getE1()); // statement list
          final StringTemplate elsIfThenPartMark = this.g.newMark(
              startThen,
              this.g.popStmtEndLocLabel(),
              (RegionSelection) null);
          optElsIfParts.add(new Pair<StringTemplate, StringTemplate>(
              elsIfCondPartMark, elsIfThenPartMark));

          // jump around else block
          final StringTemplate stLocGoto = this.g.stg.getInstanceOf("locGoto");
          stLocGoto.setAttribute("locLabel", this.g.generateNewSTLoc());

          fillInMyEnd.add(stLocGoto);

          this.g.addLocationToCurrentMethod(stLocGoto);

          stElseBlock = this.g.stg.getInstanceOf("locLabeled");
          stElseBlock.setAttribute("label", elseLoc);
          this.g.addLocationToCurrentMethod(stElseBlock);
        }
      }

      if (o.getTheOptionalElse() != null) {
        final String startElse = this.g.getNextLocLabel();
        visit(o.getTheOptionalElse()); // statement list
        optElsePartMark = this.g.newMark(
            startElse,
            this.g.popStmtEndLocLabel(),
            (RegionSelection) null);
      }

      final String endLoc = this.g.generateLocLabel_Next();
      for (final StringTemplate st : fillInMyEnd) {
        st.setAttribute("gotoLoc", endLoc);
      }

      final StringTemplate stl2 = this.g
          .createStmtAnnot(
              o,
              ifPartMark,
              thenPartMark,
              optElsIfParts,
              optElsePartMark,
              this.g.newMark(
                  startLabel,
                  endLoc,
                  o.getTheOptionalRegionSelection()));

      final StringTemplate stEndLoc = this.g.generateNewLabeledAnnotatedLoc(
          endLoc,
          stl2);

      this.g.pushStmtEndLocLabel(endLoc);
      this.g.addLocationToCurrentMethod(stEndLoc);
    }

    @Override
    protected void visitIndexedComponent(final IndexedComponent o) {
      visit(o.getTheName());
      final EvalResult theComponent = this.g.popResult();

      final Symbol s = theComponent.getTheResolvedSymbol();
      assert s != null;

      // the grammar is ambiguous when it comes to method calls, type casts,
      // and array access so the parser treats them all as indexed components
      if (s instanceof SymCall) {
        visitIndexedComponent_CallTransformation((SymCall) s, o);
      } else if (s instanceof SymDef) {
        visitIndexedComponent_TypeCast((SymDef) s, o);
      } else if (s instanceof SymObject) {
        visitIndexedComponent_SymObject((SymObject) s, o, theComponent);
      } else {
        throw new RuntimeException("Unexpected");
      }
    }

    private void visitIndexedComponent_CallTransformation(final SymCall s,
        final IndexedComponent o) {

      final StringTemplate stIndexedComponent = this.g.getCallTransTemplate();

      // call transformations have to be wrapped in NameExp

      stIndexedComponent.setAttribute("componentName", s.getTheTranslation());

      LinkedHashMap<String, SymParameter> theParamsToUse = s.getTheParameters();
      switch (s.getDescriptor()) {
        case SymAdaFunction.DESCRIPTOR: {
          theParamsToUse = s.getTheParameters();
          if (this.g.inProofContext) {
            if (this.g.inSpecPart.peek().booleanValue()) {
              theParamsToUse = ((SymAdaFunction) s)
                  .getTheOptionalImplicitSpecProofParameters();
            } else {
              theParamsToUse = ((SymAdaFunction) s)
                  .getTheOptionalImplicitBodyProofParameters();
            }
          }
          break;
        }
        case SymProofFunction.DESCRIPTOR:
        case SymProcedure.DESCRIPTOR:
          break;
        default:
          throw new RuntimeException("Unexpected");
      }

      final SymParameter[] theParams = theParamsToUse.values().toArray(
          new SymParameter[theParamsToUse.values().size()]);

      assert ((theParams.length == 0) && (o == null))
          || (theParams.length == o.getTheExps().size());

      if (theParams.length > 0) {
        final ArrayList<StringTemplate> theOptCopyBacks = new ArrayList<StringTemplate>();
        int paramIndex = 0;
        for (final Exp exp : o.getTheExps()) {
          visit(exp);
          final EvalResult resultVar = this.g.popResult();

          stIndexedComponent.setAttribute("argument", resultVar);

          final SymParameter theParam = theParams[paramIndex];
          final Mode m = theParam.getTheMode();

          if (this.g.peekGenThreeAddress()
              && ((m == Mode.IN_OUT) || (m == Mode.OUT))) {
            assert (exp instanceof NameExp) || (exp instanceof ExpQualifiedExp);

            final StringTemplate stAssToTemp = this.g.getLastAddedLocation();
            assert stAssToTemp.getName().equals("assignmentStatement");

            final RegionSelection rs = exp.getTheOptionalRegionSelection() != null ? exp
                .getTheOptionalRegionSelection() : o
                .getTheOptionalRegionSelection();

            final StringTemplate stC = this.g.stg
                .getInstanceOf("CopyBackStatementAnnotation");
            final String nextLoc = this.g.getNextLocLabel();
            final StringTemplate mark = this.g.newMark(nextLoc, nextLoc, rs);
            stC.setAttribute("mark", mark);

            // swap the original assignment arround to copy back
            final StringTemplate stCopyBack = this.g.constructAssignment(
                stAssToTemp.getAttribute("rhs").toString(),
                stAssToTemp.getAttribute("lhs").toString(),
                null,
                stC);

            theOptCopyBacks.add(stCopyBack);
          }
          paramIndex++;
        }

        if (s instanceof SymProcedure) {
          this.g.pushTheOptionalResult(theOptCopyBacks);
        }
      }

      final SymDef optRetType = s instanceof SymFunction ? ((SymFunction) s)
          .getTheReturnType() : null;

      this.g.pushResult(stIndexedComponent, s, optRetType);
    }

    private void visitIndexedComponent_SymObject(final SymObject s,
        final IndexedComponent o, final EvalResult theComponent) {

      SymCompositeDef scd = null;
      if (theComponent.getTheReturnType() instanceof SymPartialDef) {
        scd = (SymCompositeDef) ((SymPartialDef) theComponent
            .getTheReturnType()).getTheElaborationSymDef();
      } else {
        scd = (SymCompositeDef) theComponent.getTheReturnType();
      }

      SymDef theComponentSubType = null;
      ArrayList<SymDef> theIndexSubTypes = null;
      int dims = 0;

      switch (scd.getDescriptor()) {
        case SymArrayDef.DESCRIPTOR: {
          final SymArrayDef sad = (SymArrayDef) scd;
          theComponentSubType = sad.getTheComponentSubtypeDef();
          theIndexSubTypes = sad.getTheIndexSubtypeDefs();
          dims = sad.getTheDimensions();
          break;
        }
        case SymIndexSubTypeDef.DESCRIPTOR: {
          final SymIndexSubTypeDef sistd = (SymIndexSubTypeDef) scd;
          theComponentSubType = sistd.getTheParentTypeDef()
              .getTheComponentSubtypeDef();
          theIndexSubTypes = sistd.getTheSubTypeDefs();
          dims = sistd.getTheParentTypeDef().getTheDimensions();
          break;
        }
        default:
          throw new RuntimeException("Unexpected");
      }
      assert (theComponentSubType != null) && (theIndexSubTypes != null)
          && (dims != 0);

      final StringTemplate stIndexedComponent = this.g.stg
          .getInstanceOf("arrayAccess");

      stIndexedComponent.setAttribute("componentName", theComponent
          .getTheTemplate().toString());

      SymDef theLastDef = null;
      int i = 0;
      for (final Exp exp : o.getTheExps()) {
        visit(exp);
        final EvalResult resultVar = this.g.popResult();

        stIndexedComponent.setAttribute("argument", resultVar);
        theLastDef = theIndexSubTypes.get(i++);
      }

      final SymDef theRetType = i == dims ? theComponentSubType : theLastDef;
      assert theRetType != null;

      this.g.pushResult(stIndexedComponent, s, theRetType);
    }

    private void visitIndexedComponent_TypeCast(final SymDef s,
        final IndexedComponent o) {

      final StringTemplate stIndexedComponent = this.g.stg
          .getInstanceOf("castExp");
      stIndexedComponent.setAttribute("componentName", s.getTheTranslation());

      assert o.getTheExps().size() == 1;

      visit(o.getTheExps().get(0));
      final EvalResult resultVar = this.g.popResult();

      assert this.g.typeCompatible(s, resultVar.getTheReturnType());

      stIndexedComponent.setAttribute("argument", resultVar);
      assert s != null;

      this.g.pushResult(stIndexedComponent, s, s);
    }

    @Override
    protected void visitInRangeExp(final InRangeExp o) {
      // e.g. 'if I in 1 .. 10 then'

      visit(o.getTheExp());
      final EvalResult theExp = this.g.popResult();

      visit(o.getTheRange());
      final EvalResult highTempAnnot = this.g.popResult(2);
      final EvalResult lowTempAnnot = this.g.popResult(1);

      this.g.pushResult(helperRangeMembershipTest(
          theExp,
          lowTempAnnot,
          highTempAnnot,
          null));
    }

    @Override
    protected void visitJustificationStatement(final JustificationStatement o) {
      //System.err.println("What to do with justification statement?");
      for (final JustificationClause jc : o.getTheClauses()) {
        //System.err.println(jc.getTheKind());
      }
      //this.g.pushLocLabel("FAKE_LOC");
    }

    @Override
    protected void visitJustificationStatementEnd(
        final JustificationStatementEnd o) {
      //this.g.pushLocLabel("FAKE_LOC");
    }

    @Override
    protected void visitLiteralExp(final LiteralExp o) {

      visit(o.getTheLiteral());

      this.g.handleExp(this.g.popResult(), o);
    }

    @Override
    protected void visitMainProgram(final MainProgram o) {
      this.g.pushGenThreeAddress(false);
      this.g.inSpecPart.push(Boolean.FALSE);

      final StringTemplate stMainPackageDecl = this.g.stg
          .getInstanceOf("packageDeclaration");

      final SymPackage smp = this.g.st.getTheOptionalMainProgram();
      assert smp != null;

      stMainPackageDecl.setAttribute("name", smp.getTheName());

      final StringTemplate stMPHA = this.g.stg
          .getInstanceOf("MainProgramHeaderAnnotation");
      {
        helperBuildPackageHeaderAnnotation(
            stMPHA,
            smp.getTheBodyWithPackages(),
            smp.getTheBodyUseTypeClauses());

        if (!smp.getTheInheritedPackages().isEmpty()) {
          final ArrayList<String> inheritClauses = new ArrayList<String>();

          for (final SymPackage inherited : smp.getTheInheritedPackages()) {
            inheritClauses.add(inherited.getTheTranslation());
          }

          stMPHA.setAttribute(
              "theOptionalInheritClause",
              this.g.createListFromString(inheritClauses));
        }
      }

      //Add package annotations
      final StringTemplate stPackageAnnotation = this.g
          .createPackageAnnotation(
              smp,
              o.getTheOptionalRegionSelection(),
              stMPHA,
              false);
      {
        stMainPackageDecl.setAttribute("annotationList", stPackageAnnotation);

        // types and constants can be declared within a func/proc

        /************************************************************************
         * ADD CONSTANT DECL
         ***********************************************************************/
        helperAddConstDeclaration(stMainPackageDecl, smp, false);

        /************************************************************************
         * ADD TYPES
         ***********************************************************************/
        helperAddTypeDeclarationAnnotations(stMainPackageDecl, smp, false);

      }

      this.g.pushCurrentPackageStack(stMainPackageDecl);
      this.g.symbolStack.push(smp);
      visit(o.getTheSubProgramBody());
      this.g.symbolStack.pop();
      this.g.popCurrentPackageStack();

      final StringTemplate stPackageDeclModel = this.g.createPackageModel(
          smp,
          false);

      stPackageDeclModel.setAttribute("packageDeclaration", stMainPackageDecl);

      final String newFileName = SparkUtil.generatePilarFilename(
          this.g.filename,
          smp.getTheTranslation(),
          PackageKind.MAIN_METHOD);

      this.g.packageMap.put(newFileName, stMainPackageDecl.toString());
      this.g.inSpecPart.pop();
      this.g.popGenThreeAddress();
    }

    @Override
    protected void visitNamedArrayAggregate(final NamedArrayAggregate o) {

      final StringTemplate stNAA = this.g.stg
          .getInstanceOf("namedArrayAggregate");

      final SymDef theArrayDef = (SymDef) this.g.symbolStack.peek();

      List<SymDef> theIndexSubTypes = null;
      final int oldCurrentArrayDim = this.g.currentArrayDim;

      this.g.currentArrayDim = 0;

      if (theArrayDef instanceof SymArrayDef) {
        final SymArrayDef sad = (SymArrayDef) theArrayDef;
        theIndexSubTypes = sad.getTheIndexSubtypeDefs();
      } else {
        final SymIndexSubTypeDef sistd = (SymIndexSubTypeDef) theArrayDef;
        theIndexSubTypes = sistd.getTheSubTypeDefs();
      }

      final SymDef theConcreteIndexType = theIndexSubTypes
          .get(this.g.currentArrayDim);

      final String iterVar = "iter" + this.g.templocal++;

      stNAA.setAttribute(
          "theIndexType",
          theConcreteIndexType.getTheTranslation());
      stNAA.setAttribute("indexVar", iterVar);

      if (o.getTheOptionalArrayComponentAssociations() != null) {
        for (final ArrayComponentAssociation aca : o
            .getTheOptionalArrayComponentAssociations()) {

          visit(aca.getTheAggregateItem()); // the value to assign
          final EvalResult theRHS = this.g.popResult();

          for (final Choice c : aca.getTheChoices()) {
            visitChoice(c);
            final EvalResult caseCondition = this.g.popResult();

            final StringTemplate theArrayComponentAssociation = this.g.stg
                .getInstanceOf("switchCase");

            theArrayComponentAssociation.setAttribute("choice", caseCondition);
            theArrayComponentAssociation.setAttribute("actionExp", theRHS);
            stNAA.setAttribute(
                "arrayComponentAssociation",
                theArrayComponentAssociation);
          }
        }
      } else {
        assert o.getTheOthersFlag();
      }

      if (o.getTheOthersFlag()) {
        assert o.getTheOptionalOthersAggregateItem() != null;

        visit(o.getTheOptionalOthersAggregateItem());
        final EvalResult theActionExp = this.g.popResult();

        stNAA.setAttribute("optionalAggregateItem", theActionExp);
      } else {
        // FIXME: what, I thought we fixed this?????? In the meantime, pilar
        //        used to (and apparently still does) require that switch
        //        statements have a default case.
        stNAA
            .setAttribute(
                "optionalAggregateItem",
                "$I_THOUGHT_PILAR_SWITCH_STATEMENTS_DID_NOT_NEED_A_DEFAULT_ANYMORE__PLEASE_FIX_ME()");
      }

      this.g.currentArrayDim = oldCurrentArrayDim;

      this.g.pushResult(stNAA, theArrayDef, theArrayDef);
    }

    @Override
    protected void visitNamedRecordAggregate(final NamedRecordAggregate o) {
      assert o != null;

      final SymRecordDef srd = (SymRecordDef) this.g.symbolStack.peek();
      final StringTemplate stNamedRecordAggregate = this.g.stg
          .getInstanceOf("namedRecordAggregate");

      for (int i = 0; i < o.getTheRecordComponentAssociations().size(); i++) {

        final RecordComponentAssociation e = o
            .getTheRecordComponentAssociations().get(i);

        final String theField = e.getTheIDString();

        visit(e.getTheExp());
        final EvalResult resultVar = this.g.popResult();

        final StringTemplate stRCA = this.g.stg
            .getInstanceOf("recordComponentAssociation");
        stRCA.setAttribute("selectorName", theField);
        stRCA.setAttribute("exp", resultVar);

        stNamedRecordAggregate
            .setAttribute("recordComponentAssociation", stRCA);
      }

      this.g.pushResult(stNamedRecordAggregate, srd, srd);
    }

    @Override
    protected void visitNameExp(final NameExp o) {

      visit(o.getTheName());
      this.g.handleExp(this.g.popResult(), o);
    }

    @Override
    protected void visitNameRangeExp(final NameRangeExp o) {
      // e.g. 'if I in Index then' 

      visit(o.getTheExp());
      final EvalResult erExp = this.g.popResult();

      visit(o.getTheName());
      final EvalResult erSubType = this.g.popResult();

      final SymDef theType = (SymDef) erSubType.getTheResolvedSymbol();
      assert (theType != null) && (theType instanceof SymScalarDef);

      final EvalResult lowTemp = helperApplyAttribute(
          SparkAttribute.First,
          theType);
      final EvalResult highTemp = helperApplyAttribute(
          SparkAttribute.Last,
          theType);

      this.g.pushResult(helperRangeMembershipTest(
          erExp,
          lowTemp,
          highTemp,
          null));
    }

    @Override
    protected void visitNullStatement(final NullStatement o) {

      final String locLabel = this.g.generateLocLabel_Next();
      final StringTemplate stNull = this.g.generateNewLabeledAnnotatedLoc(
          locLabel,
          this.g.createStmtAnnot(
              o,
              this.g.newMark(
                  locLabel,
                  locLabel,
                  o.getTheOptionalRegionSelection())));

      this.g.pushStmtEndLocLabel(locLabel);
      this.g.addLocationToCurrentMethod(stNull);
    }

    @Override
    protected void visitNumericLiteral(final NumericLiteral o) {
      final StringTemplate st = this.g.stg.getInstanceOf("same");

      final String pilarCompliantNumeral = o.getTheNumberString().replaceAll(
          "_",
          "");
      st.setAttribute("ID", pilarCompliantNumeral);

      final SymDef theType = this.g.inferNumericType(o.getTheNumberString());

      this.g.pushResult(st, null, theType);
    }

    @Override
    protected void visitPackageBody(final PackageBody o) {
      this.g.inSpecPart.push(Boolean.FALSE);

      final SymPackage symPackage = (SymPackage) this.g.resolveSymbol(o);
      assert symPackage != null;

      this.g.symbolStack.push(symPackage);

      this.g.pushGenThreeAddress(false);

      final StringTemplate stPBHA = this.g.stg
          .getInstanceOf("PackageBodyHeaderAnnotation");
      {
        helperBuildPackageHeaderAnnotation(
            stPBHA,
            symPackage.getTheBodyWithPackages(),
            symPackage.getTheBodyUseTypeClauses());

        /**********************************************************************
         * REFINEMENT DEF
         *********************************************************************/
        StringTemplate stRD = this.g.NONE;
        if (symPackage.getTheOptionalRefinementDefinition() != null) {
          final ContainerRefinementDefinition crd = symPackage
              .getTheOptionalRefinementDefinition();

          stRD = this.g.stg.getInstanceOf("RefinementDefinition");

          final ArrayList<StringTemplate> theRCList = new ArrayList<StringTemplate>();
          for (final ContainerRefinementClause crc : crd
              .getTheRefinementClauses()) {
            final StringTemplate stRC = this.g.stg
                .getInstanceOf("RefinementClause");

            final SymRefinementAnnotation sra = crc.getTheRefinementClause();

            stRC.setAttribute("theSubject", sra.getTheOwn().getTheObject()
                .getTheTranslation());
            stRC.setAttribute(
                "theSubjectSelection",
                this.g.getLineAnnotation(sra.getTheSelection()));

            final ArrayList<StringTemplate> theRConstList = new ArrayList<StringTemplate>();
            for (final SymModeAnnotation sma : sra.getTheConstituents()) {
              final StringTemplate stRefinementConstituent = this.g.stg
                  .getInstanceOf("RefinementConstituent");

              stRefinementConstituent.setAttribute("theMode", "@"
                  + sma.getTheMode().toString());

              final Symbol cID = sma.getTheObject();
              stRefinementConstituent.setAttribute(
                  "theConstituent",
                  cID.getTheTranslation());

              stRefinementConstituent.setAttribute(
                  "theRefinementConstituentSelection",
                  this.g.getLineAnnotation(sma.getTheSelection()));

              theRConstList.add(stRefinementConstituent);
            }
            stRC.setAttribute(
                "theConstituents",
                this.g.createListFromTemplate(theRConstList));

            stRC.setAttribute(
                "theRefinementClauseSelection",
                this.g.getLineAnnotation(crc.getTheRefinementClauseSelection()));
            theRCList.add(stRC);
          }

          stRD.setAttribute(
              "theRefinementClauses",
              this.g.createListFromTemplate(theRCList));
          stRD.setAttribute("theRefinementDefinitionSelection", this.g
              .getLineAnnotation(crd.getTheRefinementDefinitionSelection()));
        }
        stPBHA.setAttribute("theOptionalRefinementDefinition", stRD);

      } // end of package body header

      final StringTemplate stPackageBodyDecl = this.g.stg
          .getInstanceOf("packageDeclaration");

      this.g.pushCurrentPackageStack(stPackageBodyDecl);
      {
        final RegionSelection headerRS = SelectionUtil.newRegionSelection(o
            .getTheOptionalRegionSelection().getTheStartCaret(), o.getTheName()
            .getTheOptionalRegionSelection().getTheEndCaret(), this.g.filepath);

        final StringTemplate stPackageBodyAnnot = this.g
            .createPackageAnnotation(symPackage, headerRS, stPBHA, true);

        stPackageBodyDecl.setAttribute("annotationList", stPackageBodyAnnot);

        /************************************************************************
         * CONSTANT DECL
         ***********************************************************************/
        helperAddConstDeclaration(stPackageBodyDecl, symPackage, true);

        /************************************************************************
         * TYPES
         ***********************************************************************/
        helperAddTypeDeclarationAnnotations(stPackageBodyDecl, symPackage, true);

        this.g.pushGenThreeAddress(true);
        /************************************************************************
         * GLOBALS AND INIT PROC
         ***********************************************************************/
        helperAddGlobalsAndInit(stPackageBodyDecl, symPackage, true, o
            .getThePackageImplementation().getTheOptionalStatementList());

        /************************************************************************
         * VISIT METHODS
         ***********************************************************************/
        stPackageBodyDecl.setAttribute(
            "packageElement",
            this.g.createCommentBlock(
                true,
                "P r o c e d u r e s / F u n c t i o n s"));
        this.g.popGenThreeAddress();

        //visit the declarative part
        visit(o.getThePackageImplementation().getTheDeclarativePart());

      } // end of package body declaration

      final StringTemplate stPackageBodyModel = this.g.createPackageModel(
          symPackage,
          true);

      stPackageBodyModel.setAttribute("packageDeclaration", stPackageBodyDecl);

      stPackageBodyDecl.setAttribute("name", symPackage.getTheTranslation());

      final String newFileName = SparkUtil.generatePilarFilename(
          this.g.filename,
          symPackage.getTheTranslation(),
          PackageKind.BODY);

      this.g.packageMap.put(newFileName, stPackageBodyModel.toString());

      this.g.popCurrentPackageStack();

      //remove the package from the symbol stack hierarchy
      this.g.symbolStack.pop();

      this.g.inSpecPart.pop();
      this.g.popGenThreeAddress();
    }

    @Override
    protected void visitPackageDeclaration(final PackageDeclaration o) {
      this.g.inSpecPart.push(Boolean.TRUE);
      this.g.pushGenThreeAddress(false);

      final SymPackage symPackage = (SymPackage) this.g.resolveSymbol(o);
      assert symPackage != null;

      this.g.symbolStack.push(symPackage);

      final boolean isStandardPackage = symPackage.getTheName()
          .equalsIgnoreCase(SparkUtil.SPARK_STANDARD_PACKAGE);

      // create the package specification header annotation
      final StringTemplate stPSHA = this.g.stg
          .getInstanceOf("PackageSpecificationHeaderAnnotation");
      {
        helperBuildPackageHeaderAnnotation(
            stPSHA,
            symPackage.getTheSpecWithPackages(),
            symPackage.getTheSpecUseTypeClauses());

        /**********************************************************************
         * OPTIONAL INHERIT CLAUSES
         *********************************************************************/
        if (!symPackage.getTheInheritedPackages().isEmpty()) {
          final ArrayList<String> inheritClauses = new ArrayList<String>();

          for (final SymPackage inherited : symPackage
              .getTheInheritedPackages()) {
            inheritClauses.add(inherited.getTheTranslation());
          }

          stPSHA.setAttribute(
              "theOptionalInheritClause",
              this.g.createListFromString(inheritClauses));
        }

        /**********************************************************************
         * OPTIONAL PACKAGE SPECIFICATION ANNOTATION
         *********************************************************************/
        final StringTemplate stPSA = this.g.stg
            .getInstanceOf("PackageSpecificationAnnotation");
        {
          boolean didSomething = false;

          /**********************************************************************
           * OPTIONAL OWN CLAUSES
           *********************************************************************/
          if (symPackage.getTheOptionalOwnStatement() != null) {

            final ContainerOwnStatement cos = symPackage
                .getTheOptionalOwnStatement();

            final StringTemplate stOwnStatement = this.g.stg
                .getInstanceOf("OwnStatement");

            final ArrayList<StringTemplate> cList = new ArrayList<StringTemplate>();
            for (final ContainerOwnClause coc : cos.getTheOwnClauses()) {
              final StringTemplate stOC = this.g.stg.getInstanceOf("OwnClause");

              if (coc.getTheOptionalType() != null) {
                stOC.setAttribute("theOptionalDeclaredType", coc
                    .getTheOptionalType().getTheTranslation());
              }

              final ArrayList<StringTemplate> sovaList = new ArrayList<StringTemplate>();
              for (final SymOwnVariableAnnotation sova : coc
                  .getTheOwnVariables()) {
                final StringTemplate stOwnVariable = this.g.stg
                    .getInstanceOf("OwnVariable");

                final Symbol varNameSym = sova.getTheObject();
                assert varNameSym != null;

                stOwnVariable.setAttribute(
                    "varName",
                    varNameSym.getTheTranslation());
                stOwnVariable.setAttribute("mode", "@"
                    + sova.getTheMode().toString());

                assert sova.getTheOwnCategory() != null;
                stOwnVariable.setAttribute("ownCategory", '@' + sova
                    .getTheOwnCategory().toString());

                stOwnVariable.setAttribute(
                    "theOwnVariableSelection",
                    this.g.getLineAnnotation(sova.getTheSelection()));

                sovaList.add(stOwnVariable);
              }
              stOC.setAttribute(
                  "theOwnVariables",
                  this.g.createListFromTemplate(sovaList));

              stOC.setAttribute(
                  "theOwnClauseSelection",
                  this.g.getLineAnnotation(coc.getTheOwnClauseSelection()));

              cList.add(stOC);
            }
            stOwnStatement.setAttribute(
                "theOwnClauses",
                this.g.createListFromTemplate(cList));
            stOwnStatement.setAttribute(
                "theOwnStatementSelection",
                this.g.getLineAnnotation(cos.getTheOwnStatementSelection()));

            stPSA.setAttribute("theOptionalOwnStatement", stOwnStatement);
            didSomething = true;
          }

          /**********************************************************************
           * OPTIONAL VAR INIT CLAUSES
           *********************************************************************/
          if (!symPackage.getTheInitializeVariables().isEmpty()) {
            final ArrayList<String> initClauses = new ArrayList<String>();
            for (final SymSimpleAnnotation s : symPackage
                .getTheInitializeVariables()) {
              final Symbol init = this.g.resolveSymbol(
                  symPackage,
                  s.getTheName(),
                  null);
              initClauses.add(init.getTheTranslation());
            }
            stPSA.setAttribute(
                "theOptionalVarInitClause",
                this.g.createListFromString(initClauses));
            didSomething = true;
          }

          if (didSomething) {
            stPSHA.setAttribute(
                "theOptionalPackageSpecificationAnnotation",
                stPSA);
          }
        }
      } // end of package specification header annotation

      //create a package declaration string template
      final StringTemplate stPackSpecDecl = this.g.stg
          .getInstanceOf("packageDeclaration");

      this.g.pushCurrentPackageStack(stPackSpecDecl);
      {
        // create header selection which includes the 'procedure' keyword and
        // the procedure's name
        final RegionSelection headerRS = isStandardPackage ? SelectionUtil
            .newRegionSelection() : SelectionUtil.newRegionSelection(o
            .getThePackageSpecification().getTheOptionalRegionSelection()
            .getTheStartCaret(), o.getThePackageSpecification().getTheName()
            .getTheOptionalRegionSelection().getTheEndCaret(), this.g.filepath);

        // Add package annotations
        final StringTemplate stPackageAnnotation = this.g
            .createPackageAnnotation(symPackage, headerRS, stPSHA, false);

        stPackSpecDecl.setAttribute("annotationList", stPackageAnnotation);

        /************************************************************************
         * ADD CONSTANT DECL
         ***********************************************************************/
        helperAddConstDeclaration(stPackSpecDecl, symPackage, false);

        /************************************************************************
         * ADD TYPES
         ***********************************************************************/
        helperAddTypeDeclarationAnnotations(stPackSpecDecl, symPackage, false);

        /************************************************************************
         * ADD GLOBALS AND INIT
         ***********************************************************************/
        helperAddGlobalsAndInit(stPackSpecDecl, symPackage, false, null);

        /************************************************************************
         * ADD REPRESENTATIVES
         ***********************************************************************/
        helperAddRepresentations(stPackSpecDecl, symPackage);

        /************************************************************************
         * VISIT METHODS
         ***********************************************************************/
        if (o.getThePackageSpecification()
            .getTheOptionalVisiblePartDeclarativePartMember() != null) {
          for (final DeclarativePartMember pubDpm : o
              .getThePackageSpecification()
              .getTheOptionalVisiblePartDeclarativePartMember()) {
            switch (pubDpm.getDescriptor()) {
              case FunctionSubProgramDeclaration.DESCRIPTOR:
              case ProofFunctionDeclaration.DESCRIPTOR:
              case ProcedureSubProgramDeclaration.DESCRIPTOR: {
                visit(pubDpm);
                break;
              }
              default:
                break;
            }
          }
        }

        if (o.getThePackageSpecification()
            .getTheOptionalPrivatePartDeclarativePartMember() != null) {
          for (final DeclarativePartMember privDpm : o
              .getThePackageSpecification()
              .getTheOptionalPrivatePartDeclarativePartMember()) {
            switch (privDpm.getDescriptor()) {
              case FunctionSubProgramDeclaration.DESCRIPTOR:
              case ProofFunctionDeclaration.DESCRIPTOR:
              case ProcedureSubProgramDeclaration.DESCRIPTOR: {
                visit(privDpm);
                break;
              }
              default:
                break;
            }
          }
        }
      } // end of package declaration

      this.g.popCurrentPackageStack();

      final StringTemplate stPackageDeclModel = this.g.createPackageModel(
          symPackage,
          false);

      stPackageDeclModel.setAttribute("packageDeclaration", stPackSpecDecl);

      stPackSpecDecl.setAttribute("name", symPackage.getTheTranslation());

      final String newFileName = SparkUtil.generatePilarFilename(
          this.g.filename,
          symPackage.getTheTranslation(),
          PackageKind.SPECIFICATION);

      this.g.packageMap.put(newFileName, stPackageDeclModel.toString());

      this.g.symbolStack.pop();

      this.g.inSpecPart.pop();
      this.g.popGenThreeAddress();
    }

    @Override
    protected void visitPositionalArrayAggregate(
        final PositionalArrayAggregate o) {
      assert o != null;
      assert o.getTheAggregateItems() != null;

      final StringTemplate stnaa = this.g.stg
          .getInstanceOf("namedArrayAggregate");

      final SymDef theArrayDef = (SymDef) this.g.symbolStack.peek();

      List<SymDef> theIndexSubTypes = null;
      this.g.currentArrayDim = 0;

      if (theArrayDef instanceof SymArrayDef) {
        final SymArrayDef sad = (SymArrayDef) theArrayDef;
        theIndexSubTypes = sad.getTheIndexSubtypeDefs();
      } else {
        final SymIndexSubTypeDef sistd = (SymIndexSubTypeDef) theArrayDef;
        theIndexSubTypes = sistd.getTheSubTypeDefs();
      }

      final SymDef theConcreteIndexType = theIndexSubTypes
          .get(this.g.currentArrayDim);

      stnaa.setAttribute(
          "theIndexType",
          theConcreteIndexType.getTheTranslation());
      stnaa.setAttribute("indexVar", "iter" + this.g.templocal++);

      for (int i = 0; i < o.getTheAggregateItems().size(); i++) {
        visit(o.getTheAggregateItems().get(i));

        final EvalResult theAgg = this.g.popResult();

        final StringTemplate stChoice = this.g.stg.getInstanceOf("switchCase");
        stChoice.setAttribute("choice", i + 1);
        stChoice.setAttribute("actionExp", theAgg);

        stnaa.setAttribute("arrayComponentAssociation", stChoice);
      }

      if (o.getTheOthersFlag()) {
        assert o.getTheOptionalOthersAggregateItem() != null;

        visit(o.getTheOptionalOthersAggregateItem());
        final EvalResult theAgg = this.g.popResult();

        stnaa.setAttribute("optionalAggregateItem", theAgg);
      }

      this.g.pushResult(stnaa, theArrayDef, theArrayDef);
    }

    @Override
    protected void visitPositionalRecordAggregate(
        final PositionalRecordAggregate o) {
      assert o != null;
      final StringTemplate stNamedRecordAggregate = this.g.stg
          .getInstanceOf("namedRecordAggregate");

      final SymRecordDef srd = (SymRecordDef) this.g.symbolStack.peek();
      final String[] theElements = srd.getTheElements().keySet()
          .toArray(new String[srd.getTheElements().size()]);

      for (int i = 0; i < o.getTheExps().size(); i++) {
        final Exp e = o.getTheExps().get(i);
        visit(e);
        final EvalResult resultVar = this.g.popResult();

        final String elementName = theElements[i];

        final StringTemplate stRCA = this.g.stg
            .getInstanceOf("recordComponentAssociation");
        stRCA.setAttribute("selectorName", elementName);
        stRCA.setAttribute("exp", resultVar);

        stNamedRecordAggregate
            .setAttribute("recordComponentAssociation", stRCA);
      }

      this.g.pushResult(stNamedRecordAggregate, srd, srd);
    }

    @Override
    protected void visitPostcondition(final Postcondition o) {
      this.g.pushGenThreeAddress(false);
      this.g.inProofContext = true;
      visit(o.getThePredicate());
      this.g.inProofContext = false;
      this.g.popGenThreeAddress();
    }

    @Override
    protected void visitPrecondition(final Precondition o) {
      this.g.pushGenThreeAddress(false);
      this.g.inProofContext = true;
      visit(o.getThePredicate());
      this.g.inProofContext = false;
      this.g.popGenThreeAddress();
    }

    @Override
    protected void visitProcedureCallStatement(final ProcedureCallStatement o) {

      final String startLabel = this.g.getNextLocLabel();

      visit(o.getTheName());
      final EvalResult stRet = this.g.popResult();

      assert !this.g.peekGenThreeAddress()
          || stRet.getTheTemplate().getName().equals("callTransformation");
      stRet.getTheTemplate().removeAttribute("annotation");

      final StringTemplate stCallWrapper = this.g.stg
          .getInstanceOf("callWrapper");
      stCallWrapper.setAttribute("theCallExp", stRet);

      String endLoc = this.g.getNextLocLabel();
      final StringTemplate mark = this.g.newMark(
          startLabel,
          endLoc,
          o.getTheOptionalRegionSelection());
      final StringTemplate stl2 = this.g.createStmtAnnot(o, mark);

      final StringTemplate stLocRSAnnot = this.g.generateNewAnnotatedLoc(
          null,
          stl2);
      stCallWrapper.setAttribute("locLabel", stLocRSAnnot);

      this.g.addLocationToCurrentMethod(stCallWrapper);

      if (!(o.getTheName() instanceof SelectedComponent)) {

        @SuppressWarnings("unchecked")
        final ArrayList<StringTemplate> theOptCopyBacks = (ArrayList<StringTemplate>) this.g
            .popTheOptionalResult();

        if ((theOptCopyBacks != null) && !theOptCopyBacks.isEmpty()) {

          final String startLocLabel = ((StringTemplate) theOptCopyBacks.get(0)
              .getAttribute("locLabel")).getAttribute("theLocLabel").toString();

          final String endLocLabel = ((StringTemplate) theOptCopyBacks.get(
              theOptCopyBacks.size() - 1).getAttribute("locLabel"))
              .getAttribute("theLocLabel").toString();
          endLoc = endLocLabel;

          final StringTemplate optCopyBackPart = this.g.newMark(
              startLocLabel,
              endLocLabel,
              (RegionSelection) null);

          stl2.setAttribute("optCopyBackPart", optCopyBackPart);

          for (final StringTemplate st : theOptCopyBacks) {
            this.g.addLocationToCurrentMethod(st);
          }
        }
      }
      this.g.pushStmtEndLocLabel(endLoc);
    }

    @Override
    protected void visitProcedureSubProgramBody(final ProcedureSubProgramBody o) {
      //get the procedure name

      final String procName = o.getTheProcedureSpecification().getTheIDString();

      final SymProcedure symProc = (SymProcedure) this.g.resolveSymbol(
          this.g.getFirstSymPackageElement(),
          procName,
          o);

      if (symProc == null) {
        this.g.emitErrorMessage(o, "Could not resolve procedure: " + procName);
      }

      //push the procedure onto the symbol stack hierarchy
      this.g.symbolStack.push(symProc);

      this.g.resetMethodFactory();

      //IMPORTANT: Must visit subprogram implementation to populate globals list
      //           This is used to generate in, out, and derives when they don't exist
      //           in the SPARK annotations
      visit(o.getTheSubProgramImplementation());
      final EvalResult stSubProgImplResult = this.g.popResult();
      final StringTemplate stSubProgImpl = stSubProgImplResult.getTheTemplate();

      final StringTemplate stReturnStatement = this.g.stg
          .getInstanceOf("returnStatement");
      stReturnStatement.setAttribute("locLabel", this.g.generateNewSTLoc());
      stSubProgImpl.setAttribute("location", stReturnStatement);

      //set the procedure name to the string template
      stSubProgImpl.setAttribute(
          "ID",
          SymbolTable.returnPilarCompliantName(symProc.getTheName()));

      /************************************************************************
       * ADD BODY PARAMS
       ***********************************************************************/
      helperAddParameters(
          stSubProgImpl,
          symProc.getTheParameters().values(),
          false);

      /************************************************************************
       * ADA PROCEDURE ANNOTATION
       ***********************************************************************/
      final StringTemplate stAPA = this.g.stg
          .getInstanceOf("AdaProcedureAnnotation");

      helperBuildAdaProcedureAnnotation(stAPA, symProc);

      /************************************************************************
       * OPTIONAL SPARK PROCEDURE ANNOTATION
       ***********************************************************************/
      StringTemplate stPA = null;
      {
        final StringTemplate stPAtemp = this.g.stg
            .getInstanceOf("ProcedureAnnotation");

        Precondition precond = null;
        Postcondition postcond = null;
        if (o.getTheOptionalProcedureAnnotation() != null) {
          precond = o.getTheOptionalProcedureAnnotation()
              .getTheOptionalPrecondition();
          postcond = o.getTheOptionalProcedureAnnotation()
              .getTheOptionalPostcondition();
        }

        final boolean didSomething = helperBuildProcedureAnnotation(
            stPAtemp,
            symProc.getTheOptionalDeclaredBodyGlobals(),
            symProc.getTheOptionalDeclaredBodyGlobalsSelection(),
            symProc.getTheInGlobalsBody(),
            precond,
            symProc.getTheOptionalDependencyRelationBody(),
            postcond,
            symProc.getTheOutGlobalsBody());

        if (didSomething) {
          stPA = stPAtemp;
        }
      }

      /************************************************************************
       * CREATE METHOD ANNOTATION CONTAINER
       ***********************************************************************/
      final StringTemplate stMA = helperBuildMethodAnnotation(
          stAPA,
          stPA,
          o.getTheOptionalRegionSelection(),
          o.getTheProcedureSpecification().getTheMethodNameSelection(),
          o.getTheProcedureSpecification().getTheOptionalRegionSelection());

      stSubProgImpl.setAttribute("annotationList", stMA);

      //remove the procedure from the symbol stack hierarchy
      this.g.symbolStack.pop();

      this.g.addPackageElement(stSubProgImpl);
    }

    @Override
    protected void visitProcedureSubProgramDeclaration(
        final ProcedureSubProgramDeclaration o) {

      final String procName = o.getTheProcedureSpecification().getTheIDString();

      final SymProcedure symProc = (SymProcedure) this.g.resolveSymbol(
          this.g.getFirstSymPackageElement(),
          procName,
          o);
      assert symProc != null;

      if (symProc == null) {
        this.g.emitErrorMessage(o, "Could not resolve procedure: " + procName);
        return;
      }

      //push the procedure onto the symbol stack hierarchy
      this.g.symbolStack.push(symProc);

      final StringTemplate stSubProgDecl = this.g.stg
          .getInstanceOf("procedureSpecification");

      stSubProgDecl.setAttribute(
          "ID",
          SymbolTable.returnPilarCompliantName(symProc.getTheName()));

      /************************************************************************
       * ADD SPEC PARAMS
       ***********************************************************************/
      helperAddParameters(
          stSubProgDecl,
          symProc.getTheParameters().values(),
          true);

      /************************************************************************
       * ADA PROCEDURE ANNOTATION
       ***********************************************************************/
      final StringTemplate stAPA = this.g.stg
          .getInstanceOf("AdaProcedureAnnotation");

      helperBuildAdaProcedureAnnotation(stAPA, symProc);

      /************************************************************************
       * OPTIONAL SPARK PROCEDURE ANNOTATION
       ***********************************************************************/
      StringTemplate stPA = null;
      {
        final StringTemplate stPAtemp = this.g.stg
            .getInstanceOf("ProcedureAnnotation");

        final boolean didSomething = helperBuildProcedureAnnotation(
            stPAtemp,
            symProc.getTheOptionalDeclaredSpecGlobals(),
            symProc.getTheOptionalDeclaredSpecGlobalsSelection(),
            symProc.getTheInGlobalsSpec(),
            o.getTheProcedureAnnotation().getTheOptionalPrecondition(),
            symProc.getTheOptionalDependencyRelationSpec(),
            o.getTheProcedureAnnotation().getTheOptionalPostcondition(),
            symProc.getTheOutGlobalsSpec());

        if (didSomething) {
          stPA = stPAtemp;
        }
      }

      /************************************************************************
       * CREATE METHOD ANNOTATION CONTAINER
       ***********************************************************************/
      final StringTemplate stMethodAnnotation = helperBuildMethodAnnotation(
          stAPA,
          stPA,
          o.getTheOptionalRegionSelection(),
          o.getTheProcedureSpecification().getTheMethodNameSelection(),
          o.getTheProcedureSpecification().getTheOptionalRegionSelection());

      stSubProgDecl.setAttribute("annotationList", stMethodAnnotation);

      //remove the procedure from the symbol stack hierarchy
      this.g.symbolStack.pop();

      this.g.addPackageElement(stSubProgDecl);
    }

    @Override
    protected void visitProofFunctionDeclaration(
        final ProofFunctionDeclaration o) {
      //get the function name from the function specification
      final String funcName = o.getTheFunctionSpecification().getTheIDString();

      final SymProofFunction symFunc = (SymProofFunction) this.g.resolveSymbol(
          this.g.getFirstSymPackageElement(),
          funcName,
          o);
      if (symFunc == null) {
        throw new RuntimeException("Could not resolve procedure: " + funcName);
      }

      helperCreateProofFunction(
          symFunc,
          symFunc.getTheParameters(),
          false,
          o.getTheOptionalRegionSelection());
    }

    @Override
    protected void visitQuantifiedExp(final QuantifiedExp o) {
      assert o != null;

      final String theIdentifier = o.getTheIdentifier();
      String quantType = null;

      switch (o.getTheKind()) {
        case FOR_ALL:
          quantType = SparkUIF.$FOR_ALL.toString();
          break;
        case FOR_SOME:
          quantType = SparkUIF.$EXISTS.toString();
          break;
        default:
          throw new RuntimeException("Unexpected");
      }

      visit(o.getTheSubTypeMark());
      final EvalResult theIterType = this.g.popResult();
      assert theIterType.getTheReturnType() != null;

      final StringTemplate stQuantExp = this.g.stg
          .getInstanceOf("quantExpRanged");
      if (o.getTheOptionalRange() != null) {
        stQuantExp.setAttribute(
            "optRange",
            helperChoiceRangeChoice(o.getTheOptionalRange(), null, null));
      } else {
        final EvalResult low = helperApplyAttribute(
            SparkAttribute.First,
            theIterType.getTheReturnType());
        final EvalResult high = helperApplyAttribute(
            SparkAttribute.Last,
            theIterType.getTheReturnType());

        final StringTemplate stChoiceRange = this.g.stg
            .getInstanceOf("choiceRange");
        stChoiceRange.setAttribute("low", low);
        stChoiceRange.setAttribute("high", high);

        stQuantExp.setAttribute("optRange", stChoiceRange);
      }

      final SymCall theCall = (SymCall) this.g.getFirstSymPackageElement();
      final SymVariable temp = this.g.addTemporaryLocal(
          theCall,
          theIdentifier,
          theIterType.getTheReturnType());
      {
        stQuantExp.setAttribute("quantType", quantType);
        stQuantExp.setAttribute("iterVar", temp.getTheTranslation());
        stQuantExp.setAttribute("iterVarTypeName", theIterType);

        visit(o.getThePredicate());
        stQuantExp.setAttribute("predicate", this.g.popResult());
      }
      this.g.removeTemporaryLocal(theCall, temp);

      final SymDef theType = this.g.st.getBaseType("boolean");
      this.g.handleExp(new EvalResult(stQuantExp, null, theType), o);
    }

    @Override
    protected void visitRangeAttributeReference(final RangeAttributeReference o) {

      visit(o.getTheName());
      final EvalResult thePrefix = this.g.popResult();
      final SymDef theSymDef = thePrefix.getTheReturnType();

      String[] theOptExp = null;
      if (o.getTheDesignator().getTheOptionalExp() != null) {
        visit(o.getTheDesignator().getTheOptionalExp());
        theOptExp = new String[] { this.g.popResult().toString() };
      }

      Symbol theSym = theSymDef;
      if (theSym instanceof SymArrayDef) {
        final SymArrayDef sad = (SymArrayDef) theSym;
        if (!sad.getIsConstrained()) {
          theSym = thePrefix.getTheResolvedSymbol();
        }
      }

      final EvalResult lowTemp = helperApplyAttribute(
          SparkAttribute.First,
          theSym,
          theSymDef,
          theOptExp);
      final EvalResult highTemp = helperApplyAttribute(
          SparkAttribute.Last,
          theSym,
          theSymDef,
          theOptExp);

      assert (lowTemp != null) && (highTemp != null);
      assert this.g.typeCompatible(
          lowTemp.getTheReturnType(),
          highTemp.getTheReturnType());
      this.g.pushResult(lowTemp, 0);
      this.g.pushResult(highTemp, 1);
    }

    @Override
    protected void visitRecordRepresentationClause(
        final RecordRepresentationClause o) {
      throw new UnsupportedOperationException(
          "Record Representation Clauses not handled yet");
    }

    @Override
    protected void visitRecordUpdate(final RecordUpdate o) {

      assert !this.g.peekGenThreeAddress() && this.g.inProofContext;
      //this.g.pushGenThreeAddress(false);
      //this.g.inProofContext = true;

      visit(o.getTheName());
      final EvalResult theRecordName = this.g.popResult();

      final SymObject so = (SymObject) theRecordName.getTheResolvedSymbol();
      assert so.getTheOptionalTypeSymDef() instanceof SymRecordDef;

      final StringTemplate stcue = this.g.stg
          .getInstanceOf("CompositeUpdateExp");
      stcue.setAttribute(
          "componentUpdateUIF",
          SparkUIF.$RECORD_UPDATE.toString());
      stcue.setAttribute("thePrefix", theRecordName.getTheTemplate());

      for (final Pair<String, Exp> p : o.getTheSelectorToExpressionList()) {

        final StringTemplate stcuee = this.g.stg
            .getInstanceOf("CompositeUpdateExpEntry");
        stcuee.setAttribute("choice", ':' + p.getE0().toLowerCase());
        visit(p.getE1());
        final EvalResult erExp = this.g.popResult();

        stcuee.setAttribute("value", erExp.getTheTemplate());
        stcue.setAttribute("entry", stcuee);
      }

      //this.g.inProofContext = false;
      //this.g.popGenThreeAddress();

      final SymDef boolType = this.g.st.getBaseType("Boolean");
      this.g.pushResult(new EvalResult(stcue, null, boolType));
    }

    @Override
    protected void visitReturnAnnotationExp(final ReturnAnnotationExp o) {
      this.g.pushGenThreeAddress(false);
      this.g.inProofContext = true;
      visit(o.getTheExp());
      this.g.inProofContext = false;
      this.g.popGenThreeAddress();

      final EvalResult er = this.g.popResult();
      // apparently return exp do not have to resolve to boolean
      //assert er.getTheReturnType() == this.g.st.getBaseType("boolean");

      this.g.handleExp(er, o.getTheExp());
    }

    @Override
    protected void visitReturnAnnotationPred(final ReturnAnnotationPred o) {

      final SymFunction sf = (SymFunction) this.g.getFirstSymPackageElement();
      final SymVariable temp = this.g.addTemporaryLocal(
          sf,
          o.getTheID(),
          sf.getTheReturnType());

      this.g.pushGenThreeAddress(false);
      this.g.inProofContext = true;
      visit(o.getThePredicate());
      this.g.inProofContext = false;
      this.g.popGenThreeAddress();

      this.g.removeTemporaryLocal(sf, temp);

      final EvalResult rhs = this.g.popResult();

      final SymDef boolType = this.g.st.getBaseType("boolean");
      assert rhs.getTheReturnType() == boolType;

      final StringTemplate ret = this.g.stg
          .getInstanceOf("returnAnnotationPred");
      ret.setAttribute("theResultVarType", sf.getTheReturnType()
          .getTheTranslation());
      ret.setAttribute("theResultVar", temp.getTheTranslation());
      ret.setAttribute("thePredicate", rhs);

      this.g.handleExp(new EvalResult(ret, null, boolType), o.getThePredicate()
          .getTheExp());
    }

    @Override
    protected void visitReturnStatement(final ReturnStatement o) {
      final String startLoc = this.g.getNextLocLabel();

      visit(o.getTheExp());
      final EvalResult theExpResultVar = this.g.popResult();

      final SymCall sc = (SymCall) this.g.symbolStack.peek();
      if (sc instanceof SymFunction) {
        assert this.g.typeCompatible(
            ((SymFunction) sc).getTheReturnType(),
            theExpResultVar.getTheReturnType());
        assert !theExpResultVar.getTheTemplate().toString().equals("");
      }

      final StringTemplate retTempLoc = this.g.stg
          .getInstanceOf("returnStatement");
      retTempLoc.setAttribute("theOptRetExp", theExpResultVar);

      final String endLoc = this.g.getNextLocLabel();
      final StringTemplate stl2 = this.g.createStmtAnnot(
          o,
          this.g.newMark(startLoc, endLoc, o.getTheOptionalRegionSelection()));

      retTempLoc.setAttribute("locLabel", //
          this.g.generateNewAnnotatedLoc(null, //this.g.getLineAnnotation(o.getTheOptionalRegionSelection()),
              //stl,
              stl2));

      this.g.pushStmtEndLocLabel(endLoc);
      this.g.addLocationToCurrentMethod(retTempLoc);
    }

    @Override
    protected void visitSelectedComponent(final SelectedComponent o) {
      final String selector_name = o.getTheIDString();

      visit(o.getTheName());
      final EvalResult stPrefix = this.g.popResult();
      assert stPrefix.getTheResolvedSymbol() != null;

      // now we need to determine what is being selected
      Symbol s = null;
      if (stPrefix.getTheResolvedSymbol() instanceof SymPackage) {
        // something like P.D where P is a package
        s = this.g.resolveSymbol(
            stPrefix.getTheResolvedSymbol(),
            selector_name,
            o);
      } else {
        // something like q.r where q is a record or q[x].r where q is an array
        // of records
        assert stPrefix.getTheReturnType() != null;
        s = this.g.resolveSymbol(stPrefix.getTheReturnType(), selector_name, o);
      }
      assert s != null;

      final SymDef theRetType = SymbolTable.getSymDef(s);
      assert (theRetType != null) || (s instanceof SymProcedure);

      StringTemplate result = null;
      if (s instanceof SymRecordComponentDef) {
        result = this.g.stg.getInstanceOf("selectedComponent");
        result.setAttribute("prefix", stPrefix);
        result.setAttribute("id", s.getTheTranslation());

        // if the selected object is a record then this is what we want to 
        // return as the resolved object.  The return type will be the
        // selected component's type
        s = stPrefix.getTheResolvedSymbol();
      } //
      else if ((s instanceof SymDef) || (s instanceof SymObject)) {
        result = this.g.stg.getInstanceOf("same");
        result.setAttribute("ID", s.getTheTranslation());
      } //
      else if (s instanceof SymCall) {
        final SymCall sc = (SymCall) s;
        if (sc.getTheNumberOfParams() == 0) {
          result = this.g.getCallTransTemplate();
          result.setAttribute("componentName", sc.getTheTranslation());
        } else {
          result = this.g.stg.getInstanceOf("same");
          result.setAttribute("ID", s.getTheTranslation());
        }
      } //
      else {
        throw new RuntimeException("Unexpected");
      }
      assert (result != null) && !result.toString().equals("");

      if (o.getTheDecoratedFlag()) {
        final StringTemplate decResult = this.g.stg
            .getInstanceOf("decorated_name");
        decResult.setAttribute("decorated_uif_name", SparkUIF.$OLD.toString());
        decResult.setAttribute("name", result);
        result = decResult;
      }

      this.g.pushResult(result, s, theRetType);
    }

    @Override
    protected void visitStatementList(final StatementList o) {
      for (final Statement s : o.getTheStatements()) {
        visit(s);
      }
    }

    @Override
    protected void visitStatementSubProgramImplementation(
        final StatementSubProgramImplementation o) {

      this.g.pushGenThreeAddress(true);
      this.g.inProofContext = false;

      final StringTemplate st = this.g.stg
          .getInstanceOf("procedureImplementation");

      this.g.pushCurrentMethodStack(st);
      {
        if (o.getTheDeclarativePart() != null) {
          //visit(o.getTheDeclarativePart());
        }

        //if the current symbol on the symbol stack is a procedure or function
        //add the local var declarations accordingly
        final SymCall theMethod = (SymCall) this.g.getFirstSymPackageElement();
        assert theMethod != null;

        /***********************************************************************
         * ADD LOCAL VAR OPTIONAL INITIALIZATIONS TO PILAR METHOD BODY
         **********************************************************************/
        // use a fixed array since the following iteration could add local
        // variables to the method

        final List<SymObject> theLinearizedObjects = SymbolTable
            .linearizeObjects(theMethod.getTheObjects());

        final SymObject[] theObjects = theLinearizedObjects
            .toArray(new SymObject[theLinearizedObjects.size()]);
        for (final SymObject local : theObjects) {

          if (local instanceof SymConstant) {
            // constants are emitted at the package level
            continue;
          }

          final SymVariable sv = (SymVariable) local;
          assert sv != null;
          if (sv.getTheExp() != null) {
            final String startLoc = this.g.getNextLocLabel();
            visit(sv.getTheExp());
            final EvalResult er = this.g.popResult();

            final StringTemplate stl2 = this.g.createStmtAnnot(
                sv,
                this.g.newMark(
                    startLoc,
                    this.g.getNextLocLabel(),
                    sv.getTheSelection()));

            final StringTemplate stASS = this.g.constructAssignment(
                sv.getTheTranslation().toString(),
                er.getTheTemplate().toString(),
                null, //sv.getTheSelection(),
                stl2);
            this.g.addLocationToCurrentMethod(stASS);
          }
        }

        /***********************************************************************
         * VISIT THE STATEMENTS
         **********************************************************************/
        visit(o.getTheStatementList());

        /***********************************************************************
         * ADD LOCAL VARS DECLARATION TO PILAR METHOD
         **********************************************************************/
        for (final SymObject local : SymbolTable.linearizeObjects(theMethod
            .getTheObjects())) {//.values()) {
          if (local instanceof SymConstant) {
            // constants are emitted at the package level
            continue;
          }

          final SymVariable sv = (SymVariable) local;
          assert sv.getTheOptionalTypeSymDef() != null;

          final StringTemplate stLocVar = this.g.stg
              .getInstanceOf("localVarDeclaration");
          stLocVar.setAttribute("type", sv.getTheOptionalTypeSymDef()
              .getTheTranslation());
          stLocVar.setAttribute("name", sv.getTheTranslation());

          final StringTemplate stLVDA = this.g.stg
              .getInstanceOf("localVarDeclarationAnnotation");
          stLVDA.setAttribute("theKind", "@" + sv.getTheKind().toString());

          if (sv.getTheKind() == SymObjectKind.CONCRETE_VARIABLE) {
            stLVDA.setAttribute(
                "theOptionalLoc",
                this.g.getLineAnnotation(sv.getTheSelection()));
          }

          stLocVar.setAttribute("annotation", stLVDA);

          st.setAttribute("localVarDeclaration", stLocVar);
        }
      }

      this.g.popGenThreeAddress();

      this.g.popCurrentMethodStack();
      this.g.pushResult(st, null, null);
    }

    @Override
    protected void visitStringLiteral(final StringLiteral o) {
      final StringTemplate st = this.g.stg.getInstanceOf("same");
      st.setAttribute("ID", "\"" + o.getTheString() + "\"");
      final SymDef stringType = this.g.st.getBaseType("string");
      this.g.pushResult(st, null, stringType);
    }

    @Override
    protected void visitUnaryExp(final UnaryExp o) {
      final StringTemplate stUnaryExp = this.g.stg.getInstanceOf("unaryExp");

      String op = null;
      switch (o.getTheUnaryOp()) {
        case NOT:
          op = "!";
          break;
        case NEG:
          op = "-";
          break;

        case POS:
          // FIXME
          op = "+";
          throw new UnsupportedOperationException("Need to handle "
              + o.getTheUnaryOp());
        case ABS:
          // FIXME
          op = "abs";
          throw new UnsupportedOperationException("Need to handle "
              + o.getTheUnaryOp());
      }
      stUnaryExp.setAttribute("unop", op);

      visit(o.getTheExp());
      final EvalResult stTheExpResultVar = this.g.popResult();
      assert stTheExpResultVar.getTheReturnType() != null;

      stUnaryExp.setAttribute("exp", stTheExpResultVar);
      this.g.handleExp(
          new EvalResult(stUnaryExp, stTheExpResultVar.getTheResolvedSymbol(),
              stTheExpResultVar.getTheReturnType()),
          o);
    }

    @Override
    protected void visitWhileLoopStatement(final WhileLoopStatement o) {

      // add the start (jump back) location 
      final String startLoc = this.g.getNextLocLabel();
      this.g.addLocationToCurrentMethod(this.g.generateNewSTLoc());

      final String condStart = this.g.getNextLocLabel();
      visit(o.getTheExp());

      final EvalResult erCond = this.g.popResult();
      final StringTemplate whileIterCond = erCond.getTheTemplate();

      final String endLoc = this.g.generateLocLabel_Exit();

      final StringTemplate condPartMark;

      { // jump to exit location if condition doesn't hold
        condPartMark = this.g.newMark(
            condStart,
            this.g.getNextLocLabel(),
            SelectionUtil.intersect(
                o.getTheIterationSchemeRegionSelection(),
                o.getTheLoopKeywordRegionSelection()));

        final StringTemplate locCondJump = this.g.stg
            .getInstanceOf("locCondJump");
        locCondJump.setAttribute("locLabel", this.g.generateNewSTLoc());
        locCondJump.setAttribute("cond", '!' + whileIterCond.toString());
        locCondJump.setAttribute("jumpLoc", endLoc);

        this.g.addLocationToCurrentMethod(locCondJump);
      }

      if (o.getTheOptionalLoopInvariant() != null) {
        this.g.emitErrorMessage(o, "currently ignoring loop invariants");
      }

      final String bodyStart = this.g.getNextLocLabel();
      visit(o.getTheStatementList()); // visit body of while loop
      final StringTemplate body = this.g.newMark(
          bodyStart,
          this.g.popStmtEndLocLabel(),
          (RegionSelection) null);

      { // add jump back to head of while loop
        final StringTemplate stLocGoto = this.g.stg.getInstanceOf("locGoto");
        stLocGoto.setAttribute("locLabel", this.g.generateNewSTLoc());
        stLocGoto.setAttribute("gotoLoc", startLoc);
        this.g.addLocationToCurrentMethod(stLocGoto);
      }

      { // add end location
        final StringTemplate stl2 = this.g
            .createStmtAnnot(
                o,
                condPartMark,
                body,
                this.g.newMark(
                    startLoc,
                    endLoc,
                    o.getTheOptionalRegionSelection()));

        final StringTemplate stEndLoc = this.g.generateNewLabeledAnnotatedLoc(
            endLoc,
            stl2);

        this.g.addLocationToCurrentMethod(stEndLoc);
      }

      this.g.pushStmtEndLocLabel(endLoc);
      // TODO: is the exit loc idea needed anymore ???
      final String poppedExitLoc = this.g.popExitLoc();
      assert poppedExitLoc.equals(endLoc);
    }
  }

  public static final String OPT_RESULT_VAR_NAME = "optResultVarName";
  public static final String CompilerVersion = "1.0";

  public static final String RESULT_VAR = "result$";

  public static final String CLASS_NAME_PRETTY = PilarTranslator.class
      .getSimpleName() + ": ";

  public static final String CLASS_NAME = PilarTranslator.class.getSimpleName()
      + "_";

  public static Map<String, String> translate(
      final List<SPARKCompilationUnit> theList,
      final SparkCompilerOptions options, final List<Message> theMessages) {

    final SymbolTable theSymbolTable = new SymbolTable();

    // load each compilation unit into the symbol table
    for (final SPARKCompilationUnit scu : theList) {

      SymbolTableBuilder.process(scu, theSymbolTable, theMessages);

      if (MessageFactory.hasErrorMessage(theMessages)) {
        return null;
      }
    }

    // run this phase to clean up any unresolved symbols
    if (!theSymbolTable.finalizeSymbolTable(theMessages)) {
      final ErrorMessage em = MessageFactory
          .newErrorMessage(PilarTranslator.CLASS_NAME_PRETTY
              + "Generation of Pilar files failed as there are unresolved symbols");
      theMessages.add(em);
      return null;
    }

    final InputStream is = SparkTemplateAnchor.class
        .getResourceAsStream(SparkTemplateAnchor.TEMPLATE_NAME);

    final StringTemplateGroup stg = new StringTemplateGroup(
        new InputStreamReader(is), DefaultTemplateLexer.class);
    final Class<?> c = NLWriter.class;
    c.getConstructors();
    stg.setStringTemplateWriter(c);

    final Map<String, String> theMap = new LinkedHashMap<String, String>();
    for (final SPARKCompilationUnit scu : theList) {
      final File f = new File(scu.getTheSparkSourceFilename());
      final PilarTranslatorContext ptc = new PilarTranslatorContext(theMap,
          stg, theSymbolTable, f.getPath(), f.getName(), theMessages, options);

      new PilarTranslatorVisitor(ptc).visit(scu.getTheCompilationUnit());

      if (MessageFactory.hasErrorMessage(theMessages)) {
        try {
          is.close();
        } catch (final IOException e) {
          e.printStackTrace();
        }
        return null;
      }
    }

    try {
      is.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }

    return theMap;
  }
}
