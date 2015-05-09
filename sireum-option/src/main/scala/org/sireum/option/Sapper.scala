/*
Copyright (c) 2011-2014 Robby, Kansas State University.
All rights reserved. This program and the accompanying materials
are made available under the terms of the Eclipse Public License v1.0
which accompanies this distribution, and is available at
http://www.eclipse.org/legal/epl-v10.html
*/
package org.sireum.option

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "sapper", className = "org.sireum.tools.sapp.Sapper", featureName = "Sireum Tools")
case class SapperMode(
  @Option(shortKey = "x", longKey = "extract", desc = "Extract Mode") //
  var isExtract : Boolean = false,

  @Arg(index = 0, value = "file.sapp") //
  var sappFile : String = "",

  @Args("files") //
  var files : Array[String] = Array())
