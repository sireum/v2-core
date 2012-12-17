package org.sireum.bakar.xml

import org.sireum.util._

object AssignmentStatementEx {
  def unapply(o : org.sireum.bakar.xml.AssignmentStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getAssignmentVariableNameQ(),
      o.getAssignmentExpressionQ()
    )
  }
}