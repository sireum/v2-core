package SeqConsMod
is
   procedure SeqConsMod_Example (q, r: in Integer; x, t: out Integer; p : in out Integer);
     --# derives x from p
     --#       & p from q
     --#       & t from r;

end SeqConsMod;

package body SeqConsMod is

   procedure SeqConsMod_Example (q, r: in Integer; x, t: out Integer; p : in out Integer)
   is
   begin
      x := p;
      p := q;
      t := r;
   end SeqConsMod_Example;
   -- postconditions: t bop a => p
end SeqConsMod;