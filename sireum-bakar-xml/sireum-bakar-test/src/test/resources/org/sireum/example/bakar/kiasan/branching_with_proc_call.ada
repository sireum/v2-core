package arithmetic 
  --#own D, E;
  --#initializes E;
  
  is
  procedure helper(W: in Integer; X: in Integer; Y: in out Integer; Z: out Integer);
  --#global in D;
  --#derives Y from W, D, X, Y & 
  --#        Z from W, D, X, Y;
  
  procedure add(Z: in out Integer);
  --# global in D;
  --# derives Z from Z, D;
  
  procedure initD;
  --#global out D;
  --#derives D from;
  
end arithmetic;

package body arithmetic is
  D: Integer;
  E: Integer;
  
  procedure helper(W: in Integer; X: in Integer; Y: in out Integer; Z: out Integer)
  is
  begin
    Y := (D + X) + (Y + W);
    Z := X + Y;
  end helper;

  function dummyFunc2(A : in Integer; B : in Integer) return Boolean
  is
  begin
    return A > B;
  end dummyFunc2;
  
  function dummy_Func return Boolean 
  --# global in D;
  is
  begin
    return D > 2;
  end dummy_Func;
  
  procedure add(Z: in out Integer)
  is
  T: Integer;
  Q: Integer;
  F_d_c: Boolean;
  begin
    Q := Z + ((-(7 * 3)) - (-(-(Z))));
    F_d_c := not(dummyFunc2(Z, Q) and Z > 2);
    if (not((D > 2 or Q > 7) and not (dummy_Func = True) and not dummy_Func and not F_d_c) and not(D + Q < 7)) then
      helper(Q, (Q + Z) * 5, Z, T);  
      Z := 7 + Z + T;
    elsif (D < 1) then
      Z := -5 + Z;
    else
      Z := 30 - Z;
    end if;
  end add;
    
  procedure initD is
  begin
    D := 0;
  end initD;
  
begin
  E := 1;
end arithmetic;