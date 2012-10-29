package Sort is
  Max_Elements : constant := 3;

  subtype Base_Index_Type is Integer range 0 .. Max_Elements;
  subtype Index_Type is Base_Index_Type range 1 .. Base_Index_Type'Last;

  type Contents_Type is range -1000 .. 1000;
  type Array_Type is array(Index_Type) of Contents_Type;

  function Ordered(A : Array_Type; L,U : Index_Type) return Boolean;

  function Perm(A, B : Array_Type; L,U : Index_Type) return Boolean;

   procedure BubbleSort(Table : in out Array_Type);
   --# derives Table from Table;
   --# post Ordered(Table, Index_Type'First, Index_Type'Last) and 
   --#      Perm(Table, Table~, Index_Type'First, Index_Type'Last);

  procedure BubbleSortFast(Table : in out Array_Type);
    --# derives Table from Table;
    --# post Ordered(Table, Index_Type'First, Index_Type'Last) and
    --#   Perm(Table, Table~, Index_Type'First, Index_Type'Last);
    
  procedure InsertionSort(Table : in out Array_Type);
    --# derives Table from Table;
    --# post Ordered(Table, Index_Type'First, Index_Type'Last) and
    --#   Perm(Table,Table~, Index_Type'First, Index_Type'Last);
    
  procedure ShellSort(Table : in out Array_Type);
  --# derives Table from Table;
  --# post Ordered(Table, Index_Type'First, Index_Type'Last) and
  --#      Perm(Table, Table~, Index_Type'First, Index_Type'Last);
  
end Sort;

package body Sort is
  function Num_Repetition(A : Array_Type; E : Contents_Type; 
                          L,U : Index_Type) return Integer
  is 
    Result: Integer := 0;
  begin
    for I in Index_Type range L .. U loop
      if A(I) = E then
        Result := Result + 1;
      end if;
    end loop;
    return Result;
  end Num_Repetition;

  function Perm(A, B : Array_Type; L,U : Index_Type) return Boolean 
  is
    Result: Boolean := True;
  begin
    for I in Index_Type range L .. U loop
      if Num_Repetition(A, A(I), L, U) /= Num_Repetition(B, A(I), L, U) then
        Result := False;
      end if;
    end loop;
    return Result;
  end Perm;

  function Ordered(A: Array_Type; L,U : Index_Type) return Boolean
  is
    Result: Boolean := True;
  begin
    for I in Index_Type range L .. U - 1 loop
      if A(I) > A(I + 1) then
        Result := False;
      end if;
    end loop;
    return Result;
  end Ordered;

  function Partitioned(A : Array_Type;
                       L, M, U : Index_Type) return Boolean 
  --# pre Index_Type'First <= L and L <= M and M <= U and U <= Index_Type'Last;
  is
    Result: Boolean := True;
  begin
    for I in Index_Type range L .. M loop
      for J in Index_Type range M + 1 .. U loop
        if A(I) > A(J) then
          Result := False;
        end if;
      end loop;
    end loop;
    return Result;
  end Partitioned;
  
  procedure Swap_Elements(T : in out Array_Type; I, J : in Index_Type)
    --# derives T from T,I,J;
    --# post T = T~[I => T~(J); J => T~(I)] and 
    --#      Perm(T, T~, Index_Type'First, Index_Type'Last);
  is
    Temp : Contents_Type;
  begin
    Temp := T(I);
    T(I) := T(J);
    T(J) := Temp;
  end Swap_Elements;

  procedure BubbleSort(Table : in out Array_Type) is
    begin 
     for I in reverse Index_Type range Array_Type'Range loop
       for J in Index_Type range Array_Type'First + 1 .. I loop
         if Table(J - 1) > Table(J) then
           Swap_Elements(Table, J - 1, J);
         end if;
         --# assert (for all K in Index_Type range Array_Type'First .. J - 1 =>  
         --#          (Table(K) <= Table(J)))
         --#   and Perm(Table, Table~, Index_Type'First, Index_Type'Last); 
       end loop;
       --# assert Ordered(Table, I, Index_Type'Last)
       --#   and Partitioned(Table, Index_Type'First, I, Index_Type'Last)
       --#   and Perm(Table, Table~, Index_Type'First, Index_Type'Last);
     end loop;
  end BubbleSort;

  procedure BubbleSortFast(Table : in out Array_Type)
  is
    Bound: Base_Index_Type := Index_Type'Last;
    T : Base_Index_Type;
    J : Index_Type;
  begin 
    loop exit when Bound = 0;
      J := Index_Type'First;
      T := 0;
      while J <= Bound - 1 loop
        if Table(J) > Table(J + 1) then
          Swap_Elements(Table, J, J + 1);
          T := J;
        end if;
        J := J + 1;
                 
        --# assert (for all K in Index_Type range Index_Type'First .. J - 1 => 
        --#           (Table(K) <= Table(J))) and
        --#        Partitioned(Table, Index_Type'First, T, J) and 
        --#        Perm(Table, Table~, Index_Type'First, Index_Type'Last);
      end loop;
      --# assert Ordered(Table, Bound, Index_Type'Last) and
      --#         Partitioned(Table, Index_Type'First, Bound, Index_Type'Last) 
      --#         and Perm(Table, Table~, Index_Type'First, Index_Type'Last);
             
      Bound := T;
    end loop;
  end BubbleSortFast;
    
  procedure InsertionSort(Table : in out Array_Type) 
  is
    J : Index_Type;
    Temp: Contents_Type;
  begin 
    for P in Index_Type range Index_Type'First + 1 .. Index_Type'Last loop
      Temp := Table(P);
      J := P;
      while (J >= Index_Type'First + 1 and then Table(J - 1) > Temp) loop
        Table(J) := Table(J - 1);
        J := J - 1;
        --# assert Temp = Table~(P) and Index_Type'First <= J and J < P and
        --#   (for all K in Index_Type range J + 1 .. P => 
        --#      (Table(K) > Temp)) and 
        --#   Ordered(Table, Index_Type'First, P) and
        --#   Perm(Table[J => Temp], Table~, Index_Type'First, P); 
      end loop;
      
      Table(J) := Temp;
      
      --# assert Index_Type'First + 1 <= P and P <= Index_Type'Last and
      --#      Ordered(Table, Index_Type'First, P) and
      --#      Perm(Table, Table~, Index_Type'First, P) and 
      --#      (for all K in Index_Type range P + 1 .. Index_Type'Last => 
      --#        (Table(K) = Table~(K)));
    end loop;
    
  end InsertionSort;
  
  procedure ShellSort(Table : in out Array_Type) 
  is
    Gap : Index_Type;
    J : Integer;
  begin 
    Gap := Index_Type'Last;
    loop
      Gap := Gap / 2;
      for I in Index_Type range Gap .. Index_Type'Last loop
        J := I - Gap;
        while J >= Index_Type'First and then Table(J) > Table(J + Gap) loop
          Swap_Elements(Table, J, J + Gap);
          J := J - Gap;
          -- assert (for all K in Index_Type range J + Gap .. I => (
          --          K - L /= Gap or Table(L) <= Table(K))) 
          --        and Perm(Table, Table~, J + Gap, I); 
        end loop;
        -- assert (for all K,L in Index_Type range 1 .. I => (
        --           K - L /= Gap or Table(L) <= Table(K))) 
        --        and Perm(Table, Table~, 1, I); 
      end loop;
      exit when Gap = Index_Type'First;
      -- assert (for all K,L in Index_Type range 1..Max_Table_Size => (
      --           K - L != Gap) or Table(L) <= Table(K);
    end loop;
  end ShellSort;
end Sort;
