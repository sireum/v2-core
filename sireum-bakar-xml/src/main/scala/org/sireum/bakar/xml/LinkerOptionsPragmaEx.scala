package org.sireum.bakar.xml

import org.sireum.util._

object LinkerOptionsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.LinkerOptionsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}