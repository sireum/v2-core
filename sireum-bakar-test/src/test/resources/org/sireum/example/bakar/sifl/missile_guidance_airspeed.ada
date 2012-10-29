-- Airspeed simulator implementation
--
-- $Log: airspeed.adb,v $
-- Revision 1.2  2005/01/24 19:19:05  adi
-- Hacked to implement logging
--
-- Revision 1.1.1.1  2004/01/12 19:29:12  adi
-- Added from tarfile
--
--
-- Revision 1.1  2003/08/08 20:45:17  adi
-- Initial revision
--
package Airspeed
  --# own Last_Speed, Last_Accel, Last_Time, Valid_Time, Ticks;
  --# initializes Last_Speed, Last_Accel, Last_Time, Valid_Time, Ticks;
is   
   type Meter_Per_Sec is range -5000 .. 5000;
   type Meter_Per_Sec_2 is range -200 .. 200;
   type Cm_Per_Sec_2 is range -30_000 .. 30_000;
   type Millisecond is range 0..(100*24);
   
   Valid_Time : Boolean := False;
   Ticks : Millisecond := 0;
   
   Last_Speed : Meter_Per_Sec := 0;
   Last_Accel : cm_Per_Sec_2 := 0;
   Last_Time     : Millisecond := Millisecond'First;
   
   procedure Read(T : out Millisecond;
                  Valid : out Boolean);
     --# global in out Ticks;
     --#        in Valid_Time;
     --# derives
     --#      Valid from Valid_Time &
     --#      T, Ticks from Valid_Time, Ticks;
   
   procedure Extrapolate_Speed(Speed : out Meter_Per_sec);
     --# global in     Last_Speed, Last_Accel, Last_Time, Valid_Time;
     --#        in out Ticks;
     --# derives Ticks from *, Valid_Time &
     --#         Speed from Valid_Time, Ticks, Last_Speed,
     --#         Last_Accel, Last_Time;
   
end Airspeed;


package body Airspeed
is
   
   -- Read the current time
   procedure Read(T : out Millisecond;
                  Valid : out Boolean)
   is
   begin
      if Valid_Time then
         Valid := True;
         T := Ticks;
         -- Each read takes at least 1 millisecond
	 if Ticks < 60 then
            Ticks := Ticks + 1;
         else
            Ticks := 0;
         end if;
      else
         T := 0;
         Valid := False;
      end if;
   end Read;
   
   function Delta_Time (Orig, Now : Millisecond)
                       return Millisecond
   is
      Gap : Millisecond;
   begin
      if Now >= Orig then
         Gap := Now - Orig;
      else
         -- Wraparound
         Gap := Millisecond'Last - Orig;
         Gap := Gap + Now;
      end if;
      return Gap;
   end Delta_Time;

   -- Work out the current speed.
   procedure Extrapolate_Speed(Speed : out Meter_Per_sec)
     -- Ticks from 
     -- Speed from
   is
      Time_Now,T_Delta : Millisecond;
      Time_Valid : Boolean;
      S_Delta : Meter_Per_Sec;
      VMS : Millisecond;
   begin
      Read(Time_Now, Time_Valid);
      -- Time_Now   from Valid_Time when true; 
      --                 Ticks      when Valid_Time
      -- Time_Valid from Valid_Time when true;
      -- Ticks      from Valid_Time when true; 
      --                 Ticks      when Valid_Time; 
      --                 Ticks      when !Valid_Time
      if not Time_Valid 
      then
        -- Can't extrapolate
        Speed := 0;
      else
         -- How many seconds change
         T_Delta := Delta_Time(Last_Time,Time_Now);
	 -- T_Delta derives from Last_Time when true;
	 --                      Time_Now  when true
         if Last_accel < 0 then
            VMS := Millisecond(0-Last_accel);
            S_Delta := 0-Meter_Per_Sec((VMS * T_Delta)/100_000);
         else
            VMS := Millisecond(Last_accel);
            S_Delta := Meter_Per_Sec((VMS * T_Delta)/100_000);
         end if;
         Speed := Last_Speed + S_Delta;
      end if;
   end Extrapolate_Speed;

end Airspeed;
