package org.sireum.bakar.xml

import org.sireum.util._

object FormalPackageDeclarationWithBoxEx {
  def unapply(o : org.sireum.bakar.xml.FormalPackageDeclarationWithBox) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getGenericUnitNameQ(),
      o.getAspectSpecificationsQl()
    )
  }
}