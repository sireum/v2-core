package org.sireum.bakar.xml

import org.sireum.util._

object DeclarativeItemListEx {
  def unapply(o : org.sireum.bakar.xml.DeclarativeItemList) = {
    Some(
      o.getDeclarativeItems()
    )
  }
}