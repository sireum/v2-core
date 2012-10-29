package Array_Unconstrained
is
  type Vector is array(Integer range <>) of Integer;
  
  function isSorted(Z : Vector) return Boolean;
  function indexIsValue(Z : Vector; Index, V : Integer) return Boolean;
end Array_Unconstrained;

package body Array_Unconstrained is

  function isSorted(Z : Vector) return Boolean is
    B : Boolean := True;
  begin
    for I in Integer range Z'First .. Z'Last - 1  loop
      B := Z(I) <= Z(I + 1);
      exit when not B;
    end loop;
    return B;
  end isSorted;

  function indexIsValue(Z : Vector; Index, V : Integer) return Boolean is
  begin
    return Z(Index) = V;
  end indexIsValue;
end Array_Unconstrained;


with Array_Unconstrained;
--# inherit Array_Unconstrained;
package T is
  subtype Index is Integer range 0 .. 10;
  subtype ConsVector is Array_Unconstrained.Vector(Index);
  
  function isSorted(CV : ConsVector) return Boolean;
  function indexIsValue(Z : ConsVector; I : Index; V : Integer) return Boolean;
end T;

package body T is
  function isSorted(CV : ConsVector) return Boolean is
  begin
    return Array_Unconstrained.isSorted(CV);
  end isSorted;
  
  function indexIsValue(Z : ConsVector; I : Index; V : Integer) return Boolean is
  begin
    return Array_Unconstrained.indexIsValue(Z, I, V);
  end indexIsValue;  
end T;
