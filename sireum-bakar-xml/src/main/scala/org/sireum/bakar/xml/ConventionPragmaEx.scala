package org.sireum.bakar.xml

import org.sireum.util._

object ConventionPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ConventionPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}