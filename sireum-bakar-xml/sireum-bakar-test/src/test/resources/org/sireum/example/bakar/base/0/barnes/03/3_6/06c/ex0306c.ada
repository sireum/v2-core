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
      C := M; D := N;
      while D /= 0 loop
         R := C rem D;
         C := D; D := R;
      end loop;
      G := C;
   end GCD;
end P;

