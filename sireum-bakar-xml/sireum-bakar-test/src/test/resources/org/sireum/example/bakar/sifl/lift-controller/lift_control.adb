
with Building, Buttons, Lift;
--# inherit Building, Buttons, Lift;
--# main_program;
procedure Lift_Control
--# global in out Buttons.Button_History;
--#        out Building.Door_History, Lift.State;
--# derives Building.Door_History, Buttons.Button_History, Lift.State
--#                  from Buttons.Button_History;
is

   procedure Traverse(Direction: in Building.Direction_Type)
   --# global in out Building.Door_History, Buttons.Button_History,
   --#               Lift.State;
   --# derives Building.Door_History,
   --#         Buttons.Button_History,
   --#         Lift.State
   --#               from *, Direction, Buttons.Button_History, Lift.State;
   is separate;

begin   -- Lift_Control
   Building.Initialize_Door_States;
   Lift.Initialize_Lift_State;
   loop
      Traverse(Building.Up);
      Traverse(Building.Down);
   end loop;
end Lift_Control;
