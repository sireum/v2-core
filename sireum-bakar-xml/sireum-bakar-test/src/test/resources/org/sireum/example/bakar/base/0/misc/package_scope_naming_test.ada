package P
--# own Y;
--# initializes Y;
is
  Y: Integer;
  procedure Q(X: in Integer; Z: out Integer);
  --# derives Z from X;
end P;

package body P is
  --# inherit P;
  package P2
  is
    procedure R(X: out Integer);
    --# global in P.Y;
    --# derives X from P.Y;
  end P2;

  package body P2 is
    procedure R(X: out Integer) is 
    begin
      X := P.Y;
    end R;
  end P2;

  procedure Q(X: in Integer; Z: out Integer) is
  begin
    Z := X;
  end Q;

begin
  Y := 1;
end P;

--#inherit P;
package P.P2
--# own Q;
--# initializes Q;
is
  Q: Integer;
  procedure Q2(X: in Integer; Y: out Integer; Z: out Integer);
  --# global in P.Y;
  --# derives Y from X &
  --#         Z from P.Y;

end P.P2;

package body P.P2 is

  procedure Q2(X: in Integer; Y: out Integer; Z: out Integer) is
  begin
    Y := X;
    Z := P.Y;
  end Q2;

begin
  Q := 3 + 2;
end P.P2;
  
  