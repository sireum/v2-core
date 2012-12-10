package org.sireum.bakar.compiler.v1.module

import org.sireum.bakar.compiler.v1.option.SparkCompilerOptions
import org.sireum.pipeline._
import org.sireum.util._
import org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit
import org.sireum.bakar.compiler.v1.module.util.SPARKParserResult
import org.sireum.pipeline.gen.ModuleGenerator
import org.sireum.option.PipelineMode
import org.sireum.pilar.ast.Model
import org.sireum.bakar.compiler.v1.module._

/**
 * Wrapper module
 */
case class BakarCompilerv1(
  title : String = "Bakar Compiler Module v1",
    
  @Input
  sparkCompilerOptions : SparkCompilerOptions,

  @Output
  sources : ISeq[String],
  
  @Output
  models : ISeq[Model]
)

/**** Submodules ****/

case class BakarParserv1(
  title : String = "Bakar Parser Module v1",
  
  @Input
  sparkCompilerOptions : SparkCompilerOptions,
  
  @Produce
  sparkParserResult : SPARKParserResult
)

case class BakarPackageDependencyAnalysisv1(
  title : String = "Bakar Package Dependency Analysis Module v1",
  
  @Input
  sparkCompilerOptions : SparkCompilerOptions,
  
  @Consume(Array(classOf[BakarParserv1]))
  sparkParserResult : SPARKParserResult,
  
  @Produce
  transOrder : ISeq[SPARKCompilationUnit] 
)

case class BakarTranslatorv1(
  title : String = "Bakar Translator Module v1",
  
  @Input
  sparkCompilerOptions : SparkCompilerOptions,

  @Consume(Array(classOf[BakarPackageDependencyAnalysisv1]))
  transOrder : ISeq[SPARKCompilationUnit],
  
  @Output
  sources : ISeq[String]
)


object hold {
  def main(args : Array[String]) {
    val opt = PipelineMode()
    opt.classNames = Array(BakarTranslatorv1.getClass.getName.dropRight(1),
        BakarCompilerv1.getClass.getName.dropRight(1))
    opt.dir = "./src/main/scala/org/sireum/bakar/compiler/v1/module"
    opt.genClassName = "BakarCompilerModulesCorev1"

    ModuleGenerator.run(opt)
  }
} 