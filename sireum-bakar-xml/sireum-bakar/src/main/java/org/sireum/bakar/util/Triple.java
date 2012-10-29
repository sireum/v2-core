/******************************************************************************
 * Copyright (c) 2007 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar.util;

public class Triple<E0, E1, E2>
    implements Tuple {
  protected E0 e0;
  protected E1 e1;
  protected E2 e2;

  public Triple() {
  }

  public Triple(final E0 e0, final E1 e1, final E2 e2) {
    assert (e0 != null) && (e1 != null) && (e2 != null);
    this.e0 = e0;
    this.e1 = e1;
    this.e2 = e2;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public boolean equals(final Object o) {
    assert (this.e0 != null) && (this.e1 != null) && (this.e2 != null);
    if (o instanceof Triple) {
      final Triple other = (Triple) o;
      if (!this.e0.equals(other.e0)) {
        return false;
      }
      if (!this.e1.equals(other.e1)) {
        return false;
      }
      return this.e2.equals(other.e2);
    }
    return false;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> A get(final int index) {
    assert (this.e0 != null) && (this.e1 != null) && (this.e2 != null);
    switch (index) {
      case 0:
        return (A) this.e0;
      case 1:
        return (A) this.e1;
      case 2:
        return (A) this.e2;
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

  public E2 getE2() {
    return this.e2;
  }

  @Override
  public int hashCode() {
    assert (this.e0 != null) && (this.e1 != null) && (this.e2 != null);
    return (this.e0 == null ? 0 : this.e0.hashCode() * 4)
        + (this.e1 == null ? 0 : this.e1.hashCode() * 2)
        + (this.e2 == null ? 0 : this.e2.hashCode());
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
      case 2:
        this.e2 = (E2) a;
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

  public void setE2(final E2 e2) {
    assert e2 != null;
    this.e2 = e2;
  }

  @Override
  public int size() {
    assert (this.e0 != null) && (this.e1 != null) && (this.e2 != null);
    return 3;
  }

  @Override
  public String toString() {
    return "(" + this.e0.toString() + ", " + this.e1.toString() + ", "
        + this.e2.toString() + ")";
  }
}
