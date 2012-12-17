package org.sireum.bakar.xml

import org.sireum.util._

object ElaborateBodyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ElaborateBodyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}