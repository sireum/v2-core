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

