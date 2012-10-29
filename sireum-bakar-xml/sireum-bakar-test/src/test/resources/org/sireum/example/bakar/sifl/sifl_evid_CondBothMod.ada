package CondBothMod
is
   procedure CondBothMod_Example (a, p, r: in Integer; t: out Integer);
     --# derives t from a, p, r; 

end CondBothMod;

package body CondBothMod is

   procedure CondBothMod_Example (a, p, r: in Integer; t: out Integer)
   is
   begin
      if p > a
      then
      	t := p;
      else
	t := r;
      end if;
   end CondBothMod_Example;
   
end CondBothMod;