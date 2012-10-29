package Stacks is

   type Stack is private;

   procedure Clear(S : out Stack);
   --# derives S from ;

private
   Stack_Size : constant := 100;
   type Pointer_Range is range 0 .. Stack_Size;
   subtype Index_Range is Pointer_Range range 1 .. Stack_Size;
   type Vector is array(Index_Range) of Integer;
   type Stack is
      record
         Stack_Vector : Vector;
         Stack_Pointer : Pointer_Range;
      end record;
end Stacks;

package body Stacks is

   procedure Clear(S : out Stack) is
   begin
      S := Stack'(Stack_Vector  => Vector'(Index_Range => 0),
		  Stack_Pointer => 0);
   end Clear;

end Stacks;

