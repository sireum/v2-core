/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.symbol

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait Symbol extends Resource

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolDefinition //
  extends Symbol // 
  with ResourceDefinition //
  with FileLocation //
  with LineColumnLocation

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait SymbolUser extends Symbol with ResourceUser

