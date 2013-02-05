/*
Copyright (c) 2011-2013 Singkhorn Sittirug, Jason Belt, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.test.cli
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.sireum.test.framework.TestFramework
import org.sireum.cli.SireumCli
import java.io.PrintWriter
import org.sireum.option.CliGenOption
import org.sireum.option.CliGenMode
 
/**
 * @author <a href="mailto:sitt@k-state.edu">Singkhorn Sittirug</a>
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
*  */
@RunWith(classOf[JUnitRunner])
class Clitest extends TestFramework {
  
//  test("good test1") {
//    val cr = new SireumCli().parse("kiasan java".split(" "))
//    assert(cr.status)
//  }
//  
//  test("good test2") {
//    val cr = new SireumCli().parse("kiasan java check".split(" "))
//    assert(cr.status)
//  }
//  
//  test("good test3") {
//    val cr = new SireumCli().parse("kiasan java report".split(" "))
//    assert(cr.status)
//  }  
  
  test("good test4") {
    val cr = new SireumCli().parse("tools".split(" "))
    assert(cr.status)
  }
  
  
  test("valid") {
    val cr = new SireumCli().parse("tools cligen".split(" "))
    assert(cr.status)
  }
  
  test("valid2") {
    val cr = new SireumCli().parse("tools pipeline".split(" "))
    assert(cr.status)
  }
  
  test("bad - non existing mode") {
    val cr = new SireumCli().parse("faketool cligen".split(" "))
    assert(cr.tags.exists(t => t.description.get.indexOf("is not a mode of") >= 0))
    assert(!cr.status)
  }
  
  test("invalid classname") {
    val cr = new SireumCli().parse("tools cligen blah".split(" "))
    assert(cr.tags.size == 1 &&
        cr.tags.exists(t =>
      t.description.get.startsWith(CliGenOption.ERROR_CLASS_NAME)
      ))
    assert(!cr.status)
  }
  
  test("bad - Missing required arguments"){
    val cr = new SireumCli().parse("tools cligen -d .".split(" "))
    assert(cr.tags.exists(t => t.description.get.indexOf("Missing required arguments") >= 0))
    assert(!cr.status)   
  }
  
  test("bad - non-existing option") {
    val cr = new SireumCli().parse("tools cligen -o".split(" "))
    assert(cr.tags.exists(t => t.description.get.indexOf("is not an option") >= 0))
    assert(!cr.status)   
  }
  
  test("bad - option requiring argument but not supplied") {
    val cr = new SireumCli().parse("tools cligen -d".split(" "))
    assert(cr.tags.exists(t => t.description.get.indexOf("Missing required arguments") >= 0))
    assert(!cr.status)   
  }
  
//  test("bad - option without argument but supplied" ) {
//    val cr = new SireumCli().parse("kiasan java check --smt-native 10".split(" "))
//    assert(!cr.status)   
//  }
  
  test("bad - option with wrong argument type supplied") {
    val cr = new SireumCli().parse("tools cligen --max-col true".split(" "))
    assert(cr.tags.exists(t => t.description.get.indexOf("Wrong argument type supplied") >= 0))
    assert(!cr.status)     
  }
 
  
  test("bad - Unable to find class & too many args "){
    val cr = new SireumCli().parse("tools cligen arg1 arg2 arg3".split(" "))
    assert(cr.tags.exists(t => t.description.get.indexOf("Unable to find class") >= 0))
    assert(cr.tags.exists(t => t.description.get.indexOf("Too many arguments") >= 0))
    assert(!cr.status) 
  }
  
  test("setting same option twice") {
      val cr = new SireumCli().parse("tools cligen -c SCli --class-name S2Cli".split(" "))
    val opt = cr.options.get.asInstanceOf[CliGenMode]
    assert (!cr.status)  
    assert(cr.tags.exists(t => t.description.get.indexOf("Option already set") >= 0))
  }
  
}