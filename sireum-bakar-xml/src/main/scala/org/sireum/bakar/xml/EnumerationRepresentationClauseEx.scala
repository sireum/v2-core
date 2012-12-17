package org.sireum.bakar.xml

import org.sireum.util._

object EnumerationRepresentationClauseEx {
  def unapply(o : org.sireum.bakar.xml.EnumerationRepresentationClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getRepresentationClauseExpressionQ()
    )
  }
}