package org.sireum.bakar.xml

import org.sireum.util._

object FunctionCallEx {
  def unapply(o : org.sireum.bakar.xml.FunctionCall) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getFunctionCallParametersQl(),
      o.getIsPrefixCallQ(),
      o.getIsPrefixNotationQ(),
      o.getType()
    )
  }
}