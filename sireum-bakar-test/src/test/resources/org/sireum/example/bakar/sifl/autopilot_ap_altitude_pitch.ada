
package AP_Altitude_Pitch
is
   type Feet       is range     0 .. 50_000;

   type Bankangle  is range   -45 .. 45;

   type Headdegree is range     0 .. 359;

   type Feetpermin is range -6000 .. 6000;

   type Machnumber is range     0 .. 100;

   type Pitchangle is range   -10 .. 20;

   type Slipangle  is range   -25 .. 25;
   
   subtype Floorfpm is Feetpermin range -1000 .. 1000;
   type Degreespersec is range -180 .. 180;
   
   procedure Target_ROC(Present_Altitude : in Feet;
			Target_Altitude  : in Feet;
			Result : out Feetpermin);
   --# derives Result from Present_Altitude, Target_Altitude;
   
    procedure Target_Rate(Present_Altitude : in Feet;
                        Target_Altitude  : in Feet;
                        Climb_Rate : in Feetpermin;
			Result : out Degreespersec);
    --# derives Result from Present_Altitude, Target_Altitude, Climb_Rate;
   
end AP_Altitude_Pitch;

   


package body AP_Altitude_Pitch
is

   procedure Target_ROC(Present_Altitude : in Feet;
			Target_Altitude  : in Feet;
			Result : out Feetpermin)
   is
   begin
      Result := Feetpermin( Integer(Target_Altitude - Present_Altitude) / 10);
      if (Result > Floorfpm'Last) then
         Result := Floorfpm'Last;
      else
	 if Result <  Floorfpm'First then
	    Result := Floorfpm'First;
	 else
	    null;
	 end if;	   
      end if;
   end Target_ROC;

   procedure Target_Rate(Present_Altitude : in Feet;
                        Target_Altitude  : in Feet;
                        Climb_Rate : in Feetpermin;
			Result : out Degreespersec)
   is
      Target_Climb_Rate : Floorfpm;
      Floor_Climb_Rate  : Floorfpm;
   begin
      Target_ROC(Present_Altitude,Target_Altitude, Target_Climb_Rate);
      -- for each 2-assertion whose consequent is modified, it will generate 7 preconditions
      -- so the total preconditions after this nested condition will be 37 = 2 + 5*7
      if Climb_Rate > Floorfpm'Last then
         Floor_Climb_Rate := Floorfpm'Last;
      else
	 if Climb_Rate < Floorfpm'First then
	    Floor_Climb_Rate := Floorfpm'First;
	 else
	    Floor_Climb_Rate := Climb_Rate;
	 end if;
      end if;

      Result := Degreespersec( (Target_Climb_Rate - Floor_Climb_Rate) / 12);  
      -- after this nested conditional, it will generate 7 2-assertions
      -- 2 2-assertions whose consequent is constant
      -- 5 2-aasertions whose consequent includes Result
      if (Result > 10) then
         Result := 10;
      else
	 -- (1) transform both functions into procedures
	 -- (2) cannot deal with NEG UnaryOp, DIV/IDIV operator, if..elsif..else..
--	 if (Result < -10) then
-- 	    Result := -10;
--	 else
	 if (Result < 0) then
 	    Result := 0;
	 else
	    null;
	 end if;
      end if;
   end Target_Rate;

end AP_Altitude_Pitch;
