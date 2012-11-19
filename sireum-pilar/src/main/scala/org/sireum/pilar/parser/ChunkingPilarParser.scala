/*
Copyright (c) 2011-2012 Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.parser

import java.net._
import java.io._
import org.sireum.pilar.ast._
import org.sireum.util._

/**
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object ChunkingPilarParser {

  def apply(source : Either[String, FileResourceUri],
            reporter : PilarParser.ErrorReporter) =
    parseModel(source, reporter)

  def parseModel(source : Either[String, FileResourceUri],
                 reporter : PilarParser.ErrorReporter) = {
    val works = marrayEmpty[Work]
    val workAcc = new WorkAcc {
      def addWork(w : Work) {
        works += w
      }
    }
    val mSource =
      source match {
        case Left(code) =>
          readModelChunks(new StringReader(code), workAcc)
          None
        case Right(fileResourceUri) =>
          val fr = new FileReader(new File(new URI(fileResourceUri)))
          try readModelChunks(fr, workAcc) finally fr.close
          Some(fileResourceUri)
      }

    val ps = works.par.map { w =>
      val errors = marrayEmpty[(Int, Int, String)]
      if (w.content.trim != "") {
        val r = new PilarParser.ErrorReporter {
          def report(source : Option[FileResourceUri], line : Int,
                     column : Int, message : String) {
            errors += ((line, column, message))
          }
        }
        val mOpt = PilarParser.parseString[Model](w.content, r, mSource,
          classOf[Model], w.startLine)
        (w.workNum, mOpt, errors)
      } else {
        (w.workNum, Some(Model(mSource, ilistEmpty,
          ilist(PackageDecl(None, ilistEmpty, ilistEmpty)))), errors)
      }
    }
    val sps = ps.seq.sortWith({ (t1, t2) => t1._1 < t2._1 })
    if (sps.exists(!_._3.isEmpty)) {
      for ((_, _, errors) <- ps)
        for ((line, column, message) <- errors)
          reporter.report(mSource, line, column, message)
      None
    } else {
      val size = sps.size
      val firstModel = sps(0)._2.get
      val packages = marrayEmpty[PackageDecl]

      var currentPackage = firstModel.packages(0)
      var currentPackageElements = marrayEmpty[PackageElement]
      for (pa <- sps) {
        val m = pa._2.get
        for (p <- m.packages) {
          if (p.name.isDefined) {
            packages += PackageDecl(currentPackage.name,
              currentPackage.annotations, currentPackageElements.toList)
            currentPackage = p
            currentPackageElements = marrayEmpty[PackageElement]
          }
          currentPackageElements ++= p.elements
        }
      }
      packages += PackageDecl(currentPackage.name,
        currentPackage.annotations, currentPackageElements.toList)
      Some(Model(mSource, firstModel.annotations, packages.toList))
    }
  }

  def readModelChunks(r : Reader, acc : WorkAcc) = {
    val lnr = new LineNumberReader(r)
    var lineNo = 0

    var workNo = 0

    var chunkLineNo = 0
    var sb = new StringBuilder

    var lineText = lnr.readLine

    val keywords = Set("package", "const", "enum", "typealias", "record",
      "global", "procedure", "vset", "fun", "extension")

    var first = true

    while (lineText != null) {
      val word = getFirstWord(lineText)

      if (keywords.contains(word)) {
        acc.addWork(Work(workNo, chunkLineNo, sb.toString))
        workNo += 1

        sb = new StringBuilder
        chunkLineNo = lineNo
      }

      sb.append(lineText)
      sb.append('\n')
      lineNo += 1

      lineText = lnr.readLine
    }

    acc.addWork(Work(workNo, chunkLineNo, sb.toString))

    workNo + 1
  }

  def getFirstWord(line : String) = {
    val size = line.size
    var i = 0
    while (i < size && line.charAt(i).isWhitespace) {
      i += 1
    }
    var j = i
    while (j < size && !line.charAt(j).isWhitespace) {
      j += 1
    }
    if (i < size && j <= size) line.substring(i, j)
    else ""
  }

  trait WorkAcc {
    def addWork(w : Work)
  }

  case class Work(workNum : Int, startLine : Int, content : String)
}