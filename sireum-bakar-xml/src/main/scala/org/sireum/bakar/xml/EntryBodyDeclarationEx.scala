package org.sireum.bakar.xml

import org.sireum.util._

object EntryBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.EntryBodyDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getEntryIndexSpecificationQ(),
      o.getParameterProfileQl(),
      o.getEntryBarrierQ(),
      o.getBodyDeclarativeItemsQl(),
      o.getBodyStatementsQl(),
      o.getBodyExceptionHandlersQl()
    )
  }
}