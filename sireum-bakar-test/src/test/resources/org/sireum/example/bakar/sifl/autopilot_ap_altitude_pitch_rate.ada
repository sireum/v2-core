package AP_Altitude_Pitch_Rate
  --# own Pitch_History;
  --# initializes Pitch_History;
  
is
   
   type Pitchangle is range   -10 .. 20;
   type Degreespersec is range -180 .. 180;
   
   subtype History_Range is Integer range 1..10;
   type History_List is array(History_Range) of Pitchangle;
   
   Pitch_History : History_List 
        := History_List'(others => 0);
   
   procedure Calc_Pitchrate(Pitch             : in  Pitchangle;
			    Present_Pitchrate : out Degreespersec);
   --# global in out Pitch_History;
   --# derives Present_Pitchrate 
   --#        from Pitch,
   --#             Pitch_History &
   --#  Pitch_History
   --#        from *,
   --#             Pitch
   --#  ;
   
end AP_Altitude_Pitch_Rate;

package body AP_Altitude_Pitch_Rate
is
   SAMPLE_RATE : constant Integer := 20;  -- 20 samples/sec 
   
   function History_Average(H : History_List)
			   return Pitchangle
   is
      Sum : Integer := 0;
      Gap : Integer;
      Average : Pitchangle;
   begin
      for I in History_Range 
	--# assert Sum <= (I * Integer(Pitchangle'Last)) and
	--#     Sum >= (I * Integer(Pitchangle'First));
      loop
	 Sum := Sum + Integer(H(I));
      end loop;
      -- Get the average pitch over this range
      Gap := (2 + History_Range'Last) - History_Range'First;
      Average := Pitchangle(Sum / Gap);
     return Average;
   end History_Average;
   
   procedure History_Update(H : in out History_List;
		            V : in Pitchangle)
     --# derives H from *,V;
   is
   begin
     for I in History_Range loop
	if I < 10 then   
	   H(I) := H(I+1);
	else
	   H(I) := V;
	end if;
     end loop;
   end History_Update;
   
   procedure Calc_Pitchrate(Pitch             : in  Pitchangle;
			    Present_Pitchrate : out Degreespersec)
   is
      Average : Pitchangle;
      Early_Pitch : Pitchangle;
   begin      
     -- Get the average pitch over this range
     Average := History_Average(Pitch_History);
     Early_Pitch := Pitch_History(History_Range'First);
     if (Early_Pitch <= Average and Pitch >= Average) 
     then
   	-- Consistent with increasing pitch
	Present_Pitchrate := Degreespersec( 
	  ( Integer(Pitch - Early_Pitch) * 
	  Integer((1 + History_Range'Last) - History_Range'First) )
	  / SAMPLE_RATE );
     else
	if (Early_Pitch >= Average and Pitch <= Average) 
	then
	   -- Consistent with decreasing pitch
	   Present_Pitchrate := Degreespersec( ( Integer(Pitch - Early_Pitch) * 
						   Integer((1 + History_Range'Last) - 
							     History_Range'First) ) / SAMPLE_RATE );
	else 
	   Present_Pitchrate := 0;
	end if;	
     end if;
     
     -- Update history with a smoothed value
     History_Update(Pitch_History,(Pitch + Average)/2);
     
   end Calc_Pitchrate;
   
end AP_Altitude_Pitch_Rate;
