package org.sireum.bakar.xml

import org.sireum.util._

object InspectionPointPragmaEx {
  def unapply(o : org.sireum.bakar.xml.InspectionPointPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}