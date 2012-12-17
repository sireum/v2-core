package org.sireum.bakar.xml

import org.sireum.util._

object CaseExpressionPathEx {
  def unapply(o : org.sireum.bakar.xml.CaseExpressionPath) = {
    Some(
      o.getSloc(),
      o.getCasePathAlternativeChoicesQl(),
      o.getDependentExpressionQ()
    )
  }
}