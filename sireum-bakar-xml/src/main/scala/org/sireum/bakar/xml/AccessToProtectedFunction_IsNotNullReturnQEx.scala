package org.sireum.bakar.xml

import org.sireum.util._

object AccessToProtectedFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProtectedFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}