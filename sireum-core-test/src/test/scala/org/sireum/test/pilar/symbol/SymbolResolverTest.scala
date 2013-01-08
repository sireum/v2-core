/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.pilar.symbol

import org.junit.runner.RunWith

import org.scalatest.junit.JUnitRunner

import org.sireum.pilar.ast._
import org.sireum.pilar.symbol.H._
import org.sireum.test.framework.pilar.symbol._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@RunWith(classOf[JUnitRunner])
class SymbolResolverTest extends SymbolResolverTestFramework {

  Resolving title "Undeclared @@x used in procedure foo is unresolved" model """
    procedure foo { 
      # return @@x; 
    }
  """ ast_satisfies {
    case ne : NameUser =>
      assert (!ne.hasResourceInfo)
      false
  }

  Resolving title "@@x used in procedure foo is resolved" model """
    global @@x; 
    procedure foo { 
      # return @@x; 
    }
  """ ast_satisfies {
    case ne : NameUser =>
      assert (ne.hasResourceInfo)
      ne.uriScheme should be === SCHEME
      ne.uriType should be === GLOBAL_VAR_TYPE
      ne.uriPaths should be === Seq(DEFAULT_PACKAGE_PATH, "@@x")
      false
  }
}