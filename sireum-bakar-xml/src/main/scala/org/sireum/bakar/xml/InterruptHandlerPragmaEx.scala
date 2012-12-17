package org.sireum.bakar.xml

import org.sireum.util._

object InterruptHandlerPragmaEx {
  def unapply(o : org.sireum.bakar.xml.InterruptHandlerPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}