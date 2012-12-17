package org.sireum.bakar.xml

import org.sireum.util._

object IndexedComponentEx {
  def unapply(o : org.sireum.bakar.xml.IndexedComponent) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getIndexExpressionsQl(),
      o.getType()
    )
  }
}