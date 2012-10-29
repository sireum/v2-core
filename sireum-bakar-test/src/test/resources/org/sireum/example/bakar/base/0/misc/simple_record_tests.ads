package SimpleRecordTests is

type Object is
      record
         X, Y : Float;
      end record;

type NestedObject is
      record
         O : Object;
      end record;


procedure Test01(Q : in out Object);
   --# derives Q from Q ;
end SimpleRecordTests;

