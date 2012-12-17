package org.sireum.bakar.xml

import org.sireum.util._

object FunctionDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}