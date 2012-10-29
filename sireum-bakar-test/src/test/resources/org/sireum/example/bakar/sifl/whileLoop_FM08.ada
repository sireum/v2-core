package WhileLoop_Test
  --#own I1, V1;
  --#initializes I1, V1;
is 
   I1: Integer := 1;
   V1: Integer := 0;
   
   procedure WhileLoop (X, H : in Integer; R : in out Integer);
   --#global in I1, V1;
   --# derives R from R, X, H, I1, V1;
end WhileLoop_Test;

package body WhileLoop_Test is

   procedure WhileLoop (X, H : in Integer; R : in out Integer)
   is
      I: Integer;
      V: Integer;
   begin
      I := I1;
      V := V1;
      while I < 7 loop
	 if I mod 2 = 0
        then
            R := R + V;
            V := V + H;
        else
            V := X;
        end if;
        I := I + 1;
      end loop;
   end WhileLoop;
   
end WhileLoop_Test;
