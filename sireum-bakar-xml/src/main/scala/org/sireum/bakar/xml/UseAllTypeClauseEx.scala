package org.sireum.bakar.xml

import org.sireum.util._

object UseAllTypeClauseEx {
  def unapply(o : org.sireum.bakar.xml.UseAllTypeClause) = {
    Some(
      o.getSloc(),
      o.getClauseNamesQl()
    )
  }
}