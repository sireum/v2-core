
package Scale
is
   subtype Scaledata is Integer range -100..100;
   type Controlangle is range -45 .. 45; -- from surfaces.ads
   type Machnumber is range     0 .. 100; -- from instruments.ads
   
   
   -- Scale a control movement with respect to current
   -- speed and data differences
   procedure Scale_Movement(Mach    : in Machnumber;
                           Present : in Scaledata;
                           Target  : in Scaledata;
                           Max     : in ControlAngle;
			   Target_Angle : out ControlAngle);
   --# derives Target_Angle from Mach, Present, Target, Max;
   
   
end Scale;


package body Scale
is
   subtype Percent is Integer range 0..100;

   -- Return an inverse curve that is well behaved
   function Inverse(Val      : Scaledata;
                    Flatness : Integer)
                   return Percent
   --# pre (Val >= 0) and (Flatness > 0) and (Flatness <= 100);
   is
      Calc : Integer;
      Ans : Percent;
   begin
      Calc := (100*Flatness) / (Flatness + Val);
      Ans := Calc;
      return Ans;
   end Inverse;


   procedure Scale_Movement(Mach    : in Machnumber;
                           Present : in Scaledata;
                           Target  : in Scaledata;
                           Max     : in ControlAngle;
			   Target_Angle : out ControlAngle)
   is
      K1, K2, Gap : Integer;
   begin
      if Present = Target then
         Target_Angle := 0;
      else
         -- Get the sign of the answer
         if Present < Target then
            -- Increase rate
            Gap := Target - Present;
         else
            -- Decrease rate
           Gap := Present - Target;
         end if;
         if Gap > 100 then
	    Gap := 100;
	 else
	    null;
         end if;
         -- Generate K1, K2 such that:
         --   K1 >= 0,  K2 > 0, K1 <= K2
         --   K1 ~= a.Gap
         --   K2 ~= b.Mach
         K1 := Inverse(Integer(Mach),20); -- tween 0 and 100
         K2 := Inverse(Gap,20); -- tween 0 and 100
         K2 := (1 + K2) + K1;  -- handle the scaling
         -- And use them to scale Target_Angle
         Target_Angle := ControlAngle((Integer(Max) * K1) / K2);
         if (Present > Target) then
--	    Target_Angle := -Target_Angle;
	    Target_Angle := 0 - Target_Angle;
	 else
	    null;
         end if;
      end if;
   end Scale_Movement;
   
   
--   function Heading_Offset(Present : Instruments.Headdegree;
--                           Target  : Instruments.Headdegree)
--                          return Instruments.Headdegree
--   is
--      V : Integer;
--      Ans : Instruments.Headdegree;
--   begin
--      V := (360 + Integer(Target)) - Integer(Present);
--      --# assert ((V >= 0) and (V < 720));
--      if (V >= 360) then
--        V := V - 360;
--      end if;
--      --# assert ((V >= 0) and (V < 360));
--      Ans := Instruments.Headdegree(V);
--      return Ans;
--   end Heading_Offset;
   
   
end Scale;





