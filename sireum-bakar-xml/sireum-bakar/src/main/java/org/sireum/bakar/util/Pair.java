/******************************************************************************
 * Copyright (c) 2007 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar.util;

public class Pair<E0, E1>
    implements Tuple {
  protected E0 e0;
  protected E1 e1;

  public Pair() {
  }

  public Pair(final E0 e0, final E1 e1) {
    assert (e0 != null) && (e1 != null);
    this.e0 = e0;
    this.e1 = e1;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public boolean equals(final Object o) {
    assert (this.e0 != null) && (this.e1 != null);
    if (o instanceof Pair) {
      final Pair other = (Pair) o;
      if (!this.e0.equals(other.e0)) {
        return false;
      }
      return (this.e1 == null) || this.e1.equals(other.e1);
    }
    return false;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> A get(final int index) {
    assert (this.e0 != null) && (this.e1 != null);
    switch (index) {
      case 0:
        return (A) this.e0;
      case 1:
        return (A) this.e1;
      default:
        throw new IllegalArgumentException();
    }
  }

  public E0 getE0() {
    return this.e0;
  }

  public E1 getE1() {
    return this.e1;
  }

  @Override
  public int hashCode() {
    assert (this.e0 != null) && (this.e1 != null);
    return ((this.e0 == null ? 0 : this.e0.hashCode()) * 2)
        + (this.e1 == null ? 0 : this.e1.hashCode());
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> void set(final int index, final A a) {
    switch (index) {
      case 0:
        this.e0 = (E0) a;
        break;
      case 1:
        this.e1 = (E1) a;
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  public void setE0(final E0 e0) {
    assert e0 != null;
    this.e0 = e0;
  }

  public void setE1(final E1 e1) {
    assert e1 != null;
    this.e1 = e1;
  }

  @Override
  public int size() {
    assert (this.e0 != null) && (this.e1 != null);
    return 2;
  }
}
