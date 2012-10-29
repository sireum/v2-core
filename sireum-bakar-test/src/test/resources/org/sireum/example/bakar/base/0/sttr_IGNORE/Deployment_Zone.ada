-- Patrice Chalin, SAnToS & DSRG.org.

package Deployment_Zone 
  --# own Zone : Zone_Type;
is
   type Coord_Type is record
      X : Integer;
      Y : Integer;
   end record;
      
   subtype Vehicle_ID_Type is Integer range 1 .. 10;
   type Vehicle_Array_Type is array (Vehicle_ID_Type) of Coord_Type;
   
   type Zone_Type is record
      Top_Left : Coord_Type;
      Bottom_Right : Coord_Type;
      Vehicles : Vehicle_Array_Type;
   end record;

   procedure Set_Init_Scenario(Zone_Init : in Zone_Type);
   --# global out Zone;
   --# derives Zone from Zone_Init;
   --# post Zone = Zone_Init;
   
   function Is_In_The_Zone(L: Coord_Type) return Boolean;
   --# global in Zone;
   --# return Zone.Top_Left.X <= L.X and L.X <= Zone.Bottom_Right.X and
   --#        Zone.Top_Left.Y <= L.Y and L.Y <= Zone.Bottom_Right.Y;
   
   function Package_Invariant return Boolean;
   --# global in Zone;
   --# return for all I in Vehicle_ID_Type => (
   --#          Is_In_The_Zone(Zone.Vehicles(I), Zone));
   
   procedure Set_New_Vehicle_Location(Vehicle_ID : Vehicle_ID_Type;
				      Location : Coord_Type);
   --# global in out Zone;
   --# derives Zone from Zone, Vehicle_ID, Location;
   --# pre Is_In_The_Zone(Location, Zone) and Package_Invariant(Zone);
   --# post Zone.Vehicles(Vehicle_ID) = Location and Package_Invariant(Zone);
   
   function Get_Vehicle_Location(Vehicle_ID : Vehicle_ID_Type) return Coord_Type;
   --# global in Zone;
   --# return Zone.Vehicles(Vehicle_ID);

   --# function Int_Abs(I : Integer) return Integer;
   --! return J => 0 < J and (J = I or J = -I);

   --# function Int_Sqr(I : Integer) return Integer; 
   --! return I*I;

   --# function Int_Sqrt(I : Integer) return Natural;
   --! return J => 0 < J and (for all K in Natural =>
   --!   (Int_Abs(Int_Sqr(J) - I) <= Int_Abs(Int_Sqr(K) - I)));

   function Distance(From_Coord, To_Coord : in Coord_Type) return Integer;
   --# return Int_Sqrt(Int_Sqr(To_Coord.X - From_Coord.X) 
   --#		     + Int_Sqr(To_Coord.Y - From_Coord.Y));

   function Vehicle_Distance(Vehicle_ID1, Vehicle_ID2 : in Vehicle_ID_Type) return Integer;
   --# global in Zone;
   --# return Distance(Zone.Vehicles(Vehicle_ID1), 
   --#                 Zone.Vehicles(Vehicle_ID2));

  function Get_Vehicle_Closest_To_Target(Target_Vehicle : Vehicle_ID_Type) 
					return Vehicle_ID_Type;
   --# global in Zone;
   --# return I => for all J in Vehicle_ID_Type => (J /= Target_Vehicle ->
   --#   Vehicle_Distance(I, Target_Vehicle, Zone) 
   --#     <= Vehicle_Distance(J, Target_Vehicle, Zone));
   
end Deployment_Zone;

package body Deployment_Zone 
is
   Zone : Zone_Type;
   
   procedure Set_Init_Scenario(Zone_Init : in Zone_Type)
   is begin
      Zone := Zone_Init;
   end Set_Init_Scenario;
   
   procedure Set_New_Vehicle_Location(Vehicle_ID : Vehicle_ID_Type;
				      Location : Coord_Type)
   is begin
      Zone.Vehicles(Vehicle_ID) := Location;
   end Set_New_Vehicle_Location;

   function Get_Vehicle_Closest_To_Target(Target_Vehicle : Vehicle_ID_Type) return Vehicle_ID_Type
   is
      Closest_Vehicle_ID : Vehicle_ID_Type;
   begin
      if(Zone.Vehicles(Target_Vehicle).X > 0) then
	 Closest_Vehicle_ID := 1;
      else
	 Closest_Vehicle_ID := 2;
      end if;
      return Closest_Vehicle_ID;
   end Get_Vehicle_Closest_To_Target;
   
   function Get_Vehicle_Location(Vehicle_ID : Vehicle_ID_Type) return Coord_Type
   is
   begin
      return Zone.Vehicles(Vehicle_ID);
   end Get_Vehicle_Location;
   
   function Is_In_The_Zone(L: Coord_Type) return Boolean
   is begin
      return Zone.Top_Left.X <= L.X and L.X <= Zone.Bottom_Right.X and
	Zone.Top_Left.Y <= L.Y and L.Y <= Zone.Bottom_Right.Y;
   end Is_In_The_Zone;
   
   function Distance(From_Coord, To_Coord : in Coord_Type) return Integer
     is separate;
   
   function Vehicle_Distance(Vehicle_ID1, Vehicle_ID2 : in Vehicle_ID_Type) return Integer
   is
   begin
      return Distance(Zone.Vehicles(Vehicle_ID1), Zone.Vehicles(Vehicle_ID2));
   end Vehicle_Distance;

   function Package_Invariant return Boolean
   is 
      I : Vehicle_ID_Type := Vehicle_ID_Type'First;
      Result : Boolean := True;
   begin
      while I <= Vehicle_ID_Type'Last and Result loop
         --# assert (for all J in Vehicle_ID_Type range
         --#        Vehicle_ID_Type'First .. I-1 => (
         --#          Is_In_The_Zone(Zone.Vehicles(J), Zone)));
	 Result := Is_In_The_Zone(Zone.Vehicles(I));
	 if I < Vehicle_ID_Type'Last then
	    I := I + 1;
	 end if;
      end loop;
      return Result;
   end Package_Invariant;

end Deployment_Zone;
