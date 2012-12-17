package org.sireum.bakar.xml

import org.sireum.util._

object StatementListEx {
  def unapply(o : org.sireum.bakar.xml.StatementList) = {
    Some(
      o.getStatements()
    )
  }
}