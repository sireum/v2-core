package org.sireum.bakar.xml.extractor

object SourceLocationEx {
  def unapply(o : org.sireum.bakar.xml.SourceLocation) = {
    Some(
      o.getLine(),
      o.getCol(),
      o.getEndline(),
      o.getEndcol()
    )
  }
}
object ContextClauseListEx {
  def unapply(o : org.sireum.bakar.xml.ContextClauseList) = {
    Some(
      o.getContextClauses()
    )
  }
}
object NotAnElementEx {
  def unapply(o : org.sireum.bakar.xml.NotAnElement) = {
    Some(
      o.getSloc()
    )
  }
}
object DefiningNameListEx {
  def unapply(o : org.sireum.bakar.xml.DefiningNameList) = {
    Some(
      o.getDefiningNames()
    )
  }
}
object AbstractEx {
  def unapply(o : org.sireum.bakar.xml.Abstract) = {
    Some(
      o.getSloc()
    )
  }
}
object DerivedTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.DerivedTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object LimitedEx {
  def unapply(o : org.sireum.bakar.xml.Limited) = {
    Some(
      o.getSloc()
    )
  }
}
object DerivedTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.DerivedTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object DefiningIdentifierEx {
  def unapply(o : org.sireum.bakar.xml.DefiningIdentifier) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningCharacterLiteralEx {
  def unapply(o : org.sireum.bakar.xml.DefiningCharacterLiteral) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningEnumerationLiteralEx {
  def unapply(o : org.sireum.bakar.xml.DefiningEnumerationLiteral) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningAndOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningAndOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningOrOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningOrOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningXorOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningXorOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningEqualOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningNotEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningNotEqualOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningLessThanOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningLessThanOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningLessThanOrEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningLessThanOrEqualOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningGreaterThanOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningGreaterThanOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningGreaterThanOrEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningGreaterThanOrEqualOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningPlusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningPlusOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningMinusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningMinusOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningConcatenateOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningConcatenateOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningUnaryPlusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningUnaryPlusOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningUnaryMinusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningUnaryMinusOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningMultiplyOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningMultiplyOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningDivideOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningDivideOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningModOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningModOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningRemOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningRemOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningExponentiateOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningExponentiateOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningAbsOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningAbsOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object DefiningNotOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DefiningNotOperator) = {
    Some(
      o.getSloc(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object IdentifierEx {
  def unapply(o : org.sireum.bakar.xml.Identifier) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object DiscreteRangeAttributeReferenceEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteRangeAttributeReference) = {
    Some(
      o.getSloc(),
      o.getRangeAttributeQ()
    )
  }
}
object DiscreteSimpleExpressionRangeEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSimpleExpressionRange) = {
    Some(
      o.getSloc(),
      o.getLowerBoundQ(),
      o.getUpperBoundQ()
    )
  }
}
object OthersChoiceEx {
  def unapply(o : org.sireum.bakar.xml.OthersChoice) = {
    Some(
      o.getSloc()
    )
  }
}
object BoxExpressionEx {
  def unapply(o : org.sireum.bakar.xml.BoxExpression) = {
    Some(
      o.getSloc(),
      o.getType()
    )
  }
}
object IntegerLiteralEx {
  def unapply(o : org.sireum.bakar.xml.IntegerLiteral) = {
    Some(
      o.getSloc(),
      o.getLitVal(),
      o.getType()
    )
  }
}
object RealLiteralEx {
  def unapply(o : org.sireum.bakar.xml.RealLiteral) = {
    Some(
      o.getSloc(),
      o.getLitVal(),
      o.getType()
    )
  }
}
object StringLiteralEx {
  def unapply(o : org.sireum.bakar.xml.StringLiteral) = {
    Some(
      o.getSloc(),
      o.getLitVal(),
      o.getType()
    )
  }
}
object AndOperatorEx {
  def unapply(o : org.sireum.bakar.xml.AndOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object OrOperatorEx {
  def unapply(o : org.sireum.bakar.xml.OrOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object XorOperatorEx {
  def unapply(o : org.sireum.bakar.xml.XorOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object EqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.EqualOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object NotEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.NotEqualOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object LessThanOperatorEx {
  def unapply(o : org.sireum.bakar.xml.LessThanOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object LessThanOrEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.LessThanOrEqualOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object GreaterThanOperatorEx {
  def unapply(o : org.sireum.bakar.xml.GreaterThanOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object GreaterThanOrEqualOperatorEx {
  def unapply(o : org.sireum.bakar.xml.GreaterThanOrEqualOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object PlusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.PlusOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object MinusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.MinusOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object ConcatenateOperatorEx {
  def unapply(o : org.sireum.bakar.xml.ConcatenateOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object UnaryPlusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.UnaryPlusOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object UnaryMinusOperatorEx {
  def unapply(o : org.sireum.bakar.xml.UnaryMinusOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object MultiplyOperatorEx {
  def unapply(o : org.sireum.bakar.xml.MultiplyOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object DivideOperatorEx {
  def unapply(o : org.sireum.bakar.xml.DivideOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object ModOperatorEx {
  def unapply(o : org.sireum.bakar.xml.ModOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object RemOperatorEx {
  def unapply(o : org.sireum.bakar.xml.RemOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object ExponentiateOperatorEx {
  def unapply(o : org.sireum.bakar.xml.ExponentiateOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object AbsOperatorEx {
  def unapply(o : org.sireum.bakar.xml.AbsOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object NotOperatorEx {
  def unapply(o : org.sireum.bakar.xml.NotOperator) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object CharacterLiteralEx {
  def unapply(o : org.sireum.bakar.xml.CharacterLiteral) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object EnumerationLiteralEx {
  def unapply(o : org.sireum.bakar.xml.EnumerationLiteral) = {
    Some(
      o.getSloc(),
      o.getRefName(),
      o.getRef(),
      o.getType()
    )
  }
}
object ExplicitDereferenceEx {
  def unapply(o : org.sireum.bakar.xml.ExplicitDereference) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getType()
    )
  }
}
object AssociationListEx {
  def unapply(o : org.sireum.bakar.xml.AssociationList) = {
    Some(
      o.getAssociations()
    )
  }
}
object IsPrefixCallEx {
  def unapply(o : org.sireum.bakar.xml.IsPrefixCall) = {
    Some(
      o.getSloc()
    )
  }
}
object FunctionCall_IsPrefixCallQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionCall.IsPrefixCallQ) = {
    Some(
      o.getIsPrefixCall()
    )
  }
}
object IsPrefixNotationEx {
  def unapply(o : org.sireum.bakar.xml.IsPrefixNotation) = {
    Some(
      o.getSloc()
    )
  }
}
object FunctionCall_IsPrefixNotationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionCall.IsPrefixNotationQ) = {
    Some(
      o.getIsPrefixNotation()
    )
  }
}
object FunctionCallEx {
  def unapply(o : org.sireum.bakar.xml.FunctionCall) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getFunctionCallParametersQl(),
      o.getIsPrefixCallQ(),
      o.getIsPrefixNotationQ(),
      o.getType()
    )
  }
}
object ExpressionListEx {
  def unapply(o : org.sireum.bakar.xml.ExpressionList) = {
    Some(
      o.getExpressions()
    )
  }
}
object IndexedComponentEx {
  def unapply(o : org.sireum.bakar.xml.IndexedComponent) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getIndexExpressionsQl(),
      o.getType()
    )
  }
}
object RangeAttributeReferenceEx {
  def unapply(o : org.sireum.bakar.xml.RangeAttributeReference) = {
    Some(
      o.getSloc(),
      o.getRangeAttributeQ()
    )
  }
}
object SimpleExpressionRangeEx {
  def unapply(o : org.sireum.bakar.xml.SimpleExpressionRange) = {
    Some(
      o.getSloc(),
      o.getLowerBoundQ(),
      o.getUpperBoundQ()
    )
  }
}
object AllCallsRemotePragmaEx {
  def unapply(o : org.sireum.bakar.xml.AllCallsRemotePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object AsynchronousPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AsynchronousPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object AtomicPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AtomicPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object AtomicComponentsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AtomicComponentsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object AttachHandlerPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AttachHandlerPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ControlledPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ControlledPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ConventionPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ConventionPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object DiscardNamesPragmaEx {
  def unapply(o : org.sireum.bakar.xml.DiscardNamesPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ElaboratePragmaEx {
  def unapply(o : org.sireum.bakar.xml.ElaboratePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ElaborateAllPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ElaborateAllPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ElaborateBodyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ElaborateBodyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ExportPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ExportPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ImportPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ImportPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object InlinePragmaEx {
  def unapply(o : org.sireum.bakar.xml.InlinePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object InspectionPointPragmaEx {
  def unapply(o : org.sireum.bakar.xml.InspectionPointPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object InterruptHandlerPragmaEx {
  def unapply(o : org.sireum.bakar.xml.InterruptHandlerPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object InterruptPriorityPragmaEx {
  def unapply(o : org.sireum.bakar.xml.InterruptPriorityPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object LinkerOptionsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.LinkerOptionsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ListPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ListPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object LockingPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.LockingPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object NormalizeScalarsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.NormalizeScalarsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object OptimizePragmaEx {
  def unapply(o : org.sireum.bakar.xml.OptimizePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object PackPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PackPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object PagePragmaEx {
  def unapply(o : org.sireum.bakar.xml.PagePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object PreelaboratePragmaEx {
  def unapply(o : org.sireum.bakar.xml.PreelaboratePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object PriorityPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PriorityPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object PurePragmaEx {
  def unapply(o : org.sireum.bakar.xml.PurePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object QueuingPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.QueuingPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object RemoteCallInterfacePragmaEx {
  def unapply(o : org.sireum.bakar.xml.RemoteCallInterfacePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object RemoteTypesPragmaEx {
  def unapply(o : org.sireum.bakar.xml.RemoteTypesPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object RestrictionsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.RestrictionsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ReviewablePragmaEx {
  def unapply(o : org.sireum.bakar.xml.ReviewablePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object SharedPassivePragmaEx {
  def unapply(o : org.sireum.bakar.xml.SharedPassivePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object StorageSizePragmaEx {
  def unapply(o : org.sireum.bakar.xml.StorageSizePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object SuppressPragmaEx {
  def unapply(o : org.sireum.bakar.xml.SuppressPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object TaskDispatchingPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.TaskDispatchingPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object VolatilePragmaEx {
  def unapply(o : org.sireum.bakar.xml.VolatilePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object VolatileComponentsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.VolatileComponentsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object AssertPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AssertPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object AssertionPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.AssertionPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object DetectBlockingPragmaEx {
  def unapply(o : org.sireum.bakar.xml.DetectBlockingPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object NoReturnPragmaEx {
  def unapply(o : org.sireum.bakar.xml.NoReturnPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object PartitionElaborationPolicyPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PartitionElaborationPolicyPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object PreelaborableInitializationPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PreelaborableInitializationPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object PrioritySpecificDispatchingPragmaEx {
  def unapply(o : org.sireum.bakar.xml.PrioritySpecificDispatchingPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ProfilePragmaEx {
  def unapply(o : org.sireum.bakar.xml.ProfilePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object RelativeDeadlinePragmaEx {
  def unapply(o : org.sireum.bakar.xml.RelativeDeadlinePragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object UncheckedUnionPragmaEx {
  def unapply(o : org.sireum.bakar.xml.UncheckedUnionPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object UnsuppressPragmaEx {
  def unapply(o : org.sireum.bakar.xml.UnsuppressPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object DefaultStoragePoolPragmaEx {
  def unapply(o : org.sireum.bakar.xml.DefaultStoragePoolPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object DispatchingDomainPragmaEx {
  def unapply(o : org.sireum.bakar.xml.DispatchingDomainPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object CpuPragmaEx {
  def unapply(o : org.sireum.bakar.xml.CpuPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object IndependentPragmaEx {
  def unapply(o : org.sireum.bakar.xml.IndependentPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object IndependentComponentsPragmaEx {
  def unapply(o : org.sireum.bakar.xml.IndependentComponentsPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object ImplementationDefinedPragmaEx {
  def unapply(o : org.sireum.bakar.xml.ImplementationDefinedPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object UnknownPragmaEx {
  def unapply(o : org.sireum.bakar.xml.UnknownPragma) = {
    Some(
      o.getSloc(),
      o.getPragmaArgumentAssociationsQl(),
      o.getPragmaName()
    )
  }
}
object RangeConstraintClassEx {
  def unapply(o : org.sireum.bakar.xml.RangeConstraintClass) = {
    Some(
      o.getRangeConstraint()
    )
  }
}
object DigitsConstraintEx {
  def unapply(o : org.sireum.bakar.xml.DigitsConstraint) = {
    Some(
      o.getSloc(),
      o.getDigitsExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}
object DeltaConstraintEx {
  def unapply(o : org.sireum.bakar.xml.DeltaConstraint) = {
    Some(
      o.getSloc(),
      o.getDeltaExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}
object DiscreteRangeListEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteRangeList) = {
    Some(
      o.getDiscreteRanges()
    )
  }
}
object IndexConstraintEx {
  def unapply(o : org.sireum.bakar.xml.IndexConstraint) = {
    Some(
      o.getSloc(),
      o.getDiscreteRangesQl()
    )
  }
}
object DiscriminantAssociationListEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantAssociationList) = {
    Some(
      o.getDiscriminantAssociations()
    )
  }
}
object DiscriminantConstraintEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantConstraint) = {
    Some(
      o.getSloc(),
      o.getDiscriminantAssociationsQl()
    )
  }
}
object ConstraintClassEx {
  def unapply(o : org.sireum.bakar.xml.ConstraintClass) = {
    Some(
      o.getConstraint()
    )
  }
}
object DiscreteSubtypeIndicationEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSubtypeIndication) = {
    Some(
      o.getSloc(),
      o.getSubtypeMarkQ(),
      o.getSubtypeConstraintQ()
    )
  }
}
object DiscreteRangeClassEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteRangeClass) = {
    Some(
      o.getDiscreteRange()
    )
  }
}
object SliceEx {
  def unapply(o : org.sireum.bakar.xml.Slice) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getSliceRangeQ(),
      o.getType()
    )
  }
}
object AccessAttributeEx {
  def unapply(o : org.sireum.bakar.xml.AccessAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object AddressAttributeEx {
  def unapply(o : org.sireum.bakar.xml.AddressAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object AdjacentAttributeEx {
  def unapply(o : org.sireum.bakar.xml.AdjacentAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object AftAttributeEx {
  def unapply(o : org.sireum.bakar.xml.AftAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object AlignmentAttributeEx {
  def unapply(o : org.sireum.bakar.xml.AlignmentAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object BaseAttributeEx {
  def unapply(o : org.sireum.bakar.xml.BaseAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object BitOrderAttributeEx {
  def unapply(o : org.sireum.bakar.xml.BitOrderAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object BodyVersionAttributeEx {
  def unapply(o : org.sireum.bakar.xml.BodyVersionAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object CallableAttributeEx {
  def unapply(o : org.sireum.bakar.xml.CallableAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object CallerAttributeEx {
  def unapply(o : org.sireum.bakar.xml.CallerAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object CeilingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.CeilingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ClassAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ClassAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ComponentSizeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ComponentSizeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ComposeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ComposeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ConstrainedAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ConstrainedAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object CopySignAttributeEx {
  def unapply(o : org.sireum.bakar.xml.CopySignAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object CountAttributeEx {
  def unapply(o : org.sireum.bakar.xml.CountAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object DefiniteAttributeEx {
  def unapply(o : org.sireum.bakar.xml.DefiniteAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object DeltaAttributeEx {
  def unapply(o : org.sireum.bakar.xml.DeltaAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object DenormAttributeEx {
  def unapply(o : org.sireum.bakar.xml.DenormAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object DigitsAttributeEx {
  def unapply(o : org.sireum.bakar.xml.DigitsAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ExponentAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ExponentAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ExternalTagAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ExternalTagAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object FirstAttributeEx {
  def unapply(o : org.sireum.bakar.xml.FirstAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}
object FirstBitAttributeEx {
  def unapply(o : org.sireum.bakar.xml.FirstBitAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object FloorAttributeEx {
  def unapply(o : org.sireum.bakar.xml.FloorAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ForeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ForeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object FractionAttributeEx {
  def unapply(o : org.sireum.bakar.xml.FractionAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object IdentityAttributeEx {
  def unapply(o : org.sireum.bakar.xml.IdentityAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ImageAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ImageAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object InputAttributeEx {
  def unapply(o : org.sireum.bakar.xml.InputAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object LastAttributeEx {
  def unapply(o : org.sireum.bakar.xml.LastAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}
object LastBitAttributeEx {
  def unapply(o : org.sireum.bakar.xml.LastBitAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object LeadingPartAttributeEx {
  def unapply(o : org.sireum.bakar.xml.LeadingPartAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object LengthAttributeEx {
  def unapply(o : org.sireum.bakar.xml.LengthAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}
object MachineAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MachineEmaxAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineEmaxAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MachineEminAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineEminAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MachineMantissaAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineMantissaAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MachineOverflowsAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineOverflowsAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MachineRadixAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineRadixAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MachineRoundsAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineRoundsAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MaxAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MaxAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MaxSizeInStorageElementsAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MaxSizeInStorageElementsAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MinAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MinAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ModelAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModelAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ModelEminAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModelEminAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ModelEpsilonAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModelEpsilonAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ModelMantissaAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModelMantissaAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ModelSmallAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModelSmallAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ModulusAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModulusAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object OutputAttributeEx {
  def unapply(o : org.sireum.bakar.xml.OutputAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object PartitionIdAttributeEx {
  def unapply(o : org.sireum.bakar.xml.PartitionIdAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object PosAttributeEx {
  def unapply(o : org.sireum.bakar.xml.PosAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object PositionAttributeEx {
  def unapply(o : org.sireum.bakar.xml.PositionAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object PredAttributeEx {
  def unapply(o : org.sireum.bakar.xml.PredAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object RangeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.RangeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}
object ReadAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ReadAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object RemainderAttributeEx {
  def unapply(o : org.sireum.bakar.xml.RemainderAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object RoundAttributeEx {
  def unapply(o : org.sireum.bakar.xml.RoundAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object RoundingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.RoundingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object SafeFirstAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SafeFirstAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object SafeLastAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SafeLastAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ScaleAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ScaleAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ScalingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ScalingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object SignedZerosAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SignedZerosAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object SizeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SizeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object SmallAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SmallAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object StoragePoolAttributeEx {
  def unapply(o : org.sireum.bakar.xml.StoragePoolAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object StorageSizeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.StorageSizeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object SuccAttributeEx {
  def unapply(o : org.sireum.bakar.xml.SuccAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object TagAttributeEx {
  def unapply(o : org.sireum.bakar.xml.TagAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object TerminatedAttributeEx {
  def unapply(o : org.sireum.bakar.xml.TerminatedAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object TruncationAttributeEx {
  def unapply(o : org.sireum.bakar.xml.TruncationAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object UnbiasedRoundingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.UnbiasedRoundingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object UncheckedAccessAttributeEx {
  def unapply(o : org.sireum.bakar.xml.UncheckedAccessAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ValAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ValAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ValidAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ValidAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ValueAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ValueAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object VersionAttributeEx {
  def unapply(o : org.sireum.bakar.xml.VersionAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object WideImageAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideImageAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object WideValueAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideValueAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object WideWidthAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideWidthAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object WidthAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WidthAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object WriteAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WriteAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MachineRoundingAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MachineRoundingAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ModAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ModAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object PriorityAttributeEx {
  def unapply(o : org.sireum.bakar.xml.PriorityAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object StreamSizeAttributeEx {
  def unapply(o : org.sireum.bakar.xml.StreamSizeAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object WideWideImageAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideWideImageAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object WideWideValueAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideWideValueAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object WideWideWidthAttributeEx {
  def unapply(o : org.sireum.bakar.xml.WideWideWidthAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object MaxAlignmentForAllocationAttributeEx {
  def unapply(o : org.sireum.bakar.xml.MaxAlignmentForAllocationAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object OverlapsStorageAttributeEx {
  def unapply(o : org.sireum.bakar.xml.OverlapsStorageAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getType()
    )
  }
}
object ImplementationDefinedAttributeEx {
  def unapply(o : org.sireum.bakar.xml.ImplementationDefinedAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}
object UnknownAttributeEx {
  def unapply(o : org.sireum.bakar.xml.UnknownAttribute) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getAttributeDesignatorIdentifierQ(),
      o.getAttributeDesignatorExpressionsQl(),
      o.getType()
    )
  }
}
object RecordAggregateEx {
  def unapply(o : org.sireum.bakar.xml.RecordAggregate) = {
    Some(
      o.getSloc(),
      o.getRecordComponentAssociationsQl(),
      o.getType()
    )
  }
}
object ExtensionAggregateEx {
  def unapply(o : org.sireum.bakar.xml.ExtensionAggregate) = {
    Some(
      o.getSloc(),
      o.getExtensionAggregateExpressionQ(),
      o.getRecordComponentAssociationsQl(),
      o.getType()
    )
  }
}
object PositionalArrayAggregateEx {
  def unapply(o : org.sireum.bakar.xml.PositionalArrayAggregate) = {
    Some(
      o.getSloc(),
      o.getArrayComponentAssociationsQl(),
      o.getType()
    )
  }
}
object NamedArrayAggregateEx {
  def unapply(o : org.sireum.bakar.xml.NamedArrayAggregate) = {
    Some(
      o.getSloc(),
      o.getArrayComponentAssociationsQl(),
      o.getType()
    )
  }
}
object AndThenShortCircuitEx {
  def unapply(o : org.sireum.bakar.xml.AndThenShortCircuit) = {
    Some(
      o.getSloc(),
      o.getShortCircuitOperationLeftExpressionQ(),
      o.getShortCircuitOperationRightExpressionQ(),
      o.getType()
    )
  }
}
object OrElseShortCircuitEx {
  def unapply(o : org.sireum.bakar.xml.OrElseShortCircuit) = {
    Some(
      o.getSloc(),
      o.getShortCircuitOperationLeftExpressionQ(),
      o.getShortCircuitOperationRightExpressionQ(),
      o.getType()
    )
  }
}
object ElementListEx {
  def unapply(o : org.sireum.bakar.xml.ElementList) = {
    Some(
      o.getElements()
    )
  }
}
object InMembershipTestEx {
  def unapply(o : org.sireum.bakar.xml.InMembershipTest) = {
    Some(
      o.getSloc(),
      o.getMembershipTestExpressionQ(),
      o.getMembershipTestChoicesQl(),
      o.getType()
    )
  }
}
object NotInMembershipTestEx {
  def unapply(o : org.sireum.bakar.xml.NotInMembershipTest) = {
    Some(
      o.getSloc(),
      o.getMembershipTestExpressionQ(),
      o.getMembershipTestChoicesQl(),
      o.getType()
    )
  }
}
object NullLiteralEx {
  def unapply(o : org.sireum.bakar.xml.NullLiteral) = {
    Some(
      o.getSloc(),
      o.getType()
    )
  }
}
object ParenthesizedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.ParenthesizedExpression) = {
    Some(
      o.getSloc(),
      o.getExpressionParenthesizedQ(),
      o.getType()
    )
  }
}
object TypeConversionEx {
  def unapply(o : org.sireum.bakar.xml.TypeConversion) = {
    Some(
      o.getSloc(),
      o.getConvertedOrQualifiedSubtypeMarkQ(),
      o.getConvertedOrQualifiedExpressionQ(),
      o.getType()
    )
  }
}
object QualifiedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.QualifiedExpression) = {
    Some(
      o.getSloc(),
      o.getConvertedOrQualifiedSubtypeMarkQ(),
      o.getConvertedOrQualifiedExpressionQ(),
      o.getType()
    )
  }
}
object AllocationFromSubtypeEx {
  def unapply(o : org.sireum.bakar.xml.AllocationFromSubtype) = {
    Some(
      o.getSloc(),
      o.getSubpoolNameQ(),
      o.getAllocatorSubtypeIndicationQ(),
      o.getType()
    )
  }
}
object AllocationFromQualifiedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.AllocationFromQualifiedExpression) = {
    Some(
      o.getSloc(),
      o.getSubpoolNameQ(),
      o.getAllocatorQualifiedExpressionQ(),
      o.getType()
    )
  }
}
object CaseExpressionEx {
  def unapply(o : org.sireum.bakar.xml.CaseExpression) = {
    Some(
      o.getSloc(),
      o.getCaseExpressionQ(),
      o.getExpressionPathsQl(),
      o.getType()
    )
  }
}
object IfExpressionEx {
  def unapply(o : org.sireum.bakar.xml.IfExpression) = {
    Some(
      o.getSloc(),
      o.getExpressionPathsQl(),
      o.getType()
    )
  }
}
object ForAllQuantifiedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.ForAllQuantifiedExpression) = {
    Some(
      o.getSloc(),
      o.getIteratorSpecificationQ(),
      o.getPredicateQ(),
      o.getType()
    )
  }
}
object ForSomeQuantifiedExpressionEx {
  def unapply(o : org.sireum.bakar.xml.ForSomeQuantifiedExpression) = {
    Some(
      o.getSloc(),
      o.getIteratorSpecificationQ(),
      o.getPredicateQ(),
      o.getType()
    )
  }
}
object ExpressionClassEx {
  def unapply(o : org.sireum.bakar.xml.ExpressionClass) = {
    Some(
      o.getExpression()
    )
  }
}
object SelectedComponentEx {
  def unapply(o : org.sireum.bakar.xml.SelectedComponent) = {
    Some(
      o.getSloc(),
      o.getPrefixQ(),
      o.getSelectorQ(),
      o.getType()
    )
  }
}
object NameClassEx {
  def unapply(o : org.sireum.bakar.xml.NameClass) = {
    Some(
      o.getName()
    )
  }
}
object DefiningNameClassEx {
  def unapply(o : org.sireum.bakar.xml.DefiningNameClass) = {
    Some(
      o.getDefiningName()
    )
  }
}
object DefiningExpandedNameEx {
  def unapply(o : org.sireum.bakar.xml.DefiningExpandedName) = {
    Some(
      o.getSloc(),
      o.getDefiningPrefixQ(),
      o.getDefiningSelectorQ(),
      o.getDefName(),
      o.getDef(),
      o.getType()
    )
  }
}
object TaskTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.TaskTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getAspectSpecificationsQl(),
      o.getDeclarationInterfaceListQl(),
      o.getTypeDeclarationViewQ()
    )
  }
}
object ProtectedTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getAspectSpecificationsQl(),
      o.getDeclarationInterfaceListQl(),
      o.getTypeDeclarationViewQ()
    )
  }
}
object IncompleteTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.IncompleteTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object TaggedIncompleteTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.TaggedIncompleteTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object PrivateTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PrivateTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object PrivateExtensionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PrivateExtensionDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object SubtypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.SubtypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object AliasedEx {
  def unapply(o : org.sireum.bakar.xml.Aliased) = {
    Some(
      o.getSloc()
    )
  }
}
object VariableDeclaration_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.VariableDeclaration.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}
object VariableDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.VariableDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object ConstantDeclaration_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ConstantDeclaration.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}
object ConstantDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ConstantDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object DeferredConstantDeclaration_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.DeferredConstantDeclaration.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}
object DeferredConstantDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.DeferredConstantDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object SingleTaskDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.SingleTaskDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getDeclarationInterfaceListQl(),
      o.getObjectDeclarationViewQ()
    )
  }
}
object SingleProtectedDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.SingleProtectedDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getDeclarationInterfaceListQl(),
      o.getObjectDeclarationViewQ()
    )
  }
}
object IntegerNumberDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.IntegerNumberDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getInitializationExpressionQ()
    )
  }
}
object RealNumberDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.RealNumberDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getInitializationExpressionQ()
    )
  }
}
object EnumerationLiteralSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.EnumerationLiteralSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl()
    )
  }
}
object NullExclusionEx {
  def unapply(o : org.sireum.bakar.xml.NullExclusion) = {
    Some(
      o.getSloc()
    )
  }
}
object DiscriminantSpecification_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantSpecification.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object DiscriminantSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasNullExclusionQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ()
    )
  }
}
object ComponentDeclaration_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ComponentDeclaration.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}
object ComponentDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ComponentDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object ReverseEx {
  def unapply(o : org.sireum.bakar.xml.Reverse) = {
    Some(
      o.getSloc()
    )
  }
}
object LoopParameterSpecification_HasReverseQEx {
  def unapply(o : org.sireum.bakar.xml.LoopParameterSpecification.HasReverseQ) = {
    Some(
      o.getHasReverse()
    )
  }
}
object DiscreteSubtypeIndicationAsSubtypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSubtypeIndicationAsSubtypeDefinition) = {
    Some(
      o.getSloc(),
      o.getSubtypeMarkQ(),
      o.getSubtypeConstraintQ()
    )
  }
}
object DiscreteRangeAttributeReferenceAsSubtypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteRangeAttributeReferenceAsSubtypeDefinition) = {
    Some(
      o.getSloc(),
      o.getRangeAttributeQ()
    )
  }
}
object DiscreteSimpleExpressionRangeAsSubtypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSimpleExpressionRangeAsSubtypeDefinition) = {
    Some(
      o.getSloc(),
      o.getLowerBoundQ(),
      o.getUpperBoundQ()
    )
  }
}
object DiscreteSubtypeDefinitionClassEx {
  def unapply(o : org.sireum.bakar.xml.DiscreteSubtypeDefinitionClass) = {
    Some(
      o.getDiscreteSubtypeDefinition()
    )
  }
}
object LoopParameterSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.LoopParameterSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasReverseQ(),
      o.getSpecificationSubtypeDefinitionQ()
    )
  }
}
object GeneralizedIteratorSpecification_HasReverseQEx {
  def unapply(o : org.sireum.bakar.xml.GeneralizedIteratorSpecification.HasReverseQ) = {
    Some(
      o.getHasReverse()
    )
  }
}
object GeneralizedIteratorSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.GeneralizedIteratorSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasReverseQ(),
      o.getIterationSchemeNameQ()
    )
  }
}
object ElementIteratorSpecification_HasReverseQEx {
  def unapply(o : org.sireum.bakar.xml.ElementIteratorSpecification.HasReverseQ) = {
    Some(
      o.getHasReverse()
    )
  }
}
object ElementIteratorSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ElementIteratorSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getSubtypeIndicationQ(),
      o.getHasReverseQ(),
      o.getIterationSchemeNameQ()
    )
  }
}
object OverridingEx {
  def unapply(o : org.sireum.bakar.xml.Overriding) = {
    Some(
      o.getSloc()
    )
  }
}
object ProcedureDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object NotOverridingEx {
  def unapply(o : org.sireum.bakar.xml.NotOverriding) = {
    Some(
      o.getSloc()
    )
  }
}
object ProcedureDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object ParameterSpecificationListEx {
  def unapply(o : org.sireum.bakar.xml.ParameterSpecificationList) = {
    Some(
      o.getParameterSpecifications()
    )
  }
}
object ProcedureDeclaration_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureDeclaration.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object ProcedureDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureDeclaration) = {
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
}
object FunctionDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object FunctionDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object NotNullReturnEx {
  def unapply(o : org.sireum.bakar.xml.NotNullReturn) = {
    Some(
      o.getSloc()
    )
  }
}
object FunctionDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object FunctionDeclaration_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionDeclaration.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object FunctionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FunctionDeclaration) = {
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
}
object ParameterSpecification_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ParameterSpecification.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}
object ParameterSpecification_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.ParameterSpecification.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object ParameterSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ParameterSpecification) = {
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
}
object ProcedureBodyDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object ProcedureBodyDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object StatementListEx {
  def unapply(o : org.sireum.bakar.xml.StatementList) = {
    Some(
      o.getStatements()
    )
  }
}
object ExceptionHandlerListEx {
  def unapply(o : org.sireum.bakar.xml.ExceptionHandlerList) = {
    Some(
      o.getExceptionHandlers()
    )
  }
}
object ProcedureBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyDeclaration) = {
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
}
object FunctionBodyDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object FunctionBodyDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object FunctionBodyDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object FunctionBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyDeclaration) = {
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
}
object ReturnVariableSpecification_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ReturnVariableSpecification.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}
object ReturnVariableSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ReturnVariableSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ()
    )
  }
}
object ReturnConstantSpecification_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ReturnConstantSpecification.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}
object ReturnConstantSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ReturnConstantSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasAliasedQ(),
      o.getObjectDeclarationViewQ(),
      o.getInitializationExpressionQ()
    )
  }
}
object NullProcedureDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.NullProcedureDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object NullProcedureDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.NullProcedureDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object NullProcedureDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.NullProcedureDeclaration) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getAspectSpecificationsQl()
    )
  }
}
object ExpressionFunctionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ExpressionFunctionDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getResultProfileQ(),
      o.getResultExpressionQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object DeclarativeItemListEx {
  def unapply(o : org.sireum.bakar.xml.DeclarativeItemList) = {
    Some(
      o.getDeclarativeItems()
    )
  }
}
object PackageDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PackageDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getVisiblePartDeclarativeItemsQl(),
      o.getPrivatePartDeclarativeItemsQl()
    )
  }
}
object PackageBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PackageBodyDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getBodyDeclarativeItemsQl(),
      o.getBodyStatementsQl(),
      o.getBodyExceptionHandlersQl()
    )
  }
}
object ObjectRenamingDeclaration_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.ObjectRenamingDeclaration.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object ObjectRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ObjectRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getHasNullExclusionQ(),
      o.getObjectDeclarationViewQ(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object ExceptionRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ExceptionRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object PackageRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.PackageRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object ProcedureRenamingDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureRenamingDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object ProcedureRenamingDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureRenamingDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object ProcedureRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureRenamingDeclaration) = {
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
}
object FunctionRenamingDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionRenamingDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object FunctionRenamingDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionRenamingDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object FunctionRenamingDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionRenamingDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object FunctionRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FunctionRenamingDeclaration) = {
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
}
object GenericPackageRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericPackageRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object GenericProcedureRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericProcedureRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object GenericFunctionRenamingDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericFunctionRenamingDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getRenamedEntityQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object TaskBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.TaskBodyDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getBodyDeclarativeItemsQl(),
      o.getBodyStatementsQl(),
      o.getBodyExceptionHandlersQl()
    )
  }
}
object DeclarationListEx {
  def unapply(o : org.sireum.bakar.xml.DeclarationList) = {
    Some(
      o.getDeclarations()
    )
  }
}
object ProtectedBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedBodyDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getProtectedOperationItemsQl()
    )
  }
}
object EntryDeclaration_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.EntryDeclaration.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object EntryDeclaration_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.EntryDeclaration.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object EntryDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.EntryDeclaration) = {
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
}
object EntryBodyDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.EntryBodyDeclaration) = {
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
}
object EntryIndexSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.EntryIndexSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getSpecificationSubtypeDefinitionQ()
    )
  }
}
object ProcedureBodyStub_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyStub.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object ProcedureBodyStub_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyStub.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object ProcedureBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureBodyStub) = {
    Some(
      o.getSloc(),
      o.getIsOverridingDeclarationQ(),
      o.getIsNotOverridingDeclarationQ(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getAspectSpecificationsQl()
    )
  }
}
object FunctionBodyStub_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyStub.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object FunctionBodyStub_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyStub.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object FunctionBodyStub_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyStub.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object FunctionBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.FunctionBodyStub) = {
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
}
object PackageBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.PackageBodyStub) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl()
    )
  }
}
object TaskBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.TaskBodyStub) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl()
    )
  }
}
object ProtectedBodyStubEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedBodyStub) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl()
    )
  }
}
object ExceptionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.ExceptionDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl()
    )
  }
}
object ChoiceParameterSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.ChoiceParameterSpecification) = {
    Some(
      o.getSloc(),
      o.getNamesQl()
    )
  }
}
object GenericProcedureDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericProcedureDeclaration) = {
    Some(
      o.getSloc(),
      o.getGenericFormalPartQl(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getAspectSpecificationsQl()
    )
  }
}
object GenericFunctionDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.GenericFunctionDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object GenericFunctionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericFunctionDeclaration) = {
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
}
object GenericPackageDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.GenericPackageDeclaration) = {
    Some(
      o.getSloc(),
      o.getGenericFormalPartQl(),
      o.getNamesQl(),
      o.getAspectSpecificationsQl(),
      o.getVisiblePartDeclarativeItemsQl(),
      o.getPrivatePartDeclarativeItemsQl()
    )
  }
}
object PackageInstantiationEx {
  def unapply(o : org.sireum.bakar.xml.PackageInstantiation) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getGenericUnitNameQ(),
      o.getGenericActualPartQl(),
      o.getAspectSpecificationsQl()
    )
  }
}
object ProcedureInstantiation_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureInstantiation.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object ProcedureInstantiation_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureInstantiation.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object ProcedureInstantiationEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureInstantiation) = {
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
}
object FunctionInstantiation_IsOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionInstantiation.IsOverridingDeclarationQ) = {
    Some(
      o.getIsOverriding()
    )
  }
}
object FunctionInstantiation_IsNotOverridingDeclarationQEx {
  def unapply(o : org.sireum.bakar.xml.FunctionInstantiation.IsNotOverridingDeclarationQ) = {
    Some(
      o.getIsNotOverriding()
    )
  }
}
object FunctionInstantiationEx {
  def unapply(o : org.sireum.bakar.xml.FunctionInstantiation) = {
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
}
object FormalObjectDeclaration_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalObjectDeclaration.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object FormalObjectDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalObjectDeclaration) = {
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
}
object FormalTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object TaggedEx {
  def unapply(o : org.sireum.bakar.xml.Tagged) = {
    Some(
      o.getSloc()
    )
  }
}
object FormalIncompleteTypeDeclaration_HasTaggedQEx {
  def unapply(o : org.sireum.bakar.xml.FormalIncompleteTypeDeclaration.HasTaggedQ) = {
    Some(
      o.getHasTagged()
    )
  }
}
object FormalIncompleteTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalIncompleteTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getHasTaggedQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object FormalProcedureDeclaration_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FormalProcedureDeclaration.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object FormalProcedureDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalProcedureDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getParameterProfileQl(),
      o.getFormalSubprogramDefaultQ(),
      o.getHasAbstractQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object FormalFunctionDeclaration_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FormalFunctionDeclaration.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object FormalFunctionDeclaration_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FormalFunctionDeclaration.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object FormalFunctionDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalFunctionDeclaration) = {
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
}
object FormalPackageDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.FormalPackageDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getGenericUnitNameQ(),
      o.getGenericActualPartQl(),
      o.getAspectSpecificationsQl()
    )
  }
}
object FormalPackageDeclarationWithBoxEx {
  def unapply(o : org.sireum.bakar.xml.FormalPackageDeclarationWithBox) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getGenericUnitNameQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object DerivedRecordExtensionDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.DerivedRecordExtensionDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object DerivedRecordExtensionDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.DerivedRecordExtensionDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object DerivedRecordExtensionDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DerivedRecordExtensionDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getParentSubtypeIndicationQ(),
      o.getDefinitionInterfaceListQl(),
      o.getRecordDefinitionQ()
    )
  }
}
object EnumerationTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.EnumerationTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getEnumerationLiteralDeclarationsQl()
    )
  }
}
object SignedIntegerTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.SignedIntegerTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getIntegerConstraintQ()
    )
  }
}
object ModularTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.ModularTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getModStaticExpressionQ()
    )
  }
}
object RootIntegerDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.RootIntegerDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object RootRealDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.RootRealDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object UniversalIntegerDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.UniversalIntegerDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object UniversalRealDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.UniversalRealDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object UniversalFixedDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.UniversalFixedDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object FloatingPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FloatingPointDefinition) = {
    Some(
      o.getSloc(),
      o.getDigitsExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}
object OrdinaryFixedPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.OrdinaryFixedPointDefinition) = {
    Some(
      o.getSloc(),
      o.getDeltaExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}
object DecimalFixedPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DecimalFixedPointDefinition) = {
    Some(
      o.getSloc(),
      o.getDeltaExpressionQ(),
      o.getDigitsExpressionQ(),
      o.getRealRangeConstraintQ()
    )
  }
}
object UnconstrainedArrayDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.UnconstrainedArrayDefinition) = {
    Some(
      o.getSloc(),
      o.getIndexSubtypeDefinitionsQl(),
      o.getArrayComponentDefinitionQ()
    )
  }
}
object DefinitionListEx {
  def unapply(o : org.sireum.bakar.xml.DefinitionList) = {
    Some(
      o.getDefinitions()
    )
  }
}
object ConstrainedArrayDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.ConstrainedArrayDefinition) = {
    Some(
      o.getSloc(),
      o.getDiscreteSubtypeDefinitionsQl(),
      o.getArrayComponentDefinitionQ()
    )
  }
}
object RecordTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.RecordTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object RecordTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.RecordTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object RecordTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.RecordTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getRecordDefinitionQ()
    )
  }
}
object TaggedRecordTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.TaggedRecordTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object TaggedRecordTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.TaggedRecordTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object TaggedRecordTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.TaggedRecordTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getRecordDefinitionQ()
    )
  }
}
object OrdinaryInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.OrdinaryInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object LimitedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.LimitedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object TaskInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.TaskInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object ProtectedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object SynchronizedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.SynchronizedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object PoolSpecificAccessToVariable_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.PoolSpecificAccessToVariable.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object PoolSpecificAccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.PoolSpecificAccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}
object AccessToVariable_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToVariable.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.AccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}
object AccessToConstant_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToConstant.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AccessToConstantEx {
  def unapply(o : org.sireum.bakar.xml.AccessToConstant) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}
object AccessToProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AccessToProcedureEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}
object AccessToProtectedProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProtectedProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AccessToProtectedProcedureEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProtectedProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}
object AccessToFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToFunction.HasNullExclusionQ) = {
    Some(
      o.getNullExclusion(),
      o.getNotAnElement()
    )
  }
}
object AccessToFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object AccessToFunctionEx {
  def unapply(o : org.sireum.bakar.xml.AccessToFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}
object AccessToProtectedFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProtectedFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AccessToProtectedFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProtectedFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object AccessToProtectedFunctionEx {
  def unapply(o : org.sireum.bakar.xml.AccessToProtectedFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}
object SubtypeIndication_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.SubtypeIndication.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}
object SubtypeIndication_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.SubtypeIndication.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object SubtypeIndicationEx {
  def unapply(o : org.sireum.bakar.xml.SubtypeIndication) = {
    Some(
      o.getSloc(),
      o.getHasAliasedQ(),
      o.getHasNullExclusionQ(),
      o.getSubtypeMarkQ(),
      o.getSubtypeConstraintQ()
    )
  }
}
object ComponentDefinition_HasAliasedQEx {
  def unapply(o : org.sireum.bakar.xml.ComponentDefinition.HasAliasedQ) = {
    Some(
      o.getHasAliased()
    )
  }
}
object ComponentDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.ComponentDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAliasedQ(),
      o.getComponentDefinitionViewQ()
    )
  }
}
object UnknownDiscriminantPartEx {
  def unapply(o : org.sireum.bakar.xml.UnknownDiscriminantPart) = {
    Some(
      o.getSloc()
    )
  }
}
object DiscriminantSpecificationListEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantSpecificationList) = {
    Some(
      o.getDiscriminantSpecifications()
    )
  }
}
object KnownDiscriminantPartEx {
  def unapply(o : org.sireum.bakar.xml.KnownDiscriminantPart) = {
    Some(
      o.getSloc(),
      o.getDiscriminantsQl()
    )
  }
}
object RecordDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.RecordDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object RecordComponentListEx {
  def unapply(o : org.sireum.bakar.xml.RecordComponentList) = {
    Some(
      o.getRecordComponents()
    )
  }
}
object RecordDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.RecordDefinition) = {
    Some(
      o.getSloc(),
      o.getHasLimitedQ(),
      o.getRecordComponentsQl()
    )
  }
}
object NullRecordDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.NullRecordDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object NullComponentEx {
  def unapply(o : org.sireum.bakar.xml.NullComponent) = {
    Some(
      o.getSloc()
    )
  }
}
object VariantListEx {
  def unapply(o : org.sireum.bakar.xml.VariantList) = {
    Some(
      o.getVariants()
    )
  }
}
object VariantPartEx {
  def unapply(o : org.sireum.bakar.xml.VariantPart) = {
    Some(
      o.getSloc(),
      o.getDiscriminantDirectNameQ(),
      o.getVariantsQl()
    )
  }
}
object VariantEx {
  def unapply(o : org.sireum.bakar.xml.Variant) = {
    Some(
      o.getSloc(),
      o.getVariantChoicesQl(),
      o.getRecordComponentsQl()
    )
  }
}
object AnonymousAccessToVariable_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToVariable.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AnonymousAccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAnonymousAccessToObjectSubtypeMarkQ()
    )
  }
}
object AnonymousAccessToConstant_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToConstant.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AnonymousAccessToConstantEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToConstant) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAnonymousAccessToObjectSubtypeMarkQ()
    )
  }
}
object AnonymousAccessToProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AnonymousAccessToProcedureEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}
object AnonymousAccessToProtectedProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AnonymousAccessToProtectedProcedureEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}
object AnonymousAccessToFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AnonymousAccessToFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object AnonymousAccessToFunctionEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}
object AnonymousAccessToProtectedFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object AnonymousAccessToProtectedFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object AnonymousAccessToProtectedFunctionEx {
  def unapply(o : org.sireum.bakar.xml.AnonymousAccessToProtectedFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}
object PrivateTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.PrivateTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object PrivateTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.PrivateTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object PrivateTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.PrivateTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ()
    )
  }
}
object TaggedPrivateTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.TaggedPrivateTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object TaggedPrivateTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.TaggedPrivateTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object TaggedPrivateTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.TaggedPrivateTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ()
    )
  }
}
object PrivateExtensionDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.PrivateExtensionDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object PrivateExtensionDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.PrivateExtensionDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object PrivateExtensionDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.PrivateExtensionDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getAncestorSubtypeIndicationQ(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object TaskDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.TaskDefinition) = {
    Some(
      o.getSloc(),
      o.getVisiblePartItemsQl(),
      o.getPrivatePartItemsQl()
    )
  }
}
object ProtectedDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.ProtectedDefinition) = {
    Some(
      o.getSloc(),
      o.getVisiblePartItemsQl(),
      o.getPrivatePartItemsQl()
    )
  }
}
object FormalPrivateTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FormalPrivateTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object FormalPrivateTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.FormalPrivateTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object FormalPrivateTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalPrivateTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ()
    )
  }
}
object FormalTaggedPrivateTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object FormalTaggedPrivateTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object FormalTaggedPrivateTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalTaggedPrivateTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ()
    )
  }
}
object FormalDerivedTypeDefinition_HasAbstractQEx {
  def unapply(o : org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasAbstractQ) = {
    Some(
      o.getHasAbstract()
    )
  }
}
object FormalDerivedTypeDefinition_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object PrivateEx {
  def unapply(o : org.sireum.bakar.xml.Private) = {
    Some(
      o.getSloc()
    )
  }
}
object FormalDerivedTypeDefinition_HasPrivateQEx {
  def unapply(o : org.sireum.bakar.xml.FormalDerivedTypeDefinition.HasPrivateQ) = {
    Some(
      o.getHasPrivate()
    )
  }
}
object FormalDerivedTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalDerivedTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getSubtypeMarkQ(),
      o.getDefinitionInterfaceListQl(),
      o.getHasPrivateQ()
    )
  }
}
object FormalDiscreteTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalDiscreteTypeDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object FormalSignedIntegerTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalSignedIntegerTypeDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object FormalModularTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalModularTypeDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object FormalFloatingPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalFloatingPointDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object FormalOrdinaryFixedPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalOrdinaryFixedPointDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object FormalDecimalFixedPointDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalDecimalFixedPointDefinition) = {
    Some(
      o.getSloc()
    )
  }
}
object FormalOrdinaryInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalOrdinaryInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object FormalLimitedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalLimitedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object FormalTaskInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalTaskInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object FormalProtectedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalProtectedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object FormalSynchronizedInterfaceEx {
  def unapply(o : org.sireum.bakar.xml.FormalSynchronizedInterface) = {
    Some(
      o.getSloc(),
      o.getDefinitionInterfaceListQl()
    )
  }
}
object FormalUnconstrainedArrayDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalUnconstrainedArrayDefinition) = {
    Some(
      o.getSloc(),
      o.getIndexSubtypeDefinitionsQl(),
      o.getArrayComponentDefinitionQ()
    )
  }
}
object FormalConstrainedArrayDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.FormalConstrainedArrayDefinition) = {
    Some(
      o.getSloc(),
      o.getDiscreteSubtypeDefinitionsQl(),
      o.getArrayComponentDefinitionQ()
    )
  }
}
object FormalPoolSpecificAccessToVariable_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalPoolSpecificAccessToVariable.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object FormalPoolSpecificAccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.FormalPoolSpecificAccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}
object FormalAccessToVariable_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToVariable.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object FormalAccessToVariableEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToVariable) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}
object FormalAccessToConstant_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToConstant.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object FormalAccessToConstantEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToConstant) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToObjectDefinitionQ()
    )
  }
}
object FormalAccessToProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object FormalAccessToProcedureEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}
object FormalAccessToProtectedProcedure_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedProcedure.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object FormalAccessToProtectedProcedureEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedProcedure) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl()
    )
  }
}
object FormalAccessToFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object FormalAccessToFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object FormalAccessToFunctionEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}
object FormalAccessToProtectedFunction_HasNullExclusionQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedFunction.HasNullExclusionQ) = {
    Some(
      o.getHasNullExclusion()
    )
  }
}
object FormalAccessToProtectedFunction_IsNotNullReturnQEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedFunction.IsNotNullReturnQ) = {
    Some(
      o.getIsNotNullReturn()
    )
  }
}
object FormalAccessToProtectedFunctionEx {
  def unapply(o : org.sireum.bakar.xml.FormalAccessToProtectedFunction) = {
    Some(
      o.getSloc(),
      o.getHasNullExclusionQ(),
      o.getAccessToSubprogramParameterProfileQl(),
      o.getIsNotNullReturnQ(),
      o.getAccessToFunctionResultProfileQ()
    )
  }
}
object AspectSpecificationEx {
  def unapply(o : org.sireum.bakar.xml.AspectSpecification) = {
    Some(
      o.getSloc(),
      o.getAspectMarkQ(),
      o.getAspectDefinitionQ()
    )
  }
}
object PragmaArgumentAssociationEx {
  def unapply(o : org.sireum.bakar.xml.PragmaArgumentAssociation) = {
    Some(
      o.getSloc(),
      o.getFormalParameterQ(),
      o.getActualParameterQ()
    )
  }
}
object DiscriminantAssociationEx {
  def unapply(o : org.sireum.bakar.xml.DiscriminantAssociation) = {
    Some(
      o.getSloc(),
      o.getDiscriminantSelectorNamesQl(),
      o.getDiscriminantExpressionQ()
    )
  }
}
object RecordComponentAssociationEx {
  def unapply(o : org.sireum.bakar.xml.RecordComponentAssociation) = {
    Some(
      o.getSloc(),
      o.getRecordComponentChoicesQl(),
      o.getComponentExpressionQ()
    )
  }
}
object ArrayComponentAssociationEx {
  def unapply(o : org.sireum.bakar.xml.ArrayComponentAssociation) = {
    Some(
      o.getSloc(),
      o.getArrayComponentChoicesQl(),
      o.getComponentExpressionQ()
    )
  }
}
object ParameterAssociationEx {
  def unapply(o : org.sireum.bakar.xml.ParameterAssociation) = {
    Some(
      o.getSloc(),
      o.getFormalParameterQ(),
      o.getActualParameterQ()
    )
  }
}
object GenericAssociationEx {
  def unapply(o : org.sireum.bakar.xml.GenericAssociation) = {
    Some(
      o.getSloc(),
      o.getFormalParameterQ(),
      o.getActualParameterQ()
    )
  }
}
object NullStatementEx {
  def unapply(o : org.sireum.bakar.xml.NullStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl()
    )
  }
}
object AssignmentStatementEx {
  def unapply(o : org.sireum.bakar.xml.AssignmentStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getAssignmentVariableNameQ(),
      o.getAssignmentExpressionQ()
    )
  }
}
object PathListEx {
  def unapply(o : org.sireum.bakar.xml.PathList) = {
    Some(
      o.getPaths()
    )
  }
}
object IfStatementEx {
  def unapply(o : org.sireum.bakar.xml.IfStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}
object CaseStatementEx {
  def unapply(o : org.sireum.bakar.xml.CaseStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getCaseExpressionQ(),
      o.getStatementPathsQl()
    )
  }
}
object LoopStatementEx {
  def unapply(o : org.sireum.bakar.xml.LoopStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementIdentifierQ(),
      o.getLoopStatementsQl()
    )
  }
}
object WhileLoopStatementEx {
  def unapply(o : org.sireum.bakar.xml.WhileLoopStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementIdentifierQ(),
      o.getWhileConditionQ(),
      o.getLoopStatementsQl()
    )
  }
}
object ForLoopStatementEx {
  def unapply(o : org.sireum.bakar.xml.ForLoopStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementIdentifierQ(),
      o.getForLoopParameterSpecificationQ(),
      o.getLoopStatementsQl()
    )
  }
}
object BlockStatementEx {
  def unapply(o : org.sireum.bakar.xml.BlockStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementIdentifierQ(),
      o.getBlockDeclarativeItemsQl(),
      o.getBlockStatementsQl(),
      o.getBlockExceptionHandlersQl()
    )
  }
}
object ExitStatementEx {
  def unapply(o : org.sireum.bakar.xml.ExitStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getExitLoopNameQ(),
      o.getExitConditionQ()
    )
  }
}
object GotoStatementEx {
  def unapply(o : org.sireum.bakar.xml.GotoStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getGotoLabelQ()
    )
  }
}
object ProcedureCallStatement_IsPrefixNotationQEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureCallStatement.IsPrefixNotationQ) = {
    Some(
      o.getIsPrefixNotation()
    )
  }
}
object ProcedureCallStatementEx {
  def unapply(o : org.sireum.bakar.xml.ProcedureCallStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getCalledNameQ(),
      o.getCallStatementParametersQl(),
      o.getIsPrefixNotationQ()
    )
  }
}
object ReturnStatementEx {
  def unapply(o : org.sireum.bakar.xml.ReturnStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getReturnExpressionQ()
    )
  }
}
object ExtendedReturnStatementEx {
  def unapply(o : org.sireum.bakar.xml.ExtendedReturnStatement) = {
    Some(
      o.getSloc(),
      o.getReturnObjectDeclarationQ(),
      o.getExtendedReturnStatementsQl(),
      o.getExtendedReturnExceptionHandlersQl()
    )
  }
}
object AcceptStatementEx {
  def unapply(o : org.sireum.bakar.xml.AcceptStatement) = {
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
}
object EntryCallStatementEx {
  def unapply(o : org.sireum.bakar.xml.EntryCallStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getCalledNameQ(),
      o.getCallStatementParametersQl()
    )
  }
}
object RequeueStatementEx {
  def unapply(o : org.sireum.bakar.xml.RequeueStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getRequeueEntryNameQ()
    )
  }
}
object RequeueStatementWithAbortEx {
  def unapply(o : org.sireum.bakar.xml.RequeueStatementWithAbort) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getRequeueEntryNameQ()
    )
  }
}
object DelayUntilStatementEx {
  def unapply(o : org.sireum.bakar.xml.DelayUntilStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getDelayExpressionQ()
    )
  }
}
object DelayRelativeStatementEx {
  def unapply(o : org.sireum.bakar.xml.DelayRelativeStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getDelayExpressionQ()
    )
  }
}
object TerminateAlternativeStatementEx {
  def unapply(o : org.sireum.bakar.xml.TerminateAlternativeStatement) = {
    Some(
      o.getSloc()
    )
  }
}
object SelectiveAcceptStatementEx {
  def unapply(o : org.sireum.bakar.xml.SelectiveAcceptStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}
object TimedEntryCallStatementEx {
  def unapply(o : org.sireum.bakar.xml.TimedEntryCallStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}
object ConditionalEntryCallStatementEx {
  def unapply(o : org.sireum.bakar.xml.ConditionalEntryCallStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}
object AsynchronousSelectStatementEx {
  def unapply(o : org.sireum.bakar.xml.AsynchronousSelectStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getStatementPathsQl()
    )
  }
}
object AbortStatementEx {
  def unapply(o : org.sireum.bakar.xml.AbortStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getAbortedTasksQl()
    )
  }
}
object RaiseStatementEx {
  def unapply(o : org.sireum.bakar.xml.RaiseStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getRaisedExceptionQ(),
      o.getAssociatedMessageQ()
    )
  }
}
object CodeStatementEx {
  def unapply(o : org.sireum.bakar.xml.CodeStatement) = {
    Some(
      o.getSloc(),
      o.getLabelNamesQl(),
      o.getQualifiedExpressionQ()
    )
  }
}
object IfPathEx {
  def unapply(o : org.sireum.bakar.xml.IfPath) = {
    Some(
      o.getSloc(),
      o.getConditionExpressionQ(),
      o.getSequenceOfStatementsQl()
    )
  }
}
object ElsifPathEx {
  def unapply(o : org.sireum.bakar.xml.ElsifPath) = {
    Some(
      o.getSloc(),
      o.getConditionExpressionQ(),
      o.getSequenceOfStatementsQl()
    )
  }
}
object ElsePathEx {
  def unapply(o : org.sireum.bakar.xml.ElsePath) = {
    Some(
      o.getSloc(),
      o.getSequenceOfStatementsQl()
    )
  }
}
object CasePathEx {
  def unapply(o : org.sireum.bakar.xml.CasePath) = {
    Some(
      o.getSloc(),
      o.getCasePathAlternativeChoicesQl(),
      o.getSequenceOfStatementsQl()
    )
  }
}
object SelectPathEx {
  def unapply(o : org.sireum.bakar.xml.SelectPath) = {
    Some(
      o.getSloc(),
      o.getGuardQ(),
      o.getSequenceOfStatementsQl()
    )
  }
}
object OrPathEx {
  def unapply(o : org.sireum.bakar.xml.OrPath) = {
    Some(
      o.getSloc(),
      o.getGuardQ(),
      o.getSequenceOfStatementsQl()
    )
  }
}
object ThenAbortPathEx {
  def unapply(o : org.sireum.bakar.xml.ThenAbortPath) = {
    Some(
      o.getSloc(),
      o.getSequenceOfStatementsQl()
    )
  }
}
object CaseExpressionPathEx {
  def unapply(o : org.sireum.bakar.xml.CaseExpressionPath) = {
    Some(
      o.getSloc(),
      o.getCasePathAlternativeChoicesQl(),
      o.getDependentExpressionQ()
    )
  }
}
object IfExpressionPathEx {
  def unapply(o : org.sireum.bakar.xml.IfExpressionPath) = {
    Some(
      o.getSloc(),
      o.getConditionExpressionQ(),
      o.getDependentExpressionQ()
    )
  }
}
object ElsifExpressionPathEx {
  def unapply(o : org.sireum.bakar.xml.ElsifExpressionPath) = {
    Some(
      o.getSloc(),
      o.getConditionExpressionQ(),
      o.getDependentExpressionQ()
    )
  }
}
object ElseExpressionPathEx {
  def unapply(o : org.sireum.bakar.xml.ElseExpressionPath) = {
    Some(
      o.getSloc(),
      o.getDependentExpressionQ()
    )
  }
}
object NameListEx {
  def unapply(o : org.sireum.bakar.xml.NameList) = {
    Some(
      o.getNames()
    )
  }
}
object UsePackageClauseEx {
  def unapply(o : org.sireum.bakar.xml.UsePackageClause) = {
    Some(
      o.getSloc(),
      o.getClauseNamesQl()
    )
  }
}
object UseTypeClauseEx {
  def unapply(o : org.sireum.bakar.xml.UseTypeClause) = {
    Some(
      o.getSloc(),
      o.getClauseNamesQl()
    )
  }
}
object UseAllTypeClauseEx {
  def unapply(o : org.sireum.bakar.xml.UseAllTypeClause) = {
    Some(
      o.getSloc(),
      o.getClauseNamesQl()
    )
  }
}
object WithClause_HasLimitedQEx {
  def unapply(o : org.sireum.bakar.xml.WithClause.HasLimitedQ) = {
    Some(
      o.getHasLimited()
    )
  }
}
object WithClause_HasPrivateQEx {
  def unapply(o : org.sireum.bakar.xml.WithClause.HasPrivateQ) = {
    Some(
      o.getHasPrivate()
    )
  }
}
object WithClauseEx {
  def unapply(o : org.sireum.bakar.xml.WithClause) = {
    Some(
      o.getSloc(),
      o.getHasLimitedQ(),
      o.getHasPrivateQ(),
      o.getClauseNamesQl()
    )
  }
}
object AttributeDefinitionClauseEx {
  def unapply(o : org.sireum.bakar.xml.AttributeDefinitionClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getRepresentationClauseExpressionQ()
    )
  }
}
object EnumerationRepresentationClauseEx {
  def unapply(o : org.sireum.bakar.xml.EnumerationRepresentationClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getRepresentationClauseExpressionQ()
    )
  }
}
object ComponentClauseListEx {
  def unapply(o : org.sireum.bakar.xml.ComponentClauseList) = {
    Some(
      o.getComponentClauses()
    )
  }
}
object RecordRepresentationClauseEx {
  def unapply(o : org.sireum.bakar.xml.RecordRepresentationClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getModClauseExpressionQ(),
      o.getComponentClausesQl()
    )
  }
}
object AtClauseEx {
  def unapply(o : org.sireum.bakar.xml.AtClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getRepresentationClauseExpressionQ()
    )
  }
}
object ComponentClauseEx {
  def unapply(o : org.sireum.bakar.xml.ComponentClause) = {
    Some(
      o.getSloc(),
      o.getRepresentationClauseNameQ(),
      o.getComponentClausePositionQ(),
      o.getComponentClauseRangeQ()
    )
  }
}
object ExceptionHandlerEx {
  def unapply(o : org.sireum.bakar.xml.ExceptionHandler) = {
    Some(
      o.getSloc(),
      o.getChoiceParameterSpecificationQ(),
      o.getExceptionChoicesQl(),
      o.getHandlerStatementsQl()
    )
  }
}
object ElementClassEx {
  def unapply(o : org.sireum.bakar.xml.ElementClass) = {
    Some(
      o.getElement()
    )
  }
}
object DerivedTypeDefinitionEx {
  def unapply(o : org.sireum.bakar.xml.DerivedTypeDefinition) = {
    Some(
      o.getSloc(),
      o.getHasAbstractQ(),
      o.getHasLimitedQ(),
      o.getParentSubtypeIndicationQ()
    )
  }
}
object DefinitionClassEx {
  def unapply(o : org.sireum.bakar.xml.DefinitionClass) = {
    Some(
      o.getDefinition()
    )
  }
}
object OrdinaryTypeDeclarationEx {
  def unapply(o : org.sireum.bakar.xml.OrdinaryTypeDeclaration) = {
    Some(
      o.getSloc(),
      o.getNamesQl(),
      o.getDiscriminantPartQ(),
      o.getTypeDeclarationViewQ(),
      o.getAspectSpecificationsQl()
    )
  }
}
object DeclarationClassEx {
  def unapply(o : org.sireum.bakar.xml.DeclarationClass) = {
    Some(
      o.getDeclaration()
    )
  }
}
object CompilationUnitEx {
  def unapply(o : org.sireum.bakar.xml.CompilationUnit) = {
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
}