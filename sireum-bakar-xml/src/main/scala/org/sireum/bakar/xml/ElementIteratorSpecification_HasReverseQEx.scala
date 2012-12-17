package org.sireum.bakar.xml

import org.sireum.util._

object ElementIteratorSpecification_HasReverseQEx {
  def unapply(o : org.sireum.bakar.xml.ElementIteratorSpecification.HasReverseQ) = {
    Some(
      o.getHasReverse()
    )
  }
}