package Inc_Upto_100 is
   procedure Inc(I: in out Integer);
     --# derives I from I;
     --# pre I < 100;
     --# post I = I~ + 1;
end Inc_Upto_100;

package body Inc_Upto_100 is
   procedure Inc(I: in out Integer) is
   begin
      I := I+1;
   end Inc;
end Inc_Upto_100;
