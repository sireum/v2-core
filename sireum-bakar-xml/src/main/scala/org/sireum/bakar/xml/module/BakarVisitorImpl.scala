package org.sireum.bakar.xml.module

import org.sireum.pipeline.PipelineJob
import org.sireum.pipeline.PipelineJobModuleInfo
import org.sireum.util.VisitorFunction
import org.sireum.bakar.xml._
import org.sireum.util.Visitor
import org.sireum.util.PartialFunctionUtil

class BakarVisitorDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarVisitorModule {

  type Visitor = Any => Boolean

  trait Context {
    var result : Object = null;

    def pushResult(o : Object) {
      //assert(result == null);
      result = o
    }

    def popResult = {
      //assert(result != null)
      val t = result
      result = null
      t
    }
  }

  def f(ctx : Context, v : => Visitor) : VisitorFunction = {
    case c @ CompilationUnitEx(a1, a2, a3, a4, a5, a6, a7, unitFullName, a9, a10) =>
      println("Compilation Unit " + unitFullName)
      true
    case ProcedureBodyDeclarationEx(a1, a2, a3, names, a5, a, a7, a8, a9) =>
      v(names)
      println("ProcedureDeclaration " + ctx.popResult)
      false
    case DefiningIdentifierEx(sloc, defname, defx, typex) =>
      ctx.pushResult(defname)
      true
  }

  def visitor : Visitor = b
  val b = Visitor.build(PartialFunctionUtil.orElses(Seq(f(new Context {}, visitor))))

  this.parseGnat2XMLresults.foreach {
    case (key, value) => {
      b(value)
    }
  }
}