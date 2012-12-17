package org.sireum.bakar.xml

import org.sireum.util._

object FormalAccessToFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}