package P
--# own Y, B, C, AbsD;
--# initializes Y, B, C, AbsD;
is
  Y: Integer;
  procedure Q(X: in Integer; Z: out Integer);
  --# global in B;
  --# derives Z from X, B;
private
   C: Integer;
end P;

package body P
  --# own AbsD is P2.D;
is
   B: Integer;

   --# inherit P;
   package P2
     --# own D;
     --# initializes D;
   is
      procedure R(X: out Integer);
      --# global in P.Y, P.B, P.C, D;
      --# derives X from P.Y, P.B, P.C, D;
   end P2;

   package body P2
   is
     D: Integer;

     procedure R(X: out Integer)
     is
     begin
        X := P.Y + P.B + P.C + D;
     end R;

  begin
     D := 0;
  end P2;

  procedure Q(X: in Integer; Z: out Integer)
  is
  begin
     Z := X + B;
  end Q;

begin
   Y := 1;
   B := 0;
   C := 0;
end P;


