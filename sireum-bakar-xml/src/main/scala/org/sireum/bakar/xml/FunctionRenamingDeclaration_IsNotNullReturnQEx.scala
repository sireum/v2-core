package org.sireum.bakar.xml

import org.sireum.util._

object FunctionRenamingDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionRenamingDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}