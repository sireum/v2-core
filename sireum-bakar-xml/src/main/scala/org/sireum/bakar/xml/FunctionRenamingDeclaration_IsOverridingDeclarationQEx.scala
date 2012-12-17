package org.sireum.bakar.xml

import org.sireum.util._

object FunctionRenamingDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionRenamingDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}