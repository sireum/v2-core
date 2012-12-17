package org.sireum.bakar.xml;

import java.lang.reflect.Field;

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
    final Field[] fields = this.getClass().getDeclaredFields();
    this.cache = new Object[fields.length];

    int i = 0;
    for (final Field f : fields) {
      try {
        final boolean isAccessible = f.isAccessible();
        f.setAccessible(true);
        this.cache[i++] = f.get(this);
        f.setAccessible(isAccessible);
      } catch (IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }
}
