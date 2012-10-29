package GCD2_Package is
  function gcd_proof(M : in Integer; N : in Integer) return Integer;

  procedure GCD(M, N : in Integer; G : out Integer);
  --# derives G from M, N;
  --# pre M >= 0 and N > 0;
  --# post G = gcd_proof(M, N);

end GCD2_Package;

package body GCD2_Package is

  function gcd_proof(M : in Integer; N : in Integer) return Integer
  is
    a : Integer;
    b : Integer;
    result : Integer;
  begin
    a := M;
    b := N;
    if a = 0 then
      result := b;
    else
      while b /= 0 loop
        if a > b then
          a := a - b;
        else 
          b := b - a;
        end if;
      end loop;
      result := a;
    end if;
    return result;
  end gcd_proof;

  procedure Divide(M, N : in Integer;
                   Q, R : out Integer)
  --# derives Q, R from M, N;
  --# pre (M >= 0) and (N > 0);
  --# post (M = Q * N + R) and (R < N) and (R >= 0);
  is
  begin
    Q := 0;
    R := M;
    loop
      --# assert (M = Q * N + R) and (R >= 0);
      exit when R < N;
      Q := Q + 1;
      R := R - N;
    end loop;
  end Divide;

  procedure Swap(X, Y : in out Integer)
  --# derives X from Y & Y from X;
  --# post X = Y~ and Y = X~;
  is
    T : Integer;
  begin
    T := X; 
    X := Y; 
    Y := T;
  end Swap;
   
  procedure GCD(M, N : in Integer; G : out Integer)
  is
    C, D : Integer;
    Q, R : Integer;
  begin
    C := M;
    D := N;
    while D /= 0 loop
      --# assert C >= 0 and D > 0;
      --# accept Flow_Message, 10, Q, "Assignment to Q is ineffective";
      --# accept Flow_Message, 33, Q, "The variable Q is neither referenced nor exported";
      Divide(C,D,Q,R); -- Q is unused
      C := R;
      Swap(C,D);
    end loop;
    G := C;
  end GCD; -- Q is unused
  
end GCD2_Package;