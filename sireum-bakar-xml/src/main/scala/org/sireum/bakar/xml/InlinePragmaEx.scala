package org.sireum.bakar.xml

import org.sireum.util._

object InlinePragmaEx {
  def unapply(o : org.sireum.bakar.xml.InlinePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}