package org.sireum.bakar.xml

import org.sireum.util._

object FunctionBodyDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}