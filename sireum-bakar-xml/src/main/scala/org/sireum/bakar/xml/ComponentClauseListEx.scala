package org.sireum.bakar.xml

import org.sireum.util._

object ComponentClauseListEx {
  def unapply(o : org.sireum.bakar.xml.ComponentClauseList) = {
    Some(
      o.getComponentClauses()
    )
  }
}