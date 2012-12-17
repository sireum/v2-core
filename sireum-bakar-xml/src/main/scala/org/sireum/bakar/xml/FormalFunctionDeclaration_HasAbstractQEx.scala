package org.sireum.bakar.xml

import org.sireum.util._

object FormalFunctionDeclaration_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FormalFunctionDeclaration.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}