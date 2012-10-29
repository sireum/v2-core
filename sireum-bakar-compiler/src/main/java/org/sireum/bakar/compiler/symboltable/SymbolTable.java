package org.sireum.bakar.compiler.symboltable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.sireum.bakar.SparkUtil;
import org.sireum.bakar.compiler.model.Exp;
import org.sireum.bakar.compiler.model.Mode;
import org.sireum.bakar.compiler.model.Node;
import org.sireum.bakar.compiler.predefined.StandardPackage;
import org.sireum.bakar.compiler.symboltable.model.SymAbstractDef;
import org.sireum.bakar.compiler.symboltable.model.SymAdaFunction;
import org.sireum.bakar.compiler.symboltable.model.SymArrayDef;
import org.sireum.bakar.compiler.symboltable.model.SymCall;
import org.sireum.bakar.compiler.symboltable.model.SymCompositeDef;
import org.sireum.bakar.compiler.symboltable.model.SymConstant;
import org.sireum.bakar.compiler.symboltable.model.SymDec;
import org.sireum.bakar.compiler.symboltable.model.SymDef;
import org.sireum.bakar.compiler.symboltable.model.SymEnumElementDef;
import org.sireum.bakar.compiler.symboltable.model.SymEnumerationDef;
import org.sireum.bakar.compiler.symboltable.model.SymFunction;
import org.sireum.bakar.compiler.symboltable.model.SymGlobalAnnotation;
import org.sireum.bakar.compiler.symboltable.model.SymIndexSubTypeDef;
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
import org.sireum.bakar.compiler.symboltable.model.SymRecordComponentDef;
import org.sireum.bakar.compiler.symboltable.model.SymRecordDef;
import org.sireum.bakar.compiler.symboltable.model.SymSimpleAnnotation;
import org.sireum.bakar.compiler.symboltable.model.SymVariable;
import org.sireum.bakar.compiler.symboltable.model.Symbol;
import org.sireum.bakar.util.Stack;
import org.sireum.bakar.util.message.Message;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class SymbolTable {

  /**
   * see /sireum-pilar/src/org/sireum/pilar/parser/Pilar.tokens
   */
  private static final String[] PILAR_CONSTANTS = new String[] { //
  "abstact", //
      "actiondef", //
      "active", //
      "assert", //
      "assume", //
      "bit_and", //
      "bit_or", //
      "bit_xor", //
      "call", //
      "callback", //
      "case", //
      "catch", //
      "comp", //
      "const", //
      "div", //
      "default", //
      "else", // 
      "enum", //
      "expdef", //
      "extends", //
      "extension", //
      "false", //
      "from", //
      "fun", //
      "global", //
      "goto", //
      "if", //  
      "in",//
      "jmp", //
      "jumpdef", //
      "let", //
      "lazy", //
      "local", //
      "new", // 
      "null", //
      "package", //
      "procedure", //
      "record", //
      "return", //
      "self", //
      "Self", //
      "shl", //
      "shr", //
      "start", //
      "switch", //
      "then", //
      "throw", //
      "to", //
      "true", //
      "typealias", //
      "typedef", // 
      "typeswitch", //
      "ushr", //      
      "vset", //
      "wrap", //
  };

  public static final String PILAR_COMPLIANT_SUFFIX = "$_";

  public static final String SPARK_CHILD_PACKAGE_SEPARATOR = "\\.";
  private static final BiMap<String, String> PILAR_COMPLIANT_NAMES = HashBiMap
      .create();

  static {
    for (final String pilarName : SymbolTable.PILAR_CONSTANTS) {
      SymbolTable.PILAR_COMPLIANT_NAMES.put(pilarName, pilarName
          + SymbolTable.PILAR_COMPLIANT_SUFFIX);
    }
  }

  /**
   * gets the "closest" symdef for a symbol
   * 
   * @param s
   * @return
   */
  public static SymDef getSymDef(final Symbol s) {
    if (s instanceof SymObject) {
      return ((SymObject) s).getTheOptionalTypeSymDef();
    } else if (s instanceof SymEnumElementDef) {
      return ((SymEnumElementDef) s).getTheParentEnumDef();
    } else if (s instanceof SymRecordComponentDef) {
      return ((SymRecordComponentDef) s).getTheTypeSymDef();
    } else if (s instanceof SymDef) {
      return (SymDef) s;
    } else if (s instanceof SymFunction) {
      return ((SymFunction) s).getTheReturnType();
    } else if (s instanceof SymOwnVariableAnnotation) {
      assert false;
    }

    return null;
  }

  public static List<SymObject> linearizeObjects(
      final Stack<LinkedHashMap<String, SymObject>> theObjects) {
    final List<SymObject> ret = new ArrayList<SymObject>();

    for (int i = theObjects.size() - 1; i >= 0; i--) {
      final LinkedHashMap<String, SymObject> c = theObjects.peekFromTop(i);
      ret.addAll(c.values());
    }

    return ret;
  }

  public static SymObject removeSymObject(final SymPackageElement spe,
      final String lowerName) {
    for (int i = 0; i < spe.getTheObjects().size(); i++) {
      final LinkedHashMap<String, SymObject> m = spe.getTheObjects()
          .peekFromTop(i);
      if (m.containsKey(lowerName)) {
        final SymObject ret = m.remove(lowerName);

        if (m.isEmpty() && (spe.getTheObjects().size() > 1)) {
          // the context is empty so remove it
          spe.getTheObjects().pop();
        }

        return ret;
      }
    }

    assert false;
    return null;
  }

  public static String returnPilarCompliantName(final String name) {
    String elem = SymbolTable.PILAR_COMPLIANT_NAMES.get(name);

    if (elem == null) {
      elem = name;
    }

    return elem;
  }

  public static String reversePilarCompliantName(final String name) {
    String elem = name;

    final BiMap<String, String> reverse = SymbolTable.PILAR_COMPLIANT_NAMES
        .inverse();
    elem = reverse.get(name);

    if (elem == null) {
      elem = name;
    }

    return elem;
  }

  private final LinkedHashMap<Symbol, String> theUnresolvedSymbols = new LinkedHashMap<Symbol, String>();
  private final HashMap<Node, Symbol> nodeToSymMap;

  private final HashMap<String, SymPackage> packages;

  public SymPackage theSparkStandardPackage = new StandardPackage()
      .constructStandardSymPackage();

  private SymPackage theOptionalMainProgram;

  public SymbolTable() {
    this.packages = new HashMap<String, SymPackage>();
    this.nodeToSymMap = new HashMap<Node, Symbol>();
  }

  public SymPackage addChildPackage(final SymPackage parent,
      final String theChildName, final Node theOptionalNode) {
    assert parent != null;

    final String lowerName = theChildName.toLowerCase();

    final String theTranslation = parent.getTheTranslation()
        + SparkUtil.SPARK_CHILD_PACKAGE_SEPARATOR
        + SymbolTable.returnPilarCompliantName(theChildName);

    final SymPackage ret = SymPackageFactory.newPackage(
        theChildName,
        theTranslation,
        parent,
        true,
        false);

    addNodeSymMap(theOptionalNode, ret);
    assert !parent.getTheChildPackages().containsKey(lowerName);
    parent.getTheChildPackages().put(lowerName, ret);

    return ret;
  }

  public SymConstant addConstant(final SymDec sd, final String name,
      final SymDef theOptionalTypeDef, final SymOrigin theOrigin,
      final Exp exp, final Node theOptionalNode) {

    final String lowerName = name.toLowerCase();

    String theTranslation = null;

    SymPackageElement location = null;
    switch (sd.getDescriptor()) {
      case SymProofFunction.DESCRIPTOR:
      case SymAdaFunction.DESCRIPTOR:
      case SymProcedure.DESCRIPTOR: {
        final SymCall sc = (SymCall) sd;
        final SymPackage p = (SymPackage) sc.getTheOptionalParent();
        //nameToUse = sc.getTheName() + "_" + name;

        final String mid = SparkUtil.PILAR_PACKAGE_SEPARATOR
            + SparkUtil.SPARK_CONSTANT_METHOD_DEF_PREFIX + sc.getTheName()
            + ".";
        theTranslation = p.getTheTranslation() + mid + name;

        location = sc;
        break;
      }
      case SymPackage.DESCRIPTOR: {
        location = (SymPackage) sd;
        final String mid = SparkUtil.PILAR_PACKAGE_SEPARATOR
            + SparkUtil.SPARK_CONSTANT_PREFIX + ".";

        theTranslation = location.getTheTranslation() + mid + name;
        break;
      }
      default:
        assert false;
    }

    SymConstant ret = (SymConstant) getSymObject(location, lowerName);

    if (ret != null) {
      assert ret.getTheOrigin() == SymOrigin.PACKAGE_SPEC_PUBLIC_DEFERRED;
      assert exp != null;

      ret.setTheExp(exp);

    } else {
      ret = new SymConstant();
      {
        ret.setTheName(name);
        ret.setTheOptionalTypeSymDef(theOptionalTypeDef);
        ret.setTheExp(exp);
        ret.setTheOrigin(theOrigin);
        ret.setTheKind(SymObjectKind.CONSTANT);
        ret.setTheTranslation(theTranslation);

        assert theOptionalNode.getTheOptionalRegionSelection() != null;
        ret.setTheSelection(theOptionalNode.getTheOptionalRegionSelection());
      }
      assert location.getTheObjects().size() == 1;
      location.getTheObjects().peek().put(lowerName, ret);
      addNodeSymMap(theOptionalNode, ret);
    }

    return ret;
  }

  public SymPackage addEmbeddedPackage(final String packageName,
      final SymPackage parent, final Node theOptionalNode) {
    //Make a new package symbol

    final String lowerName = packageName.toLowerCase();

    final String theTranslation = parent.getTheTranslation()
        + SparkUtil.SPARK_EMBEDDED_PACKAGE_SEPARATOR
        + SymbolTable.returnPilarCompliantName(packageName);

    final SymPackage ret = SymPackageFactory.newPackage(
        packageName,
        theTranslation,
        parent,
        false,
        true);
    addNodeSymMap(theOptionalNode, ret);
    assert !parent.getTheEmbeddedPackages().containsKey(lowerName);
    parent.getTheEmbeddedPackages().put(lowerName, ret);

    return ret;
  }

  public SymFunction addFunction(final SymPackageElement theContainer,
      final String name, final SymDef theReturnTypeDef,
      final boolean isProofFunction, final boolean isInSpec,
      final Node theOptionalNode) {
    SymFunction ret = null;

    final String lowerName = name.toLowerCase();
    switch (theContainer.getDescriptor()) {
      case SymPackage.DESCRIPTOR: {
        final SymPackage p = (SymPackage) theContainer;
        //Get the function map
        final HashMap<String, SymFunction> functions = p.getTheFunctions();
        if (!functions.containsKey(lowerName)) {
          //If the function name is not already there add this function to the map

          final String funcTransName = theContainer.getTheTranslation()
              + SparkUtil.PILAR_PACKAGE_SEPARATOR
              + SymbolTable.returnPilarCompliantName(name);

          if (isProofFunction) {
            ret = SymMethodFactory.newSymProofFunction(
                name,
                funcTransName,
                theContainer,
                theReturnTypeDef);
          } else {
            ret = SymMethodFactory.newSymAdaFunction(
                name,
                funcTransName,
                theContainer,
                theReturnTypeDef);
          }
          functions.put(lowerName, ret);
        } else {
          // If the function name is already there get the function from the 
          // function map.  Should only occur for non proof functions
          assert !isProofFunction;
          ret = functions.get(lowerName);
        }
        break;
      }
      default:
        throw new RuntimeException("Nested functions not handled. " + name
            + " found in " + theContainer.getTheName());
    }
    addNodeSymMap(theOptionalNode, ret);
    return ret;
  }

  /**
   * Creates a global variable for an abstract variable in a refinement clause.
   * 
   * @param p
   *          where to place the generated variable
   * @param name
   *          the unqualified name
   * @return
   */
  public SymVariable addGlobalVarForOwnVar(final SymPackage p,
      final SymOwnVariableAnnotation sova, final Node theOptionalNode) {

    assert sova.getTheFakeGlobal() == null;

    final SymVariable ret = new SymVariable();
    sova.getTheName().toLowerCase();

    sova.setTheFakeGlobal(ret);

    // if the user didn't supply a type then introduce a new abstract type
    if (sova.getTheOptionalType() == null) {
      final SymAbstractDef sad = new SymAbstractDef();
      sad.setTheName(sova.getTheName() + SparkUtil.PROOF_TYPE_SUFFIX);

      sad.setTheTranslation(p.getTheTranslation()
          + SparkUtil.PILAR_PACKAGE_SEPARATOR + sad.getTheName());
      sad.setTheOrigin(SymOrigin.PACKAGE_SPEC_PUBLIC);
      sad.setTheSelection(sova.getTheSelection());

      sad.setTheOptionalParent(p);
      sova.setTheOptionalType(sad);

      //p.getTheDefinitions().put(sad.getTheName().toLowerCase(), sad);
    }

    ret.setTheOptionalTypeSymDef(sova.getTheOptionalType());

    ret.setTheName(sova.getTheName());
    ret.setTheOrigin(SymOrigin.PACKAGE_SPEC_PUBLIC);
    ret.setTheKind(SymObjectKind.ABSTRACT_VARIABLE);
    ret.setTheSelection(sova.getTheSelection());
    //ret.setTheOptionalSelection(sova.getTheOptionalSelection());

    ret.setTheTranslation(p.getTheTranslation()
        + SparkUtil.PILAR_PACKAGE_SEPARATOR + SparkUtil.GLOBAL_TEMP_VAR_MARKER
        + ret.getTheName());

    //p.getTheObjects().put(lowerName, ret);
    addNodeSymMap(theOptionalNode, ret);
    return ret;
  }

  private void addNodeSymMap(final Node theOptionalNode, final Symbol sym) {
    if (theOptionalNode != null) {
      assert sym != null;
      assert !this.nodeToSymMap.containsKey(theOptionalNode);
      this.nodeToSymMap.put(theOptionalNode, sym);
    }
  }

  public void addNodeToSymbolMapping(final Node o, final Symbol s) {
    this.nodeToSymMap.put(o, s);
  }

  public SymParameter addParameter(final SymCall p, final String name,
      final SymDef theTypeSymDef, final Mode mode, final Node theOptionalNode) {
    SymParameter ret = null;
    final String lowerName = name.toLowerCase();
    final HashMap<String, SymParameter> objects = p.getTheParameters();
    if (!objects.containsKey(lowerName)) {
      ret = new SymParameter();
      ret.setTheName(name);

      assert theTypeSymDef != null;
      ret.setTheOptionalTypeSymDef(theTypeSymDef);

      ret.setTheTranslation(SymbolTable.returnPilarCompliantName(name));
      //ret.setTheIndex(p.getTheNumberOfParams());
      ret.setTheMode(mode);
      ret.setTheKind(SymObjectKind.PARAMETER);

      p.setTheNumberOfParams(p.getTheNumberOfParams() + 1);
      objects.put(lowerName, ret);
    } else {
      ret = objects.get(lowerName);
    }
    addNodeSymMap(theOptionalNode, ret);
    return ret;
  }

  public SymProcedure addProcedure(final SymPackageElement theContainer,
      final String name, final Node theOptionalNode) {
    SymProcedure ret = null;
    final String lowerName = name.toLowerCase();

    //Get the procedure map
    switch (theContainer.getDescriptor()) {
      case SymPackage.DESCRIPTOR: {
        final SymPackage p = (SymPackage) theContainer;
        final HashMap<String, SymProcedure> procedures = p.getTheProcedures();
        if (!procedures.containsKey(lowerName)) {
          //If the procedure name is not already there add this procedure to the map
          final String translation = theContainer.getTheTranslation()
              + SparkUtil.PILAR_PACKAGE_SEPARATOR
              + SymbolTable.returnPilarCompliantName(name);

          ret = SymMethodFactory.newSymProcedure(
              name,
              translation,
              theContainer);

          procedures.put(lowerName, ret);
        } else {
          //If the procedure is already there get the procedure from the procedure map
          ret = procedures.get(lowerName);
        }
        break;
      }
      default:
        throw new RuntimeException(
            "Nested procedures not currently handled. \"" + name
                + "\" found in " + theContainer.getTheName());
    }
    addNodeSymMap(theOptionalNode, ret);
    return ret;
  }

  public SymPackage addRootPackage(final String theRootName,
      final Node theOptionalNode) {

    final String lowerName = theRootName.toLowerCase();

    SymbolTable.returnPilarCompliantName(theRootName);

    final SymPackage ret = SymPackageFactory.newPackage(
        theRootName,
        theRootName,
        null,
        false,
        false);

    addNodeSymMap(theOptionalNode, ret);

    assert !this.packages.containsKey(lowerName);
    this.packages.put(lowerName, ret);
    return ret;
  }

  public void addUnresolved(final Symbol s, final String theString) {
    assert (s != null) && (theString != null);
    this.theUnresolvedSymbols.put(s, theString);
  }

  public SymVariable addVariable(final SymPackageElement sd, final String name,
      final SymDef theTypeSymDef, final SymOrigin theOrigin,
      final SymObjectKind theKind, final Exp exp, final Node theOptionalNode) {

    final SymVariable ret = new SymVariable();
    final String lowerName = name.toLowerCase();
    ret.setTheName(name);

    assert theTypeSymDef != null;
    ret.setTheOptionalTypeSymDef(theTypeSymDef);
    ret.setTheExp(exp);
    ret.setTheOrigin(theOrigin);
    ret.setTheKind(theKind);

    switch (sd.getDescriptor()) {
      case SymProofFunction.DESCRIPTOR:
      case SymAdaFunction.DESCRIPTOR:
      case SymProcedure.DESCRIPTOR:
        final SymCall theMethod = (SymCall) sd;
        final SymObject so = getSymObject(theMethod, lowerName);

        if (so == null) {
          ret.setTheTranslation(SymbolTable.returnPilarCompliantName(name));
        } else {
          // the name already exists so we need to push a new context and 
          // ensure the translation is unique
          theMethod.getTheObjects()
              .push(new LinkedHashMap<String, SymObject>());

          final int stackHeight = theMethod.getTheObjects().size();
          ret.setTheTranslation(SymbolTable.returnPilarCompliantName(name)
              + SparkUtil.PROGRAM_ELEMENT_ID_SEPARATOR + stackHeight);
        }
        assert !theMethod.getTheObjects().isEmpty();
        theMethod.getTheObjects().peek().put(lowerName, ret);
        break;
      case SymPackage.DESCRIPTOR:
        final SymPackage p = (SymPackage) sd;
        final String mid = SparkUtil.PILAR_PACKAGE_SEPARATOR
            + SparkUtil.GLOBAL_VAR_MARKER;
        ret.setTheTranslation(p.getTheTranslation() + mid + name);

        assert p.getTheObjects().size() == 1;
        assert !p.getTheObjects().peek().containsKey(lowerName);
        p.getTheObjects().peek().put(lowerName, ret);
        break;
      default:
        assert false;
    }
    addNodeSymMap(theOptionalNode, ret);
    ret.setTheOptionalParent(sd);
    return ret;
  }

  /**
   * This method is essentially a hack in place of a multipass symbol resolver.
   * With spark we can have own declarations which refer to objects which are
   * not declared until we process another compilation unit. Other entities such
   * as postconditions can refer to these unresolved symbols so before we can do
   * the translation we need to resolve these. We could do this with multi
   * passes, or we can just hack it as follows :)
   * 
   * @param theMessages
   * @return
   */
  public boolean finalizeSymbolTable(final List<Message> messages) {

    // at this point the symbol in the unresolved map can only be referring
    // to abstract variables which have not been refined.  I.e. the user 
    // supplied a spec (but not the corresponding body) with an own annotation 
    // which refer to variables which were not found in the spec portion

    final int count = this.theUnresolvedSymbols.keySet().size();
    final Symbol[] theSymbols = this.theUnresolvedSymbols.keySet().toArray(
        new Symbol[count]);

    for (final Symbol s : theSymbols) {
      if (s instanceof SymOwnVariableAnnotation) {
        final SymOwnVariableAnnotation sova = (SymOwnVariableAnnotation) s;
        this.theUnresolvedSymbols.remove(sova);

        final SymPackage sp = (SymPackage) sova.getTheOptionalParent();
        // sova must be a abstract variable which is neither refined nor refers
        // to an actual ada object
        assert !sp.getHasBody();
        assert sova.getTheObject() == null;
        assert sova.getTheOptionalRefinements() == null;
        assert sova.getTheFakeGlobal() != null;

        sova.setTheObject(sova.getTheFakeGlobal());

        // introduce the "fake" global variable we created for this own variable
        // into the package's objects
        final String lowerName = sova.getTheName().toLowerCase();
        assert sp.getTheObjects().size() == 1;
        sp.getTheObjects().peek().put(lowerName, sova.getTheObject());

        final SymDef theType = sova.getTheOptionalType();
        assert theType != null;

        if (theType instanceof SymAbstractDef) {
          // if the type is not abstract then it must have been declared
          // somewhere else
          sp.getTheDefinitions().put(
              theType.getTheName().toLowerCase(),
              theType);
        }
      }
    }

    int numUnresolved = this.theUnresolvedSymbols.size();

    // now we should be able to resolve the rest of the symbols
    for (final Symbol s : this.theUnresolvedSymbols.keySet()) {
      final SymPackageElement spe = s.getTheOptionalParent();
      final SymObject so = (SymObject) this.resolveSymbol(
          spe,
          this.theUnresolvedSymbols.get(s));
      assert so != null;

      switch (s.getDescriptor()) {
        case SymGlobalAnnotation.DESCRIPTOR: {
          final SymGlobalAnnotation sga = (SymGlobalAnnotation) s;
          sga.setTheObject(so);
          numUnresolved--;
          break;
        }
        case SymParameter.DESCRIPTOR: {
          final SymParameter sparam = (SymParameter) s;
          assert so.getTheOptionalTypeSymDef() != null;
          sparam.setTheOptionalTypeSymDef(so.getTheOptionalTypeSymDef());
          numUnresolved--;
          break;
        }
        case SymSimpleAnnotation.DESCRIPTOR: {
          final SymSimpleAnnotation ssa = (SymSimpleAnnotation) s;
          ssa.setTheObject(so);
          numUnresolved--;
          break;
        }
        default:
          assert false;
      }
    }
    assert numUnresolved == 0;
    return true;
  }

  public SymDef getBaseType(final String string) {
    final String lowerName = string.toLowerCase();
    return this.theSparkStandardPackage.getTheDefinitions().get(lowerName);
  }

  private SymObject getSymObject(final SymPackageElement location,
      final String lowerName) {
    if (location instanceof SymPackage) {
      assert location.getTheObjects().size() == 1;
      return location.getTheObjects().peek().get(lowerName);
    } else {
      for (int i = 0; i < location.getTheObjects().size(); i++) {
        final LinkedHashMap<String, SymObject> context = location
            .getTheObjects().peekFromTop(i);
        if (context.containsKey(lowerName)) {
          return context.get(lowerName);
        }
      }
    }
    return null;
  }

  public SymPackage getTheOptionalMainProgram() {
    return this.theOptionalMainProgram;
  }

  public SymPackage getTheSparkStandardPackage() {
    return this.theSparkStandardPackage;
  }

  public void removeUnresolved(final Symbol s, final String theString) {
    assert this.theUnresolvedSymbols.containsKey(s);
    this.theUnresolvedSymbols.remove(s);
  }

  /**
   * resolves any objects that were waiting for theCreatedObject to be found
   * 
   * @param theCreatedObject
   */
  public void removeUnresolved(final SymObject theCreatedObject) {
    assert theCreatedObject.getTheOptionalTypeSymDef() != null;

    final List<Symbol> toRemove = new ArrayList<Symbol>();

    final String theC = theCreatedObject.getTheName().toLowerCase();
    for (final Symbol s : this.theUnresolvedSymbols.keySet()) {
      if (s.getTheName().toLowerCase().equals(theC)) {
        switch (s.getDescriptor()) {
          case SymParameter.DESCRIPTOR: {
            // implicit proof functions can create symparameters which can't be
            // typed when they are introduced as the corresponding own variable
            // has not been resolved yet
            assert false;
            break;
          }
          case SymOwnVariableAnnotation.DESCRIPTOR: {
            final SymOwnVariableAnnotation sova = (SymOwnVariableAnnotation) s;
            assert sova.getTheOptionalRefinements() == null;
            sova.setTheObject(theCreatedObject);
            sova.setTheOwnCategory(SymOwnCategory.CONCRETE);
            if (sova.getTheOptionalType() != null) {
              assert (sova.getTheOptionalType() instanceof SymAbstractDef)
                  || (sova.getTheOptionalType() == theCreatedObject
                      .getTheOptionalTypeSymDef());
            } else {
              sova.setTheOptionalType(theCreatedObject
                  .getTheOptionalTypeSymDef());
            }

            toRemove.add(sova);
            break;
          }
          case SymGlobalAnnotation.DESCRIPTOR: {
            final SymGlobalAnnotation sga = (SymGlobalAnnotation) s;
            sga.setTheObject(theCreatedObject);
            toRemove.add(sga);
            break;
          }
          case SymSimpleAnnotation.DESCRIPTOR: {
            final SymSimpleAnnotation ssa = (SymSimpleAnnotation) s;
            ssa.setTheObject(theCreatedObject);
            toRemove.add(ssa);
            break;
          }
          default:
            assert false;
        }
      }
    }
    for (final Symbol s : toRemove) {
      this.theUnresolvedSymbols.remove(s);
    }
  }

  public void removeUnresolved(final SymOwnVariableAnnotation s) {
    this.removeUnresolved(s, null);
  }

  public SymPackage resolveBasePackage(final String id) {
    final String temp = id.toLowerCase();
    return this.packages.get(temp);
  }

  /**
   * a convenience method which can be called when you have a handle on a SPARK
   * ast node such as a package dec and want to find the symbol it may be
   * associated with. It also make it much easier to differentiate b/w child and
   * embedded packages which have the same name. <br>
   * This method may return null, in which case you should use the other
   * resolveSymbol method
   * 
   * @param theNode
   * @return
   */
  public Symbol resolveSymbol(final Node theNode) {
    return this.nodeToSymMap.get(theNode);
  }

  public Symbol resolveSymbol(final Symbol s, String id) {
    final String lowerName = id.toLowerCase();
    id = null; // make sure we don't try to use the case-sensitive arg

    if ((s != null) && s.getTheName().toLowerCase().equals(lowerName)) {
      // this can occur when we have a nested package which
      // inherits from its parent
      return s;
    }

    if (lowerName.contains(".")) {
      // we could have something like --# global in p.q.y
      final String[] theSplit = lowerName.split("\\.");
      SymPackage sp = (SymPackage) resolveSymbol(s, theSplit[0]);
      assert sp != null;

      for (int i = 1; i < (theSplit.length - 1); i++) {
        sp = (SymPackage) resolveSymbol(sp, theSplit[i]);
        assert sp != null;
      }

      return resolveSymbol(sp, theSplit[theSplit.length - 1]);
    }

    if (s instanceof SymObject) {
      return resolveSymbolFromSymObject((SymObject) s, lowerName);
    } else if (s instanceof SymCompositeDef) {
      return resolveSymbolFromSymCompositeDef((SymCompositeDef) s, lowerName);
    } else if (s instanceof SymPartialDef) {
      return resolveSymbol(
          ((SymPartialDef) s).getTheElaborationSymDef(),
          lowerName);
    } else if (s instanceof SymPackageElement) {
      return resolveSymbolFromSymPackageElement(
          (SymPackageElement) s,
          lowerName);
    }

    // is it a root package??
    if ((s == null) && this.packages.containsKey(lowerName)) {
      return this.packages.get(lowerName);
    }

    return null;
  }

  private Symbol resolveSymbolFromSymCompositeDef(final SymCompositeDef s,
      final String lowerName) {
    switch (s.getDescriptor()) {
      case SymArrayDef.DESCRIPTOR: {
        assert false;
        break;
      }
      case SymIndexSubTypeDef.DESCRIPTOR: {
        assert false;
        break;
      }
      case SymRecordDef.DESCRIPTOR: {
        final SymRecordDef srd = (SymRecordDef) s;
        return srd.getTheElements().get(lowerName);
      }
      default:
        assert false;
    }
    return null;
  }

  private Symbol resolveSymbolFromSymObject(final SymObject s,
      final String lowerName) {
    return resolveSymbol(s.getTheOptionalTypeSymDef(), lowerName);
  }

  private Symbol resolveSymbolFromSymPackageElement(final SymPackageElement s,
      final String lowerName) {
    //Symbol ret = s.getTheObjects().get(lowerName);
    Symbol ret = getSymObject(s, lowerName);

    if (ret == null) {
      ret = s.getTheDefinitions().get(lowerName);
    }

    if (ret == null) {
      // is it an enum element ??
      outer: for (final SymDef sd : s.getTheDefinitions().values()) {
        if (sd.getDescriptor() == SymEnumerationDef.DESCRIPTOR) {
          final SymEnumerationDef sed = (SymEnumerationDef) sd;
          for (final SymEnumElementDef seed : sed.getTheElements()) {
            if (seed.getTheName().toLowerCase().equals(lowerName)) {
              ret = seed;
              break outer;
            }
          }
        }
      }
    }

    if ((ret == null) && (s instanceof SymCall)) {
      ret = ((SymCall) s).getTheParameters().get(lowerName);
    }

    if ((ret == null) && (s instanceof SymPackage)) {
      final SymPackage sp = (SymPackage) s;
      ret = sp.getTheProcedures().get(lowerName);

      if (ret == null) {
        ret = sp.getTheFunctions().get(lowerName);
      }

      if (ret == null) {
        for (final SymPackage specWith : sp.getTheSpecWithPackages()) {
          if (specWith.getTheName().toLowerCase().equals(lowerName)) {
            ret = specWith;
            break;
          }
        }
      }

      if (ret == null) {
        for (final SymPackage bodyWith : sp.getTheBodyWithPackages()) {
          if (bodyWith.getTheName().toLowerCase().equals(lowerName)) {
            ret = bodyWith;
            break;
          }
        }
      }

      if (ret == null) {
        for (final SymPackage inherited : sp.getTheInheritedPackages()) {
          if (inherited.getTheName().toLowerCase().equals(lowerName)) {
            ret = inherited;
            break;
          }
        }
      }

      if (ret == null) {
        ret = sp.getTheChildPackages().get(lowerName);
      }

      if (ret == null) {
        ret = sp.getTheEmbeddedPackages().get(lowerName);
      }
    }

    if ((ret == null) && (s.getTheOptionalParent() != null)) {
      ret = resolveSymbol(s.getTheOptionalParent(), lowerName);
    }

    if ((ret == null) && (s != this.theSparkStandardPackage)) {
      ret = resolveSymbol(this.theSparkStandardPackage, lowerName);
    }

    return ret;
  }

  public void setTheOptionalMainProgram(final SymPackage mainProg) {
    assert this.theOptionalMainProgram == null;
    assert mainProg != null;
    this.theOptionalMainProgram = mainProg;
  }
}
