package org.sireum.bakar.xml

import org.sireum.util._

object WithClause_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.WithClause.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}