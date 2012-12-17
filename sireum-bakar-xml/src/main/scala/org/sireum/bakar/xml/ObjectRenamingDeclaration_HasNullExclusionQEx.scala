package org.sireum.bakar.xml

import org.sireum.util._

object ObjectRenamingDeclaration_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.ObjectRenamingDeclaration.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}