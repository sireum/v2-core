package org.sireum.bakar.xml

import org.sireum.util._

object UsePackageClauseEx {
  def unapply(o : org.sireum.bakar.xml.UsePackageClause) = {
    Some(
      o.getSloc(),
      o.getClauseNamesQl()
    )
  }
}