package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureInstantiationEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureInstantiation) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getGenericUnitNameQ(),
      o.getGenericActualPartQl(),
      o.getAspectSpecificationsQl()
    )
  }
}