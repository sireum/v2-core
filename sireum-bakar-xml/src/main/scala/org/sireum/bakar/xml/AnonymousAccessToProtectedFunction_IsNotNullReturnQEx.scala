package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToProtectedFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}