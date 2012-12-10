/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.option;


public final class PackageDependencyModuleOptions extends ModuleOptions {
  protected boolean genDependencyGraph = false;

  public final static int DESCRIPTOR = 2;

  public PackageDependencyModuleOptions() {
  }

  @Override
  public final int getDescriptor() {
    return PackageDependencyModuleOptions.DESCRIPTOR;
  }

  public final boolean getGenDependencyGraph() {
    return this.genDependencyGraph;
  }

  /**
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
  public final void setGenDependencyGraph(final boolean genDependencyGraph) {
    this.genDependencyGraph = genDependencyGraph;
  }
}
