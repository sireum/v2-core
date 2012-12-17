package org.sireum.bakar.xml

import org.sireum.util._

object ContextClauseListEx {
  def unapply(o : org.sireum.bakar.xml.ContextClauseList) = {
    Some(
      o.getContextClauses()
    )
  }
}