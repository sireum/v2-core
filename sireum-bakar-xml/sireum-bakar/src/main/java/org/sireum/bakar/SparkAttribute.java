/******************************************************************************
 * Copyright (c) 2009 Belt, Kansas State University, and others.              *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar;

import java.util.ArrayList;
import java.util.List;

import org.sireum.bakar.ISparkAttribute.AttributePrefixType;

class Defs {

  public static final List<AttributePrefixType> SCALAR_SUBTYPE = toList(AttributePrefixType.SCALAR_SUBTYPE);
  public static final List<AttributePrefixType> MODULAR_SUBTYPE = toList(AttributePrefixType.MODULAR_SUBTYPE);
  public static final List<AttributePrefixType> FIXED_SUBTYPE = toList(AttributePrefixType.FIXED_POINT_SUBTYPE);
  public static final List<AttributePrefixType> FLOAT_SUBTYPE = toList(AttributePrefixType.FLOATING_POINT_SUBTYPE);
  public static final List<AttributePrefixType> ARRAY_OBJECT = toList(AttributePrefixType.ARRAY_OBJECT);

  public static final List<AttributePrefixType> ARRAY_TYPE = toList(AttributePrefixType.ARRAY_TYPE);
  public static final List<AttributePrefixType> ARRAY_SUBTYPE_CONSTRAINED = toList(AttributePrefixType.ARRAY_SUBTYPE_CONSTRAINED);
  public static final List<AttributePrefixType> ARRAY_SUBTYPE = toList(AttributePrefixType.ARRAY_SUBTYPE);

  @SuppressWarnings("unchecked")
  static List<AttributePrefixType> toList(final Object... args) {
    final List<AttributePrefixType> ret = new ArrayList<AttributePrefixType>();
    for (final Object o : args) {
      if (o instanceof List) {
        ret.addAll((List<AttributePrefixType>) o);
      } else {
        ret.add((AttributePrefixType) o);
      }
    }
    return ret;
  }
}

/**
 * The ADA attributes supported by SPARK
 * <p>
 * 
 * Comments indicate type of object each designator is applicable to
 * <ul>
 * <li>S => Scalar
 * <li>X => Object
 * <li>A(N) => Array using dimension N
 * <li>A => Array
 * </ul>
 * 
 * @see <a
 *      href="http://www.adaic.org/standards/95lrm/html/RM-K.html">Consolidated
 *      Ada Reference Manual, LNCS 2219 Annex K</a>
 */
public enum SparkAttribute implements ISparkAttribute {

