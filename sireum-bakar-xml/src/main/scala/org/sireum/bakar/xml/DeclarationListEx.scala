package org.sireum.bakar.xml

import org.sireum.util._

object DeclarationListEx {
  def unapply(o : org.sireum.bakar.xml.DeclarationList) = {
    Some(
      o.getDeclarations()
    )
  }
}