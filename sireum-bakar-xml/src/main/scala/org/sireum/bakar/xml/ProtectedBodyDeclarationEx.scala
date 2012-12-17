package org.sireum.bakar.xml

import org.sireum.util._

object ProtectedBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedBodyDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getProtectedOperationItemsQl()
    )
  }
}