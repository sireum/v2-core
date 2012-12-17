package org.sireum.bakar.xml

import org.sireum.util._

object UncheckedUnionPragmaEx {
  def unapply(o : org.sireum.bakar.xml.UncheckedUnionPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}