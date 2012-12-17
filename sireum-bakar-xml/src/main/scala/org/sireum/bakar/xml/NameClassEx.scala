package org.sireum.bakar.xml

import org.sireum.util._

object NameClassEx {
  def unapply(o : org.sireum.bakar.xml.NameClass) = {
    Some(
      o.getName()
    )
  }
}