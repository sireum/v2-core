/*
Copyright (c) 2011-2012 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.core.module

import org.sireum.pilar.ast.Model
import org.sireum.util._
import org.sireum.pipeline.Input
import org.sireum.pipeline.Output
import org.sireum.pipeline.Consume
import org.sireum.pipeline.Produce
import org.sireum.pipeline.gen.ModuleGenerator
import org.sireum.pilar.symbol.SymbolTable
import org.sireum.pipeline.PipelineJob
import org.sireum.alir.ReachingDefinitionAnalysis
import org.sireum.alir.DataDependenceGraph
import org.sireum.alir.AlirIntraProceduralGraph
import org.sireum.alir.ControlFlowGraph
import org.sireum.alir.ControlDependenceGraph
import org.sireum.alir.ImmediateDominatorGraph
import org.sireum.alir.DominanceFrontierGraph
import org.sireum.alir.ProgramDependenceGraph
import org.sireum.option.PipelineMode

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class PilarSources(
  title : String = "Pilar Source",
  
  @Input 
  @Produce 
  sources : ISeq[Either[String, ResourceUri]])

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class PilarParser(
  title : String = "Pilar Parser",
  
  @Input
  sources : ISeq[Either[String, ResourceUri]],
  
  @Output 
  @Produce 
  models : ISeq[Model])

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class ChunkingPilarParser(
  title : String = "Pilar Parser",
  
  @Input
  sources : ISeq[Either[String, ResourceUri]],
  
  @Output 
  @Produce 
  models : ISeq[Model])

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class PilarSymbolResolver(
  title : String = "Pilar Symbol Resolver",
  
  @Input
  parallel : Boolean,
  
  @Input 
  @Consume(Array(classOf[PilarParser]))
  @Output 
  @Produce 
  models : ISeq[Model],
  
  @Input
  hasExistingSymbolTable : scala.Option[SymbolTable],
  
  @Input
  hasExistingModels : scala.Option[ISeq[Model]],
  
  @Output
  symbolTable : SymbolTable)
  
/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait BogorFactory {
  def create(job : PipelineJob) : org.sireum.bogor.Bogor
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
case class Bogor(
  title : String = "Bogor",
  
  @Input 
  @Consume(Array(classOf[PilarSymbolResolver])) 
  models : ISeq[Model],
  
  @Input
  factory : BogorFactory)

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object hold {
  def main(args : Array[String]) {
    val opt = PipelineMode()
    opt.classNames = Array(
        PilarSources.getClass().getName().dropRight(1),
        ChunkingPilarParser.getClass().getName().dropRight(1),
        PilarSymbolResolver.getClass().getName().dropRight(1),
        Bogor.getClass.getName.dropRight(1))
    opt.dir = "./src/main/scala/org/sireum/core/module"
    opt.genClassName = "SireumModulesCore"

    ModuleGenerator.run(opt)
  }
}
