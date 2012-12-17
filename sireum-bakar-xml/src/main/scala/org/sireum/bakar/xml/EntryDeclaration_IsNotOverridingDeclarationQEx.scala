package org.sireum.bakar.xml

import org.sireum.util._

object EntryDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.EntryDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}