/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.selection.model;

public class SelectionVisitor<G> {
  protected G g;

  public SelectionVisitor(final G g) {
    assert g != null;
    this.g = g;
  }

  protected void visit(final Object o) {
    assert false;
  }

  public void visit(final SelectionNode o) {
    assert o != null;
    switch (o.getDescriptor()) {
      case Caret.DESCRIPTOR:
        visitCaret((Caret) o);
        break;

      case NullSelection.DESCRIPTOR:
        visitNullSelection((NullSelection) o);
        break;

      case RegionSelection.DESCRIPTOR:
        visitRegionSelection((RegionSelection) o);
        break;

      default:
        assert false;
        break;
    }
  }

  protected void visitCaret(final Caret o) {
    assert o != null;
    visitSelectionNode(o);
  }

  protected void visitNullSelection(final NullSelection o) {
    assert o != null;
    visitSelectionNode(o);
  }

  protected void visitRegionSelection(final RegionSelection o) {
    assert o != null;
    visitSelectionNode(o);

    visit(o.theStartCaret);

    visit(o.theEndCaret);
  }

  protected void visitSelectionNode(final SelectionNode o) {
    assert o != null;
  }

}
