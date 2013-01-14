/*
Copyright (c) 2011-2012 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

/**
 * The following class will be called reflectively.  Create the file
 * BogorDef.scala in the directory corresponding to org.sireum.core.module
 * and paste the code into it
 */

package org.sireum.core.module

import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.util._
import org.sireum.pipeline._

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object BogorModuleDef {
  val ERROR_TAG_TYPE = MarkerType(
    "org.sireum.bogor.tag.error",
    None,
    "Bogor Error",
    MarkerTagSeverity.Error,
    MarkerTagPriority.Normal,
    ilist(MarkerTagKind.Problem, MarkerTagKind.Text))

  val WARNING_TAG_TYPE = MarkerType(
    "org.sireum.bogor.tag.error",
    None,
    "Bogor Warning",
    MarkerTagSeverity.Warning,
    MarkerTagPriority.Normal,
    ilist(MarkerTagKind.Problem, MarkerTagKind.Text))  
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class BogorModuleDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends BogorModule {
  // add implementation here
  //this.factory.create(job).search
}
/*
package org.sireum.core.module


object BogorConsumerView {
  import BogorModule._

  final class BCV(job : PipelineJob) {
    @inline
    def factory : BogorFactory = job(FACTORY_KEY)
  }

  @inline
  implicit def job2bcv(job : PipelineJob) : BCV = new BCV(job)
}

object BogorProducerView {
  import BogorModule._

  final class BPV(job : PipelineJob) {

    @inline
    def factory(factory : BogorFactory) = {
      job(FACTORY_KEY) = factory
      job
    }
  }

  @inline
  implicit def job2bpv(job : PipelineJob) : BPV = new BPV(job)
}

trait BogorFactory {
  def create(job : PipelineJob) : org.sireum.bogor.Bogor
}

object BogorModule extends PipelineModule {

  def title : String = "Bogor Module"

  def compute(job : PipelineJob, info : PipelineJobModuleInfo) {
    import SireumConsumerView._
    import PilarParserConsumerView._
    import PilarSymbolResolutionConsumerView._
    import BogorConsumerView._

    job.factory.create(job).search
  }

  val FACTORY_KEY = "Bogor Factory"
  val REPORT_KEY = "Bogor Report"
  val ERROR_TAG_TYPE = MarkerType(
    "org.sireum.bogor.tag.error",
    None,
    "Bogor Error",
    MarkerTagSeverity.Error,
    MarkerTagPriority.Normal,
    Array(MarkerTagKind.Problem, MarkerTagKind.Text))
  val WARNING_TAG_TYPE = MarkerType(
    "org.sireum.bogor.tag.error",
    None,
    "Bogor Warning",
    MarkerTagSeverity.Warning,
    MarkerTagPriority.Normal,
    Array(MarkerTagKind.Problem, MarkerTagKind.Text))
}
*/ 