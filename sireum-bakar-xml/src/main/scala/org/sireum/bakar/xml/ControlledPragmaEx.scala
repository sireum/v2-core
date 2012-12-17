package org.sireum.bakar.xml

import org.sireum.util._

object ControlledPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ControlledPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}