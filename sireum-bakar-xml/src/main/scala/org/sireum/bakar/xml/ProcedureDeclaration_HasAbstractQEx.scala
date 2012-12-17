package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureDeclaration_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureDeclaration.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}