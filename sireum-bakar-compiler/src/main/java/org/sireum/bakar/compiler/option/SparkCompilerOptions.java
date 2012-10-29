/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.option;

import org.sireum.bakar.util.Util;

public final class SparkCompilerOptions extends Option {
  protected String[] sparkFilenames;

  protected String optSourceDirPath = '.' + java.io.File.separator;

  protected String optOutputPath = '.' + java.io.File.separator;

  protected String optTempOutputPath;

  protected boolean regressionTesting = false;

  protected CompilerModuleOptions optCompilerOptions;

  protected PackageDependencyModuleOptions optPackageDependencyOptions;

  protected ExaminerModuleOptions optExaminerOptions;

  protected LocationExtractorModuleOptions optLocationExtractorOptions;

  public final static int DESCRIPTOR = 1;

  public SparkCompilerOptions() {
  }

  @Override
  public final int getDescriptor() {
    return SparkCompilerOptions.DESCRIPTOR;
  }

  public final CompilerModuleOptions getOptCompilerOptions() {
    return this.optCompilerOptions;
  }

  public final ExaminerModuleOptions getOptExaminerOptions() {
    return this.optExaminerOptions;
  }

  public final LocationExtractorModuleOptions getOptLocationExtractorOptions() {
    return this.optLocationExtractorOptions;
  }

  public final String getOptOutputPath() {
    return this.optOutputPath;
  }

  public final PackageDependencyModuleOptions getOptPackageDependencyOptions() {
    return this.optPackageDependencyOptions;
  }

  public final String getOptSourceDirPath() {
    return this.optSourceDirPath;
  }

  public final String getOptTempOutputPath() {
    return this.optTempOutputPath;
  }

  public final boolean getRegressionTesting() {
    return this.regressionTesting;
  }

  public final String[] getSparkFilenames() {
    return this.sparkFilenames;
  }

  public final void setOptCompilerOptions(
      final CompilerModuleOptions optCompilerOptions) {
    this.optCompilerOptions = optCompilerOptions;
  }

  /**
   * if non-null then the spark syntax checker module will be run
   */
  public final void setOptExaminerOptions(
      final ExaminerModuleOptions optExaminerOptions) {
    this.optExaminerOptions = optExaminerOptions;
  }

  /**
   * if non-null then the location extractor module will be run
   */
  public final void setOptLocationExtractorOptions(
      final LocationExtractorModuleOptions optLocationExtractorOptions) {
    this.optLocationExtractorOptions = optLocationExtractorOptions;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code Default}
   * 
   * <pre>
   * '.' + java.io.File.separator
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setOptOutputPath(final String optOutputPath) {
    assert optOutputPath != null;
    if (optOutputPath != null) {
      this.optOutputPath = optOutputPath.intern();
    } else {
      this.optOutputPath = null;
    }
  }

  public final void setOptPackageDependencyOptions(
      final PackageDependencyModuleOptions optPackageDependencyOptions) {
    this.optPackageDependencyOptions = optPackageDependencyOptions;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * '.' + java.io.File.separator
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setOptSourceDirPath(final String optSourceDirPath) {
    if (optSourceDirPath != null) {
      this.optSourceDirPath = optSourceDirPath.intern();
    } else {
      this.optSourceDirPath = null;
    }
  }

  public final void setOptTempOutputPath(final String optTempOutputPath) {
    if (optTempOutputPath != null) {
      this.optTempOutputPath = optTempOutputPath.intern();
    } else {
      this.optTempOutputPath = null;
    }
  }

  /**
   * if true then only the filenames will be emitted in the pilar AST (ie.
   * absolute paths are scrubbed)
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * false
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setRegressionTesting(final boolean regressionTesting) {
    this.regressionTesting = regressionTesting;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * <li>{@code NonNullElements}</li>
   * </ul>
   */
  public final void setSparkFilenames(final String[] sparkFilenames) {
    assert sparkFilenames != null;

    assert Util.nonNullElements(sparkFilenames);
    this.sparkFilenames = sparkFilenames;
  }
}
