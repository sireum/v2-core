package org.sireum.bakar.xml

import org.sireum.util._

object AllCallsRemotePragmaEx {
  def unapply(o : org.sireum.bakar.xml.AllCallsRemotePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}