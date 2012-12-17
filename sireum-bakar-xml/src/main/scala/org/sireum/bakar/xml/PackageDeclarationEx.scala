package org.sireum.bakar.xml

import org.sireum.util._

object PackageDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PackageDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getVisiblePartDeclarativeItemsQl(),
      o.getPrivatePartDeclarativeItemsQl()
    )
  }
}