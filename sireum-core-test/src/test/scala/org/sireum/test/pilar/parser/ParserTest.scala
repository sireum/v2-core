/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.pilar.parser

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.junit.ShouldMatchersForJUnit
import org.sireum.test.framework.pilar.parser.ParserTestFramework
import org.sireum.example.alir.AlirExamples
import org.sireum.example.pilar.symbol.SymbolExamples
import org.sireum.example.pilar.parser.ParserExamples

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class ParserTest extends ParserTestFramework {

  ParserExamples.goodModelFiles.foreach { fileUri =>
    Parsing model "without error" from_file fileUri
  }
  
  SymbolExamples.goodModelFiles.foreach { fileUri =>
    Parsing model "without error" from_file fileUri
  }
  
  AlirExamples.goodModelFiles.foreach { fileUri =>
    Parsing model "without error" from_file fileUri
  }
}