package org.sireum.bakar.xml

import org.sireum.util._

object InMembershipTestEx {
  def unapply(o : org.sireum.bakar.xml.InMembershipTest) = {
    Some(
      o.getSloc(),
      o.getMembershipTestExpressionQ(),
      o.getMembershipTestChoicesQl(),
      o.getType()
    )
  }
}