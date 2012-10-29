/******************************************************************************
 * Copyright (c) 2007 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar.util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class ComparatorUtil {

  final static BigInteger MAX_INT = BigInteger.valueOf(Integer.MAX_VALUE);
  final static BigInteger MIN_INT = BigInteger.valueOf(Integer.MIN_VALUE);
  final static BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
  final static BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);

  public static int compare(final BigInteger bi1, final BigInteger bi2) {
    return bi1.compareTo(bi2);
  }

  public static int compare(final boolean b1, final boolean b2) {
    if (b1 == b2) {
      return 0;
    } else if (b1) {
      return 1;
    } else {
      return -1;
    }
  }

  public static int compare(final Boolean b1, final Boolean b2) {
    if (b1 == b2) {
      return 0;
    } else if (b1 == null) {
      return -1;
    } else if (b2 == null) {
      return 1;
    }
    return ComparatorUtil.compare(b1.booleanValue(), b2.booleanValue());
  }

  public static int compare(final Byte b1, final Byte b2) {
    if (b1 == b2) {
      return 0;
    } else if (b1 == null) {
      return -1;
    } else if (b2 == null) {
      return 1;
    }
    return ComparatorUtil.compare(b1.intValue(), b2.intValue());
  }

  public static int compare(final Character c1, final Character c2) {
    if (c1 == c2) {
      return 0;
    } else if (c1 == null) {
      return -1;
    } else if (c2 == null) {
      return 1;
    }
    return ComparatorUtil.compare(c1.charValue(), c2.charValue());
  }

  public static int compare(final double d1, final double d2) {
    if ((d1 < d2) && (d1 > d2)) {
      return 0;
    } else if (d1 > d2) {
      return 1;
    } else {
      return -1;
    }
  }

  public static int compare(final Double d1, final Double d2) {
    if (d1 == d2) {
      return 0;
    } else if (d1 == null) {
      return -1;
    } else if (d2 == null) {
      return 1;
    }
    return ComparatorUtil.compare(d1.doubleValue(), d2.doubleValue());
  }

  public static int compare(final float f1, final float f2) {
    if ((f1 < f2) && (f1 > f2)) {
      return 0;
    } else if (f1 > f2) {
      return 1;
    } else {
      return -1;
    }
  }

  public static int compare(final Float f1, final Float f2) {
    if (f1 == f2) {
      return 0;
    } else if (f1 == null) {
      return -1;
    } else if (f2 == null) {
      return 1;
    }
    return ComparatorUtil.compare(f1.floatValue(), f2.floatValue());
  }

  public static int compare(final int i1, final int i2) {
    if (i1 < i2) {
      return -1;
    } else if (i1 > i2) {
      return 1;
    } else {
      return 0;
    }
  }

  public static int compare(final Integer i1, final Integer i2) {
    if (i1 == i2) {
      return 0;
    } else if (i1 == null) {
      return -1;
    } else if (i2 == null) {
      return 1;
    }
    return ComparatorUtil.compare(i1.intValue(), i2.intValue());
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static int compare(final List l1, final List l2, final Comparator c) {
    if (l1 == l2) {
      return 0;
    } else if (l1 == null) {
      return -1;
    } else if (l2 == null) {
      return 1;
    }
    int count;
    int result = ComparatorUtil.compare(count = l1.size(), l2.size());
    if (result != 0) {
      return result;
    }
    for (int i = 0; i < count; i++) {
      result = c.compare(l1.get(i), l2.get(i));
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

  public static int compare(final long l1, final long l2) {
    if (l1 < l2) {
      return -1;
    } else if (l1 > l2) {
      return 1;
    } else {
      return 0;
    }
  }

  public static int compare(final Long l1, final Long l2) {
    if (l1 == l2) {
      return 0;
    } else if (l1 == null) {
      return -1;
    } else if (l2 == null) {
      return 1;
    }
    return ComparatorUtil.compare(l1.longValue(), l2.longValue());
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static int compare(final Map m1, final Map m2, final Comparator c) {
    if (m1 == m2) {
      return 0;
    } else if (m1 == null) {
      return -1;
    } else if (m2 == null) {
      return 1;
    }
    int count;
    int result = ComparatorUtil.compare(count = m1.size(), m2.size());
    final Object[] ks1 = m1.keySet().toArray();
    if (!(m1 instanceof TreeMap)) {
      Arrays.sort(ks1, c);
    }
    final Object[] ks2 = m2.keySet().toArray();
    if (!(m1 instanceof TreeMap)) {
      Arrays.sort(ks2, c);
    }
    for (int i = 0; i < count; i++) {
      final Object k1 = ks1[i];
      final Object k2 = ks2[i];
      result = c.compare(k1, k2);
      if (result != 0) {
        return result;
      }
      result = c.compare(m1.get(k1), m2.get(k2));
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static int compare(final Set s1, final Set s2, final Comparator c) {
    if (s1 == s2) {
      return 0;
    } else if (s1 == null) {
      return -1;
    } else if (s2 == null) {
      return 1;
    }
    int count;
    int result = ComparatorUtil.compare(count = s1.size(), s2.size());
    if (result != 0) {
      return result;
    }
    final Object[] os1 = s1.toArray();
    if (!(s1 instanceof TreeSet)) {
      Arrays.sort(os1, c);
    }
    final Object[] os2 = s2.toArray();
    if (!(s1 instanceof TreeSet)) {
      Arrays.sort(os2, c);
    }
    for (int i = 0; i < count; i++) {
      result = c.compare(os1[i], os2[i]);
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

  public static int compare(final Short s1, final Short s2) {
    if (s1 == s2) {
      return 0;
    } else if (s1 == null) {
      return -1;
    } else if (s2 == null) {
      return 1;
    }
    return ComparatorUtil.compare(s1.intValue(), s2.intValue());
  }

  public static int compare(final String s1, final String s2) {
    if (s1 == s2) {
      return 0;
    } else if (s1 == null) {
      return -1;
    } else if (s2 == null) {
      return 1;
    }
    return s1.compareTo(s2);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static int compare(final Tuple t1, final Tuple t2, final Comparator c) {
    if (t1 == t2) {
      return 0;
    } else if (t1 == null) {
      return -1;
    } else if (t2 == null) {
      return 1;
    }
    int count;
    int result = ComparatorUtil.compare(count = t1.size(), t2.size());
    if (result != 0) {
      return result;
    }
    for (int i = 0; i < count; i++) {
      result = c.compare(t1.get(i), t2.get(i));
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

  public static boolean isWithinMaxInt(final BigInteger bi) {
    return ComparatorUtil.MAX_INT.compareTo(bi) >= 0;
  }

  public static boolean isWithinMaxLong(final BigInteger bi) {
    return ComparatorUtil.MAX_LONG.compareTo(bi) >= 0;
  }

  public static boolean isWithinMinInt(final BigInteger bi) {
    return ComparatorUtil.MIN_INT.compareTo(bi) <= 0;
  }

  public static boolean isWithinMinLong(final BigInteger bi) {
    return ComparatorUtil.MIN_LONG.compareTo(bi) <= 0;
  }
}
