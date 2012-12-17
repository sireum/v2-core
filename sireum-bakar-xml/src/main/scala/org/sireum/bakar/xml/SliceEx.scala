package org.sireum.bakar.xml

import org.sireum.util._

object SliceEx {
  def unapply(o : org.sireum.bakar.xml.Slice) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getSliceRangeQ(),
      o.getType()
    )
  }
}