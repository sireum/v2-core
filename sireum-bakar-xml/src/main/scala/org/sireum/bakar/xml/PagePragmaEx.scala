package org.sireum.bakar.xml

import org.sireum.util._

object PagePragmaEx {
  def unapply(o : org.sireum.bakar.xml.PagePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}