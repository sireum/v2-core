package org.sireum.bakar.xml

import org.sireum.util._

object DeclarationClassEx {
  def unapply(o : org.sireum.bakar.xml.DeclarationClass) = {
    Some(
      o.getDeclaration()
    )
  }
}