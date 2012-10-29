package org.sireum.bakar.compiler.module

import org.antlr.runtime.ANTLRFileStream
import org.sireum.bakar.compiler.model._
import org.sireum.bakar.compiler.parser.CompilationParser
import org.sireum.bakar.util.message.Message
import org.sireum.util._
import org.sireum.pipeline._
import org.sireum.bakar.compiler.model._
import org.sireum.bakar.compiler.module.util.SPARKParserResult
import org.sireum.bakar.compiler.module.util.SPARKCompilationUnit
import org.sireum.bakar.compiler.module.util.SPARKCompilationUnitType
import org.sireum.bakar.compiler.module.util.SPARKLibraryItemType
import org.sireum.bakar.compiler.module.util.SPARKSpecBodyUnit
import org.sireum.bakar.compiler.module._
import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.JavaConversions.bufferAsJavaList

class BakarParserDef(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarParserModule {
  val sco = this.sparkCompilerOptions

  val spr = new SPARKParserResult
  spr.setTheSparkSourceFilenames(sco.getSparkFilenames)
  spr.setThePackageNameToSpecBodyNodes(new java.util.HashMap[String, SPARKSpecBodyUnit]())

  for (fileName <- sco.getSparkFilenames) {
    Parser(spr, fileName).parse
  }

  this.sparkParserResult_=(spr)
}

class VisitorContext {
  var isSpec = false
  var isMainProgram = false

  var packageName : Option[String] = None
  var mainProgramSubProgramName : Option[String] = None
  var result : Option[String] = None

  var theWithNames : MList[String] = mlistEmpty
  var theUseTypeNames : MList[String] = mlistEmpty
  var theOptionalInheritNames : Option[MList[String]] = None

  var theContextClause : Option[ContextClause] = None

  def popResult : Option[String] = {
    assert(this.result.isDefined)
    val oldResult = this.result
    this.result = None
    return oldResult
  }

  def pushResult(stArg : Some[String]) {
    assert(this.result.isEmpty && !stArg.isEmpty)
    this.result = stArg
  }
}

case class Parser(spr : SPARKParserResult, sparkFileName : String) {
  import scala.collection.JavaConversions._

  def handleMainProgram(cu : CompilationUnit, vc : VisitorContext) {
    spr synchronized {
      assert(vc.isMainProgram && vc.mainProgramSubProgramName.isDefined)
      assert(spr.getTheOptionalMainCompilationUnit == null)
      val scu = new SPARKCompilationUnit
      scu.setTheSparkSourceFilename(this.sparkFileName)
      scu.setTheContextClause(vc.theContextClause.get)

      scu.setTheOptionalInheritNames(new java.util.ArrayList(vc.theOptionalInheritNames.get))
      scu.setTheCompilationUnitType(SPARKCompilationUnitType.LIBRARY_ITEM)
      scu.setTheOptionalLibraryItemType(SPARKLibraryItemType.MAIN_SUBPROGRAM)
      scu.setTheCompilationUnit(cu)

      if (!vc.theUseTypeNames.isEmpty) {
        scu.setTheOptionalUseNames(new java.util.ArrayList(vc.theUseTypeNames))
      }
      if (!vc.theWithNames.isEmpty) {
        scu.setTheOptionalWithNames(new java.util.ArrayList(vc.theWithNames))
      }

      spr.setTheOptionalMainCompilationUnit(scu)
    }
  }

  def handlePackages(cu : CompilationUnit, vc : VisitorContext) {
    assert(vc.packageName.isDefined)

    val packageName = vc.packageName.get
    val isSpec = vc.isSpec

    spr synchronized {
      val pnameMap = spr.getThePackageNameToSpecBodyNodes

      var theSpecBodyUnit = pnameMap.get(packageName)

      if (theSpecBodyUnit == null) {
        assert(pnameMap.get(packageName) == null)
        theSpecBodyUnit = new SPARKSpecBodyUnit()
        theSpecBodyUnit.setThePackageName(packageName)
        pnameMap.put(packageName, theSpecBodyUnit)
      }

      val scu = new SPARKCompilationUnit()
      scu.setThePackageName(packageName)
      scu.setTheSparkSourceFilename(sparkFileName)
      scu.setTheCompilationUnitType(SPARKCompilationUnitType.LIBRARY_ITEM)

      scu.setTheContextClause(vc.theContextClause.get)

      if (!vc.theUseTypeNames.isEmpty) {
        scu.setTheOptionalUseNames(new java.util.ArrayList(vc.theUseTypeNames))
      }

      if (!vc.theWithNames.isEmpty) {
        scu.setTheOptionalWithNames(new java.util.ArrayList(vc.theWithNames))
      }

      scu.setTheCompilationUnit(cu)

      if (isSpec) {
        if (vc.theOptionalInheritNames.isDefined) {
          scu.setTheOptionalInheritNames(new java.util.ArrayList(vc.theOptionalInheritNames.get))
        }
        scu.setTheOptionalLibraryItemType(SPARKLibraryItemType.PRIVATE_PACKAGE_DECLARATION)
        theSpecBodyUnit.setTheOptionalPackageSpecification(scu)
      } else {
        scu.setTheOptionalLibraryItemType(SPARKLibraryItemType.PACKAGE_BODY)
        theSpecBodyUnit.setTheOptionalPackageBody(scu)
      }
    }
  }

  def parse = {
    val messages = new java.util.ArrayList[Message]()
    val theParseResults = doit(this.sparkFileName, messages)

    for (cu <- theParseResults.getTheOptionalCompilationUnits) {
      val vc = new VisitorContext()
      new NodeVisitor[VisitorContext](vc) {

        override def visitCompilationUnit(o : CompilationUnit) {
          this.g.theContextClause = Some(o.getTheContextClause)
          //this.g.theWithNames = Some(mlistEmpty)
          //this.g.theUseTypeNames = Some(mlistEmpty)

          this.visitContextClause(o.getTheContextClause)

          o.getDescriptor match {
            case LibraryCompilationUnit.DESCRIPTOR =>
              visit(o.asInstanceOf[LibraryCompilationUnit].getTheLibraryItem)
            case SubUnitCompilationUnit.DESCRIPTOR =>
              val sucu = o.asInstanceOf[SubUnitCompilationUnit]
              visit(sucu.getTheName)
              this.g.popResult

              visit(sucu.getTheProperBody)

              assert(false)
          }
        }

        override def visitIDName(o : IDName) {
          this.g.pushResult(Some(o.getTheIDString))
        }

        override def visitMainProgram(o : MainProgram) {
          this.g.isMainProgram = true

          if (o.getTheOptionalInheritClauses != null) {
            this.g.theOptionalInheritNames = Some(mlistEmpty)
            for (inheritName <- o.getTheOptionalInheritClauses) {
              visit(inheritName)
              this.g.theOptionalInheritNames.get.add(this.g.popResult.get)
            }
          }
          this.g.mainProgramSubProgramName =
            Some(o.getTheSubProgramBody.getTheSubProgramImplementation.getTheIDString)
        }

        override def visitPackageBody(o : PackageBody) {
          this.g.isSpec = false
          visit(o.getTheName)
          this.g.packageName = this.g.popResult
        }

        override def visitPackageDeclaration(o : PackageDeclaration) {
          if (o.getTheOptionalInheritClauses != null) {
            this.g.theOptionalInheritNames = Some(mlistEmpty)
            for (inheritName <- o.getTheOptionalInheritClauses) {
              visit(inheritName)
              this.g.theOptionalInheritNames.get.add(this.g.popResult.get)
            }
          }
          visit(o.getThePackageSpecification)
        }

        override def visitPackageSpecification(o : PackageSpecification) {
          this.g.isSpec = true
          o.getTheName.getDescriptor match {
            case SelectedComponent.DESCRIPTOR | IDName.DESCRIPTOR =>
              visit(o.getTheName)
              this.g.packageName = this.g.popResult
            case _ =>
              assert(false)
          }
        }

        override def visitSelectedComponent(o : SelectedComponent) {
          val sb = new StringBuilder()
          o.getTheName.getDescriptor match {
            case SelectedComponent.DESCRIPTOR | IDName.DESCRIPTOR =>
              visit(o.getTheName)
              sb.append(this.g.popResult.get)
          }
          sb.append("." + o.getTheIDString)
          this.g.pushResult(Some(sb.toString))
        }

        override def visitSubUnitCompilationUnit(o : SubUnitCompilationUnit) {
          assert(false)
        }

        override def visitUseTypeClause(o : UseTypeClause) {
          for (n <- o.getTheNames) {
            visit(n)
            this.g.theUseTypeNames.add(this.g.popResult.get)
          }
        }

        override def visitWithClause(o : WithClause) {
          for (n <- o.getTheNames) {
            visit(n)
            this.g.theWithNames.add(this.g.popResult.get)
          }
        }
      }.visit(cu)

      if (vc.isMainProgram) {
        handleMainProgram(cu, vc)
      } else {
        handlePackages(cu, vc)
      }
    }
  }

  def doit(fn : String, messages : java.util.ArrayList[Message]) : Compilation = {
    try {
      val afs = new ANTLRFileStream(fn)
      return CompilationParser.parse(fn, afs, messages)
    } catch {
      case e =>
        e.printStackTrace
    }
    return null
  }
}