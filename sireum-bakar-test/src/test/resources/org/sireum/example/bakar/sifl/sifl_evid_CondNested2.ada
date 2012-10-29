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
	    t := (a+1) + (2+b);
	 else
	    t:= (q+a+1); 
	 end if;
      else
	t := (q+2+b);
      end if;
        
   end NestedCondional_Example;
   
end NestedCondional;
