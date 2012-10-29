package body Dependence_Test_Suite_02 is

   procedure Success0 (O : out Integer)
   is
   begin
      O := 5;
   end Success0;

   -- direct copy from input to output
   procedure Success1 (I : in Integer; O : out Integer)
   is
   begin
      O := I;
   end Success1;

   -- simple indirect copy through a local variable
   procedure Success2 (I : in Integer; O : out Integer)
   is
      L : Integer;
   begin
      L := I;
      O := L;
   end Success2;


   -- simple while loop for test (non)-loop-carried dependences
   procedure Success14 (I1 : in Integer; O1 : out Integer)
   is
      Sum, J, L1, L2 : Integer;
   begin
      J := 1;
      Sum := 0;
      L2 := 0;
      while J <= I1 loop
         Sum := Sum + J; -- Sum has one loop-carried, and one non-carried dep.
         L1 := Sum; -- non-loop carried
         L2 := L1;  -- non-loop carried
         J := J + 1;
      end loop;

      O1 := Sum + L2;
   end Success14;


end Dependence_Test_Suite_02;