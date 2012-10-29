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

package org.sireum.bakar.compiler.translator;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;
import org.sireum.bakar.SparkUtil;
import org.sireum.bakar.compiler.model.AttributeReference;
import org.sireum.bakar.compiler.model.BasicDeclarationBasicDeclarativeItem;
import org.sireum.bakar.compiler.model.ComponentDeclaration;
import org.sireum.bakar.compiler.model.Constituent;
import org.sireum.bakar.compiler.model.ConstrainedArrayDefinition;
import org.sireum.bakar.compiler.model.Constraint;
import org.sireum.bakar.compiler.model.DeclarativePartMember;
import org.sireum.bakar.compiler.model.DependencyClause;
import org.sireum.bakar.compiler.model.DependencyRelation;
import org.sireum.bakar.compiler.model.EmbeddedPackageDeclaration;
import org.sireum.bakar.compiler.model.EnumerationTypeDefinition;
import org.sireum.bakar.compiler.model.ExpRange;
import org.sireum.bakar.compiler.model.FloatingPointDefinition;
import org.sireum.bakar.compiler.model.FullTypeDeclaration;
import org.sireum.bakar.compiler.model.FunctionSubProgramBody;
import org.sireum.bakar.compiler.model.FunctionSubProgramDeclaration;
import org.sireum.bakar.compiler.model.GlobalDeclaration;
import org.sireum.bakar.compiler.model.GlobalDefinition;
import org.sireum.bakar.compiler.model.GlobalMode;
import org.sireum.bakar.compiler.model.IDAttributeDesignator;
import org.sireum.bakar.compiler.model.IDName;
import org.sireum.bakar.compiler.model.IndexConstraint;
import org.sireum.bakar.compiler.model.MainProgram;
import org.sireum.bakar.compiler.model.Mode;
import org.sireum.bakar.compiler.model.ModularTypeDefinition;
import org.sireum.bakar.compiler.model.Name;
import org.sireum.bakar.compiler.model.NameExp;
import org.sireum.bakar.compiler.model.Node;
import org.sireum.bakar.compiler.model.NodeCloner;
import org.sireum.bakar.compiler.model.NodeVisitor;
import org.sireum.bakar.compiler.model.NumberDeclaration;
import org.sireum.bakar.compiler.model.ObjectDeclaration;
import org.sireum.bakar.compiler.model.OrdinaryFixedPointDefinition;
import org.sireum.bakar.compiler.model.OwnVariable;
import org.sireum.bakar.compiler.model.OwnVariableSpecification;
import org.sireum.bakar.compiler.model.PackageAnnotation;
import org.sireum.bakar.compiler.model.PackageBody;
import org.sireum.bakar.compiler.model.PackageDeclaration;
import org.sireum.bakar.compiler.model.PackageSpecification;
import org.sireum.bakar.compiler.model.ParameterSpecification;
import org.sireum.bakar.compiler.model.Postcondition;
import org.sireum.bakar.compiler.model.Precondition;
import org.sireum.bakar.compiler.model.PrivateExtensionDeclaration;
import org.sireum.bakar.compiler.model.PrivateTypeDeclaration;
import org.sireum.bakar.compiler.model.ProcedureSubProgramBody;
import org.sireum.bakar.compiler.model.ProcedureSubProgramDeclaration;
import org.sireum.bakar.compiler.model.ProofFunctionDeclaration;
import org.sireum.bakar.compiler.model.ProofTypeDeclaration;
import org.sireum.bakar.compiler.model.Range;
import org.sireum.bakar.compiler.model.RangeAttributeReference;
import org.sireum.bakar.compiler.model.RangeConstraint;
import org.sireum.bakar.compiler.model.RecordDefinition;
import org.sireum.bakar.compiler.model.RecordTypeDefinition;
import org.sireum.bakar.compiler.model.RecordTypeExtension;
import org.sireum.bakar.compiler.model.RefinementClause;
import org.sireum.bakar.compiler.model.RenamingDeclaration;
import org.sireum.bakar.compiler.model.RepresentationClause;
import org.sireum.bakar.compiler.model.SelectedComponent;
import org.sireum.bakar.compiler.model.SignedIntegerTypeDefinition;
import org.sireum.bakar.compiler.model.StatementSubProgramImplementation;
import org.sireum.bakar.compiler.model.SubTypeDeclaration;
import org.sireum.bakar.compiler.model.TypeAssertion;
import org.sireum.bakar.compiler.model.UnconstrainedArrayDefinition;
import org.sireum.bakar.compiler.model.UseTypeClause;
import org.sireum.bakar.compiler.model.WithClause;
import org.sireum.bakar.compiler.module.util.SPARKCompilationUnit;
import org.sireum.bakar.compiler.symboltable.SymbolTable;
import org.sireum.bakar.compiler.symboltable.model.ContainerDependencyClause;
import org.sireum.bakar.compiler.symboltable.model.ContainerDependencyRelation;
import org.sireum.bakar.compiler.symboltable.model.ContainerOwnClause;
import org.sireum.bakar.compiler.symboltable.model.ContainerOwnStatement;
import org.sireum.bakar.compiler.symboltable.model.ContainerRefinementClause;
import org.sireum.bakar.compiler.symboltable.model.ContainerRefinementDefinition;
import org.sireum.bakar.compiler.symboltable.model.SymAbstractDef;
import org.sireum.bakar.compiler.symboltable.model.SymAdaFunction;
import org.sireum.bakar.compiler.symboltable.model.SymArrayDef;
import org.sireum.bakar.compiler.symboltable.model.SymCall;
import org.sireum.bakar.compiler.symboltable.model.SymDef;
import org.sireum.bakar.compiler.symboltable.model.SymDerivesAnnotation;
import org.sireum.bakar.compiler.symboltable.model.SymDerivesAnnotationModel;
import org.sireum.bakar.compiler.symboltable.model.SymEnumElementDef;
import org.sireum.bakar.compiler.symboltable.model.SymEnumerationDef;
import org.sireum.bakar.compiler.symboltable.model.SymFixedDef;
import org.sireum.bakar.compiler.symboltable.model.SymFloatingDef;
import org.sireum.bakar.compiler.symboltable.model.SymFunction;
import org.sireum.bakar.compiler.symboltable.model.SymGlobalAnnotation;
import org.sireum.bakar.compiler.symboltable.model.SymIndexSubTypeDef;
import org.sireum.bakar.compiler.symboltable.model.SymModDef;
import org.sireum.bakar.compiler.symboltable.model.SymModeAnnotation;
import org.sireum.bakar.compiler.symboltable.model.SymNullDerivesAnnotation;
import org.sireum.bakar.compiler.symboltable.model.SymObject;
import org.sireum.bakar.compiler.symboltable.model.SymObjectKind;
import org.sireum.bakar.compiler.symboltable.model.SymOrigin;
import org.sireum.bakar.compiler.symboltable.model.SymOwnCategory;
import org.sireum.bakar.compiler.symboltable.model.SymOwnVariableAnnotation;
import org.sireum.bakar.compiler.symboltable.model.SymPackage;
import org.sireum.bakar.compiler.symboltable.model.SymPackageElement;
import org.sireum.bakar.compiler.symboltable.model.SymParameter;
import org.sireum.bakar.compiler.symboltable.model.SymPartialDef;
import org.sireum.bakar.compiler.symboltable.model.SymProcedure;
import org.sireum.bakar.compiler.symboltable.model.SymProofFunction;
import org.sireum.bakar.compiler.symboltable.model.SymRangeSubTypeDef;
import org.sireum.bakar.compiler.symboltable.model.SymRecordComponentDef;
import org.sireum.bakar.compiler.symboltable.model.SymRecordDef;
import org.sireum.bakar.compiler.symboltable.model.SymRefinementAnnotation;
import org.sireum.bakar.compiler.symboltable.model.SymScalarDef;
import org.sireum.bakar.compiler.symboltable.model.SymSignedIntegerDef;
import org.sireum.bakar.compiler.symboltable.model.SymSimpleAnnotation;
import org.sireum.bakar.compiler.symboltable.model.Symbol;
import org.sireum.bakar.compiler.translator.SymbolTableBuilder.SymbolTableBuilderContext.EvalResult;
import org.sireum.bakar.selection.model.RegionSelection;
import org.sireum.bakar.util.Pair;
import org.sireum.bakar.util.message.ErrorMessage;
import org.sireum.bakar.util.message.Message;
import org.sireum.bakar.util.message.MessageFactory;
import org.sireum.bakar.util.template.SparkTemplateAnchor;

/**
 * @author Jon Hoag
 * @author Jason Belt
 * 
 */
public class SymbolTableBuilder {

  protected static class SymbolTableBuilderContext {

    static class EvalResult {
      StringTemplate theTemplate;
      Symbol theSymbol;

      EvalResult(final StringTemplate st, final Symbol s) {
        this.theTemplate = st;
        this.theSymbol = s;
      }

      Symbol getTheSymbol() {
        return this.theSymbol;
      }

      StringTemplate getTheTemplate() {
        return this.theTemplate;
      }

      @Override
      public String toString() {
        return this.theTemplate.toString();
      }
    }

    private final StringTemplateGroup stg;
    private EvalResult result;
    final SymbolTable symbolTable;
    final org.sireum.bakar.util.Stack<Symbol> symbolStack = new org.sireum.bakar.util.Stack<Symbol>();
    LinkedHashSet<SymPackage> withPackages = new LinkedHashSet<SymPackage>();
    LinkedHashSet<SymDef> theUseTypeClauses = new LinkedHashSet<SymDef>();

