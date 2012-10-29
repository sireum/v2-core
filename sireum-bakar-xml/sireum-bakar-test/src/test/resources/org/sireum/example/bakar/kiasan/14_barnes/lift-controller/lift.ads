

with Building;
--# inherit Building;
package Lift
--# own State;
is
   function Present_Floor return Building.Floor_Type;
   --# global State;

   procedure Initialize_Lift_State;
   --# global out State;
   --# derives State from ;

   procedure Move(Next_Stop: in Building.Floor_Type);
   --# global out State;
   --# derives State from Next_Stop;

end Lift;
