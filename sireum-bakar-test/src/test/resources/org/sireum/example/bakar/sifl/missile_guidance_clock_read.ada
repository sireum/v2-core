-- Clock implementation for simulation
--
-- $Log: clock.adb,v $
-- Revision 1.1.1.1  2004/01/12 19:29:12  adi
-- Added from tarfile
--
--
-- Revision 1.2  2003/02/13 00:17:55  adi
-- Added Command separate
--
-- Revision 1.1  2003/02/08 17:38:45  adi
-- Initial revision
--
--
package Clock
  --# own  Valid_Time, Ticks;
  --# initializes  Valid_Time, Ticks;
is
   type Millisecond is range 0..(10*(36*24));
   
   procedure Read(T : out Millisecond;
                  Valid : out Boolean);
     --# global in out Ticks;
     --#        in Valid_Time;
     --# derives
     --#      Valid from Valid_Time &
     --#      T, Ticks from Valid_Time, Ticks;
end Clock;


package body Clock
is
   Valid_Time : Boolean := False;
   Ticks : Millisecond := 0;

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

end Clock;
