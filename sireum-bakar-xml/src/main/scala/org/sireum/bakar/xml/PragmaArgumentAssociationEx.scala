package org.sireum.bakar.xml

import org.sireum.util._

object PragmaArgumentAssociationEx {
  def unapply(o : org.sireum.bakar.xml.PragmaArgumentAssociation) = {
    Some(
      o.getSloc(),
      o.getFormalParameterQ(),
      o.getActualParameterQ()
    )
  }
}