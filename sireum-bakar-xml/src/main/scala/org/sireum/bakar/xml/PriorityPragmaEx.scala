package org.sireum.bakar.xml

import org.sireum.util._

object PriorityPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PriorityPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}