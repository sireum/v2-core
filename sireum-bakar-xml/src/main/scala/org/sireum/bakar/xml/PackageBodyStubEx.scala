package org.sireum.bakar.xml

import org.sireum.util._

object PackageBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.PackageBodyStub) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl()
    )
  }
}