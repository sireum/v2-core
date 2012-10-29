-- Eculidean algorithm
 
package GCD_Package
is

  function gcd(M, N : Integer) return Integer;
  --# pre M >= 0 and N >= 0;
  --# --pre M >= 2 and N >= 2;

end GCD_Package;

package body GCD_Package
is

  function gcd(M, N : Integer) return Integer
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
  end gcd;

end GCD_Package;