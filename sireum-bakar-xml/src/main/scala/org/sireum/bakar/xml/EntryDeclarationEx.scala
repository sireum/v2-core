package org.sireum.bakar.xml

import org.sireum.util._

object EntryDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.EntryDeclaration) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getEntryFamilyDefinitionQ(),
      o.getParameterProfileQl(),
      o.getAspectSpecificationsQl()
    )
  }
}