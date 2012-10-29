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
	    t := 1;
	 else
	    t:= q; 
	 end if;
      else
	t := 2;
      end if;
        
   end NestedCondional_Example;
   
end NestedCondional;
