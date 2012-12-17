package org.sireum.bakar.xml

import org.sireum.util._

object SharedPassivePragmaEx {
  def unapply(o : org.sireum.bakar.xml.SharedPassivePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}