/******************************************************************************
 * Copyright (c) 2007 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar.util;

import java.util.ArrayList;

public class Stack<E> {

  protected final ArrayList<E> list;
  protected int lastIndex = -1;

  public Stack() {
    this.list = new ArrayList<E>();
  }

  public Stack(final int initialCapacity) {
    this.list = new ArrayList<E>(initialCapacity);
  }

  public void clear() {
    this.list.clear();
  }

  public ArrayList<E> getList() {
    return this.list;
  }

  public boolean isEmpty() {
    return this.lastIndex == -1;
  }

  public E peek() {
    assert this.lastIndex >= 0;
    return this.list.get(this.lastIndex);
  }

  public E peekFromTop(final int index) {
    final int i = this.lastIndex - index;
    assert i >= 0;
    return this.list.get(i);
  }

  public E pop() {
    assert this.lastIndex >= 0;
    final E result = this.list.remove(this.lastIndex);
    this.lastIndex--;
    return result;
  }

  public void push(final E e) {
    this.list.add(e);
    this.lastIndex++;
  }

  public int size() {
    return this.lastIndex + 1;
  }

  public Object[] toArray() {
    return this.list.toArray();
  }
}
