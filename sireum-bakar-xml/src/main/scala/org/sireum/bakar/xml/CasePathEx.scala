package org.sireum.bakar.xml

import org.sireum.util._

object CasePathEx {
  def unapply(o : org.sireum.bakar.xml.CasePath) = {
    Some(
      o.getSloc(),
      o.getCasePathAlternativeChoicesQl(),
      o.getSequenceOfStatementsQl()
    )
  }
}