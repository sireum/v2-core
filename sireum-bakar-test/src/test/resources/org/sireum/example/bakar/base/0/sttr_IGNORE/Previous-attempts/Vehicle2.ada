package Vehicle 
  --# own Vehicle_Location : Location_Type;
is
   type Location_Type is record
      X : Integer;
      Y : Integer;
   end record;
   
   subtype Index_Type is Integer range 1 .. 10;
   type Location_Array_Type is array (Index_Type) of Location_Type;

   function Distance_From(Other : in Location_Type) return Integer;
   --# global in Vehicle_Location;
   --! return TBC.
   
   function Closest(Other_Locations : Location_Array_Type) return Index_Type;
   --# global in Vehicle_Location;
   --! return TBC;
   
   procedure Adjust_Location(Delta_X : in Integer;
			     Delta_Y : in Integer);
   --# global in out Vehicle_Location;
   --# derives Vehicle_Location from Delta_X, Delta_Y, Vehicle_Location;
   --! pre TBC.
   --! post TBC.
   
   procedure Set_Start_Location(Vehicle_Start_Location : in Location_Type);
   --# global out Vehicle_Location;
   --# derives Vehicle_Location from Vehicle_Start_Location;
   --# post Vehicle_Location = Vehicle_Start_Location;
   
end Vehicle;

--  package body Vehicle is
--     Vehicle_Location : Location_Type;
--     Base_Location : Location_Type;
   
--     function Distance_From_Base return Integer
--     is
--     begin
--        return Vehicle_Location.X + Base_Location.Y;
--     end Distance_From_Base;
   
--     procedure Adjust_Location(Delta_X : Integer;
--  			     Delta_Y : Integer)
--     is begin
--        Vehicle_Location.X := Vehicle_Location.X + Delta_X;
--        Vehicle_Location.Y := Vehicle_Location.Y + Delta_Y;
--     end Adjust_Location;
   
--     procedure Init(Vehicle_Init_Location : in Location_Type;
--  		  Base_Init_Location : in Location_Type)
--     is
--     begin
--        Vehicle_Location := Vehicle_Init_Location;
--        Base_Location := Base_Init_Location;
--     end Init;

--  end Vehicle;
