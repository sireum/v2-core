package M is

   -- Solve simultaneous equations
   --    A.x + B.y = P
   --    C.x + D.y = Q
   -- for variables x and y.
   procedure Solve(A, B, C, D : in     Integer;
                   P, Q       : in     Float;
                   X, Y       :    out Float);
   --# derives X, Y from A, B, C, D, P, Q;
   --# pre ((A * D) /= (B * C));
   --# post (Float (A) * X) + (Float (B) * Y) = P and
   --#      (Float (C) * X) + (Float (D) * Y) = Q;

end M;



