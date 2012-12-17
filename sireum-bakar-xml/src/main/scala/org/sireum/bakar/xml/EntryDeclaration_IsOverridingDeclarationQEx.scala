package org.sireum.bakar.xml

import org.sireum.util._

object EntryDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.EntryDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}