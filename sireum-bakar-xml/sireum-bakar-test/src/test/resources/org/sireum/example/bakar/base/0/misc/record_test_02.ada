package RecordTest02 is

type Object is
      record
         X, Y : Float;
      end record;

-- type NestedObject is
--      record
--         O : Object;
--      end record;


procedure Test01(Q : in out Object);
   --# derives Q from Q ;

procedure Test02(I1, I2 : in Float; O1, O2 : out Float);
 --# derives O1 from I1
 --#       & O2 from I2;

end RecordTest02;



package body RecordTest02 is

-----------------------------------------------------------------
   -- Simple example that swaps the values of an Object record.

   procedure Test01(Q : in out Object)
   is
      T : Float;
   begin
      T := Q.X;
      Q.X := Q.Y;
      Q.Y := T;
   end Test01;

-----------------------------------------------------------------
   -- This example illustrates that flows through record fields
   -- are NOT merged inside of procs (e.g., since O1 only depends
   -- on I1 and not I2 as well.  Flows are only merged
   -- at proc boundaries (derives statements cannot mention
   -- record components).

 procedure Test02(I1, I2 : in Float; O1, O2 : out Float)
   is
      Q : Object;
   begin
      Q.X := I1;
      Q.Y := I2;
      O1 := Q.X;
      O2 := Q.Y;
   end Test02;

end RecordTest02;

