package Array_Utilities is
   Max_Table_Size : constant := 100;
   type Base_Index_Type is range 0..Max_Table_Size;
   subtype Index_Type is Base_Index_Type range 1..Max_Table_Size;
   type Contents_Type is range -1000 .. 1000;
   type Array_Type is array(Index_Type) of Contents_Type;

   function Ordered(A : Array_Type; L,U : Index_Type) return Boolean;
   --# pre 1 <= L and L <= U and U <= Max_Table_Size;

   function Perm(A, B : Array_Type) return Boolean;


   procedure Sort(Table : in out Array_Type);
   --# derives Table from Table;
   --# post Ordered(Table,1,Max_Table_Size) and
   --#   Perm(Table,Table~);
end Array_Utilities;

package body Array_Utilities is
  function Num_Repetition(A : Array_Type; E : Contents_Type) return Integer
    is Result: Integer := 0;
  begin
    for I in Index_Type range 1 .. Max_Table_Size loop
      if A(I) = E then
        Result := Result + 1;
      end if;
    end loop;
    return Result;
  end Num_Repetition;

  function Perm(A, B : Array_Type) return Boolean is
    Result: Boolean := True;
  begin
    for I in Index_Type range 1 .. Max_Table_Size loop
      if Num_Repetition(A, A(I)) /= Num_Repetition(B, A(I)) then
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
        --# pre 1 <= L and L <= M and M <= U and U <= Max_Table_Size;
        is
          Result: Boolean := True;
    begin
      for I in Index_Type range L .. M loop
         for J in Index_Type range M+1 .. U loop
           if A(I) > A(J) then
             Result := False;
           end if;
         end loop;
      end loop;
      return Result;
   end Partitioned;

   procedure Sort(Table : in out Array_Type) is
      Key : Index_Type;

      function Find_Smallest(Arr : Array_Type; L,U: Index_Type)
                            return Index_Type
        --# pre 1 <= L and L <= U and U <= Max_Table_Size;
        --# return I => (for all J in Index_Type range
        --#        L .. U => (Arr(J) >= Arr(I)));
      is
         K : Index_Type;
      begin
         K := L;
         for I in Index_Type range L+1..U loop
            if Arr(I) < Arr(K) then
               K := I;
            end if;
            --# assert 1 <= L and L+1 <= I and
            --#   I <= U and U <= Max_Table_Size and
            --#   K in Index_Type and
            --#   (for all J in Index_Type range
            --#     L .. I => (Arr(J) >= Arr(K)));
         end loop;
         return K;
      end Find_Smallest;

      procedure Swap_Elements(T : in out Array_Type;
                              I, J : in Index_Type)
        --# derives T from T,I,J;
        --# post T = T~[I => T~(J); J => T~(I)] and Perm(T,T~);
      is
         Temp : Contents_Type;
      begin
         Temp := T(I); T(I) := T(J); T(J) := Temp;
      end Swap_Elements;

   begin -- Sort
      for Low in Index_Type range 1 .. Max_Table_Size-1 loop
         Key := Find_Smallest(Table,Low,Max_Table_Size);
         if Key /= Low then
            Swap_Elements(Table,Low,Key);
         end if;
         --# assert 1 <= Low and Low <= Max_Table_Size-1 and
         --#      Ordered(Table,1,Low) and
         --#      Partitioned(Table,1,Low,Max_Table_Size) and
         --#      Perm(Table,Table~);
      end loop;
   end Sort;
end Array_Utilities;

