package org.sireum.bakar.xml

import org.sireum.util._

object FunctionDeclaration_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionDeclaration.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}