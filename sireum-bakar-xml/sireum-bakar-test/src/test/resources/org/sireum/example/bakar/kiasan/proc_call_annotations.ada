package proc_calls 
--# own globA;
--# initializes globA;

--@ invariant globA >= 0;
is
  
  globA : Integer := 3;
  
  procedure P(a: in Integer; b: in out Integer; c: out Integer);
  --# global in out globA;
  --# derives b, c from a, b &
  --#         globA from globA, a, b;
  --# pre a > b;
  --# post c > b~ and b = a + b~ and globA /= globA~;

  procedure helper(x: in out Integer; y: out Integer);
  --# global in out globA;
  --# derives x, y from x &
  --#         globA from x, globA;
  --# pre (for all i in Integer range 0..10 => (x /= i)) and x > 9;
  --# post y = (1 + 2) + x~;
    
end proc_calls;

package body proc_calls
--@ invariant globA <= 10;
is

  procedure P(a: in Integer; b: in out Integer; c: out Integer) is
  begin
    --# assert a > b;
    b := a + b;
  	c := a + b;
  	--# assert for all i in Integer range 0..10 => (a /= i);
  	globA := globA + b;
  end P;
  
  procedure helper(x: in out Integer; y: out Integer) is
    arg0: Integer;
  begin
  	arg0 := 1 + 2;
  	P(arg0, x, y);
  end helper;
  
end proc_calls;