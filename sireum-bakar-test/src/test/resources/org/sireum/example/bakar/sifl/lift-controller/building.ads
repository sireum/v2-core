-- Section 13.2a
-- package specifications of lift system
-- plus complete main subprogram
-- including subunits


package Building
--# own Door_History;
is
   type Direction_Type is (Up, Down);
   type Floor_Type is (Floor_0, Floor_1, Floor_2, Floor_3);

   procedure Initialize_Door_States;
   --# global out Door_History;
   --# derives Door_History from ;
   --  postcondition: all doors closed;

   procedure Open_Door(Floor: in Floor_Type);
   --# global in out Door_History;
   --# derives Door_History from Door_History, Floor;

   procedure Close_Door(Floor: in Floor_Type);
   --# global in out Door_History;
   --# derives Door_History from Door_History, Floor;

end Building;
