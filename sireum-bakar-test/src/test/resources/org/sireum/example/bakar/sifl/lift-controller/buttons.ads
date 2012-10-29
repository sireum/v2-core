

with Building;
--# inherit Building;
package Buttons
--# own Button_History;
--# initializes Button_History;
is
   procedure Inspect_Floor_Button(Direction: in Building.Direction_Type;
                                  Floor: in Building.Floor_Type;
                                  Call_Found: out Boolean);
   --# global in out Button_History;
   --# derives Call_Found from Direction, Floor, Button_History &
   --#         Button_History from Button_History;

   procedure Inspect_Lift_Button(Floor: in Building.Floor_Type;
                                 Call_Found: out Boolean);
   --# global in out Button_History;
   --# derives Call_Found from Floor, Button_History &
   --#         Button_History from Button_History;
   procedure Clear_Floor_Button(Direction: in Building.Direction_Type;
                                Floor: in Building.Floor_Type);
   --# global in out Button_History;
   --# derives Button_History from Direction, Floor, Button_History;

   procedure Clear_Lift_Button(Floor: in Building.Floor_Type);
   --# global in out Button_History;
   --# derives Button_History from Floor, Button_History;

end Buttons;
