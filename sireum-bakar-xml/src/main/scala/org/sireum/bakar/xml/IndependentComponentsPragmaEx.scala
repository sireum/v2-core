package org.sireum.bakar.xml

import org.sireum.util._

object IndependentComponentsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.IndependentComponentsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}