package org.sireum.bakar.xml

import org.sireum.util._

object PathListEx {
  def unapply(o : org.sireum.bakar.xml.PathList) = {
    Some(
      o.getPaths()
    )
  }
}