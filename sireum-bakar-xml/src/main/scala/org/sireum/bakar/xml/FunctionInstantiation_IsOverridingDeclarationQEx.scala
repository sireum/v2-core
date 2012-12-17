package org.sireum.bakar.xml

import org.sireum.util._

object FunctionInstantiation_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionInstantiation.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}