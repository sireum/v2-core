package org.sireum.bakar.xml

import org.sireum.util._

object FormalProcedureDeclaration_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FormalProcedureDeclaration.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}