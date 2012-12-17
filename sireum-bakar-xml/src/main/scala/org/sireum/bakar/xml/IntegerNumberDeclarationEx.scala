package org.sireum.bakar.xml

import org.sireum.util._

object IntegerNumberDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.IntegerNumberDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getInitializationExpressionQ()
    )
  }
}