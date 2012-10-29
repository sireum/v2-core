package SeqConsNotMod
is
   procedure SeqConsNotMod_Example (q: in Integer; x: out Integer; p : in out Integer);
     --# derives x from p
     --#       & p from q;

end SeqConsNotMod;

package body SeqConsNotMod is

   procedure SeqConsNotMod_Example (q: in Integer; x: out Integer; p : in out Integer)
   is
   begin
      x := p;
      p := q;
   end SeqConsNotMod_Example;
   
end SeqConsNotMod;