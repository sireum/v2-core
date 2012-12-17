package org.sireum.bakar.xml

import org.sireum.util._

object NullProcedureDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.NullProcedureDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}