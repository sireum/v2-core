package org.sireum.bakar.xml

import org.sireum.util._

object FunctionDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}