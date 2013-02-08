/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.option

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Mode(command = "bakar",
  desc = "Sireum Bakar Tools",
  header = """
Sireum for Spark
(c) 2012, SAnToS Laboratory, Kansas State University
""")
case class SireumBakarMode(
  typ: SireumBakarTypeMode = SireumBakarTypeMode(),
  program: SireumBakarProgramMode = SireumBakarProgramMode())

object TypeTarget extends Enum {
  sealed abstract class Type extends EnumElem
  object Ocaml extends Type
  object Coq extends Type
  object Java extends Type
  object Scala extends Type

  def elements = ivector(Ocaml, Coq, Java, Scala)
}

@Main(value = "type", className = "org.sireum.bakar.BakarType",
  featureName = "Sireum Bakar XML", desc = "Generate Type Definitions")
case class SireumBakarTypeMode(
  @Option(shortKey = "t", longKey = "type", desc = "") var typ: TypeTarget.Type = TypeTarget.Coq,

  @Option(longKey = "dir", desc = "Destination directory") var dir: String = "",

  @Option(longKey = "packageName", desc = "Package Name for Java") var packageName: String = "",

  @Arg(index = 0, value = "xml-schema") var xmlSchemaLoc: String = "")

object ProgramTarget extends Enum {
  sealed abstract class Type extends EnumElem
  object Ocaml extends Type
  object Coq extends Type
  object Java extends Type

  def elements = ivector(Ocaml, Coq, Java)
}

@Main(value = "program", className = "org.sireum.bakar.BakarProgram", featureName = "Sireum Bakar Translator",
  desc = "Translation of Spark/Ada Programs")
case class SireumBakarProgramMode(
  @Option(shortKey = "p", longKey = "program", desc = "") var typ: ProgramTarget.Type = ProgramTarget.Coq,

  @Option(longKey = "dir", desc = "Destination directory") var destDir: String = "",

  @Arg(index = 0, value = "src-files") var srcFiles: ISeq[String] = ivectorEmpty)