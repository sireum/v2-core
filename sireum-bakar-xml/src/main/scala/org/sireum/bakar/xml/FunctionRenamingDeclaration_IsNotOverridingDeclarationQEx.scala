package org.sireum.bakar.xml

import org.sireum.util._

object FunctionRenamingDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionRenamingDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}