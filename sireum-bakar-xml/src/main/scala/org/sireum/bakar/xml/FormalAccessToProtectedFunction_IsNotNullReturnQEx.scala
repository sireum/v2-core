package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToProtectedFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}