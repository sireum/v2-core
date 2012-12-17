package org.sireum.bakar.xml

import org.sireum.util._

object ExceptionRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ExceptionRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}