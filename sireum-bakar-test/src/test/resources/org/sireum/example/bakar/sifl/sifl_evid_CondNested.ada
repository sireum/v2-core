package NestedCondional
is
   procedure NestedCondional_Example (a, b, p, q: in Integer; t: out Integer);
     --# derives t from a, b, p, q;

end NestedCondional;

package body NestedCondional is

   procedure NestedCondional_Example (a, b, p, q: in Integer; t: out Integer)
   is
   begin      
      if p > a
      then
	 if p > b
	 then 
	    t := a + 1;
	 else
	    t:= b + 2; 
	 end if;
      else
	t := p + q;
      end if;
        
   end NestedCondional_Example;
   
end NestedCondional;
