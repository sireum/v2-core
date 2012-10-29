package P is
   type T is range -128 .. 128;

   procedure Inc(X : in out T);
   --# derives X from X;

end P;

package body P is
   procedure Inc(X : in out T)
   is
   begin
      X := X + 1;
   end Inc;
end P;
