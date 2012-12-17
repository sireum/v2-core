package org.sireum.bakar.xml

import org.sireum.util._

object DefiningExpandedNameEx {
  def unapply(o : org.sireum.bakar.xml.DefiningExpandedName) = {
    Some(
      o.getSloc(),
      o.getDefiningPrefixQ(),
      o.getDefiningSelectorQ(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}