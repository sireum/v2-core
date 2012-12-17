package org.sireum.bakar.xml

import org.sireum.util._

object CodeStatementEx {
  def unapply(o : org.sireum.bakar.xml.CodeStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getQualifiedExpressionQ()
    )
  }
}