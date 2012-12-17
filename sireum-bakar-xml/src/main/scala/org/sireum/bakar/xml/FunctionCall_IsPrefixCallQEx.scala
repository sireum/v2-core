package org.sireum.bakar.xml

import org.sireum.util._

object FunctionCall_IsPrefixCallQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionCall.IsPrefixCallQ) = {
    Some(
      o.getIsPrefixCall()
    )
  }
}