package org.sireum.bakar.xml

import org.sireum.util._

object CpuPragmaEx {
  def unapply(o : org.sireum.bakar.xml.CpuPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}