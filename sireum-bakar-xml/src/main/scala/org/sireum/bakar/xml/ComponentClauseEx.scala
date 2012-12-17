package org.sireum.bakar.xml

import org.sireum.util._

object ComponentClauseEx {
  def unapply(o : org.sireum.bakar.xml.ComponentClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getComponentClausePositionQ(),
      o.getComponentClauseRangeQ()
    )
  }
}