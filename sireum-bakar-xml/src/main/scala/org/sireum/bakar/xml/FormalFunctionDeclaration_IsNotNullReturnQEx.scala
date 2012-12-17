package org.sireum.bakar.xml

import org.sireum.util._

object FormalFunctionDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FormalFunctionDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}