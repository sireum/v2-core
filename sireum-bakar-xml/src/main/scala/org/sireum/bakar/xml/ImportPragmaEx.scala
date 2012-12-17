package org.sireum.bakar.xml

import org.sireum.util._

object ImportPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ImportPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}