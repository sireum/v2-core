package org.sireum.bakar.xml

import org.sireum.util._

object UnknownPragmaEx {
  def unapply(o : org.sireum.bakar.xml.UnknownPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}