package org.sireum.bakar.xml

import org.sireum.util._

object PreelaborableInitializationPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PreelaborableInitializationPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}