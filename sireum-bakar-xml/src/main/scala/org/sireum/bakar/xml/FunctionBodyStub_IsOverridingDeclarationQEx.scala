package org.sireum.bakar.xml

import org.sireum.util._

object FunctionBodyStub_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyStub.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}