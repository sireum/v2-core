package org.sireum.bakar.xml

import org.sireum.util._

object AtClauseEx {
  def unapply(o : org.sireum.bakar.xml.AtClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getRepresentationClauseExpressionQ()
    )
  }
}