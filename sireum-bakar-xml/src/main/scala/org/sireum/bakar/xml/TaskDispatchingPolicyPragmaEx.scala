package org.sireum.bakar.xml

import org.sireum.util._

object TaskDispatchingPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.TaskDispatchingPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}