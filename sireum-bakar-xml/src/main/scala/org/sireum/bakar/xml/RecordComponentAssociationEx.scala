package org.sireum.bakar.xml

import org.sireum.util._

object RecordComponentAssociationEx {
  def unapply(o : org.sireum.bakar.xml.RecordComponentAssociation) = {
    Some(
      o.getSloc(),
      o.getRecordComponentChoicesQl(),
      o.getComponentExpressionQ()
    )
  }
}