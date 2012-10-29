package Flip_Flop
  --# own State: Boolean; N: Natural;
  --# initializes State, N;
is

   function Read return Boolean;
   --# global in State;
   --# return State;

   procedure Flip;
   --# global in out State;
   --# derives State from State;
   --# post State = not State~;

-- private

   N: Natural;

   --# function Represents(N: Natural) return Boolean;
   --! return not (N = 0);

end Flip_Flop;
