package org.sireum.bakar.xml

import org.sireum.util._

object SourceLocationEx {
  def unapply(o : org.sireum.bakar.xml.SourceLocation) = {
    Some(
      o.getLine(),
      o.getCol(),
      o.getEndline(),
      o.getEndcol()
    )
  }
}