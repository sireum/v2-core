package org.sireum.bakar.xml

import org.sireum.util._

object RealNumberDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.RealNumberDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getInitializationExpressionQ()
    )
  }
}