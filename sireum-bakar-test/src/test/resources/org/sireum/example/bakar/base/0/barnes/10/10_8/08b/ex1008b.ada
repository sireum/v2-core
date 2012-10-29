-- A broken GCD calculator
package P is

   procedure GCD(M, N : in Integer; G : out Integer);
   --# derives G from M,N;

end P;

package body P is

   procedure GCD(M, N : in Integer; G : out Integer)
   is
      C, D : Integer;
      R : Integer;
   begin
      C := M; D := N;     -- 1,2
      while D /= M loop   -- 3  wrong code but no flow errors
         R := C rem D;    -- 4
         C := D; D := R;  -- 5, 6
      end loop;
      G := C;             -- 7
   end GCD;

end P;
