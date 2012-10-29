package org.sireum.bakar;

import org.sireum.bakar.util.PackageKind;

/**
 * This static class holds utility methods for working with Pilar syntax objects
 * from the Pilar SPARK profile.
 * 
 * @author johnhatcliff
 * @author belt
 * 
 */
public class SparkUtil {
  public static final String SEP = "__"; // spark disallows two underscores in a row
  public static final String PROGRAM_ELEMENT_ID_SEPARATOR = "$";

  public static final String GLOBAL_VAR_MARKER = "@@";
  public static final String GLOBAL_TEMP_VAR_MARKER = "@@$";
  public static final String PILAR_PACKAGE_SEPARATOR = "::";

  public static final String IMPLICIT_RESULT_ID = SparkUtil.SEP
      + "implicit_result_id";

  public static final String SPARK_CONSTANT_PREFIX = "$Const";
  public static final String SPARK_CONSTANT_METHOD_DEF_PREFIX = SparkUtil.SPARK_CONSTANT_PREFIX
      + SparkUtil.SEP + "methodDef" + SparkUtil.SEP;

  public static final String SPARK_EMBEDDED_PACKAGE_SEPARATOR = "_E_";
  public static final String SPARK_CHILD_PACKAGE_SEPARATOR = "_C_";
  public static final String TEMP_VAR_PREFIX = "temp$";
  public static final String NULL_VAR_ID_STRING = "$NullVariable";
  public static final String PACKAGE_SPEC_NAME_SUFFIX = "$$";
  public static final String INIT_SPEC_PROC_NAME = "$$sinit";
  public static final String INIT_BODY_PROC_NAME = "$$binit";

  public static final String PACKAGE_SPECIFICATION_HEADER_ANNOTATION_ID_STRING = "PackageSpecificationHeaderAnnotation";
  public static final String METHOD_DEFINED_TYPE = "_methodDefType_";
  public static final String COMPILER_NAME = "Sireum SPARK Compiler";
  public static final String SPARK_MAIN_PROGRAM = "SPARK_MAIN_PROGRAM";
  public static final String SPARK_STANDARD_PACKAGE = "Standard";

  public static final String PROOF_TYPE_SUFFIX = SparkUtil.SEP + "type";

  // the following are used for the call annotation inserter (CAI)
  public static final String CAI_CALL_ANNOTATION_ID = "CallAnnotation";
  public static final String CAI_ACTUAL_IN_NODE_ID = "ActualInNode";
  public static final String CAI_ACTUAL_OUT_NODE_ID = "ActualOutNode";
  public static final String CAI_PRELUDE_ID = "prelude";
  public static final String CAI_POSTLUDE_ID = "postlude";

  public static final String MARK_PLACEHOLDER = "MARK_PLACEHOLDER";

  public static String generatePilarFilename(
      final String theSparkSourceFilename,
      final String theSpark2PilarPackageNameTranslation,
      final PackageKind thePackageKind) {
    assert (theSparkSourceFilename != null)
        && (theSpark2PilarPackageNameTranslation != null)
        && (thePackageKind != null);

    String fname = theSparkSourceFilename.replace('.', '_') + '_'
        + theSpark2PilarPackageNameTranslation;

    if (thePackageKind == PackageKind.SPECIFICATION) {
      fname += SparkUtil.PACKAGE_SPEC_NAME_SUFFIX;
    }
    return fname;
  }

  /**
   * 
   * @param theName
   *          can be either the simple or fully qualified global var name and
   *          with or without Util.GLOBAL_VAR_MARKER
   * @return the proof function's parameter name given to the declared global in
   *         vars of an actual Ada function
   */
  public static String getProofFuncGlobalVar2ParamName(final String theName) {
    assert theName != null;
    String n = theName;

    // FIXME: these are really bad hacks in the sense that a method could
    // refer to two global vars from different packages which have the same
    // name (e.g. derives X from A.State, B.State).  Fixing this will
    // require another phase in the symbol table builder
    final int pos1 = n.indexOf(SparkUtil.GLOBAL_VAR_MARKER);
    if (pos1 >= 0) {
      n = n.substring(pos1 + SparkUtil.GLOBAL_VAR_MARKER.length());
    }

    final int pos2 = n.indexOf(".");
    if (pos2 >= 0) {
      n = n.substring(pos2 + 1);
    }

    assert !n.contains(SparkUtil.PILAR_PACKAGE_SEPARATOR);

    return "implicit" + SparkUtil.SEP + n.toLowerCase();
  }

  /**
   * @param the
   *          element to find
   * @return the SPARK_ATTRIBUTE element if it exists, null otherwise
   */
  static public SparkAttribute getSparkAttribute(final String name) {
    try {
      if (name.charAt(0) == '$') {
        return SparkAttribute.valueOf(name.substring(1));
      } else {
        return SparkAttribute.valueOf(name);
      }
    } catch (final Exception e) { // empty
    }
    return null;
  }

}
