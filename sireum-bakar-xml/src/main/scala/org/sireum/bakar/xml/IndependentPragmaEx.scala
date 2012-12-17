package org.sireum.bakar.xml

import org.sireum.util._

object IndependentPragmaEx {
  def unapply(o : org.sireum.bakar.xml.IndependentPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}