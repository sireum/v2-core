/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.framework.alir.intra

import java.io.OutputStreamWriter
import org.sireum.test.framework._
import org.sireum.core.module._
import org.sireum.pilar.ast._
import org.sireum.pilar.parser._
import org.sireum.pilar.symbol._
import org.sireum.alir._
import org.sireum.util._
import org.sireum.pipeline._
import java.io.PrintWriter

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait AlirIntraProceduralTestFramework extends TestFramework {

  def Analyzing : this.type = this

  def title(s : String) : this.type = {
    _title = caseString + s
    casePrefix = ""
    this
  }

  def model(code : String) =
    AlirConfiguration(title, ilist(Left(code)))

  def file(fileUri : FileResourceUri) =
    AlirConfiguration(title, ilist(Right(fileUri)))

  case class AlirConfiguration //
  (title : String, srcs : ISeq[Either[String, FileResourceUri]]) {

    test(title) {

      val job = PipelineJob()
      val options = job.properties
      PilarParserModule.setSources(options, srcs)
      PilarSymbolResolverModule.setHasExistingModels(options, None)
      PilarSymbolResolverModule.setHasExistingSymbolTable(options, None)
      PilarSymbolResolverModule.setParallel(options, false)

      AlirIntraProceduralModule.setShouldBuildCfg(options, true)
      AlirIntraProceduralModule.setShouldBuildCdg(options, true)
      AlirIntraProceduralModule.setShouldBuildRda(options, true)
      AlirIntraProceduralModule.setShouldBuildDdg(options, true)
      AlirIntraProceduralModule.setShouldBuildPdg(options, true)
      AlirIntraProceduralModule.setShouldBuildDfg(options, true)
      AlirIntraProceduralModule.setShouldBuildIdg(options, true)

      AlirIntraProceduralModule.setProcedureAbsUriIterator(options,
        None)

      pipeline.compute(job)

      if (job.lastStageInfo.hasError) {
        val pwOut = new PrintWriter(Console.out)
        val pwErr = new PrintWriter(Console.err)
        println("Errors from stage: " + job.lastStageInfo.title)
        val stageTags = job.lastStageInfo.tags.toList
        PipelineUtil.printTags(stageTags, pwOut, pwErr)
        pwErr.println(Tag.collateAsString(job.lastStageInfo.tags.toList))
        pwErr.flush
        for (m <- job.lastStageInfo.info) {
          val mTags = m.tags.toList
          PipelineUtil.printTags(mTags, pwOut, pwErr)
          pwErr.println(Tag.collateAsString(mTags))
          pwErr.flush
        }
      }

      //val job = pipeline.compute(PipelineJob().sources(srcs))
      //val tags = job.tags

      //assert(tags.isEmpty,
      //  "Unexpecting warnings/errors but found:\n" + Tag.collateAsString(tags))

      val st = PilarSymbolResolverModule.getSymbolTable(options)
      val r = AlirIntraProceduralModule.getResult(options)
      //      val w = new OutputStreamWriter(Console.out)
      r.foreach { p =>
        val (procedureUri, result) = p
        alirF.foreach { f =>
          f(st.procedureSymbolTable(procedureUri), result)
        }
        //        result.cfg.toDot(w)
        //        w.write('\n')
        //        result.cdgOpt.get.toDot(w)
        //        w.write('\n')
      }
    }

    def satisfies(f : (ProcedureSymbolTable, AlirIntraProcedural.AlirIntraproceduralAnalysisResult) => Unit) : this.type = {
      alirF += f
      this
    }

    val alirF = marrayEmpty[(ProcedureSymbolTable, AlirIntraProcedural.AlirIntraproceduralAnalysisResult) => Unit]
  }

  protected var _title : String = null
  protected var num = 0
  protected def title() = if (_title == null) {
    num += 1
    "Analysis #" + num
  } else _title

  protected val pipeline =
    PipelineConfiguration(
      "Parser Test Pipeline",
      false,
      PipelineStage(
        "Pilar Parsing",
        false,
        PilarParserModule),
      PipelineStage(
        "Pilar Symbol Resolution",
        false,
        PilarSymbolResolverModule),
      PipelineStage(
        "Alir IntraProcedural Analysis",
        false,
        AlirIntraProceduralModule))
}
