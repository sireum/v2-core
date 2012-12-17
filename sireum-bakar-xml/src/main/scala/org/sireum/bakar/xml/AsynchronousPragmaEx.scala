package org.sireum.bakar.xml

import org.sireum.util._

object AsynchronousPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AsynchronousPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}