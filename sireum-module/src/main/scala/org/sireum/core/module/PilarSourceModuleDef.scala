/*
Copyright (c) 2011-2012 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.core.module

import org.sireum.pilar.ast._
import org.sireum.pilar.symbol._
import org.sireum.util._
import org.sireum.pipeline._

/** The following class will be called reflectively.  Create the file 
  * PilarSourcesDef.scala in the directory corresponding to org.sireum.core.module
  * and paste the code into it
  */


/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class PilarSourcesDef (val job : PipelineJob, info : PipelineJobModuleInfo) extends PilarSourcesModule {
  // simply moves the sources from the global key to the local key
  this.sources = this.sources
}

/*
object PilarSourceConsumerView {
  import PilarSourceModuleConstants._

  final class PSCV(job : PipelineJob) {
    @inline
    def sources =
      job(SOURCES_KEY).asInstanceOf[Seq[Either[String, FileResourceUri]]]

    @inline
    def hasExistingModels = job ? EXISTING_MODEL_KEY

    @inline
    def hasExistingSymbolTable = job ? EXISTING_SYMBOL_TABLE_KEY

    @inline
    def existingModels : ISeq[Model] = job(EXISTING_MODEL_KEY)

    @inline
    def existingSymbolTable : SymbolTable = job(EXISTING_SYMBOL_TABLE_KEY)
  }

  @inline
  implicit def job2pscv(job : PipelineJob) : PSCV = new PSCV(job)
}

object PilarSourceProducerView {
  import PilarSourceModuleConstants._

  final class PSPV(job : PipelineJob) {
    @inline
    def sources(sources : Either[String, FileResourceUri]) : PipelineJob = {
      job(SOURCES_KEY) = Array(sources).toSeq
      job
    }

    @inline
    def sources(sources : ISeq[Either[String, FileResourceUri]]) : PipelineJob = {
      job(SOURCES_KEY) = sources
      job
    }

    @inline
    def existingModels(models : ISeq[Model]) {
      job(EXISTING_MODEL_KEY) = models
    }

    @inline
    def existingSymbolTable(st : SymbolTable) {
      job(EXISTING_SYMBOL_TABLE_KEY) = st
    }
  }

  @inline
  implicit def job2pspv(job : PipelineJob) : PSPV = new PSPV(job)
}

object PilarSourceModuleConstants {
  val SOURCES_KEY = "Pilar Sources"
  val EXISTING_MODEL_KEY = "Existing Pilar Models"
  val EXISTING_SYMBOL_TABLE_KEY = "Existing Pilar Symbol Table"
}
*/