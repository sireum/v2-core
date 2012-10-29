
package AP_Heading_Roll 
is
   type Degreespersec is range -180 .. 180; -- from Degrees.ads
   
   type Bankangle  is range   -45 .. 45;  -- from instruments.ads
   type Headdegree is range     0 .. 359; -- from instruments.ads
   
    procedure Target_ROR(Present_Heading : in Headdegree;
			Target_Heading  : in Headdegree;
			Result : out Bankangle);
    --# derives Result from Present_Heading, Target_Heading;
    
    procedure Target_Rate(Present_Heading : in Headdegree;
			Target_Heading  : in Headdegree;
			Bank            : in Bankangle;
			Result : out Bankangle);
    --# derives Result from Present_Heading, Target_Heading, Bank;
   
end AP_Heading_Roll;

   


package body AP_Heading_Roll
is
   
   function Heading_Offset(Present : Headdegree;   -- from scale.adb
                           Target  : Headdegree)
                          return Headdegree
   is
      V : Headdegree;
      Ans : Headdegree;
      ConstV: Headdegree;  
   begin
      --      V := (360 + Integer(Target)) - Integer(Present);
      ConstV := 359;
      V := (ConstV + Target) - Present;
      if (V > ConstV) 
      then
	 V := V - ConstV;
      else
	 null;
      end if;
      Ans := V;
      return Ans;
   end Heading_Offset;
   
   procedure Target_ROR(Present_Heading : in Headdegree;
			Target_Heading  : in Headdegree;
			Result : out Bankangle)
   is
      Offset : Headdegree;
   begin
      Offset := Heading_Offset(Present_Heading,Target_Heading);
       if (Offset > 40 and Offset <= 180) then
	 Result := 40;
       else
	  if (Offset > 180 and Offset < 319) 
	  then
--	     Result := -40;
	     Result := 0;
	  else
	     if (Offset <= 40) 
	     then
		Result := BankAngle(Offset);
	     else
		Result := BankAngle(360 - Integer(Offset));
	     end if;
	  end if;
       end if;
       
   end Target_ROR;
   
   procedure Target_Rate(Present_Heading : in Headdegree;
			Target_Heading  : in Headdegree;
			Bank            : in Bankangle;
			Result : out Bankangle)
   is
      Target_Bank : Bankangle;
   begin
      Target_ROR(Present_Heading,Target_Heading, Target_Bank);
      -- before the procedure call, the postcondition is: true >> (AP_Heading_Roll::Degreespersec) temp$3# ; 
      -- which is a Cast Expression, and it will not match any consequent of the called procedure contract
      -- so it will lead to lose imprecision
--      Result := (Degreespersec(Target_Bank) - Degreespersec(Bank)) / 5;
      Result := (Target_Bank - Bank) / 5;
      if Result > 10 then
	 Result := 10;
      else
--	 if Result < -10 
	 if Result < (0 - 10) 
	 then
--	    Result := -10;
	    Result := 0 - 10;
	 else
	    null;
	 end if;
      end if;
   end Target_Rate;
   
end AP_Heading_Roll;
