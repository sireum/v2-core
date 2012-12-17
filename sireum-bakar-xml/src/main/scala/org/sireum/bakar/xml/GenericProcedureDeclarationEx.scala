package org.sireum.bakar.xml

import org.sireum.util._

object GenericProcedureDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericProcedureDeclaration) = {
    Some(
      o.getSloc(),
      o.getGenericFormalPartQl(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getAspectSpecificationsQl()
    )
  }
}