  /**
   * For every subtype S of a floating point type T:<br>
   * S'Adjacent denotes a function with the following specification: <br>
   * 
   * <pre>
   * function S'Adjacent (X, Towards : T)  
   *   return T
   * </pre>
   * 
   * If Towards = X, the function yields X; otherwise, it yields the machine
   * number of the type T adjacent to X in the direction of Towards, if that
   * machine number exists. If the result would be outside the base range of S,
   * Constraint_Error is raised. When T'Signed_Zeros is True, a zero result has
   * the sign of X. When Towards is zero, its sign has no bearing on the result.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Adjacent {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.INDEX_TYPE_BASE;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

    @Override
    public boolean throwsConstraintError() {
      return true;
    }
  },

  /**
   * For every fixed point subtype S: <br>
   * S'Aft yields the number of decimal digits needed after the decimal point to
   * accommodate the delta of the subtype S, unless the delta of the subtype S
   * is greater than 0.1, in which case the attribute yields the value one.
   * (S'Aft is the smallest positive integer N for which (10**N)*S'Delta is
   * greater than or equal to one.) The value of this attribute is of the type
   * universal_integer.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5-10.html">3.5.10</a>
   */
  Aft // S
  {
    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.UNIVERSAL_INTEGER;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FIXED_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }
  },

  /**
   * For every scalar subtype S:<br>
   * S'Base denotes an unconstrained subtype of the type of S. This
   * unconstrained subtype is called the base subtype of the type.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5.html">3.5</a>
   */
  Base {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.INDEX_TYPE_BASE;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.SCALAR_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return true;
    }

  },

  /**
   * For every subtype S of a floating point type T: <br>
   * S'Ceiling denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Ceiling (X : T) 
   *   return T
   * </pre>
   * 
   * The function yields the value \ceil(X), i.e., the smallest (most negative)
   * integral value greater than or equal to X. When X is zero, the result has
   * the sign of X; a zero result otherwise has a negative sign when
   * S'Signed_Zeros is True.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Ceiling {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }
  },

  /**
   * For a prefix X that denotes an array subtype or array object (after any
   * implicit dereference): <br>
   * Denotes the size in bits of components of the type of X. The value of this
   * attribute is of type universal_integer.
   * 
   * @see <a href="http://www.adaic.org/standards/95lrm/html/RM-13-3.html"> 13.3
   *      </a>
   */
  Component_Size {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.UNIVERSAL_INTEGER;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.toList(Defs.ARRAY_SUBTYPE, Defs.ARRAY_OBJECT);
    }

    @Override
    public boolean isStatic() {
      return true;
    }
  },

  /**
   * For every subtype S of a floating point type T:<br>
   * S'Compose denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Compose (Fraction : T;
   *                     Exponent : universal_integer)
   *   return T
   * </pre>
   * 
   * Let v be the value Fraction * T'Machine_Radix<sup>Exponent-k</sup>, where k
   * is the normalized exponent of Fraction. If v is a machine number of the
   * type T, or if |v| >= T'Model_Small, the function yields v; otherwise, it
   * yields either one of the machine numbers of the type T adjacent to v.
   * Constraint_Error is optionally raised if v is outside the base range of S.
   * A zero result has the sign of Fraction when S'Signed_Zeros is True.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Compose {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

    @Override
    public boolean throwsConstraintError() {
      return true;
    }
  },

  /**
   * For every subtype S of a floating point type T:<br>
   * S'Copy_Sign denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Copy_Sign (Value, Sign : T) 
   *   return T
   * </pre>
   * 
   * If the value of Value is nonzero, the function yields a result whose
   * magnitude is that of Value and whose sign is that of Sign; otherwise, it
   * yields the value zero. Constraint_Error is optionally raised if the result
   * is outside the base range of S. A zero result has the sign of Sign when
   * S'Signed_Zeros is True.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Copy_Sign {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

    @Override
    public boolean throwsConstraintError() {
      return true;
    }
  },

  /**
   * For every fixed point subtype S:<br>
   * S'Delta denotes the delta of the fixed point subtype S. The value of this
   * attribute is of the type universal_real.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5-10.html">3.5.10</a>
   */
  Delta {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.UNIVERSAL_REAL;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FIXED_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return true;
    }
  },

  /**
   * For every subtype S of a floating point type T: <br>
   * Yields the value True if every value expressible in the form<br>
   * +- mantissa * T'Machine_Radix<sup>T'Machine_Emin</sup></br> where mantissa
   * is a nonzero T'Machine_Mantissa-digit fraction in the number base
   * T'Machine_Radix, the first digit of which is zero, is a machine number (see
   * <a
   * href="http://www.adaic.org/standards/95lrm/html/RM-3-5-7.html">3.5.7</a>)
   * of the type T; yields the value False otherwise. The value of this
   * attribute is of the predefined type Boolean.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Denorm {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.BOOLEAN;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      assert false;
      return false;
    }
  },

  /**
   * For every floating point subtype S:<br>
   * S'Digits denotes the requested decimal precision for the subtype S. The
   * value of this attribute is of the type universal_integer.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5-8.html">3.5.8</a>
   */
  Digits {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.UNIVERSAL_INTEGER;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FIXED_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return true;
    }
  },
  /**
   * For every subtype S of a floating point type T:<br>
   * S'Exponent denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Exponent (X : T)
   *   return universal_integer
   * </pre>
   * 
   * The function yields the normalized exponent of X.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Exponent {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.UNIVERSAL_INTEGER;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }
  },

  /**
   * <table>
   * <tr>
   * <td>A'First</td>
   * <td>For a prefix A that is of an array type (after any implicit
   * dereference), or denotes a constrained array subtype:<br>
   * A'First denotes the lower bound of the first index range; its type is the
   * corresponding index type.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-6-2.html">3.6.2</a>
   *      </td>
   *      </tr>
   * 
   *      <tr>
   *      <td>S'First</td> <td>For every scalar subtype S:<br>
   *      S'First denotes the lower bound of the range of S. The value of this
   *      attribute is of the type of S.
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5.html">3.5</a>
   *      </td>
   *      </tr>
   * 
   *      <tr>
   *      <td>A'First(N)</td> <td>For a prefix A that is of an array type (after
   *      any implicit dereference), or denotes a constrained array subtype:<br>
   *      A'First(N) denotes the lower bound of the N-th index range; its type
   *      is the corresponding index type.
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-6-2.html">3.6.2</a>
   *      </td>
   *      </tr>
   *      </table>
   */
  First {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.toList(
          Defs.ARRAY_TYPE,
          Defs.ARRAY_SUBTYPE_CONSTRAINED,
          Defs.SCALAR_SUBTYPE);
    }

    @Override
    public boolean isStatic() {
      return true;
    }
  },

  /**
   * For every subtype S of a floating point type T:<br>
   * S'Floor denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Floor (X : T) 
   *   return T
   * </pre>
   * 
   * The function yields the value \floor(X), i.e., the largest (most positive)
   * integral value less than or equal to X. When X is zero, the result has the
   * sign of X; a zero result otherwise has a positive sign.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Floor {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }
  },

  /**
   * For every fixed point subtype S:<br>
   * S'Fore yields the minimum number of characters needed before the decimal
   * point for the decimal representation of any value of the subtype S,
   * assuming that the representation does not include an exponent, but includes
   * a one-character prefix that is either a minus sign or a space. (This
   * minimum number does not include superfluous zeros or underlines, and is at
   * least 2.) The value of this attribute is of the type universal_integer.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5-10.html">3.5.10</a>
   */
  Fore {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.UNIVERSAL_INTEGER;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FIXED_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return true;
    }
  },

  /**
   * For every subtype S of a floating point type T:<br>
   * S'Fraction denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Fraction (X : T) 
   *   return T
   * </pre>
   * 
   * The function yields the value X * T'Machine_Radix<sup>-k</sup>, where k is
   * the normalized exponent of X. A zero result, which can only occur when X is
   * zero, has the sign of X.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Fraction {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

  },

  /**
   * <table>
   * <tr>
   * <td>A'Last</td>
   * <td>For a prefix A that is of an array type (after any implicit
   * dereference), or denotes a constrained array subtype:<br>
   * A'Last denotes the upper bound of the first index range; its type is the
   * corresponding index type.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-6-2.html">3.6.2.</a>
   *      </td>
   *      </tr>
   * 
   *      <tr>
   *      <td>S'Last</td> <td>For every scalar subtype S:<br>
   *      S'Last denotes the upper bound of the range of S. The value of this
   *      attribute is of the type of S.
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5.html">3.5.</a>
   *      </td>
   *      </tr>
   * 
   *      <tr>
   *      <td>A'Last(N)</td> <td>For a prefix A that is of an array type (after
   *      any implicit dereference), or denotes a constrained array subtype:<br>
   *      A'Last(N) denotes the upper bound of the N-th index range; its type is
   *      the corresponding index type.
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-6-2.html">3.6.2.</a>
   *      </td>
   *      </tr>
   *      </table>
   */
  Last {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.toList(
          Defs.ARRAY_TYPE,
          Defs.ARRAY_SUBTYPE_CONSTRAINED,
          Defs.SCALAR_SUBTYPE);
    }

    @Override
    public boolean isStatic() {
      return true;
    }

  },

  /**
   * For every subtype S of a floating point type T:<br>
   * S'Leading_Part denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Leading_Part (X : T;
   *                          Radix_Digits : universal_integer)
   *   return T
   * </pre>
   * 
   * Let v be the value T'Machine_Radix<sup>k-Radix_Digits</sup>, where k is the
   * normalized exponent of X. The function yields the value
   * <ul>
   * <li>\floor(X/v) * v, when X is nonnegative and Radix_Digits is positive;
   * <li>\ceil(X/v) * v, when X is negative and Radix_Digits is positive.
   * </ul>
   * Constraint_Error is raised when Radix_Digits is zero or negative. A zero
   * result, which can only occur when X is zero, has the sign of X.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Leading_Part {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

    @Override
    public boolean throwsConstraintError() {
      return true;
    }
  },

  /**
   * <table>
   * <tr>
   * <td>A'Length</td>
   * <td>For a prefix A that is of an array type (after any implicit
   * dereference), or denotes a constrained array subtype:<br>
   * A'Length denotes the number of values of the first index range (zero for a
   * null range); its type is universal_integer.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-6-2.html">3.6.2.</a>
   *      </td>
   *      </tr>
   * 
   *      <tr>
   *      <td>A'Length(N)</td> <td>For a prefix A that is of an array type
   *      (after any implicit dereference), or denotes a constrained array
   *      subtype:<br>
   *      A'Length(N) denotes the number of values of the N-th index range (zero
   *      for a null range); its type is universal_integer.
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-6-2.html">3.6.2.</a>
   *      </td>
   *      </tr>
   *      </table>
   */
  Length {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.UNIVERSAL_INTEGER;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.toList(Defs.ARRAY_TYPE, Defs.ARRAY_SUBTYPE_CONSTRAINED);
    }

    @Override
    public boolean isStatic() {
      return true;
    }
  },

  /*
    Machine, // S
    Machine_Emax, // S
    Machine_Emin, // S
    Machine_Mantissa, // S
    Machine_Overflows, // S
    Machine_Radix, // S
    Machine_Rounds, // S
   */

  /**
   * For every scalar subtype S:<br>
   * S'Max denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Max(Left, Right : S'Base) 
   *   return S'Base
   * </pre>
   * 
   * The function returns the greater of the values of the two parameters.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5.html">3.5.</a>
   */
  Max {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.INDEX_TYPE_BASE;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.SCALAR_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

  },

  /**
   * For every scalar subtype S:<br>
   * S'Min denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Min(Left, Right : S'Base) 
   *   return S'Base
   * </pre>
   * 
   * The function returns the lesser of the values of the two parameters.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5.html">3.5.</a>
   */
  Min {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.INDEX_TYPE_BASE;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.SCALAR_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

  },

  /*
    Model, // S
    Model_Emin, // S
    Model_Epsilon, // S 
    Model_Mantissa, // S
    Model_Small, // S
   */

  /**
   * For every modular subtype S:<br>
   * S'Modulus yields the modulus of the type of S, as a value of the type
   * universal_integer.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5-4.html">3.5.4</a>
   */
  Modulus {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.UNIVERSAL_INTEGER;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.MODULAR_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return true;
    }
  },

  /*
    Pos
   */

  /**
   * For every scalar subtype S:<br>
   * S'Pred denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Pred(Arg : S'Base) 
   *   return S'Base
   * </pre>
   * 
   * For an enumeration type, the function returns the value whose position
   * number is one less than that of the value of Arg; Constraint_Error is
   * raised if there is no such value of the type.
   * <ul>
   * <li>For an integer type, the function returns the result of subtracting one
   * from the value of Arg.
   * <li>For a fixed point type, the function returns the result of subtracting
   * small from the value of Arg.
   * <li>For a floating point type, the function returns the machine number (as
   * defined in <a
   * href="http://www.adaic.org/standards/95lrm/html/RM-3-5-7.html">3.5.7</a>)
   * immediately below the value of Arg;
   * </ul>
   * Constraint_Error is raised if there is no such machine number.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5.html">3.5</a>
   */
  Pred {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.INDEX_TYPE_BASE;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.SCALAR_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

    @Override
    public boolean throwsConstraintError() {
      return true;
    }
  },

  /**
   * <table>
   * <tr>
   * <td>A'Range</td>
   * <td>For a prefix A that is of an array type (after any implicit
   * dereference), or denotes a constrained array subtype:<br>
   * A'Range is equivalent to the range A'First .. A'Last, except that the
   * prefix A is only evaluated once.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-6-2.html">3.6.2.</a>
   *      </td>
   *      </tr>
   * 
   *      <tr>
   *      <td>S'Range</td> <td>For every scalar subtype S:<br>
   *      S'Range is equivalent to the range S'First .. S'Last.
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5.html">3.5.</a>
   *      </td>
   *      </tr>
   * 
   *      <tr>
   *      <td>A'Range(N)</td> <td>For a prefix A that is of an array type (after
   *      any implicit dereference), or denotes a constrained array subtype:<br>
   *      A'Range(N) is equivalent to the range A'First(N) .. A'Last(N), except
   *      that the prefix A is only evaluated once.
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-6-2.html">3.6.2</a>
   *      </tr>
   *      </table>
   */
  Range {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.toList(
          Defs.ARRAY_TYPE,
          Defs.ARRAY_SUBTYPE_CONSTRAINED,
          Defs.SCALAR_SUBTYPE);
    }

    @Override
    public boolean isStatic() {
      return true;
    }

  },

  /**
   * For every subtype S of a floating point type T:<br>
   * S'Remainder denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Remainder (X, Y : T) 
   *   return T
   * </pre>
   * 
   * For nonzero Y, let v be the value X - n * Y, where n is the integer nearest
   * to the exact value of X/Y; if |n-X/Y| = 1/2,then n is chosen to be even. If
   * v is a machine number of the type T, the function yields v; otherwise, it
   * yields zero. Constraint_Error is raised if Y is zero. A zero result has the
   * sign of X when S'Signed_Zeros is True.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Remainder {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

    @Override
    public boolean throwsConstraintError() {
      return true;
    }
  },

  /**
   * For every subtype S of a floating point type T:<br>
   * S'Rounding denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Rounding (X : T) 
   *   return T
   * </pre>
   * 
   * The function yields the integral value nearest to X, rounding away from
   * zero if X lies exactly halfway between two integers. A zero result has the
   * sign of X when S'Signed_Zeros is True.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-A-5-3.html">A.5.3</a>
   */
  Rounding {

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.FLOAT_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

  },

  // FIXME: got bored, still need to define these

  /*
    Safe_First, // S
    Safe_Last, // S
    Scaling, // S
    Signed_Zeros, // S
    Size, // S + X
    Small, // S
   */

  /**
   * For every scalar subtype S: <br>
   * S'Succ denotes a function with the following specification:<br>
   * 
   * <pre>
   * function S'Succ(Arg : S'Base) 
   *   return S'Base
   * </pre>
   * 
   * For an enumeration type, the function returns the value whose position
   * number is one more than that of the value of Arg; Constraint_Error is
   * raised if there is no such value of the type.
   * <ul>
   * <li>For an integer type, the function returns the result of adding one to
   * the value of Arg.
   * <li>For a fixed point type, the function returns the result of adding small
   * to the value of Arg.
   * <li>For a floating point type, the function returns the machine number (as
   * defined in <a
   * href="http://www.adaic.org/standards/95lrm/html/RM-3-5-7.html">3.5.7</a>)
   * immediately above the value of Arg;
   * </ul>
   * Constraint_Error is raised if there is no such machine number.
   * 
   * @see <a
   *      href="http://www.adaic.org/standards/95lrm/html/RM-3-5.html">3.5.</a>
   */
  Succ {

    @Override
    public AttributeReturnType getTheReturnType() {
      return AttributeReturnType.INDEX_TYPE_BASE;
    }

    @Override
    public List<AttributePrefixType> getTheValidPrefixTypes() {
      return Defs.SCALAR_SUBTYPE;
    }

    @Override
    public boolean isStatic() {
      return false;
    }

    @Override
    public boolean throwsConstraintError() {
      return true;
    }
  };

  // FIXME: got bored, still need to define these

  /*
    Truncation, // S
    Unbiased_Rounding, // S
    Val, // S
    Valid, // X
    Emax, // S
    Epsilon, // S
    Large, // S
    Mantissa, // S
    Safe_Emax, // S
    Safe_Large, // S
    Safe_Small, // S

    // the following are only used in proof contexts (Barnes p330)
    Append, //
    Tail
   */

  @Override
  public AttributeReturnType getTheReturnType() {
    return AttributeReturnType.INDEX_TYPE;
  }

  @Override
  public abstract List<AttributePrefixType> getTheValidPrefixTypes();

  @Override
  public boolean throwsConstraintError() {
    return false;
  }

  @Override
  public String toString() {
    return '$' + name();
  }
}
