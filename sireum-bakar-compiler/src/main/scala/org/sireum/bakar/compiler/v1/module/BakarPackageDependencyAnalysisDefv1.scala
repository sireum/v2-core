package org.sireum.bakar.compiler.v1.module

import org.sireum.pipeline._
import org.sireum.bakar.compiler.v1.module.util.SPARKCompilationUnit
import scala.collection.JavaConversions._
import org.sireum.bakar.SparkUtil
import org.sireum.bakar.compiler.v1.module.util.SPARKSpecBodyUnit
import org.sireum.bakar.compiler.v1.predefined.StandardPackage
import org.sireum.util._
import org.sireum.bakar.compiler.v1.module.util.SPARKLibraryItemType
import org.sireum.bakar.compiler.v1.module.util.SPARKParserResult
import org.sireum.bakar.compiler.v1.option.SparkCompilerOptions
import org.sireum.bakar.compiler.v1.module._
import org.sireum.bakar.compiler.v1.module.BakarPackageDependencyAnalysisv1Module

class BakarPackageDependencyAnalysisv1Def(val job : PipelineJob, info : PipelineJobModuleInfo) extends BakarPackageDependencyAnalysisv1Module {
  val sco = this.sparkCompilerOptions
  val spr = this.sparkParserResult
  this.transOrder_=(new Helper(sco, spr).execute.toList)
}

class Helper(sco : SparkCompilerOptions, spr : SPARKParserResult) {
  var theStandardPackageRefinement : SPARKSpecBodyUnit = null
  val dg = new DependencyGraph()

  def execute : MList[SPARKCompilationUnit] = {

    if (spr.getThePackageNameToSpecBodyNodes != null) {
      for (packageName <- spr.getThePackageNameToSpecBodyNodes.keySet) {
        val ssbu = spr.getThePackageNameToSpecBodyNodes.get(packageName)

        if (packageName.toLowerCase.equalsIgnoreCase(SparkUtil.SPARK_STANDARD_PACKAGE)) {
          assert(ssbu.getTheOptionalPackageBody == null)
          this.theStandardPackageRefinement = ssbu;
        }

        if (ssbu.getTheOptionalPackageSpecification != null) {
          val packSpec = ssbu.getTheOptionalPackageSpecification
          computeDependencies(packSpec)
        }

        if (ssbu.getTheOptionalPackageBody != null) {
          val packBody = ssbu.getTheOptionalPackageBody
          computeDependencies(packBody)
        }
      }
    }

    if (spr.getTheOptionalMainCompilationUnit != null) {
      computeDependencies(spr.getTheOptionalMainCompilationUnit)
    }

    val pdmo = sco.getOptPackageDependencyOptions
    if (pdmo != null && pdmo.getGenDependencyGraph) {

    }

    val theSeenSet = msetEmpty[SPARKCompilationUnit]
    val theOrder = mlistEmpty[SPARKCompilationUnit]
    for (dn <- this.dg.theNodes) {
      generateTranslatorItems(dn, theOrder, theSeenSet)
    }

    if (this.theStandardPackageRefinement != null) {
      //theOrder.add(0, this.theStandardPackageRefinement.getTheOptionalPackageSpecification)
      this.theStandardPackageRefinement.getTheOptionalPackageSpecification +=: theOrder
    } else {
      //theOrder.add(0, new StandardPackage().constructSPARKCompilationUnit)
      new StandardPackage().constructSPARKCompilationUnit +=: theOrder
    }

    return theOrder
  }

  def computeDependencies(scu : SPARKCompilationUnit) {
    assert(scu != null)

    if (this.dg.doesContain(scu)) {
      return
    }

    val dn = new DependencyNode(scu)
    this.dg.theNodes += dn

    if (scu.getTheOptionalLibraryItemType == SPARKLibraryItemType.PACKAGE_BODY) {
      val ssbu = this.spr.getThePackageNameToSpecBodyNodes.get(scu.getThePackageName)
      if (ssbu.getTheOptionalPackageSpecification != null) {
        dn.ancestors += ssbu.getTheOptionalPackageSpecification
      }
    }

    val combinedDepList = mlistEmpty[String]
    if (scu.getTheOptionalWithNames != null) {
      combinedDepList ++= scu.getTheOptionalWithNames
    }

    if (scu.getTheOptionalInheritNames != null) {
      for (n <- scu.getTheOptionalInheritNames) {
        if (!combinedDepList.contains(n)) {
          combinedDepList += n
        }
      }
    }

    for (packageName <- combinedDepList) {
      val ancestorSBU = this.spr.getThePackageNameToSpecBodyNodes.get(packageName)
      assert(ancestorSBU != null)
      if (ancestorSBU.getTheOptionalPackageSpecification != null) {
        dn.ancestors += ancestorSBU.getTheOptionalPackageSpecification
        computeDependencies(ancestorSBU.getTheOptionalPackageSpecification)
      }
      if (ancestorSBU.getTheOptionalPackageBody != null) {
        dn.ancestors += ancestorSBU.getTheOptionalPackageBody
        computeDependencies(ancestorSBU.getTheOptionalPackageBody)
      }
    }
  }

  def generateTranslatorItems(dn : DependencyNode, scuOrder : MList[SPARKCompilationUnit],
                              theSeenSet : MSet[SPARKCompilationUnit]) {
    if (theSeenSet.contains(dn.getScu)) {
      return
    }
    theSeenSet += dn.getScu

    for (ancestor <- dn.ancestors) {
      generateTranslatorItems(this.dg.getNode(ancestor), scuOrder, theSeenSet)
    }

    scuOrder += dn.getScu
  }
}

class DependencyGraph {
  val theNodes = mlistEmpty[DependencyNode]

  def doesContain(scu : SPARKCompilationUnit) : Boolean = {
    for (dn <- this.theNodes) {
      if (dn.getScu == scu) {
        return true
      }
    }
    return false
  }

  def getNode(ancestor : SPARKCompilationUnit) : DependencyNode = {
    for (dn <- this.theNodes) {
      if (dn.getScu == ancestor) {
        return dn
      }
    }
    throw new Exception()
    return null;
  }
}

class DependencyNode(theScu : SPARKCompilationUnit) {
  val ancestors = mlistEmpty[SPARKCompilationUnit]
  def getScu = theScu
}