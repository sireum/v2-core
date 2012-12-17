package org.sireum.bakar.xml

import org.sireum.util._

object DerivedRecordExtensionDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.DerivedRecordExtensionDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}