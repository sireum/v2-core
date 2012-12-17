package org.sireum.bakar.xml

import org.sireum.util._

object PrioritySpecificDispatchingPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PrioritySpecificDispatchingPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}