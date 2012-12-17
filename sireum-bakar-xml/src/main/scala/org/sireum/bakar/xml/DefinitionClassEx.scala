package org.sireum.bakar.xml

import org.sireum.util._

object DefinitionClassEx {
  def unapply(o : org.sireum.bakar.xml.DefinitionClass) = {
    Some(
      o.getDefinition()
    )
  }
}