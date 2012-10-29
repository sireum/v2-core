--# inherit Flip_Flop;
package Flip_Flop.PTF
is
   function Represents(N: Natural) return Boolean;
   --# return not (N = 0);

end Flip_Flop.PTF;

package body Flip_Flop.PTF
is
   function Represents(N: Natural) return Boolean
   is begin
      return not (N = 0);
   end Represents;

end Flip_Flop.PTF;
