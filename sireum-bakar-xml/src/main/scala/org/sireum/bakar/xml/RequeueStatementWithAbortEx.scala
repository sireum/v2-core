package org.sireum.bakar.xml

import org.sireum.util._

object RequeueStatementWithAbortEx {
  def unapply(o : org.sireum.bakar.xml.RequeueStatementWithAbort) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getRequeueEntryNameQ()
    )
  }
}