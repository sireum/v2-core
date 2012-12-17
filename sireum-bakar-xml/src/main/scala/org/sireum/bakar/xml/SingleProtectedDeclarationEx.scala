package org.sireum.bakar.xml

import org.sireum.util._

object SingleProtectedDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.SingleProtectedDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getDeclarationInterfaceListQl(),
      o.getObjectDeclarationViewQ()
    )
  }
}