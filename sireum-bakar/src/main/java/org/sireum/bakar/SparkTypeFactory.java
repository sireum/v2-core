/******************************************************************************
 * Copyright (c) 2009 Belt, Kansas State University, and others.              *
 * All rights reserved. This program and the accompanying materials           *
 * are made available under the terms of the Eclipse Public License v1.0      *
 * which accompanies this distribution, and is available at                   *
 * http://www.eclipse.org/legal/epl-v10.html                                  *
 ******************************************************************************/

package org.sireum.bakar;

public class SparkTypeFactory {

  public enum PrimitiveType {
    INTEGER {

      @Override
      public String getPilarSparkName() {
        return PrimitiveType.prefix + "Integer";
      }

      @Override
      public String getSparkName() {
        return "Integer";
      }

    },
    NATURAL {

      @Override
      public String getPilarSparkName() {
        return PrimitiveType.prefix + "Natural";
      }

      @Override
      public String getSparkName() {
        return "Natural";
      }
    },
    POSITIVE {

      @Override
      public String getPilarSparkName() {
        return PrimitiveType.prefix + "Positive";
      }

      @Override
      public String getSparkName() {
        return "Positive";
      }
    },
    LONG_INTEGER {

      @Override
      public String getPilarSparkName() {
        return PrimitiveType.prefix + "Long_Integer";
      }

      @Override
      public String getSparkName() {
        return "Long_Integer";
      }
    },
    FLOAT {

      @Override
      public String getPilarSparkName() {
        return PrimitiveType.prefix + "Float";
      }

      @Override
      public String getSparkName() {
        return "Float";
      }
    },
    BOOLEAN {

      @Override
      public String getPilarSparkName() {
        return PrimitiveType.prefix + "Boolean";
      }

      @Override
      public String getSparkName() {
        return "Boolean";
      }
    },
    CHARACTER {

      @Override
      public String getPilarSparkName() {
        return PrimitiveType.prefix + "Character";
      }

      @Override
      public String getSparkName() {
        return "Character";
      }
    },
    STRING {

      @Override
      public String getPilarSparkName() {
        return PrimitiveType.prefix + "String";
      }

      @Override
      public String getSparkName() {
        return "String";
      }
    };

    public static String prefix = SparkUtil.SPARK_STANDARD_PACKAGE
        + SparkUtil.PILAR_PACKAGE_SEPARATOR;

    public String getKey() {
      return getSparkName().toLowerCase();
    }

    public abstract String getPilarSparkName();

    public abstract String getSparkName();
  }
}
