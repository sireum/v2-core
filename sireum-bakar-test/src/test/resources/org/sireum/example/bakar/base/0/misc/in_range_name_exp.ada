package in_range_name_exp
is
  function test(Z : Integer) return Boolean;
end in_range_name_exp;

package body in_range_name_exp
is

  function test(Z : Integer) return Boolean
  is
    ret : Boolean;
  begin
    ret := Z in Natural;
        
    if ret then
      ret := Z in 1 .. 10;
    end if;
    
    if ret then
      ret := Z not in Positive;
    end if;
    
    if ret then
      ret := Z not in 3 .. 5;
    end if;
    
    return ret;
  end test;

end in_range_name_exp;