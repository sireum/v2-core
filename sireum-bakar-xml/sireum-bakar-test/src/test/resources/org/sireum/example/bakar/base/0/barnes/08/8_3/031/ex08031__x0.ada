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


with Input_Port, Output_Port,The_Stack;
--# inherit Input_Port, Output_Port, The_Stack;
--# main_program;
procedure Copy
--# global in out The_Stack.State;
--#        in     Input_Port.Inputs;
--#        out    Output_Port.Outputs;
--# derives
--#    The_Stack.State from *, Input_Port.Inputs &
--#    Output_Port.Outputs
--#       from Input_Port.Inputs, The_Stack.State;
is
   Value : Integer;
begin
   loop
      Input_Port.Read_From_Port(Value);
      The_Stack.Push(Value);
      exit when Value = 0;
   end loop;
   loop
      The_Stack.Pop(Value);
      Output_Port.Write_To_Port(Value);
      exit when Value = 0;
   end loop;
end Copy;
