package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}