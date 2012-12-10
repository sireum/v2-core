package org.sireum.bakar.compiler.v1.predefined;

import java.util.ArrayList;
import java.util.Map;

import org.sireum.bakar.SparkTypeFactory.PrimitiveType;
import org.sireum.bakar.SparkUIF;
import org.sireum.bakar.SparkUtil;
import org.sireum.bakar.compiler.v1.model.AttributeReference;
import org.sireum.bakar.compiler.v1.model.ContextClause;
import org.sireum.bakar.compiler.v1.model.Exp;
import org.sireum.bakar.compiler.v1.model.FunctionCall;
import org.sireum.bakar.compiler.v1.model.IDAttributeDesignator;
import org.sireum.bakar.compiler.v1.model.IDName;
import org.sireum.bakar.compiler.v1.model.LibraryCompilationUnit;
import org.sireum.bakar.compiler.v1.model.LiteralExp;
import org.sireum.bakar.compiler.v1.model.NameExp;
import org.sireum.bakar.compiler.v1.model.NumericLiteral;
import org.sireum.bakar.compiler.v1.model.PackageAnnotation;
import org.sireum.bakar.compiler.v1.model.PackageDeclaration;
import org.sireum.bakar.compiler.v1.model.PackageSpecification;
import org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit;
import org.sireum.bakar.compiler.v1.symboltable.SymPackageFactory;
import org.sireum.bakar.compiler.v1.symboltable.model.SymArrayDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymEnumElementDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymEnumerationDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymFloatingDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymOrigin;
import org.sireum.bakar.compiler.v1.symboltable.model.SymPackage;
import org.sireum.bakar.compiler.v1.symboltable.model.SymRangeSubTypeDef;
import org.sireum.bakar.compiler.v1.symboltable.model.SymSignedIntegerDef;
import org.sireum.bakar.selection.SelectionUtil;
import org.sireum.bakar.selection.model.RegionSelection;
import org.sireum.bakar.util.Path;

public class StandardPackage {

  public SPARKCompilationUnit constructSPARKCompilationUnit() {
    final SPARKCompilationUnit scu = new SPARKCompilationUnit();
    scu.setThePackageName(SparkUtil.SPARK_STANDARD_PACKAGE);
    scu.setTheSparkSourceFilename(Path
        .getSourceDirectory(StandardPackage.class)
        + StandardPackage.class.getSimpleName() + ".java");

    scu.setTheContextClause(new ContextClause());

    final LibraryCompilationUnit lcu = new LibraryCompilationUnit();
    final PackageDeclaration pd = new PackageDeclaration();

    final PackageSpecification ps = new PackageSpecification();
    {
      final IDName n = new IDName();
      n.setTheIDString(SparkUtil.SPARK_STANDARD_PACKAGE);
      ps.setTheName(n);

      final PackageAnnotation pa = new PackageAnnotation();
      ps.setThePackageAnnotation(pa);
    }

    pd.setThePackageSpecification(ps);
    lcu.setTheLibraryItem(pd);
    lcu.setTheContextClause(scu.getTheContextClause());
    scu.setTheCompilationUnit(lcu);

    return scu;
  }

