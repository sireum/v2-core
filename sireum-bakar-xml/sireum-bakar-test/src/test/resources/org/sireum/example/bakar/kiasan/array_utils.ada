package Array_Utils
is
  type Vector is array(Integer range <>) of Integer;

  function isSorted(Z : Vector) return Boolean;

  procedure Util_BubbleSort(A : in out Vector);
  --# derives A from A;
  --# post for all I in Integer range A'First .. A'Last - 1 => (A(I) <= A(I + 1));
  -- post isSorted(A);

  procedure Util_BubbleSort_using_SwapElements(A : in out Vector);
  --# derives A from A;
  --# post for all I in Integer range A'First .. A'Last - 1 => (A(I) <= A(I + 1));
    
  procedure Util_InsertionSort(A : in out Vector);
  --# derives A from A;
  --# post for all I in Integer range A'First .. A'Last - 1 => (A(I) <= A(I + 1));
  
  procedure Util_SelectionSort(A : in out Vector);
  --# derives A from A;
  --# post for all I in Integer range A'First .. A'Last - 1 => (A(I) <= A(I + 1));
  
end Array_Utils;

package body Array_Utils is

  procedure Util_BubbleSort(A : in out Vector)
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
  end Util_BubbleSort;

  procedure SwapElements(A : in out Vector; I, J : in Integer) 
    --# derives A from A, I, J;
    --# pre I in A'Range and J in A'Range;
    --# post A(I) = A~(J) and A(J) = A~(I);
    -- post A~ = A[I => A(J); J => A(I)];
  is  
    Temp : Integer;
  begin
    Temp := A(I);
    A(I) := A(J);
    A(J) := Temp;
  end SwapElements;
  
  procedure Util_BubbleSort_using_SwapElements(A : in out Vector)
  is
    Swapped : Boolean;
    N : Integer;
  begin
    N := A'Last;
    loop 
      Swapped := False;
      for I in Integer range A'First .. N - 1 loop
        if A(I) > A(I + 1) then
          SwapElements(A, I, I + 1);
          Swapped := True;
        end if;
      end loop;
      N := N - 1;
      exit when not Swapped;
    end loop; 
  end Util_BubbleSort_using_SwapElements;
  
  procedure Util_InsertionSort (A : in out Vector)
  is
    Value : Integer;
    J : Natural;
  begin
    for I in Integer range A'First + 1 .. A'Last loop
      Value := A(I);
      J := I - 1;
      while J >= A'First and then A(J) > Value loop
        A(J + 1) := A(J);
        J := J - 1;
      end loop;
      A(J + 1) := Value;
    end loop;
  end Util_InsertionSort;
  
  procedure Util_SelectionSort (A : in out Vector)
  is
    Min : Integer;
    Temp : Integer;
  begin
    for I in Integer range A'First .. A'Last - 1 loop
      Min := I;
      for J in Integer range I + 1 .. A'Last loop
        if A(Min) > A(J) then
          Min := J;
        end if;
      end loop;
      if Min /= I then
        Temp := A(I);
        A(I) := A(Min);
        A(Min) := Temp;
      end if;
    end loop;
  end Util_SelectionSort;
  
  function isSorted(Z : Vector) return Boolean is
    B : Boolean := True;
  begin
    for I in Integer range Z'First .. Z'Last - 1  loop
      B := Z(I) <= Z(I + 1);
      exit when not B;
    end loop;
    return B;
  end isSorted;
end Array_Utils;