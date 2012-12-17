package org.sireum.bakar.xml

import org.sireum.util._

object ElementClassEx {
  def unapply(o : org.sireum.bakar.xml.ElementClass) = {
    Some(
      o.getElement()
    )
  }
}