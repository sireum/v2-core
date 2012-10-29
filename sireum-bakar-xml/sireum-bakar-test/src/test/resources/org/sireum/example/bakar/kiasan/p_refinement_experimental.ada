package P
--# own Y; State : StateType;
--# initializes Y, State;
is
  Y: Integer;
  
  --# type StateType is abstract;
  
  --# function nonZero(Z : StateType) return Boolean;
  
  procedure Q;
  --# global in out Y, State;
  --# derives State from State &
  --#         Y from Y;
  --# pre Y < 1 and nonZero(State);
end P;

package body P
  --# own State is X;
is
  X: Integer;

  procedure Q
  --# global in out Y, X;
  --# derives X from X &
  --#         Y from Y;
  --# post Y < 1 and
  --#      X > 1;
  is
  begin
    Y := Y + 1;
    X := X + 1;
  end Q;
  
begin
   Y := 1;
   X := 1;
end P;


