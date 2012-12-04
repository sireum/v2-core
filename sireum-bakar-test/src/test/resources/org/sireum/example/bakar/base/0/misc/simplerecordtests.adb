-- Simple example for records

package body SimpleRecordTests is

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
   -- are NOT merged inside of procedures (e.g., since O1 only depends
   -- on I1 and not I2 as well.  Flows are only merged
   -- at procedure boundaries (derives statements cannot mention
   -- record components).

 procedure Test02(I1, I2 : in Float; O1, O2 : out Float)
     --# derives O1 from I1
     --#       & O2 from I2;
   is
      Q : Object;
   begin
      Q.X := I1;
      Q.Y := I2;
      O1 := Q.X;
      O2 := Q.Y;
   end Test02;

----------------------------------------------------------------
   -- nested records (with a path "." navigation to fields)

   -- This example illustrates that flows through nested record fields
   -- are NOT merged inside of procedures (e.g., since O1 only depends
   -- on I1 and not I2 as well.  Flows are only merged
   -- at procedure boundaries (derives statements cannot mention
   -- record components).

 procedure Test03(I1, I2 : in Float; O1, O2 : out Float)
     --# derives O1 from I1
     --#       & O2 from I2;
   is
      Q : NestedObject;
   begin
      Q.O.X := I1;
      Q.O.Y := I2;
      O1 := Q.O.X;
      O2 := Q.O.Y;
   end Test03;

 end SimpleRecordTests;




