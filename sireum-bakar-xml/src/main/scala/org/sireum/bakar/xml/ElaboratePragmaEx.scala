package org.sireum.bakar.xml

import org.sireum.util._

object ElaboratePragmaEx {
  def unapply(o : org.sireum.bakar.xml.ElaboratePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}