package The_Stack
--# own State : Stack_Type;   -- abstract variable
--# initializes State;
is
   --# type Stack_Type is abstract;

   --# function Not_Full(S : Stack_Type) return Boolean;
   --# function Not_Empty(S : Stack_Type) return Boolean;
   --# function Append(S : Stack_Type; X : Integer) return Stack_Type;

   procedure Push(X : in Integer);
   --# global in out State;
   --# derives State from State, X;
   --# pre Not_Full(State);
   --# post State = Append(State~, X);

   procedure Pop(X : out Integer);
   --# global in out State;
   --# derives State, X from State;
   --# pre Not_Empty(State);
   --# post State~ = Append(State, X);

end The_Stack;

package body The_Stack
--# own State is S, Pointer;  -- refinement definition
is
   Stack_Size : constant := 100;
   type Pointer_Range is range 0 .. Stack_Size;
   subtype Index_Range is Pointer_Range range 1..Stack_Size;
   type Vector is array(Index_Range) of Integer;
   S : Vector;
   Pointer : Pointer_Range;

   procedure Push(X : in Integer)
   --# global in out S, Pointer;
   --# derives S       from S, Pointer, X &
   --#         Pointer from Pointer;
   --# pre Pointer < Stack_Size;
   --# post Pointer = Pointer~ + 1 and
   --#      S = S~[Pointer => X];
   is
   begin
      Pointer := Pointer + 1;
      S(Pointer) := X;
   end Push;

   procedure Pop(X : out Integer)
   --# global in S; in out Pointer;
   --# derives Pointer from Pointer &
   --#         X       from S, Pointer;
   --# pre Pointer /= 0;
   --# post Pointer = Pointer~ - 1 and
   --#      X = S(Pointer~);
   is
   begin
      X := S(Pointer);
      Pointer := Pointer - 1;
   end Pop;

begin  -- initialization
   Pointer := 0;
   S := Vector'(Index_Range => 0);
end The_Stack;

