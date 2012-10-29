package RecordShape 
is
  type Object is tagged
  record
    X_Coord, Y_Coord : Float;
  end record;
  
  anObject: constant Object := Object'(0.0, 0.0);
  
  anInt: constant := 3;
end RecordShape;


--# inherit RecordShape;
package RecordShape.CirclePackage
is
  type Circle is new RecordShape.Object with
  record
    Radius: Float;
  end record;
  
  aCircle: constant Circle := Circle'(Radius => 1.0, X_Coord => 1.0, Y_Coord => 1.0);
end RecordShape.CirclePackage;


--# inherit RecordShape;
package RecordShape.TrianglePackage
is
  type Triangle is new RecordShape.Object with
  record
    A, B, C: Float;
  end record;
  
  aTriangle: constant Triangle := Triangle'(0.0, 0.0, 3.0, 4.0, 5.0);
end RecordShape.TrianglePackage;
