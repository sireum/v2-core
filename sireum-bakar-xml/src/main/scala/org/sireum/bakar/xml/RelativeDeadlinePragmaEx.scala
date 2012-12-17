package org.sireum.bakar.xml

import org.sireum.util._

object RelativeDeadlinePragmaEx {
  def unapply(o : org.sireum.bakar.xml.RelativeDeadlinePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}