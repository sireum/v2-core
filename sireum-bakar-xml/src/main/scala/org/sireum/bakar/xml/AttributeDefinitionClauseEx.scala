package org.sireum.bakar.xml

import org.sireum.util._

object AttributeDefinitionClauseEx {
  def unapply(o : org.sireum.bakar.xml.AttributeDefinitionClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getRepresentationClauseExpressionQ()
    )
  }
}