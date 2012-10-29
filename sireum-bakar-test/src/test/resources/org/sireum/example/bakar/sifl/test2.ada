package Main 
is
   type Pitchangle is range   -10 .. 20;
   type Degreespersec is range -180 .. 180;
   
   subtype History_Range is Integer range 1..10;
   type History_List is array(History_Range) of Pitchangle;
   
   procedure History_Update(H : in out History_List;
		            V : in Pitchangle);
   --# derives H from *,V;
   
end Main;

package body Main
is
   
   procedure History_Update(H : in out History_List;
		            V : in Pitchangle)
   is
      I:  History_Range;
   begin
      I := 1;
     while I <= 10 loop
--	if I < History_Range'Last then
	if I < 10 then   
	   H(I) := H(I+1);
	else
	   H(I) := V;
	end if;
	I := I + 1;
     end loop;
   end History_Update;

end Main;
