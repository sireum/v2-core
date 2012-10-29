-- Lift controller building package
package Building
--# own Door_History : Doors_State_Type;
is
   type Direction_Type is (Up, Down);
   type Floor_Type is (Floor_0, Floor_1, Floor_2, Floor_3);
   type Door_Position is (Open, Closed);
   type Doors_State_Type is array (Floor_Type) of Door_Position;

   procedure Initialize_Door_States;
   --# global out Door_History;
   --# derives Door_History from ;
   --# post Door_History = Doors_State_Type'(Floor_Type => Closed);

   procedure Open_Door (Floor : in Floor_Type);
   --# global in out Door_History;
   --# derives Door_History from Door_History, Floor;
   --# pre Door_History (Floor) = Closed;
   --# post Door_History = Door_History~[Floor => Open];

   procedure Close_Door (Floor : in Floor_Type);
   --# global in out Door_History;
   --# derives Door_History from Door_History, Floor;
   --# pre Door_History (Floor) = Open;
   --# post Door_History = Door_History~[Floor => Closed];
end Building;


