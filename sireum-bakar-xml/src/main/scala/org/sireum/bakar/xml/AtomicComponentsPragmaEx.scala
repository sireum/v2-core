package org.sireum.bakar.xml

import org.sireum.util._

object AtomicComponentsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AtomicComponentsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}