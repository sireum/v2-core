package org.sireum.bakar.xml

import org.sireum.util._

object ImplementationDefinedPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ImplementationDefinedPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}