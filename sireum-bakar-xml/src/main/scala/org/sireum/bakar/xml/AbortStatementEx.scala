package org.sireum.bakar.xml

import org.sireum.util._

object AbortStatementEx {
  def unapply(o : org.sireum.bakar.xml.AbortStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getAbortedTasksQl()
    )
  }
}