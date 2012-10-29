package P
--# own Y, B, C;  -- Y is spec-public, B is body-declared, C is spec-private
--# initializes Y, B, C;

is
   Y: Integer;

  function F1(X: in Integer; Z: in Integer) return Integer;
  --# global in Y;

private
   C: Integer;
end P;

package body P is
  B: Integer;

  function F1(X: in Integer; Z: in Integer) return Integer is
  begin
     return X + Z + Y;
  end F1;

  procedure P1(M: in Integer; N: in Integer)
  --# global in Y; out B;
  --# derives B from M, N, Y;
  is
  begin
     B := F1(M,N);
  end P1;

  procedure P2(M: in Integer; N: in Integer)
  --# global in C, Y; out B;
  --# derives B from M, N, C, Y;
  is
  begin
     B := F1(M,N) + C;
  end P2;

begin
   Y := 1;
   B := 0;
   C := 0;
end P;
