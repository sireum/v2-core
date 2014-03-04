/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.framework

import org.sireum.pilar.ast._
import org.sireum.pilar.parser._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object TestUtil {
  def parse[T <: PilarAstNode] //
  (source : Either[String, FileResourceUri], claz : Class[T]) =
    PilarParser.parseWithErrorAsString(source, claz)

  def parse[T <: PilarAstNode : scala.reflect.runtime.universe.TypeTag] //
  (source : Either[String, FileResourceUri]) =
    Parser.parseWithErrorAsString(source)
}