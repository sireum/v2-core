package Stacks is

   type Stack is private;

   function Is_Empty(S : Stack) return Boolean;
   function Is_Full(S : Stack) return Boolean;

   procedure Clear(S : out Stack);
   --# derives S from ;

   procedure Push(S : in out Stack; X : in Integer);
   --# derives S from S, X;

   procedure Pop(S : in out Stack; X : out Integer);
   --# derives S, X from S;

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
   function Is_Empty(S : Stack) return Boolean is
   begin
      return S.Stack_Pointer = 0;
   end Is_Empty;

   function Is_Full(S : Stack) return Boolean is
   begin
      return S.Stack_Pointer = Stack_Size;
   end Is_Full;

   procedure Clear(S : out Stack) is
   begin
      S.Stack_Pointer := 0;
      S.Stack_Vector := Vector'(Index_Range => 0);
   end Clear;

   procedure Push(S : in out Stack; X : in Integer) is
   begin
      S.Stack_Pointer := S.Stack_Pointer + 1;
      S.Stack_Vector(S.Stack_Pointer) := X;
   end Push;

   procedure Pop(S : in out Stack; X : out Integer) is
   begin
      X := S.Stack_Vector(S.Stack_Pointer);
      S.Stack_Pointer := S.Stack_Pointer - 1;
   end Pop;
end Stacks;

