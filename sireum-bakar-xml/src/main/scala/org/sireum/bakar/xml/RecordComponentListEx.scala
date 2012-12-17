package org.sireum.bakar.xml

import org.sireum.util._

object RecordComponentListEx {
  def unapply(o : org.sireum.bakar.xml.RecordComponentList) = {
    Some(
      o.getRecordComponents()
    )
  }
}