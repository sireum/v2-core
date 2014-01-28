/*
Copyright (c) 2011-2013 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.option

import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
@Main(value = "kiasan", 
  //className = "org.sireum.bakar.tools.BakarKiasan",
  //featureName = "Sireum Bakar Tools:Gnat.sapp",
  className = "org.sireum.bakar.kiasan.driver.KiasanBakarDriver",
  featureName = "Sireum Bakar Kiasan:Gnat.sapp:Z3.sapp",
  desc = "Symbolic Execution of SPARK/Ada Programs")
case class KiasanBakarMode(
  general : KiasanBakarGeneralGroup = KiasanBakarGeneralGroup(),
  analysis : KiasanBakarAnalysisGroup = KiasanBakarAnalysisGroup(),
  report : KiasanBakarReportGroup = KiasanBakarReportGroup(),
  //solver : KiasanBakarSolverGroup = KiasanBakarSolverGroup(),
  
  @Arg(index = 0, value="package-name")
  var packageName : String = "",
  
  @Arg(index = 1, value="method-name")
  var methodName : String = ""
)

@Group("General Options")
case class KiasanBakarGeneralGroup(
  @Option(longKey = "source-paths", desc = "Directories containing the SPARK source files")
  var sourcePaths : ISeq[String] = ivectorEmpty,
  
  @Option(longKey = "source-files", desc = "Spark filenames")
  var sourceFiles : ISeq[String] = ivectorEmpty
)

@Group("Analysis Options")
case class KiasanBakarAnalysisGroup(
  @Option(shortKey = "a", longKey = "array-bound", desc = "Array Element Bound")
  var arrayBound : Int = 3,
  
  @Option(shortKey = "d", longKey = "depth-bound", desc = "Depth Bound")
  var depthBound : Int = 100,
  
  @Option(shortKey = "l", longKey = "loop-bound", desc = "Loop Bound")
  var loopBound : Int = 10,
  
  @Option(shortKey = "i", longKey = "invoke-bound", desc = "Method invoke-chain bound")
  var invokeBound : Int = 10,
  
  @Option(shortKey = "t", longKey = "timeout", desc = "Timeout in minutes")
  var timeout : Int = 10,
  
  @Option(shortKey = "o", longKey = "outdir", desc = "Output directory path")
  var outdir : String = ".",
    
  @Option(longKey = "enable-ldp", desc = "Enable LDP")
  var enableLdp : Boolean = false
)

@Group("Report Options")
case class KiasanBakarReportGroup(
  @Option(longKey = "disable-file-cases", desc="Disable Kiasan report file cases")
  var disableFileCases : Boolean = true
)

@Group("Solver Options")
case class KiasanBakarSolverGroup()

