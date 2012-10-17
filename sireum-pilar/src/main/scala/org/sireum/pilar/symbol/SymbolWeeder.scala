/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.symbol

import org.sireum.pilar.symbol._
import org.sireum.pilar.symbol.SymbolTableMessage._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolWeeder

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait RecordHierarchyWeeder extends SymbolWeeder {
  self : SymbolTableProducer =>

  def recordHierarchyWeeder : Unit = {
    if (tables.recordTable.isEmpty)
      return
    val superRecordsMap = mmapEmpty[ResourceUri, MSet[ResourceUri]]
    for (rd <- tables.recordTable.values) {
      rd.extendsClauses.foreach { ec =>
        superRecordsMap.getOrElseUpdate(rd.name.resourceUri,
          msetEmpty[ResourceUri]) += ec.name.resourceUri
      }
    }
    FixedPoint.fix(superRecordsMap)
    superRecordsMap.foreach { p =>
      if (p._2.contains(p._1)) {
        val rdName = tables.recordTable(p._1).name
        reportError(rdName.locationFile,
          rdName.locationLine, rdName.locationColumn,
          CIRCULAR_RECORD.format(rdName.name))
      }
    }
  }
}