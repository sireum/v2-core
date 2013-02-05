/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum

import scala.annotation.target._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
package object option {
  type Mode = org.sireum.option.annotation.Mode @getter @setter
  type Main = org.sireum.option.annotation.Main @getter @setter
  type Group = org.sireum.option.annotation.Group @getter @setter
  type Option = org.sireum.option.annotation.Option @getter @setter
  type Arg = org.sireum.option.annotation.Arg @getter @setter
  type Args = org.sireum.option.annotation.Args @getter @setter  
  type OptionalArg = org.sireum.option.annotation.OptionalArg @getter @setter
  type Check = org.sireum.option.annotation.Check @getter @setter
}