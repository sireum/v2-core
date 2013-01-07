package body BinaryExpressions
is
  function X return Integer is begin return 1; end X;

  function Add(I, J : Integer) return Integer
  is
    r0, r1, r2, r3, r4, r5, r6 : Integer;
  begin
    r0 := 1 + 2;
    r1 := I + 1;
    r2 := I + I;
    r3 := I + J;
    r4 := J + I;
    r5 := J + J;
    r6 := J + 1;
    return r6;
  end Add;

  function Sub(I, J : Integer) return Integer
  is
    r0, r1, r2, r3, r4, r5, r6 : Integer;
  begin
    r0 := 1 - 2;
    r1 := I - 1;
    r2 := I - I;
    r3 := I - J;
    r4 := J - I;
    r5 := J - J;
    r6 := J - 1;
    return r6;
  end Sub;

  function Mult(I, J : Integer) return Integer
  is
    r0, r1, r2, r3, r4, r5, r6 : Integer;
  begin
    r0 := 1 * 2;
    r1 := I * 1;
    r2 := I * I;
    r3 := I * J;
    r4 := J * I;
    r5 := J * J;
    r6 := J * 1;
    return r6;
  end Mult;

  function Division(I, J : Integer) return Integer
  is
    r0, r1, r2, r3, r4, r5, r6 : Integer;
  begin
    r0 := 1 / 2;
    r1 := I / 1;
    r2 := I / I;
    r3 := I / J;
    r4 := J / I;
    r5 := J / J;
    r6 := J / 1;
    return r6;
  end Division;

end BinaryExpressions;