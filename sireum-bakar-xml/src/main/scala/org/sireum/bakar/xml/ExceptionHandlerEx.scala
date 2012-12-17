package org.sireum.bakar.xml

import org.sireum.util._

object ExceptionHandlerEx {
  def unapply(o : org.sireum.bakar.xml.ExceptionHandler) = {
    Some(
      o.getSloc(),
      o.getChoiceParameterSpecificationQ(),
      o.getExceptionChoicesQl(),
      o.getHandlerStatementsQl()
    )
  }
}