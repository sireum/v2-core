package CondWithoutElse
is
   procedure CondWithoutElse_Example (a, p, r: in Integer; t: in out Integer);
     --# derives t from a, p, r, t;

end CondWithoutElse;

package body CondWithoutElse is

   procedure CondWithoutElse_Example (a, p, r: in Integer; t: in out Integer)
   is
   begin
      if p > a
      then
	t := r;
      end if;
   end CondWithoutElse_Example;
   
end CondWithoutElse;
