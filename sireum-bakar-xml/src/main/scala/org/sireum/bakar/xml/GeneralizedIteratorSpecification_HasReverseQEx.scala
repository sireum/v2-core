package org.sireum.bakar.xml

import org.sireum.util._

object GeneralizedIteratorSpecification_HasReverseQEx {
  def unapply(o : org.sireum.bakar.xml.GeneralizedIteratorSpecification.HasReverseQ) = {
    Some(
      o.getHasReverse()
    )
  }
}