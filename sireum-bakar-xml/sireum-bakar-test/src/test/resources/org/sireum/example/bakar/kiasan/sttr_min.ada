package sttr is
  function Min(A, B : Integer) return Integer;

  function Min_Contract(A, B : Integer) return Integer;
  --# return R => (R <= A) and (R <= B) and (R = A or R = B);
  
end sttr;

package body sttr is

  function Min(A, B : Integer) return Integer is
    Z : Integer;
  begin
    Z := A;
    if B > A then
      Z := B;
    end if;

    --# accept Flow_Message, 10, "Ineffective statement";
    if Z > A or else Z > B then
      --# assert false;
      null;
    end if;

    return Z;
  end Min;

  function Min_Contract(A, B : Integer) return Integer is
    Z : Integer;
  begin
    Z := A;
    if B > A then
      Z := B;
    end if;
    return Z;
  end Min_Contract;
  
end sttr;