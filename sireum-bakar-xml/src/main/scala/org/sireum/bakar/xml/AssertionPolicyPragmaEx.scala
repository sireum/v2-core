package org.sireum.bakar.xml

import org.sireum.util._

object AssertionPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AssertionPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}