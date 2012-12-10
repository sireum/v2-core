package org.sireum.bakar.compiler.v1.symboltable;

import java.util.LinkedHashMap;

import org.sireum.bakar.compiler.v1.symboltable.model.SymObject;
import org.sireum.bakar.compiler.v1.symboltable.model.SymPackage;
import org.sireum.bakar.compiler.v1.symboltable.model.SymPackageElement;

public class SymPackageFactory {

  public static SymPackage newPackage(final String packageName,
      final String theTranslation) {
    return SymPackageFactory.newPackage(
        packageName,
        theTranslation,
        null,
        false,
        false);
  }

  public static SymPackage newPackage(final String packageName,
      final String theTranslation, final SymPackageElement theParent,
      final boolean isChild, final boolean isEmbedded) {
    final SymPackage sp = new SymPackage();
    sp.setTheName(packageName);
    sp.setTheTranslation(theTranslation);
    sp.setIsChild(isChild);
    sp.setIsEmbedded(isEmbedded);
    sp.getTheObjects().push(new LinkedHashMap<String, SymObject>());

    if (theParent != null) {
      sp.setTheOptionalParent(theParent);
    }
    return sp;
  }
}
