--# inherit SimpleMath;
package SimpleMath.PTF is
   function Fact (N : Natural) return Natural;
   --# return R => (N = 0 -> R = 1) and
   --#             (N > 0 -> R = N*Fact(N-1));
   function Fact_Measure (N : Natural) return Natural;
   --# return N;

end SimpleMath.PTF;

package body SimpleMath.PTF is
   function Fact (N : Natural) return Natural is
      --# hide Fact
   begin
      if (N = 0) then
         R := 1;
      else
         R := N * Fact(N-1);
      end if;
      return R;
   end Fact;

   function Fact_Measure (N : Natural) return Natural is
   begin
      return N;
   end Fact_Measure;

end SimpleMath.PTF;
