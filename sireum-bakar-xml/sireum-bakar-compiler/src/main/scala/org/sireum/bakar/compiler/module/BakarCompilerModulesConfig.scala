package org.sireum.bakar.compiler.module

import org.sireum.bakar.compiler.option.SparkCompilerOptions
import org.sireum.pipeline._
import org.sireum.util._
import org.sireum.bakar.compiler.module.util.SPARKCompilationUnit
import org.sireum.bakar.compiler.module.util.SPARKParserResult
import org.sireum.pipeline.gen.ModuleGenerator
import org.sireum.option.PipelineMode
import org.sireum.pilar.ast.Model
import org.sireum.bakar.compiler.module._

/**
 * Wrapper module
 */
case class BakarCompiler(
  title : String = "Bakar Compiler Module",
    
  @Input
  sparkCompilerOptions : SparkCompilerOptions,

  @Output
  sources : ISeq[String],
  
  @Output
  models : ISeq[Model]
)

/**** Submodules ****/

case class BakarParser(
  title : String = "Bakar Parser Module",
  
  @Input
  sparkCompilerOptions : SparkCompilerOptions,
  
  @Produce
  sparkParserResult : SPARKParserResult
)

case class BakarPackageDependencyAnalysis(
  title : String = "Bakar Package Dependency Analysis Module",
  
  @Input
  sparkCompilerOptions : SparkCompilerOptions,
  
  @Consume(Array(classOf[BakarParser]))
  sparkParserResult : SPARKParserResult,
  
  @Produce
  transOrder : ISeq[SPARKCompilationUnit] 
)

case class BakarTranslator(
  title : String = "Bakar Translator Module",
  
  @Input
  sparkCompilerOptions : SparkCompilerOptions,

  @Consume(Array(classOf[BakarPackageDependencyAnalysis]))
  transOrder : ISeq[SPARKCompilationUnit],
  
  @Output
  sources : ISeq[String]
)


object hold {
  def main(args : Array[String]) {
    val opt = PipelineMode()
    opt.classNames = Array(BakarTranslator.getClass.getName.dropRight(1),
        BakarCompiler.getClass.getName.dropRight(1))
    opt.dir = "./src/main/scala/org/sireum/bakar/compiler/module"
    opt.genClassName = "BakarCompilerModulesCore"

    ModuleGenerator.run(opt)
  }
} 