package org.sireum.bakar.xml

import org.sireum.util._

object GenericPackageDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericPackageDeclaration) = {
    Some(
      o.getSloc(),
      o.getGenericFormalPartQl(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getVisiblePartDeclarativeItemsQl(),
      o.getPrivatePartDeclarativeItemsQl()
    )
  }
}