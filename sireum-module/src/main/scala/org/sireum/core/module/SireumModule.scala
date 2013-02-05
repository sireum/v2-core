/*
Copyright (c) 2011-2013 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.core.module

import org.sireum.pipeline.PipelineJob

object SireumConsumerView {
  import SireumModuleConstants._

  final class SCV(job : PipelineJob) {

    @inline
    def parallel : Boolean =
      if (job ? PARALLEL_KEY)
        job(PARALLEL_KEY)
      else true
  }

  @inline
  implicit def job2scv(job : PipelineJob) : SCV = new SCV(job)
}

object SireumProducerView {
  import SireumModuleConstants._

  final class SPV(job : PipelineJob) {

    @inline
    def parallel(parallel : Boolean) {
      job(PARALLEL_KEY) = parallel
    }
  }

  @inline
  implicit def job2spv(job : PipelineJob) : SPV = new SPV(job)
}

object SireumModuleConstants {

  val PARALLEL_KEY = "Parallel Mode"
}