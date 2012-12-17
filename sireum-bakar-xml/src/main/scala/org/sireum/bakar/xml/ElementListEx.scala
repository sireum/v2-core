package org.sireum.bakar.xml

import org.sireum.util._

object ElementListEx {
  def unapply(o : org.sireum.bakar.xml.ElementList) = {
    Some(
      o.getElements()
    )
  }
}