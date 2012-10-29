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
  --# derives x, y from x, globA &
  --#         globA from x, globA;
  --# pre (for all i in Integer range 1..10 => (x /= i and then i = i)) and x > 9;
  --# post y = ((globA~ + globA) + x~) and then (x = x~);

  procedure subtract(a: in Integer; b: in out Integer);
  --# derives b from a, b;
  
end proc_calls;

package body proc_calls
--@ invariant globA <= 10;
is
  --# function proofFunc return Integer;
  
  function Z_PRIVATE (I : Integer) return Integer is
  begin
    return I;
  end Z_PRIVATE;
  
  function Q_PRIVATE (I : Integer) return Integer
    --# global globA;
    --# return g => g > 2;
    -- return g => g > proofFunc;
  is
  begin
    -- assert proofFunc > 9;
    -- assert globA > 9;
    return globA + I;
  end Q_PRIVATE;

  procedure subtract(a: in Integer; b: in out Integer) is
  begin
    b := a - b;
  end subtract;

  procedure P(a: in Integer; b: in out Integer; c: out Integer) is
  begin
    subtract(a, b);
    
    --# assert b = a - b~;
    b := a + b;
    --# assert b = b~;
    
    b := Z_PRIVATE(b);
    
    for I in Integer range 1 .. b
    loop
      b := b + 1;
    end loop;
    
  	c := a + b;
  	--# assert for all i in Integer range 0..a => (a + b~ /= i);
  	globA := Q_PRIVATE(1) + b;
  end P;
  
  procedure helper(x: in out Integer; y: out Integer) is
    arg0: Integer;
  begin
  	arg0 := Q_PRIVATE(2);
  	P(arg0, x, y);
  end helper;
  
  
end proc_calls;