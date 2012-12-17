package org.sireum.bakar.xml

import org.sireum.util._

object PackageInstantiationEx {
  def unapply(o : org.sireum.bakar.xml.PackageInstantiation) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getGenericUnitNameQ(),
      o.getGenericActualPartQl(),
      o.getAspectSpecificationsQl()
    )
  }
}