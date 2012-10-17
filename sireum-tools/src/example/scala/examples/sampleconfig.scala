package org.sireum.pipeline.examples

import org.sireum.pilar.ast.Model
import org.sireum.util.ResourceUri
import org.sireum.pipeline._

case class C1(
  @Input 
  @Produce 
  @Output
  var sources : Either[String, ResourceUri])

case class C3(
  @Input 
  @Produce
  var sources : Either[String, ResourceUri])

case class C2(
  @Produce
  var models : ISeq[Model],

  @Produce
  var messages : ISeq[String],

  @Input 
  @Consume(Array(classOf[C1], classOf[C3]))
  @Output 
  var sources : Either[ResourceUri, String])
