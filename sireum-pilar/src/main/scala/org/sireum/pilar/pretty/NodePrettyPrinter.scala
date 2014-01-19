/*
Copyright (c) 2011-2013 Jason Belt, Robby, Kansas State University.        
All rights reserved. This program and the accompanying materials      
are made available under the terms of the Eclipse Public License v1.0 
which accompanies this distribution, and is available at              
http://www.eclipse.org/legal/epl-v10.html                             
*/

package org.sireum.pilar.pretty

import org.stringtemplate.v4._

import org.sireum.pilar.ast._
import org.sireum.pilar.state._
import org.sireum.util._
import org.apache.commons.lang3.StringEscapeUtils

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
object NodePrettyPrinter {
  def print(o: PilarAstNode, vprint: Value => String = { _.toString }) =
    new NodePrettyPrinter(vprint).print(o)
}

/**
 * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
 * @author <a href="mailto:robby@k-state.edu">Robby</a>
 */
class NodePrettyPrinter(vprint: Value => String) {
  type BVisitor = Any => Boolean

  /**
   * @author <a href="mailto:belt@k-state.edu">Jason Belt</a>
   * @author <a href="mailto:robby@k-state.edu">Robby</a>
   */
  trait Context {
    val stg: STGroupFile = new STGroupFile(getClass.getResource("pretty.stg"), "UTF-8", '$', '$')
    var result: ST = null
    var parent: PilarAstNode = null

    var variableArity: Boolean = false

    def processName(o: Any) = {
      o match {
        case x: NameDefinition => stg.getInstanceOf("name").add("ID", x.name)
        case x: NameUser => stg.getInstanceOf("name").add("ID", x.name)
      }
    }

    def processAnnotationList(v: => BVisitor, st: ST, annots: ISeq[Annotation], noIndent: Boolean = false) {
      if (annots.isEmpty)
        return
      val stAnnotationList = if (noIndent) stg.getInstanceOf("annotationListNoIndent") else stg.getInstanceOf("annotationList")
      for (a <- annots) {
        v(a)
        stAnnotationList.add("annotation", popResult)
      }
      st.add("annotationList", stAnnotationList)
    }

    def peekResult = {
      //assert (this.result != null)
      this.result
    }

    def pushResult(st: ST) = {
      //assert (this.result == null && st != null)
      this.result = st
    }

    def popResult = {
      //assert (this.result != null)
      val r = result
      this.result = null
      r
    }
  }

