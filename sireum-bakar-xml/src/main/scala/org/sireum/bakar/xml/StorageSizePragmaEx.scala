package org.sireum.bakar.xml

import org.sireum.util._

object StorageSizePragmaEx {
  def unapply(o : org.sireum.bakar.xml.StorageSizePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}