package org.sireum.bakar.xml

import org.sireum.util._

object ExceptionHandlerListEx {
  def unapply(o : org.sireum.bakar.xml.ExceptionHandlerList) = {
    Some(
      o.getExceptionHandlers()
    )
  }
}