  public SymPackage constructStandardSymPackage() {

    class CreateExp {
      Exp attributeExp(final String attribute, final SymDef type) {
        final NameExp ne = new NameExp();
        final AttributeReference ar = new AttributeReference();
        {
          final IDName theType = new IDName();
          theType.setTheIDString(type.getTheName()); // don't use translation here
          ar.setTheName(theType);

          final IDAttributeDesignator ad = new IDAttributeDesignator();
          ad.setTheIDString(attribute);
          ar.setTheAttributeDesignator(ad);
        }
        ne.setTheName(ar);
        return ne;
      }

      public Exp FunctionCall(final String uifName) {
        final NameExp ne = new NameExp();
        final FunctionCall fc = new FunctionCall();
        final IDName n = new IDName();
        n.setTheIDString(uifName);
        fc.setTheName(n);
        ne.setTheName(fc);
        return ne;
      }

      public Exp integerLiteralExp(final int i) {
        final LiteralExp le = new LiteralExp();
        final NumericLiteral il = new NumericLiteral();
        il.setTheNumberString(Integer.toString(i));
        le.setTheLiteral(il);
        return le;
      }
    }

    final RegionSelection dummySelection = SelectionUtil.newRegionSelection();

    final SymPackage sp = SymPackageFactory.newPackage(
        SparkUtil.SPARK_STANDARD_PACKAGE,
        SparkUtil.SPARK_STANDARD_PACKAGE);

    sp.setThePackageSpecSelection(dummySelection);

    final Map<String, SymDef> theDefs = sp.getTheDefinitions();

    final CreateExp ce = new CreateExp();

    final SymSignedIntegerDef integerType;
    final SymRangeSubTypeDef naturalType;
    final SymRangeSubTypeDef positiveType;
    final SymSignedIntegerDef longIntegerType;
    final SymFloatingDef floatType;
    final SymEnumerationDef boolType;
    final SymEnumerationDef charType;
    final SymArrayDef stringType;

    { // integer
      final PrimitiveType INT = PrimitiveType.INTEGER;
      integerType = new SymSignedIntegerDef();
      integerType.setTheName(INT.getSparkName());
      integerType.setTheTranslation(INT.getPilarSparkName());
      integerType.setTheLowRangeExp(ce.FunctionCall(SparkUIF.$NEG_INFTY
          .toString()));
      integerType.setTheHighRangeExp(ce.FunctionCall(SparkUIF.$POS_INFTY
          .toString()));
      theDefs.put(INT.getKey(), integerType);
    }

    { // natural
      final PrimitiveType NAT = PrimitiveType.NATURAL;
      naturalType = new SymRangeSubTypeDef();
      naturalType.setTheName(NAT.getSparkName());
      naturalType.setTheTranslation(NAT.getPilarSparkName());
      naturalType.setTheParentTypeDef(integerType);

      naturalType.setTheLowRangeExp(ce.integerLiteralExp(0));
      naturalType.setTheHighRangeExp(ce.attributeExp("Last", integerType));
      theDefs.put(NAT.getKey(), naturalType);
    }

    { // positive
      final PrimitiveType POS = PrimitiveType.POSITIVE;
      positiveType = new SymRangeSubTypeDef();
      positiveType.setTheName(POS.getSparkName());
      positiveType.setTheTranslation(POS.getPilarSparkName());
      positiveType.setTheParentTypeDef(integerType);

      positiveType.setTheLowRangeExp(ce.integerLiteralExp(1));
      positiveType.setTheHighRangeExp(ce.attributeExp("Last", integerType));
      theDefs.put(POS.getKey(), positiveType);
    }

    { // long integer
      final PrimitiveType LONG = PrimitiveType.LONG_INTEGER;
      longIntegerType = new SymSignedIntegerDef();
      longIntegerType.setTheName(LONG.getSparkName());
      longIntegerType.setTheTranslation(LONG.getPilarSparkName());
      longIntegerType.setTheLowRangeExp(ce.FunctionCall(SparkUIF.$NEG_INFTY
          .toString()));
      longIntegerType.setTheHighRangeExp(ce.FunctionCall(SparkUIF.$POS_INFTY
          .toString()));
      theDefs.put(LONG.getKey(), longIntegerType);
    }

    { // float
      final PrimitiveType FLOAT = PrimitiveType.FLOAT;
      floatType = new SymFloatingDef();
      floatType.setTheName(FLOAT.getSparkName());
      floatType.setTheTranslation(FLOAT.getPilarSparkName());

      floatType.setTheExp(ce.FunctionCall(SparkUIF.$UNDEFINED.toString()));

      floatType.setTheOptionalLowRangeExp(ce.FunctionCall(SparkUIF.$NEG_INFTY
          .toString()));
      floatType.setTheOptionalHighRangeExp(ce.FunctionCall(SparkUIF.$POS_INFTY
          .toString()));
      theDefs.put(FLOAT.getKey(), floatType);
    }

    { // booleans
      final PrimitiveType BOOL = PrimitiveType.BOOLEAN;
      boolType = new SymEnumerationDef();
      boolType.setTheName(BOOL.getSparkName());
      boolType.setTheTranslation(BOOL.getPilarSparkName());
      boolType.setTheElements(new ArrayList<SymEnumElementDef>());

      { // false value
        final SymEnumElementDef falseElem = new SymEnumElementDef();
        falseElem.setTheName("False");
        falseElem.setTheTranslation("false"); // don't prepend boolean
        falseElem.setTheParentEnumDef(boolType);
        falseElem.setTheSelection(dummySelection);
        boolType.getTheElements().add(falseElem);
        theDefs.put("boolean", boolType);
      }

      { // true value
        final SymEnumElementDef trueElem = new SymEnumElementDef();
        trueElem.setTheName("True");
        trueElem.setTheTranslation("true"); // don't prepend boolean
        trueElem.setTheParentEnumDef(boolType);
        trueElem.setTheSelection(dummySelection);
        boolType.getTheElements().add(trueElem);
      }
    }

    { // char
      final PrimitiveType CHAR = PrimitiveType.CHARACTER;
      charType = new SymEnumerationDef();
      charType.setTheName(CHAR.getSparkName());
      charType.setTheTranslation(CHAR.getPilarSparkName());
      charType.setTheElements(new ArrayList<SymEnumElementDef>());
      // TODO: add the char elements??
      theDefs.put(CHAR.getKey(), charType);
    }

    { // string
      final PrimitiveType STRING = PrimitiveType.STRING;
      stringType = new SymArrayDef();
      stringType.setTheName(STRING.getSparkName());
      stringType.setTheTranslation(STRING.getPilarSparkName());
      stringType.setIsConstrained(false);
      stringType.setTheComponentSubtypeDef(charType);
      stringType.setTheDimensions(1);
      final ArrayList<SymDef> theDimType = new ArrayList<SymDef>();
      theDimType.add(positiveType);
      stringType.setTheIndexSubtypeDefs(theDimType);
      theDefs.put(STRING.getKey(), stringType);
    }

    for (final SymDef sd : theDefs.values()) {
      sd.setTheOrigin(SymOrigin.PACKAGE_SPEC_PUBLIC);
      sd.setTheOptionalParent(sp);
      sd.setTheSelection(dummySelection);
    }

    // TODO: add the rest of the types

    return sp;
  }
}
