package org.sireum.bakar.alir.module

import org.sireum.util._
import org.sireum.pipeline._
import org.sireum.bakar.compiler.v1.option.SparkCompilerOptions
import org.sireum.bakar.alir.BakarNodeDefRef
import org.sireum.core.module.PilarSymbolResolverModule
import org.sireum.core.module.PilarParserModule
import org.sireum.pilar.ast._
import org.sireum.pilar.ast.Assignment
import org.sireum.alir.BasicVarDefRef
import org.sireum.alir.Slot
import org.sireum.pilar.symbol.ProcedureSymbolTable

object BakarNodeDefRefModule extends PipelineModule {
  def title : String = "Bakar Alir Intraprocedural Module"
  val RESULTS = "BNDR_RESULTS"

/**
 * considering this module is useful only in testing as the 
 * def/ref will be used in RDA directly without invoking this 
 * module. Based on this assumption formating/sorting the results
 * to match expected result is not expensive in the actual execution 
 * of the system.
*/  
  def compute(job: PipelineJob, info: PipelineJobModuleInfo): MBuffer[Tag] = {
    println("NDR module")
    val tags = marrayEmpty[Tag]
    val st = PilarSymbolResolverModule.getSymbolTable(job.properties)
    val ast = PilarParserModule.getModels(job.properties)
    val BNDR = new BakarNodeDefRef(st, new org.sireum.alir.BasicVarAccesses(st))
    var result = ""
    var pstSorted = st.procedureSymbolTables.toIndexedSeq
    pstSorted = pstSorted.sortWith((pst1,pst2) => 
      (pst1.procedure.name.toString() compareTo pst2.procedure.name.toString()) < 0)
   pstSorted.foreach { pst =>
      result  += ("-----------\n" + pst.procedureUri + "\n-----------")
      Visitor.build({
        case assign: Assignment => {
          result += (assign.asInstanceOf[Command].commandDescriptorInfo._1.get+ " : \n")
          result += ("Strong Definition...........")
          result += printer(BNDR.strongDefinitions(assign))
          result += ("Definition..................")
          result += printer(BNDR.definitions(assign))
          assign match  {
            case act : AssignAction => {
               result +=("Refernece...................")
               result += printer(BNDR.references(act.asInstanceOf[Action]))
            }
            case _ => //do nothing 
          }
          false
        }
      })(pst.procedure)
    }
   setResults(job, result)
   marrayEmpty[Tag]
  }
  
  def printer(vars : ISet[Slot]) : String = {
    var result = ("[") 
    val test = vars.fold("")((r,c) => (r,c) match {
      case ("",c) => c 
      case _ => r+", "+c
    })
    result += test + "]\n"
    result
  }
  
  def inputDefined(job : PipelineJob) : MBuffer[Tag] = { marrayEmpty[Tag]}
  
  def outputDefined(job : PipelineJob) : MBuffer[Tag] = { marrayEmpty[Tag]}
  
  def setResults(job : PipelineJob, results : String) {
    job(BakarNodeDefRefModule.RESULTS) = results
  }

  def getResults(job : PipelineJob) : String  = {
    return job(BakarNodeDefRefModule.RESULTS)
  }
  
}     