/******************************************************************************
 * Copyright (c) 2007 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar.util;

public class NTuple
    implements Tuple {
  protected Object[] elements;

  public NTuple() {
  }

  public NTuple(final Object... elements) {
    setElements(elements);
  }

  @Override
  public boolean equals(final Object o) {
    if (o instanceof NTuple) {
      final NTuple other = (NTuple) o;
      if (((this.elements == null) && (other.elements != null))
          || ((this.elements != null) && (other.elements == null))) {
        return false;
      }
      if (this.elements == other.elements) {
        return true;
      }
      if (this.elements.length != other.elements.length) {
        return false;
      }
      final int count = this.elements.length;
      for (int i = 0; i < count; i++) {
        if ((this.elements[i] != null)
            && !this.elements[i].equals(other.elements[i])) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> A get(final int index) {
    if (index >= this.elements.length) {
      throw new IllegalArgumentException();
    }
    return (A) this.elements[index];
  }

  public Object[] getElements() {
    return this.elements;
  }

  @Override
  public int hashCode() {
    int result = 0;
    int multiplier = 0;
    if (this.elements != null) {
      for (int i = this.elements.length - 1; i >= 0; i--, multiplier <<= 1) {
        result += (this.elements[i] == null ? 0 : this.elements[i].hashCode()
            * multiplier);
      }
    }
    return result;
  }

  @Override
  public <A> void set(final int index, final A a) {
    assert a != null;
    if (index >= this.elements.length) {
      throw new IllegalArgumentException();
    }
    this.elements[index] = a;
  }

  public void setElements(final Object[] elements) {
    assert (elements != null) && Util.nonNullElements(elements);
    this.elements = elements;
  }

  @Override
  public int size() {
    if (this.elements != null) {
      return this.elements.length;
    }
    return 0;
  }
}
