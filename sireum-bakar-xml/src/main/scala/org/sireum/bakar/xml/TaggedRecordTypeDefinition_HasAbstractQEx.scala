package org.sireum.bakar.xml

import org.sireum.util._

object TaggedRecordTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.TaggedRecordTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}