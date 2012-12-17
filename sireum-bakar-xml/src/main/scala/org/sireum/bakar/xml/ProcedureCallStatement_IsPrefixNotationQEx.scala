package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureCallStatement_IsPrefixNotationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureCallStatement.IsPrefixNotationQ) = {
    Some(
      o.getIsPrefixNotation()
    )
  }
}