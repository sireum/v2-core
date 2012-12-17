package org.sireum.bakar.xml

import org.sireum.util._

object LoopParameterSpecification_HasReverseQEx {
  def unapply(o : org.sireum.bakar.xml.LoopParameterSpecification.HasReverseQ) = {
    Some(
      o.getHasReverse()
    )
  }
}