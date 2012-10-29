package SimpleMath1 
--# own B;
--# initializes B;
--@ invariant B >= Integer'First + 1;
is
  procedure Inc(I: in out Integer);
  --# global in B;
  --# derives I from I, B;
  --# pre I < Integer'Last;
  --# post I = I~ + 1;
end SimpleMath1;

package body SimpleMath1
--@ invariant B >= 0 and then
--@           B <= Integer'Last;
--@ invariant B >= 5;     
is
  B : Integer := 0;
  procedure Inc(I: in out Integer) is
  begin
    I := I+B;
  end Inc;
end SimpleMath1;
