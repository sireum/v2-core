package org.sireum.bakar.xml

import org.sireum.util._

object WithClauseEx {
  def unapply(o : org.sireum.bakar.xml.WithClause) = {
    Some(
      o.getSloc(),
      o.getHasLimitedQ(),
      o.getHasPrivateQ(),
      o.getClauseNamesQl()
    )
  }
}