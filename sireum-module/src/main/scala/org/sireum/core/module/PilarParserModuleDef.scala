/*
Copyright (c) 2011-2013 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

/**
 * The following class will be called reflectively.  Create the file
 * PilarParserDef.scala in the directory corresponding to org.sireum.core.module
 * and paste the code into it
 */

package org.sireum.core.module

import org.sireum.pipeline._
import org.sireum.util._
import org.sireum.pilar.ast._
import org.sireum.pilar._

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object PilarParserDef{
  val ERROR_TAG_TYPE = MarkerType(
    "org.sireum.pilar.tag.error.parse",
    None,
    "Pilar Parser Error",
    MarkerTagSeverity.Error,
    MarkerTagPriority.Normal,
    ivector(MarkerTagKind.Problem, MarkerTagKind.Text))
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class PilarParserDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends PilarParserModule {
  val srcs = this.sources
  val result = marrayEmpty[Model]
  for (src <- srcs) {
    val mOpt = org.sireum.pilar.parser.PilarParser(src, reporter(info))
    if (!mOpt.isEmpty)
      result += mOpt.get
    else
      info.hasError = true
  }

  this.models_=(result.toList)

  def reporter(info : PipelineJobModuleInfo) =
    new org.sireum.pilar.parser.PilarParser.ErrorReporter {
      def report(source : Option[FileResourceUri], line : Int,
                 column : Int, message : String) =
        info.tags += Tag.toTag(source, line, column, message, PilarParserDef.ERROR_TAG_TYPE)
    }
}

/*
package org.sireum.core.module

import org.sireum.pilar.ast._
import org.sireum.pilar.parser._
import org.sireum.util._
import org.sireum.pipeline._

object PilarParserModule extends PipelineModule {

  def title : String = "Pilar Parser Module"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) = {
    import PilarSourceConsumerView._
    import PilarParserProducerView._

    val srcs = job.sources
    val result = marrayEmpty[Model]
    for (src <- srcs) {
      val mOpt = org.sireum.pilar.parser.PilarParser(src, reporter(info))
      if (!mOpt.isEmpty)
        result += mOpt.get
      else
        info.hasError = true
    }

    job.models(result : _*)
  }

  def reporter(info : PipelineJobModuleInfo) =
    new org.sireum.pilar.parser.PilarParser.ErrorReporter {
      def report(source : Option[FileResourceUri], line : Int,
                 column : Int, message : String) =
        info.tags += Tag.toTag(source, line, column, message, ERROR_TAG_TYPE)
    }

  val MODEL_KEY = "Pilar Models"
  val ERROR_TAG_TYPE = MarkerType(
    "org.sireum.pilar.tag.error.parse",
    None,
    "Pilar Parser Error",
    MarkerTagSeverity.Error,
    MarkerTagPriority.Normal,
    Array(MarkerTagKind.Problem, MarkerTagKind.Text))
}

object PilarParserConsumerView {
  final class PPCV(job : PipelineJob) {
    import PilarParserModule._

    @inline
    def models : ISeq[Model] = job(MODEL_KEY)
  }

  @inline
  implicit def job2ppcv(job : PipelineJob) : PPCV = new PPCV(job)
}

object PilarParserProducerView {
  final class PPPV(job : PipelineJob) {
    import PilarParserModule._

    @inline
    def models(models : Model*) {
      job(MODEL_KEY) = models
    }
  }

  @inline
  implicit def job2pppv(job : PipelineJob) : PPPV = new PPPV(job)
}
*/ 