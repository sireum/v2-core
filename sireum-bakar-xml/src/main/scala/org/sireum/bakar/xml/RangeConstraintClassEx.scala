package org.sireum.bakar.xml

import org.sireum.util._

object RangeConstraintClassEx {
  def unapply(o : org.sireum.bakar.xml.RangeConstraintClass) = {
    Some(
      o.getRangeConstraint()
    )
  }
}