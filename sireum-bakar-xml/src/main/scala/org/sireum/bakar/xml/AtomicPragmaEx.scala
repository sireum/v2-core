package org.sireum.bakar.xml

import org.sireum.util._

object AtomicPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AtomicPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}