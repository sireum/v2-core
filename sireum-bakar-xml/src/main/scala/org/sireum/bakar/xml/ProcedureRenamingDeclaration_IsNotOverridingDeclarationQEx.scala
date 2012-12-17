package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureRenamingDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureRenamingDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}