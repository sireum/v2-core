package org.sireum.bakar.xml

import org.sireum.util._

object OptimizePragmaEx {
  def unapply(o : org.sireum.bakar.xml.OptimizePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}