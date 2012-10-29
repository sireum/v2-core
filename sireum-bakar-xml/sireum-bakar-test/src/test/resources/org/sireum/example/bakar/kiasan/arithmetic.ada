package arithmetic is

  procedure sub(X: in Integer; Y: in Integer; Z: out Integer);
  --# derives Z from X, Y;
  
end arithmetic;

package body arithmetic is
  
  procedure sub(X: in Integer; Y: in Integer; Z: out Integer)
  is
  begin
    if X > Y then
      Z := X - Y;
    else 
      Z := Y - X;
    end if;
  end sub;

  procedure alsoSub(X, Y : Integer; Z : out Integer)
  --# derives Z from X, Y;
  is
  begin
    sub(X, Y, Z);
  end alsoSub;
  
  function combine(X: in Integer; Y: in Integer) return Integer
  is
    subResult:Integer;
    alsoSubResult:Integer;    
  begin
  
    sub(X, Y, subResult);

    alsoSub(Y, X, alsoSubResult);
    
    return subResult + alsoSubResult;
  end combine;
  
  function trueBranch(X, Y : Integer) return Integer
  --# pre X > Y;
  is
    result : Integer;
  begin
    sub(X, Y, result);
    return result;
  end trueBranch;
  
  function falseBranch(X, Y : Integer) return Integer
  --# pre X <= Y;
  is
    result : Integer;
  begin
    sub(X, Y, result);
    return result;
  end falseBranch;
  
end arithmetic;