/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.framework

import org.scalatest._
import org.scalatest.junit.ShouldMatchersForJUnit

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
trait TestFramework extends FunSuite with ShouldMatchersForJUnit with Assertions {
  private type T = org.scalatest.Tag

  private var isSingle = false
  protected var casePrefix = ""
  private val tagsToInclude = marrayEmpty[String]

  def Single : this.type = {
    isSingle = true
    tagsToInclude += SINGLE_TAG_NAME
    this
  }
  
  def Case(s : String) : this.type = {
    casePrefix = s
    this
  }

  protected override def test(testName : String, testTags : T*)(f : => Unit) {
    val tags = if (isSingle) testTags.+:(SingleTestTag) else testTags
    isSingle = false

    super.test(testName, tags : _*)(f)
  }

  override def run //
  (testName : Option[String], reporter : Reporter, stopper : Stopper,
   filter : Filter, configMap : Map[String, Any],
   distributor : Option[Distributor], tracker : Tracker) = {
    val f = new Filter(filter.tagsToInclude match {
      case Some(s) => Some(s ++ tagsToInclude)
      case _       => if (tagsToInclude.isEmpty) None else Some(tagsToInclude.toSet)
    }, filter.tagsToExclude)
    super.run(testName, reporter, stopper, f, configMap, distributor, tracker)
  }

  private object SingleTestTag extends T(SINGLE_TAG_NAME)

  private val SINGLE_TAG_NAME = "Single"
}