package org.sireum.bakar.xml

import org.sireum.util._

object NotInMembershipTestEx {
  def unapply(o : org.sireum.bakar.xml.NotInMembershipTest) = {
    Some(
      o.getSloc(),
      o.getMembershipTestExpressionQ(),
      o.getMembershipTestChoicesQl(),
      o.getType()
    )
  }
}