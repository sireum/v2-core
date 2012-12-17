package org.sireum.bakar.xml

import org.sireum.util._

object GotoStatementEx {
  def unapply(o : org.sireum.bakar.xml.GotoStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getGotoLabelQ()
    )
  }
}