package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureBodyDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}