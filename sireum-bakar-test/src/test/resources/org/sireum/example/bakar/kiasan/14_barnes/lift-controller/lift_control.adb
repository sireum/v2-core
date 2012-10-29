
with Building, Buttons, Lift, Lift_Control_Helper;
--# inherit Building, Buttons, Lift, Lift_Control_Helper;
--# main_program;
procedure Lift_Control
--# global in out Buttons.Button_History;
--#        out Building.Door_History, Lift.State;
--# derives Building.Door_History, Buttons.Button_History, Lift.State
--#                  from Buttons.Button_History;
is
begin   -- Lift_Control
   Building.Initialize_Door_States;
   Lift.Initialize_Lift_State;
   loop
      Lift_Control_Helper.Traverse(Building.Up);
      Lift_Control_Helper.Traverse(Building.Down);
   end loop;
end Lift_Control;