    int embeddedPackageCount = 0;
    boolean inPackageDeclaration = false;
    boolean inPrivatePart = false;
    final String filepath;
    public boolean visitName;

    List<Message> messages;
    public boolean processedStandardPackage;
    public List<SymModeAnnotation> theUnresolvedRefinementConstituents = new ArrayList<SymModeAnnotation>();

    public SymbolTableBuilderContext(final SymbolTable theSymbolTable,
        final StringTemplateGroup stg, final String filepath,
        final List<Message> messages) {
      this.symbolTable = theSymbolTable;
      this.stg = stg;
      this.filepath = filepath;
      this.messages = messages;
    }

    public void addNodeToSymbolMapping(final Node o, final Symbol s) {
      assert (o != null) && (s != null);
      this.symbolTable.addNodeToSymbolMapping(o, s);
    }

    public void emitErrorMessage(final Node o, final String messageText) {
      final int l = o.getTheOptionalRegionSelection().getTheStartCaret()
          .getTheLine();
      final int c = o.getTheOptionalRegionSelection().getTheStartCaret()
          .getTheCol();
      final ErrorMessage em = MessageFactory.newErrorMessage(
          this.filepath,
          l,
          c,
          messageText);
      this.messages.add(em);
    }

    public SymPackage getFirstSymPackageOnStack() {

      for (int i = 0; i < this.symbolStack.size(); i++) {
        if (this.symbolStack.peekFromTop(i) instanceof SymPackage) {
          return (SymPackage) this.symbolStack.peekFromTop(i);
        }
      }

      return null;
    }

    public SymbolTable getSymbolTable() {
      return this.symbolTable;
    }

    public SymPackageElement getTopSymbol() {
      if (!this.symbolStack.isEmpty()) {
        return (SymPackageElement) this.symbolStack.peek();
      }
      return null;
    }

    public EvalResult popResult() {
      assert this.result != null;
      final EvalResult temp = this.result;
      this.result = null;
      return temp;
    }

    public void pushResult(final StringTemplate st, final Symbol ret) {
      assert this.result == null;
      assert !st.toString().equals("");
      this.result = new EvalResult(st, ret);
    }
  }

