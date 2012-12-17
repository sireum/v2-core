package org.sireum.bakar.xml

import org.sireum.util._

object SingleTaskDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.SingleTaskDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getDeclarationInterfaceListQl(),
      o.getObjectDeclarationViewQ()
    )
  }
}