package org.sireum.bakar.xml

import org.sireum.util._

object PrivateExtensionDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.PrivateExtensionDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}