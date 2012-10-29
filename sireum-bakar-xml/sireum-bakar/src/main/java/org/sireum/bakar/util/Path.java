package org.sireum.bakar.util;

import java.io.File;
import java.net.URI;

/**
 * Methods useful for manipulating paths (represented as Strings).
 * 
 * @author chalin
 * 
 */
public class Path {

  public static final String FILE_SEP = "/";

  public static String appendFileSeparator(final String path) {
    return path.endsWith(Path.FILE_SEP) ? path : path + Path.FILE_SEP;
  }

  public static boolean ensureExists(final URI dir) {
    return dir.isAbsolute() && new File(dir.normalize()).mkdirs();
  }

  public static String getExtension(final String fileName) {
    final int pos = fileName.lastIndexOf(".");
    if (pos >= 0) {
      return fileName.substring(pos + 1);
    }
    return null;
  }

  /**
   * @param path
   * @return the text after the last FILE_SEP in path. If path does not contain
   *         FILE_SEP then path is returned
   */
  public static String getFilename(final String path) {
    //final int pos = path.lastIndexOf(FILE_SEP);
    final int pos = path.lastIndexOf(Path.FILE_SEP);
    if (pos != -1) {
      return path.substring(pos + 1);
    }
    return path;
  }

  public static String getFilenameWithoutExtension(final String n) {
    final String ret = Path.getFilename(n);
    final int pos = ret.lastIndexOf('.');
    if (pos != -1) {
      return ret.substring(0, pos);
    }
    return ret;
  }

  /**
   * @param path
   * @return the text up to and including the last FILE_SEP in path. If path
   *         does not contain FILE_SEP then path is returned
   */
  public static String getPath(final String path) {
    final int pos = path.lastIndexOf(Path.FILE_SEP);
    if (pos != -1) {
      return path.substring(0, pos + 1);
    }
    return path;
  }

  /**
   * Using the path to clazz as a starting point, replace "/bin/" by "/src/" and
   * return the result. Note that in general the result ends with a FILE_SEP.
   * 
   * @param clazz
   * @return
   */
  public static String getSourceDirectory(final Class<?> clazz) {
    return getSourceDirectory(clazz, "src");
  }

  public static String getSourceDirectory(final Class<?> clazz, final String src) {
    assert clazz != null;
    final String bin = Path.FILE_SEP + "bin" + Path.FILE_SEP + "org"
        + Path.FILE_SEP;
    final String srcr = Path.FILE_SEP + src + Path.FILE_SEP + "org"
        + Path.FILE_SEP;

    // Note: originally used "" as the resource which is incorrect.  If this 
    //       method was called using a class that is in a different project 
    //       than the current one then it returned a path relative to the 
    //       current project.  Having it find the class file for clazz ensures
    //       the returned path will be correct
    final String path = clazz.getResource(clazz.getSimpleName() + ".class")
        .getPath();
    return Path.getPath(path.replaceFirst(bin, srcr));
  }

  public static String getURI(final String path) {
    final File f = new File(path);
    if (!f.exists() || !f.isFile()) {
      return path;
    }
    return f.toURI().toString();
  }

  /**
   * Assuming that clazz is loaded from the path p = "/d1/d2/.../di/dj/.../dn"
   * and dirName is "di" then this returns "dj/.../dn". If dirName is not in p
   * then returns p.
   */
  public static String pathAfterDir(final Class<?> clazz, final String dirName) {
    assert clazz != null;
    final String dir = Path.FILE_SEP + dirName + Path.FILE_SEP;
    return Path.pathAfterMatch(clazz.getResource("").getPath(), dir);
  }

  /**
   * If path = "/d1/d2/.../di/dj/.../dn" and dirName is "di" then this returns
   * "dj/.../dn". If dirName is not in p then returns p.
   */
  public static String pathAfterDir(final String path, final String dirName) {
    final String match = Path.FILE_SEP + dirName + Path.FILE_SEP;
    return Path.pathAfterMatch(path, match);
  }

  /**
   * Given path = "/a/b/c/d/b/a" and match = "/b/" this method returns
   * "c/d/b/a".
   */
  public static String pathAfterMatch(final String path, final String match) {
    final int i = path.indexOf(match);
    return i < 0 ? path : path.substring(i + match.length());
  }

  /**
   * Assuming that clazz is loaded from the path p = "/d1/d2/.../di/dj/.../dn"
   * and dirName is "dj" then this returns "/d1/d2/.../di/". If dirName is not
   * in p then returns p.
   */
  public static String pathBeforeDir(final Class<?> clazz, final String dirName) {
    assert clazz != null;
    final String dir = Path.FILE_SEP + dirName + Path.FILE_SEP;
    final String result = Path.trimPathSuffixUpToAndIncludingMatch(clazz
        .getResource("").getPath(), dir);
    return result.equals("") ? result : result + Path.FILE_SEP;
  }

  /**
   * Assuming that clazz is loaded from the path p = "/d1/d2/.../dk/.../dn" and
   * dirName is "dk" then this returns "/d1/d2/.../dk/". If dirName is not in p
   * then returns p.
   */
  public static String pathUpToDir(final Class<?> clazz, final String dirName) {
    assert clazz != null;
    final String dir = Path.FILE_SEP + dirName + Path.FILE_SEP;
    return Path.trimPathSuffixUpToMatchAndKeepMatch(clazz.getResource("")
        .getPath(), dir);
  }

  public static String purifyPath(final String s) {
    return s.replaceAll("\\\\", "/");
  }

  public static void purifyPaths(final String[] files) {
    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        files[i] = purifyPath(files[i]);
      }
    }
  }

  /**
   * Given path = "/a/b/c/d" and match = "/b/" this method returns "/a".
   */
  public static String trimPathSuffixUpToAndIncludingMatch(final String path,
      final String match) {
    final int i = path.indexOf(match);
    return i < 0 ? path : path.substring(0, i);
  }

  /**
   * Given path = "/a/b/c/d" and match = "/b/" this method returns "/a/b/".
   */
  public static String trimPathSuffixUpToMatchAndKeepMatch(final String path,
      final String match) {
    final int i = path.indexOf(match);
    return i < 0 ? path : path.substring(0, i + match.length());
  }

  /**
   * In the following we use "/" to represent the FILE_SEP. Given path = "/a",
   * returns "/a". Given path = "/a/b/.../z/", returns "/a/b/.../z".
   */
  public static String trimTrailingFileSeparator(final String path) {
    String result = path;
    if (path.endsWith(Path.FILE_SEP)) {
      result = path.substring(0, path.length() - 1);
    }
    return result;
  }
}
