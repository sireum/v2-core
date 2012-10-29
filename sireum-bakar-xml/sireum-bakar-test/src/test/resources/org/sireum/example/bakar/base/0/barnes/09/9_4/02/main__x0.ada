
with Go_Between;
--# inherit The_Stack, Go_Between;
--# main_program;
procedure Main
  --# global in out The_Stack.State;
  --# derives The_Stack.State from The_Stack.State;
is
begin
   Go_Between.Use_Stack;
end Main;





