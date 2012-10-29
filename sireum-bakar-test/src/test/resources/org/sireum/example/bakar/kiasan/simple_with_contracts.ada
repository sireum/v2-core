package arithmetic 
  --# own D : Integer; E;
  --# initializes E;
  
  is
  procedure add(X: in Integer; Y: in Integer; Z: out Integer);
  --# global in out D; in E;
  --# derives Z from X, Y, D, E &
  --#         D from D;
  --# pre D >= 0;
  --# post (D~ >= 3 and then (Z = X + (Y + D))) or else
  --#      (D~ <= 2 and then (Z = X - (Y - D)));
  
  procedure initD;
  --# global out D;
  --# derives D from;
  
end arithmetic;

package body arithmetic is
  D: Integer;
  E: Boolean;
  
  function boolTest1 return Boolean
  --# global in D, E;
  is
    ret: Boolean;
  begin
    if D >= 3 then
      ret := E;
    else
      ret := not E;
    end if;
    
    --#assert D >= 3 -> ret = E;
    return ret;
  end boolTest1;
  
  procedure add(X: in Integer; Y: in Integer; Z: out Integer)
  is
  begin
    D := D + 1;
    if (boolTest1) then
      Z := X + (Y + D);
    else
      Z := X - (Y - D);
    end if;
    
  end add;

  procedure initD is
  begin
    D := 0;
  end initD;
  
begin
  E := False;
end arithmetic;