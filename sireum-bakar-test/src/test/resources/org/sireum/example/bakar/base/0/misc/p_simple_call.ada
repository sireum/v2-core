package P
--# own Y, B, C;  -- Y is spec-public, B is body-declared, C is spec-private
--# initializes Y, B, C;

is
  Y: Integer;
  procedure Q(X: in Integer; Z: out Integer);
  --# global in B, Y, C;
  --# derives Z from X, B, Y, C;
  
  procedure R(X: out Integer);
  --# global in Y, B, C;
  --# derives X from Y, B, C;
  
private
   C: Integer;
end P;


package body P is
  B: Integer;

  procedure R(X: out Integer) is
  begin
     X := Y + B + C;
  end R;
 
  function incX(X : Integer) return Integer
    --# return incX(X);
  is
    result: Integer;
  begin
    if(X = 0) then
      result := X + 1;
    else
      result := X - 1;
    end if;
    return result;
  end incX;
  
  procedure Q(X: in Integer; Z: out Integer)
  is
  begin
     R(Z);
     Z := Z + incX(X);
  end Q;

begin
   Y := 1;
   B := 0;
   C := 0;
end P;