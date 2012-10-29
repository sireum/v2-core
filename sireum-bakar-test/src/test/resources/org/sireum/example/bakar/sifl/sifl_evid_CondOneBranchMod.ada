package CondOneBranchMod
is
   procedure CondOneBranchMod_Example (a, p, r: in Integer; x, t: in out Integer);
     --# derives t from a, p, r, t
     --#       & x from a, p, x; 

end CondOneBranchMod;

package body CondOneBranchMod is

   procedure CondOneBranchMod_Example (a, p, r: in Integer; x, t: in out Integer)
   is
   begin
      if p > a
      then
      	x := p;
      else
	t := r;
      end if;
   end CondOneBranchMod_Example;
   
end CondOneBranchMod;