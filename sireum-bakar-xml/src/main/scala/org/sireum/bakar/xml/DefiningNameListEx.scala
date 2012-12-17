package org.sireum.bakar.xml

import org.sireum.util._

object DefiningNameListEx {
  def unapply(o : org.sireum.bakar.xml.DefiningNameList) = {
    Some(
      o.getDefiningNames()
    )
  }
}