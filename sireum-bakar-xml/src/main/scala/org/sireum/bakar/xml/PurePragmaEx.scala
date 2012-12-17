package org.sireum.bakar.xml

import org.sireum.util._

object PurePragmaEx {
  def unapply(o : org.sireum.bakar.xml.PurePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}