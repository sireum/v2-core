package QuantifierTest 
--# own V;
--# initializes V;
is

  type Index is range 1..10;
  type Vector is array (Index) of Integer;
  
  V:Vector;
  
  procedure dummy(I : in Index);
  --# global in out V;
  --# derives V from V, I;
  
end QuantifierTest;

package body QuantifierTest
is

  procedure dummy(I: in Index) is
  begin
    --# assert for some J in Index range 1..2 => (V(J) > 0 and V(J) < 7);
    V(I) := V(I) + 3;
    --# assert 3=3 and then (for all J in Index => (V(J) > 0 or V(J) < 7)); 
  end dummy;
  
begin
  V := Vector'(others=>0);
end QuantifierTest;
