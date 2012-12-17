package org.sireum.bakar.xml

import org.sireum.util._

object ProfilePragmaEx {
  def unapply(o : org.sireum.bakar.xml.ProfilePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}