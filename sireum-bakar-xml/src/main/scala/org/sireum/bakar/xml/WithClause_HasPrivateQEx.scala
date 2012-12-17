package org.sireum.bakar.xml

import org.sireum.util._

object WithClause_HasPrivateQEx {
  def unapply(o : org.sireum.bakar.xml.WithClause.HasPrivateQ) = {
    Some(
      o.getHasPrivate()
    )
  }
}