package org.sireum.bakar.xml

import org.sireum.util._

object AccessToFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}