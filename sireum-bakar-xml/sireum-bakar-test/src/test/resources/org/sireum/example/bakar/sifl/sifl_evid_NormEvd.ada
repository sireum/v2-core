package NormEvd
is
   procedure NormEvd_Example (b, p, q: in Integer; t: out Integer; a: in out Integer);
     --# derives t from a, b, p, q
     --#       & a from a, b; 

end NormEvd;

package body NormEvd is

   procedure NormEvd_Example (b, p, q: in Integer; t: out Integer; a: in out Integer)
   is
   begin
      a := a + b;
      while a <= b loop
         a := a + b;
      end loop;
      
      if p > a
      then
	 if p > b
	 then 
	    t := a;
	 else
	    t:= b; 
	 end if;
      else
	t := p + q;
      end if;
        
   end NormEvd_Example;
   
end NormEvd;
