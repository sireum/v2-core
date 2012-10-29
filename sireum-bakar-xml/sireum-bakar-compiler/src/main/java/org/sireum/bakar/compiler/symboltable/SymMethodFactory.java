package org.sireum.bakar.compiler.symboltable;

import java.util.LinkedHashMap;

import org.sireum.bakar.compiler.symboltable.model.SymAdaFunction;
import org.sireum.bakar.compiler.symboltable.model.SymCall;
import org.sireum.bakar.compiler.symboltable.model.SymDef;
import org.sireum.bakar.compiler.symboltable.model.SymFunction;
import org.sireum.bakar.compiler.symboltable.model.SymObject;
import org.sireum.bakar.compiler.symboltable.model.SymPackageElement;
import org.sireum.bakar.compiler.symboltable.model.SymProcedure;
import org.sireum.bakar.compiler.symboltable.model.SymProofFunction;

public class SymMethodFactory {

  private static void fillIn(final SymCall sc, final String theSparkName,
      final String theTranslation, final SymPackageElement theOptionalParent) {
    sc.setTheName(theSparkName);
    sc.setTheTranslation(theTranslation);
    sc.setTheOptionalParent(theOptionalParent);
    sc.getTheObjects().push(new LinkedHashMap<String, SymObject>());
  }

  private static void fillInFunction(final SymFunction sf,
      final String theSparkName, final String theTranslation,
      final SymPackageElement theOptionalParent, final SymDef theReturnType) {
    SymMethodFactory
        .fillIn(sf, theSparkName, theTranslation, theOptionalParent);
    sf.setTheReturnType(theReturnType);
  }

  public static SymAdaFunction newSymAdaFunction(final String theSparkName,
      final String theTranslation, final SymPackageElement theOptionalParent,
      final SymDef theReturnType) {
    final SymAdaFunction saf = new SymAdaFunction();
    SymMethodFactory.fillInFunction(
        saf,
        theSparkName,
        theTranslation,
        theOptionalParent,
        theReturnType);
    return saf;
  }

  public static SymProcedure newSymProcedure(final String theSparkName,
      final String theTranslation, final SymPackageElement theOptionalParent) {
    final SymProcedure sp = new SymProcedure();
    SymMethodFactory
        .fillIn(sp, theSparkName, theTranslation, theOptionalParent);
    return sp;
  }

  public static SymProofFunction newSymProofFunction(final String theSparkName,
      final String theTranslation, final SymPackageElement theOptionalParent,
      final SymDef theReturnType) {
    final SymProofFunction spf = new SymProofFunction();
    SymMethodFactory.fillInFunction(
        spf,
        theSparkName,
        theTranslation,
        theOptionalParent,
        theReturnType);
    return spf;
  }
}
