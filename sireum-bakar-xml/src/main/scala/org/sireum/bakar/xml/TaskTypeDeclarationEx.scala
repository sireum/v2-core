package org.sireum.bakar.xml

import org.sireum.util._

object TaskTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.TaskTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getAspectSpecificationsQl(),
      o.getDeclarationInterfaceListQl(),
      o.getTypeDeclarationViewQ()
    )
  }
}