--
--  Stack example from Barnes -- with no refinement
--
package The_Stack
  --# own S, Pointer;   -- abstract variable
  --# initializes S, Pointer;
is
   Stack_Size : constant := 100;
   type Pointer_Range is range 0 .. Stack_Size;
   subtype Index_Range is Pointer_Range range 1..Stack_Size;
   type Vector is array(Index_Range) of Integer;
   S : Vector;
   Pointer : Pointer_Range;

   procedure Push(X : in Integer);
   --# global in out S, Pointer;
   --# derives S       from S, Pointer, X &
   --#         Pointer from Pointer;
   --# pre Pointer < Stack_Size;
   --# post S = S~[Pointer => X];
   
   procedure Pop(X : out Integer);
   --# global in S; in out Pointer;
   --# derives Pointer from Pointer &
   --#         X       from S, Pointer;
   --# pre Pointer > Index_Range'First;
   --# post X = S(Pointer~);
end The_Stack;

package body The_Stack 
is
   procedure Push(X : in Integer) 
   is
   begin
      Pointer := Pointer + 1;
      S(Pointer) := X;
   end Push;

   procedure Pop(X : out Integer) 
   is
   begin
      X := S(Pointer);
      Pointer := Pointer - 1;
   end Pop;

begin  -- initialization
   Pointer := 0;
   S := Vector'(Index_Range => 0);
end The_Stack;
