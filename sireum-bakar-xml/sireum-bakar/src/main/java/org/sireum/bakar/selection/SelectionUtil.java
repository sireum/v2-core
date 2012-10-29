/******************************************************************************
 * Copyright (c) 2009 Belt, Kansas State University, and others.              *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar.selection;

import org.sireum.bakar.selection.model.Caret;
import org.sireum.bakar.selection.model.RegionSelection;
import org.sireum.bakar.util.Path;

public final class SelectionUtil {

  private static final String NONE = "NONE";

  public static final Caret clone(final Caret c) {
    if (c == null) {
      return null;
    }

    final Caret ret = new Caret();
    ret.setTheOffset(c.getTheOffset());
    ret.setTheLine(c.getTheLine());
    ret.setTheCol(c.getTheCol());
    return ret;
  }

  public static final RegionSelection clone(final RegionSelection rs) {
    final RegionSelection ret = new RegionSelection();
    ret.setTheStartCaret(SelectionUtil.clone(rs.getTheStartCaret()));
    ret.setTheEndCaret(SelectionUtil.clone(rs.getTheEndCaret()));
    ret.setTheOptionalSource(rs.getTheOptionalSource());
    return ret;
  }

  public static int compare(final Caret a, final Caret b) {
    if (a == b) {
      return 0;
    }

    if (a == null) {
      return -1;
    } else if (b == null) {
      return 1;
    }

    if (SelectionUtil.isEmpty(a)) {
      if (SelectionUtil.isEmpty(b)) {
        return 0;
      } else {
        return -1;
      }
    } else if (SelectionUtil.isEmpty(b)) {
      return 1;
    }

    // both non-null and non-empty so compare their offsets
    return org.sireum.bakar.util.ComparatorUtil.compare(
        a.getTheOffset(),
        b.getTheOffset());
  }

  /**
   * 
   * @param a
   * @param b
   * @return Note: only compares the start carets of {@code a} and {@code b}
   *         <ul>
   *         <li>-1 if o1 starts before o2
   *         <li>0 if o1 and o2 both start at the same offset
   *         <li>1 if o1 starts after o2
   *         <ul>
   */
  public static int compare(final RegionSelection a, final RegionSelection b) {
    if (a == b) {
      return 0; // handles case where both are null
    }

    // perhaps one is non-null
    if (a == null) {
      return -1;
    } else if (b == null) {
      return 1;
    }

    // both non-empty

    // sanity check to ensure that a and b come from the same file
    assert 0 == org.sireum.bakar.util.ComparatorUtil.compare(
        a.getTheOptionalSource(),
        b.getTheOptionalSource());

    return SelectionUtil.compare(a.getTheStartCaret(), b.getTheStartCaret());
  }

  public static final boolean containedBy(final RegionSelection a,
      final RegionSelection b) {
    return SelectionUtil.greaterEqual(
        a.getTheStartCaret(),
        b.getTheStartCaret())
        && SelectionUtil.lessEqual(a.getTheEndCaret(), b.getTheEndCaret());
  }

  public static final boolean contains(final RegionSelection a,
      final RegionSelection b) {
    return SelectionUtil.lessEqual(a.getTheStartCaret(), b.getTheStartCaret())
        && SelectionUtil.greaterEqual(a.getTheEndCaret(), b.getTheEndCaret());
  }

  protected static final String getPilarAnnotation(final Caret c) {
    return SelectionUtil.getPilarAnnotation(c, false);
  }

  protected static final String getPilarAnnotation(final Caret c,
      final boolean incrementColOffset) {
    final int inc = incrementColOffset ? 1 : 0;

    final StringBuilder sb = new StringBuilder("@Caret(\n");
    sb.append("  theLine = ");
    sb.append(c.getTheLine());
    sb.append(",\n");

    sb.append("  theCol = ");
    sb.append(c.getTheCol() + inc);
    sb.append(",\n");

    sb.append("  theOffset = ");
    sb.append(c.getTheOffset());

    sb.append(")");
    return sb.toString();
  }

  public static final String getPilarAnnotation(final RegionSelection s) {
    return SelectionUtil.getPilarAnnotation(s, false, false);
  }

  public static final String getPilarAnnotation(final RegionSelection s,
      final boolean suppressFilePath, final boolean incrementColOffset) {
    assert s != null;

    if (SelectionUtil.isEmpty(s)) {
      return "@EMPTY_REGION_SELECTION";
    }

    final StringBuilder sb = new StringBuilder("@RegionSelection(");

    sb.append("\n  theStartCaret = \n  ");
    sb.append(SelectionUtil.getPilarAnnotation(
        s.getTheStartCaret(),
        incrementColOffset));
    sb.append(",\n");

    sb.append("  theEndCaret = \n  ");
    sb.append(SelectionUtil.getPilarAnnotation(
        s.getTheEndCaret(),
        incrementColOffset));
    sb.append(",\n");

    String source;
    if (s.getTheOptionalSource() == null) {
      source = '@' + SelectionUtil.NONE;
    } else {
      source = "\""
          + (suppressFilePath ? Path.getFilename(s.getTheOptionalSource()) : s
              .getTheOptionalSource()) + "\"";
    }
    sb.append("  theOptionalSource = " + source);

    sb.append("\n)");
    return sb.toString();
  }

  public static final boolean greaterEqual(final Caret a, final Caret b) {
    if (a.getTheLine() > b.getTheLine()) {
      return true;
    } else if ((a.getTheLine() == b.getTheLine())
        && (a.getTheCol() >= b.getTheCol())) {
      return true;
    }
    return false;
  }

  /**
   * Non-destructively intersects a and b. They must originate from the same
   * file
   * 
   * @param a
   * @param b
   * @return the intersected selection
   */
  public static final RegionSelection intersect(final RegionSelection a,
      final RegionSelection b) {
    if (a == null) {
      return b;
    }
    if (b == null) {
      return a;
    }

    if (SelectionUtil.isEmpty(a)) {
      return SelectionUtil.clone(b);
    }

    if (SelectionUtil.isEmpty(b)) {
      return SelectionUtil.clone(a);
    }

    if (SelectionUtil.containedBy(a, b)) {
      return SelectionUtil.clone(b);
    }
    if (SelectionUtil.containedBy(b, a)) {
      return SelectionUtil.clone(a);
    }

    final Caret acStart = a.getTheStartCaret();
    final Caret acEnd = a.getTheEndCaret();

    final Caret bcStart = b.getTheStartCaret();
    final Caret bcEnd = b.getTheEndCaret();

    final RegionSelection ret = new RegionSelection();

    if ((a.getTheOptionalSource() != null)
        || (b.getTheOptionalSource() != null)) {
      assert a.getTheOptionalSource().equals(b.getTheOptionalSource());
      ret.setTheOptionalSource(a.getTheOptionalSource());
    }

    if (SelectionUtil.lessEqual(acStart, bcStart)) {
      ret.setTheStartCaret(SelectionUtil.clone(acStart));
    } else {
      ret.setTheStartCaret(SelectionUtil.clone(bcStart));
    }

    if (SelectionUtil.greaterEqual(acEnd, bcEnd)) {
      ret.setTheEndCaret(SelectionUtil.clone(acEnd));
    } else {
      ret.setTheEndCaret(SelectionUtil.clone(bcEnd));
    }

    assert SelectionUtil.contains(ret, a) && SelectionUtil.contains(ret, b);
    return ret;
  }

  public static final boolean isEmpty(final Caret c) {
    if (c == null) {
      return true;
    }

    return (c.getTheLine() == -1) || (c.getTheCol() == -1)
        || (c.getTheOffset() == -1);
  }

  public static final boolean isEmpty(final RegionSelection c) {
    if (c == null) {
      return true;
    }

    return SelectionUtil.isEmpty(c.getTheStartCaret())
        || SelectionUtil.isEmpty(c.getTheEndCaret());
  }

  private static final boolean lessEqual(final Caret a, final Caret b) {
    if (a.getTheLine() < b.getTheLine()) {
      return true;
    } else if ((a.getTheLine() == b.getTheLine())
        && (a.getTheCol() <= b.getTheCol())) {
      return true;
    }
    return false;
  }

  public static final Caret newCaret() {
    return new Caret();
  }

  public static final Caret newCaret(final int lineNumber,
      final int charOffset, final int offset) {
    final Caret c = new Caret();
    c.setTheLine(lineNumber);
    c.setTheCol(charOffset);
    c.setTheOffset(offset);
    return c;
  }

  public static final RegionSelection newRegionSelection() {
    return SelectionUtil.newRegionSelection(null);
  }

  public static final RegionSelection newRegionSelection(final Caret a,
      final Caret b, final String theOptionalSource) {
    final RegionSelection rs = new RegionSelection();
    rs.setTheStartCaret(a);
    rs.setTheEndCaret(b);
    String optSrc = theOptionalSource;
    if (optSrc != null) {
      optSrc = optSrc.replaceAll("\\\\", "/");
    }
    rs.setTheOptionalSource(optSrc);
    return rs;
  }

  public static final RegionSelection newRegionSelection(
      final String theOptionalSource) {
    return SelectionUtil.newRegionSelection(
        new Caret(),
        new Caret(),
        theOptionalSource);
  }

  public static final boolean nonEmptyIntersection(final RegionSelection a,
      final RegionSelection b) {

    if (SelectionUtil.lessEqual(a.getTheStartCaret(), b.getTheStartCaret())
        && SelectionUtil.greaterEqual(a.getTheEndCaret(), b.getTheStartCaret())) {
      return true;
    }

    if (SelectionUtil.lessEqual(a.getTheStartCaret(), b.getTheEndCaret())
        && SelectionUtil.greaterEqual(a.getTheEndCaret(), b.getTheEndCaret())) {
      return true;
    }

    return false;
  }

  public static final String toString(final Caret c) {
    if (c == null) {
      return null;
    }
    final StringBuilder sb = new StringBuilder();
    sb.append('[');
    sb.append(c.getTheLine());
    sb.append(',');
    sb.append(c.getTheCol());
    sb.append(']');
    return sb.toString();
  }

  public static String toString(final RegionSelection rs) {
    return SelectionUtil.toString(rs, true);
  }

  public static String toString(final RegionSelection rs,
      final boolean suppressFilename) {
    if (rs == null) {
      return null;
    }
    final StringBuilder sb = new StringBuilder();
    sb.append('(');
    sb.append(SelectionUtil.toString(rs.getTheStartCaret()));
    sb.append(", ");
    sb.append(SelectionUtil.toString(rs.getTheEndCaret()));
    sb.append(')');

    if (!suppressFilename) {
      sb.append('@');
      sb.append(rs.getTheOptionalSource());
    }

    return sb.toString();
  }
}
