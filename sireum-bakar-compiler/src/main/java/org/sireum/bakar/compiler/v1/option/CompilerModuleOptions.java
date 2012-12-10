/******************************************************************************
 * Copyright (c) 2009 Robby, Kansas State University, and others.             *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/
package org.sireum.bakar.compiler.v1.option;


public final class CompilerModuleOptions extends ModuleOptions {
  protected boolean genThreeAddressCode = true;

  protected boolean desugarRecordAggregates = false;

  protected boolean desugarArrayAggregates = false;

  public final static int DESCRIPTOR = 3;

  public CompilerModuleOptions() {
  }

  @Override
  public final int getDescriptor() {
    return CompilerModuleOptions.DESCRIPTOR;
  }

  public final boolean getDesugarArrayAggregates() {
    return this.desugarArrayAggregates;
  }

  public final boolean getDesugarRecordAggregates() {
    return this.desugarRecordAggregates;
  }

  public final boolean getGenThreeAddressCode() {
    return this.genThreeAddressCode;
  }

  /**
   * if {@code true} then array aggregates will be translated into equivalent
   * for-loop initialization blocks
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
  public final void setDesugarArrayAggregates(
      final boolean desugarArrayAggregates) {
    this.desugarArrayAggregates = desugarArrayAggregates;
  }

  /**
   * if {@code true} then record aggregates will be translated into equivalent
   * for-loop initialization blocks
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
  public final void setDesugarRecordAggregates(
      final boolean desugarRecordAggregates) {
    this.desugarRecordAggregates = desugarRecordAggregates;
  }

  /**
   * <ul>
   * <li>{@code Default}
   * 
   * <pre>
   * true
   * </pre>
   * 
   * </li>
   * </ul>
   */
  public final void setGenThreeAddressCode(final boolean genThreeAddressCode) {
    this.genThreeAddressCode = genThreeAddressCode;
  }
}
