package org.sireum.bakar.xml

import org.sireum.util._

object ElaborateAllPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ElaborateAllPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}