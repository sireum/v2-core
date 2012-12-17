package org.sireum.bakar.xml

import org.sireum.util._

object DefiningNameClassEx {
  def unapply(o : org.sireum.bakar.xml.DefiningNameClass) = {
    Some(
      o.getDefiningName()
    )
  }
}