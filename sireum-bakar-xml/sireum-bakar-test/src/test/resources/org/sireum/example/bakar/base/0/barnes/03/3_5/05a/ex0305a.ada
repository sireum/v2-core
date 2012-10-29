package P is
   procedure Divide(M, N : in Integer;
                    Q, R : out Integer);
   --# derives Q, R from M,N;
   --# pre (M >= 0) and (N > 0);
   --# post (M = Q * N + R) and (R < N) and (R >= 0);
end P;

package body P is
   procedure Divide(M, N : in Integer;
                    Q, R : out Integer)
   is
   begin
      Q := 0;
      R := M;
      loop
         --# assert (M = Q * N + R) and (R >= 0);
         exit when R < N;
         Q := Q + 1;
         R := R - N;
      end loop;
   end Divide;
end P;
