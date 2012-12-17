package org.sireum.bakar.xml

import org.sireum.util._

object FormalProcedureDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalProcedureDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getFormalSubprogramDefaultQ(),
      o.getHasAbstractQ(),
      o.getAspectSpecificationsQl()
    )
  }
}