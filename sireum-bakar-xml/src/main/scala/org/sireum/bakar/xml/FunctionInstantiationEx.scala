package org.sireum.bakar.xml

import org.sireum.util._

object FunctionInstantiationEx {
  def unapply(o : org.sireum.bakar.xml.FunctionInstantiation) = {
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