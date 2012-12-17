package org.sireum.bakar.xml

import org.sireum.util._

object DigitsConstraintEx {
  def unapply(o : org.sireum.bakar.xml.DigitsConstraint) = {
    Some(
      o.getSloc(),
      o.getDigitsExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}