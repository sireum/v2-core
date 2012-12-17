package org.sireum.bakar.xml

import org.sireum.util._

object OthersChoiceEx {
  def unapply(o : org.sireum.bakar.xml.OthersChoice) = {
    Some(
      o.getSloc()
    )
  }
}