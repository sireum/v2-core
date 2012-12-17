package org.sireum.bakar.xml

import org.sireum.util._

object ProcedureInstantiation_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureInstantiation.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}