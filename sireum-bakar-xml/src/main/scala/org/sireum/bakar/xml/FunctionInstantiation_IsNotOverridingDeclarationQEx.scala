package org.sireum.bakar.xml

import org.sireum.util._

object FunctionInstantiation_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionInstantiation.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}