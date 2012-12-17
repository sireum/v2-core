package org.sireum.bakar.xml

import org.sireum.util._

object DefaultStoragePoolPragmaEx {
  def unapply(o : org.sireum.bakar.xml.DefaultStoragePoolPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}