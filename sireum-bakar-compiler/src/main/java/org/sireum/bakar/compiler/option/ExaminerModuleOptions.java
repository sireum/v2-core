/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.option;


public final class ExaminerModuleOptions extends ModuleOptions {
  protected String sparkExeLocation;

  //protected org.sireum.spark.examiner.SparkExaminerOptions examinerOptions;

  //protected org.sireum.spark.project.ISparkExaminerTagTypeProvider examinerTagTypeProvider;

  public final static int DESCRIPTOR = 4;

  public ExaminerModuleOptions() {
  }

  @Override
  public final int getDescriptor() {
    return ExaminerModuleOptions.DESCRIPTOR;
  }

  public final String getSparkExeLocation() {
    return this.sparkExeLocation;
  }

  /**
   * <ul>
   * <li>{@code NonNull}</li>
   * </ul>
   */
  public final void setSparkExeLocation(final String sparkExeLocation) {
    assert sparkExeLocation != null;
    if (sparkExeLocation != null) {
      this.sparkExeLocation = sparkExeLocation.intern();
    } else {
      this.sparkExeLocation = null;
    }
  }
}
