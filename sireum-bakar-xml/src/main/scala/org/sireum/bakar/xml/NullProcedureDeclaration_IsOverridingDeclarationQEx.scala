package org.sireum.bakar.xml

import org.sireum.util._

object NullProcedureDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.NullProcedureDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}