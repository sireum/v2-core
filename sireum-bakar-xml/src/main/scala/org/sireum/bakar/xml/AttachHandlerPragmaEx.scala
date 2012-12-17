package org.sireum.bakar.xml

import org.sireum.util._

object AttachHandlerPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AttachHandlerPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}