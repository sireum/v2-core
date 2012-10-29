/******************************************************************************
 * Copyright (c) 2009 Belt, Kansas State University, and others.              *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar;

import java.util.List;

public interface ISparkAttribute {

  public static enum AttributePrefixType {
    SCALAR_SUBTYPE, //
    MODULAR_SUBTYPE, //
    FIXED_POINT_SUBTYPE, //
    FLOATING_POINT_SUBTYPE, //    
    ARRAY_OBJECT, //
    ARRAY_TYPE, //
    ARRAY_SUBTYPE_CONSTRAINED, //
    ARRAY_SUBTYPE
  }

  public static enum AttributeReturnType {
    BOOLEAN, // this is the actual predefined Ada/Spark Boolean 
    UNIVERSAL_INTEGER, // anonymous type; see Barnes 5.4
    UNIVERSAL_REAL, // anonymous type; see Barnes 5.4
    INDEX_TYPE, // the type the attribute is being applied to
    INDEX_TYPE_BASE
    // the unconstrained subtype of the index type 
  }

  public AttributeReturnType getTheReturnType();

  public List<AttributePrefixType> getTheValidPrefixTypes();

  public boolean isStatic();

  public boolean throwsConstraintError();
}
