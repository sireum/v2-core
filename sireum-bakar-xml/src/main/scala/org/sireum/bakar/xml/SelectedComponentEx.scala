package org.sireum.bakar.xml

import org.sireum.util._

object SelectedComponentEx {
  def unapply(o : org.sireum.bakar.xml.SelectedComponent) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getSelectorQ(),
      o.getType()
    )
  }
}