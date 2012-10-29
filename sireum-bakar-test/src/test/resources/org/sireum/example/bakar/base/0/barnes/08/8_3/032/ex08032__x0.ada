package Input_Port
  --# own in Inputs;
is
   procedure Read_From_Port(Input_Value : out Integer);
   --# global in Inputs;
   --# derives Input_Value from Inputs;

   function End_Of_Input return Boolean;
   --# global in Inputs;
end Input_Port;

package Output_Port
  --# own out Outputs;
is
   procedure Write_To_Port(Output_Value : in Integer);
   --# global out Outputs;
   --# derives Outputs from Output_Value;
end Output_Port;


with Input_Port, Output_Port, Stacks;
--# inherit Input_Port, Output_Port, Stacks;
--# main_program;
procedure Copy
--# global in     Input_Port.Inputs;
--#        out    Output_Port.Outputs;
--# derives Output_Port.Outputs from Input_Port.Inputs;
is
   The_Stack : Stacks.Stack;
   Value     : Integer;
   Done      : Boolean;
begin
   Stacks.Clear(The_Stack);
   loop
      Input_Port.Read_From_Port(Value);
      Stacks.Push(The_Stack, Value);
      Done := Input_Port.End_Of_Input;
      exit when Done;
   end loop;
   loop
      Stacks.Pop(The_Stack, Value);
      Output_Port.Write_To_Port(Value);
      exit when Stacks.Is_Empty(The_Stack);
   end loop;
end Copy;
