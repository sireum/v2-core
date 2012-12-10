/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.option;


public class OptionFactory {

  public static CompilerModuleOptions newCompilerModuleOptions() {
    final CompilerModuleOptions result = new CompilerModuleOptions();
    return result;
  }

  public static LocationExtractorModuleOptions newLocationExtractorModuleOptions() {
    final LocationExtractorModuleOptions result = new LocationExtractorModuleOptions();
    return result;
  }

  public static PackageDependencyModuleOptions newPackageDependencyModuleOptions() {
    final PackageDependencyModuleOptions result = new PackageDependencyModuleOptions();
    return result;
  }

  /**
   * @param sparkFilenames
   *          <ul>
   *          <li>{@code NonNull}</li>
   *          <li>{@code NonNullElements}</li>
   *          </ul>
   */
  public static SparkCompilerOptions newSparkCompilerOptions(
      final String[] sparkFilenames) {
    final SparkCompilerOptions result = new SparkCompilerOptions();
    result.setSparkFilenames(sparkFilenames);
    return result;
  }

  public OptionFactory() {
  }
}
