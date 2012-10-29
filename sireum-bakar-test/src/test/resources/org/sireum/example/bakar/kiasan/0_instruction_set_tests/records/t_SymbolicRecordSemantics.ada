package SymbolicRecordSemantics 
is

  type Object is
  record
    X, Y, Z : Integer;
  end record;

  function dummy return Boolean;

end SymbolicRecordSemantics;

package body SymbolicRecordSemantics
is
  function dummy return Boolean is begin return True; end dummy;
  
  function t_aggregate return Object is begin
    return Object'(X => 0, Y => 1, Z => 2);
  end t_aggregate;
  
  function t_SR_L1 return Integer 
  is 
    Q : Object;
  begin
    Q := t_aggregate;
    return Q.X;
  end t_SR_L1;

  function t_SR_L2(Q : Object) return Integer 
  is begin
    return Q.Y;
  end t_SR_L2;
  
  procedure t_SR_U(Q : in out Object)
    --# derives Q from Q;
    --# pre Q.X /= 1;
    --# post Q = Q~[X => 1];
  is 
  begin
    Q.X := 1;
  end t_SR_U;

  procedure t_SR_U_BAD(Q : in out Object)
    --# derives Q from Q;
    --# pre Q.X /= 1;
    --# post Q = Q~[X => 1; Y => 3];
  is 
  begin
    Q.X := 1;
  end t_SR_U_BAD;
  
  function t_SR_EQUAL(Q, P : in Object) return Boolean is begin
    return Q = P;
  end t_SR_EQUAL;

end SymbolicRecordSemantics;