package org.sireum.bakar.xml

import org.sireum.util._

object AsynchronousSelectStatementEx {
  def unapply(o : org.sireum.bakar.xml.AsynchronousSelectStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}