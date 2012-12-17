package org.sireum.bakar.xml

import org.sireum.util._

object CompilationUnitEx {
  def unapply(o : org.sireum.bakar.xml.CompilationUnit) = {
    Some(
      o.getSloc(),
      o.getContextClauseElementsQl(),
      o.getUnitDeclarationQ(),
      o.getPragmasAfterQl(),
      o.getUnitKind(),
      o.getUnitClass(),
      o.getUnitOrigin(),
      o.getUnitFullName(),
      o.getDefName(),
      o.getSourceFile()
    )
  }
}