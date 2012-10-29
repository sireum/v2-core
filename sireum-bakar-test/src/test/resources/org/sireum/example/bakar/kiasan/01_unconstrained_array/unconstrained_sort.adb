package body Unconstrained_Sort
is
  type Vector is array(Integer range <>) of Integer;
    
  function dummy return Integer
  is begin
    return 1;
  end dummy;
   
  function isSorted(Z : Vector) return Boolean
  --# return isSorted(Z); 
  is
    B : Boolean := True;
  begin
    for I in Integer range Z'First .. Z'Last - 1 loop
      B := Z(I) <= Z(I + 1);
      exit when not B;
    end loop;
    return B;
  end isSorted;

  procedure BubbleSort(A : in out Vector)
    --# derives A from A;
    --# post A(33) = 5 and then isSorted(A);
  is
    Swapped : Boolean;
    Temp : Integer;
    N : Integer;
  begin
    N := A'Last;
    loop 
      Swapped := False;
      for I in Integer range A'First .. N - 1 loop
        if A(I) > A(I + 1) then
          Temp := A(I);
          A(I) := A(I + 1);
          A(I + 1) := Temp;
          Swapped := True;
        end if;
      end loop;
      N := N - 1;
      exit when not Swapped;
    end loop; 
  end BubbleSort;
end Unconstrained_Sort;
