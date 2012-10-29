package org.sireum.bakar.alir

import org.sireum.alir._
import org.sireum.util._
import org.sireum.pilar.symbol.SymbolTable
import org.sireum.pilar.ast._

final case class RecordSlot(accessExp: AccessExp) extends Slot {
  override def toString = {
    var Expstring = ""
    var visitor: Any => Boolean = Visitor.build({
      case ne: NameExp =>
        if (ne.name.hasResourceInfo) {
          val uri = ne.name.resourceUri
          Expstring = uri.substring(uri.lastIndexOf('/') + 1) + Expstring
        }
        false
      case nu: NameUser => {
        Expstring += "." + nu.name
        false
      }
    })
    visitor(accessExp)
    Expstring
  }
}

class BakarNodeDefRef(st: SymbolTable, val varAccesses: VarAccesses) extends DefRef {
  private val bvdr = new BasicVarDefRef(st, varAccesses)
  private val trueFilter = { j: CallJump =>
    { procedureAbsUri: ResourceUri => true }
  }

  def definitions(a: Assignment): ISet[Slot] = {
    import org.sireum.pilar.symbol.H
    var result = isetEmpty[Slot]
    a match {
      case aA: AssignAction =>
        aA.lhs match {
          case ie: IndexingExp => {
            val lhs = ie.exp
            result = result + VarSlot(lhs match { case ne: NameExp => ne.name.resourceUri })
          }
          case ae: AccessExp => {
            result = result + RecordSlot(ae)
          }
          case _ => result = bvdr.definitions(a)
        }
      case _ => result = bvdr.definitions(a)
    }
    result
  }

  def strongDefinitions(a: Assignment): ISet[Slot] = {
    a match {
      case aA: AssignAction =>
        aA.lhs match {
          case ie: IndexingExp =>
            isetEmpty[Slot]
          case ae: AccessExp => this.definitions(a)
          case _ => bvdr.strongDefinitions(a)
        }
      case _ => bvdr.strongDefinitions(a)
    }
  }

  def references(a: Action): ISet[Slot] = {
    import org.sireum.pilar.symbol.H
    var result = isetEmpty[Slot]
    val aA = a.asInstanceOf[AssignAction]

    if (aA.rhs.isInstanceOf[IndexingExp]) {
      val ier = aA.rhs.asInstanceOf[IndexingExp]
      result = result + VarSlot(ier.exp match { case ne: NameExp => ne.name.resourceUri })
      val y = ier.indices.map(f => VarSlot(f match { case ne: NameExp => ne.name.resourceUri }))
      result = result ++ y
    }

    if (aA.rhs.isInstanceOf[AccessExp]) {
      val aer = aA.rhs.asInstanceOf[AccessExp]
      result += RecordSlot(aer)
    }

    if (aA.lhs.isInstanceOf[IndexingExp]) {
      val iel = aA.lhs.asInstanceOf[IndexingExp]
      result = result ++ iel.indices.map(f => VarSlot(f match { case ne: NameExp => ne.name.resourceUri }))
      if (!aA.rhs.isInstanceOf[IndexingExp] && !aA.rhs.isInstanceOf[AccessExp])
        result = result + VarSlot(aA.rhs match { case ne: NameExp => ne.name.resourceUri })
    }

    if (aA.lhs.isInstanceOf[AccessExp]) {
      //  val ael = aA.lhs.asInstanceOf[AccessExp]
      if (!aA.rhs.isInstanceOf[IndexingExp] && !aA.rhs.isInstanceOf[AccessExp])
        result = result + VarSlot(aA.rhs match { case ne: NameExp => ne.name.resourceUri })
    }
    if (!aA.rhs.isInstanceOf[IndexingExp] && !aA.lhs.isInstanceOf[IndexingExp]
      && !aA.rhs.isInstanceOf[AccessExp] && !aA.lhs.isInstanceOf[AccessExp]) {
      result = result ++ bvdr.references(a)
      result
    }
    result
  }

  def references(j: Jump): ISet[Slot] = bvdr.references(j)
  def callReferences(j: CallJump): ISeq[ISet[Slot]] = bvdr.callReferences(j)
  def callDefinitions(j: CallJump): ISeq[ISet[Slot]] = bvdr.callDefinitions(j)
}