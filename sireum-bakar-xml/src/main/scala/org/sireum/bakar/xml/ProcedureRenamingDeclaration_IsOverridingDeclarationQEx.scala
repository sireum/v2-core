package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureRenamingDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureRenamingDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}