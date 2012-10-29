package Simple_Sort
is
  Max_Elements : constant := 3;
  
  subtype Index_Type is Integer range 1 .. Max_Elements;
  type Vector is array(Index_Type) of Integer;

  function isSorted(Z : Vector) return Boolean;
  function isPerm(A, B : Vector) return Boolean;
  function isSet(A : Vector) return Boolean;
  
  procedure BubbleSort(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);
  --# post isSorted(A) and then isPerm(A, A~);

  procedure BubbleSort_using_SwapElements(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);  
  --# post isSorted(A) and then isPerm(A, A~);
    
  procedure InsertionSort(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);  
  --# post isSorted(A) and then isPerm(A, A~);
  
  procedure SelectionSort(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);  
  --# post isSorted(A) and then isPerm(A, A~);
  
  procedure ShellSort(A : in out Vector);
  --# derives A from A;
  -- pre isSet(A);  
  --# post isSorted(A) and then isPerm(A, A~);
    
end Simple_Sort;

package body Simple_Sort is
 
  function isSorted(Z : Vector) return Boolean is
    B : Boolean;
  begin
    for I in Index_Type range Index_Type'First .. Index_Type'Last - 1 loop
      B := Z(I) <= Z(I + 1);
      exit when not B;
    end loop;
    return B;
  end isSorted;
  
  function Num_Repetitions(A : Vector; E : Integer) return Integer
  is
    Result : Integer := 0;
  begin
    for I in Index_Type loop
      if A(I) = E then
        Result := Result + 1;
      end if;
    end loop;
    return Result;
  end Num_Repetitions;
  
  function isSet(A : Vector) return Boolean
  is
    Result : Boolean;
  begin
    for I in Index_Type loop
      for J in Index_Type loop
        Result := I = J or else A(I) /= A(J);
        exit when not Result;
      end loop;
      exit when not Result;
    end loop;
    return Result;
  end isSet;
  
  function isPerm(A, B : Vector) return Boolean
  is 
    Result : Boolean := True;
  begin
    for I in Index_Type loop
      -- if elem at I - 1 is the same as elem at I then we already know the
      -- number of repetitions are equal
      if I = Index_Type'First or else A(I - 1) /= A(I) then
        Result := Num_Repetitions(A, A(I)) = Num_Repetitions(B, A(I));
      end if;
      exit when not Result;
    end loop;
    return Result;
  end isPerm;
  
  procedure BubbleSort(A : in out Vector)
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

  procedure SwapElements(A : in out Vector; I, J : in Integer) 
    --# derives A from A, I, J;
    --# pre I in A'Range and J in A'Range;
    --# post A~ = A[I => A(J); J => A(I)];    
  is  
    Temp : Integer;
  begin
    Temp := A(I);
    A(I) := A(J);
    A(J) := Temp;
  end SwapElements;
  
  procedure BubbleSort_using_SwapElements(A : in out Vector)
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
  end BubbleSort_using_SwapElements;
  
  procedure InsertionSort (A : in out Vector)
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
  end InsertionSort;
  
  procedure SelectionSort (A : in out Vector)
  is
    Min : Integer;
    Temp : Integer;
  begin
    for Pos in Index_Type loop
      Min := Pos;
      for I in Index_Type range Pos + 1 .. Index_Type'Last loop
        if A(I) < A(Min) then
          Min := I;
        end if;
      end loop;
      
      if Pos /= Min then
        Temp := A(Pos);
        A(Pos) := A(Min);
        A(Min) := Temp;
      end if;
    end loop;
  end SelectionSort;
  
  procedure ShellSort (A : in out Vector) is
    Increment : Natural := Index_Type'Last / 2;
    J : Index_Type;
    Temp : Integer;
  begin
    while Increment > 0 loop
      for I in Index_Type range Increment .. Index_Type'Last loop
        J := I;
        Temp := A(I);
        while J > Increment and then A(J - Increment) > Temp loop
          A(J) := A(J - Increment);
          J := J - Increment;
        end loop;
        A(J) := Temp;
      end loop;

      Increment := Increment / 2;
    end loop;
  end ShellSort;
  
end Simple_Sort;