package org.sireum.bakar.xml

import org.sireum.util._

object GenericFunctionDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.GenericFunctionDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}