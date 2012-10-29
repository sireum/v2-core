package P is
   procedure Exchange(X, Y : in out Float);
   --# derives X from Y &
   --#         Y from X;
end P;

package body P is
   procedure Exchange(X, Y : in out Float)
   is
      T : Float;
   begin
      T := X; X := Y; Y := T;
   end Exchange;
end P;



