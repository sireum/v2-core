package Dependence_Test_Suite
is
   procedure Success0 (O : out Integer);
   --# derives O from ;
end Dependence_Test_Suite;

package body Dependence_Test_Suite is

   procedure Success0 (O : out Integer)
   is
   begin
      O := 5;
   end Success0;

   -- direct copy from input to output
   procedure Success1 (I : in Integer; O : out Integer)
   --# derives O from I ;
   is
   begin
      O := I;
   end Success1;

   -- simple indirect copy through a local variable
   procedure Success2 (I : in Integer; O : out Integer)
   --# derives O from I ;
   is
      L : Integer;
   begin
      L := I;
      O := L;
   end Success2;

   -- simple indirect copy through a chain of local variables
   procedure Success3 (I : in Integer; O : out Integer)
   --# derives O from I ;
   is
      L1, L2, L3 : Integer;
   begin
      L1 := I;
      L2 := L1;
      L3 := L2;
      O := L3;
   end Success3;

   -- indirect copies from multiple inputs
   procedure Success4 (I1, I2 : in Integer; O : out Integer)
   --# derives O from I1, I2 ;
   is
      L1, L2, L3 : Integer;
   begin
      L1 := I1;
      L2 := I2;
      L3 := L1 + L2;
      O := L3;
   end Success4;

   -- kill of an input variable
   procedure Success5 (I1, I2 : in Integer; O1, O2 : out Integer)
    --# derives O1 from I1
    --#       & O2 from I2 ;
   is
      L1, L2, L3 : Integer;
   begin
      O1 := I2; -- this def is killed; O1 does not derive from I2
      L1 := I1;
      L2 := L1;
      L3 := L1 + L2;
      O2 := O1;
      O1 := L3;
   end Success5;

   -- simple control dependence
   procedure Success6 (I1, I2 : in Integer; O1, O2 : out Integer)
    --# derives O1 from I1
    --#       & O2 from I2 ;
   is
   begin
      O1 := I1;
      if I2 > 5
      then -- note: SPARK is cannot detect both branches give same value
         O2 := 5;
      else
         O2 := 5;
      end if;
   end Success6;

   -- control chained through data dependence for I1
   procedure Success7 (I1, I2 : in Integer; O : out Integer)
    --# derives O from I1, I2;
   is
      L1 : Integer;
   begin
      L1 := I1;
      if L1 > I2
      then
         O := 6;
      else
         O := 7;
      end if;
   end Success7;

   -- mixed control and data dependence
   procedure Success8 (I1, I2 : in Integer; O : out Integer)
   --# derives O from I1, I2;
   is
      L1, L2 : Integer;
   begin
      L1 := I1;
      if L1 > 5
      then
         L2 := 6;
      else
         L2 := I2;
      end if;
      O := L2;
   end Success8;

   -- nested conditionals (transitive control dependence)
   procedure Success9 (I1, I2, I3 : in Integer; O : out Integer)
   --# derives O from I1, I2, I3;
   is
      L1, L2 : Integer;
   begin
      L1 := I1;
      if L1 > 5
      then
         L2 := 6;
      else
         if I2 < 0
         then
            L2 := I3;
         else
            L2 := 7;
         end if;
      end if;
      O := L2;
   end Success9;

      -- nested conditionals, killing definitions
   procedure Success10 (I1, I2, I3 : in Integer; O1, O2 : out Integer)
     --# derives O1 from I1, I2
     --#       & O2 from I3;
   is
      L1, L2 : Integer;
   begin
      L2 := 5;
      L1 := I3;
      O2 := L1;
      L1 := I2; -- kill I3
      if L1 > 5
      then
         if I1 < 0
         then
            L2 := 8;
         else
            L2 := 7;
         end if;
      end if;
      O1 := L2;
   end Success10;

   -- simple while loop, summing values from 1 to I1
   procedure Success11 (I1 : in Integer; O1 : out Integer)
     --# derives O1 from I1;
   is
      Sum, J : Integer;
   begin
      J := 1;
      Sum := 0;
      while J <= I1 loop
         Sum := Sum + J;
         J := J + 1;
      end loop;

      O1 := Sum;
   end Success11;

   -- simple while loop with if in body,
   --   summing values from 1 to I1 but bounded by I2
   procedure Success12 (I1, I2 : in Integer; O1 : out Integer)
     --# derives O1 from I1, I2;
   is
      Sum, J : Integer;
   begin
      J := 1;
      Sum := 0;
      while J <= I1 loop
         Sum := Sum + J;
         if Sum > I2 then -- reset Sum if value exceeds I2
            Sum := 0;
         end if;
         J := J + 1;
      end loop;

      O1 := Sum;
   end Success12;

   -- nested while
   procedure Success13 (I1, I2 : in Integer; O1 : out Integer)
     --# derives O1 from I1, I2;
   is
      Sum, J1, J2 : Integer;
   begin
      J1 := 1;
      J2 := 1;
      Sum := 0;
      while J1 <= I1 loop
         while J2 <= I2 loop
            Sum := Sum + J1*J2;
            J2 := J2 + 1;
         end loop;
         J1 := J1 + 1;
      end loop;

      O1 := Sum;
   end Success13;

   -- simple while loop for test (non)-loop-carried dependences
   procedure Success14 (I1 : in Integer; O1 : out Integer)
     --# derives O1 from I1;
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

   -- simple for loop, summing values from 1 to 10
   -- for loop range expression is constant only
   procedure Success15 (O1 : out Integer)
     --# derives O1 from ;
   is
      Sum : Integer;
   begin
      Sum := 0;
      -- note on SPARK restrictions: J must not be previously declared.
      --       The "for" loop range represents a declaration.
      --       J must not be used outside the body of the for loop
      --       J cannot be assigned in body of loop
      for J in Integer range 1..10 loop
         Sum := Sum + J;
      end loop;

      O1 := Sum;
   end Success15;

   -- simple for loop, summing values from 1 to I1, in *reverse* order
   --  (test keyword "reverse")
   procedure Success16 (I1 : in Integer; O1 : out Integer)
     --# derives O1 from I1;
   is
      Sum : Integer;
   begin
      Sum := 0;
      for J in reverse Integer range 1..I1 loop
         Sum := Sum + J;
      end loop;

      O1 := Sum;
   end Success16;

   -- simple for loop with if in body,
   --   summing values from 1 to I1 but bounded by I2
   procedure Success17 (I1, I2 : in Integer; O1 : out Integer)
     --# derives O1 from I1, I2;
   is
      Sum : Integer;
   begin
      Sum := 0;
      for J in Integer range 1..I1 loop
         Sum := Sum + J;
         if Sum > I2 then -- reset Sum if value exceeds I2
            Sum := 0;
         end if;
      end loop;

      O1 := Sum;
   end Success17;

   -- nested for
   procedure Success18 (I1, I2 : in Integer; O1 : out Integer)
     --# derives O1 from I1, I2;
   is
      Sum : Integer;
   begin
      Sum := 0;
      for J1 in Integer range 1..I1 loop
         for J2 in Integer range 1..I2 loop
            Sum := Sum + J1*J2;
         end loop;
      end loop;

      O1 := Sum;
   end Success18;

   -- expression calculation in loop bounds
   procedure Success19 (I1, I2 : in Integer; O1 : out Integer)
     --# derives O1 from I1, I2;
   is
      Sum : Integer;
   begin
      Sum := 0;
      -- I'm surprised that Sum is allowed in range declaration since
      -- it is updated in the loop.  I assume the assignment to Sum
      -- has no effect on the loop bound (similarly if we were to assign to
      -- I1,I2)
      for J1 in Integer range (Sum+1)..(I1*I2) loop
            Sum := Sum + J1;
      end loop;

      O1 := Sum;
   end Success19;

   -- See Barnes p. 130-133 for a discussion of constraints on loops
   -- and exits.  If an 'exit' has a 'when' clause, the closest enclosing
   -- statement of the 'exit' must appear a loop.  For example, an
   -- exit/when cannot occur inside a conditional.  For an 'exit' without
   -- a 'when', the 'exit can occur in a conditional but it must be the
   -- last statement in the "true" branch.  The conditional must be immediately
   -- enclosed by a loop and have no else/elsif parts.

   -- simple non-schematic loop, summing values from 1 to I1
   -- conditional exit
   -- based on previous example Success11
   procedure Success20 (I1 : in Integer; O1 : out Integer)
   --# derives O1 from I1;
   is
      Sum, J : Integer;
   begin
      J := 1;
      Sum := 0;
      loop
         Sum := Sum + J;
         J := J + 1;
         exit when J >= I1;
      end loop;

      O1 := Sum;
   end Success20;

   -- simple non-schematic loop, summing values from 1 to I1
   -- unconditional exit embedded in a conditional
   -- based on previous example Success11
   procedure Success21 (I1 : in Integer; O1 : out Integer)
   --# derives O1 from I1;
   is
      Sum, J : Integer;
   begin
      J := 1;
      Sum := 0;
      loop
         Sum := Sum + J;
         J := J + 1;
         if J >= I1 then
            exit;
         end if;
      end loop;

      O1 := Sum;
   end Success21;

   -- simple non-schematic loop with if in body,
   --   summing values from 1 to I1 but bounded by I2
   --   based on example Success12 above
   procedure Success22 (I1, I2 : in Integer; O1 : out Integer)
     --# derives O1 from I1, I2;
   is
      Sum, J : Integer;
   begin
      J := 1;
      Sum := 0;
      loop
         exit when J > I1;
         Sum := Sum + J;
         if Sum > I2 then -- reset Sum if value exceeds I2
            Sum := 0;
         end if;
         J := J + 1;
      end loop;

      O1 := Sum;
   end Success22;

   -- nested non-schmetic loops with exits in different positions
   procedure Success23 (I1, I2 : in Integer; O1 : out Integer)
     --# derives O1 from I1, I2;
   is
      Sum, J1, J2 : Integer;
   begin
      J1 := 1;
      J2 := 1;
      Sum := 0;
      loop
         loop
            Sum := Sum + J1*J2;
            exit when J2 > I2;
            J2 := J2 + 1;
         end loop;
         J1 := J1 + 1;
         exit when J1 > I1;
      end loop;

      O1 := Sum;
   end Success23;

   -- simple non-schematic loop, summing values from 1 to I1
   -- multiple conditional exits
   procedure Success24 (I1 : in Integer; O1 : out Integer)
   --# derives O1 from I1;
   is
      Sum, J : Integer;
   begin
      J := 1;
      Sum := 0;
      loop
         Sum := Sum + J;
         exit when (Sum - J) > (100 + J);  -- silly nested expressions
         J := J + 1;
         exit when J > I1;
      end loop;

      O1 := Sum;
   end Success24;

   -- simple non-schematic loop, summing values from 1 to I1
   -- conditional exit with "direct name" used in exit.
   -- Per Barnes p. 131, the name in a direct exit must match
   -- the name of the immediately enclosing loop.  Thus, it provides no
   -- additional functionality (as all 'exit's exit the immediately enclosing
   -- loop).  The name is used for documentation or as a unique id for
   -- a control parameter in verification conditions.
   procedure Success25 (I1 : in Integer; O1 : out Integer)
   --# derives O1 from I1;
   is
      Sum, J : Integer;
   begin
      J := 1;
      Sum := 0;
      MyLoop:
      loop
         Sum := Sum + J;
         J := J + 1;
         exit MyLoop when J >= I1;
      end loop MyLoop; -- in a named loop, must give name at end

      O1 := Sum;
   end Success25;

   -- simple while loop with conditional exit in body
   --   summing values from 1 to I1 but bounded by I2
   procedure Success26 (I1, I2 : in Integer; O1 : out Integer)
     --# derives O1 from I1, I2;
   is
      Sum, J : Integer;
   begin
      J := 1;
      Sum := 0;
      while J <= I1 loop
         Sum := Sum + J;
         exit when Sum > I2;
         J := J + 1;
      end loop;

      O1 := Sum;
   end Success26;

   -- simple for loop with conditional exit in body,
   --   summing values from 1 to I1 but bounded by I2
   procedure Success27 (I1, I2 : in Integer; O1 : out Integer)
     --# derives O1 from I1, I2;
   is
      Sum : Integer;
   begin
      Sum := 0;
      for J in Integer range 1..I1 loop
         Sum := Sum + J;
         exit when Sum > I2;
      end loop;

      O1 := Sum;
   end Success27;

end Dependence_Test_Suite;