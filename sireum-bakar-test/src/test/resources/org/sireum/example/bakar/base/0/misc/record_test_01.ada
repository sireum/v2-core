package RecordTest01 
--# own aNestedObject, anObject;
--# initializes aNestedObject, anObject;
is

  type Object is
  record
    X, Y : Integer;
  end record;

  type NestedObject is
  record
    O : Object;
  end record;

  procedure Test01(Q : in out Object);
  --# derives Q from Q ;
  
  aNestedObject: NestedObject;
   
  anObject: Object;
  
end RecordTest01;

package body RecordTest01 is

  procedure Test01(Q : in out Object)
  is
    T : Integer;
  begin
    T := Q.X;
    Q.X := Q.Y;
    Q.Y := T;
  end Test01;

  procedure Test02(I1, I2 : in Integer; O1, O2 : out Integer)
  --# derives O1 from I1
  --#       & O2 from I2;
  is
    Q : NestedObject;
  begin
    Q.O.X := I1;
    Q.O.Y := I2;
    O1 := Q.O.X;
    O2 := Q.O.Y;
  end Test02;

begin
  anObject := Object'(1, 2); -- positional
  aNestedObject := NestedObject'(O => Object'(1, 2)); -- named and positional
end RecordTest01;

