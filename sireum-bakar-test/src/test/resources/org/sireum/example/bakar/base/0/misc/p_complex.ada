package P
--# own Y, B : Integer; C : Integer; AbsD, AbsAbsQ;
--# initializes Y, B, C, AbsD, AbsAbsQ;
is
  Y: Integer;
  procedure Q(X: in Integer; Z: out Integer);
  --# global in B;
  --# derives Z from X, B;
private
   C: Integer;
end P;

package body P
  --# own AbsD is P2.D &
  --#     AbsAbsQ is P2.AbsQ;
is

   B: Integer;

   --# inherit P;
   package P2
     --# own D, AbsQ;
     --# initializes D, AbsQ;
   is
      procedure R(X: out Integer);
      --# global in P.Y, P.B, P.C, D, AbsQ;
      --# derives X from P.Y, P.B, P.C, D, AbsQ;
   end P2;

   package body P2
     --# own AbsQ is P3.Q;
   is
     D: Integer;

     --# inherit P, P2;
     package P3
       --# own Q;
       --# initializes Q;
     is
        Q: Integer;
        procedure Q2(X: in Integer; Y: out Integer; Z: out Integer);
        --# global in P.Y, P.C, P2.D;
        --# derives Y from X &
        --#         Z from P.Y, P.C, P2.D;
     end P3;

     package body P3 is

        procedure Q2(X: in Integer; Y: out Integer; Z: out Integer) is
        begin
           Y := X;
           Z := (P.Y + P.C) + P2.D;
        end Q2;

     begin
        Q := 3;
     end P3;

     procedure R(X: out Integer)
       --# global in P.Y, P.B, P.C, D, P3.Q;
       --# derives X from P.Y, P.B, P.C, D, P3.Q;
     is
     begin
        X := (((P.Y + P.B) + P.C) + D) + P3.Q;
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
