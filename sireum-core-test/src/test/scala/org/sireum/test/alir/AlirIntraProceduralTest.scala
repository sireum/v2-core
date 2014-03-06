/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.alir

import org.sireum.example.pilarv3.alir.AlirExamples
import org.junit.runner.RunWith

import org.scalatest.junit.JUnitRunner

import org.sireum.test.framework.alir.intra.AlirIntraProceduralTestFramework

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class AlirIntraProceduralTest extends AlirIntraProceduralTestFramework {

  AlirExamples.goodModelFiles.
    //filter { s => s.endsWith("rdtest3.plr") }.
    foreach { fileUri =>
      Analyzing title fileUri file fileUri
    }
}
