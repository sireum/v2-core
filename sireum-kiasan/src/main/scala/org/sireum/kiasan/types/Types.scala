package org.sireum.kiasan.types

import org.sireum.util.IMap
import org.sireum.util.ResourceUri
import org.sireum.util.math._

trait Type

// Boolean is represented with Integer: false == 0, true <> 0
object IntegerType extends Type

case class ArrayType(
    val min_index: Integer,
    val max_index: Integer,
    val componentType: ResourceUri
    //val componentType: Type
    ) extends Type
        
case class RecordType(
    //val components: IMap[ResourceUri, (Type, fieldname)]
    val components: IMap[ResourceUri, (ResourceUri, String)]
    ) extends Type

