package org.sireum.bakar.xml

import org.sireum.util._

object TaskBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.TaskBodyStub) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl()
    )
  }
}