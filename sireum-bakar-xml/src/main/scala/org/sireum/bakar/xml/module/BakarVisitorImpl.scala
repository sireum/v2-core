package org.sireum.bakar.xml.module

import org.sireum.pipeline.PipelineJob
import org.sireum.pipeline.PipelineJobModuleInfo
import org.sireum.util.VisitorFunction
import org.sireum.bakar.xml.extractor._
import org.sireum.util.Visitor

class BakarVisitorDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarVisitorModule {

  val f : VisitorFunction = {
    case c @ CompilationUnitEx(a1, a2, a3, a4, a5, a6, a7, unitFullName, a9, a10) =>
      println("Compilation Unit " + unitFullName)
      true
    case ProcedureBodyDeclarationEx(a1,a2,a3,names,a5,a,a7,a8,a9) =>
        println("ProcedureDeclaration " + names.getDefiningNames())
      true
    case DefiningIdentifierEx(sloc, defname, defx, typex) =>
      println("DefiningIdentifier " + defname)
      true
  }

  val results = this.parseGnat2XMLresults

  val b = Visitor.build(f)

  results.foreach {
    case (key, value) => {
      b(value)
    }
  }
}