package SimpleMath is
   --# function Fact (N : Natural) return Natural;

   function Factorial (N : Natural) return Natural;
   --# pre Fact(N) <= Natural'Last;
   --# return Fact (N);
end SimpleMath;

package body SimpleMath is
   function Factorial (N : Natural) return Natural
   is
      Result : Natural := 1;
   begin
      for Term in Integer range 1 .. N loop
         Result := Result * Term;
         --# assert 1 <= Term and Term <= N and
         --#        Result = Fact (Term);
         --!# assert Term > 0 and Result = Fact (Term);
      end loop;
      return Result;
   end Factorial;
end SimpleMath;
