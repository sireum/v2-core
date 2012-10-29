package Drag 
is
   
   type integer32 is range 0 .. 1000; 
   
   -- Constants
   lin_quad_boundary : constant integer32 := 100;
   transonic_boundary : constant integer32 := 400;
   postsonic_boundary : constant integer32 := 450;
   drag_at_mach1 : constant integer32 := (307 * 307) / (lin_quad_boundary);
   transonic_mach1_diff : constant integer32 := (transonic_boundary - 307);
   drag_at_transonic : constant integer32 := drag_at_mach1;
   
   
   procedure Calc_Drag(Profile  : in integer32;
                       Speed    : in integer32;
                       Altitude : in integer32;
                       Drag_force : out integer32);
   --# derives Drag_force from Speed, Profile, Altitude;
   
end Drag;
   
package body Drag
is


   function Relative_Drag_At_Altitude(Altitude : in integer32) 
				     return integer32
   is
      intermediate : integer32;
   begin
      intermediate := (50_000 - (Altitude)) / 250;
      -- intermediate is now between 100 and 200
      if intermediate < 100 then
         intermediate := 100;
      else
	 if intermediate > 200 then
	    intermediate := 200;
	 else
	    null;
	 end if;
      end if;
      return (intermediate - 100);
   end Relative_Drag_At_Altitude;


   procedure Calc_Drag(Profile  : in integer32;
                       Speed    : in integer32;
                       Altitude : in integer32;
                       Drag_force : out integer32)
   is
      working : integer32;
      sdiff,ddiff : integer32;
   begin
      if speed < 307 then
         -- Sub-mach1
	 if speed < 100 then
	 -- linear: ranges from 1 to 100
            working := speed;
         else
            -- quadratic, ranges from 100 to about 900
            working := (speed*speed) / (lin_quad_boundary);
         end if;
      else
	 if speed < 400 then
	 -- Weird transonic drag going on, very steep
            -- ranges from about 900 to about 1350
            sdiff := speed - 307;
            working := drag_at_mach1 + (sdiff * (sdiff * sdiff))/2000;
	else
	   if speed < 450 then
	   -- Falls off linearly back to quadratic
	      sdiff := postsonic_boundary - transonic_boundary;
	      ddiff := drag_at_transonic - drag_at_mach1;
	      working := drag_at_mach1 + ((postsonic_boundary - speed)*ddiff)/sdiff;
	   else
	      -- linear again
	      sdiff := speed - postsonic_boundary;
	      working := drag_at_mach1 + sdiff;
	   end if; 
	end if;
      end if;
      working := (working * Relative_Drag_At_Altitude(altitude)) / 100;
      drag_force := (working * profile)/2;
   end calc_drag;
   
end Drag;
