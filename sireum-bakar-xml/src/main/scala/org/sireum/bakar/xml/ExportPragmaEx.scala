package org.sireum.bakar.xml

import org.sireum.util._

object ExportPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ExportPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}