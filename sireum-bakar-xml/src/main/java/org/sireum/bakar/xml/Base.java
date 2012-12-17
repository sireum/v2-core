package org.sireum.bakar.xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.sireum.util.Visitable;

public abstract class Base
    implements Visitable {

  private Object[] cache = null;

  @Override
  public Object[] getChildren() {
    if (this.cache == null) {
      populate();
    }
    return this.cache;
  }

  @Override
  public int getNumOfChildren() {
    if (this.cache == null) {
      populate();
    }
    return this.cache.length;
  }

  private void populate() {
    this.cache = new Object[this.getClass().getDeclaredFields().length];

    // Note: using jaxb getter methods rather than accessing the fields 
    // directly as the getters for lists types always return an empty list
    // rather than null
    int i = 0;
    for (final Method m : this.getClass().getDeclaredMethods()) {
      try {
        if (m.getName().startsWith("get")) {
          this.cache[i++] = m.invoke(this);
        }
      } catch (IllegalAccessException | IllegalArgumentException
          | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
    assert (i == this.cache.length);
  }
}
