package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureInstantiation_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureInstantiation.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}