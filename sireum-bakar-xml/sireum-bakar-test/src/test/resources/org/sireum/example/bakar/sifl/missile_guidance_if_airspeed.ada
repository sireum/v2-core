package If_Airspeed
  --#own Last_Speed, Last_Valid;
  --#initializes Last_Speed, Last_Valid;
is
   type Meter_Per_Sec is range -5000 .. 5000;
   
   Last_Speed : Meter_Per_Sec := 0;
   Last_Valid  : Boolean := False;
   
   procedure Get_Speed(Speed : out Meter_Per_Sec;
                        Valid  : out Boolean);
   --# global in Last_Speed, Last_Valid;
   --# derives Speed from Last_Speed, Last_Valid &
   --#        Valid from Last_Valid;
   
end If_Airspeed;

package body If_Airspeed
is
   -- Find out the current speed and its validity
   procedure Get_Speed(Speed : out Meter_Per_Sec;
                        Valid  : out Boolean)
   is
   begin
      Valid := Last_Valid;
      if Last_Valid then
         Speed := Last_Speed;
      else
         Speed := 0;
      end if;
   end Get_Speed;

end If_Airspeed;
