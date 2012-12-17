package org.sireum.bakar.xml

import org.sireum.util._

object FunctionBodyDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}