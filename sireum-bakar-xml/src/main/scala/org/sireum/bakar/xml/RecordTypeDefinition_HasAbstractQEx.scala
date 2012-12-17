package org.sireum.bakar.xml

import org.sireum.util._

object RecordTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.RecordTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}