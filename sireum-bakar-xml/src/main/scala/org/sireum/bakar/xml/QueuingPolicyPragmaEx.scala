package org.sireum.bakar.xml

import org.sireum.util._

object QueuingPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.QueuingPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}