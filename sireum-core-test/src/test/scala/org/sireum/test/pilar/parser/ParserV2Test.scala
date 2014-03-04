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
import org.sireum.test.framework.pilar.parser.ParserV2TestFramework
import org.sireum.example.alir.AlirExamples
import org.sireum.example.pilar.symbol.SymbolExamples
import org.sireum.example.pilar.parser.ParserExamples
import org.sireum.util._
import org.sireum.pilar.ast.PilarAstNode

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class ParserV2Test extends ParserV2TestFramework {

  val xs = new XStreamer with PilarAstNode.XStreamer {
    override val isDiet = false
  }

  val xsDiet = new XStreamer with PilarAstNode.XStreamer {
    override val isDiet = true
  }

  val checkXStreamer : VisitorFunction = {
    case o : org.sireum.pilar.ast.Model =>
      val s = xs.to(o)
      xs.to(xs.from(s)) should be(s)
      val sDiet = xsDiet.to(o)
      xsDiet.to(xsDiet.from(sDiet)) should be(sDiet)
      false
  }

  ParserExamples.goodModelFiles.foreach { fileUri =>
    Parsing model "without error" from_file fileUri ast_satisfies checkXStreamer
  }

  SymbolExamples.goodModelFiles.foreach { fileUri =>
    Parsing model "without error" from_file fileUri ast_satisfies checkXStreamer
  }

  AlirExamples.goodModelFiles.foreach { fileUri =>
    Parsing model "without error" from_file fileUri ast_satisfies checkXStreamer
  }
}