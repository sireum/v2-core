/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.selection.model;

public class SelectionFactory {

  /**
   * @param theLine
   *          <ul>
   *          <li>{@code Default}
   * 
   *          <pre>
   * -1
   * </pre>
   * 
   *          </li>
   *          </ul>
   * @param theCol
   *          <ul>
   *          <li>{@code Default}
   * 
   *          <pre>
   * -1
   * </pre>
   * 
   *          </li>
   *          </ul>
   * @param theOffset
   *          <ul>
   *          <li>{@code Default}
   * 
   *          <pre>
   * -1
   * </pre>
   * 
   *          </li>
   *          </ul>
   */
  public static Caret newCaret(final int theLine, final int theCol,
      final int theOffset) {
    final Caret result = new Caret();
    result.setTheLine(theLine);
    result.setTheCol(theCol);
    result.setTheOffset(theOffset);
    return result;
  }

  public static NullSelection newNullSelection() {
    final NullSelection result = new NullSelection();
    return result;
  }

  /**
   * @param theStartCaret
   *          <ul>
   *          <li>{@code NonNull}</li>
   *          </ul>
   * @param theEndCaret
   *          <ul>
   *          <li>{@code NonNull}</li>
   *          </ul>
   */
  public static RegionSelection newRegionSelection(
      final String theOptionalSource, final Caret theStartCaret,
      final Caret theEndCaret) {
    final RegionSelection result = new RegionSelection();
    result.setTheOptionalSource(theOptionalSource);
    result.setTheStartCaret(theStartCaret);
    result.setTheEndCaret(theEndCaret);
    return result;
  }

  public SelectionFactory() {
  }
}
