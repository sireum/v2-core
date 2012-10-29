package arithmetic 
  is
    procedure dummy_Func(a : in Integer; b : in Integer; r : out Boolean);
  --# derives r from a, b;
end arithmetic;

package body arithmetic is
  
  procedure dummy_Func(a : in Integer; b : in Integer; r : out Boolean)
  is
  begin
    if (a > b and then b > 5) or else b = 5 
      -- hi there!!!
    
          -- I like returns!!!
    then
      r := True;
    elsif a > b then
      r := False;
    elsif a > b and (b > 5 or else b = a) then
      r := True;
    elsif b < a or b > 5 then
      r := False;
    else
      r := False;
    end if;
  end dummy_Func;
end arithmetic;