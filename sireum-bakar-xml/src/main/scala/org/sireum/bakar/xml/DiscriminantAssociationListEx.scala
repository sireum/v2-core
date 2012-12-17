package org.sireum.bakar.xml

import org.sireum.util._

object DiscriminantAssociationListEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantAssociationList) = {
    Some(
      o.getDiscriminantAssociations()
    )
  }
}