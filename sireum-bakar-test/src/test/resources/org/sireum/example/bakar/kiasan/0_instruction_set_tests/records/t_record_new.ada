package RecordNewTest 
is

  type Object is
  record
    X, Y, Z : Integer;
  end record;

  function dummy return Boolean;

end RecordNewTest;

package body RecordNewTest
is
  function dummy return Boolean is begin return True; end dummy;
  
  function t_aggregate return Object is begin
    return Object'(X => 0, Y => 1, Z => 2);
  end t_aggregate;
  
  function t_lookup_x(Q : Object) return Integer 
  is begin
    return Q.X;
  end t_lookup_x;

  function t_lookup_y(Q : Object) return Integer 
  is begin
    return Q.Y;
  end t_lookup_y;
  
  procedure t_swap_values(Q : in out Object)
    --# derives Q from Q;
  is 
    Temp : Integer;
  begin
    Temp := Q.X;
    Q.X := Q.Y;
    Q.Y := Temp;
  end t_swap_values;

  procedure t_swap_distinct_values(Q : in out Object) 
    --# derives Q from Q;
  is 
    Temp : Integer;
  begin
    if Q.X /= Q.Y then
      Temp := Q.X;
      Q.X := Q.Y;
      Q.Y := Temp;
    end if;
  end t_swap_distinct_values;

  function t_swap_values_aggregate return Object is 
    Q : Object;
    Temp : Integer;
  begin
    Q := t_aggregate;
    Temp := Q.X;
    Q.X := Q.Y;
    Q.Y := Temp;
    return Q;
  end t_swap_values_aggregate;

  procedure t_increment_x(Q : in out Object) 
    --# derives Q from Q;
    --# pre Q.X < Integer'Last;
    --# post Q~.Y = Q.Y + 1;
  is begin
    Q.X := Q.X + 1;
  end t_increment_x;
    
  procedure t_increment_x_FAIL(Q : in out Object) 
    --# derives Q from Q;
    --# pre Q.X < Integer'Last;
    --# post Q~.Y = Q.Y + 1;
  is begin
    Q.X := Q.X - 1;
  end t_increment_x_FAIL;
      
  procedure t_decrement_y(Q : in out Object) 
    --# derives Q from Q;
    --# pre Q.Y > Integer'First;
    --# post Q.Y = Q~.Y - 1;
  is begin
    Q.Y := Q.Y - 1;
  end t_decrement_y;

  procedure t_decrement_y_FAIL(Q : in out Object) 
    --# derives Q from Q;
    --# pre Q.Y > Integer'First;
    --# post Q.Y = Q~.Y - 1;
  is begin
    Q.Y := Q.Y + 1;
  end t_decrement_y_FAIL;
end RecordNewTest;