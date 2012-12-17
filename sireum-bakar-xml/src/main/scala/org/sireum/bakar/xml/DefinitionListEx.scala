package org.sireum.bakar.xml

import org.sireum.util._

object DefinitionListEx {
  def unapply(o : org.sireum.bakar.xml.DefinitionList) = {
    Some(
      o.getDefinitions()
    )
  }
}