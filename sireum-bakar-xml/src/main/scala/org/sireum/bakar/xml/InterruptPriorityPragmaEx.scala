package org.sireum.bakar.xml

import org.sireum.util._

object InterruptPriorityPragmaEx {
  def unapply(o : org.sireum.bakar.xml.InterruptPriorityPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}