package org.sireum.bakar.xml

import org.sireum.util._

object ExceptionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ExceptionDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl()
    )
  }
}