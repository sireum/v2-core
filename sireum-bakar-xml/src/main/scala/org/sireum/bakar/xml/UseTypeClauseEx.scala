package org.sireum.bakar.xml

import org.sireum.util._

object UseTypeClauseEx {
  def unapply(o : org.sireum.bakar.xml.UseTypeClause) = {
    Some(
      o.getSloc(),
      o.getClauseNamesQl()
    )
  }
}