
import org.sireum.util._

object SourceLocation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SourceLocation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SourceLocation]
      Some(
        o.getLine(),
        o.getCol(),
        o.getEndline(),
        o.getEndcol()
      )
    } 
    else 
      None
  }
}
object ContextClauseList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ContextClauseList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ContextClauseList]
      Some(
        o.getContextClauses()
      )
    } 
    else 
      None
  }
}
object NotAnElement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NotAnElement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NotAnElement]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object DefiningNameList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningNameList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningNameList]
      Some(
        o.getDefiningNames()
      )
    } 
    else 
      None
  }
}
object Abstract {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Abstract]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Abstract]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object DerivedTypeDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DerivedTypeDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DerivedTypeDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object Limited {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Limited]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Limited]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object DerivedTypeDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DerivedTypeDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DerivedTypeDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object DefiningIdentifier {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningIdentifier]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningIdentifier]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningCharacterLiteral {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningCharacterLiteral]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningCharacterLiteral]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningEnumerationLiteral {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningEnumerationLiteral]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningEnumerationLiteral]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningAndOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningAndOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningAndOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningOrOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningOrOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningOrOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningXorOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningXorOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningXorOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningEqualOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningEqualOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningEqualOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningNotEqualOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningNotEqualOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningNotEqualOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningLessThanOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningLessThanOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningLessThanOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningLessThanOrEqualOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningLessThanOrEqualOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningLessThanOrEqualOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningGreaterThanOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningGreaterThanOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningGreaterThanOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningGreaterThanOrEqualOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningGreaterThanOrEqualOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningGreaterThanOrEqualOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningPlusOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningPlusOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningPlusOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningMinusOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningMinusOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningMinusOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningConcatenateOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningConcatenateOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningConcatenateOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningUnaryPlusOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningUnaryPlusOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningUnaryPlusOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningUnaryMinusOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningUnaryMinusOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningUnaryMinusOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningMultiplyOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningMultiplyOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningMultiplyOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningDivideOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningDivideOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningDivideOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningModOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningModOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningModOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningRemOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningRemOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningRemOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningExponentiateOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningExponentiateOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningExponentiateOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningAbsOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningAbsOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningAbsOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiningNotOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningNotOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningNotOperator]
      Some(
        o.getSloc(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object Identifier {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Identifier]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Identifier]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DiscreteRangeAttributeReference {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscreteRangeAttributeReference]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscreteRangeAttributeReference]
      Some(
        o.getSloc(),
        o.getRangeAttributeQ()
      )
    } 
    else 
      None
  }
}
object DiscreteSimpleExpressionRange {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscreteSimpleExpressionRange]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscreteSimpleExpressionRange]
      Some(
        o.getSloc(),
        o.getLowerBoundQ(),
        o.getUpperBoundQ()
      )
    } 
    else 
      None
  }
}
object OthersChoice {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OthersChoice]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OthersChoice]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object BoxExpression {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.BoxExpression]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.BoxExpression]
      Some(
        o.getSloc(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object IntegerLiteral {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IntegerLiteral]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IntegerLiteral]
      Some(
        o.getSloc(),
        o.getLitVal(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object RealLiteral {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RealLiteral]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RealLiteral]
      Some(
        o.getSloc(),
        o.getLitVal(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object StringLiteral {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.StringLiteral]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.StringLiteral]
      Some(
        o.getSloc(),
        o.getLitVal(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AndOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AndOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AndOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object OrOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OrOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OrOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object XorOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.XorOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.XorOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object EqualOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EqualOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EqualOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object NotEqualOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NotEqualOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NotEqualOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object LessThanOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LessThanOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LessThanOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object LessThanOrEqualOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LessThanOrEqualOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LessThanOrEqualOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object GreaterThanOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GreaterThanOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GreaterThanOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object GreaterThanOrEqualOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GreaterThanOrEqualOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GreaterThanOrEqualOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object PlusOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PlusOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PlusOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MinusOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MinusOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MinusOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ConcatenateOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ConcatenateOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ConcatenateOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object UnaryPlusOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UnaryPlusOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UnaryPlusOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object UnaryMinusOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UnaryMinusOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UnaryMinusOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MultiplyOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MultiplyOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MultiplyOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DivideOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DivideOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DivideOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ModOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ModOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ModOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object RemOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RemOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RemOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ExponentiateOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExponentiateOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExponentiateOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AbsOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AbsOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AbsOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object NotOperator {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NotOperator]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NotOperator]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object CharacterLiteral {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CharacterLiteral]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CharacterLiteral]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object EnumerationLiteral {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EnumerationLiteral]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EnumerationLiteral]
      Some(
        o.getSloc(),
        o.getRefName(),
        o.getRef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ExplicitDereference {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExplicitDereference]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExplicitDereference]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AssociationList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AssociationList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AssociationList]
      Some(
        o.getAssociations()
      )
    } 
    else 
      None
  }
}
object IsPrefixCall {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IsPrefixCall]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IsPrefixCall]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FunctionCall_IsPrefixCallQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionCall.IsPrefixCallQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionCall.IsPrefixCallQ]
      Some(
        o.getIsPrefixCall()
      )
    } 
    else 
      None
  }
}
object IsPrefixNotation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IsPrefixNotation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IsPrefixNotation]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FunctionCall_IsPrefixNotationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionCall.IsPrefixNotationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionCall.IsPrefixNotationQ]
      Some(
        o.getIsPrefixNotation()
      )
    } 
    else 
      None
  }
}
object FunctionCall {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionCall]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionCall]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getFunctionCallParametersQl(),
        o.getIsPrefixCallQ(),
        o.getIsPrefixNotationQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ExpressionList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExpressionList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExpressionList]
      Some(
        o.getExpressions()
      )
    } 
    else 
      None
  }
}
object IndexedComponent {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IndexedComponent]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IndexedComponent]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getIndexExpressionsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object RangeAttributeReference {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RangeAttributeReference]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RangeAttributeReference]
      Some(
        o.getSloc(),
        o.getRangeAttributeQ()
      )
    } 
    else 
      None
  }
}
object SimpleExpressionRange {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SimpleExpressionRange]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SimpleExpressionRange]
      Some(
        o.getSloc(),
        o.getLowerBoundQ(),
        o.getUpperBoundQ()
      )
    } 
    else 
      None
  }
}
object AllCallsRemotePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AllCallsRemotePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AllCallsRemotePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object AsynchronousPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AsynchronousPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AsynchronousPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object AtomicPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AtomicPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AtomicPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object AtomicComponentsPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AtomicComponentsPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AtomicComponentsPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object AttachHandlerPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AttachHandlerPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AttachHandlerPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ControlledPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ControlledPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ControlledPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ConventionPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ConventionPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ConventionPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object DiscardNamesPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscardNamesPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscardNamesPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ElaboratePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElaboratePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElaboratePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ElaborateAllPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElaborateAllPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElaborateAllPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ElaborateBodyPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElaborateBodyPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElaborateBodyPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ExportPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExportPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExportPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ImportPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ImportPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ImportPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object InlinePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.InlinePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.InlinePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object InspectionPointPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.InspectionPointPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.InspectionPointPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object InterruptHandlerPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.InterruptHandlerPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.InterruptHandlerPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object InterruptPriorityPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.InterruptPriorityPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.InterruptPriorityPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object LinkerOptionsPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LinkerOptionsPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LinkerOptionsPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ListPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ListPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ListPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object LockingPolicyPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LockingPolicyPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LockingPolicyPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object NormalizeScalarsPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NormalizeScalarsPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NormalizeScalarsPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object OptimizePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OptimizePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OptimizePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object PackPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PackPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PackPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object PagePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PagePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PagePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object PreelaboratePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PreelaboratePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PreelaboratePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object PriorityPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PriorityPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PriorityPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object PurePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PurePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PurePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object QueuingPolicyPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.QueuingPolicyPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.QueuingPolicyPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object RemoteCallInterfacePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RemoteCallInterfacePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RemoteCallInterfacePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object RemoteTypesPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RemoteTypesPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RemoteTypesPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object RestrictionsPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RestrictionsPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RestrictionsPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ReviewablePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ReviewablePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ReviewablePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object SharedPassivePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SharedPassivePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SharedPassivePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object StorageSizePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.StorageSizePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.StorageSizePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object SuppressPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SuppressPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SuppressPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object TaskDispatchingPolicyPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaskDispatchingPolicyPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaskDispatchingPolicyPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object VolatilePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.VolatilePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.VolatilePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object VolatileComponentsPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.VolatileComponentsPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.VolatileComponentsPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object AssertPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AssertPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AssertPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object AssertionPolicyPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AssertionPolicyPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AssertionPolicyPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object DetectBlockingPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DetectBlockingPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DetectBlockingPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object NoReturnPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NoReturnPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NoReturnPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object PartitionElaborationPolicyPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PartitionElaborationPolicyPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PartitionElaborationPolicyPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object PreelaborableInitializationPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PreelaborableInitializationPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PreelaborableInitializationPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object PrioritySpecificDispatchingPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PrioritySpecificDispatchingPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PrioritySpecificDispatchingPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ProfilePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProfilePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProfilePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object RelativeDeadlinePragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RelativeDeadlinePragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RelativeDeadlinePragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object UncheckedUnionPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UncheckedUnionPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UncheckedUnionPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object UnsuppressPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UnsuppressPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UnsuppressPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object DefaultStoragePoolPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefaultStoragePoolPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefaultStoragePoolPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object DispatchingDomainPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DispatchingDomainPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DispatchingDomainPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object CpuPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CpuPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CpuPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object IndependentPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IndependentPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IndependentPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object IndependentComponentsPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IndependentComponentsPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IndependentComponentsPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object ImplementationDefinedPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ImplementationDefinedPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ImplementationDefinedPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object UnknownPragma {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UnknownPragma]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UnknownPragma]
      Some(
        o.getSloc(),
        o.getPragmaArgumentAssociationsQl(),
        o.getPragmaName()
      )
    } 
    else 
      None
  }
}
object RangeConstraintClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RangeConstraintClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RangeConstraintClass]
      Some(
        o.getRangeConstraint()
      )
    } 
    else 
      None
  }
}
object DigitsConstraint {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DigitsConstraint]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DigitsConstraint]
      Some(
        o.getSloc(),
        o.getDigitsExpressionQ(),
        o.getRealRangeConstraintQ()
      )
    } 
    else 
      None
  }
}
object DeltaConstraint {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DeltaConstraint]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DeltaConstraint]
      Some(
        o.getSloc(),
        o.getDeltaExpressionQ(),
        o.getRealRangeConstraintQ()
      )
    } 
    else 
      None
  }
}
object DiscreteRangeList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscreteRangeList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscreteRangeList]
      Some(
        o.getDiscreteRanges()
      )
    } 
    else 
      None
  }
}
object IndexConstraint {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IndexConstraint]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IndexConstraint]
      Some(
        o.getSloc(),
        o.getDiscreteRangesQl()
      )
    } 
    else 
      None
  }
}
object DiscriminantAssociationList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscriminantAssociationList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscriminantAssociationList]
      Some(
        o.getDiscriminantAssociations()
      )
    } 
    else 
      None
  }
}
object DiscriminantConstraint {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscriminantConstraint]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscriminantConstraint]
      Some(
        o.getSloc(),
        o.getDiscriminantAssociationsQl()
      )
    } 
    else 
      None
  }
}
object ConstraintClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ConstraintClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ConstraintClass]
      Some(
        o.getConstraint()
      )
    } 
    else 
      None
  }
}
object DiscreteSubtypeIndication {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscreteSubtypeIndication]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscreteSubtypeIndication]
      Some(
        o.getSloc(),
        o.getSubtypeMarkQ(),
        o.getSubtypeConstraintQ()
      )
    } 
    else 
      None
  }
}
object DiscreteRangeClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscreteRangeClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscreteRangeClass]
      Some(
        o.getDiscreteRange()
      )
    } 
    else 
      None
  }
}
object Slice {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Slice]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Slice]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getSliceRangeQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AccessAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AddressAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AddressAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AddressAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AdjacentAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AdjacentAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AdjacentAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AftAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AftAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AftAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AlignmentAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AlignmentAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AlignmentAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object BaseAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.BaseAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.BaseAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object BitOrderAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.BitOrderAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.BitOrderAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object BodyVersionAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.BodyVersionAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.BodyVersionAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object CallableAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CallableAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CallableAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object CallerAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CallerAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CallerAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object CeilingAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CeilingAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CeilingAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ClassAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ClassAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ClassAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ComponentSizeAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ComponentSizeAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ComponentSizeAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ComposeAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ComposeAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ComposeAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ConstrainedAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ConstrainedAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ConstrainedAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object CopySignAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CopySignAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CopySignAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object CountAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CountAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CountAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DefiniteAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiniteAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiniteAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DeltaAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DeltaAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DeltaAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DenormAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DenormAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DenormAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object DigitsAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DigitsAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DigitsAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ExponentAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExponentAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExponentAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ExternalTagAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExternalTagAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExternalTagAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object FirstAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FirstAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FirstAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getAttributeDesignatorExpressionsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object FirstBitAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FirstBitAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FirstBitAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object FloorAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FloorAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FloorAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ForeAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ForeAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ForeAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object FractionAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FractionAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FractionAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object IdentityAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IdentityAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IdentityAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ImageAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ImageAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ImageAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object InputAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.InputAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.InputAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object LastAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LastAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LastAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getAttributeDesignatorExpressionsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object LastBitAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LastBitAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LastBitAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object LeadingPartAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LeadingPartAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LeadingPartAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object LengthAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LengthAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LengthAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getAttributeDesignatorExpressionsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MachineAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MachineAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MachineAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MachineEmaxAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MachineEmaxAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MachineEmaxAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MachineEminAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MachineEminAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MachineEminAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MachineMantissaAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MachineMantissaAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MachineMantissaAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MachineOverflowsAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MachineOverflowsAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MachineOverflowsAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MachineRadixAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MachineRadixAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MachineRadixAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MachineRoundsAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MachineRoundsAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MachineRoundsAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MaxAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MaxAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MaxAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MaxSizeInStorageElementsAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MaxSizeInStorageElementsAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MaxSizeInStorageElementsAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MinAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MinAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MinAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ModelAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ModelAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ModelAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ModelEminAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ModelEminAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ModelEminAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ModelEpsilonAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ModelEpsilonAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ModelEpsilonAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ModelMantissaAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ModelMantissaAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ModelMantissaAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ModelSmallAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ModelSmallAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ModelSmallAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ModulusAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ModulusAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ModulusAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object OutputAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OutputAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OutputAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object PartitionIdAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PartitionIdAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PartitionIdAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object PosAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PosAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PosAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object PositionAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PositionAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PositionAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object PredAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PredAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PredAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object RangeAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RangeAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RangeAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getAttributeDesignatorExpressionsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ReadAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ReadAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ReadAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object RemainderAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RemainderAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RemainderAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object RoundAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RoundAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RoundAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object RoundingAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RoundingAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RoundingAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object SafeFirstAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SafeFirstAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SafeFirstAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object SafeLastAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SafeLastAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SafeLastAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ScaleAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ScaleAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ScaleAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ScalingAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ScalingAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ScalingAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object SignedZerosAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SignedZerosAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SignedZerosAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object SizeAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SizeAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SizeAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object SmallAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SmallAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SmallAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object StoragePoolAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.StoragePoolAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.StoragePoolAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object StorageSizeAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.StorageSizeAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.StorageSizeAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object SuccAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SuccAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SuccAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object TagAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TagAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TagAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object TerminatedAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TerminatedAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TerminatedAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object TruncationAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TruncationAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TruncationAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object UnbiasedRoundingAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UnbiasedRoundingAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UnbiasedRoundingAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object UncheckedAccessAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UncheckedAccessAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UncheckedAccessAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ValAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ValAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ValAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ValidAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ValidAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ValidAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ValueAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ValueAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ValueAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object VersionAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.VersionAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.VersionAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object WideImageAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WideImageAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WideImageAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object WideValueAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WideValueAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WideValueAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object WideWidthAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WideWidthAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WideWidthAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object WidthAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WidthAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WidthAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object WriteAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WriteAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WriteAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MachineRoundingAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MachineRoundingAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MachineRoundingAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ModAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ModAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ModAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object PriorityAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PriorityAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PriorityAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object StreamSizeAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.StreamSizeAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.StreamSizeAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object WideWideImageAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WideWideImageAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WideWideImageAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object WideWideValueAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WideWideValueAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WideWideValueAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object WideWideWidthAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WideWideWidthAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WideWideWidthAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object MaxAlignmentForAllocationAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.MaxAlignmentForAllocationAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.MaxAlignmentForAllocationAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object OverlapsStorageAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OverlapsStorageAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OverlapsStorageAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ImplementationDefinedAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ImplementationDefinedAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ImplementationDefinedAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getAttributeDesignatorExpressionsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object UnknownAttribute {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UnknownAttribute]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UnknownAttribute]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getAttributeDesignatorIdentifierQ(),
        o.getAttributeDesignatorExpressionsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object RecordAggregate {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RecordAggregate]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RecordAggregate]
      Some(
        o.getSloc(),
        o.getRecordComponentAssociationsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ExtensionAggregate {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExtensionAggregate]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExtensionAggregate]
      Some(
        o.getSloc(),
        o.getExtensionAggregateExpressionQ(),
        o.getRecordComponentAssociationsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object PositionalArrayAggregate {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PositionalArrayAggregate]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PositionalArrayAggregate]
      Some(
        o.getSloc(),
        o.getArrayComponentAssociationsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object NamedArrayAggregate {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NamedArrayAggregate]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NamedArrayAggregate]
      Some(
        o.getSloc(),
        o.getArrayComponentAssociationsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AndThenShortCircuit {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AndThenShortCircuit]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AndThenShortCircuit]
      Some(
        o.getSloc(),
        o.getShortCircuitOperationLeftExpressionQ(),
        o.getShortCircuitOperationRightExpressionQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object OrElseShortCircuit {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OrElseShortCircuit]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OrElseShortCircuit]
      Some(
        o.getSloc(),
        o.getShortCircuitOperationLeftExpressionQ(),
        o.getShortCircuitOperationRightExpressionQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ElementList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElementList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElementList]
      Some(
        o.getElements()
      )
    } 
    else 
      None
  }
}
object InMembershipTest {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.InMembershipTest]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.InMembershipTest]
      Some(
        o.getSloc(),
        o.getMembershipTestExpressionQ(),
        o.getMembershipTestChoicesQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object NotInMembershipTest {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NotInMembershipTest]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NotInMembershipTest]
      Some(
        o.getSloc(),
        o.getMembershipTestExpressionQ(),
        o.getMembershipTestChoicesQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object NullLiteral {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NullLiteral]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NullLiteral]
      Some(
        o.getSloc(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ParenthesizedExpression {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ParenthesizedExpression]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ParenthesizedExpression]
      Some(
        o.getSloc(),
        o.getExpressionParenthesizedQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object TypeConversion {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TypeConversion]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TypeConversion]
      Some(
        o.getSloc(),
        o.getConvertedOrQualifiedSubtypeMarkQ(),
        o.getConvertedOrQualifiedExpressionQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object QualifiedExpression {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.QualifiedExpression]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.QualifiedExpression]
      Some(
        o.getSloc(),
        o.getConvertedOrQualifiedSubtypeMarkQ(),
        o.getConvertedOrQualifiedExpressionQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AllocationFromSubtype {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AllocationFromSubtype]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AllocationFromSubtype]
      Some(
        o.getSloc(),
        o.getSubpoolNameQ(),
        o.getAllocatorSubtypeIndicationQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object AllocationFromQualifiedExpression {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AllocationFromQualifiedExpression]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AllocationFromQualifiedExpression]
      Some(
        o.getSloc(),
        o.getSubpoolNameQ(),
        o.getAllocatorQualifiedExpressionQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object CaseExpression {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CaseExpression]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CaseExpression]
      Some(
        o.getSloc(),
        o.getCaseExpressionQ(),
        o.getExpressionPathsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object IfExpression {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IfExpression]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IfExpression]
      Some(
        o.getSloc(),
        o.getExpressionPathsQl(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ForAllQuantifiedExpression {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ForAllQuantifiedExpression]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ForAllQuantifiedExpression]
      Some(
        o.getSloc(),
        o.getIteratorSpecificationQ(),
        o.getPredicateQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ForSomeQuantifiedExpression {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ForSomeQuantifiedExpression]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ForSomeQuantifiedExpression]
      Some(
        o.getSloc(),
        o.getIteratorSpecificationQ(),
        o.getPredicateQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object ExpressionClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExpressionClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExpressionClass]
      Some(
        o.getExpression()
      )
    } 
    else 
      None
  }
}
object SelectedComponent {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SelectedComponent]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SelectedComponent]
      Some(
        o.getSloc(),
        o.getPrefixQ(),
        o.getSelectorQ(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object NameClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NameClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NameClass]
      Some(
        o.getName()
      )
    } 
    else 
      None
  }
}
object DefiningNameClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningNameClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningNameClass]
      Some(
        o.getDefiningName()
      )
    } 
    else 
      None
  }
}
object DefiningExpandedName {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefiningExpandedName]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefiningExpandedName]
      Some(
        o.getSloc(),
        o.getDefiningPrefixQ(),
        o.getDefiningSelectorQ(),
        o.getDefName(),
        o.getDef(),
        o.getType()
      )
    } 
    else 
      None
  }
}
object TaskTypeDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaskTypeDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaskTypeDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getDiscriminantPartQ(),
        o.getAspectSpecificationsQl(),
        o.getDeclarationInterfaceListQl(),
        o.getTypeDeclarationViewQ()
      )
    } 
    else 
      None
  }
}
object ProtectedTypeDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProtectedTypeDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProtectedTypeDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getDiscriminantPartQ(),
        o.getAspectSpecificationsQl(),
        o.getDeclarationInterfaceListQl(),
        o.getTypeDeclarationViewQ()
      )
    } 
    else 
      None
  }
}
object IncompleteTypeDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IncompleteTypeDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IncompleteTypeDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getDiscriminantPartQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object TaggedIncompleteTypeDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaggedIncompleteTypeDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaggedIncompleteTypeDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getDiscriminantPartQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object PrivateTypeDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PrivateTypeDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PrivateTypeDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getDiscriminantPartQ(),
        o.getTypeDeclarationViewQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object PrivateExtensionDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PrivateExtensionDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PrivateExtensionDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getDiscriminantPartQ(),
        o.getTypeDeclarationViewQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object SubtypeDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SubtypeDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SubtypeDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getTypeDeclarationViewQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object Aliased {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Aliased]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Aliased]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object VariableDeclaration_HasAliasedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.VariableDeclaration.HasAliasedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.VariableDeclaration.HasAliasedQ]
      Some(
        o.getHasAliased()
      )
    } 
    else 
      None
  }
}
object VariableDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.VariableDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.VariableDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasAliasedQ(),
        o.getObjectDeclarationViewQ(),
        o.getInitializationExpressionQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object ConstantDeclaration_HasAliasedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ConstantDeclaration.HasAliasedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ConstantDeclaration.HasAliasedQ]
      Some(
        o.getHasAliased()
      )
    } 
    else 
      None
  }
}
object ConstantDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ConstantDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ConstantDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasAliasedQ(),
        o.getObjectDeclarationViewQ(),
        o.getInitializationExpressionQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object DeferredConstantDeclaration_HasAliasedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DeferredConstantDeclaration.HasAliasedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DeferredConstantDeclaration.HasAliasedQ]
      Some(
        o.getHasAliased()
      )
    } 
    else 
      None
  }
}
object DeferredConstantDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DeferredConstantDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DeferredConstantDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasAliasedQ(),
        o.getObjectDeclarationViewQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object SingleTaskDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SingleTaskDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SingleTaskDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl(),
        o.getDeclarationInterfaceListQl(),
        o.getObjectDeclarationViewQ()
      )
    } 
    else 
      None
  }
}
object SingleProtectedDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SingleProtectedDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SingleProtectedDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl(),
        o.getDeclarationInterfaceListQl(),
        o.getObjectDeclarationViewQ()
      )
    } 
    else 
      None
  }
}
object IntegerNumberDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IntegerNumberDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IntegerNumberDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getInitializationExpressionQ()
      )
    } 
    else 
      None
  }
}
object RealNumberDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RealNumberDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RealNumberDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getInitializationExpressionQ()
      )
    } 
    else 
      None
  }
}
object EnumerationLiteralSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EnumerationLiteralSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EnumerationLiteralSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl()
      )
    } 
    else 
      None
  }
}
object NullExclusion {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NullExclusion]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NullExclusion]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object DiscriminantSpecification_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscriminantSpecification.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscriminantSpecification.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object DiscriminantSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscriminantSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscriminantSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasNullExclusionQ(),
        o.getObjectDeclarationViewQ(),
        o.getInitializationExpressionQ()
      )
    } 
    else 
      None
  }
}
object ComponentDeclaration_HasAliasedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ComponentDeclaration.HasAliasedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ComponentDeclaration.HasAliasedQ]
      Some(
        o.getHasAliased()
      )
    } 
    else 
      None
  }
}
object ComponentDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ComponentDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ComponentDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasAliasedQ(),
        o.getObjectDeclarationViewQ(),
        o.getInitializationExpressionQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object Reverse {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Reverse]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Reverse]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object LoopParameterSpecification_HasReverseQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LoopParameterSpecification.HasReverseQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LoopParameterSpecification.HasReverseQ]
      Some(
        o.getHasReverse()
      )
    } 
    else 
      None
  }
}
object DiscreteSubtypeIndicationAsSubtypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscreteSubtypeIndicationAsSubtypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscreteSubtypeIndicationAsSubtypeDefinition]
      Some(
        o.getSloc(),
        o.getSubtypeMarkQ(),
        o.getSubtypeConstraintQ()
      )
    } 
    else 
      None
  }
}
object DiscreteRangeAttributeReferenceAsSubtypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscreteRangeAttributeReferenceAsSubtypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscreteRangeAttributeReferenceAsSubtypeDefinition]
      Some(
        o.getSloc(),
        o.getRangeAttributeQ()
      )
    } 
    else 
      None
  }
}
object DiscreteSimpleExpressionRangeAsSubtypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscreteSimpleExpressionRangeAsSubtypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscreteSimpleExpressionRangeAsSubtypeDefinition]
      Some(
        o.getSloc(),
        o.getLowerBoundQ(),
        o.getUpperBoundQ()
      )
    } 
    else 
      None
  }
}
object DiscreteSubtypeDefinitionClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscreteSubtypeDefinitionClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscreteSubtypeDefinitionClass]
      Some(
        o.getDiscreteSubtypeDefinition()
      )
    } 
    else 
      None
  }
}
object LoopParameterSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LoopParameterSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LoopParameterSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasReverseQ(),
        o.getSpecificationSubtypeDefinitionQ()
      )
    } 
    else 
      None
  }
}
object GeneralizedIteratorSpecification_HasReverseQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GeneralizedIteratorSpecification.HasReverseQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GeneralizedIteratorSpecification.HasReverseQ]
      Some(
        o.getHasReverse()
      )
    } 
    else 
      None
  }
}
object GeneralizedIteratorSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GeneralizedIteratorSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GeneralizedIteratorSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasReverseQ(),
        o.getIterationSchemeNameQ()
      )
    } 
    else 
      None
  }
}
object ElementIteratorSpecification_HasReverseQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElementIteratorSpecification.HasReverseQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElementIteratorSpecification.HasReverseQ]
      Some(
        o.getHasReverse()
      )
    } 
    else 
      None
  }
}
object ElementIteratorSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElementIteratorSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElementIteratorSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getSubtypeIndicationQ(),
        o.getHasReverseQ(),
        o.getIterationSchemeNameQ()
      )
    } 
    else 
      None
  }
}
object Overriding {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Overriding]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Overriding]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object ProcedureDeclaration_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureDeclaration.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureDeclaration.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object NotOverriding {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NotOverriding]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NotOverriding]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object ProcedureDeclaration_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureDeclaration.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureDeclaration.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object ParameterSpecificationList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ParameterSpecificationList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ParameterSpecificationList]
      Some(
        o.getParameterSpecifications()
      )
    } 
    else 
      None
  }
}
object ProcedureDeclaration_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureDeclaration.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureDeclaration.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object ProcedureDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureDeclaration]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getHasAbstractQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object FunctionDeclaration_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionDeclaration.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionDeclaration.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object FunctionDeclaration_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionDeclaration.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionDeclaration.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object NotNullReturn {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NotNullReturn]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NotNullReturn]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FunctionDeclaration_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionDeclaration.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionDeclaration.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object FunctionDeclaration_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionDeclaration.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionDeclaration.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object FunctionDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionDeclaration]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getResultProfileQ(),
        o.getHasAbstractQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object ParameterSpecification_HasAliasedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ParameterSpecification.HasAliasedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ParameterSpecification.HasAliasedQ]
      Some(
        o.getHasAliased()
      )
    } 
    else 
      None
  }
}
object ParameterSpecification_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ParameterSpecification.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ParameterSpecification.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object ParameterSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ParameterSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ParameterSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasAliasedQ(),
        o.getHasNullExclusionQ(),
        o.getObjectDeclarationViewQ(),
        o.getInitializationExpressionQ(),
        o.getMode()
      )
    } 
    else 
      None
  }
}
object ProcedureBodyDeclaration_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureBodyDeclaration.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureBodyDeclaration.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object ProcedureBodyDeclaration_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureBodyDeclaration.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureBodyDeclaration.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object StatementList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.StatementList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.StatementList]
      Some(
        o.getStatements()
      )
    } 
    else 
      None
  }
}
object ExceptionHandlerList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExceptionHandlerList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExceptionHandlerList]
      Some(
        o.getExceptionHandlers()
      )
    } 
    else 
      None
  }
}
object ProcedureBodyDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureBodyDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureBodyDeclaration]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getAspectSpecificationsQl(),
        o.getBodyDeclarativeItemsQl(),
        o.getBodyStatementsQl(),
        o.getBodyExceptionHandlersQl()
      )
    } 
    else 
      None
  }
}
object FunctionBodyDeclaration_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionBodyDeclaration.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionBodyDeclaration.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object FunctionBodyDeclaration_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionBodyDeclaration.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionBodyDeclaration.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object FunctionBodyDeclaration_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionBodyDeclaration.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionBodyDeclaration.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object FunctionBodyDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionBodyDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionBodyDeclaration]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getResultProfileQ(),
        o.getAspectSpecificationsQl(),
        o.getBodyDeclarativeItemsQl(),
        o.getBodyStatementsQl(),
        o.getBodyExceptionHandlersQl()
      )
    } 
    else 
      None
  }
}
object ReturnVariableSpecification_HasAliasedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ReturnVariableSpecification.HasAliasedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ReturnVariableSpecification.HasAliasedQ]
      Some(
        o.getHasAliased()
      )
    } 
    else 
      None
  }
}
object ReturnVariableSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ReturnVariableSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ReturnVariableSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasAliasedQ(),
        o.getObjectDeclarationViewQ(),
        o.getInitializationExpressionQ()
      )
    } 
    else 
      None
  }
}
object ReturnConstantSpecification_HasAliasedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ReturnConstantSpecification.HasAliasedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ReturnConstantSpecification.HasAliasedQ]
      Some(
        o.getHasAliased()
      )
    } 
    else 
      None
  }
}
object ReturnConstantSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ReturnConstantSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ReturnConstantSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasAliasedQ(),
        o.getObjectDeclarationViewQ(),
        o.getInitializationExpressionQ()
      )
    } 
    else 
      None
  }
}
object NullProcedureDeclaration_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NullProcedureDeclaration.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NullProcedureDeclaration.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object NullProcedureDeclaration_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NullProcedureDeclaration.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NullProcedureDeclaration.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object NullProcedureDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NullProcedureDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NullProcedureDeclaration]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object ExpressionFunctionDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExpressionFunctionDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExpressionFunctionDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getResultProfileQ(),
        o.getResultExpressionQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object DeclarativeItemList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DeclarativeItemList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DeclarativeItemList]
      Some(
        o.getDeclarativeItems()
      )
    } 
    else 
      None
  }
}
object PackageDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PackageDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PackageDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl(),
        o.getVisiblePartDeclarativeItemsQl(),
        o.getPrivatePartDeclarativeItemsQl()
      )
    } 
    else 
      None
  }
}
object PackageBodyDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PackageBodyDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PackageBodyDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl(),
        o.getBodyDeclarativeItemsQl(),
        o.getBodyStatementsQl(),
        o.getBodyExceptionHandlersQl()
      )
    } 
    else 
      None
  }
}
object ObjectRenamingDeclaration_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ObjectRenamingDeclaration.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ObjectRenamingDeclaration.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object ObjectRenamingDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ObjectRenamingDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ObjectRenamingDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasNullExclusionQ(),
        o.getObjectDeclarationViewQ(),
        o.getRenamedEntityQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object ExceptionRenamingDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExceptionRenamingDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExceptionRenamingDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getRenamedEntityQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object PackageRenamingDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PackageRenamingDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PackageRenamingDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getRenamedEntityQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object ProcedureRenamingDeclaration_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureRenamingDeclaration.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureRenamingDeclaration.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object ProcedureRenamingDeclaration_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureRenamingDeclaration.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureRenamingDeclaration.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object ProcedureRenamingDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureRenamingDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureRenamingDeclaration]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getRenamedEntityQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object FunctionRenamingDeclaration_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionRenamingDeclaration.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionRenamingDeclaration.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object FunctionRenamingDeclaration_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionRenamingDeclaration.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionRenamingDeclaration.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object FunctionRenamingDeclaration_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionRenamingDeclaration.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionRenamingDeclaration.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object FunctionRenamingDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionRenamingDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionRenamingDeclaration]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getResultProfileQ(),
        o.getRenamedEntityQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object GenericPackageRenamingDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GenericPackageRenamingDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GenericPackageRenamingDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getRenamedEntityQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object GenericProcedureRenamingDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GenericProcedureRenamingDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GenericProcedureRenamingDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getRenamedEntityQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object GenericFunctionRenamingDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GenericFunctionRenamingDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GenericFunctionRenamingDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getRenamedEntityQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object TaskBodyDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaskBodyDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaskBodyDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl(),
        o.getBodyDeclarativeItemsQl(),
        o.getBodyStatementsQl(),
        o.getBodyExceptionHandlersQl()
      )
    } 
    else 
      None
  }
}
object DeclarationList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DeclarationList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DeclarationList]
      Some(
        o.getDeclarations()
      )
    } 
    else 
      None
  }
}
object ProtectedBodyDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProtectedBodyDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProtectedBodyDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl(),
        o.getProtectedOperationItemsQl()
      )
    } 
    else 
      None
  }
}
object EntryDeclaration_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EntryDeclaration.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EntryDeclaration.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object EntryDeclaration_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EntryDeclaration.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EntryDeclaration.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object EntryDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EntryDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EntryDeclaration]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getEntryFamilyDefinitionQ(),
        o.getParameterProfileQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object EntryBodyDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EntryBodyDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EntryBodyDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getEntryIndexSpecificationQ(),
        o.getParameterProfileQl(),
        o.getEntryBarrierQ(),
        o.getBodyDeclarativeItemsQl(),
        o.getBodyStatementsQl(),
        o.getBodyExceptionHandlersQl()
      )
    } 
    else 
      None
  }
}
object EntryIndexSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EntryIndexSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EntryIndexSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getSpecificationSubtypeDefinitionQ()
      )
    } 
    else 
      None
  }
}
object ProcedureBodyStub_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureBodyStub.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureBodyStub.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object ProcedureBodyStub_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureBodyStub.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureBodyStub.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object ProcedureBodyStub {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureBodyStub]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureBodyStub]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object FunctionBodyStub_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionBodyStub.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionBodyStub.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object FunctionBodyStub_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionBodyStub.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionBodyStub.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object FunctionBodyStub_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionBodyStub.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionBodyStub.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object FunctionBodyStub {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionBodyStub]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionBodyStub]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getResultProfileQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object PackageBodyStub {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PackageBodyStub]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PackageBodyStub]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object TaskBodyStub {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaskBodyStub]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaskBodyStub]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object ProtectedBodyStub {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProtectedBodyStub]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProtectedBodyStub]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object ExceptionDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExceptionDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExceptionDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object ChoiceParameterSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ChoiceParameterSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ChoiceParameterSpecification]
      Some(
        o.getSloc(),
        o.getNamesQl()
      )
    } 
    else 
      None
  }
}
object GenericProcedureDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GenericProcedureDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GenericProcedureDeclaration]
      Some(
        o.getSloc(),
        o.getGenericFormalPartQl(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object GenericFunctionDeclaration_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GenericFunctionDeclaration.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GenericFunctionDeclaration.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object GenericFunctionDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GenericFunctionDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GenericFunctionDeclaration]
      Some(
        o.getSloc(),
        o.getGenericFormalPartQl(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getResultProfileQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object GenericPackageDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GenericPackageDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GenericPackageDeclaration]
      Some(
        o.getSloc(),
        o.getGenericFormalPartQl(),
        o.getNamesQl(),
        o.getAspectSpecificationsQl(),
        o.getVisiblePartDeclarativeItemsQl(),
        o.getPrivatePartDeclarativeItemsQl()
      )
    } 
    else 
      None
  }
}
object PackageInstantiation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PackageInstantiation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PackageInstantiation]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getGenericUnitNameQ(),
        o.getGenericActualPartQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object ProcedureInstantiation_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureInstantiation.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureInstantiation.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object ProcedureInstantiation_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureInstantiation.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureInstantiation.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object ProcedureInstantiation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureInstantiation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureInstantiation]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getGenericUnitNameQ(),
        o.getGenericActualPartQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object FunctionInstantiation_IsOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionInstantiation.IsOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionInstantiation.IsOverridingDeclarationQ]
      Some(
        o.getIsOverriding()
      )
    } 
    else 
      None
  }
}
object FunctionInstantiation_IsNotOverridingDeclarationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionInstantiation.IsNotOverridingDeclarationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionInstantiation.IsNotOverridingDeclarationQ]
      Some(
        o.getIsNotOverriding()
      )
    } 
    else 
      None
  }
}
object FunctionInstantiation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FunctionInstantiation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FunctionInstantiation]
      Some(
        o.getSloc(),
        o.getIsOverridingDeclarationQ(),
        o.getIsNotOverridingDeclarationQ(),
        o.getNamesQl(),
        o.getGenericUnitNameQ(),
        o.getGenericActualPartQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object FormalObjectDeclaration_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalObjectDeclaration.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalObjectDeclaration.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object FormalObjectDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalObjectDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalObjectDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getHasNullExclusionQ(),
        o.getObjectDeclarationViewQ(),
        o.getInitializationExpressionQ(),
        o.getAspectSpecificationsQl(),
        o.getMode()
      )
    } 
    else 
      None
  }
}
object FormalTypeDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalTypeDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalTypeDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getDiscriminantPartQ(),
        o.getTypeDeclarationViewQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object Tagged {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Tagged]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Tagged]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FormalIncompleteTypeDeclaration_HasTaggedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalIncompleteTypeDeclaration.HasTaggedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalIncompleteTypeDeclaration.HasTaggedQ]
      Some(
        o.getHasTagged()
      )
    } 
    else 
      None
  }
}
object FormalIncompleteTypeDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalIncompleteTypeDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalIncompleteTypeDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getDiscriminantPartQ(),
        o.getHasTaggedQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object FormalProcedureDeclaration_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalProcedureDeclaration.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalProcedureDeclaration.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object FormalProcedureDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalProcedureDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalProcedureDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getFormalSubprogramDefaultQ(),
        o.getHasAbstractQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object FormalFunctionDeclaration_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalFunctionDeclaration.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalFunctionDeclaration.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object FormalFunctionDeclaration_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalFunctionDeclaration.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalFunctionDeclaration.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object FormalFunctionDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalFunctionDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalFunctionDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getResultProfileQ(),
        o.getFormalSubprogramDefaultQ(),
        o.getHasAbstractQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object FormalPackageDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalPackageDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalPackageDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getGenericUnitNameQ(),
        o.getGenericActualPartQl(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object FormalPackageDeclarationWithBox {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalPackageDeclarationWithBox]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalPackageDeclarationWithBox]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getGenericUnitNameQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object DerivedRecordExtensionDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DerivedRecordExtensionDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DerivedRecordExtensionDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object DerivedRecordExtensionDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DerivedRecordExtensionDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DerivedRecordExtensionDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object DerivedRecordExtensionDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DerivedRecordExtensionDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DerivedRecordExtensionDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ(),
        o.getParentSubtypeIndicationQ(),
        o.getDefinitionInterfaceListQl(),
        o.getRecordDefinitionQ()
      )
    } 
    else 
      None
  }
}
object EnumerationTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EnumerationTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EnumerationTypeDefinition]
      Some(
        o.getSloc(),
        o.getEnumerationLiteralDeclarationsQl()
      )
    } 
    else 
      None
  }
}
object SignedIntegerTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SignedIntegerTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SignedIntegerTypeDefinition]
      Some(
        o.getSloc(),
        o.getIntegerConstraintQ()
      )
    } 
    else 
      None
  }
}
object ModularTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ModularTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ModularTypeDefinition]
      Some(
        o.getSloc(),
        o.getModStaticExpressionQ()
      )
    } 
    else 
      None
  }
}
object RootIntegerDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RootIntegerDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RootIntegerDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object RootRealDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RootRealDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RootRealDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object UniversalIntegerDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UniversalIntegerDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UniversalIntegerDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object UniversalRealDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UniversalRealDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UniversalRealDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object UniversalFixedDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UniversalFixedDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UniversalFixedDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FloatingPointDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FloatingPointDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FloatingPointDefinition]
      Some(
        o.getSloc(),
        o.getDigitsExpressionQ(),
        o.getRealRangeConstraintQ()
      )
    } 
    else 
      None
  }
}
object OrdinaryFixedPointDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OrdinaryFixedPointDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OrdinaryFixedPointDefinition]
      Some(
        o.getSloc(),
        o.getDeltaExpressionQ(),
        o.getRealRangeConstraintQ()
      )
    } 
    else 
      None
  }
}
object DecimalFixedPointDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DecimalFixedPointDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DecimalFixedPointDefinition]
      Some(
        o.getSloc(),
        o.getDeltaExpressionQ(),
        o.getDigitsExpressionQ(),
        o.getRealRangeConstraintQ()
      )
    } 
    else 
      None
  }
}
object UnconstrainedArrayDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UnconstrainedArrayDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UnconstrainedArrayDefinition]
      Some(
        o.getSloc(),
        o.getIndexSubtypeDefinitionsQl(),
        o.getArrayComponentDefinitionQ()
      )
    } 
    else 
      None
  }
}
object DefinitionList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefinitionList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefinitionList]
      Some(
        o.getDefinitions()
      )
    } 
    else 
      None
  }
}
object ConstrainedArrayDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ConstrainedArrayDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ConstrainedArrayDefinition]
      Some(
        o.getSloc(),
        o.getDiscreteSubtypeDefinitionsQl(),
        o.getArrayComponentDefinitionQ()
      )
    } 
    else 
      None
  }
}
object RecordTypeDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RecordTypeDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RecordTypeDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object RecordTypeDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RecordTypeDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RecordTypeDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object RecordTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RecordTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RecordTypeDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ(),
        o.getRecordDefinitionQ()
      )
    } 
    else 
      None
  }
}
object TaggedRecordTypeDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaggedRecordTypeDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaggedRecordTypeDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object TaggedRecordTypeDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaggedRecordTypeDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaggedRecordTypeDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object TaggedRecordTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaggedRecordTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaggedRecordTypeDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ(),
        o.getRecordDefinitionQ()
      )
    } 
    else 
      None
  }
}
object OrdinaryInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OrdinaryInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OrdinaryInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object LimitedInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LimitedInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LimitedInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object TaskInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaskInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaskInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object ProtectedInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProtectedInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProtectedInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object SynchronizedInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SynchronizedInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SynchronizedInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object PoolSpecificAccessToVariable_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PoolSpecificAccessToVariable.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PoolSpecificAccessToVariable.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object PoolSpecificAccessToVariable {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PoolSpecificAccessToVariable]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PoolSpecificAccessToVariable]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToObjectDefinitionQ()
      )
    } 
    else 
      None
  }
}
object AccessToVariable_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToVariable.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToVariable.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AccessToVariable {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToVariable]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToVariable]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToObjectDefinitionQ()
      )
    } 
    else 
      None
  }
}
object AccessToConstant_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToConstant.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToConstant.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AccessToConstant {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToConstant]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToConstant]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToObjectDefinitionQ()
      )
    } 
    else 
      None
  }
}
object AccessToProcedure_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToProcedure.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToProcedure.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AccessToProcedure {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToProcedure]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToProcedure]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl()
      )
    } 
    else 
      None
  }
}
object AccessToProtectedProcedure_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToProtectedProcedure.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToProtectedProcedure.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AccessToProtectedProcedure {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToProtectedProcedure]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToProtectedProcedure]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl()
      )
    } 
    else 
      None
  }
}
object AccessToFunction_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToFunction.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToFunction.HasNullExclusionQ]
      Some(
        o.getNullExclusion(),
        o.getNotAnElement()
      )
    } 
    else 
      None
  }
}
object AccessToFunction_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToFunction.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToFunction.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object AccessToFunction {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToFunction]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToFunction]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getAccessToFunctionResultProfileQ()
      )
    } 
    else 
      None
  }
}
object AccessToProtectedFunction_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToProtectedFunction.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToProtectedFunction.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AccessToProtectedFunction_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToProtectedFunction.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToProtectedFunction.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object AccessToProtectedFunction {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AccessToProtectedFunction]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AccessToProtectedFunction]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getAccessToFunctionResultProfileQ()
      )
    } 
    else 
      None
  }
}
object SubtypeIndication_HasAliasedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SubtypeIndication.HasAliasedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SubtypeIndication.HasAliasedQ]
      Some(
        o.getHasAliased()
      )
    } 
    else 
      None
  }
}
object SubtypeIndication_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SubtypeIndication.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SubtypeIndication.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object SubtypeIndication {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SubtypeIndication]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SubtypeIndication]
      Some(
        o.getSloc(),
        o.getHasAliasedQ(),
        o.getHasNullExclusionQ(),
        o.getSubtypeMarkQ(),
        o.getSubtypeConstraintQ()
      )
    } 
    else 
      None
  }
}
object ComponentDefinition_HasAliasedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ComponentDefinition.HasAliasedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ComponentDefinition.HasAliasedQ]
      Some(
        o.getHasAliased()
      )
    } 
    else 
      None
  }
}
object ComponentDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ComponentDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ComponentDefinition]
      Some(
        o.getSloc(),
        o.getHasAliasedQ(),
        o.getComponentDefinitionViewQ()
      )
    } 
    else 
      None
  }
}
object UnknownDiscriminantPart {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UnknownDiscriminantPart]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UnknownDiscriminantPart]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object DiscriminantSpecificationList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscriminantSpecificationList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscriminantSpecificationList]
      Some(
        o.getDiscriminantSpecifications()
      )
    } 
    else 
      None
  }
}
object KnownDiscriminantPart {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.KnownDiscriminantPart]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.KnownDiscriminantPart]
      Some(
        o.getSloc(),
        o.getDiscriminantsQl()
      )
    } 
    else 
      None
  }
}
object RecordDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RecordDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RecordDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object RecordComponentList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RecordComponentList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RecordComponentList]
      Some(
        o.getRecordComponents()
      )
    } 
    else 
      None
  }
}
object RecordDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RecordDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RecordDefinition]
      Some(
        o.getSloc(),
        o.getHasLimitedQ(),
        o.getRecordComponentsQl()
      )
    } 
    else 
      None
  }
}
object NullRecordDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NullRecordDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NullRecordDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object NullComponent {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NullComponent]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NullComponent]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object VariantList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.VariantList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.VariantList]
      Some(
        o.getVariants()
      )
    } 
    else 
      None
  }
}
object VariantPart {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.VariantPart]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.VariantPart]
      Some(
        o.getSloc(),
        o.getDiscriminantDirectNameQ(),
        o.getVariantsQl()
      )
    } 
    else 
      None
  }
}
object Variant {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Variant]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Variant]
      Some(
        o.getSloc(),
        o.getVariantChoicesQl(),
        o.getRecordComponentsQl()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToVariable_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToVariable.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToVariable.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToVariable {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToVariable]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToVariable]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAnonymousAccessToObjectSubtypeMarkQ()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToConstant_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToConstant.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToConstant.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToConstant {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToConstant]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToConstant]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAnonymousAccessToObjectSubtypeMarkQ()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToProcedure_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProcedure.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProcedure.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToProcedure {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProcedure]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProcedure]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToProtectedProcedure_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedProcedure.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedProcedure.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToProtectedProcedure {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedProcedure]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedProcedure]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToFunction_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToFunction.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToFunction.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToFunction_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToFunction.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToFunction.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToFunction {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToFunction]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToFunction]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getAccessToFunctionResultProfileQ()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToProtectedFunction_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedFunction.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedFunction.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToProtectedFunction_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedFunction.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedFunction.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object AnonymousAccessToProtectedFunction {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedFunction]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AnonymousAccessToProtectedFunction]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getAccessToFunctionResultProfileQ()
      )
    } 
    else 
      None
  }
}
object PrivateTypeDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PrivateTypeDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PrivateTypeDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object PrivateTypeDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PrivateTypeDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PrivateTypeDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object PrivateTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PrivateTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PrivateTypeDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ()
      )
    } 
    else 
      None
  }
}
object TaggedPrivateTypeDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaggedPrivateTypeDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaggedPrivateTypeDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object TaggedPrivateTypeDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaggedPrivateTypeDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaggedPrivateTypeDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object TaggedPrivateTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaggedPrivateTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaggedPrivateTypeDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ()
      )
    } 
    else 
      None
  }
}
object PrivateExtensionDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PrivateExtensionDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PrivateExtensionDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object PrivateExtensionDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PrivateExtensionDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PrivateExtensionDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object PrivateExtensionDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PrivateExtensionDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PrivateExtensionDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ(),
        o.getAncestorSubtypeIndicationQ(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object TaskDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TaskDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TaskDefinition]
      Some(
        o.getSloc(),
        o.getVisiblePartItemsQl(),
        o.getPrivatePartItemsQl()
      )
    } 
    else 
      None
  }
}
object ProtectedDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProtectedDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProtectedDefinition]
      Some(
        o.getSloc(),
        o.getVisiblePartItemsQl(),
        o.getPrivatePartItemsQl()
      )
    } 
    else 
      None
  }
}
object FormalPrivateTypeDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalPrivateTypeDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalPrivateTypeDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object FormalPrivateTypeDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalPrivateTypeDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalPrivateTypeDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object FormalPrivateTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalPrivateTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalPrivateTypeDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ()
      )
    } 
    else 
      None
  }
}
object FormalTaggedPrivateTypeDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object FormalTaggedPrivateTypeDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object FormalTaggedPrivateTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ()
      )
    } 
    else 
      None
  }
}
object FormalDerivedTypeDefinition_HasAbstractQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasAbstractQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasAbstractQ]
      Some(
        o.getHasAbstract()
      )
    } 
    else 
      None
  }
}
object FormalDerivedTypeDefinition_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object Private {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.Private]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.Private]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FormalDerivedTypeDefinition_HasPrivateQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasPrivateQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasPrivateQ]
      Some(
        o.getHasPrivate()
      )
    } 
    else 
      None
  }
}
object FormalDerivedTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalDerivedTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalDerivedTypeDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ(),
        o.getSubtypeMarkQ(),
        o.getDefinitionInterfaceListQl(),
        o.getHasPrivateQ()
      )
    } 
    else 
      None
  }
}
object FormalDiscreteTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalDiscreteTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalDiscreteTypeDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FormalSignedIntegerTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalSignedIntegerTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalSignedIntegerTypeDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FormalModularTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalModularTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalModularTypeDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FormalFloatingPointDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalFloatingPointDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalFloatingPointDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FormalOrdinaryFixedPointDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalOrdinaryFixedPointDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalOrdinaryFixedPointDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FormalDecimalFixedPointDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalDecimalFixedPointDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalDecimalFixedPointDefinition]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object FormalOrdinaryInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalOrdinaryInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalOrdinaryInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object FormalLimitedInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalLimitedInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalLimitedInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object FormalTaskInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalTaskInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalTaskInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object FormalProtectedInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalProtectedInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalProtectedInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object FormalSynchronizedInterface {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalSynchronizedInterface]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalSynchronizedInterface]
      Some(
        o.getSloc(),
        o.getDefinitionInterfaceListQl()
      )
    } 
    else 
      None
  }
}
object FormalUnconstrainedArrayDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalUnconstrainedArrayDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalUnconstrainedArrayDefinition]
      Some(
        o.getSloc(),
        o.getIndexSubtypeDefinitionsQl(),
        o.getArrayComponentDefinitionQ()
      )
    } 
    else 
      None
  }
}
object FormalConstrainedArrayDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalConstrainedArrayDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalConstrainedArrayDefinition]
      Some(
        o.getSloc(),
        o.getDiscreteSubtypeDefinitionsQl(),
        o.getArrayComponentDefinitionQ()
      )
    } 
    else 
      None
  }
}
object FormalPoolSpecificAccessToVariable_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalPoolSpecificAccessToVariable.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalPoolSpecificAccessToVariable.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object FormalPoolSpecificAccessToVariable {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalPoolSpecificAccessToVariable]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalPoolSpecificAccessToVariable]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToObjectDefinitionQ()
      )
    } 
    else 
      None
  }
}
object FormalAccessToVariable_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToVariable.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToVariable.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object FormalAccessToVariable {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToVariable]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToVariable]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToObjectDefinitionQ()
      )
    } 
    else 
      None
  }
}
object FormalAccessToConstant_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToConstant.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToConstant.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object FormalAccessToConstant {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToConstant]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToConstant]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToObjectDefinitionQ()
      )
    } 
    else 
      None
  }
}
object FormalAccessToProcedure_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToProcedure.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToProcedure.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object FormalAccessToProcedure {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToProcedure]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToProcedure]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl()
      )
    } 
    else 
      None
  }
}
object FormalAccessToProtectedProcedure_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedProcedure.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedProcedure.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object FormalAccessToProtectedProcedure {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedProcedure]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedProcedure]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl()
      )
    } 
    else 
      None
  }
}
object FormalAccessToFunction_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToFunction.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToFunction.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object FormalAccessToFunction_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToFunction.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToFunction.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object FormalAccessToFunction {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToFunction]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToFunction]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getAccessToFunctionResultProfileQ()
      )
    } 
    else 
      None
  }
}
object FormalAccessToProtectedFunction_HasNullExclusionQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedFunction.HasNullExclusionQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedFunction.HasNullExclusionQ]
      Some(
        o.getHasNullExclusion()
      )
    } 
    else 
      None
  }
}
object FormalAccessToProtectedFunction_IsNotNullReturnQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedFunction.IsNotNullReturnQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedFunction.IsNotNullReturnQ]
      Some(
        o.getIsNotNullReturn()
      )
    } 
    else 
      None
  }
}
object FormalAccessToProtectedFunction {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedFunction]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.FormalAccessToProtectedFunction]
      Some(
        o.getSloc(),
        o.getHasNullExclusionQ(),
        o.getAccessToSubprogramParameterProfileQl(),
        o.getIsNotNullReturnQ(),
        o.getAccessToFunctionResultProfileQ()
      )
    } 
    else 
      None
  }
}
object AspectSpecification {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AspectSpecification]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AspectSpecification]
      Some(
        o.getSloc(),
        o.getAspectMarkQ(),
        o.getAspectDefinitionQ()
      )
    } 
    else 
      None
  }
}
object PragmaArgumentAssociation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PragmaArgumentAssociation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PragmaArgumentAssociation]
      Some(
        o.getSloc(),
        o.getFormalParameterQ(),
        o.getActualParameterQ()
      )
    } 
    else 
      None
  }
}
object DiscriminantAssociation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DiscriminantAssociation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DiscriminantAssociation]
      Some(
        o.getSloc(),
        o.getDiscriminantSelectorNamesQl(),
        o.getDiscriminantExpressionQ()
      )
    } 
    else 
      None
  }
}
object RecordComponentAssociation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RecordComponentAssociation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RecordComponentAssociation]
      Some(
        o.getSloc(),
        o.getRecordComponentChoicesQl(),
        o.getComponentExpressionQ()
      )
    } 
    else 
      None
  }
}
object ArrayComponentAssociation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ArrayComponentAssociation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ArrayComponentAssociation]
      Some(
        o.getSloc(),
        o.getArrayComponentChoicesQl(),
        o.getComponentExpressionQ()
      )
    } 
    else 
      None
  }
}
object ParameterAssociation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ParameterAssociation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ParameterAssociation]
      Some(
        o.getSloc(),
        o.getFormalParameterQ(),
        o.getActualParameterQ()
      )
    } 
    else 
      None
  }
}
object GenericAssociation {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GenericAssociation]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GenericAssociation]
      Some(
        o.getSloc(),
        o.getFormalParameterQ(),
        o.getActualParameterQ()
      )
    } 
    else 
      None
  }
}
object NullStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NullStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NullStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl()
      )
    } 
    else 
      None
  }
}
object AssignmentStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AssignmentStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AssignmentStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getAssignmentVariableNameQ(),
        o.getAssignmentExpressionQ()
      )
    } 
    else 
      None
  }
}
object PathList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.PathList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.PathList]
      Some(
        o.getPaths()
      )
    } 
    else 
      None
  }
}
object IfStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IfStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IfStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getStatementPathsQl()
      )
    } 
    else 
      None
  }
}
object CaseStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CaseStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CaseStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getCaseExpressionQ(),
        o.getStatementPathsQl()
      )
    } 
    else 
      None
  }
}
object LoopStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.LoopStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.LoopStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getStatementIdentifierQ(),
        o.getLoopStatementsQl()
      )
    } 
    else 
      None
  }
}
object WhileLoopStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WhileLoopStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WhileLoopStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getStatementIdentifierQ(),
        o.getWhileConditionQ(),
        o.getLoopStatementsQl()
      )
    } 
    else 
      None
  }
}
object ForLoopStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ForLoopStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ForLoopStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getStatementIdentifierQ(),
        o.getForLoopParameterSpecificationQ(),
        o.getLoopStatementsQl()
      )
    } 
    else 
      None
  }
}
object BlockStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.BlockStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.BlockStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getStatementIdentifierQ(),
        o.getBlockDeclarativeItemsQl(),
        o.getBlockStatementsQl(),
        o.getBlockExceptionHandlersQl()
      )
    } 
    else 
      None
  }
}
object ExitStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExitStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExitStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getExitLoopNameQ(),
        o.getExitConditionQ()
      )
    } 
    else 
      None
  }
}
object GotoStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.GotoStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.GotoStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getGotoLabelQ()
      )
    } 
    else 
      None
  }
}
object ProcedureCallStatement_IsPrefixNotationQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureCallStatement.IsPrefixNotationQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureCallStatement.IsPrefixNotationQ]
      Some(
        o.getIsPrefixNotation()
      )
    } 
    else 
      None
  }
}
object ProcedureCallStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ProcedureCallStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ProcedureCallStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getCalledNameQ(),
        o.getCallStatementParametersQl(),
        o.getIsPrefixNotationQ()
      )
    } 
    else 
      None
  }
}
object ReturnStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ReturnStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ReturnStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getReturnExpressionQ()
      )
    } 
    else 
      None
  }
}
object ExtendedReturnStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExtendedReturnStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExtendedReturnStatement]
      Some(
        o.getSloc(),
        o.getReturnObjectDeclarationQ(),
        o.getExtendedReturnStatementsQl(),
        o.getExtendedReturnExceptionHandlersQl()
      )
    } 
    else 
      None
  }
}
object AcceptStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AcceptStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AcceptStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getAcceptEntryDirectNameQ(),
        o.getAcceptEntryIndexQ(),
        o.getAcceptParametersQl(),
        o.getAcceptBodyStatementsQl(),
        o.getAcceptBodyExceptionHandlersQl()
      )
    } 
    else 
      None
  }
}
object EntryCallStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EntryCallStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EntryCallStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getCalledNameQ(),
        o.getCallStatementParametersQl()
      )
    } 
    else 
      None
  }
}
object RequeueStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RequeueStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RequeueStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getRequeueEntryNameQ()
      )
    } 
    else 
      None
  }
}
object RequeueStatementWithAbort {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RequeueStatementWithAbort]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RequeueStatementWithAbort]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getRequeueEntryNameQ()
      )
    } 
    else 
      None
  }
}
object DelayUntilStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DelayUntilStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DelayUntilStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getDelayExpressionQ()
      )
    } 
    else 
      None
  }
}
object DelayRelativeStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DelayRelativeStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DelayRelativeStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getDelayExpressionQ()
      )
    } 
    else 
      None
  }
}
object TerminateAlternativeStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TerminateAlternativeStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TerminateAlternativeStatement]
      Some(
        o.getSloc()
      )
    } 
    else 
      None
  }
}
object SelectiveAcceptStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SelectiveAcceptStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SelectiveAcceptStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getStatementPathsQl()
      )
    } 
    else 
      None
  }
}
object TimedEntryCallStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.TimedEntryCallStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.TimedEntryCallStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getStatementPathsQl()
      )
    } 
    else 
      None
  }
}
object ConditionalEntryCallStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ConditionalEntryCallStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ConditionalEntryCallStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getStatementPathsQl()
      )
    } 
    else 
      None
  }
}
object AsynchronousSelectStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AsynchronousSelectStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AsynchronousSelectStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getStatementPathsQl()
      )
    } 
    else 
      None
  }
}
object AbortStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AbortStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AbortStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getAbortedTasksQl()
      )
    } 
    else 
      None
  }
}
object RaiseStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RaiseStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RaiseStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getRaisedExceptionQ(),
        o.getAssociatedMessageQ()
      )
    } 
    else 
      None
  }
}
object CodeStatement {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CodeStatement]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CodeStatement]
      Some(
        o.getSloc(),
        o.getLabelNamesQl(),
        o.getQualifiedExpressionQ()
      )
    } 
    else 
      None
  }
}
object IfPath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IfPath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IfPath]
      Some(
        o.getSloc(),
        o.getConditionExpressionQ(),
        o.getSequenceOfStatementsQl()
      )
    } 
    else 
      None
  }
}
object ElsifPath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElsifPath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElsifPath]
      Some(
        o.getSloc(),
        o.getConditionExpressionQ(),
        o.getSequenceOfStatementsQl()
      )
    } 
    else 
      None
  }
}
object ElsePath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElsePath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElsePath]
      Some(
        o.getSloc(),
        o.getSequenceOfStatementsQl()
      )
    } 
    else 
      None
  }
}
object CasePath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CasePath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CasePath]
      Some(
        o.getSloc(),
        o.getCasePathAlternativeChoicesQl(),
        o.getSequenceOfStatementsQl()
      )
    } 
    else 
      None
  }
}
object SelectPath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.SelectPath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.SelectPath]
      Some(
        o.getSloc(),
        o.getGuardQ(),
        o.getSequenceOfStatementsQl()
      )
    } 
    else 
      None
  }
}
object OrPath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OrPath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OrPath]
      Some(
        o.getSloc(),
        o.getGuardQ(),
        o.getSequenceOfStatementsQl()
      )
    } 
    else 
      None
  }
}
object ThenAbortPath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ThenAbortPath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ThenAbortPath]
      Some(
        o.getSloc(),
        o.getSequenceOfStatementsQl()
      )
    } 
    else 
      None
  }
}
object CaseExpressionPath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CaseExpressionPath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CaseExpressionPath]
      Some(
        o.getSloc(),
        o.getCasePathAlternativeChoicesQl(),
        o.getDependentExpressionQ()
      )
    } 
    else 
      None
  }
}
object IfExpressionPath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.IfExpressionPath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.IfExpressionPath]
      Some(
        o.getSloc(),
        o.getConditionExpressionQ(),
        o.getDependentExpressionQ()
      )
    } 
    else 
      None
  }
}
object ElsifExpressionPath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElsifExpressionPath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElsifExpressionPath]
      Some(
        o.getSloc(),
        o.getConditionExpressionQ(),
        o.getDependentExpressionQ()
      )
    } 
    else 
      None
  }
}
object ElseExpressionPath {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElseExpressionPath]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElseExpressionPath]
      Some(
        o.getSloc(),
        o.getDependentExpressionQ()
      )
    } 
    else 
      None
  }
}
object NameList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.NameList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.NameList]
      Some(
        o.getNames()
      )
    } 
    else 
      None
  }
}
object UsePackageClause {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UsePackageClause]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UsePackageClause]
      Some(
        o.getSloc(),
        o.getClauseNamesQl()
      )
    } 
    else 
      None
  }
}
object UseTypeClause {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UseTypeClause]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UseTypeClause]
      Some(
        o.getSloc(),
        o.getClauseNamesQl()
      )
    } 
    else 
      None
  }
}
object UseAllTypeClause {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.UseAllTypeClause]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.UseAllTypeClause]
      Some(
        o.getSloc(),
        o.getClauseNamesQl()
      )
    } 
    else 
      None
  }
}
object WithClause_HasLimitedQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WithClause.HasLimitedQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WithClause.HasLimitedQ]
      Some(
        o.getHasLimited()
      )
    } 
    else 
      None
  }
}
object WithClause_HasPrivateQ {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WithClause.HasPrivateQ]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WithClause.HasPrivateQ]
      Some(
        o.getHasPrivate()
      )
    } 
    else 
      None
  }
}
object WithClause {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.WithClause]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.WithClause]
      Some(
        o.getSloc(),
        o.getHasLimitedQ(),
        o.getHasPrivateQ(),
        o.getClauseNamesQl()
      )
    } 
    else 
      None
  }
}
object AttributeDefinitionClause {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AttributeDefinitionClause]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AttributeDefinitionClause]
      Some(
        o.getSloc(),
        o.getRepresentationClauseNameQ(),
        o.getRepresentationClauseExpressionQ()
      )
    } 
    else 
      None
  }
}
object EnumerationRepresentationClause {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.EnumerationRepresentationClause]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.EnumerationRepresentationClause]
      Some(
        o.getSloc(),
        o.getRepresentationClauseNameQ(),
        o.getRepresentationClauseExpressionQ()
      )
    } 
    else 
      None
  }
}
object ComponentClauseList {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ComponentClauseList]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ComponentClauseList]
      Some(
        o.getComponentClauses()
      )
    } 
    else 
      None
  }
}
object RecordRepresentationClause {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.RecordRepresentationClause]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.RecordRepresentationClause]
      Some(
        o.getSloc(),
        o.getRepresentationClauseNameQ(),
        o.getModClauseExpressionQ(),
        o.getComponentClausesQl()
      )
    } 
    else 
      None
  }
}
object AtClause {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.AtClause]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.AtClause]
      Some(
        o.getSloc(),
        o.getRepresentationClauseNameQ(),
        o.getRepresentationClauseExpressionQ()
      )
    } 
    else 
      None
  }
}
object ComponentClause {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ComponentClause]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ComponentClause]
      Some(
        o.getSloc(),
        o.getRepresentationClauseNameQ(),
        o.getComponentClausePositionQ(),
        o.getComponentClauseRangeQ()
      )
    } 
    else 
      None
  }
}
object ExceptionHandler {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ExceptionHandler]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ExceptionHandler]
      Some(
        o.getSloc(),
        o.getChoiceParameterSpecificationQ(),
        o.getExceptionChoicesQl(),
        o.getHandlerStatementsQl()
      )
    } 
    else 
      None
  }
}
object ElementClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.ElementClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.ElementClass]
      Some(
        o.getElement()
      )
    } 
    else 
      None
  }
}
object DerivedTypeDefinition {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DerivedTypeDefinition]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DerivedTypeDefinition]
      Some(
        o.getSloc(),
        o.getHasAbstractQ(),
        o.getHasLimitedQ(),
        o.getParentSubtypeIndicationQ()
      )
    } 
    else 
      None
  }
}
object DefinitionClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DefinitionClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DefinitionClass]
      Some(
        o.getDefinition()
      )
    } 
    else 
      None
  }
}
object OrdinaryTypeDeclaration {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.OrdinaryTypeDeclaration]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.OrdinaryTypeDeclaration]
      Some(
        o.getSloc(),
        o.getNamesQl(),
        o.getDiscriminantPartQ(),
        o.getTypeDeclarationViewQ(),
        o.getAspectSpecificationsQl()
      )
    } 
    else 
      None
  }
}
object DeclarationClass {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.DeclarationClass]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.DeclarationClass]
      Some(
        o.getDeclaration()
      )
    } 
    else 
      None
  }
}
object CompilationUnit {
  def unapply(c : java.lang.Class[_]) = {
    if(c.isInstanceOf[org.sireum.bakar.xml.CompilationUnit]) {
      val o = c.asInstanceOf[org.sireum.bakar.xml.CompilationUnit]
      Some(
        o.getSloc(),
        o.getContextClauseElementsQl(),
        o.getUnitDeclarationQ(),
        o.getPragmasAfterQl(),
        o.getUnitKind(),
        o.getUnitClass(),
        o.getUnitOrigin(),
        o.getUnitFullName(),
        o.getDefName(),
        o.getSourceFile()
      )
    } 
    else 
      None
  }
}