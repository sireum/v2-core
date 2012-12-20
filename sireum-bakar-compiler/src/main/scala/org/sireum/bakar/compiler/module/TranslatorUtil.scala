package org.sireum.bakar.compiler.module

import collection.JavaConversions._
import org.sireum.bakar.xml._
import org.sireum.pilar.ast._
import org.sireum.util._

object TranslatorUtil {
  val emptyAnnot = ilistEmpty[Annotation]
      
  def getTypeDeclarations(e : ElementList) = {
    e.getElements().filter {
      case x : OrdinaryTypeDeclaration => true
      case x : SubtypeDeclaration      => true
      case _                           => false
    }
  }

  def getConstantDeclarations(e : ElementList) = {
    e.getElements().filter {
      case x : RealNumberDeclaration       => true
      case x : IntegerNumberDeclaration    => true
      case x : DeferredConstantDeclaration => true
      case x : ConstantDeclaration         => true
      case _                               => false
    }
  }

  def getGlobalDeclarations(e : ElementList) = {
    e.getElements.filter {
      case x : VariableDeclaration => true
      case _                       => false
    }
  }

  def getMethodDeclarations(e : ElementList) = {
    e.getElements().filter {
      case o : ProcedureDeclaration        => true      
      case o : ProcedureBodyDeclaration    => true
      case o : FunctionDeclaration         => true      
      case o : FunctionBodyDeclaration     => true
      case o : GenericProcedureDeclaration => true
      case o : NullProcedureDeclaration    => true
      case _                               => false
    }
  }
}