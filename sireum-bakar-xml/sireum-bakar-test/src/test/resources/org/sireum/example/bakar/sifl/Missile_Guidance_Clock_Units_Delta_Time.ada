-- Time-related utilities implementation
--
-- $Log: clock_utils.adb,v $
-- Revision 1.1.1.1  2004/01/12 19:29:12  adi
-- Added from tarfile
--
--
-- Revision 1.1  2003/02/09 20:35:18  adi
-- Initial revision
--
--
package Clock_Units
is
   type Millisecond is range 0..(100*24);   
end Clock_Units;

package body Clock_Units is
   -- Wraparound time diff delta calculation
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

end Clock_Units;
