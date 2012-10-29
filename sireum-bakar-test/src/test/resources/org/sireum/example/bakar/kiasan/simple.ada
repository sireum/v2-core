package arithmetic 
  --#own D, E;
  --#initializes E;
  
  is
  procedure add(X: in Integer; Y: in Integer; Z: out Integer);
  --# global in D;
  --# derives Z from X, Y, D;

  procedure initD;
  --#global out D;
  --#derives D from;
  
end arithmetic;

package body arithmetic is
  D: Integer;
  E: Integer;
  
  function boolTest1 return Boolean
  --# global in D;
  is
    ret: Boolean;
  begin
  	-- assert D > 2 and
  	--  D < 5;
  	
    if D > 2 then
      ret := True;
    else
      ret := False;
    end if;
    return ret;
  end boolTest1;
  
  procedure add(X: in Integer; Y: in Integer; Z: out Integer)
  is
  begin
    Z := X + (Y + D);
  end add;

  procedure initD is
  begin
    D := 0;
  end initD;
  
begin
  E := 1;
end arithmetic;