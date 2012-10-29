package World
  --# own in In_State; out Out_State;
is
   procedure Read_Temp(T : out Integer);
   --# global in In_State;
   --# derives T from In_State;

   procedure Write_Press(P : in Integer);
   --# global out Out_State;
   --# derives Out_State from P;
end World;

with World;
--# inherit World;
--# main_program;
procedure Process(Press : in Integer; Temp : out Integer)
  --# global in World.In_State; out World.Out_State;
  --# derives Temp from World.In_State &
  --#         World.Out_State from Press;
is
begin
   World.Write_Press(Press);
   World.Read_Temp(Temp);
end Process;

