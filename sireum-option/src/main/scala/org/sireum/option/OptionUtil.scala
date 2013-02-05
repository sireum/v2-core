/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.option

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object OptionUtil {
  val InfoMarker = MarkerType("option.info", None, "Information Marker",
    MarkerTagSeverity.Info, MarkerTagPriority.Normal, ivector(MarkerTagKind.Text))

  val ErrorMarker = MarkerType("option.error", None, "Error Marker",
    MarkerTagSeverity.Error, MarkerTagPriority.Normal, ivector(MarkerTagKind.Problem))

  val WarningMarker = MarkerType("option.warning", None, "Warning Marker",
    MarkerTagSeverity.Warning, MarkerTagPriority.Normal, ivector(MarkerTagKind.Problem))

  def genTag(mt : MarkerType, desc : String) : InfoTag = {
    return InfoTag(mt, Some(desc))
  }
}
