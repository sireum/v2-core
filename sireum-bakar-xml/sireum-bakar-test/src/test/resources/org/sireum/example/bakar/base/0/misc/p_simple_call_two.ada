package P
--# own Y, B, C;  -- Y is spec-public, B is body-declared, C is spec-private
--# initializes Y, B, C;

is
  Y: Integer;
  procedure Q(X: in Integer; Z: out Integer);
  --# global in B, Y, C;
  --# derives Z from X, B, Y, C;

  procedure R(X: in Integer; Z: out Integer);
  --# global in Y, B, C;
  --# derives Z from X, Y, B, C;

private
   C: Integer;
end P;

package body P is
  B: Integer;

  procedure R(X: in Integer; Z: out Integer) is
  begin
     Z := X + Y + B + C;
  end R;

  procedure Q(X: in Integer; Z: out Integer)
  is
  begin
     R(X,Z);
     Z := Z + X;
  end Q;

begin
   Y := 1;
   B := 0;
   C := 0;
end P;


