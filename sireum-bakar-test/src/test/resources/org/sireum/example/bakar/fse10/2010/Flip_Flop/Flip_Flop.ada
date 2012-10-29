package Flip_Flop
  --# own State: Bool;
  --# initializes State;
is
   --  The following two declarations are necessary only because Spark does
   --  not allow own variables of concrete types to be refined. Hopefully this
   --  can be added soon.
   --
   --# type Bool is abstract;
   --# function B(X: Bool) return Boolean;

   function Read return Boolean;
   --# global in State;
   --# return B(State);

   procedure Flip;
   --# global in out State;
   --# derives State from State;
   --# post B(State) = not B(State~);

end Flip_Flop;

package body Flip_Flop
  --# own State is N;
is
   N: Natural;

   --# function Represents(N: Natural) return Boolean;
   --! return not (N = 0);

   function Read return Boolean
     --# global in N;
     --# return not (N = 0);
   is begin
      return not (N = 0);
   end Read;

   procedure Flip
     --# global in out N;
     --# derives N from N;
     --# post (N~ = 0 -> N = 1) and
     --#      (N~ /= 0 -> N = 0);
   is begin
      if (N = 0) then
         N := 1;
      else
         N := 0;
      end if;
   end Flip;
begin
   N := 0;
end Flip_Flop;