  protected static class SymbolTableBuilderVisitor extends
      NodeVisitor<SymbolTableBuilder.SymbolTableBuilderContext> {

    public SymbolTableBuilderVisitor(
        final SymbolTableBuilder.SymbolTableBuilderContext g1) {
      super(g1);
    }

    EvalResult getNameAndSymbol(final Name o) {
      assert (o instanceof IDName) || (o instanceof SelectedComponent);

      this.g.visitName = true;
      visit(o);
      this.g.visitName = false;
      return this.g.popResult();
    }

    SymPackage getParentPackage(final Name o) {
      switch (o.getDescriptor()) {
        case IDName.DESCRIPTOR:
          return this.g.symbolTable.resolveBasePackage(((IDName) o)
              .getTheIDString());
        case SelectedComponent.DESCRIPTOR:
          final SelectedComponent sc = (SelectedComponent) o;
          final SymPackage parent = getParentPackage(sc.getTheName());
          return parent.getTheChildPackages().get(
              sc.getTheIDString().toLowerCase());
        default:
          assert false;
      }
      return null;
    }

    void helperCreateImplicitProofFunction(final SymAdaFunction symbol,
        final boolean isSpec) {

      // now we need to create the proof function parameters, this will be the
      // original params plus new params for any in global declarations

      final LinkedHashMap<String, SymParameter> theProofParams = new LinkedHashMap<String, SymParameter>();
      theProofParams.putAll(symbol.getTheParameters());

      ArrayList<SymGlobalAnnotation> theList = symbol.getTheInGlobalsSpec();

      if (!isSpec && !symbol.getTheInGlobalsBody().isEmpty()) {
        theList = symbol.getTheInGlobalsBody();
      }

      for (final SymGlobalAnnotation sga : theList) {

        final String uniqueParamName = SparkUtil
            .getProofFuncGlobalVar2ParamName(sga.getTheName());

        final SymParameter sp = new SymParameter();
        sp.setTheMode(Mode.IN);
        sp.setTheOptionalParent(symbol);
        sp.setTheName(uniqueParamName);
        sp.setTheTranslation(uniqueParamName);
        if (isSpec) {
          sp.setTheOptionalSpecSelection(sga.getTheSelection());
        } else {
          sp.setTheOptionalBodySelection(sga.getTheSelection());
        }
        //sp.setTheSelection(sga.getTheSelection());
        //sp.setTheOptionalSelection(sga.getTheOptionalSelection());
        if (sga.getTheObject() != null) {
          assert sga.getTheObject().getTheOptionalTypeSymDef() != null;
          sp.setTheOptionalTypeSymDef(sga.getTheObject()
              .getTheOptionalTypeSymDef());
        } else {
          this.g.symbolTable.addUnresolved(sp, sga.getTheName());
        }

        theProofParams.put(uniqueParamName, sp);
      }

      final String proofFuncSuffix = symbol.getTheOptionalParent()
          .getTheTranslation()
          + SparkUtil.PILAR_PACKAGE_SEPARATOR
          + symbol.getTheName();

      symbol.setTheTranslation(proofFuncSuffix);
      if (isSpec) {
        symbol.setTheOptionalImplicitSpecProofParameters(theProofParams);
      } else {
        symbol.setTheOptionalImplicitBodyProofParameters(theProofParams);
      }
    }

    /**
     * This is a helper function to get a sym package from a package name. Note
     * that this will create a sym package if visitingPackageDeclaration is
     * true, otherwise it will simply return the sym package which has already
     * been defined (i.e. by having visited the package declaration which has to
     * be done before visiting the package body) <br>
     * Therefore, it is expected that this function will only be called by
     * visitPackageDeclaration and visitPackageBody
     * 
     * @param thePackageName
     * @param visitingPackageDeclaration
     * @return
     */
    SymPackage helperGetSymPackage(final Node o, final Name thePackageName,
        final boolean visitingPackageDeclaration) {

      assert (o instanceof PackageDeclaration) || (o instanceof PackageBody);

      if (thePackageName instanceof IDName) {
        final String lowerName = ((IDName) thePackageName).getTheIDString()
            .toLowerCase();
        if (lowerName.equalsIgnoreCase(SparkUtil.SPARK_STANDARD_PACKAGE)) {
          return this.g.symbolTable.getTheSparkStandardPackage();
        }
      }

      final SymPackage theParent = this.g.getFirstSymPackageOnStack();

      SymPackage symPackage = null;

      if (theParent != null) {
        // child packages are only allowed at the library level so
        // if theParent is nonnull then this must be an embedded
        // package and the name must be a simple name

        assert this.g.embeddedPackageCount != 0;
        assert thePackageName instanceof IDName;
        final String pname = ((IDName) thePackageName).getTheIDString();

        if (visitingPackageDeclaration) {
          symPackage = this.g.symbolTable.addEmbeddedPackage(
              pname,
              theParent,
              o);
        } else {
          symPackage = theParent.getTheEmbeddedPackages().get(
              pname.toLowerCase());
        }
      } else {
        // we have to be at the library level
        assert this.g.embeddedPackageCount == 0;

        switch (thePackageName.getDescriptor()) {
          case SelectedComponent.DESCRIPTOR: {
            // oh, a child package.  The dotted names must start
            // with a root package and then a possible list of
            // child packages
            // e.g. rootP.child1.child2.child3
            final SelectedComponent sc = (SelectedComponent) thePackageName;
            final SymPackage e = getParentPackage(sc.getTheName());
            if (visitingPackageDeclaration) {
              symPackage = this.g.symbolTable.addChildPackage(
                  e,
                  sc.getTheIDString(),
                  o);
            } else {
              symPackage = e.getTheChildPackages().get(
                  sc.getTheIDString().toLowerCase());
            }
            break;
          }
          case IDName.DESCRIPTOR: {
            // just a boring old root package
            final String pname = ((IDName) thePackageName).getTheIDString();

            if (visitingPackageDeclaration) {
              symPackage = this.g.symbolTable.addRootPackage(pname, o);
            } else {
              symPackage = this.g.symbolTable.resolveBasePackage(pname);
            }
            break;
          }
          default:
            assert false;
        }
      }

      assert symPackage != null;
      return symPackage;
    }

    Pair<String, String> helperGetTheTypeNames(final Symbol theSymbol,
        final String origName) {
      String theTranslationName = "";
      String thePilarRecordName = "";
      {
        if (theSymbol instanceof SymCall) {
          thePilarRecordName = theSymbol.getTheName()
              + SparkUtil.METHOD_DEFINED_TYPE + origName;

          final SymPackage sp = (SymPackage) theSymbol.getTheOptionalParent();
          theTranslationName = sp.getTheTranslation()
              + SparkUtil.PILAR_PACKAGE_SEPARATOR + thePilarRecordName;
        } else {
          thePilarRecordName = origName;

          final SymPackage sp = (SymPackage) theSymbol;
          theTranslationName = sp.getTheTranslation()
              + SparkUtil.PILAR_PACKAGE_SEPARATOR + thePilarRecordName;
        }
      }
      assert !theTranslationName.equals("");
      assert !thePilarRecordName.equals("");

      theTranslationName = SymbolTable
          .returnPilarCompliantName(theTranslationName);
      thePilarRecordName = SymbolTable
          .returnPilarCompliantName(thePilarRecordName);

      return new Pair<String, String>(theTranslationName, thePilarRecordName);
    }

    void helperRecordDefinition(final RecordDefinition theSparkRecordDef,
        final SymRecordDef theParentRecordDef,
        final LinkedHashMap<String, SymRecordComponentDef> linkedHashMap) {

      for (final ComponentDeclaration c : theSparkRecordDef
          .getTheOptionalComponentDeclarations()) {
        for (final IDName id : c.getTheIDNames()) {

          final EvalResult res = getNameAndSymbol(id);
          final String varName = res.toString();

          final SymRecordComponentDef var = new SymRecordComponentDef();
          var.setTheName(varName);
          var.setTheTranslation(varName.toLowerCase());

          final EvalResult typeName = getNameAndSymbol(c.getTheName());
          final SymDef typeDef = (SymDef) typeName.getTheSymbol();
          assert typeDef != null;

          var.setTheTypeSymDef(typeDef);
          var.setTheParentRecordDef(theParentRecordDef);
          var.setTheSelection(c.getTheOptionalRegionSelection());
          assert c.getTheOptionalRegionSelection() != null;

          linkedHashMap.put(varName.toLowerCase(), var);
        }
      }
    }

    private LinkedHashSet<SymPackage> helperVisitInheritClause(
        final ArrayList<Name> theInheritClauses) {
      final LinkedHashSet<SymPackage> inheritedPackages = new LinkedHashSet<SymPackage>();
      if (theInheritClauses != null) {
        for (final Name inheritName : theInheritClauses) {

          final EvalResult inres = getNameAndSymbol(inheritName);
          assert inres.getTheSymbol() != null;

          final SymPackage inheritSymbol = (SymPackage) inres.getTheSymbol();
          inheritedPackages.add(inheritSymbol);
          //add the parent
          if (inheritSymbol.getTheOptionalParent() != null) {
            inheritedPackages.add((SymPackage) inheritSymbol
                .getTheOptionalParent());
          }
        }
      }
      return inheritedPackages;
    }

    @Override
    protected void visitDependencyClause(final DependencyClause o) {
      final SymProcedure p = (SymProcedure) this.g.symbolStack.peek();

      final ContainerDependencyRelation cdr = this.g.inPackageDeclaration ? p
          .getTheOptionalDependencyRelationSpec() : p
          .getTheOptionalDependencyRelationBody();
      final List<ContainerDependencyClause> theList = cdr
          .getTheDependencyClauses();
      final ContainerDependencyClause cdc = new ContainerDependencyClause();
      cdc.setTheDependencyClauseRegionSelection(o
          .getTheOptionalRegionSelection());
      theList.add(cdc);

      final ArrayList<SymDerivesAnnotationModel> sdaList = new ArrayList<SymDerivesAnnotationModel>();
      cdc.setTheDependencyClauseMembers(sdaList);

      for (final Name e : o.getTheExportedVariables()) {

        final SymDerivesAnnotation derives = new SymDerivesAnnotation();
        sdaList.add(derives);

        final SymSimpleAnnotation export = new SymSimpleAnnotation();
        export.setTheOptionalParent(p);

        final EvalResult er = getNameAndSymbol(e);
        if (er.getTheSymbol() == null) {
          export.setTheName(er.getTheTemplate().toString());
          this.g.symbolTable.addUnresolved(export, er.getTheTemplate()
              .toString());
        } else {
          export.setTheObject((SymObject) er.getTheSymbol());
        }

        export.setTheSelection(e.getTheOptionalRegionSelection());
        assert e.getTheOptionalRegionSelection() != null;

        derives.setTheOutVar(export);

        if (o.getTheOptionalImportedVariables() != null) {
          for (final Name i : o.getTheOptionalImportedVariables()) {
            final SymSimpleAnnotation imported = new SymSimpleAnnotation();
            imported.setTheOptionalParent(p);

            final EvalResult er2 = getNameAndSymbol(i);
            if (er2.getTheSymbol() == null) {
              imported.setTheName(er2.getTheTemplate().toString());
              this.g.symbolTable.addUnresolved(imported, er2.getTheTemplate()
                  .toString());
            } else {
              imported.setTheObject((SymObject) er2.getTheSymbol());
            }

            imported.setTheSelection(i.getTheOptionalRegionSelection());
            assert i.getTheOptionalRegionSelection() != null;

            derives.getTheInVars().add(imported);
          }
        }

        if (o.getTheImportStarFlag()) {
          derives.getTheInVars().add(export);
        }
      }
    }

    @Override
    protected void visitDependencyRelation(final DependencyRelation o) {
      final SymProcedure p = (SymProcedure) this.g.symbolStack.peek();

      final RegionSelection rsDP = o.getTheOptionalRegionSelection();
      assert rsDP != null;

      final ContainerDependencyRelation cdr = new ContainerDependencyRelation();
      cdr.setTheDependencyRelationRegionSelection(rsDP);
      cdr.setTheDependencyClauses(new ArrayList<ContainerDependencyClause>());

      if (this.g.inPackageDeclaration) {
        p.setTheOptionalDependencyRelationSpec(cdr);
      } else {
        p.setTheOptionalDependencyRelationBody(cdr);
      }

      if (o.getTheOptionalDependencyClauses() != null) {
        for (final DependencyClause dc : o.getTheOptionalDependencyClauses()) {
          visit(dc);
        }
      }

      if (o.getTheOptionalNullDependencyVariables() != null) {
        final List<ContainerDependencyClause> theList = cdr
            .getTheDependencyClauses();
        final ContainerDependencyClause cdc = new ContainerDependencyClause();
        cdc.setTheDependencyClauseRegionSelection(o
            .getTheOptionalRegionSelection());
        theList.add(cdc);

        final ArrayList<SymDerivesAnnotationModel> sdaList = new ArrayList<SymDerivesAnnotationModel>();
        cdc.setTheDependencyClauseMembers(sdaList);

        final SymNullDerivesAnnotation snda = new SymNullDerivesAnnotation();
        final ArrayList<SymSimpleAnnotation> daList = new ArrayList<SymSimpleAnnotation>();
        snda.setTheInVars(daList);

        sdaList.add(snda);

        for (final Name n : o.getTheOptionalNullDependencyVariables()) {

          final SymSimpleAnnotation imported = new SymSimpleAnnotation();
          imported.setTheOptionalParent(p);

          final EvalResult er2 = getNameAndSymbol(n);
          if (er2.getTheSymbol() == null) {
            imported.setTheName(er2.getTheTemplate().toString());
            this.g.symbolTable.addUnresolved(imported, er2.getTheTemplate()
                .toString());
          } else {
            imported.setTheObject((SymObject) er2.getTheSymbol());
          }

          imported.setTheSelection(n.getTheOptionalRegionSelection());
          assert n.getTheOptionalRegionSelection() != null;

          daList.add(imported);
        }
      }
    }

    @Override
    protected void visitEmbeddedPackageDeclaration(
        final EmbeddedPackageDeclaration o) {
      // Set the package imports
      this.g.embeddedPackageCount++;
      this.g.withPackages.addAll(this.g.getFirstSymPackageOnStack()
          .getTheSpecWithPackages());
      this.g.withPackages.addAll(this.g.getFirstSymPackageOnStack()
          .getTheBodyWithPackages());
      super.visitEmbeddedPackageDeclaration(o);
      this.g.embeddedPackageCount--;
    }

    @Override
    protected void visitFullTypeDeclaration(final FullTypeDeclaration o) {
      final String origName = o.getTheIDString();

      final SymPackageElement theSymbol = (SymPackageElement) this.g.symbolStack
          .peek();

      final Pair<String, String> theNames = helperGetTheTypeNames(
          theSymbol,
          origName);
      final String theTranslationName = theNames.getE0();
      final String thePilarRecordName = theNames.getE1();

      SymDef typeDefinition = null;

      switch (o.getTheTypeDefinition().getDescriptor()) {
        case RecordTypeDefinition.DESCRIPTOR: {
          final RecordTypeDefinition recordType = (RecordTypeDefinition) o
              .getTheTypeDefinition();
          final SymRecordDef recordDefinition = new SymRecordDef();
          recordDefinition.setIsRecordTagged(recordType.getTheTaggedFlag());

          recordDefinition
              .setTheElements(new LinkedHashMap<String, SymRecordComponentDef>());

          helperRecordDefinition(
              recordType.getTheRecordDefinition(),
              recordDefinition,
              recordDefinition.getTheElements());

          typeDefinition = recordDefinition;
          break;
        }
        case RecordTypeExtension.DESCRIPTOR: {
          final RecordTypeExtension rte = (RecordTypeExtension) o
              .getTheTypeDefinition();

          final EvalResult er = getNameAndSymbol(rte.getTheSubtypeMark());
          final SymDef typeDef = (SymDef) er.getTheSymbol();
          assert typeDef != null;

          final SymRecordDef parentRecordDef = (SymRecordDef) typeDef;
          assert parentRecordDef.getIsRecordTagged();

          final SymRecordDef extensionRecordDef = new SymRecordDef();

          extensionRecordDef.setTheOptionalParentDef(parentRecordDef);

          extensionRecordDef
              .setTheElements(new LinkedHashMap<String, SymRecordComponentDef>());
          extensionRecordDef.getTheElements().putAll(
              parentRecordDef.getTheElements());

          helperRecordDefinition(
              rte.getTheRecordDefinition(),
              extensionRecordDef,
              extensionRecordDef.getTheElements());

          typeDefinition = extensionRecordDef;

          break;
        }
        case EnumerationTypeDefinition.DESCRIPTOR: {
          final EnumerationTypeDefinition enumType = (EnumerationTypeDefinition) o
              .getTheTypeDefinition();
          final SymEnumerationDef enumDefinition = new SymEnumerationDef();
          for (final IDName id : enumType.getTheIDNames()) {
            final SymEnumElementDef elementDefinition = new SymEnumElementDef();
            elementDefinition.setTheParentEnumDef(enumDefinition);

            final EvalResult er = getNameAndSymbol(id);
            final String theEnumElement = er.getTheTemplate().toString();

            elementDefinition.setTheName(theEnumElement);
            final String compliantElem = SymbolTable
                .returnPilarCompliantName(theEnumElement);
            elementDefinition.setTheTranslation(theTranslationName + "."
                + compliantElem);
            enumDefinition.getTheElements().add(elementDefinition);
          }
          typeDefinition = enumDefinition;
          break;
        }
        case UnconstrainedArrayDefinition.DESCRIPTOR: {
          final UnconstrainedArrayDefinition arrayType = (UnconstrainedArrayDefinition) o
              .getTheTypeDefinition();
          final SymArrayDef arrayDefinition = new SymArrayDef();
          arrayDefinition.setIsConstrained(false);

          final EvalResult er = getNameAndSymbol(arrayType
              .getTheComponentName());
          er.getTheTemplate().toString();
          SymDef sd = (SymDef) er.getTheSymbol();
          assert sd != null;

          arrayDefinition.setTheComponentSubtypeDef(sd);

          for (final Name indexName : arrayType.getTheIndexSubTypeNames()) {
            final EvalResult er3 = getNameAndSymbol(indexName);
            sd = (SymDef) er3.getTheSymbol();
            assert sd != null;

            arrayDefinition.getTheIndexSubtypeDefs().add(sd);

            arrayDefinition
                .setTheDimensions(arrayDefinition.getTheDimensions() + 1);
          }
          typeDefinition = arrayDefinition;
          break;
        }
        case ConstrainedArrayDefinition.DESCRIPTOR: {
          final ConstrainedArrayDefinition arrayType = (ConstrainedArrayDefinition) o
              .getTheTypeDefinition();
          final SymArrayDef arrayDefinition = new SymArrayDef();
          arrayDefinition.setIsConstrained(true);

          final EvalResult er4 = getNameAndSymbol(arrayType
              .getTheComponentName());
          SymDef sd = (SymDef) er4.getTheSymbol();
          assert sd != null;

          arrayDefinition.setTheComponentSubtypeDef(sd);

          for (final Name indexName : arrayType.getTheDiscreteSubTypeNames()) {

            final EvalResult er5 = getNameAndSymbol(indexName);
            sd = (SymDef) er5.getTheSymbol();
            assert sd != null;

            arrayDefinition.getTheIndexSubtypeDefs().add(sd);

            arrayDefinition
                .setTheDimensions(arrayDefinition.getTheDimensions() + 1);
          }
          typeDefinition = arrayDefinition;
          break;
        }
        case SignedIntegerTypeDefinition.DESCRIPTOR: {
          final SignedIntegerTypeDefinition integerType = (SignedIntegerTypeDefinition) o
              .getTheTypeDefinition();
          final SymSignedIntegerDef integerDefinition = new SymSignedIntegerDef();
          integerDefinition
              .setTheHighRangeExp(integerType.getTheHighRangeExp());
          integerDefinition.setTheLowRangeExp(integerType.getTheLowRangeExp());
          typeDefinition = integerDefinition;
          break;
        }
        case ModularTypeDefinition.DESCRIPTOR: {
          final ModularTypeDefinition modType = (ModularTypeDefinition) o
              .getTheTypeDefinition();
          final SymModDef modularDefinition = new SymModDef();
          modularDefinition.setTheExp(modType.getTheExp());
          typeDefinition = modularDefinition;
          break;
        }
        case OrdinaryFixedPointDefinition.DESCRIPTOR: {
          final OrdinaryFixedPointDefinition fixedPointType = new OrdinaryFixedPointDefinition();
          final SymFixedDef fixedDefinition = new SymFixedDef();
          fixedDefinition.setTheExp(fixedPointType.getTheExp());
          fixedDefinition.setTheHighRangeExp(fixedPointType
              .getTheHighRangeExp());
          fixedDefinition.setTheLowRangeExp(fixedPointType.getTheLowRangeExp());
          typeDefinition = fixedDefinition;
          break;
        }
        case FloatingPointDefinition.DESCRIPTOR: {
          final FloatingPointDefinition floatingPointType = new FloatingPointDefinition();
          final SymFloatingDef floatingDefinition = new SymFloatingDef();
          floatingDefinition.setTheExp(floatingPointType.getTheExp());
          if (floatingPointType.getTheOptionalHighRangeExp() != null) {
            floatingDefinition.setTheOptionalHighRangeExp(floatingPointType
                .getTheOptionalHighRangeExp());
          }
          if (floatingPointType.getTheOptionalLowRangeExp() != null) {
            floatingDefinition.setTheOptionalLowRangeExp(floatingPointType
                .getTheOptionalLowRangeExp());
          }
          typeDefinition = floatingDefinition;
          break;
        }
        default:
          assert false;
      }

      assert typeDefinition != null;

      final SymOrigin theOrigin = this.g.inPackageDeclaration ? (this.g.inPrivatePart ? SymOrigin.PACKAGE_SPEC_PRIVATE
          : SymOrigin.PACKAGE_SPEC_PUBLIC)
          : (theSymbol instanceof SymCall ? SymOrigin.METHOD
              : SymOrigin.PACKAGE_BODY);
      typeDefinition.setTheOrigin(theOrigin);

      assert o.getTheOptionalRegionSelection() != null;
      typeDefinition.setTheSelection(o.getTheOptionalRegionSelection());
      //typeDefinition.setTheSelection(SelectionCreation.create(o,
      //    this.g.filepath));

      typeDefinition.setTheName(thePilarRecordName);
      typeDefinition.setTheTranslation(theTranslationName);

      if (theSymbol.getTheDefinitions().get(origName.toLowerCase()) != null) {
        // refining a type.  This will happen when we have something like
        //     type Matrix is limited private;
        assert theSymbol instanceof SymPackage;

        final SymPartialDef partialDef = (SymPartialDef) theSymbol
            .getTheDefinitions().get(origName.toLowerCase());
        partialDef.setTheTranslation(typeDefinition.getTheTranslation());
        partialDef.setTheElaborationSymDef(typeDefinition);

        typeDefinition.setTheOrigin(partialDef.getTheOrigin());

        typeDefinition.setIsPrivateTypeDeclaration(true);
        typeDefinition.setTheOptionalPrivateTypeDeclarationSelection(partialDef
            .getTheSelection());

        typeDefinition.setIsTagged(partialDef.getIsTagged());
        typeDefinition.setIsLimited(partialDef.getIsLimited());

        // remove the partial def since it's now embedded in the typeDefinition.
        // NOTE: that we could have sym objects that point to the partial def
        //       so we aren't really getting rid of it.  We could update those
        //       pointers so they point to typeDefinition, but for now this 
        //       does not seem necessary
        theSymbol.getTheDefinitions().remove(origName.toLowerCase());
      } else {
        typeDefinition.setIsPrivateTypeDeclaration(false);
      }

      theSymbol.getTheDefinitions().put(origName.toLowerCase(), typeDefinition);
    }

    @Override
    protected void visitFunctionSubProgramBody(final FunctionSubProgramBody o) {
      final String functionName = o.getTheFunctionSpecification()
          .getTheIDString();

      final SymPackageElement theContainer = (SymPackageElement) this.g.symbolStack
          .peek();

      final EvalResult er = getNameAndSymbol(o.getTheFunctionSpecification()
          .getTheName());
      final SymDef sd = (SymDef) er.getTheSymbol();
      assert sd != null;

      final SymAdaFunction symbol = (SymAdaFunction) this.g.symbolTable
          .addFunction(
              theContainer,
              functionName,
              sd,
              false,
              this.g.inPackageDeclaration,
              o);
      //symbol.setTheOptionalSelection(SelectionCreation.create(o,
      //    this.g.filepath));

      assert o.getTheOptionalRegionSelection() != null;
      symbol.setTheSelection(o.getTheOptionalRegionSelection());

      this.g.symbolStack.push(symbol);
      super.visitFunctionSubProgramBody(o);
      this.g.symbolStack.pop();

      helperCreateImplicitProofFunction(symbol, false);

    }

    @Override
    protected void visitFunctionSubProgramDeclaration(
        final FunctionSubProgramDeclaration o) {
      final String functionName = o.getTheFunctionSpecification()
          .getTheIDString();

      final EvalResult er = getNameAndSymbol(o.getTheFunctionSpecification()
          .getTheName());
      final SymDef sd = (SymDef) er.getTheSymbol();
      assert sd != null;

      final SymAdaFunction symbol = (SymAdaFunction) this.g.symbolTable
          .addFunction(
              (SymPackage) this.g.symbolStack.peek(),
              functionName,
              sd,
              false,
              this.g.inPackageDeclaration,
              o);
      //symbol.setTheSelection(SelectionCreation.create(o, this.g.filepath));
      assert o.getTheOptionalRegionSelection() != null;
      symbol.setTheSelection(o.getTheOptionalRegionSelection());

      symbol.setIsPrivate(this.g.inPrivatePart);

      this.g.symbolStack.push(symbol);
      super.visitFunctionSubProgramDeclaration(o);
      this.g.symbolStack.pop();

      helperCreateImplicitProofFunction(symbol, true);
    }

    @Override
    protected void visitGlobalDeclaration(final GlobalDeclaration o) {
      final SymCall c = (SymCall) this.g.symbolStack.peek();

      for (final Name name : o.getTheNames()) {
        final SymGlobalAnnotation a = new SymGlobalAnnotation();
        a.setTheOptionalParent(c);

        final EvalResult er = getNameAndSymbol(name);
        a.setTheName(er.toString());

        // If we're in a spec then we may not know the symbol that is being 
        // referred to until we parse out the body.  This will be resolved
        // when we process the object declarations and can be done by just 
        // looking at the simple names
        if (er.getTheSymbol() != null) {
          a.setTheObject((SymObject) er.getTheSymbol());
        } else {
          this.g.symbolTable.addUnresolved(a, er.toString());
        }

        assert name.getTheOptionalRegionSelection() != null;
        a.setTheSelection(name.getTheOptionalRegionSelection());

        if (this.g.inPackageDeclaration) {
          c.getTheOptionalDeclaredSpecGlobals().add(a);
        } else {
          c.getTheOptionalDeclaredBodyGlobals().add(a);
        }
        a.setTheMode(o.getTheMode());

        if (o.getTheMode() == GlobalMode.IN) {
          // c can be a func or a procedure
          if (this.g.inPackageDeclaration) {
            c.getTheInGlobalsSpec().add(a);
          } else {
            c.getTheInGlobalsBody().add(a);
          }
        } else if (o.getTheMode() == GlobalMode.OUT) {
          final SymProcedure p = (SymProcedure) this.g.symbolStack.peek();
          if (this.g.inPackageDeclaration) {
            p.getTheOutGlobalsSpec().add(a);
          } else {
            p.getTheOutGlobalsBody().add(a);
          }
        } else if (o.getTheMode() == GlobalMode.IN_OUT) {
          final SymProcedure p = (SymProcedure) this.g.symbolStack.peek();
          if (this.g.inPackageDeclaration) {
            p.getTheInGlobalsSpec().add(a);
            p.getTheOutGlobalsSpec().add(a);
          } else {
            p.getTheInGlobalsBody().add(a);
            p.getTheOutGlobalsBody().add(a);
          }
        } else if ((o.getTheMode() == GlobalMode.NONE)
            && (c instanceof SymFunction)) {
          a.setTheMode(GlobalMode.IN);
          if (this.g.inPackageDeclaration) {
            c.getTheInGlobalsSpec().add(a);
          } else {
            c.getTheInGlobalsBody().add(a);
          }
        } else {
          this.g.emitErrorMessage(o, SparkUtil.COMPILER_NAME
              + " does not allow for unmoded global annotations in procedures");
        }
      }
    }

    @Override
    protected void visitGlobalDefinition(final GlobalDefinition o) {
      final SymCall c = (SymCall) this.g.symbolStack.peek();
      if (this.g.inPackageDeclaration) {
        c.setTheOptionalDeclaredSpecGlobalsSelection(o
            .getTheOptionalRegionSelection());
      } else {
        c.setTheOptionalDeclaredBodyGlobalsSelection(o
            .getTheOptionalRegionSelection());
      }

      for (final GlobalDeclaration gd : o.getTheGlobalDeclarations()) {
        visit(gd);
      }
    }

    @Override
    protected void visitIDName(final IDName o) {
      if (this.g.visitName) {
        final StringTemplate st = this.g.stg.getInstanceOf("same");
        final String id = o.getTheIDString();
        st.setAttribute("ID", id);
        Symbol theDef = null;
        if (!this.g.symbolStack.isEmpty()) {
          theDef = this.g.symbolTable.resolveSymbol(
              this.g.symbolStack.peek(),
              id);
        } else {
          // this has to be a package
          theDef = this.g.symbolTable.resolveBasePackage(id);
        }
        this.g.pushResult(st, theDef);
      }
    }

    @Override
    protected void visitMainProgram(final MainProgram o) {

      final SymPackage symPackage = this.g.symbolTable.addRootPackage(
          SparkUtil.SPARK_MAIN_PROGRAM,
          o);
      symPackage.setTheBodyUseTypeClauses(this.g.theUseTypeClauses);
      symPackage.setTheBodyWithPackages(this.g.withPackages);

      symPackage.setBodyFile(this.g.filepath);

      /************************************************************************
       * INHERIT NAMES
       ***********************************************************************/
      final LinkedHashSet<SymPackage> inheritedPackages = helperVisitInheritClause(o
          .getTheOptionalInheritClauses());
      symPackage.setTheInheritedPackages(inheritedPackages);

      /*
      symPackage.setTheOptionalSelection(SelectionCreation.create(o,
          this.g.filepath));
      symPackage.setTheSelection(SelectionCreation.create(o, this.g.filepath));
      */
      assert o.getTheOptionalRegionSelection() != null;
      symPackage.setThePackageSpecSelection(o.getTheOptionalRegionSelection());

      this.g.symbolStack.push(symPackage);

      visit(o.getTheSubProgramBody());

      this.g.symbolStack.pop();

      this.g.symbolTable.setTheOptionalMainProgram(symPackage);
    }

    @Override
    protected void visitNumberDeclaration(final NumberDeclaration o) {

      SymOrigin theOrigin = SymOrigin.PACKAGE_BODY;
      if (this.g.inPackageDeclaration) {
        theOrigin = SymOrigin.PACKAGE_SPEC_PUBLIC;
      }
      if (this.g.inPrivatePart) {
        theOrigin = SymOrigin.PACKAGE_SPEC_PRIVATE;
      }

      // TODO: can't type anything here since the constant can be assigned to
      // an arbitrary expression e.g. 1 + 3 or 1.0 + 3.0

      for (final IDName name : o.getTheIDNames()) {

        final EvalResult er = getNameAndSymbol(name);
        final String var = er.toString();

        final Symbol symbol = this.g.symbolStack.peek();
        SymObject object = null;
        switch (symbol.getDescriptor()) {
          case SymPackage.DESCRIPTOR: {
            object = this.g.symbolTable.addConstant(
                (SymPackage) symbol,
                var,
                null,
                theOrigin,
                o.getTheExp(),
                name);
            break;
          }
          case SymProcedure.DESCRIPTOR:
          case SymAdaFunction.DESCRIPTOR:
          case SymProofFunction.DESCRIPTOR: {
            object = this.g.symbolTable.addVariable(
                (SymCall) symbol,
                var,
                null,
                SymOrigin.METHOD,
                SymObjectKind.PARAMETER,
                o.getTheExp(),
                o);
            break;
          }
          default:
            assert false;
        }
        object.setIsPrivate(this.g.inPrivatePart);
        //object.setTheSelection(SelectionCreation.create(name, this.g.filepath));
        assert name.getTheOptionalRegionSelection() != null;
        object.setTheSelection(name.getTheOptionalRegionSelection());
      }
    }

    @Override
    protected void visitObjectDeclaration(final ObjectDeclaration o) {
      final boolean isConstant = o.getTheConstantFlag();

      final SymOrigin theOrigin = this.g.inPackageDeclaration ? (this.g.inPrivatePart ? SymOrigin.PACKAGE_SPEC_PRIVATE
          : SymOrigin.PACKAGE_SPEC_PUBLIC)
          : (this.g.symbolStack.peek() instanceof SymCall ? SymOrigin.METHOD
              : SymOrigin.PACKAGE_BODY);

      for (final IDName theVarName : o.getTheDefiningIdentifierList()) {

        final EvalResult er = getNameAndSymbol(theVarName);
        final String varName = er.toString();

        final SymPackageElement topStackSymbol = (SymPackageElement) this.g.symbolStack
            .peek();
        SymObject object = null;

        final EvalResult er2 = getNameAndSymbol(o.getTheSubtypeMark());
        er2.toString();

        final SymDef theType = (SymDef) er2.getTheSymbol();
        assert theType != null;

        if (isConstant) {
          SymOrigin conOrigin = theOrigin;
          if ((conOrigin == SymOrigin.PACKAGE_SPEC_PUBLIC)
              && (o.getTheOptionalInitializingExp() == null)) {
            conOrigin = SymOrigin.PACKAGE_SPEC_PUBLIC_DEFERRED;
          }
          object = this.g.symbolTable.addConstant(
              topStackSymbol,
              varName,
              theType,
              conOrigin,
              o.getTheOptionalInitializingExp(),
              theVarName);
        } else {
          object = this.g.symbolTable.addVariable(
              topStackSymbol,
              varName,
              theType,
              theOrigin,
              SymObjectKind.CONCRETE_VARIABLE,
              o.getTheOptionalInitializingExp(),
              theVarName);
        }
        assert object != null;
        //object.setTheSelection(SelectionCreation.create(theVarName,
        //    this.g.filepath));

        assert theVarName.getTheOptionalRegionSelection() != null;
        object.setTheSelection(theVarName.getTheOptionalRegionSelection());

        // see if anyone is waiting for a resolution of the simplename.  This 
        // should really only occur for own variables or global declarations 
        // that refer to a global var which is declared in the body portion
        this.g.symbolTable.removeUnresolved(object);
      }
    }

    @Override
    protected void visitPackageAnnotation(final PackageAnnotation o) {

      final SymPackage p = (SymPackage) this.g.symbolStack.peek();
      p.setSpecificationFile(this.g.filepath);

      // all the own variables the package declares
      final ArrayList<OwnVariableSpecification> own = o
          .getTheOptionalOwnVariables();
      if (own != null) {
        // the region selection for the entire own statement including the
        // 'own' keyword and the ';'
        final RegionSelection rsOwnRS = o
            .getTheOptionalOwnVariablesRegionSelection();
        assert rsOwnRS != null;

        final ContainerOwnStatement cos = new ContainerOwnStatement();
        p.setTheOptionalOwnStatement(cos);
        cos.setTheOwnStatementSelection(rsOwnRS);
        cos.setTheOwnClauses(new ArrayList<ContainerOwnClause>());

        for (final OwnVariableSpecification ownVarSpec : own) {

          // the regions selection for a given own clause which include the
          // list of variables, the optional type name, and the ';'
          final RegionSelection rsOwnSpecRS = ownVarSpec
              .getTheOptionalRegionSelection();
          assert rsOwnSpecRS != null;

          final ContainerOwnClause coc = new ContainerOwnClause();
          cos.getTheOwnClauses().add(coc);
          coc.setTheOwnClauseSelection(rsOwnSpecRS);
          coc.setTheOwnVariables(new ArrayList<SymOwnVariableAnnotation>());

          for (final OwnVariable ownVar : ownVarSpec.getTheOwnVariables()) {
            final SymOwnVariableAnnotation sva = new SymOwnVariableAnnotation();

            assert ownVar.getTheOptionalRegionSelection() != null;
            sva.setTheSelection(ownVar.getTheOptionalRegionSelection());

            coc.getTheOwnVariables().add(sva);

            sva.setTheName(ownVar.getTheIDString());
            sva.setTheOptionalParent(p);
            sva.setTheMode(ownVar.getTheMode());

            if (ownVarSpec.getTheOptionalSubtypeMark() != null) {
              // User has supplied a type so each own var will have this type.
              // We process the own variables after we process the object/type
              // declarations so the type must be in the symbol table already

              final EvalResult theOwnVarType = getNameAndSymbol(ownVarSpec
                  .getTheOptionalSubtypeMark());
              assert theOwnVarType.getTheSymbol() != null;

              sva.setTheOptionalType((SymDef) theOwnVarType.getTheSymbol());
              coc.setTheOptionalType((SymDef) theOwnVarType.getTheSymbol());
            }
            //sva.setTheSelection(SelectionCreation.create(ownVar,
            //    this.g.filepath));

            // if the own variable is referring to a symobject declared in the
            // spec file then we will have a handle on it by this point since
            // we process the declared objects in the spec before we process
            // the 
            final SymObject theAdaObject = (SymObject) this.g.symbolTable
                .resolveSymbol(p, ownVar.getTheIDString());

            if (theAdaObject == null) {
              // we have to assume the own variable is abstract until we find  
              // out differently (e.g. we find the own variable is referring to
              // an actual ada variable in the body
              sva.setTheOwnCategory(SymOwnCategory.ABSTRACT);

              this.g.symbolTable.addGlobalVarForOwnVar(p, sva, ownVar);

              // add the own var to the unresolved map.  It must be the case that
              // we can resolve this (or call it abstract) before we get to the
              // translation phase
              this.g.symbolTable.addUnresolved(sva, sva.getTheName());
            } else {
              sva.setTheOwnCategory(SymOwnCategory.CONCRETE);
              sva.setTheObject(theAdaObject);
            }

            p.getTheOwnVariables().add(sva);
          }
        }
      }

      // own vars the package initializes
      final ArrayList<OwnVariable> init = o.getTheOptionalInitializeVariables();
      if (init != null) {
        for (final OwnVariable ownVar : init) {
          final SymSimpleAnnotation ssa = new SymSimpleAnnotation();
          ssa.setTheName(ownVar.getTheIDString());
          /*
          ssa
              .setTheSelection(SelectionCreation
                  .create(ownVar, this.g.filepath));
          */
          assert ownVar.getTheOptionalRegionSelection() != null;
          ssa.setTheSelection(ownVar.getTheOptionalRegionSelection());

          p.getTheInitializeVariables().add(ssa);
        }
      }
    }

    @Override
    protected void visitPackageBody(final PackageBody o) {

      final SymPackage symPackage = helperGetSymPackage(
          o,
          o.getTheName(),
          false);
      assert symPackage != null;

      this.g.addNodeToSymbolMapping(o, symPackage);

      symPackage.setBodyFile(this.g.filepath);
      /*
      symPackage.setTheOptionalSelection(SelectionCreation.create(o,
          this.g.filepath));
      */
      assert o.getTheOptionalRegionSelection() != null;
      symPackage.setThePackageBodySelection(o.getTheOptionalRegionSelection());

      /************************************************************************
       * WITH and USE TYPE
       ***********************************************************************/
      symPackage.setTheBodyWithPackages(this.g.withPackages);
      symPackage.setTheBodyUseTypeClauses(this.g.theUseTypeClauses);

      this.g.symbolStack.push(symPackage);
      this.g.embeddedPackageCount++;
      {
        visitPackageImplementation(o.getThePackageImplementation());

        if (!o.getTheRefinementClauses().isEmpty()) {
          final ContainerRefinementDefinition crd = new ContainerRefinementDefinition();
          symPackage.setTheOptionalRefinementDefinition(crd);

          final RegionSelection refRS = o
              .getTheOptionalRefinementClausesRegionSelection();
          assert refRS != null;
          crd.setTheRefinementDefinitionSelection(refRS);

          crd.setTheRefinementClauses(new ArrayList<ContainerRefinementClause>());

          for (final RefinementClause e0 : o.getTheRefinementClauses()) {
            visitRefinementClause(e0);
          }
        }
      }
      this.g.embeddedPackageCount--;
      this.g.symbolStack.pop();
    }

    @Override
    protected void visitPackageDeclaration(final PackageDeclaration o) {

      final SymPackage symPackage = helperGetSymPackage(o, o
          .getThePackageSpecification().getTheName(), true);
      assert symPackage != null;

      // handle the standard package separately
      if (symPackage.getTheTranslation().equalsIgnoreCase(
          SparkUtil.SPARK_STANDARD_PACKAGE)) {
        this.g.symbolTable.addNodeToSymbolMapping(o, symPackage);

        this.g.symbolStack.push(symPackage);

        final PackageSpecification ps = o.getThePackageSpecification();
        if (ps.getTheOptionalVisiblePartDeclarativePartMember() != null) {

          final int size = symPackage.getTheDefinitions().keySet().size();
          final String[] origOrder = symPackage.getTheDefinitions().keySet()
              .toArray(new String[size]);

          for (final DeclarativePartMember dpm : ps
              .getTheOptionalVisiblePartDeclarativePartMember()) {
            // for now assume that we'll only declare full type declarations in
            // the standard package
            final FullTypeDeclaration ftd = (FullTypeDeclaration) ((BasicDeclarationBasicDeclarativeItem) dpm)
                .getTheBasicDeclaration();

            final String lowerName = ftd.getTheIDString().toLowerCase();

            // further assume that we are only refining a basic spark type
            final SymScalarDef origDef = (SymScalarDef) symPackage
                .getTheDefinitions().get(lowerName);
            assert origDef != null;

            symPackage.getTheDefinitions().remove(lowerName);

            visit(ftd);

            final SymScalarDef newDef = (SymScalarDef) symPackage
                .getTheDefinitions().get(lowerName);
            newDef.setTheOptionalParent(origDef.getTheOptionalParent());
            newDef.setTheOrigin(origDef.getTheOrigin());
            newDef.setTheTranslation(origDef.getTheTranslation());
          }

          // YUCK, by removing the default definition of the standard package
          // defs we change their order which will cause the regression tests
          // to fail, so instead we'll use this hack for now
          final LinkedHashMap<String, SymDef> copyBack = new LinkedHashMap<String, SymDef>(
              size);
          for (final String s : origOrder) {
            copyBack.put(s, symPackage.getTheDefinitions().get(s));
          }
          symPackage.setTheDefinitions(copyBack);
        }

        this.g.symbolStack.pop();
        return;
      }

      symPackage.setIsPrivate(o.getThePrivateFlag());

      /************************************************************************
       * WITH and USE TYPES
       ***********************************************************************/
      symPackage.setTheSpecWithPackages(this.g.withPackages);
      symPackage.setTheSpecUseTypeClauses(this.g.theUseTypeClauses);

      /************************************************************************
       * INHERIT NAMES
       ***********************************************************************/
      final LinkedHashSet<SymPackage> inheritedPackages = helperVisitInheritClause(o
          .getTheOptionalInheritClauses());
      symPackage.setTheInheritedPackages(inheritedPackages);

      //symPackage.setTheSelection(SelectionCreation.create(o, this.g.filepath));
      assert o.getTheOptionalRegionSelection() != null;
      symPackage.setThePackageSpecSelection(o.getTheOptionalRegionSelection());

      this.g.withPackages = new LinkedHashSet<SymPackage>();
      this.g.theUseTypeClauses = new LinkedHashSet<SymDef>();
      this.g.symbolStack.push(symPackage);
      this.g.inPackageDeclaration = true;
      {
        visit(o.getThePackageSpecification());
      }
      this.g.inPackageDeclaration = false;
      this.g.symbolStack.pop();
    }

    @Override
    protected void visitPackageSpecification(final PackageSpecification o) {
      if (o.getTheOptionalVisiblePartDeclaration() != null) {
        visit(o.getTheOptionalVisiblePartDeclaration());
      }

      if (o.getTheOptionalVisiblePartDeclarativePartMember() != null) {
        visit(o.getTheOptionalVisiblePartDeclarativePartMember());
      }

      this.g.inPrivatePart = true;
      {
        if (o.getTheOptionalPrivatePartDeclaration() != null) {
          visit(o.getTheOptionalPrivatePartDeclaration());
        }
        if (o.getTheOptionalPrivatePartDeclarativePartMember() != null) {
          visit(o.getTheOptionalPrivatePartDeclarativePartMember());
        }
      }
      this.g.inPrivatePart = false;

      visit(o.getThePackageAnnotation());
    }

    @Override
    protected void visitParameterSpecification(final ParameterSpecification o) {

      this.g.symbolStack.peek();

      final Symbol symbol = this.g.symbolStack.peek();
      for (final IDName name : o.getTheParameterNames()) {
        final EvalResult er = getNameAndSymbol(name);
        final String id = er.toString();

        SymParameter parameter = new SymParameter();
        final EvalResult er2 = getNameAndSymbol(o.getTheSubtypeMark());

        final SymDef sd = (SymDef) er2.getTheSymbol();
        assert sd != null;

        Mode theMode = o.getTheMode();

        if (theMode == Mode.NONE) {
          // "the mode of a parameter can be omitted and is taken to be in by
          // default" (Barnes p39)
          theMode = Mode.IN;
        }

        parameter = this.g.symbolTable.addParameter(
            (SymCall) symbol,
            id,
            sd,
            theMode,
            name);

        assert o.getTheOptionalRegionSelection() != null;

        if (this.g.inPackageDeclaration) {
          parameter.setTheOptionalSpecSelection(name
              .getTheOptionalRegionSelection());
        } else {
          parameter.setTheOptionalBodySelection(name
              .getTheOptionalRegionSelection());
        }
      }
    }

    @Override
    protected void visitPostcondition(final Postcondition o) {
    }

    @Override
    protected void visitPrecondition(final Precondition o) {
    }

    @Override
    protected void visitPrivateExtensionDeclaration(
        final PrivateExtensionDeclaration o) {
      o.getTheSubTypeIndication();
      //TODO: PrivateExtensionDeclaration
      System.err.println(SymbolTableBuilder.CLASS_NAME
          + "SubTypeIndication not handled");
      assert false;
    }

    @Override
    protected void visitPrivateTypeDeclaration(final PrivateTypeDeclaration o) {
      final SymPartialDef partialType = new SymPartialDef();
      final SymPackage p = (SymPackage) this.g.symbolStack.peek();
      partialType.setTheOrigin(SymOrigin.PACKAGE_SPEC_PUBLIC);
      //partialType.setTheSelection(SelectionCreation.create(o, this.g.filepath));

      assert o.getTheOptionalRegionSelection() != null;
      partialType.setTheSelection(o.getTheOptionalRegionSelection());

      final String typeName = o.getTheIDString();
      partialType.setTheName(typeName);
      partialType.setIsTagged(o.getTheTaggedFlag());
      partialType.setIsLimited(o.getTheLimitedFlag());
      p.getTheDefinitions().put(typeName.toLowerCase(), partialType);
    }

    @Override
    protected void visitProcedureSubProgramBody(final ProcedureSubProgramBody o) {
      final String procName = o.getTheProcedureSpecification().getTheIDString();
      final SymProcedure symbol = this.g.symbolTable.addProcedure(
          (SymPackageElement) this.g.symbolStack.peek(),
          procName,
          o);

      assert o.getTheOptionalRegionSelection() != null;
      symbol.setTheSelection(o.getTheOptionalRegionSelection());

      this.g.symbolStack.push(symbol);
      {
        visit(o.getTheOptionalProcedureAnnotation());
        visit(o.getTheProcedureSpecification());
        visit(o.getTheSubProgramImplementation());
        //super.visitProcedureSubProgramBody(o);
      }
      this.g.symbolStack.pop();
    }

    @Override
    protected void visitProcedureSubProgramDeclaration(
        final ProcedureSubProgramDeclaration o) {
      final String procName = o.getTheProcedureSpecification().getTheIDString();
      final SymProcedure symbol = this.g.symbolTable.addProcedure(
          (SymPackage) this.g.symbolStack.peek(),
          procName,
          o);
      symbol.setIsPrivate(this.g.inPrivatePart);

      assert o.getTheOptionalRegionSelection() != null;
      symbol.setTheSelection(o.getTheOptionalRegionSelection());

      this.g.symbolStack.push(symbol);
      {
        //super.visitProcedureSubProgramDeclaration(o);
        visitProcedureSpecification(o.getTheProcedureSpecification());
        visitProcedureAnnotation(o.getTheProcedureAnnotation());
      }
      this.g.symbolStack.pop();
    }

    @Override
    protected void visitProofFunctionDeclaration(
        final ProofFunctionDeclaration o) {
      final String functionName = o.getTheFunctionSpecification()
          .getTheIDString();

      final EvalResult er = getNameAndSymbol(o.getTheFunctionSpecification()
          .getTheName());
      final SymDef sd = (SymDef) er.getTheSymbol();
      assert sd != null;

      final SymProofFunction symbol = (SymProofFunction) this.g.symbolTable
          .addFunction(
              (SymPackage) this.g.symbolStack.peek(),
              functionName,
              sd,
              true,
              this.g.inPackageDeclaration,
              o);
      //symbol.setTheSelection(SelectionCreation.create(o, this.g.filepath));
      assert o.getTheOptionalRegionSelection() != null;
      symbol.setTheSelection(o.getTheOptionalRegionSelection());

      symbol.setIsPrivate(this.g.inPrivatePart);

      this.g.symbolStack.push(symbol);
      visit(o.getTheFunctionSpecification()
          .getTheOptionalParameterSpecification());
      this.g.symbolStack.pop();
    }

    @Override
    protected void visitProofTypeDeclaration(final ProofTypeDeclaration o) {

      final String origName = o.getTheDefiningIdentifier();

      final SymPackageElement theSymbol = (SymPackageElement) this.g.symbolStack
          .peek();

      final SymAbstractDef sad = new SymAbstractDef();

      final Pair<String, String> theNames = helperGetTheTypeNames(
          theSymbol,
          origName);
      final String theTranslationName = theNames.getE0();
      final String thePilarName = theNames.getE1();

      sad.setTheName(thePilarName);
      sad.setTheTranslation(theTranslationName);

      final SymOrigin theOrigin = this.g.inPackageDeclaration ? SymOrigin.PACKAGE_SPEC_PUBLIC
          : theSymbol instanceof SymCall ? SymOrigin.METHOD
              : SymOrigin.PACKAGE_BODY;
      sad.setTheOrigin(theOrigin);
      //sad.setTheSelection(SelectionCreation.create(o, this.g.filepath));
      assert o.getTheOptionalRegionSelection() != null;
      sad.setTheSelection(o.getTheOptionalRegionSelection());

      theSymbol.getTheDefinitions().put(origName.toLowerCase(), sad);
    }

    @Override
    protected void visitRefinementClause(final RefinementClause o) {

      final SymPackage p = (SymPackage) this.g.symbolStack.peek();
      final SymRefinementAnnotation sra = new SymRefinementAnnotation();

      final ContainerRefinementClause crc = new ContainerRefinementClause();
      p.getTheOptionalRefinementDefinition().getTheRefinementClauses().add(crc);

      final RegionSelection refRS = o.getTheOptionalRegionSelection();
      assert refRS != null;
      crc.setTheRefinementClauseSelection(refRS);

      // if we get to this point then it must be the case that we
      // are refining an abstract variable (assuming we are relying
      // on the examiner to ensure the project is valid).  Thus we
      // need to introduce a "fake" global variable for the abstract
      // variable so that we can look it up during the translation.

      SymOwnVariableAnnotation theOwn = null;
      for (final SymOwnVariableAnnotation sova : p.getTheOwnVariables()) {
        if (sova.getTheName().toLowerCase()
            .equals(o.getTheSubject().getTheIDString().toLowerCase())) {
          //o.getTheIDString().toLowerCase())) {
          theOwn = sova;
          break;
        }
      }
      assert theOwn != null;

      // add the "fake" global variable as a first class citizen in the package
      // as global var declarations and implicit proof function parameters
      // could be referring to it
      assert (theOwn.getTheObject() == null)
          && (theOwn.getTheFakeGlobal() != null);
      theOwn.setTheObject(theOwn.getTheFakeGlobal());

      assert p.getTheObjects().size() == 1;
      p.getTheObjects().peek()
          .put(theOwn.getTheName().toLowerCase(), theOwn.getTheObject());
      final SymDef theAbsType = theOwn.getTheOptionalType();
      assert theOwn.getTheOptionalType() == theOwn.getTheObject()
          .getTheOptionalTypeSymDef();
      p.getTheDefinitions().put(
          theAbsType.getTheName().toLowerCase(),
          theAbsType);

      // link them together
      theOwn.setTheOptionalRefinements(sra);
      sra.setTheOwn(theOwn);

      // we've resolved the own variable as an abstract own variable which is
      // refined in the body
      this.g.symbolTable.removeUnresolved(theOwn);

      for (final Constituent c : o.getTheConstituents()) {
        final SymModeAnnotation constituent = new SymModeAnnotation();

        final EvalResult er = getNameAndSymbol(c.getTheName());
        constituent.setTheName(er.toString());

        assert er.getTheSymbol() != null;
        constituent.setTheObject((SymObject) er.getTheSymbol());

        /*
        constituent.setTheSelection(SelectionCreation
            .create(c, this.g.filepath));
            */
        assert c.getTheOptionalRegionSelection() != null;
        constituent.setTheSelection(c.getTheOptionalRegionSelection());

        constituent.setTheMode(c.getTheMode());
        sra.getTheConstituents().add(constituent);
      }

      //sra.setTheSelection(SelectionCreation.create(o, this.g.filepath));
      assert o.getTheSubject().getTheOptionalRegionSelection() != null;
      sra.setTheSelection(o.getTheSubject().getTheOptionalRegionSelection());

      p.getTheRefinements().add(sra);
      crc.setTheRefinementClause(sra);
    }

    @Override
    protected void visitRenamingDeclaration(final RenamingDeclaration o) {
      System.err.println(SymbolTableBuilder.CLASS_NAME
          + "do we care about renaming declaration "
          + o.getClass().getSimpleName());
    }

    @Override
    protected void visitRepresentationClause(final RepresentationClause o) {
      final SymPackageElement spe = (SymPackageElement) this.g.symbolStack
          .peek();
      assert spe != null;

      ArrayList<RepresentationClause> theList = spe
          .getTheOptionalRepresentationClauses();
      if (theList == null) {
        theList = new ArrayList<RepresentationClause>();
        spe.setTheOptionalRepresentationClauses(theList);
      }

      theList.add(o);
    }

    @Override
    protected void visitSelectedComponent(final SelectedComponent o) {
      if (this.g.visitName) {
        final StringTemplate st = this.g.stg.getInstanceOf("same");
        final String selector_name = o.getTheIDString();

        // NOTE: dont call getStringFromName here since that will set 
        //       g.visitName to false when it returns
        visit(o.getTheName());
        final EvalResult er = this.g.popResult();
        st.setAttribute("ID", er.getTheTemplate() + "." + selector_name);

        // assert er.getTheSymbol() != null;
        // It's probably a better idea to have the calling function
        // check whether the returned symbol is null.  For example,
        // we could have '--# own AbsD is P2.D' where P2 is 
        // nested package which hasn't been visited yet.

        final Symbol ret = this.g.symbolTable.resolveSymbol(
            er.getTheSymbol(),
            selector_name);

        this.g.pushResult(st, ret);
      }
    }

    @Override
    protected void visitStatementSubProgramImplementation(
        final StatementSubProgramImplementation o) {
      visit(o.getTheDeclarativePart());
      // We don't need to visit the actual implementation code during the
      // symbol table builder phase
      //visit(o.getTheStatementList();
    }

    @Override
    protected void visitSubTypeDeclaration(final SubTypeDeclaration o) {
      final String origName = o.getTheIDString();

      final SymPackageElement theSymbol = (SymPackageElement) this.g.symbolStack
          .peek();

      final Pair<String, String> theNames = helperGetTheTypeNames(
          theSymbol,
          origName);
      final String theTranslationName = theNames.getE0();
      final String thePilarRecordName = theNames.getE1();

      SymDef theBaseTypeSymDef = null;
      {
        final EvalResult er = getNameAndSymbol(o.getTheSubTypeIndication()
            .getTheName());

        theBaseTypeSymDef = (SymDef) er.getTheSymbol();
      }
      assert theBaseTypeSymDef != null;

      SymDef subTypeDefinition = null;

      final Constraint optConstraint = o.getTheSubTypeIndication()
          .getTheOptionalConstraint();
      if (optConstraint == null) {

        final SymRangeSubTypeDef srstd = new SymRangeSubTypeDef();
        srstd.setTheParentTypeDef(theBaseTypeSymDef);

        // treat unconstrained subtype defs as if the user specified
        // subtype X is <basetype> range <basetype>'First .. <basetype>'Last;      
        {
          final NameExp low = new NameExp();
          final AttributeReference ar = new AttributeReference();
          ar.setTheName(NodeCloner.clone(o.getTheSubTypeIndication()
              .getTheName(), new HashMap<Node, Node>()));
          final IDAttributeDesignator iad = new IDAttributeDesignator();
          iad.setTheIDString("First");
          ar.setTheAttributeDesignator(iad);
          low.setTheName(ar);
          srstd.setTheLowRangeExp(low);
        }
        {
          final NameExp high = new NameExp();
          final AttributeReference ar = new AttributeReference();
          ar.setTheName(NodeCloner.clone(o.getTheSubTypeIndication()
              .getTheName(), new HashMap<Node, Node>()));
          final IDAttributeDesignator iad = new IDAttributeDesignator();
          iad.setTheIDString("Last");
          ar.setTheAttributeDesignator(iad);
          high.setTheName(ar);
          srstd.setTheHighRangeExp(high);
        }

        subTypeDefinition = srstd;
      } else {
        switch (optConstraint.getDescriptor()) {
          case RangeConstraint.DESCRIPTOR: {
            final SymRangeSubTypeDef rangeDefinition = new SymRangeSubTypeDef();

            rangeDefinition.setTheParentTypeDef(theBaseTypeSymDef);

            final RangeConstraint rc = (RangeConstraint) optConstraint;
            final Range r = rc.getTheRange();
            switch (r.getDescriptor()) {
              case ExpRange.DESCRIPTOR:
                final ExpRange er = (ExpRange) r;
                rangeDefinition.setTheHighRangeExp(er.getTheHighRangeExp());
                rangeDefinition.setTheLowRangeExp(er.getTheLowRangeExp());
                break;
              case RangeAttributeReference.DESCRIPTOR:
                //TODO: handle RangeAttributeReference
                System.err.println("RangeAttributeReference not handled");
                assert false;
                break;
            }
            subTypeDefinition = rangeDefinition;
            break;
          }
          case IndexConstraint.DESCRIPTOR: {
            final SymIndexSubTypeDef indexDefinition = new SymIndexSubTypeDef();
            indexDefinition
                .setTheParentTypeDef((SymArrayDef) theBaseTypeSymDef);
            final IndexConstraint ic = (IndexConstraint) optConstraint;
            for (final Name n : ic.getTheDiscreteSubTypeNames()) {
              final EvalResult erSubType = getNameAndSymbol(n);
              final SymDef theType = (SymDef) erSubType.getTheSymbol();
              assert theType != null;

              indexDefinition.getTheSubTypeDefs().add(theType);
            }
            subTypeDefinition = indexDefinition;
            break;
          }
          default:
            assert false;
        }
      }
      assert subTypeDefinition != null;

      final SymOrigin theOrigin = this.g.inPackageDeclaration ? (this.g.inPrivatePart ? SymOrigin.PACKAGE_SPEC_PRIVATE
          : SymOrigin.PACKAGE_SPEC_PUBLIC)
          : (theSymbol instanceof SymCall ? SymOrigin.METHOD
              : SymOrigin.PACKAGE_BODY);
      subTypeDefinition.setTheOrigin(theOrigin);

      //subTypeDefinition.setTheSelection(SelectionCreation.create(o,
      //    this.g.filepath));
      assert o.getTheOptionalRegionSelection() != null;
      subTypeDefinition.setTheSelection(o.getTheOptionalRegionSelection());

      subTypeDefinition.setTheName(thePilarRecordName);
      subTypeDefinition.setTheTranslation(theTranslationName);

      // for now assume that symcalls only come from packages.  We'll
      // need to fix this for a method nested within a method
      //final SymPackage thePackage = (SymPackage) (theSymbol instanceof SymPackage ? theSymbol
      //    : theSymbol.getTheOptionalParent());

      //subTypeDefinition.setTheTranslation(thePackage.getTheTranslation()
      //    + Util.PILAR_PACKAGE_SEPARATOR + compliantID);

      theSymbol.getTheDefinitions().put(
          origName.toLowerCase(),
          subTypeDefinition);
    }

    @Override
    protected void visitTypeAssertion(final TypeAssertion o) {
      assert false;
    }

    @Override
    protected void visitUseTypeClause(final UseTypeClause o) {

      if (o.getTheNames() != null) {
        for (final Name useTypeName : o.getTheNames()) {
          final EvalResult er = getNameAndSymbol(useTypeName);
          final SymDef sf = (SymDef) er.getTheSymbol();
          assert sf != null;

          this.g.theUseTypeClauses.add(sf);
        }
      }
    }

    @Override
    protected void visitWithClause(final WithClause o) {
      for (final Name n : o.getTheNames()) {
        final EvalResult er = getNameAndSymbol(n);
        this.g.withPackages.add((SymPackage) er.getTheSymbol());
      }
    }
  }

  public static final String CLASS_NAME = SymbolTableBuilder.class
      .getSimpleName() + ": ";

  public static void process(final SPARKCompilationUnit scu,
      final SymbolTable theSymbolTable, final List<Message> theMessages) {

    final InputStream is = SparkTemplateAnchor.class
        .getResourceAsStream(SparkTemplateAnchor.TEMPLATE_NAME);

    final StringTemplateGroup stg = new StringTemplateGroup(
        new InputStreamReader(is), DefaultTemplateLexer.class);

    stg.setStringTemplateWriter(PilarTranslator.NLWriter.class);

    final File f = new File(scu.getTheSparkSourceFilename());

    final SymbolTableBuilderContext vc = new SymbolTableBuilderContext(
        theSymbolTable, stg, f.getPath(), theMessages);

    // add pre-passes here using different visitors if necessary
    final SymbolTableBuilderVisitor symTableBuilder = new SymbolTableBuilderVisitor(
        vc);

    assert scu.getTheCompilationUnit() != null;

    symTableBuilder.visit(scu.getTheContextClause());
    symTableBuilder.visit(scu.getTheCompilationUnit());
    try {
      is.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
}
