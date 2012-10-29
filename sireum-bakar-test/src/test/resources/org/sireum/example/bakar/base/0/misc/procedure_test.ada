package ProcedureTest01 is

  procedure P(a: in Integer; b: in out Integer; c: out Integer);
  --# derives c from a, b &
  --#         b from a, b;

  procedure helper(x: in out Integer; y: out Integer);
  --# derives x from *, x &
  --#         y from x;

  procedure Q(S: in Integer; T: out Integer; R: out Integer);
  --# derives T from S &
  --#         R from ;

end ProcedureTest01;

package body ProcedureTest01 is

  procedure P(a: in Integer; b: in out Integer; c: out Integer) is
  begin
    b := a + b;
    c := a + b;
  end P;

  procedure helper(x: in out Integer; y: out Integer) is
    arg0: Integer;
  begin
        arg0 := 1;
        P(arg0, x, y);
  end helper;

  procedure Q(S: in Integer; T: out Integer; R: out Integer) is
     L1 : Integer;
  begin
     L1 := S; -- slice on T, in slice
     P(2, L1, T); -- slice on T, in slice
     R := 5;
     T := T + L1; -- slice on T, in slice
  end Q;
end ProcedureTest01;
