package org.sireum.bakar.xml

import org.sireum.util._

object GenericAssociationEx {
  def unapply(o : org.sireum.bakar.xml.GenericAssociation) = {
    Some(
      o.getSloc(),
      o.getFormalParameterQ(),
      o.getActualParameterQ()
    )
  }
}