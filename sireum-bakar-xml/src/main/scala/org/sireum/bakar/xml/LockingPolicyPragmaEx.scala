package org.sireum.bakar.xml

import org.sireum.util._

object LockingPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.LockingPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}