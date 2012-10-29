-- An "Integer" version of the "Float" Exchange
-- procedure given by Barnes on p. 74 (ex0304a.ada).

package Exchange_Int is
   procedure Exchange(X, Y : in out Integer);
   --# derives X from Y &
   --#         Y from X;
   --# post X = Y~ and Y = X~;
end Exchange_Int;

package body Exchange_Int is
   procedure Exchange(X, Y : in out Integer)
   is
      T : Integer;
   begin
      T := X; X := Y; Y := T;
   end Exchange;
end Exchange_Int;



