package org.sireum.bakar.xml

import org.sireum.util._

object FormalPackageDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalPackageDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getGenericUnitNameQ(),
      o.getGenericActualPartQl(),
      o.getAspectSpecificationsQl()
    )
  }
}