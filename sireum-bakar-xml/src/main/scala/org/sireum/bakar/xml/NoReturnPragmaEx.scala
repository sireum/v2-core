package org.sireum.bakar.xml

import org.sireum.util._

object NoReturnPragmaEx {
  def unapply(o : org.sireum.bakar.xml.NoReturnPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}