/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.option;


public class OptionVisitor<G> {
  protected G g;

  public OptionVisitor(final G g) {
    assert g != null;
    this.g = g;
  }

  protected void visit(final Object o) {
    assert false;
  }

  public void visit(final Option o) {
    assert o != null;
    switch (o.getDescriptor()) {
      case SparkCompilerOptions.DESCRIPTOR:
        visitSparkCompilerOptions((SparkCompilerOptions) o);
        break;

      case PackageDependencyModuleOptions.DESCRIPTOR:
        visitPackageDependencyModuleOptions((PackageDependencyModuleOptions) o);
        break;

      case CompilerModuleOptions.DESCRIPTOR:
        visitCompilerModuleOptions((CompilerModuleOptions) o);
        break;

      case LocationExtractorModuleOptions.DESCRIPTOR:
        visitLocationExtractorModuleOptions((LocationExtractorModuleOptions) o);
        break;

      default:
        assert false;
        break;
    }
  }

  protected void visitCompilerModuleOptions(final CompilerModuleOptions o) {
    assert o != null;
    visitModuleOptions(o);
  }

  protected void visitLocationExtractorModuleOptions(
      final LocationExtractorModuleOptions o) {
    assert o != null;
    visitModuleOptions(o);
  }

  protected void visitModuleOptions(final ModuleOptions o) {
    assert o != null;
    visitOption(o);
  }

  protected void visitOption(final Option o) {
    assert o != null;
  }

  protected void visitPackageDependencyModuleOptions(
      final PackageDependencyModuleOptions o) {
    assert o != null;
    visitModuleOptions(o);
  }

  protected void visitSparkCompilerOptions(final SparkCompilerOptions o) {
    assert o != null;
    visitOption(o);

    for (final String e0 : o.sparkFilenames) {
    }

    if (o.optCompilerOptions != null) {
      visit(o.optCompilerOptions);
    }

    if (o.optPackageDependencyOptions != null) {
      visit(o.optPackageDependencyOptions);
    }

    if (o.optExaminerOptions != null) {
      visit(o.optExaminerOptions);
    }

    if (o.optLocationExtractorOptions != null) {
      visit(o.optLocationExtractorOptions);
    }
  }

}
