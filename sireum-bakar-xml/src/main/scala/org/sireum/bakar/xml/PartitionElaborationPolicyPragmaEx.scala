package org.sireum.bakar.xml

import org.sireum.util._

object PartitionElaborationPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PartitionElaborationPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}