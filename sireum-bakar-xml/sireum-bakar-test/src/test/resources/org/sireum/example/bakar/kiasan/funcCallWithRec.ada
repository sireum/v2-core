package arithmetic 
  --#own D;
  --#initializes D;
  
  is
  procedure modGlobal;
  --# global in out D;
  --# derives D from D;

  procedure add(X: in Integer; Y: in Integer; Z: out Integer);
  --# derives Z from X, Y;
  --# pre Y > 0;
  --# post Z = X + Y;
  
  procedure sub(X: in Integer; Y: in Integer; Z: out Integer);
  --# derives Z from X, Y;
  
end arithmetic;

package body arithmetic is
  D: Integer;
  
  procedure modGlobal
  is
  begin
  	--# assert D > 0 and then
  	--#        D <= 5;
    D := D + 1;
  end modGlobal;
  
  
	procedure foo(A : in out Integer)
	--# derives A from A;
	--# pre A > 0;
	is
	begin
	  --# assert A < 5;
	  A := A + 1;
	end foo;

  function boolTest(X: in Integer; YOU: in Integer; Z: in Boolean) return Boolean
  is
    Q: Boolean;
    P: Boolean := True;
  begin
  
    if (X >= 2 and (YOU > 3 + 2 or YOU < 7)) then
      P := False;
    end if;
    
    if X > 2 or X < 5 then
      P := True;
    end if;
    
    if X > YOU then
      Q := True;
    elsif X < YOU then
      Q := False;
    else
      Q := Z;
    end if;
 
    return Q and P;    
  end boolTest;
   
  procedure add(X: in Integer; Y: in Integer; Z: out Integer)
  is
  begin
    if X > Y and X = X then
      Z := X + Y;
    else
      Z := X + X;
    end if;
  end add;
  
  procedure sub(X: in Integer; Y: in Integer; Z: out Integer)
  is
  begin
    Z := (X - Y) - 1;
  end sub;
  
  function combine(X: in Integer; Y: in Integer) return Integer
  is
    addResult:Integer := 1;
    subResult:Integer;
  begin
  	if boolTest(X, Y, True) then
    	add(X, Y, addResult);
   	end if;
    sub(X, addResult, subResult);
    return subResult;
  end combine;
begin
  D := 0;
end arithmetic;