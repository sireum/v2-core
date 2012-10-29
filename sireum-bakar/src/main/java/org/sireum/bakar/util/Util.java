/******************************************************************************
 * Copyright (c) 2007 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.Sun14ReflectionProvider;

public class Util {
  public static URL[] convertToURLs(final String classpaths)
      throws MalformedURLException {
    final String[] cps = classpaths.split(File.pathSeparator);
    return Util.convertToURLs(cps);
  }

  public static URL[] convertToURLs(final String[] cps)
      throws MalformedURLException {
    final URL[] rst = new URL[cps.length];
    for (int i = 0; i < cps.length; i++) {
      final File f;
      if (cps[i].endsWith(".jar") || cps[i].endsWith("/")) {
        f = new File(cps[i]);
      } else {
        f = new File(cps[i] + "/");
      }
      rst[i] = f.toURI().toURL();
    }
    return rst;
  }

  public static boolean deleteDirectory(final File dir) {
    if (dir.exists()) {
      final File[] files = dir.listFiles();
      final int size = files.length;
      for (int i = 0; i < size; i++) {
        final File file = files[i];
        if (file.isDirectory()) {
          Util.deleteDirectory(file);
        } else {
          file.delete();
        }
      }
    }
    return dir.delete();
  }

  public static int digits(int n) {
    int i = 0;
    while (n != 0) {
      n = n / 10;
      i++;
    }
    return i == 0 ? 1 : i;
  }

  public static String dumpXML(final Object o) {
    final XStream xstream = new XStream();
    return xstream.toXML(o);
  }

  /*
   * -------------------------------------------------------------------- An
   * adaptation of lookup2.c, by Bob Jenkins, December 1996, Public Domain.
   * hash(), hash2(), hash3, and mix() are externally useful functions.
   * Routines to test the hash are included if SELF_TEST is defined. You can
   * use this free for any purpose. It has no warranty.
   * --------------------------------------------------------------------
   */
  public static int[] jenkinsHashCode(final byte[] k, final int prevHash,
      final int fromIndex, final int size, final int[] result) {
    assert (k != null) && (result != null) && (result.length > 2);
    /* Set up the internal state */
    final int length = size - fromIndex;
    int len = length;
    int a = 0x9e3779b9;
    int b = a; /* the golden ratio; an arbitrary value */
    int c = prevHash; /* the previous hash value */
    int i = fromIndex;

    /*---------------------------------------- handle most of the key */
    while (len >= 12) {
      a += (k[0 + i] + (k[1 + i] << 8) + (k[2 + i] << 16) + (k[3 + i] << 24));
      b += (k[4 + i] + (k[5 + i] << 8) + (k[6 + i] << 16) + (k[7 + i] << 24));
      c += (k[8 + i] + (k[9 + i] << 8) + (k[10 + i] << 16) + (k[11 + i] << 24));
      a -= b;
      a -= c;
      a ^= (c >>> 13);
      b -= c;
      b -= a;
      b ^= (a << 8);
      c -= a;
      c -= b;
      c ^= (b >>> 13);
      a -= b;
      a -= c;
      a ^= (c >>> 12);
      b -= c;
      b -= a;
      b ^= (a << 16);
      c -= a;
      c -= b;
      c ^= (b >>> 5);
      a -= b;
      a -= c;
      a ^= (c >>> 3);
      b -= c;
      b -= a;
      b ^= (a << 10);
      c -= a;
      c -= b;
      c ^= (b >>> 15);
      i += 12;
      len -= 12;
    }

    /*------------------------------------- handle the last 11 bytes */
    c += length;
    switch (len)
    /* all the case statements fall through */
    {
      case 11:
        c += (k[10 + i] << 24);
      case 10:
        c += (k[9 + i] << 16);
      case 9:
        c += (k[8 + i] << 8);
        /* the first byte of c is reserved for the length */
      case 8:
        b += (k[7 + i] << 24);
      case 7:
        b += (k[6 + i] << 16);
      case 6:
        b += (k[5 + i] << 8);
      case 5:
        b += k[4 + i];
      case 4:
        a += (k[3 + i] << 24);
      case 3:
        a += (k[2 + i] << 16);
      case 2:
        a += (k[1 + i] << 8);
      case 1:
        a += k[0 + i];
        /* case 0: nothing left to add */
    }
    a -= b;
    a -= c;
    a ^= (c >>> 13);
    b -= c;
    b -= a;
    b ^= (a << 8);
    c -= a;
    c -= b;
    c ^= (b >>> 13);
    a -= b;
    a -= c;
    a ^= (c >>> 12);
    b -= c;
    b -= a;
    b ^= (a << 16);
    c -= a;
    c -= b;
    c ^= (b >>> 5);
    a -= b;
    a -= c;
    a ^= (c >>> 3);
    b -= c;
    b -= a;
    b ^= (a << 10);
    c -= a;
    c -= b;
    c ^= (b >>> 15);
    /*-------------------------------------------- report the result */
    result[0] = a;
    result[1] = b;
    result[2] = c;
    return result;
  }

  public static int[] jenkinsHashCode(final int[] k, final int prevHash,
      final int fromIndex, final int size, final int[] result) {
    assert (k != null) && (result != null) && (result.length > 2);
    /* Set up the internal state */
    final int length = size - fromIndex;
    int len = length;
    int a = 0x9e3779b9; /* the golden ratio; an arbitrary value */
    int b = a;
    int c = prevHash; /* the previous hash value */
    int i = fromIndex;

    /*---------------------------------------- handle most of the key */
    while (len >= 3) {
      a += k[0 + i];
      b += k[1 + i];
      c += k[2 + i];
      a -= b;
      a -= c;
      a ^= (c >>> 13);
      b -= c;
      b -= a;
      b ^= (a << 8);
      c -= a;
      c -= b;
      c ^= (b >>> 13);
      a -= b;
      a -= c;
      a ^= (c >>> 12);
      b -= c;
      b -= a;
      b ^= (a << 16);
      c -= a;
      c -= b;
      c ^= (b >>> 5);
      a -= b;
      a -= c;
      a ^= (c >>> 3);
      b -= c;
      b -= a;
      b ^= (a << 10);
      c -= a;
      c -= b;
      c ^= (b >>> 15);
      i += 3;
      len -= 3;
    }

    /*-------------------------------------- handle the last 2 ub4's */
    c += length;
    switch (len)
    /* all the case statements fall through */
    {
    /* c is reserved for the length */
      case 2:
        b += k[1 + i];
      case 1:
        a += k[0 + i];
        /* case 0: nothing left to add */
    }
    a -= b;
    a -= c;
    a ^= (c >>> 13);
    b -= c;
    b -= a;
    b ^= (a << 8);
    c -= a;
    c -= b;
    c ^= (b >>> 13);
    a -= b;
    a -= c;
    a ^= (c >>> 12);
    b -= c;
    b -= a;
    b ^= (a << 16);
    c -= a;
    c -= b;
    c ^= (b >>> 5);
    a -= b;
    a -= c;
    a ^= (c >>> 3);
    b -= c;
    b -= a;
    b ^= (a << 10);
    c -= a;
    c -= b;
    c ^= (b >>> 15);
    /*-------------------------------------------- report the result */
    result[0] = a;
    result[1] = b;
    result[2] = c;
    return result;
  }

  public static String loadTextFile(final String filePath) throws IOException {
    final File file = new File(filePath);
    if (!file.exists()) {
      return null;
    }
    final int size = (int) file.length();
    assert size >= 0;
    final char[] buffer = new char[size];
    final FileReader fr = new FileReader(filePath);
    fr.read(buffer);
    fr.close();
    return new String(buffer);
  }

  @SuppressWarnings("unchecked")
  public static <O> O loadXML(final File file) {
    O result = null;
    try {
      final FileReader fr = new FileReader(file.getAbsolutePath());
      final XStream xstream = Util.newXStream();
      result = (O) xstream.fromXML(fr);
      fr.close();
      return result;
    } catch (final Exception e) {
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public static <O> O loadXML(final String text) {
    final XStream xstream = Util.newXStream();
    return (O) xstream.fromXML(text);
  }

  public static boolean mkdirs(final File dir) {
    if (dir.isFile()) {
      return false;
    }
    return Util.mkdirsRecurse(dir);
  }

  private static boolean mkdirsRecurse(final File dir) {
    if (dir.exists()) {
      return false;
    }
    Util.mkdirsRecurse(dir.getParentFile());
    return dir.mkdir();
  }

  public static XStream newXStream() {
    return new XStream(new Sun14ReflectionProvider());
  }

  @SuppressWarnings("rawtypes")
  public static boolean nonNullElements(final Collection c) {
    if (c == null) {
      return true;
    }
    for (final Object o : c) {
      if (!Util.nonNullElements(o)) {
        return false;
      }
    }
    return true;
  }

  @SuppressWarnings("rawtypes")
  public static boolean nonNullElements(final Map<Object, Object> m) {
    if (m == null) {
      return true;
    }
    for (final Entry o : m.entrySet()) {
      if (!Util.nonNullElements(o.getKey())) {
        return false;
      }
      if (!Util.nonNullElements(o.getValue())) {
        return false;
      }
    }
    return true;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static boolean nonNullElements(final Object o) {
    if (o == null) {
      return true;
    } else if (o instanceof Collection) {
      return Util.nonNullElements((Collection) o);
    } else if (o instanceof Map) {
      return Util.nonNullElements((Map) o);
    } else if (o.getClass().isArray()) {
      final int size = Array.getLength(o);
      for (int i = 0; i < size; i++) {
        if (Array.get(o, i) == null) {
          return false;
        }
      }
      return true;
    } else {
      return o != null;
    }
  }

  public static String toString(final int i, final int digits) {
    assert i >= 0;
    final int iDigits = Util.digits(i);
    assert digits >= iDigits;
    final int numOfZeros = digits - iDigits;
    final StringBuilder sb = new StringBuilder();
    for (int j = 0; j < numOfZeros; j++) {
      sb.append('0');
    }
    sb.append(i);
    return sb.toString();
  }

  /**
   * Calculate how many bits wide a number is, i.e. position of highest 1 bit.
   * Fully unraveled binary search method.
   * 
   * @return p, where 2**p is first power of two >= n. e.g. binary 0001_0101 ->
   *         5, 0xffffffff -> 32, 0 -> 0, 1 -> 1, 2 -> 2, 3 -> 2, 4 -> 3
   * @param n
   *          The number to check bits width on. (author Dirk Bosmans
   *          Dirk.Bosmans@tijd.com)
   */
  public static int widthInBits(final int n) {
    if (n < 0) {
      return 32;
    }

    if (n > 0x0000ffff) {
      if (n > 0x00ffffff) {
        if (n > 0x0fffffff) {
          if (n > 0x3fffffff) {
            // if ( n > 0x7fffffff )
            // return 32
            // else
            return 31;
          } else {
            // !( n > 0x3fffffff )
            if (n > 0x1fffffff) {
              return 30;
            } else {
              return 29;
            }
          }
        } else {
          // !( n > 0x0fffffff )
          if (n > 0x03ffffff) {
            if (n > 0x07ffffff) {
              return 28;
            } else {
              return 27;
            }
          } else {
            // !( n > 0x03ffffff )
            if (n > 0x01ffffff) {
              return 26;
            } else {
              return 25;
            }
          }
        }
      } else {
        // !( n > 0x00ffffff )
        if (n > 0x000fffff) {
          if (n > 0x003fffff) {
            if (n > 0x007fffff) {
              return 24;
            } else {
              return 23;
            }
          } else {
            // !( n > 0x003fffff )
            if (n > 0x001fffff) {
              return 22;
            } else {
              return 21;
            }
          }
        } else {
          // !( n > 0x000fffff )
          if (n > 0x0003ffff) {
            if (n > 0x0007ffff) {
              return 20;
            } else {
              return 19;
            }
          } else {
            // !( n > 0x0003ffff )
            if (n > 0x0001ffff) {
              return 18;
            } else {
              return 17;
            }
          }
        }
      }
    } else {
      // !( n > 0x0000ffff )
      if (n > 0x000000ff) {
        if (n > 0x00000fff) {
          if (n > 0x00003fff) {
            if (n > 0x00007fff) {
              return 16;
            } else {
              return 15;
            }
          } else {
            // !( n > 0x00003fff )
            if (n > 0x00001fff) {
              return 14;
            } else {
              return 13;
            }
          }
        } else {
          // !( n > 0x00000fff )
          if (n > 0x000003ff) {
            if (n > 0x000007ff) {
              return 12;
            } else {
              return 11;
            }
          } else {
            // !( n > 0x000003ff )
            if (n > 0x000001ff) {
              return 10;
            } else {
              return 9;
            }
          }
        }
      } else {
        // !( n > 0x000000ff )
        if (n > 0x0000000f) {
          if (n > 0x0000003f) {
            if (n > 0x0000007f) {
              return 8;
            } else {
              return 7;
            }
          } else {
            // !( n > 0x0000003f )
            if (n > 0x0000001f) {
              return 6;
            } else {
              return 5;
            }
          }
        } else {
          // !( n > 0x0000000f )
          if (n > 0x00000003) {
            if (n > 0x00000007) {
              return 4;
            } else {
              return 3;
            }
          } else {
            // !( n > 0x00000003 )
            if (n > 0x00000001) {
              return 2;
            }

            return n;

            /*
             * else if ( n > 0x00000000 ) return 1; else return 0;
             */
          }
        }
      }
    }
  }

  /**
   * Calculate how many bits wide a number is, i.e. position of highest 1 bit.
   * Fully unraveled binary search method.
   * 
   * @return p, where 2**p is first power of two >= n. e.g. binary 0001_0101 ->
   *         5, 0xffffffff -> 32, 0 -> 0, 1 -> 1, 2 -> 2, 3 -> 2, 4 -> 3
   * @param n
   *          The number to check bits width on.
   */
  public static int widthInBits(final long n) {
    return Util.widthInBits((int) n >> 32)
        + Util.widthInBits((int) n & 0xffffffffL);
  }

  public static void writeTextFile(final String filePath, final String code)
      throws IOException {
    final FileWriter fw = new FileWriter(filePath);
    fw.write(code);
    fw.close();
    System.out.println(filePath + " was generated.");
    System.out.flush();
  }
}
