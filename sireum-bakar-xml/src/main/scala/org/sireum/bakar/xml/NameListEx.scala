package org.sireum.bakar.xml

import org.sireum.util._

object NameListEx {
  def unapply(o : org.sireum.bakar.xml.NameList) = {
    Some(
      o.getNames()
    )
  }
}