  def model(ctx: Context, v: => BVisitor): VisitorFunction = {
    case o: Model =>
      val st = ctx.stg.getInstanceOf("model")

      ctx.processAnnotationList(v, st, o.annotations)

      for (p <- o.packages) {
        v(p)
        st.add("packageDeclaration", ctx.popResult)
      }
      ctx.pushResult(st)
      false
    case o: PackageDecl =>
      val st = ctx.stg.getInstanceOf("packageDeclaration")

      if (o.name.isDefined)
        st.add("name", ctx.processName(o.name.get))

      for (pe <- o.elements) {
        v(pe)
        st.add("packageElement", ctx.popResult)
      }

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)
      false
  }

  def packageElement(ctx: Context, v: => BVisitor): VisitorFunction = {
    case o: ConstDecl =>
      val st = ctx.stg.getInstanceOf("constDeclaration")
      st.add("ID", o.name.name)

      ctx.processAnnotationList(v, st, o.annotations)

      for (ce <- o.elements) {
        val ste = ctx.stg.getInstanceOf("constElement")
        ste.add("ID", ce.name.name)

        v(ce.exp)
        ste.add("exp", ctx.popResult)

        ctx.processAnnotationList(v, ste, ce.annotations)

        st.add("constElement", ste)
      }
      ctx.pushResult(st)
      false
    case o: EmptyBody =>
      ctx.pushResult(ctx.stg.getInstanceOf("body"))
      false
    case o: EnumDecl =>
      val st = ctx.stg.getInstanceOf("enumDeclaration")
      st.add("ID", o.name.name)
      
      ctx.processAnnotationList(v, st, o.annotations)
      
      for(ce <- o.elements) {
        v(ce)
        st.add("enumElement", ctx.popResult)
      }
      ctx.pushResult(st)
      false
    case o: EnumElement =>
      val st = ctx.stg.getInstanceOf("enumElement")
      st.add("ID", o.name.name)
      
      ctx.processAnnotationList(v, st, o.annotations)
      
      ctx.pushResult(st)
      false
    case o: ImplementedBody =>
      val st = ctx.stg.getInstanceOf("body")

      for (lv <- o.locals) {
        v(lv)
        st.add("localVarDeclaration", ctx.popResult)
      }

      for (l <- o.locations) {
        v(l)
        st.add("location", ctx.popResult)
      }

      for (cc <- o.catchClauses) {
        Console.err.println("body catch clauses not handled")
      }

      ctx.pushResult(st)
      false
    case o: GlobalVarDecl =>
      val st = ctx.stg.getInstanceOf("globalVarDeclaration")

      if (o.typeSpec.isDefined) {
        v(o.typeSpec.get)
        st.add("type", ctx.popResult)
      }

      st.add("GLOBALID", o.name.name)

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)

      false
    case o: LocalVarDecl =>
      val st = ctx.stg.getInstanceOf("localVarDeclaration")

      if (o.typeSpec.isDefined) {
        v(o.typeSpec.get)
        st.add("type", ctx.popResult)
      }

      st.add("ID", o.name.name)

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)
      false
    case o: ParamDecl =>
      val st = ctx.stg.getInstanceOf("param")

      if (o.typeSpec.isDefined) {
        v(o.typeSpec.get)
        st.add("type", ctx.popResult)
      }

      st.add("ID", o.name.name)

      ctx.processAnnotationList(v, st, o.annotations)

      if (ctx.variableArity)
        st.add("variable", "...")

      ctx.pushResult(st)
      false
    case o: ProcedureDecl =>
      val st = ctx.stg.getInstanceOf("procedureDeclaration")

      //ctx.processTypeVarTuple(st, o.typeVars)

      if (o.returnType.isDefined) {
        v(o.returnType.get)
        st.add("type", ctx.popResult)
      }

      st.add("ID", o.name.name)

      if (!o.params.isEmpty) {
        val stParams = ctx.stg.getInstanceOf("params")
        val params = o.params
        val size = params.size
        val variableArity = o.varArity
        for (i <- 0 to size - 1) {
          ctx.variableArity = variableArity && ((i + 1) == size);
          v(params(i))
          stParams.add("param", ctx.popResult)
        }
        st.add("params", stParams)
      }

      ctx.processAnnotationList(v, st, o.annotations)

      v(o.body)
      st.add("body", ctx.popResult)

      ctx.pushResult(st)
      false
    case o: RecordDecl =>
      val st = ctx.stg.getInstanceOf("recordDeclaration")
      st.add("ID", ctx.processName(o.name))

      if (!o.typeVars.isEmpty)
        Console.err.println("Not handling RecordDecl typeVars")

      ctx.processAnnotationList(v, st, o.annotations)

      if (!o.extendsClauses.isEmpty)
        Console.err.println("Not handling RecordDecl extendsClauses")

      for (a <- o.attributes) {
        var sta = ctx.stg.getInstanceOf("attribute")

        if (a.typeSpec.isDefined) {
          v(a.typeSpec.get)
          sta.add("type", ctx.popResult)
        }

        sta.add("ID", ctx.processName(a.name))

        ctx.processAnnotationList(v, sta, a.annotations)

        if (a.binding.isDefined)
          Console.err.println("Not handling AttributeDecl binding")

        st.add("attribute", sta)
      }
      ctx.pushResult(st)
      false
    case o: TypeAliasDecl =>
      val st = ctx.stg.getInstanceOf("typealiasDeclaration")

      v(o.typeSpec)
      st.add("type", ctx.popResult)

      st.add("ID", ctx.processName(o.name))

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)
      false
  }

  def typeSpec(ctx: Context, v: => BVisitor): VisitorFunction = {
    case o: NamedTypeSpec =>
      val st = ctx.stg.getInstanceOf("namedType")

      st.add("name", o.name.name)

      if (!o.typeArgs.isEmpty) {
        Console.err.println("NamedTypeSpec typeArgs not handled")
      }

      ctx.pushResult(st)

      false
  }

  def location(ctx: Context, v: => BVisitor): VisitorFunction = {
    case o: ActionLocation =>
      val st = ctx.stg.getInstanceOf("location")

      if (o.name.isDefined) {
        st.add("LOCID", o.name.get.name)
      }

      ctx.processAnnotationList(v, st, o.annotations)

      v(o.action)
      st.add("singleTransformation", ctx.popResult)

      ctx.pushResult(st)
      false
    case o: JumpLocation =>
      val st = ctx.stg.getInstanceOf("location")

      if (o.name.isDefined) {
        st.add("LOCID", o.name.get.name)
      }

      v(o.jump)
      st.add("singleTransformation", ctx.popResult)

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)
      false
    case o: EmptyLocation =>
      val st = ctx.stg.getInstanceOf("location")

      if (o.name.isDefined) {
        st.add("LOCID", o.name.get.name)
      }

      st.add("singleTransformation", "// empty loc")

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)
      false
    case o @ (ComplexLocation |
      Transformation |
      ExpGuard |
      ElseGuard) =>
      Console.err.println("Not handling " + o.getClass.getSimpleName)
      false
  }

  def action(ctx: Context, v: => BVisitor): VisitorFunction = {
    case o: AssignAction =>
      val st = ctx.stg.getInstanceOf("assignment")

      v(o.lhs)
      st.add("lhs", ctx.popResult)

      v(o.rhs)
      st.add("rhs", ctx.popResult)

      st.add("op", o.op)

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)
      false
    case o: AssertAction =>
      val st = ctx.stg.getInstanceOf("assert")

      v(o.cond)
      st.add("exp", ctx.popResult)

      if (o.message.isDefined) {
        v(o.message.get)
        st.add("message", ctx.popResult)
      }

      ctx.processAnnotationList(v, st, o.annotations)
      ctx.pushResult(st)
      false
    case o: AssumeAction =>
      val st = ctx.stg.getInstanceOf("assume")

      v(o.cond)
      st.add("exp", ctx.popResult)

      ctx.processAnnotationList(v, st, o.annotations)
      ctx.pushResult(st)
      false
    case o @ (ThrowAction | StartAction | ExtCallAction) =>
      Console.err.println("Not handling " + o.getClass.getSimpleName)
      false
  }

  def annotations(ctx: Context, v: => BVisitor): VisitorFunction = {
    case o: Annotation =>
      val st = ctx.stg.getInstanceOf("annotation")
      st.add("ID", o.name.name)

      if (!o.params.isEmpty) {
        val params = o.params
        if (params.size == 1 && params.head.isInstanceOf[ExpAnnotationParam] &&
          params.head.name.isDefined) {
          v(params.head)
          val s = ctx.popResult.asInstanceOf[String]
          st.add("annotaitonParams", s)
        } else {
          val stAnnotParams = ctx.stg.getInstanceOf("annotationParams")
          for (ap <- params) {
            v(ap)
            stAnnotParams.add("annotationParam", ctx.popResult)
          }
          st.add("annotationParams", stAnnotParams)
        }
      }
      ctx.pushResult(st)
      false
    case o: AnnotationAnnotationParam =>
      val st = ctx.stg.getInstanceOf("annotationParam")
      if (o.name.isDefined)
        st.add("ID", ctx.processName(o.name.get))

      v(o.annotation)
      st.add("annotationOrExp", ctx.popResult)

      ctx.pushResult(st)
      false
    case o: ExpAnnotationParam =>
      val st = ctx.stg.getInstanceOf("annotationParam")
      if (o.name.isDefined) {
        st.add("ID", ctx.processName(o.name.get))
      }
      v(o.exp)
      st.add("annotationOrExp", ctx.popResult)
      ctx.pushResult(st)
      false
  }

  def exp(ctx: Context, v: => BVisitor): VisitorFunction = {
    case o: AccessExp =>
      val st = ctx.stg.getInstanceOf("accessExp")

      v(o.exp)
      st.add("exp", ctx.popResult)

      st.add("ID", o.attributeName.name)

      val ast = ctx.stg.getInstanceOf("annotatedExp")
      ast.add("exp", st)

      ctx.processAnnotationList(v, ast, o.annotations)

      ctx.pushResult(ast)
      false
    case o: AttributeInit =>
      val st = ctx.stg.getInstanceOf("attrInit")

      st.add("id", o.name.name)
      v(o.exp)
      st.add("exp", ctx.popResult)

      ctx.pushResult(st)
      false
    case o: BinaryExp =>
      val st = ctx.stg.getInstanceOf("binaryExp")

      st.add("binop", o.op)

      v(o.left)
      st.add("exp1", ctx.popResult)

      v(o.right)
      st.add("exp2", ctx.popResult)

      ctx.pushResult(st)
      false
    case o: CallExp =>
      val st = ctx.stg.getInstanceOf("callExp")
      v(o.exp)
      st.add("exp", ctx.popResult)

      v(o.arg)
      st.add("arg", ctx.popResult)

      ctx.pushResult(st)
      false
    case o: CastExp =>
      val st = ctx.stg.getInstanceOf("castExp")
      v(o.typeSpec)
      st.add("type", ctx.popResult)

      v(o.exp)
      st.add("exp", ctx.popResult)

      ctx.pushResult(st)
      false
    case o: FunExp =>
      val st = ctx.stg.getInstanceOf("funExp")

      for (m <- o.matchings) {
        v(m)
        st.add("matching", ctx.popResult)
      }

      ctx.pushResult(st)
      false
    case o: IfExp =>
      val st = ctx.stg.getInstanceOf("ifExp")
      
      for(ite <- o.ifThens) {
        v(ite)
        st.add("ifThenExp", ctx.popResult)
      }
      
      if(o.elseExp != null) {
        v(o.elseExp)
        
        val stElse = ctx.stg.getInstanceOf("ifElseExp")
        stElse.add("exp", ctx.popResult)
        st.add("ifElseExp", stElse)
      }
      
      ctx.pushResult(st)
      false
    case o: IfThenExp =>
      val st = ctx.stg.getInstanceOf("ifThenExp")
      
      v(o.cond)
      st.add("exp", ctx.popResult)
      
      ctx.processAnnotationList(v, st, o.annotations)
      
      v(o.exp)
      st.add("exp2", ctx.popResult)
      
      ctx.pushResult(st)
      false
    case o: IndexingExp =>
      val st = ctx.stg.getInstanceOf("indexingExp")
      v(o.exp)
      st.add("exp", ctx.popResult)
      for (e <- o.indices) {
        v(e)
        st.add("expi", ctx.popResult)
      }
      ctx.pushResult(st)
      false

    case o: LiteralExp =>
      val st = ctx.stg.getInstanceOf("literal")

      o.typ match {
        case LiteralType.STRING =>
          st.add("literal", "\"" + StringEscapeUtils.escapeJava(o.text) + "\"")
        case LiteralType.RAW =>
          st.add("literal", "\"\"\"" + o.text + "\"\"\"")
        case _ =>
          st.add("literal", o.text)
      }

      ctx.pushResult(st)
      false
    case o: Matching =>
      val st = ctx.stg.getInstanceOf("matching")

      for (p <- o.params) {
        v(p)
        st.add("param", ctx.popResult)
      }
      v(o.exp)
      st.add("exp", ctx.popResult)
      ctx.pushResult(st)
      false
    case o: NameExp =>
      ctx.pushResult(ctx.processName(o.name))
      false
    case o: NewFunctionExp =>
      val st = ctx.stg.getInstanceOf("newFunction")
      
      for(e <- o.elements) {
        val stMapping = ctx.stg.getInstanceOf("mapping")
        
        v(e._1)
        stMapping.add("e1", ctx.popResult)
        
        v(e._2)
        stMapping.add("e2", ctx.popResult)
        
        st.add("mapping", stMapping)
      }
      ctx.pushResult(st)
      false
    case o: NewRecordExp =>
      val st = ctx.stg.getInstanceOf("newRecord")
      v(o.recordType)
      st.add("type", ctx.popResult)

      for (ai <- o.attributeInits) {
        v(ai)
        st.add("attrInit", ctx.popResult)
      }

      ctx.pushResult(st)
      false
    case o: SwitchCaseExp =>
      val st = ctx.stg.getInstanceOf("switchCaseExp")
      v(o.exp)
      st.add("exp2", ctx.popResult)

      ctx.processAnnotationList(v, st, o.annotations)

      v(o.cond)
      st.add("exp", ctx.popResult)

      ctx.pushResult(st)
      false
    case o: SwitchExp =>
      val st = ctx.stg.getInstanceOf("switchExp")
      v(o.exp)
      st.add("exp", ctx.popResult)

      for (s <- o.cases) {
        v(s)
        st.add("switchCaseExp", ctx.popResult)
      }

      if (o.defaultCase != null) {
        v(o.defaultCase)
        val std = ctx.stg.getInstanceOf("switchDefaultExp")
        std.add("exp", ctx.popResult)
        st.add("switchDefaultExp", std)
      }
      ctx.pushResult(st)
      false
    case o: TupleExp =>
      val st = ctx.stg.getInstanceOf("tuple")
      for (e <- o.exps) {
        v(e)
        st.add("annotatedExp", ctx.popResult)
      }
      ctx.pushResult(st)
      false
    case o: TypeExp =>
      val st = ctx.stg.getInstanceOf("typeExp")
      v(o.typeSpec)
      st.add("type", ctx.popResult)
      ctx.pushResult(st)
      false
    case o: UnaryExp =>
      val st = ctx.stg.getInstanceOf("unaryExp")
      st.add("unop", o.op)

      v(o.exp)
      st.add("exp", "(" + ctx.popResult.render + ")")

      ctx.pushResult(st)

      false
    case o: ValueExp =>
      val st = ctx.stg.getInstanceOf("literal")
      st.add("literal", vprint(o.value))
      ctx.pushResult(st)
      false
  }

  def jump(ctx: Context, v: => BVisitor): VisitorFunction = {
    case o: CallJump =>
      val st = ctx.stg.getInstanceOf("callTransformation")

      ctx.processAnnotationList(v, st, o.annotations)

      for (lhs <- o.lhss) {
        v(lhs)
        st.add("lhs", ctx.popResult)
      }

      v(o.callExp)
      st.add("exp", ctx.popResult)

      if (o.jump.isDefined) {
        v(o.jump)
        st.add("jump", ctx.popResult)
      }

      ctx.pushResult(st)
      false
    case o: GotoJump =>
      val st = ctx.stg.getInstanceOf("goto")

      st.add("ID", ctx.processName(o.target))

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)
      false
    case o: IfJump =>
      val st = ctx.stg.getInstanceOf("ifJump")

      for (itj <- o.ifThens) {
        v(itj)
        st.add("ifThenJump", ctx.popResult)
      }

      if (o.ifElse.isDefined) {
        v(o.ifElse.get)
        st.add("ifElseJump", ctx.popResult)
      }

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)
      false
    case o: IfThenJump =>
      val st = ctx.stg.getInstanceOf("ifThenJump")

      v(o.cond)
      st.add("exp", ctx.popResult)

      ctx.processAnnotationList(v, st, o.annotations)

      st.add("ID", ctx.processName(o.target))

      ctx.pushResult(st)
      false
    case o: ReturnJump =>
      val st = ctx.stg.getInstanceOf("return")

      if (o.exp.isDefined) {
        v(o.exp.get)
        st.add("exp", ctx.popResult)
      }

      ctx.processAnnotationList(v, st, o.annotations)

      ctx.pushResult(st)
      false
  }

  def e(ctx: Context, v: => BVisitor): VisitorFunction = {
    case x =>
      Console.err.println(getClass.getSimpleName + ": Not handling: " + x)
      false
  }

  def v: BVisitor = b
  val ctx = new Context {}

  val b = Visitor.build(
    Visitor.first(
      ivector(model(ctx, v),
        packageElement(ctx, v),
        typeSpec(ctx, v),
        location(ctx, v),
        jump(ctx, v),
        action(ctx, v),
        exp(ctx, v),
        annotations(ctx, v),
        e(ctx, v))))

  def print(o: PilarAstNode): String = {
    b(o)
    ctx.popResult.render
  }
}