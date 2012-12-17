package org.sireum.bakar.xml

import org.sireum.util._

object ArrayComponentAssociationEx {
  def unapply(o : org.sireum.bakar.xml.ArrayComponentAssociation) = {
    Some(
      o.getSloc(),
      o.getArrayComponentChoicesQl(),
      o.getComponentExpressionQ()
    )
  }
}