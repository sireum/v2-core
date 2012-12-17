package org.sireum.bakar.xml

import org.sireum.util._

object RecordRepresentationClauseEx {
  def unapply(o : org.sireum.bakar.xml.RecordRepresentationClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getModClauseExpressionQ(),
      o.getComponentClausesQl()
    )
  }
}