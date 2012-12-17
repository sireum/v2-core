package org.sireum.bakar.xml

import org.sireum.util._

object PackPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PackPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}