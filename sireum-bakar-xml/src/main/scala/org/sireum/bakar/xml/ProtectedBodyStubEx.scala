package org.sireum.bakar.xml

import org.sireum.util._

object ProtectedBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedBodyStub) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl()
    )
  }
}