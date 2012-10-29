package Inc_Upto_Last is
   procedure Inc(I: in out Integer);
   --# derives I from I;
   --# pre I < Integer'Last;
   --# post I = I~ + 1;
end Inc_Upto_Last;

package body Inc_Upto_Last is
   procedure Inc(I: in out Integer) is
   begin
      I := I+1;
   end Inc;
end Inc_Upto_Last;
