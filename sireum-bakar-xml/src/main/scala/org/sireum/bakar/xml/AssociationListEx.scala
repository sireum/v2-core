package org.sireum.bakar.xml

import org.sireum.util._

object AssociationListEx {
  def unapply(o : org.sireum.bakar.xml.AssociationList) = {
    Some(
      o.getAssociations()
    )
  }
}