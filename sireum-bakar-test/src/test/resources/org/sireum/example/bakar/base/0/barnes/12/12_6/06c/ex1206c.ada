package Pressure
  --# own State, Temp_Copy;
  --# initializes State;
is
   function Value return Integer;
   --# global State;

   procedure Put_Temp(T : in Integer);
   --# global out Temp_Copy;
   --# derives Temp_Copy from T;

   procedure Calculate;
   --# global in out State; in Temp_Copy;
   --# derives State from State, Temp_Copy;
end Pressure;

--# inherit Pressure;
package Temperature
  --# own State;
  --# initializes State;
is
   function Value return Integer;
   --# global State;

   procedure Calculate;
   --# global in out State; out Pressure.Temp_Copy;
   --# derives State from State &
   --#         Pressure.Temp_Copy from State;
end Temperature;

with Pressure, Temperature;
--# inherit Pressure, Temperature;
--# main_program;
procedure PT(P, T : out Integer)
--# global in Pressure.State;
--#        in out Temperature.State;
--#        out Pressure.Temp_Copy;
--# derives P from Pressure.State &
--#         T, Temperature.State from Temperature.State &
--#         Pressure.Temp_Copy from Temperature.State;
is
begin
   Temperature.Calculate;
   T := Temperature.Value;
   P := Pressure.Value;
end PT;




