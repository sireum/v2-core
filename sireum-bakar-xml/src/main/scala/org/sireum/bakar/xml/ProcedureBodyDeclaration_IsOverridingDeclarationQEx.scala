package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureBodyDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}