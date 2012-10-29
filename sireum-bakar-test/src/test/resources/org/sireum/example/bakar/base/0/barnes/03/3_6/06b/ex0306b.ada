package P is

   procedure CAB(A, B, C : in out Float);
   --# derives A from C &
   --#         B from A &
   --#         C from B;
   --# post A = C~ and B = A~ and C = B~;

end P;

package body P is

   procedure Exchange(X, Y : in out Float)
   --# derives X from Y &
   --#         Y from X;
   --# post X = Y~ and Y = X~;
   is
     T : Float;
   begin
      T := X;
      X := Y;
      --# check X = Y~;
      Y := T;
   end Exchange;

   procedure CAB(A, B, C : in out Float)
   is
   begin
      Exchange(A,B);
      Exchange(A,C);
   end CAB;

end P;
