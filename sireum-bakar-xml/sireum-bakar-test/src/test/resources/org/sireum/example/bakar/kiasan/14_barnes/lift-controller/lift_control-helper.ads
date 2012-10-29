with Building, Buttons, Lift;
use type Building.Floor_Type;

--# inherit Building, Buttons, Lift;
package Lift_Control_Helper
is
   procedure Traverse(Direction: in Building.Direction_Type);
   --# global in out Building.Door_History, Buttons.Button_History,
   --#               Lift.State;
   --# derives Building.Door_History,
   --#         Buttons.Button_History,
   --#         Lift.State
   --#               from *, Direction, Buttons.Button_History, Lift.State;
   
end Lift_Control_Helper;