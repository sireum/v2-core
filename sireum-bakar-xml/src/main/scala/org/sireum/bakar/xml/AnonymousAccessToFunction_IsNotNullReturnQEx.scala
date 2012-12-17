package org.sireum.bakar.xml

import org.sireum.util._

object AnonymousAccessToFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}