package org.sireum.bakar.xml

import org.sireum.util._

object ParameterAssociationEx {
  def unapply(o : org.sireum.bakar.xml.ParameterAssociation) = {
    Some(
      o.getSloc(),
      o.getFormalParameterQ(),
      o.getActualParameterQ()
    )
  }
}