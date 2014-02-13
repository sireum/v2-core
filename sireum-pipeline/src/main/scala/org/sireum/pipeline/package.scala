/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum

import scala.annotation.meta._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
package object pipeline {
  type Consume = org.sireum.tools.pipeline.annotation.Consume @getter @setter
  type Input = org.sireum.tools.pipeline.annotation.Input @getter @setter
  type Produce = org.sireum.tools.pipeline.annotation.Produce @getter @setter
  type Output = org.sireum.tools.pipeline.annotation.Output @getter @setter
}
